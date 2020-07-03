package LogIn.SignIn;

import Main.Main;
import Notification.Connection;
import animatefx.animation.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.ref.PhantomReference;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import static Constants.MediaPlayers.*;
import static Constants.MediaPlayers.BUTTON_EFFECT;
import static Constants.Notifications.SIGN_IN;
import static Constants.Report.*;
import static Constants.Responsive.*;
import static Constants.Paths.*;
import static Constants.Ports.*;
import static LogIn.HandleFields.HandleEnterSignIn;
import static LogIn.HandleFields.HandlePass;
import static Main.Main.*;

public class SignInCon implements Initializable {

    @FXML private AnchorPane anchorPane;
    @FXML private ImageView imageView;

    @FXML private Label nameLbl;
    @FXML private Label passLbl;
    @FXML private Label goSignUp;
    @FXML private Label goRetrievalPass;
    @FXML private Label report;

    @FXML private TextField nameField;
    @FXML private TextField passField;

    @FXML private Button enter;
    private Parent parent;

    private DataInputStream from;
    private DataOutputStream to;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Make Contacts Responsive
        ResponsivePane(anchorPane, 100, 100);
        ResponsiveIMG(imageView, 75, 8, 32);

        ResponsiveLBL(nameLbl, 5, 5, 36, 49, 50);
        ResponsiveLBL(passLbl, 5, 7, 44, 46.4, 50);
        ResponsiveLBL(goSignUp, 5, 8, 51, 53, 45);
        ResponsiveLBL(goRetrievalPass, 5, 14, 56, 50, 45);
        ResponsiveLBL(report, 8, 17, 61, 47, 40);
        report.setAlignment(Pos.CENTER);

        ResponsiveTXF(nameField, 6, 9, 38, 53, 30);
        ResponsiveTXF(passField, 6, 9, 46, 53, 30);

        ResponsiveBtn(enter, 1, 6.8, 73, 51, 30);

        InnerShadow(nameLbl, passLbl, goSignUp, goRetrievalPass, report, enter);

        //If enter Is Active, Show A Animation
        enter.disableProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue)new Tada(enter).play();
        });

        passField.textProperty().addListener((observable, oldValue, newValue) -> {
            HandlePass(passField, report, enter);
        });
    }

    //To Get Sure That None Of nameFieldUp And passFieldUp Is Empty
    @FXML public void handle(){
        HandleEnterSignIn(nameField, passField, enter, report);
        if (report.getText().equals("Invalid Username!") || report.getText().equals("Username and password are not match!"))
            report.setText("");
    }

    public int result = 0;
    //Connect to server and open a loading
    @FXML public void SignIn(){
        PlayEffect(BUTTON_EFFECT);
        try {
            Socket socket = new Socket(HOST, LOGIN);
            from = new DataInputStream(socket.getInputStream());
            to = new DataOutputStream(socket.getOutputStream());
            startLoad();

            Timeline load = new Timeline(new KeyFrame(Duration.millis(3000), ae -> result = communicateToServer()));
            load.setOnFinished(event -> {
                Platform.runLater(() -> {

                    ZoomOut zoomOut = new ZoomOut(parent);
                    zoomOut.setOnFinished(event1 -> finishLoad());
                    zoomOut.play();
                });
            });
            load.play();

        }catch (Exception e){
            //stay on page
            e.printStackTrace();
        }
    }

    //Open loading
    public void startLoad(){
        try {
            parent = FXMLLoader.load(Main.class.getResource(LoadProgressFXMLPATH));
            anchorPane.getChildren().add(parent);
            Main.stage.setHeight(Main.stage.getHeight()+0.1);
            new ZoomIn(parent).play();
        } catch (IOException e) {
            //stay on page
            e.printStackTrace();
        }
    }

    //Close loading and get result from server
    public void finishLoad(){
        anchorPane.getChildren().remove(parent);
        Platform.runLater(() -> {
            if (result == INVALID_USERNAME){
                report.setText("Invalid Username!");
                nameField.requestFocus();
            }
            else if (result == NOT_MATCH){
                report.setText("Username and password are not match!");
                nameField.requestFocus();
                passField.requestFocus();
            }
            else if (result == SUCCESSFUL){
                Main.USERNAME = nameField.getText();
                Main.stage.setTitle(Main.stage.getTitle()+" "+USERNAME);
                try {
                    to.writeInt(1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                new Thread(new Connection()).start();
                openFxml(MenuFXMLPATH);
            }
        });
        enter.setDisable(true);
    }

    //Send information to server
    private int communicateToServer(){
        String username = nameField.getText().trim();
        String pass = passField.getText().trim();

        int value = 0;
        try {
            to.writeInt(SIGN_IN);
            to.writeUTF(username);
            to.writeUTF(pass);

            value = from.readInt();

        } catch (IOException e) {
            e.printStackTrace();
            //stay on page
        }
        return value;
    }

    @FXML private void goSignUp(){
        PlayEffect(BUTTON_EFFECT);
        openFxml(MENU_PLAYER, SignUpFXMLPATH);
    }

    @FXML private void goRetrievalPass(){
        PlayEffect(BUTTON_EFFECT);
        openFxml(MENU_MEDIA_PLAYER, RetrievalPassFXMLPATH);
    }
}
