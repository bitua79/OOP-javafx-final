package Options.Friends;

import AutoScrollLabel.AutoScrollLabel;
import Main.Main;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static Constants.Notifications.REMOVE_FRIEND;
import static Constants.Ports.FRIEND_SETTING;
import static Constants.Ports.HOST;
import static Constants.Report.*;
import static Constants.Responsive.ResponsiveBtn;
import static Constants.Responsive.ResponsiveLBL;

public class Friend extends HBox {
    private AutoScrollLabel username;
    private Label report;
    private Button remove;
    private Button chat;

    public Friend(String username, Label report) {

        this.username = new AutoScrollLabel(username, 500, 15);
        this.username.moveText();

        this.report = report;
        this.remove = new Button("Remove");
        this.remove.setOnAction(event -> delete());

        this.chat = new Button("chat");

        //Make Contacts Responsive
        ResponsiveLBL(this.username, 1, 12, 50, 50, 40);
        ResponsiveBtn(remove, 1, 7, 50, 60, 30);
        ResponsiveBtn(chat, 1, 5, 10, 60, 30);
        Main.stage.setHeight(Main.stage.getHeight()+0.00000000001);

        this.getChildren().addAll(this.username, remove, chat);
        this.setSpacing(10);

        Main.DropShadow(this.username, remove, chat);
    }

    public Friend(String username) {
        this.username = new AutoScrollLabel(username, 500, 15);
    }

    //send username and friendUsername to server and get:
    //-4 -> if friend doesn't exist
    //-5 -> if friend doesn't exist in the list
    // 1 -> if friend removed successfully
    public void delete(){

        try {
            Socket socket = new Socket(HOST, FRIEND_SETTING);

            DataOutputStream to = new DataOutputStream(socket.getOutputStream());
            DataInputStream from = new DataInputStream(socket.getInputStream());

            to.writeInt(REMOVE_FRIEND);
            to.writeUTF(Main.USERNAME);
            to.writeUTF(this.username.getText());

            int result = from.readInt();
            if (result == SUCCESSFUL){
                VBox vBox = (VBox) remove.getParent().getParent();
                vBox.getChildren().remove(this);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Label getUsername() {
        return username;
    }

    public Label getReport() {
        return report;
    }

    public Button getRemove() {
        return remove;
    }

    public Button getChat() {
        return chat;
    }
}
