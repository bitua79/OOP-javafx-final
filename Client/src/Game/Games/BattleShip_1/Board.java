package Game.Games.BattleShip_1;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

import java.util.ArrayList;
import java.util.List;

import static Constants.Paths.PHOTO;

public class Board extends Parent {
    private VBox rows = new VBox();
    private boolean isEnemy = false;
    public int pieceCount = 11;
    public int kingCount = 1;
    public int rookCount = 2;
    public int knightCount = 3;
    public int pawnCount = 5;
    public int width;
    public int height;

    //make board by blocs in columns and rows
    public Board(boolean enemy, EventHandler<? super MouseEvent> eventHandler) {
        this.isEnemy = enemy;
        for (int y = 0; y < 10; y++) {
            HBox row = new HBox();
                for (int x = 0; x < 10; x++) {
                Cell cell = new Cell(x, y, this);
                cell.setOnMouseClicked(eventHandler);
                cell.getStyleClass().add("rectangle");
                row.getChildren().add(cell);
            }
            rows.getChildren().add(row);
        }
        getChildren().add(rows);
    }

    //place a piece Starting from bloc(x, y)
    public boolean placePiece(Piece piece, int x, int y) {
        if (canPlacePiece(piece, x, y)) {
            int width = piece.getWidth();
            int height = piece.getHeight();
            for (int i = x; i < x + width; i++)
                for (int j = y; j < y + height; j++) {
                    Cell pieceCell = getBloc(i, j);
                    pieceCell.setPiece(piece);
                    if (!isEnemy) {
                        pieceCell.setFill(new ImagePattern(new Image(PHOTO+"X.png")));
//                        pieceBloc.setStroke(Paint.valueOf("#949dcd"));
                        pieceCell.setStroke(Color.BLACK);
                    }
                }
            return true;
        }
        return false;
    }

    //return the bloc which locate in "x" th column and "y" th row
    public Cell getBloc(int x, int y) {
        return (Cell)((HBox)rows.getChildren().get(y)).getChildren().get(x);
    }

    //return an array which contains all valid neighbors of bloc(x, y)
    public Cell[] getNeighbors(int x, int y) {
        Point2D[] pointS = new Point2D[]{
                new Point2D(x - 1, y - 1),
                new Point2D(x - 1, y),
                new Point2D(x - 1, y + 1),
                new Point2D(x, y - 1),
                new Point2D(x, y + 1),
                new Point2D(x + 1, y - 1),
                new Point2D(x + 1, y),
                new Point2D(x + 1, y + 1)
        };

        List<Cell> neighbors = new ArrayList<Cell>();

        for (Point2D p : pointS) {
            if (isValidPoint(p)) {
                neighbors.add(getBloc((int) p.getX(), (int) p.getY()));
            }
        }

        return neighbors.toArray(new Cell[0]);
    }

    //return true if we can place the piece starting from bloc(x, y)
    public boolean canPlacePiece(Piece piece, int x, int y) {
        int width = piece.getWidth();
        int height = piece.getHeight();

        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++){
                //if bloc is not valid
                if (!isValidPoint(i, j))
                    return false;

                Cell tempCell = getBloc(i, j);
                //if this bloc belongs to an another piece
                if (tempCell.getPiece() != null)
                    return false;

                //if bloc was shoot(for buying new pieces)
                if (tempCell.isDead())
                    return false;

                for (Cell neighbor : getNeighbors(i, j)) {

                    //if bloc's neighbors belong to a piece
                    if (neighbor.getPiece() != null)
                        return false;
                }
            }
        }
        return true;
    }

    //if this point or actually bloc is valid
    public boolean isValidPoint(Point2D point) {
        return isValidPoint(point.getX(), point.getY());
    }

    //if this point or actually bloc is valid by use x and y
    public boolean isValidPoint(double x, double y) {
        return x >= 0 && x < 10 && y >= 0 && y < 10;
    }
    public boolean isEnemy() {
        return isEnemy;
    }
}