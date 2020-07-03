package Game.Games.SnakeLadder_1;

import animatefx.animation.BounceIn;
import javafx.animation.KeyFrame;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import javafx.util.Duration;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static Constants.Notifications.*;
import static Constants.Paths.MenuFXMLPATH;
import static Constants.Paths.WinsFXMLPATH;
import static Constants.Ports.GAME_RESULT;
import static Constants.Ports.HOST;
import static Main.Main.*;
import static Main.Main.mainScene;

public class SnakeLadder_Game_1 extends Pane {

    public static String first_player = "Player1";
    private static int player1 = 1;
    private Cell lastCell1;

    public static String second_player = "Player2";
    private static int player2 = 1;
    private Cell lastCell2 = new Cell(11, 11);

    private Board board;
    private Label label;
    private Button button;
    private DataOutputStream to;

    public SnakeLadder_Game_1(Label label, Button button) {
        board = new Board();
        this.getChildren().add(board);

        this.button = button;
        button.setOnAction(event -> turn());
        this.label = label;
        lastCell1 = board.getCells()[9][0];
        lastCell1.shoot(first_player);
        lastCell2 = board.getCells()[9][0];

        try {
            Socket socket = new Socket(HOST, GAME_RESULT);
            to = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void snakeOrLadder(int end, String player){
        if (player.equals(first_player)){
            if (end< player1)label.setText("player1 faced to a snake and fall "+(player1-end)+" blocs.");
            if (end> player2)label.setText("player1 faced to a ladder and raise "+(end-player1)+" blocs.");

            player1 = end;

        }else if (player.equals(second_player)){
            if (end< player2)label.setText("player2 faced to a snake and fall "+(player2-end)+" blocs.");
            if (end> player2)label.setText("player2 faced to a ladder and raise "+(end-player2)+" blocs.");

            player2 = end;
        }
    }

    public void move(String player){

        int move = 6;
        String movement = "";
        while (move==6){
            move = (int) (Math.random()*6 + 1);
            movement += move+"  ";
            if (move == 6)movement += " +  ";

            label.setText("Tass is: "+movement);
            if (player.equals(first_player) && player1+move <= 100){
                player1 += move;
                //check snake & ladder for player1
                if (board.getTransmissions().containsKey(player1))
                    snakeOrLadder((Integer) board.getTransmissions().get(player1), first_player);
                }
            else if (player.equals(second_player) && player2+move <= 100){
                player2 += move;
                //check snake & ladder for player2
                if (board.getTransmissions().containsKey(player2))
                    snakeOrLadder((Integer) board.getTransmissions().get(player2), second_player);
                }
            }
    }

    public void turn() {
        //end game
        if (player1==100 || player2 ==100)return;

        button.setDisable(true);
        lastCell1.shoot(first_player);
        move(first_player);
        int[] place1 = situation(player1);

        TranslateTransition transition1 = new TranslateTransition();
        setTranslation(transition1, place1, lastCell1);
        transition1.setOnFinished(event -> {
            lastCell1 = board.getCells()[place1[0]][place1[1]];
            updateBoard();

            if (player1 == 100){
                win();
                return;
            }

            move(second_player);
            int[] place2 = situation(player2);

            TranslateTransition transition2 = new TranslateTransition();
            setTranslation(transition2, place2, lastCell2);
            transition2.setOnFinished(e -> {
                lastCell2 = board.getCells()[place2[0]][place2[1]];
                updateBoard();

                if (player2==100){
                    lose();
                }
                button.setDisable(false);
            });
        });
    }

    public void setTranslation(TranslateTransition translation, int[] place, Cell lastCell){
        translation.setNode(lastCell);
        translation.setDuration(Duration.millis(2000));

        double x = place[1] - lastCell.column;
        double y = place[0] - lastCell.row;

        translation.setByX(x * (lastCell.getWidth()+1));
        translation.setByY(y * (lastCell.getHeight()+1));
        translation.play();
    }

    public void updateBoard(){
        board.arrangeBoard(lastCell1, lastCell2);
        lastCell1 = board.getCells()[lastCell1.row][lastCell1.column];
        lastCell2 = board.getCells()[lastCell2.row][lastCell2.column];
    }

    public int[] situation(int situation){
        int[] place = new int[2];

        for (int i=0; i<10; i++){
            for (int j=0; j<10; j++){
                if (board.getCellIndexes()[i][j] == situation){
                    place[0] = i;
                    place[1] = j;
                }
            }
        }
        return place;
    }

    public void win(){
        Platform.runLater(() -> {
            label.setText("You win! Click anywhere to exit!");
            button.setDisable(true);
            mainScene.setOnMouseClicked(mouseEvent -> {
                try {
                    to.writeInt(ADD_RESULT);
                    to.writeUTF("snake1");
                    to.writeUTF(USERNAME);
                    to.writeInt(WINPlayer1);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                openFxml(MenuFXMLPATH, WinsFXMLPATH);
                mainScene.setOnMouseClicked(null);
            });
        });
    }

    public void lose(){
        Platform.runLater(() -> {
            label.setText("You lose! Click anywhere to exit!");
            button.setDisable(true);
            mainScene.setOnMouseClicked(mouseEvent -> {
                try {
                    to.writeInt(ADD_RESULT);
                    to.writeUTF("snake1");
                    to.writeUTF(USERNAME);
                    to.writeInt(LOSEPlayer1);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                openFxml(MenuFXMLPATH, LoseFXMLPATH);
                mainScene.setOnMouseClicked(null);
            });
        });
    }
}
