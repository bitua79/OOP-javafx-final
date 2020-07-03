package Entrance;

import Constants.Responsive;
import Main.Main;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.util.Duration;


import java.net.URL;
import java.util.ResourceBundle;

import static Constants.Paths.LoadingFXMLPATH;
import static Main.Main.openFxml;

public class EntranceCon implements Initializable {

    @FXML private ImageView imageView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Responsive.ResponsiveIMG(imageView, 100, 0, 0);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(3000)));
        timeline.setOnFinished(event -> {
             Main.openFxml(LoadingFXMLPATH);
        });

        timeline.play();
    }
}
