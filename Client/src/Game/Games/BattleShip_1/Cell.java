package Game.Games.BattleShip_1;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import static Constants.MediaPlayers.*;
import static Constants.Paths.PHOTO;
import static Constants.Responsive.ResponsiveShape;

public class Cell extends Rectangle{

    private int x, y;
    private Piece piece = null;
    private boolean dead = false;
    private Board board;

    //sounds
//    private AudioClip kill = new AudioClip("file:src/Client/Media/kill.wav");


    public Cell(int x, int y, Board board) {
        super(60, 60);
        ResponsiveShape(this, 5);

        setStrokeWidth(3);

        setOpacity(0.3);
        setFill(Color.THISTLE);
        setStroke(Paint.valueOf("#949dcd"));

        this.x = x;
        this.y = y;
        this.board = board;
        setOpacity(0.3);
        setOnMouseEntered(event -> {
            if (!isDead()){
                if (board.isEnemy() || (!board.isEnemy() && piece==null))
                    setFill(Color.BLACK);
            }
        });
        setOnMouseExited(event -> {
            if (!isDead()){
                if (board.isEnemy() || (!board.isEnemy() && piece==null))
                    setFill(Color.THISTLE);
            }
        });
    }

    //it set the died cells and return if this cell is a ship cell or not
    public boolean shoot() {
        dead = true;
        if(piece == null){
            setFill(new ImagePattern(new Image(PHOTO+"GLASS.png")));
            setOpacity(0.8);
        }
        if (board.isEnemy()){
            PlayEffect(BUTTON_EFFECT);
        }
        if (piece != null ) {
            piece.hit();
            if (piece.isEnemy()){
                setFill(new ImagePattern(new Image(PHOTO+"O.png")));
            }else setFill(new ImagePattern(new Image(PHOTO+"X.png")));

            setOpacity(0.8);
            //after shoot all blocs of a piece
            if (!piece.isAlive()) {
                PlayEffect(BROKE_GLASS_EFFECT);
                //player gets some money after shoot to enemy piece
                if(!piece.isEnemy()){
                    if (piece.getType() == 11){
                        board.kingCount--;
                    }
                    else if (piece.getType() == 10 || piece.getType() == 9) {
                        board.rookCount--;
                    }
                    else if (piece.getType() > 5 && piece.getType() <= 8) {
                        board.knightCount--;
                    }
                    else if (piece.getType() > 0 && piece.getType() <= 5) {
                        board.pawnCount--;
                    }
                }
                board.pieceCount--;
            }
            return true;
        }
        return false;
    }

    //getter and setter
    public Piece getPiece() {
        return piece;
    }
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public boolean isDead() {
        return dead;
    }

    public int getBlocX() {
        return x;
    }

    public int getBlocY() {
        return y;
    }

    public Board getBoard() {
        return board;
    }

}

