package Game.Games.BattleShip_1;
import javafx.scene.Parent;

public class Piece extends Parent{

    public int type;
    private int width;
    private int height;
    private int health;
    private boolean isEnemy;
    private boolean isVertical = true;

    public Piece(int type, boolean isVertical, boolean isEnemy) {
        //1 KING
        if (type==11)make(3, 3);

        //2 ROOK
        if (type==10)make(2, 2);
        if (type==9)make(2, 2);

        //3 HORSE
        if (type==8 && isVertical)make(1, 2);
        if (type==8 && !isVertical)make(2, 1);
        if (type==7 && isVertical)make(1, 2);
        if (type==7 && !isVertical)make(2, 1);
        if (type==6 && isVertical)make(1, 2);
        if (type==6 && !isVertical)make(2, 1);

        //5 SOLDIER
        if (type==5)make(1, 1);
        if (type==4)make(1, 1);
        if (type==3)make(1, 1);
        if (type==2)make(1, 1);
        if (type==1)make(1, 1);

        this.type = type;
        this.isEnemy = isEnemy;
    }

    public void make(int width, int height) {
        this.width = width;
        this.height = height;
        this.health = (width * height);
    }

    public void hit() { health--; }

    public boolean isAlive() { return health > 0; }


    //getter and setter
    public int getType() { return type; }
    public void setType(int type) { this.type = type; }

    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    public boolean isVertical() { return isVertical; }
    public void setVertical(boolean vertical) { isVertical = vertical; }

    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }

    public boolean isEnemy() {
        return isEnemy;
    }
}

