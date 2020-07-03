package Options.AccountSetting.ChangePass;
import Main.Main;
import Options.AccountSetting.AcSeCon;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
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
import static Constants.Notifications.CHANGE_PASSWORD;
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

public class ChangePassCon implements Initializable {

    @FXML private AnchorPane anchorPane;

    @FXML private TextField passField;
    @FXML private TextField newPassField;
    @FXML private TextField conNwPassField;

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

        ResponsiveTXF(passField, 1, 25, 25, 54, 24);
        ResponsiveTXF(newPassField, 1, 25, 32, 54, 24);
        ResponsiveTXF(conNwPassField, 1, 25, 39, 54, 24);


        ResponsiveIMG(imageView, 73, 62, 19, 19.5);
        ResponsiveIMG(yesIMG, 19,  12, 62, 53);
        ResponsiveIMG(noIMG, 19, 12, 62, 64);

        ResponsiveLBL(questionLbl, 10, 25, 47, 58, 60);
        ResponsiveLBL(report, 5, 26, 43, 54, 50);
        report.setAlignment(Pos.CENTER);
        ResponsiveLBL(yesLbl, 1, 20, 67, 57, 60);
        ResponsiveLBL(noLbl, 1, 20, 67, 68, 60);

        DropShadow(questionLbl, report, yesLbl, noLbl, passField, newPassField, conNwPassField);

        passField.textProperty().addListener((observable, oldValue, newValue) -> handleField(passField));
        newPassField.textProperty().addListener((observable, oldValue, newValue) -> handleField(newPassField));
        conNwPassField.textProperty().addListener((observable, oldValue, newValue) -> handleField(conNwPassField));
    }

    //Change textfield style and disable yes if its length < 8
    public void handleField(TextField textField){
        if (textField.getText().length() < 8){
            report.setText("Short Password!");
            textField.setId("Wrong-text-field");
        }else {
            boolean disable = textField.getText().isEmpty() || textField.getText().trim().isEmpty();
            textField.setId("");
            report.setText("");
            yesIMG.setDisable(disable);
            yesLbl.setDisable(disable);
        }
    }

    //reject change password -> back
    public void No(){
        PlayEffect(BUTTON_EFFECT);
        AcSeCon.close();
    }

    //connect to server if pass is correct -> and change password
    //send username and password to server and get:
    //-1 -> if username and password are not match
    // 1 -> if username and password match
    public void Yes(){
        PlayEffect(BUTTON_EFFECT);
        try {
            if (newPassField.getText().equals(passField.getText())){
                report.setText("New password matches Current one!");
                return;
            }
            if (!conNwPassField.getText().equals(newPassField.getText())){
                report.setText("Not match new password and confirm!");
                return;
            }
            Socket socket = new Socket(HOST, ACCOUNT_SETTING);
            DataOutputStream to = new DataOutputStream(socket.getOutputStream());
            DataInputStream from = new DataInputStream(socket.getInputStream());

            to.writeInt(CHANGE_PASSWORD);
            to.writeUTF(Main.USERNAME);
            to.writeUTF(passField.getText().trim());
            to.writeUTF(newPassField.getText().trim());

            int result = from.readInt();
            if (result == NOT_MATCH){
                report.setText("Wrong Current password!");
                passField.setText("");

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
