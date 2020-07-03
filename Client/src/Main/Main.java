package Main;

import Constants.Paths;
import Constants.Responsive;
import Constants.Ports;
import animatefx.animation.FadeIn;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static Constants.MediaPlayers.PlayMainMU;
import static Constants.MediaPlayers.PlayMedia;
import static Constants.Ports.GET_OFFLINE;
import static Constants.Ports.HOST;

public class Main extends Application implements Paths {

    public static Scene mainScene;
    public static Stage stage;

    public static AnchorPane parent;
    private static Parent back;
    private static AnchorPane mixPane;
    private static MediaView mediaView;

    public static String USERNAME = "bita";
    public static boolean isPlaying = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        AnchorPane anchorPane = new AnchorPane();
        mainScene = new Scene(anchorPane, 1350, 750);

        Font.loadFont("file:src/Resources/Font/UECHIGOT.TTF", 20);
        Font.loadFont("file:src/Resources/Font/Gabriola.ttf", 20);

        PlayMainMU();

        openFxml(LoadingFXMLPATH);

        primaryStage.heightProperty().addListener((observable, oldValue, newValue) -> {
            primaryStage.setWidth((newValue.doubleValue() * 135.0) / 75);
        });

        primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> {
            primaryStage.setHeight((newValue.doubleValue() * 75.0) / 135);
        });

        stage.getIcons().add(new Image(PHOTO +"ICON2.png"));
        stage.setOnCloseRequest(event -> quit());
        primaryStage.setFullScreen(true);
        primaryStage.setScene(mainScene);
        stage.setTitle("Erienne");
        primaryStage.show();
    }

    public static void openFxml(String URL){

        new Thread(() -> {
            try {
                parent = FXMLLoader.load(Main.class.getResource(URL));
                openFxml(parent);

            } catch (IOException e) {
                e.printStackTrace();
                openFxml(MenuFXMLPATH);
            }
        }).start();
    }

    public static void openFxml(Parent parent){
        new Thread(() -> {
            Platform.runLater(() -> {
                Main.mainScene.setRoot(new Pane());
                mainScene.setRoot(parent);
                stage.setHeight(Main.stage.getHeight()+0.000000001);
            });

            FadeIn fadeIn = new FadeIn(parent);
            fadeIn.setSpeed(0.4);
            fadeIn.play();
        }).start();
    }

    public static void openFxml(String backURL, String frontURL){
        new Thread(() -> {
            try {
                back = FXMLLoader.load(Main.class.getResource(backURL));
                parent = FXMLLoader.load(Main.class.getResource(frontURL));

                AnchorPane backPane = new AnchorPane(back);
                AnchorPane frontPane = new AnchorPane(parent);

                backPane.setOpacity(0.5);
                frontPane.setOpacity(0.8);
                mixPane = new AnchorPane(backPane, frontPane);
                openFxml(mixPane);

            } catch (IOException e) {
                openFxml(MenuFXMLPATH);
            }
        }).start();
    }

    public static void openFxml(Parent back, Parent front){
        new Thread(() -> {
                AnchorPane backPane = new AnchorPane(back);
                AnchorPane frontPane = new AnchorPane(front);

                backPane.setOpacity(0.5);
                frontPane.setOpacity(0.8);
                mixPane = new AnchorPane(backPane, frontPane);
                openFxml(mixPane);

        }).start();
    }


    public static void openFxml(MediaPlayer mediaPlayer, String URL){
        new Thread(() -> {
            try {
                parent = FXMLLoader.load(Main.class.getResource(URL));

                Platform.runLater(() -> {
                    mediaView = new MediaView(mediaPlayer);
                    back = new AnchorPane(mediaView);

                    Responsive.ResponsiveMV(mediaView, 101.3, 101, 0, 0);
                    mediaView.setOpacity(0.5);

                    mixPane = new AnchorPane(mediaView, parent);
                    openFxml(mixPane);
                    PlayMedia(mediaPlayer);
                    PlayMainMU();
                });

            } catch (IOException e) {
                openFxml(MenuFXMLPATH);
            }
        }).start();
    }

    public static void InnerShadow(Node... node){
        for (Node value : node) {
            value.setEffect(new InnerShadow(BlurType.THREE_PASS_BOX, Color.rgb(0, 0, 0, 0.7), 6, 0.0, 0, 2));
        }
    }

    public static void DropShadow(Node... node){
        for (Node value : node) {
            value.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, Color.rgb(0, 0, 0, 0.7), 6, 0.0, 0, 2));
        }
    }

    public static void backToMenu(){
        openFxml(MenuFXMLPATH);
    }

    public static void quit(){

        try {
            Socket socket = new Socket(HOST, GET_OFFLINE);
            DataOutputStream to = new DataOutputStream(socket.getOutputStream());

            to.writeUTF(USERNAME);

            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
            openFxml(MenuFXMLPATH);
        }

    }
}
