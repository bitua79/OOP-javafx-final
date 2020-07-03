package Notification;

import Game.Games.Common.RequestCon;

import Game.Games.TicTacToe_2.Tic_2Con;
import Main.Main;
import animatefx.animation.FadeIn;
import animatefx.animation.FadeOut;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static Constants.MediaPlayers.*;
import static Constants.Notifications.*;
import static Constants.Paths.RequestFXMLPATH;
import static Constants.Paths.Tic_2displayFXMLPATH;
import static Main.Color.*;
import static Main.Main.*;

class PlayTic_2Req implements Runnable{

    private Socket socket;
    private DataInputStream from;
    private DataOutputStream to;

    public PlayTic_2Req(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {

            from = new DataInputStream(socket.getInputStream());
            to = new DataOutputStream(socket.getOutputStream());

            String firstPlayer = from.readUTF();
            System.out.println(YELLOW_BOLD_BRIGHT+firstPlayer+" wants to play with you!");

            if (isPlaying)to.writeInt(BUSY_IS_PLAYING);
            else {
                openReqPage(firstPlayer);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Parent parent;
    public void openReqPage(String friendName){

        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader((getClass().getResource(RequestFXMLPATH)));
                RequestCon con = new RequestCon(friendName, new Accept(), new Reject());
                loader.setController(con);
                parent = loader.load();

                Main.parent.getChildren().add(parent);
                Main.stage.setHeight(Main.stage.getHeight()+0.00001);
                new FadeIn(parent).play();
                PlayEffect(ALARM_EFFECT);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    class Accept implements Runnable{
        @Override
        public void run() {
            Platform.runLater(() -> {
                try {
                    to.writeInt(ACCEPT);
                    FXMLLoader loader = new FXMLLoader((getClass().getResource(Tic_2displayFXMLPATH)));
                    Tic_2Con con = new Tic_2Con(USERNAME);
                    loader.setController(con);

                    Parent parent = loader.load();
                    openFxml(parent);

                    System.out.println(GREEN_BOLD_BRIGHT+"You accepted the game !");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    class Reject implements Runnable{
        @Override
        public void run() {
            Platform.runLater(() -> {
                try {
                    to.writeInt(REJECT);
                    FadeOut fadeOut = new FadeOut(parent);
                    fadeOut.setOnFinished(event -> {
                        Main.parent.getChildren().remove(parent);
                    });

                    fadeOut.play();
                    System.out.println(RED_BOLD_BRIGHT+"You rejected the game !");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
