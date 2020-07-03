package Game.Games.TicTacToe_2;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Board extends HBox {
    public static Cell[][] cell = new Cell[3][3];

    public Board(EventHandler<? super MouseEvent> eventHandler){
        VBox rows = new VBox();
        for (int y = 0; y < 3; y++) {
            HBox row = new HBox();
            for (int x = 0; x < 3; x++) {
                cell[x][y] = new Cell(x, y);
                cell[x][y].setOnMouseClicked(eventHandler);
                row.getChildren().add(cell[x][y]);
            }
            rows.getChildren().add(row);
        }
        this.getChildren().add(rows);
    }

    public Cell getCell(int i, int j){
        return cell[i][j];
    }
}
