package Loading.ProgressLoad;

import Constants.Responsive;
import Main.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class LoadProgressCon implements Initializable {

    @FXML AnchorPane anchorPane;
    @FXML ImageView imageView1;
    @FXML ImageView imageView2;
    @FXML ImageView imageView3;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Responsive.ResponsivePane(anchorPane, 100, 100);
        Responsive.ResponsiveIMG(imageView1, 52, 30, 23, 37);
        Responsive.ResponsiveIMG(imageView2, 36, 21, 32.2, 39.8);
        Responsive.ResponsiveIMG(imageView3, 16, 8, 51, 46.5);

    }
}