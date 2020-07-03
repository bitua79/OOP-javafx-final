package Options.AccountSetting.LogOut;
import Main.Main;
import Options.AccountSetting.AcSeCon;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import static Constants.MediaPlayers.*;
import static Constants.Paths.SignInFXMLPATH;
import static Constants.Ports.GET_OFFLINE;
import static Constants.Ports.HOST;
import static Constants.Responsive.*;
import static Main.Main.*;

public class LogOutCon implements Initializable {

    @FXML private AnchorPane anchorPane;

    @FXML private ImageView imageView;
    @FXML private ImageView yesIMG;
    @FXML private ImageView noIMG;

    @FXML private Label questionLbl;
    @FXML private Label yesLbl;
    @FXML private Label noLbl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Make Contacts Responsive
        ResponsivePane(anchorPane, 100, 100);
        ResponsiveIMG(imageView, 73, 62, 19, 19.5);
        ResponsiveIMG(yesIMG, 19,  12, 54, 50);
        ResponsiveIMG(noIMG, 19, 12, 54, 61);

        ResponsiveLBL(questionLbl, 10, 20, 23, 55, 70);
        ResponsiveLBL(yesLbl, 1, 20, 59, 54, 60);
        ResponsiveLBL(noLbl, 1, 20, 59, 65, 60);

        DropShadow(questionLbl, yesLbl, noLbl);
    }

    //reject log out -> back
    public void No() {
        PlayEffect(BUTTON_EFFECT);
        AcSeCon.close();
    }

    //log out -> offline and go sign in page
    public void Yes(){
        PlayEffect(BUTTON_EFFECT);
        try {
            Socket socket = new Socket(HOST, GET_OFFLINE);
            DataOutputStream to = new DataOutputStream(socket.getOutputStream());

            to.writeUTF(USERNAME);
            openFxml(MENU_MEDIA_PLAYER, SignInFXMLPATH);

        } catch (IOException e) {
            e.printStackTrace();
            openFxml(MenuFXMLPATH);
        }

        Main.mainScene.setRoot(new Pane());
        openFxml(MENU_PLAYER, SignInFXMLPATH);
    }
}
