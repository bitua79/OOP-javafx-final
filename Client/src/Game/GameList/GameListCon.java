package Game.GameList;

import Game.ChooseMod.ChooseModCon;
import Main.Main;
import animatefx.animation.ZoomIn;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Constants.MediaPlayers.*;
import static Constants.Paths.*;
import static Constants.Responsive.*;
import static Main.Main.*;

public class GameListCon implements Initializable {

    @FXML AnchorPane anchorPane;
    @FXML ImageView backImage;
    @FXML ImageView frontImage;

    @FXML ImageView battle_shipIMG;
    @FXML ImageView snack_ladderIMG;
    @FXML ImageView tic_tac_toeIMG;

    @FXML Label battle_shipLbl;
    @FXML Label snack_ladderLbl;
    @FXML Label tic_tac_toeLbl;

    @FXML Button back;
    @FXML Button gameResult;
    private Parent parent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Make Contacts Responsive
        ResponsivePane(anchorPane, 100, 100);
        ResponsiveIMG(backImage, 120, 0, 0);
        ResponsiveIMG(frontImage, 100 , 0, 0);

        ResponsiveIMG(tic_tac_toeIMG, 42, 28, 2, 71);
        ResponsiveIMG(snack_ladderIMG, 42, 28, 33, 55);
        ResponsiveIMG(battle_shipIMG, 42, 28, 60, 36);

        ResponsiveLBL(tic_tac_toeLbl, 8, 14, 13.5, 79, 60);
        ResponsiveLBL(snack_ladderLbl, 19, 12, 36, 63, 60);
        ResponsiveLBL(battle_shipLbl, 8, 15, 72, 44, 60);

        ResponsiveBtn(back, 1, 6, 5, 3, 30);
        back.setOnAction(event -> {
            PlayEffect(BUTTON_EFFECT);
            backToMenu();
        });
        ResponsiveBtn(gameResult, 1, 9, 90, 3, 30);

        InnerShadow(battle_shipLbl, snack_ladderLbl, tic_tac_toeLbl, back, gameResult);

        gameResult.setOnAction(event -> {
            PlayEffect(BUTTON_EFFECT);
            open();
        });
    }

    public void open(){
        try {
            parent = FXMLLoader.load(Main.class.getResource(GameResultFXMLPATH));
            anchorPane.getChildren().add(parent);

            Main.stage.setHeight(Main.stage.getHeight()+0.1);
            new ZoomIn(parent).play();
        } catch (IOException e) {
            e.printStackTrace();
            //stay on page
        }
    }

    public void openMod(String _1, String _2){
        PlayEffect(BUTTON_EFFECT);
        FXMLLoader loader = new FXMLLoader((getClass().getResource(ChooseModFXMLPATH)));
        ChooseModCon con = new ChooseModCon(_1, _2);
        loader.setController(con);
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        openFxml(parent);
    }

    public void openTicMod() {
        openMod(Tic_1displayFXMLPATH, ChooseFrFXMLPATH);
    }

    public void openBattleMod() {
        openMod(Battle_1displayFXMLPATH, Tic_2displayFXMLPATH);
    }

    public void openSnakeMod() throws IOException {
        openMod(Snake_1displayFXMLPATH, Tic_2displayFXMLPATH);
    }
}
