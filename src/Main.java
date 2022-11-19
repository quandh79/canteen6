import helper.DefaultScene;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import static helper.RootStage.rootStage;
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
        //
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        rootStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        primaryStage.setTitle("POS Market");
        primaryStage.setScene(new DefaultScene(root));
        primaryStage.show();

    }
}
