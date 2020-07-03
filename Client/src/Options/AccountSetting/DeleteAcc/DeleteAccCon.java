package Options.AccountSetting.DeleteAcc;
import Main.Main;
import Options.AccountSetting.AcSeCon;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import static Constants.MediaPlayers.*;
import static Constants.MediaPlayers.BUTTON_EFFECT;
import static Constants.Notifications.DELETE_ACCOUNT;
import static Constants.Paths.OptionFXMLPATH;
import static Constants.Paths.SignInFXMLPATH;
import static Constants.Ports.ACCOUNT_SETTING;
import static Constants.Ports.HOST;
import static Constants.Report.NOT_MATCH;
import static Constants.Report.SUCCESSFUL;
import static Constants.Responsive.*;
import static Main.Main.DropShadow;
import static Main.Main.openFxml;

public class DeleteAccCon implements Initializable {

    @FXML private AnchorPane anchorPane;
    @FXML private TextField passField;

    @FXML private ImageView imageView;
    @FXML private ImageView yesIMG;
    @FXML private ImageView noIMG;

    @FXML private Label questionLbl;
    @FXML private Label report;
    @FXML private Label yesLbl;
    @FXML private Label noLbl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Make Contacts Responsive
        ResponsivePane(anchorPane, 100, 100);
        ResponsiveTXF(passField, 1, 20, 40, 54, 24);

        ResponsiveIMG(imageView, 73, 62, 19, 19.5);
        ResponsiveIMG(yesIMG, 19,  12, 58, 53);
        ResponsiveIMG(noIMG, 19, 12, 58, 64);

        ResponsiveLBL(questionLbl, 10, 25, 20, 55, 60);
        ResponsiveLBL(report, 5, 16, 47, 57, 50);
        ResponsiveLBL(yesLbl, 1, 20, 63, 57, 60);
        ResponsiveLBL(noLbl, 1, 20, 63, 68, 60);

        DropShadow(questionLbl, report, yesLbl, noLbl, passField);

        passField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (passField.getText().length() < 8){
                report.setText("Short Password!");
                passField.setId("Wrong-text-field");
            }else {
                boolean disable = passField.getText().isEmpty() || passField.getText().trim().isEmpty();
                passField.setId("");
                report.setText("");
                yesIMG.setDisable(disable);
                yesLbl.setDisable(disable);
            }
        });
    }

    //reject delete account -> back
    public void No(){
        PlayEffect(BUTTON_EFFECT);
        AcSeCon.close();
    }

    //connect to server if pass is correct -> and delete account
    //send username and password to server and get:
    //-1 -> if username and password are not match
    // 1 -> if username and password match
    public void Yes(){
        PlayEffect(BUTTON_EFFECT);
        try {
            Socket socket = new Socket(HOST, ACCOUNT_SETTING);
            DataOutputStream to = new DataOutputStream(socket.getOutputStream());
            DataInputStream from = new DataInputStream(socket.getInputStream());

            to.writeInt(DELETE_ACCOUNT);
            to.writeUTF(Main.USERNAME);
            to.writeUTF(passField.getText().trim());

            int result = from.readInt();
            if (result == NOT_MATCH){
                passField.setText("");
                report.setText("Wrong password!");

            } else if (result == SUCCESSFUL){
                Main.mainScene.setRoot(new Pane());
                openFxml(MENU_PLAYER, SignInFXMLPATH);
            }

        } catch (Exception e) {
            openFxml(OptionFXMLPATH);
            e.printStackTrace();
        }
    }
}
