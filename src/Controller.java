import autoCB.AutoCompleteComboBoxListener;
import dao.impls.BillsRepository;
import dao.impls.ProductsRepository;
import entities.Bill;
import entities.Products;
import helper.DefaultScene;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static helper.RootStage.rootStage;
import static billDetails.BillDetailsController.bill;
public class Controller implements Initializable {
    //layout
    public Pane home;
    public Button addProducts;
    public Button addBill;
    public Button handleCancel;
    public Button handleHistory;
    public Text txVailidate;
    public Text txTotal;
    public ComboBox<Products> cboSelectNameProduct;
    public TextField txtQuantity;
    public TextField txtPrice;

    //table
    public TableView<Products> tbvAddProduct;
    public TableColumn<Products, String> tdId;
    public TableColumn<Products, String> tdName;
    public TableColumn tdUnit;
    public TableColumn tdQuantity;
    public TableColumn<Products, Float> tdPrice;
    public TableColumn tdSubTotal;

    //properties
    private final ObservableList<Products> pr = FXCollections.observableArrayList();

    private final ProductsRepository rp = new ProductsRepository();
    private final BillsRepository billsRepository = new BillsRepository();
    public Button del;

    //method
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Products> productsList = FXCollections.observableArrayList();
        productsList.setAll(rp.all());

        cboSelectNameProduct.setItems(productsList);
        new AutoCompleteComboBoxListener<>(cboSelectNameProduct);
        cboSelectNameProduct.setConverter(new StringConverter<Products>() {

            @Override
            public String toString(Products object) {
                if (object == null) return null;
                return object.toString();
            }

            @Override
            public Products fromString(String string) {
                Products employee = new Products();
                for(Products emp : cboSelectNameProduct.getItems()){
                    if(emp.getName().equals(string)){
                        employee = emp;
                        break;
                    }
                }
                return employee;
            }
        });

        tdId.setCellFactory(col -> {
            TableCell<Products, String> cell = new TableCell<>();
            cell.textProperty().bind(Bindings.when(cell.emptyProperty())
                    .then("")
                    .otherwise(cell.indexProperty().add(1).asString()));
            return cell;
        });
        tdName.setCellValueFactory(new PropertyValueFactory<>("name"));
//        tdUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        tdQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tdPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tdSubTotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

        tbvAddProduct.setItems(pr);

        tbvAddProduct.setEditable(true);
        del.setOnAction(event -> deleteProduct() );
        tdQuantity.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tdQuantity.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Products, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Products, Integer> event) {
                Products product = event.getRowValue();
                if (event.getNewValue()<=0){
                    pr.remove(product);
                }else{
                    pr.get(pr.indexOf(product)).setQuantity(event.getNewValue());
                }
                tbvAddProduct.refresh();
                txTotal.setText(String.valueOf(finalCost()));
                bill.setTotal(finalCost());
                addBill.setDisable(pr.size()<=0? true: false);
            }
        });
        addBill.setDisable(pr.size()<=0? true: false);
    }

    boolean checkValidate() throws Exception {
        String check = txtQuantity.getText();
        Pattern pattern = Pattern.compile("\\d*");
        Matcher matcher = pattern.matcher(check);
        txVailidate.setVisible(false);

        if (((check.length() == 0) || !(matcher.matches()) || Integer.parseInt(check) == 0)) {
            txtQuantity.setStyle("-fx-border-color: red; -fx-border-width: 3px");
            txVailidate.setVisible(true);
            return false;
        } else {
            txtQuantity.setStyle(null);
            return true;
        }
    }

    public float finalCost() {
        float total = 0;
        for (Products p : pr) {
            float entryCost = p.getQuantity() * p.getPrice();
            total = total + entryCost;
        }
        return total;
    }

    public void handleAddProduct(ActionEvent actionEvent)  {
        Integer qty = Integer.parseInt(txtQuantity.getText());
        Products selectedProduct = cboSelectNameProduct.getSelectionModel().getSelectedItem();
        try{
            checkValidate();
            if(!checkValidate()) throw new Exception();
            if (pr.contains(selectedProduct)) {
                selectedProduct = pr.get(pr.indexOf(selectedProduct));
                Integer existedQty = selectedProduct.getQuantity();
                selectedProduct.setQuantity(existedQty += qty);
                tbvAddProduct.refresh();
            } else {
                selectedProduct.setQuantity(qty);
                pr.add(selectedProduct);
            }
            addBill.setDisable(pr.size()<=0? true: false);
            txTotal.setText(String.valueOf(finalCost()));
            txtQuantity.clear();
            txtPrice.clear();
            cboSelectNameProduct.getSelectionModel().clearSelection();
        }catch (Exception e){}

    }


    public void handleSubmit(ActionEvent actionEvent) {
        try {
            bill =billsRepository.create() ;
            if(pr.size() <=0) throw new Exception();
            bill.setDatetime(new Date());
            bill.setTotal(finalCost());
            boolean res = billsRepository.uploadDetail(bill, new ArrayList<>(pr));
            if (res) {
                Parent listBook = FXMLLoader.load(getClass().getResource("billDetails/billDetails.fxml"));
                rootStage.setTitle("Bill");
                rootStage.setScene(new Scene(listBook, 800, 600));
            }
        }catch (Exception err){}

    }

    public void handleCancel(ActionEvent a) throws Exception {
        pr.clear();
        tbvAddProduct.refresh();
        txTotal.setText(String.valueOf(finalCost()));
        addBill.setDisable(pr.size()<=0? true: false);
    }

    public void handleChangeHistory(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("./billsHistory/billsHistory.fxml"));
        rootStage.setTitle("Bill");
        rootStage.setScene(new Scene(root,800,600));
    }

    public void choose(ActionEvent actionEvent) throws NullPointerException {
        txtPrice.setText(String.valueOf(cboSelectNameProduct.getSelectionModel().getSelectedItem().getPrice()));
        txtPrice.setEditable(false);
    }

    public void deleteProduct() {
        try {
            ObservableList<Products> proSelected, allProduct;
            allProduct = tbvAddProduct.getItems();
            proSelected = tbvAddProduct.getSelectionModel().getSelectedItems();
            proSelected.forEach(allProduct::remove);
            txTotal.setText(String.valueOf(finalCost()));
        }catch (Exception e){System.out.println("e.getMessage()");}
    }
}
