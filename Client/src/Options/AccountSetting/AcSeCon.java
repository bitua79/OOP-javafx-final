package Options.AccountSetting;

import Main.Main;
import Options.Main.OptionsCon;
import animatefx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Constants.MediaPlayers.*;
import static Constants.Paths.*;
import static Constants.Responsive.*;
import static Main.Main.DropShadow;
import static Main.Main.openFxml;

public class AcSeCon implements Initializable {

    @FXML private AnchorPane anchorPane;
    @FXML private ImageView imageView;

    @FXML private Label changeNameLbl;
    @FXML private Label changePassLbl;
    @FXML private Label deleteAccLbl;
    @FXML private Label logoutLbl;
    public static Parent parent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Make Contacts Responsive
        ResponsivePane(anchorPane, 100, 100);
        ResponsiveIMG(imageView, 72.4, 34.4, 19, 50);

        ResponsiveLBL(changeNameLbl, 1, 26, 43, 58, 50);
        ResponsiveLBL(changePassLbl, 1, 27, 51, 58, 50);
        ResponsiveLBL(deleteAccLbl, 1, 18, 58, 61, 50);
        ResponsiveLBL(logoutLbl, 1, 12, 65, 61, 50);

        DropShadow(changeNameLbl, changePassLbl, deleteAccLbl, logoutLbl);
    }

    //Open ChangeName alarm
    public void openChangeName(){
        open(ChangeNameFXMLPATH);
    }

    //Open ChangePass alarm
    public void openChangePass(){
        open(ChangePassFXMLPATH);
    }

    //Open DeleteAcc alarm
    public void openDeleteAcc(){
        open(DeleteAccFXMLPATH);
    }

    //Open logout alarm
    public void openLogOut(){
        open(LogoutFXMLPATH);
    }

    public static Pane mix;
    //Open alarm
    @FXML public void open(String URL){
        PlayEffect(BUTTON_EFFECT);
        try {
            parent = FXMLLoader.load(Main.class.getResource(URL));

            mix = new Pane(OptionsCon.mix, parent);
            OptionsCon.mix.setOpacity(0.3);
            Main.mainScene.setRoot(mix);
            Main.stage.setHeight(Main.stage.getHeight()+0.1);

            FadeIn fadeIn = new FadeIn(parent);
            fadeIn.play();

        } catch (IOException e) {
            e.printStackTrace();
            openFxml(OptionFXMLPATH);
        }
    }

    //Close alarm
    @FXML public static void close(){
        PlayEffect(BUTTON_EFFECT);
        FadeOut fadeOut = new FadeOut(parent);
        OptionsCon.mix.setOpacity(1);
        fadeOut.setOnFinished(event -> {
            mix.getChildren().removeAll(parent);
        });
        fadeOut.play();
    }
}
