package Game.Games.SnakeLadder_1;

import Main.Main;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.ImageCursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaView;

import java.net.URL;
import java.util.ResourceBundle;

import static Constants.MediaPlayers.*;
import static Constants.MediaPlayers.MENU_MEDIA_PLAYER;
import static Constants.Paths.*;
import static Constants.Responsive.*;
import static Game.Games.Common.GamesCommon.compose;
import static Game.Games.Common.GamesCommon.createTransition;
import static Game.Games.TicTacToe_1.Tic_1Con.setMute;
import static Main.Main.*;
import static Main.Main.stage;

public class Snake_1Con implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML private MediaView mediaView;
    @FXML private ImageView board;
    @FXML private ImageView optionsIm;

    @FXML private Button back;
    @FXML private Button exit;
    @FXML private Button mute;
    @FXML private Button menu;
    @FXML private Button options;
    @FXML private Button roll;

    @FXML private Label report;
    private double percent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Make Contacts Responsive
        ResponsivePane(anchorPane, 100, 100, 0, 0);
        anchorPane.setCursor(new ImageCursor(CURSOR_GAME));
        ResponsiveMV(mediaView, 100, 100 , 0, 0);
        ResponsiveIMG(board, 60, 60, 11, 48);

        ResponsiveBtn(options, 7, 8, 0, 92, 30);
        ResponsiveIMG(optionsIm, 7, 8, 0, 90);

        ResponsiveBtn(back, 1, 6, 3, 2, 30);
        ResponsiveBtn(mute, 1, 6, 0, 91, 30);
        mute.setOnAction(event -> setMute(mute));
        ResponsiveBtn(menu, 1, 6, 0, 91, 30);
        ResponsiveBtn(exit, 1, 6, 0, 91, 30);

        ResponsiveBtn(roll, 1, 6, 78, 50, 30);
        ResponsiveLBL(report, 1, 30, 78, 57, 50);

        InnerShadow(back, options, mute, menu, exit, report, roll);

        mediaView.setMediaPlayer(TIC_PLAYER);
        PlayMedia(TIC_PLAYER);

        FadeIn(GAME_MEDIA_PLAYER, MENU_MEDIA_PLAYER);

        stage.heightProperty().addListener((observable, oldValue, newValue) -> {
            percent = stage.getHeight() / 1080;
            options.setLayoutX(stage.getWidth() - options.getPrefWidth() - 25);
            options.setStyle("-fx-background-color: linear-gradient(to right, thistle,  #BAA19A); -fx-background-radius: 20");

            TranslateTransition[] transitions = new TranslateTransition[3];
            transitions[0] = createTransition(mute, percent * 15 , percent * -1 );
            transitions[1] = createTransition(menu, percent * 11 , percent * -11);
            transitions[2] = createTransition(exit, percent * 4 , percent * -20);

            options.setOnAction(event -> {
                PlayEffect(BUTTON_EFFECT);
                compose(transitions);
            });
        });

        SnakeLadder_Game_1 snake = new SnakeLadder_Game_1(report, roll);
        ResponsivePos(snake, 11, 48);

        anchorPane.getChildren().addAll(snake);
    }

    public void goMenu(){
        PlayEffect(BUTTON_EFFECT);
        openFxml(MenuFXMLPATH);
    }

    public void goBack() {
        PlayEffect(BUTTON_EFFECT);
        openFxml(GameListFXMLPATH);
    }

    public void quit(){
        PlayEffect(BUTTON_EFFECT);
        Main.quit();
    }
}
