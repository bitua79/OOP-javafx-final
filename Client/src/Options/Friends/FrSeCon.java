package Options.Friends;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import static Constants.MediaPlayers.*;
import static Constants.Notifications.*;
import static Constants.Ports.FRIEND_SETTING;
import static Constants.Ports.HOST;
import static Constants.Report.*;
import static Constants.Responsive.*;
import static Main.Main.*;

public class FrSeCon implements Initializable {

    @FXML private AnchorPane anchorPane;
    @FXML private VBox vBox;

    @FXML private ImageView imageView;
    @FXML private TabPane tabPane;
    @FXML private ScrollPane scrollPane;

    @FXML private Label title;
    @FXML private Label report;

    @FXML private Button add;
    @FXML private TextField usernameField;

    public static Parent parent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Make Contacts Responsive
        ResponsivePane(anchorPane, 100, 100);
        ResponsivePane(vBox, 26, 24, 30, 4);
        ResponsivePane(tabPane, 70.5, 32.5, 24, 50.5, 20, 30);
        ResponsivePane(scrollPane, 26, 27, 30, 4);
        ResponsiveIMG(imageView, 72.4, 34.4, 19, 50);

        ResponsiveLBL(title, 2, 26, 13, 3, 38);
        ResponsiveTXF(usernameField, 1, 21, 18, 3, 30);
        ResponsiveBtn(add, 1, 5, 18, 25, 30);
        ResponsiveLBL(report, 2, 25, 25, 4, 35);

        DropShadow(title, report, usernameField, add);
        showFriend();

        usernameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) usernameField.setId("Wrong-text-field");
            else usernameField.setId("");
            report.setText("");
        });

    }

    public void add(){
        PlayEffect(BUTTON_EFFECT);
        try {
            if (usernameField.getText().trim().equals(USERNAME)){
                report.setText("You can't add yourself to your friends!");
                return;
            }

            Socket socket = new Socket(HOST, FRIEND_SETTING);
            DataOutputStream to = new DataOutputStream(socket.getOutputStream());
            DataInputStream from = new DataInputStream(socket.getInputStream());

            to.writeInt(ADD_FRIEND);
            to.writeUTF(USERNAME);
            to.writeUTF(usernameField.getText().trim());

            int result = from.readInt();
            if (result == INVALID_USERNAME){
                report.setText("Username doesn't exist!");
                usernameField.setId("Wrong-text-field");

            }else if (result == VALID_USERNAME){
                report.setText("Username have already added in your friends!");
                usernameField.setId("Wrong-text-field");

            }else if (result == SUCCESSFUL){
                vBox.getChildren().add(new Friend(usernameField.getText().trim(), report));
                usernameField.setText("");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void showFriend(){
        try {
            Socket socket = new Socket(HOST, FRIEND_SETTING);
            DataOutputStream to = new DataOutputStream(socket.getOutputStream());
            DataInputStream from = new DataInputStream(socket.getInputStream());

            to.writeInt(SHOW_ALL_FRIENDS);
            to.writeUTF(USERNAME);

            int size = from.readInt();
            for(int i=0; i<size; i++){
                vBox.getChildren().add(new Friend(from.readUTF(), report));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

