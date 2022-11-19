package home;

import helper.DefaultScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

import static helper.RootStage.rootStage;

public class HomeController {
    public void goToProduct(ActionEvent actionEvent) throws IOException {
        Parent history = FXMLLoader.load(getClass().getResource("../Product/product.fxml"));
        rootStage.setTitle("History");
        rootStage.setScene(new DefaultScene(history));
    }
}
