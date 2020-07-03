package Game.Games.TicTacToe_2;

import Constants.Responsive;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import static Constants.MediaPlayers.*;
import static Constants.Paths.PHOTO;

public class Cell extends Rectangle {
    private char shoot = ' ';
    private int row;
    private int column;

    public Cell(int row, int column) {
        Responsive.ResponsiveShape(this, 17);
        this.row = row;
        this.column = column;

        setStrokeWidth(3);
        setOpacity(0.3);
        setFill(Color.THISTLE);
        setStroke(Paint.valueOf("#949dcd"));
    }


    public char getShoot() {
        return shoot;
    }

    public void setShoot(char shoot) {
        PlayEffect(BUTTON_EFFECT);
        this.shoot = shoot;
        this.setOpacity(0.8);
        if (shoot == 'X'){
            this.setFill(new ImagePattern(new Image(PHOTO+"X.png")));
        }else if(shoot == 'O'){
            this.setFill(new ImagePattern(new Image(PHOTO+"O.png")));
        }
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

}
