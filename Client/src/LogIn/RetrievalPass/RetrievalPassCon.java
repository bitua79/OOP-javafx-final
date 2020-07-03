package LogIn.RetrievalPass;

import Main.Main;
import animatefx.animation.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import static Constants.Notifications.RETRIEVAL_PASS;
import static Constants.Paths.*;
import static Constants.Ports.*;
import static Constants.Report.*;
import static LogIn.HandleFields.HandleEnterPass;
import static Constants.Responsive.*;
import static Main.Main.InnerShadow;
import static Main.Main.openFxml;

public class RetrievalPassCon implements Initializable {

    @FXML private AnchorPane anchorPane;
    @FXML private ImageView imageView;

    @FXML private Label nameLbl;
    @FXML private Label questionLbl;
    @FXML private Label answerLbl;
    @FXML private Label report;

    @FXML private TextField nameField;
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
        ResponsiveIMG(imageView, 75, 9, 32);

        ResponsiveLBL(nameLbl, 5, 5, 33, 35, 50);
        ResponsiveLBL(questionLbl, 5, 7, 42, 33, 50);
        ResponsiveLBL(answerLbl, 52, 6, 6.5, 34, 50);
        ResponsiveLBL(report, 7, 17, 58, 36, 50);

        ResponsiveTXF(nameField, 5, 13, 36, 40, 30);
        ResponsiveChoiceBox(questionBox, 6, 13, 44, 40, 30);
        ResponsiveTXF(answerField, 5, 13, 52, 40, 30);

        ResponsiveBtn(back, 1, 7, 13, 33, 30);
        ResponsiveBtn(enter, 1, 10, 73, 41, 40);

        InnerShadow(nameLbl, questionLbl, answerLbl, report, enter, back);

        //If enter Is Active, Show A Animation
        enter.disableProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue)new Tada(enter).play();
        });

    }

    @FXML public void handle(){
        HandleEnterPass(nameField, answerField, enter, report);
        if (report.getText().equals("Invalid Username!") || report.getText().equals("Username and question or answer are not match!"))
            report.setText("");
    }

    public int result = 0;
    //Connect to server and open a loading
    @FXML private void FindPass(){
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
            e.printStackTrace();
            //stay on page
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
            e.printStackTrace();
            //stay on page
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
                report.setText("Username and question or answer are not match!");
                answerField.requestFocus();
            }
            else if (result == SUCCESSFUL){
                try {
                    report.setText("Your Password is:\n  "+from.readUTF());
                } catch (IOException e) {
                    e.printStackTrace();
                    //try again
                    communicateToServer();
                    finishLoad();
                }
            }
        });
        enter.setDisable(true);
    }

    //Send information to server
    private int communicateToServer(){
        String username = nameField.getText().trim();
        String question = questionBox.getValue().toString().trim();
        String answer = answerField.getText().trim();

        int value = 0;
        try {
            to.writeInt(RETRIEVAL_PASS);
            to.writeUTF(username);
            to.writeUTF(question);
            to.writeUTF(answer);
            value = from.readInt();

        } catch (IOException e) {
            e.printStackTrace();
            //stay on page
        }
        return value;
    }

    @FXML private void backToSignIn(){
        PlayEffect(BUTTON_EFFECT);
        openFxml(MENU_MEDIA_PLAYER, SignInFXMLPATH);
    }
}
