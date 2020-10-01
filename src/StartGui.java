import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartGui extends Application implements Initializable {

    @FXML private ImageView logo;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("StartGui.fxml"));

        Scene primaryscene = new Scene(root);
        primaryStage.setScene(primaryscene);

        primaryStage.setScene(primaryscene);
        primaryStage.show();
    }

    public Image getImage() throws FileNotFoundException{
        Image image = new Image(new FileInputStream("bin/Bilder/logo.png"));
        return image;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            logo.setImage(getImage());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
