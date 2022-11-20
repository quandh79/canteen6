package home;

import helper.DefaultScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

import static helper.RootStage.rootStage;

public class HomeController {
    public void goToProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../Product/product.fxml"));
        rootStage.setTitle("San pham");
        rootStage.setScene(new Scene(root,800,600));
    }

    public void goToBill(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../billsHistory/billsHistory.fxml"));
        rootStage.setTitle("Hoa don");
        rootStage.setScene(new Scene(root,800,600));
    }
}
