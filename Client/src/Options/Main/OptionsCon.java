package Options.Main;

import Main.Main;
import animatefx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Constants.MediaPlayers.*;
import static Constants.Paths.*;
import static Constants.Responsive.*;
import static Main.Main.*;

public class OptionsCon implements Initializable {

    @FXML private AnchorPane anchorPane;
    @FXML private MediaView mediaView;
    @FXML private MediaView front;

    @FXML private CheckBox SFX;
    @FXML private CheckBox mute;
    @FXML private Slider volume;

    @FXML private Label optionLbl;
    @FXML private Label volumeLbl;
    @FXML private Label accountLbl;
    @FXML private Label friendLbl;

    @FXML private Button back;
    private Parent parent;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Make Contacts Responsive
        ResponsivePane(anchorPane, 100, 100);
        ResponsiveMV(mediaView, 100, 100, 0, 0);
        ResponsiveMV(front, 100, 100 , 0, 0);

        ResponsiveLBL(optionLbl, 17, 21, 5, 16, 120);
        ResponsiveLBL(volumeLbl, 11, 14, 28, 8, 80);
        ResponsiveLBL(accountLbl, 1, 19, 67, 9, 60);
        ResponsiveLBL(friendLbl, 1, 13, 74, 9, 60);

        ResponsiveBtn(back, 5, 7, 3, 89, 35);
        Main.stage.widthProperty().addListener((observable, oldValue, newValue) -> {
            back.setLayoutX(Main.stage.getWidth()* 0.9 - back.getWidth());
        });
        back.setOnAction(event -> Main.backToMenu());

        ResponsiveSlider(volume, 4, 16, 37, 20);
        ResponsiveCheckBox(mute, 8, 20, 44, 9, 63);
        ResponsiveCheckBox(SFX, 8, 20, 55, 9, 63);

        DropShadow(optionLbl, volumeLbl, accountLbl, friendLbl, SFX, mute, back);

        mediaView.setMediaPlayer(OPTIONS_PLAYER);
        PlayMedia(OPTIONS_PLAYER);
        front.setMediaPlayer(OPTIONS_FRONT_PLAYER);
        PlayMedia(OPTIONS_FRONT_PLAYER);

        //Mute all media players
        mute.selectedProperty().addListener((observable, oldValue, newValue) -> {
            PlayEffect(BUTTON_EFFECT);
            if (newValue){
                MENU_MEDIA_PLAYER.setMute(true);
            }
            else {
                MENU_MEDIA_PLAYER.setMute(false);
            }
        });

        //Mute sound effects
        SFX.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) EffectVolume = 0;
            else EffectVolume = MediaVolume;
            setSFX();
        });

        //Change volume value
        volume.valueProperty().addListener((observable, oldValue, newValue) -> {
            MediaVolume = newValue.doubleValue() / 100;
            BUTTON_EFFECT.setVolume(MediaVolume);
            MENU_MEDIA_PLAYER.setVolume(MediaVolume);
            GAME_MEDIA_PLAYER.setVolume(MediaVolume);
            if (!SFX.isSelected())EffectVolume = MediaVolume;
        });
    }

    //Open account setting page
    @FXML public void openAcSetting(){
        PlayEffect(BUTTON_EFFECT);
        open(AcSettingFXMLPATH);
    }

    @FXML public void openFrSetting(){
        PlayEffect(BUTTON_EFFECT);
        open(FriendSettingFXMLPATH);
    }

    public static AnchorPane mix;
    private Button closeBack;
    public void open(String URL){
        PlayEffect(BUTTON_EFFECT);
        try {
            parent = FXMLLoader.load(Main.class.getResource(URL));

            //add a back button to options page
            closeBack = new Button("Back");
            closeBack.setOnAction(event -> close());
            closeBack.getStylesheets().add(OptionsCon.class.getResource("OptionsStyle.css").toExternalForm());
            ResponsiveBtn(closeBack, 5, 6, 28, 53, 30);
            DropShadow(closeBack);

            mix = new AnchorPane(anchorPane, parent);
            Main.mainScene.setRoot(mix);
            anchorPane.setDisable(true);
            Main.stage.setHeight(Main.stage.getHeight()+0.1);

            FadeInRight fadeInRight = new FadeInRight(parent);
            fadeInRight.setOnFinished(event -> mix.getChildren().add(closeBack));
            fadeInRight.play();

        } catch (IOException e) {
            e.printStackTrace();
            openFxml(OptionFXMLPATH);
        }
    }

    //Close page
    @FXML public void close(){
        PlayEffect(BUTTON_EFFECT);
        FadeOutRight fadeOutRight = new FadeOutRight(parent);
        fadeOutRight.setOnFinished(event -> {
            mix.getChildren().removeAll(parent);
            anchorPane.setDisable(false);
        });
        mix.getChildren().remove(closeBack);
        fadeOutRight.play();
    }
}
