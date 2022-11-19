package billDetails;

import dao.impls.BillsRepository;
import entities.Bill;
import entities.Products;
import helper.DefaultScene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import static helper.RootStage.rootStage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BillDetailsController implements Initializable {
    public Text txBillId;
    public Text txDate;
    public TableView tbvBillProduct;
    public TableColumn tdName;
    public TableColumn tdUnit;
    public TableColumn tdQuantity;
    public TableColumn tdPrice;
    public TableColumn tdSubTotal;
    public Text txTotal;
    public AnchorPane apBillDetails;
    public Text txMartketName;
    public Button btNewBill;
    public Button btHistory;
    public Text txAddress;

    public static Bill bill;
    private final ObservableList<Products> pr = FXCollections.observableArrayList();
    private final BillsRepository billsRepository = new BillsRepository();

    public void goToNewBill(ActionEvent actionEvent) throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("../home.fxml"));
        rootStage.setTitle("New Bill");
        rootStage.setScene(new DefaultScene(p));
    }

    public void goToHistory(ActionEvent actionEvent) throws Exception {
        Parent p = FXMLLoader.load(getClass().getResource("../billsHistory/billsHistory.fxml"));
        rootStage.setTitle("History");
        rootStage.setScene(new DefaultScene(p));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txBillId.setText(bill.getId().toString());
        txDate.setText(bill.getDatetime().toLocaleString());
        txTotal.setText(String.valueOf(bill.getTotal()));
        pr.setAll(billsRepository.getDetail(bill));

        tdName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tdQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tdPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tdSubTotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
        tbvBillProduct.setItems(pr);

    }
}
