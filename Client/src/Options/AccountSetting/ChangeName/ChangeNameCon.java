package Options.AccountSetting.ChangeName;
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
import static Constants.Notifications.CHANGE_USERNAME;
import static Constants.Paths.SignInFXMLPATH;
import static Constants.Ports.ACCOUNT_SETTING;
import static Constants.Ports.HOST;
import static Constants.Report.*;
import static Constants.Responsive.*;
import static Main.Main.*;

public class ChangeNameCon implements Initializable {

    @FXML private AnchorPane anchorPane;

    @FXML private TextField passField;
    @FXML private TextField newNameField;

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

        ResponsiveTXF(passField, 1, 25, 27, 54, 24);
        ResponsiveTXF(newNameField, 1, 25, 34, 54, 24);

        ResponsiveIMG(imageView, 73, 62, 19, 19.5);
        ResponsiveIMG(yesIMG, 19,  12, 62, 53);
        ResponsiveIMG(noIMG, 19, 12, 62, 64);

        ResponsiveLBL(questionLbl, 10, 25, 47, 58, 60);
        ResponsiveLBL(report, 5, 26, 41, 54, 50);
        report.setAlignment(Pos.CENTER);
        ResponsiveLBL(yesLbl, 1, 20, 67, 57, 60);
        ResponsiveLBL(noLbl, 1, 20, 67, 68, 60);

        DropShadow(questionLbl, report, yesLbl, noLbl, passField, newNameField);

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

    //reject change username -> back
    public void No(){
        PlayEffect(BUTTON_EFFECT);
        AcSeCon.close();
    }

    //connect to server if pass is correct -> and change username
    //send username and password to server and get:
    //-2 -> if username is valid
    //-1 -> if username and password are not match
    // 1 -> if username and password match
    public void Yes(){
        PlayEffect(BUTTON_EFFECT);
        try {
            if (newNameField.getText().trim().isEmpty()){
                report.setText("New username field should not be empty!");
                return;
            }
            else if (newNameField.getText().equals(USERNAME)){
                report.setText("New username matches Current one!");
                return;
            }
            Socket socket = new Socket(HOST, ACCOUNT_SETTING);
            DataOutputStream to = new DataOutputStream(socket.getOutputStream());
            DataInputStream from = new DataInputStream(socket.getInputStream());

            to.writeInt(CHANGE_USERNAME);
            to.writeUTF(Main.USERNAME);
            to.writeUTF(passField.getText().trim());
            to.writeUTF(newNameField.getText().trim());
            System.out.println(newNameField.getText().trim());

            int result = from.readInt();
            System.out.println(result);
            if (result == NOT_MATCH){
                passField.setText("");
                report.setText("Wrong password!");

            } else if (result == VALID_USERNAME){
                passField.setText("");
                newNameField.setText("");
                report.setText("New Username is Valid!");

            } else if (result == SUCCESSFUL){
                USERNAME = newNameField.getText().trim();
                stage.setTitle("Erienne "+USERNAME);
                Main.mainScene.setRoot(new Pane());
                openFxml(MENU_PLAYER, SignInFXMLPATH);
            }

        } catch (Exception e) {
            openFxml(OptionFXMLPATH);
            e.printStackTrace();
        }
    }
}
