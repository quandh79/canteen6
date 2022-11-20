package Product;

import dao.impls.ProductsRepository;
import entities.Products;
import helper.DefaultScene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

import static helper.RootStage.rootStage;

public class ProductController implements Initializable {
    public TableView tbProduct;
    public TableColumn tdId;
    public TableColumn tdName;
    public TableColumn tdPrice;

    public void goToBill(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../home.fxml"));
        rootStage.setTitle("Hóa đơn");
        rootStage.setScene(new Scene(root,800,600));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tdId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tdName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tdPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        ObservableList<Products> ls = FXCollections.observableArrayList();
        ProductsRepository pr = new ProductsRepository();
        ls.addAll(pr.all());
        tbProduct.setItems(ls);
    }
}
