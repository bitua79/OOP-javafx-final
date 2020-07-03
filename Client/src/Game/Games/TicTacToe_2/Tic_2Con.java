package Game.Games.TicTacToe_2;

import Game.Games.TicTacToe_1.Tic_1Con;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.ImageCursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Rectangle;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import static Constants.MediaPlayers.*;
import static Constants.Paths.*;
import static Constants.Ports.*;
import static Constants.Responsive.*;
import static Game.Games.Common.GamesCommon.compose;
import static Game.Games.Common.GamesCommon.createTransition;
import static Main.Main.*;

public class Tic_2Con {

    @FXML private AnchorPane mainAnchorPane;
    @FXML private MediaView mediaView;
    @FXML private Label report;

    @FXML private AnchorPane anchorPane;
    @FXML private Rectangle backRec;
    @FXML private Rectangle rectangle;
    @FXML private VBox vBox;

    @FXML private TextArea typeArea;
    @FXML private TextArea showArea;
    @FXML private Button send;

    @FXML private Button back;
    @FXML private Button exit;
    @FXML private Button mute;
    @FXML private Button menu;
    @FXML private Button options;
    @FXML private ImageView optionsIm;

    private String friendName;
    private double percent;
    private double spacing = stage.getHeight() * 0.03;
    private String[][] emojiStrings = {{"\uD83D\uDCA9", "\uD83D\uDE01", "\uD83D\uDE07", "\uD83D\uDE30", "\uD83D\uDE0E"},
            {"\uD83D\uDE08", "\uD83D\uDE11", "\uD83D\uDE41", "\uD83D\uDE2D", "\uD83D\uDE2C"}};

    public Tic_2Con(String friendName) {
        this.friendName = friendName;
    }

    public void initialize() {

        //Make Contacts Responsive
        ResponsivePane(mainAnchorPane, 100 ,100, 0, 0);
        anchorPane.setCursor(new ImageCursor(CURSOR_GAME));
        ResponsiveMV(mediaView, 100 ,100 ,0, 0);
        ResponsiveLBL(report, 8, 50, 70, 18, 40);

        ResponsiveTxtArea(typeArea, 8, 55, 83, 10, 25);
        ResponsiveBtn(send, 1, 6, 85, 2, 30);
        mediaView.setMediaPlayer(TIC_PLAYER);
        PlayMedia(TIC_PLAYER);

        FadeIn(GAME_MEDIA_PLAYER, MENU_MEDIA_PLAYER);

        ResponsiveBtn(options, 7, 8, 0, 0, 30);
        ResponsiveIMG(optionsIm, 7, 8, 0, 90);

        ResponsiveBtn(back, 1, 6, 3, 62, 30);
        back.setOnAction(event -> {
            PlayEffect(BUTTON_EFFECT);
            openFxml(GameListFXMLPATH);
        });
        ResponsiveBtn(mute, 1, 6, 0, 0, 30);
        mute.setOnAction(event -> {
            PlayEffect(BUTTON_EFFECT);
            Tic_1Con.setMute(mute);
        });
        ResponsiveBtn(menu, 1, 6, 0, 0, 30);
        menu.setOnAction(event -> {
            PlayEffect(BUTTON_EFFECT);
            backToMenu();
        });
        ResponsiveBtn(exit, 1, 6, 0, 0, 30);
        exit.setOnAction(event -> {
            PlayEffect(BUTTON_EFFECT);
            quit();
        });

        stage.heightProperty().addListener((observable, oldValue, newValue) -> {
            percent = stage.getHeight() / 1080;
            options.setStyle("-fx-background-color: linear-gradient(to right, thistle,  #BAA19A); -fx-background-radius: 20");

            TranslateTransition[] transitions = new TranslateTransition[3];
            transitions[0] = createTransition(mute, percent * -15 , percent * -1 );
            transitions[1] = createTransition(menu, percent * -11 , percent * -13);
            transitions[2] = createTransition(exit, percent * -2 , percent * -22);

            options.setOnAction(event -> compose(transitions));
            spacing = 0.03 * stage.getHeight();
        });

        ResponsivePane(anchorPane, 97, 27, 2, 70);
        ResponsiveShape(backRec, 95, 48, 0, 0);

        ResponsiveTxtArea(showArea, 70, 25, 2, 1, 30);
        ResponsivePane(vBox, 15, 23, 79, 2);
        ResponsiveShape(rectangle, 17, 45, 77, 1);

        int counter = 0;
        for (int i=0; i<2; i++){
            HBox hBox = new HBox();
            for (int j=0; j<5; j++){
                counter++;
                ImageView imageView = new ImageView(new Image(PHOTO+"/Emoji/Game/"+counter+".png"));
                ResponsiveIMG(imageView, 5, 3, 3, 3);
                imageView.setOpacity(0.8);

                String emoji = emojiStrings[i][j];
                imageView.setOnMouseClicked(event -> {
                    PlayEffect(BUTTON_EFFECT);
                    typeArea.appendText(emoji);
                });
                hBox.getChildren().add(imageView);
            }
            stage.heightProperty().addListener(e -> hBox.setSpacing(spacing));
            vBox.getChildren().add(hBox);
        }
        stage.heightProperty().addListener(e -> vBox.setSpacing(spacing));



        InnerShadow(back, mute, menu, exit, typeArea, showArea, options, optionsIm, vBox, report);
        DropShadow(send);

        startGame();
        startChat();
    }

    public void startGame(){
        try {
            Socket gameSocket = new Socket(HOST, PLAY_TIC_TAC_TOE_2);

            Tic_Tac_Toe_Game_2 gameDisplay = new Tic_Tac_Toe_Game_2(gameSocket, report, USERNAME, friendName);
            gameDisplay.setCursor(new ImageCursor(CURSOR_GAME));
            ResponsivePane(gameDisplay, 1, 1, 15, 32);
            mainAnchorPane.getChildren().add(gameDisplay);
            isPlaying = true;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DataOutputStream ChatTo;
    public void startChat(){
        try {
            Socket socket = new Socket(HOST, CHAT_TIC_TAC_TOE_2);
            ChatTo = new DataOutputStream(socket.getOutputStream());
            DataInputStream from = new DataInputStream(socket.getInputStream());

            send.setOnAction(event -> {
                PlayEffect(BUTTON_EFFECT);
                sendMessage(ChatTo);
            });
            ChatTo.writeUTF(USERNAME);
            new Thread(new ChatHandle(from, showArea)).start();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(DataOutputStream to){
        try {
            to.writeUTF(typeArea.getText().trim());

            typeArea.setText("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stopChat(){
        try {
            ChatTo.writeUTF(STOP_CHAT_SOCKET);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ChatHandle implements Runnable{

    private DataInputStream from;
    private TextArea textArea;
    public ChatHandle(DataInputStream from, TextArea textArea) {
        this.from = from;
        this.textArea = textArea;
    }

    @Override
    public void run() {
        try {
            while (true){
                String newMessage = from.readUTF();
                Platform.runLater(()->{
                    textArea.appendText(newMessage+"\n");
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
