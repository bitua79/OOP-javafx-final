package Game.Story;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaView;

import java.net.URL;
import java.util.ResourceBundle;

import static Constants.MediaPlayers.MAIN_STORY_PLAYER;
import static Constants.Paths.GameListFXMLPATH;
import static Constants.Paths.MenuFXMLPATH;
import static Constants.Responsive.*;
import static Main.Main.DropShadow;
import static Main.Main.openFxml;

public class MainStoryCon implements Initializable {

    @FXML private AnchorPane anchorPane;
    @FXML private MediaView mediaView;
    @FXML private Button back;
    @FXML private Button pass;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Make Contacts Responsive
        ResponsivePane(anchorPane, 100, 100);
        ResponsiveMV(mediaView, 100, 100, 0, 0);

        ResponsiveBtn(back, 1, 6, 5, 3, 30);
        ResponsiveBtn(pass, 1, 6, 5, 90, 30);

        mediaView.setMediaPlayer(MAIN_STORY_PLAYER);
        MAIN_STORY_PLAYER.setAutoPlay(true);
        MAIN_STORY_PLAYER.setOnEndOfMedia(this::goGameList);

        DropShadow(back, pass);
    }

    public void goGameList(){
        MAIN_STORY_PLAYER.stop();
        openFxml(GameListFXMLPATH);
    }

    public void backToMenu(){
        MAIN_STORY_PLAYER.stop();
        openFxml(MenuFXMLPATH);
    }

}
