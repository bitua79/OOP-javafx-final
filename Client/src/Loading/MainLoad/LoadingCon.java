package Loading.MainLoad;

import Constants.Responsive;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

import static Constants.MediaPlayers.*;
import static Constants.Paths.*;
import static Main.Main.openFxml;

public class LoadingCon implements Initializable{

    @FXML private AnchorPane anchorPane;
    @FXML private MediaView mediaView ;
    @FXML private ProgressBar progressBar;
    private double progressBarValue;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Make Contacts Responsive
        Responsive.ResponsivePane(anchorPane, 100, 100);

        mediaView.setMediaPlayer(LOADING_PLAYER);
        Responsive.ResponsiveMV(mediaView, 100, 100,  0, 0);
        PlayMedia(LOADING_PLAYER);

        setProgress();

    }

    public void setProgress(){
        progressBarValue = 0.01;
        Responsive.ResponsivePRB(progressBar, 120, 0.925, 20);

        //Spend Time For Progress
        Timeline progressTimeline = new Timeline(new KeyFrame(Duration.millis(20), ae -> {
            progressBarValue *= 1.02;
            progressBar.setProgress(progressBarValue);
        }));

        //After Finish  The Progress Go To Main Page
        progressTimeline.setOnFinished(event -> openFxml(MenuFXMLPATH,  SignInFXMLPATH));


        //Start Progress
        progressTimeline.setCycleCount(233);
        progressTimeline.play();
    }

}
