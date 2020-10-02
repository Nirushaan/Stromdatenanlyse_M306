package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartGui extends Application{



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("StartGui.fxml"));

        Scene primaryscene = new Scene(root);
        primaryStage.setScene(primaryscene);

        primaryStage.setTitle("Datenanalyse");

        primaryStage.setScene(primaryscene);
        primaryStage.show();

    }

}
