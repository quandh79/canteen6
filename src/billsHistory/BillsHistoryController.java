package billsHistory;

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
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static helper.RootStage.rootStage;

public class BillsHistoryController implements Initializable {
    public TableColumn tdTotal;
    public TableColumn tdDate;
    public TableColumn tdId;
    public TableColumn<Bill, Button> tdAction;
    public TableView tbvBill;
    public AnchorPane billList;

    private final ObservableList<Bill> billObservableList = FXCollections.observableArrayList();
    private final BillsRepository billsRepository = new BillsRepository();

    public void goToNewBill(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../home.fxml"));
        rootStage.setTitle("New Bill");
        rootStage.setScene(new Scene(root,800,600));
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        billObservableList.setAll(billsRepository.all());

        tdId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tdDate.setCellValueFactory(new PropertyValueFactory<>("datetime"));
        tdTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        tdAction.setCellValueFactory(new PropertyValueFactory<Bill, Button>("view"));
        tbvBill.setItems(billObservableList);
    }


    public void goHome(ActionEvent actionEvent)throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../home/homec.fxml"));
        rootStage.setTitle("Home");
        rootStage.setScene(new Scene(root,800,600));
    }
}
