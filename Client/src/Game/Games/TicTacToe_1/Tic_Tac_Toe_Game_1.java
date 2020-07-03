package Game.Games.TicTacToe_1;

import Main.Main;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.io.DataOutputStream;
import java.io.IOException;

import static Constants.Notifications.*;
import static Constants.Paths.*;
import static Main.Main.openFxml;

public class Tic_Tac_Toe_Game_1 implements Runnable {

    private int row;
    private int column;
    private Label label;
    private DataOutputStream to;

    public Tic_Tac_Toe_Game_1(int row, int column, Label label, DataOutputStream dataOutputStream) {
        this.row = row;
        this.column = column;
        this.label = label;
        this.to = dataOutputStream;
    }

    @Override
    public void run() {
        //After End Game, Player Not Allowed To Shoot
        if (Board.boardSituation(Board.board) != 0){
            if (Board.boardSituation(Board.board) == WINPlayer1){
                openFxml(MenuFXMLPATH, WinsFXMLPATH);
                return;
            }
            else if (Board.boardSituation(Board.board) == LOSEPlayer1){
                openFxml(MenuFXMLPATH, LoseFXMLPATH);
                return;

            }else if (Board.boardSituation(Board.board) == DRAW){
                openFxml(MenuFXMLPATH, DrawFXMLPATH);
                return;
            }
            return;
        }

        if (Board.board[row][column].getShoot() == ' ' && Board.isTurn){
            Board.board[row][column].setShoot('X');

            Board.isTurn = false;

            //Add result to data
            if (Board.boardSituation(Board.board) != 0){
                try {
                    to.writeInt(ADD_RESULT);
                    to.writeUTF("tic-tac-toe1");
                    to.writeUTF(Main.USERNAME);
                    to.writeInt(Board.boardSituation(Board.board));

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
             enemyMove();
        }
    }

    public void enemyMove(){
        int x;
        int y;

        do {
            int[] turn = MiniMax.findBestMove(Board.board);
            x = turn[0];
            y = turn[1];

        }while (Board.board[x][y].getShoot() != ' ');

        Board.board[x][y].setShoot('O');
        if (Board.boardSituation(Board.board) != 0){
            if (Board.boardSituation(Board.board) == WINPlayer1)
                Platform.runLater(() -> {
                    label.setText("You WON! Click anywhere to exit!");
                });
            else if (Board.boardSituation(Board.board) == LOSEPlayer1){
                Platform.runLater(() -> {
                    label.setText("You LOSE! Click anywhere to exit!");
                });
            }else if (Board.boardSituation(Board.board) == DRAW){
                Platform.runLater(() -> {
                    label.setText("It's a DRAW! Click anywhere to exit!");
                });
            }
        }
        Board.isTurn = true;

        new Timeline(new KeyFrame(Duration.millis(1000))).play();

        //Add result to data
        if (Board.boardSituation(Board.board) != 0){
            try {
                to.writeInt(ADD_RESULT);
                to.writeUTF("tic-tac-toe1");
                to.writeUTF(Main.USERNAME);
                to.writeInt(Board.boardSituation(Board.board));

            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
    }


}
