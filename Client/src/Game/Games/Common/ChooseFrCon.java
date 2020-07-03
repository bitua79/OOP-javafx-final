package Game.Games.Common;

import AutoScrollLabel.AutoScrollLabel;
import Game.ChooseMod.ChooseModCon;
import Game.Games.TicTacToe_2.Tic_2Con;
import Main.Main;
import com.sun.corba.se.impl.orb.PrefixParserAction;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static Constants.MediaPlayers.*;
import static Constants.Notifications.SHOW_ALL_FRIENDS;
import static Constants.Notifications.SHOW_ONLINE_FRIENDS;
import static Constants.Paths.GameListFXMLPATH;
import static Constants.Ports.FRIEND_SETTING;
import static Constants.Ports.HOST;
import static Constants.Responsive.*;
import static Main.Main.*;

public class ChooseFrCon implements Initializable {

    @FXML private AnchorPane anchorPane;
    @FXML private ImageView backGif;
    @FXML private ImageView imageView;

    @FXML private ScrollPane scrollPane;
    @FXML private VBox vBox;
    @FXML private Rectangle rectangle;
    @FXML private Label label;
    @FXML private Button back;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Make Contacts Responsive
        ResponsivePane(anchorPane, 100 , 0, 0, 0);
        ResponsiveIMG(backGif, 100, 100, 0, 0);
        ResponsiveIMG(imageView, 72, 41, 16, 29);

        ResponsivePane(scrollPane, 48, 32, 28, 34);
        ResponsivePane(vBox, 50, 32, 28, 34);
        ResponsiveShape(rectangle, 48, 60, 28, 33);
        ResponsiveLBL(label, 6, 23, 19, 38, 50);
        ResponsiveBtn(back, 4, 6, 79, 32, 30);
        back.setOnAction(event -> {
            PlayEffect(BUTTON_EFFECT);
            openFxml(GameListFXMLPATH);
        });

        InnerShadow(label, scrollPane);
        DropShadow(rectangle, back);

        showFriends();
    }

    public void showFriends() {
        try {
            Socket socket = new Socket(HOST, FRIEND_SETTING);
            DataOutputStream to = new DataOutputStream(socket.getOutputStream());
            DataInputStream from = new DataInputStream(socket.getInputStream());

            to.writeInt(SHOW_ONLINE_FRIENDS);
            to.writeUTF(USERNAME);

            int size = from.readInt();
            if (size == 0){
                label.setText("No Online Friend!");
            }

            for (int i=0; i<size; i++){
                String name = from.readUTF();
                addFriendToVbx(name);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addFriendToVbx(String name){

        AutoScrollLabel label= new AutoScrollLabel(name, 500, 30);
        label.setPadding(new Insets(10, 20, 10, 10));
        if (name.length() > 20)label.moveText();

        Button choose = new Button("Choose");
        choose.setOnAction(event -> chooseFriend(name));

        ResponsiveLBL(label, 1, 20, 50, 50, 40);
        ResponsiveBtn(choose, 1, 7, 50, 60, 30);

        HBox hBox = new HBox(label, choose);
        hBox.setPadding(new Insets(20, 10, 10, 10));
        vBox.getChildren().add(hBox);
    }

    public void chooseFriend(String name){
        PlayEffect(BUTTON_EFFECT);
        FXMLLoader loader = new FXMLLoader((getClass().getResource(Tic_2displayFXMLPATH)));
        Tic_2Con con = new Tic_2Con(name);
        loader.setController(con);

        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        openFxml(parent);
    }
}
