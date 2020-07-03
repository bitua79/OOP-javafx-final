package Game.Games.SnakeLadder_1;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

public class Board extends VBox {

    private Map transmissions = new HashMap<Integer, Integer>();
    private int[][] cellIndexes = new int[10][10];
    private Cell[][] cells = new Cell[10][10];

    public Board(){

        for (int i=0; i<10; i++){
            HBox hBox = new HBox();
            for (int j=0; j<10; j++){
                cells[i][j] = new Cell(i, j);
                hBox.getChildren().add(cells[i][j]);
            }
            this.getChildren().add(hBox);
        }

        int counter = 1;
        for (int i=9; i>=0; i--){
            if (i%2==0) for (int j=9; j>=0; j--){
                    cellIndexes[i][j] = counter;
                    counter ++;
                }
            else for (int j=0; j<10; j++){
                    cellIndexes[i][j] = counter;
                    counter ++;
                }


        }
        initSnakeAndLadders();
    }

    public void initSnakeAndLadders(){
        transmissions.put(1, 38);
        transmissions.put(4, 14);
        transmissions.put(9, 31);
        transmissions.put(17, 7);
        transmissions.put(21, 42);
        transmissions.put(28, 84);
        transmissions.put(51, 67);
        transmissions.put(54, 34);
        transmissions.put(64, 60);
        transmissions.put(72, 91);
        transmissions.put(80, 99);
        transmissions.put(87, 36);
        transmissions.put(93, 73);
        transmissions.put(95, 75);
        transmissions.put(98, 79);
    }

    public void arrangeBoard(Cell last1, Cell last2){
        this.getChildren().clear();

        for (int i=0; i<10; i++){
            HBox hBox = new HBox();
            for (int j=0; j<10; j++){
                cells[i][j] = new Cell(i, j);
                hBox.getChildren().add(cells[i][j]);
            }
            this.getChildren().add(hBox);
        }
        cells[last1.row][last1.column].shoot(SnakeLadder_Game_1.first_player);
        cells[last2.row][last2.column].shoot(SnakeLadder_Game_1.second_player);
    }

    public Map getTransmissions() {
        return transmissions;
    }

    public int[][] getCellIndexes() {
        return cellIndexes;
    }

    public Cell[][] getCells() {
        return cells;
    }
}
