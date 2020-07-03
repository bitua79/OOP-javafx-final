package LogIn.SignUp;

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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import static Constants.MediaPlayers.*;
import static Constants.MediaPlayers.BUTTON_EFFECT;
import static Constants.Notifications.SIGN_UP;
import static Constants.Paths.*;
import static Constants.Ports.*;
import static Constants.Report.*;
import static Constants.Responsive.*;
import static LogIn.HandleFields.*;
import static Main.Main.*;

public class SignUpCon implements Initializable{

    @FXML private AnchorPane anchorPane;
    @FXML private ImageView imageView;

    @FXML private Label nameLbl;
    @FXML private Label passLbl;
    @FXML private Label emailLbl;
    @FXML private Label questionLbl;
    @FXML private Label answerLbl;
    @FXML private Label report;

    @FXML private TextField nameField;
    @FXML private TextField passField;
    @FXML private TextField emailField;
    @FXML private TextField answerField;
    @FXML private ChoiceBox questionBox;

    @FXML private Button enter;
    @FXML private Button back;
    private Parent parent;

    private DataInputStream from;
    private DataOutputStream to;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Make Contacts Responsive
        ResponsivePane(anchorPane, 100, 100);
        ResponsiveIMG(imageView, 76, 10, 32);

        ResponsiveLBL(nameLbl, 5, 6, 26, 34, 40);
        ResponsiveLBL(passLbl, 5, 10, 33, 32.5, 40);
        ResponsiveLBL(emailLbl, 5, 5, 41, 33, 40);
        ResponsiveLBL(questionLbl, 5, 8, 47, 33, 40);
        ResponsiveLBL(answerLbl, 5, 7, 54, 33, 40);
        ResponsiveLBL(report, 8, 17, 61, 36, 40);
        report.setAlignment(Pos.CENTER);

        ResponsiveTXF(nameField, 4, 12.5, 28, 38, 30);
        ResponsiveTXF(passField, 4, 12.5,35, 38, 30);
        ResponsiveTXF(emailField, 3, 12.5,42, 38, 30);
        ResponsiveChoiceBox(questionBox, 5, 12.5,49, 38, 30);
        ResponsiveTXF(answerField, 5, 12.5,56, 38, 30);

        ResponsiveBtn(back, 1, 6, 12, 33, 30);
        ResponsiveBtn(enter, 1, 8.5, 74, 43, 30);

        InnerShadow(nameLbl, passLbl, emailLbl, questionLbl, answerLbl, report, enter, back);

        //If enter Is Active, Show A Animation
        enter.disableProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue)new Tada(enter).play();
        });

        emailField.textProperty().addListener((observable, oldValue, newValue) -> {
            HandleEmail(emailField, report, enter);
        });

        passField.textProperty().addListener((observable, oldValue, newValue) -> {
            HandlePass(passField, report, enter);
        });

        answerField.textProperty().addListener((observable, oldValue, newValue) -> {
            HandleAnswer(answerField, report, enter);
        });

        report.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()){

                if (!passField.getId().equals("Wrong-text-field"))HandlePass(passField, report, enter);
                if (!emailField.getId().equals("Wrong-text-field"))HandleEmail(emailField, report, enter);
                if (!answerField.getId().equals("Wrong-text-field"))HandleAnswer(answerField, report, enter);

            }else {
                enter.setDisable(true);
            }
        });
    }

    //To Get Sure That None Of sign up page fields Is Empty
    @FXML private void handleEnter() {
        HandleEnterSignUP(nameField, passField, emailField, answerField, enter, report);
        if (report.getText().equals("Valid Username!") || report.getText().equals("Valid Email!"))
            report.setText("");
    }

    public int result = 0;
    //Connect to server and open a loading
    @FXML private void SignUp(){
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
            if (result == VALID_USERNAME){
                report.setText("Valid Username!");
                nameField.requestFocus();
            }
            else if (result == VALID_EMAIL){
                report.setText("Valid Email!");
                emailField.requestFocus();
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
        String email = emailField.getText().trim();
        String question = questionBox.getValue().toString().trim();
        String answer = answerField.getText().trim();

        int value = 0;
        try {
            to.writeInt(SIGN_UP);
            to.writeUTF(username);
            to.writeUTF(pass);
            to.writeUTF(email);
            to.writeUTF(question);
            to.writeUTF(answer);
            value = from.readInt();

        } catch (IOException e) {
            //stay on page
            e.printStackTrace();
        }
        return value;
    }

    @FXML private void backToSignIn(){
        PlayEffect(BUTTON_EFFECT);
        openFxml(MenuFXMLPATH, SignInFXMLPATH);
    }
}
