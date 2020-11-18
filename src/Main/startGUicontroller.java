package Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class startGUicontroller implements Initializable {
    @FXML
    private ImageView logo;



    public Image getImage() throws FileNotFoundException {
        Image image = new Image(new FileInputStream("resources/Bilder/logo.png"));
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

    public void start(ActionEvent actionEvent) throws IOException {
        Parent saveGUIparent = FXMLLoader.load(getClass().getResource("DiagrammGui.fxml"));
        Scene saveGUIscene = new Scene(saveGUIparent);

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        window.setScene(saveGUIscene);
        window.show();

    }
}
