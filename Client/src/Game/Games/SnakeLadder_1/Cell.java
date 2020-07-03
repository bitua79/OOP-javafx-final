package Game.Games.SnakeLadder_1;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import static Constants.Paths.PHOTO;
import static Constants.Responsive.ResponsiveShape;

public class Cell extends Rectangle {
    int row;
    int column;

    public Cell(int row, int column){
        this.row = row;
        this.column = column;
        ResponsiveShape(this, 6);

        setOpacity(0.5);
        this.setFill(Color.PLUM);
        setStroke(Paint.valueOf("#949dcd"));
    }

    public void shoot(String player){
        if (player.equals(SnakeLadder_Game_1.first_player)){
            setFill(new ImagePattern(new Image(PHOTO+"X.png")));
        }else {
            setFill(new ImagePattern(new Image(PHOTO+"O.png")));
        }

        this.setOpacity(0.8);
    }

}
