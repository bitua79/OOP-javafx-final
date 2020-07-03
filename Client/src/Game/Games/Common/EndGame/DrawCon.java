package Game.Games.Common.EndGame;

import animatefx.animation.RollIn;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

import static Constants.MediaPlayers.*;
import static Constants.Responsive.*;
import static Constants.Responsive.ResponsiveLBL;
import static Main.Main.*;

public class DrawCon implements Initializable {

    @FXML private AnchorPane anchorPane;
    @FXML private MediaView mediaView;
    @FXML private Label label;
    @FXML private Button button;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Make Contacts Responsive
        ResponsivePane(anchorPane, 100, 100, 0, 0);
        ResponsiveMV(mediaView, 70, 80, 15, 33);
        mediaView.setMediaPlayer(DRAW_PLAYER);
        PlayMedia(DRAW_PLAYER);

        if (EffectVolume != 0) DRAW_EFFECT.setVolume(0.2);
        DRAW_EFFECT.play();

        FadeIn(MENU_MEDIA_PLAYER, GAME_MEDIA_PLAYER);
        isPlaying = false;

        ResponsiveBtn(button, 1, 6, 18, 35, 30);
        ResponsiveLBL(label, 1, 20, 24, 45, 70);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2500), ae -> {
            new RollIn(label).play();
        }));
        timeline.setCycleCount(10);
        timeline.play();
        InnerShadow(button, label);

        button.setOnAction(event -> {
            PlayEffect(BUTTON_EFFECT);
            backToMenu();
        });
    }
}
