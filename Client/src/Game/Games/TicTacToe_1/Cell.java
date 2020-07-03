package Game.Games.TicTacToe_1;
import Constants.Responsive;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import static Constants.MediaPlayers.*;
import static Constants.Paths.PHOTO;
import static Constants.Responsive.ResponsiveShape;

public class Cell extends Rectangle {
    private char shoot;
    private int row;
    private int column;
    public Cell(int row, int column) {
        super(200, 200);
        ResponsiveShape(this, 20);
        this.row = row;
        this.column = column;
        this.shoot = ' ';

        setStrokeWidth(3);
        setOpacity(0.3);
        setFill(Color.THISTLE);
        setStroke(Paint.valueOf("#949dcd"));
    }

    public void setShoot(char shoot) {
        this.shoot = shoot;
        if (shoot == 'X'){
            this.setFill(new ImagePattern(new Image(PHOTO+"X.png")));
            PlayEffect(BUTTON_EFFECT);
            setOpacity(0.8);
        }else if(shoot == 'O'){
            setFill(new ImagePattern(new Image(PHOTO+"O.png")));
            setOpacity(0.8);
        }
    }

    public void setShootTemp(char shoot){
        this.shoot = shoot;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public char getShoot() {
        return shoot;
    }
}