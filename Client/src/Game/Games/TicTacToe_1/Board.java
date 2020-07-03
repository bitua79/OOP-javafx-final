package Game.Games.TicTacToe_1;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import static Constants.Notifications.*;

public class Board extends HBox{
    public static Cell[][] board = new Cell[3][3];
    public static boolean isTurn = true;
    public VBox rows = new VBox();

    public Board(int X, int Y, EventHandler<? super MouseEvent> eventHandler) {
        isTurn = true;
        board = new Cell[3][3];
        this.setLayoutX(X);
        this.setLayoutY(Y);
        for (int y = 0; y < 3; y++) {
            HBox row = new HBox();
            for (int x = 0; x < 3; x++) {
                board[x][y] = new Cell(x, y);
                board[x][y].setOnMouseClicked(eventHandler);
                row.getChildren().add(board[x][y]);
            }
            rows.getChildren().add(row);
        }
        this.getChildren().add(rows);
    }

    public static int boardSituation(Cell[][] board){
        // returns 0 if game is not finished
        // returns 1 if X wins
        // returns 2 if O wins
        // returns 3 if game has been draw
        for (int i = 0; i < 3; i++) {
            if (board[i][0].getShoot() == 'X' && board[i][1].getShoot() == 'X' && board[i][2].getShoot() == 'X') return WINPlayer1;
            if (board[i][0].getShoot() == 'O' && board[i][1].getShoot() == 'O' && board[i][2].getShoot() == 'O') return 2;
        }
        for (int i = 0; i < 3; i++) {
            if (board[0][i].getShoot() == 'X' && board[1][i].getShoot() == 'X' && board[2][i].getShoot() == 'X') return WINPlayer1;
            if (board[0][i].getShoot() == 'O' && board[1][i].getShoot() == 'O' && board[2][i].getShoot() == 'O') return LOSEPlayer1;
        }
        if (board[0][0].getShoot() == 'X' && board[1][1].getShoot() == 'X' && board[2][2].getShoot() == 'X') return WINPlayer1;
        if (board[0][2].getShoot() == 'X' && board[1][1].getShoot() == 'X' && board[2][0].getShoot() == 'X') return WINPlayer1;

        if (board[0][0].getShoot() == 'O' && board[1][1].getShoot() == 'O' && board[2][2].getShoot() == 'O') return LOSEPlayer1;
        if (board[0][2].getShoot() == 'O' && board[1][1].getShoot() == 'O' && board[2][0].getShoot() == 'O') return LOSEPlayer1;

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j].getShoot() == ' ') return 0;

        return DRAW;
    }
}
