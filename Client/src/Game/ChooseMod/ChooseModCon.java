package Game.ChooseMod;

import Constants.Responsive;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaView;

import static Constants.MediaPlayers.*;
import static Constants.Paths.GameListFXMLPATH;
import static Constants.Responsive.*;
import static Main.Main.openFxml;

public class ChooseModCon{

    private String _1Player;
    private String _2Player;
    private Label label2;

    @FXML private AnchorPane anchorPane;
    @FXML private MediaView mediaView;
    @FXML private ImageView _1PlayerIm;
    @FXML private ImageView _2PlayerIm;
    @FXML private Label label;
    @FXML private Button back;

    @FXML private Label label1_1;
    @FXML private Label label1_2;
    @FXML private Label label1_3;

    @FXML private Label label2_1;
    @FXML private Label label2_2;
    @FXML private Label label2_3;

    public ChooseModCon(String _1Player, String _2Player) {
        this._1Player = _1Player;
        this._2Player = _2Player;
    }

    public void initialize() {

        //Make Contacts Responsive
        ResponsivePane(anchorPane, 100, 100 ,0, 0);
        ResponsiveMV(mediaView, 110, 100, 0, 0);
        ResponsiveIMG(_1PlayerIm, 67, 33, 7, 2);
        ResponsiveIMG(_2PlayerIm, 68, 33, 7, 64);
        ResponsiveBtn(back, 1, 7, 87, 1, 30);

        ResponsiveLBL(label, 7, 24, 4, 42, 55);
        ResponsiveLBL(label1_1, 10, 14, 18, 11.4, 100);
        ResponsiveLBL(label1_2, 7, 17, 38, 11.5, 70);
        ResponsiveLBL(label1_3, 11, 21, 48, 10, 100);

        ResponsiveLBL(label2_1, 10, 16, 18, 74, 100);
        ResponsiveLBL(label2_2, 16, 17, 31, 72, 60);
        ResponsiveLBL(label2_3, 11, 21, 49, 74.1, 100);

        back.setOnAction(event -> {
            PlayEffect(BUTTON_EFFECT);
            openFxml(GameListFXMLPATH);
        });
        mediaView.setMediaPlayer(CHOOSE_MOD_PLAYER);
        PlayMedia(CHOOSE_MOD_PLAYER);
    }

    public void go_1Player(){
        PlayEffect(BUTTON_EFFECT);
        openFxml(_1Player);
    }

    public void go_2Player(){
        PlayEffect(BUTTON_EFFECT);
        openFxml(_2Player);
    }
}




