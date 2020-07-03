package Menu;

import Main.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Constants.MediaPlayers.*;
import static Main.Main.InnerShadow;
import static Constants.Paths.OptionFXMLPATH;
import static Constants.Responsive.*;
import static Main.Main.*;

public class MenuCon implements Initializable {

    @FXML private AnchorPane anchorPane;
    @FXML private MediaView mediaView;
    @FXML private Label name;
    @FXML private Label welcome;
    @FXML private Label games;
    @FXML private Label chat;
    @FXML private Label options;
    @FXML private Label exit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Make Contacts Responsive
        ResponsivePane(anchorPane, 100, 100);

        mediaView.setMediaPlayer(MENU_PLAYER);
        ResponsiveMV(mediaView, 100, 100, 0, 0);
        PlayMedia(MENU_PLAYER);

        ResponsiveLBL(name, 1, 34, 7, 56, 200);
        ResponsiveLBL(welcome, 1,50, 27, 45, 100);
        welcome.setAlignment(Pos.CENTER);
        welcome.setText("Welcome "+Main.USERNAME);

        ResponsiveLBL(games, 1, 19, 40, 65, 120);
        games.setOnMouseClicked(event -> {
            PlayEffect(BUTTON_EFFECT);
            openFxml(GameListFXMLPATH);
        });

        ResponsiveLBL(chat, 1, 13, 52, 66, 120);

        ResponsiveLBL(options, 1, 21, 64,  64, 120);
        options.setOnMouseClicked(event -> {
            PlayEffect(BUTTON_EFFECT);
            openFxml(OptionFXMLPATH);
        });

        ResponsiveLBL(exit, 1, 10, 76, 66, 120);
        exit.setOnMouseClicked(event -> {
            PlayEffect(BUTTON_EFFECT);
            quit();
        });

        InnerShadow(name, welcome, games, chat, options, exit);
    }
}
