package Game.Games.BattleShip_1;

public class Goal extends Cell {
    private Cell right;
    private Cell left;
    private Cell up;
    private Cell down;
    private Cell right_up;
    private Cell right_down;
    private Cell left_up;
    private Cell left_down;


    public Goal(int x, int y, Board board) {
        super(x, y, board);
        if (board.isValidPoint(x+1, y))
            this.down = board.getBloc(x+1, y);

        if (board.isValidPoint(x+1, y+1))
            this.right_down =board.getBloc(x+1, y+1);

        if (board.isValidPoint(x+1, y))
            this.left_down = board.getBloc(x+1, y);

        if (board.isValidPoint(x-1, y))
            this.up = board.getBloc(x-1, y);

        if (board.isValidPoint(x-1, y+1))
            this.right_up = board.getBloc(x-1, y+1);

        if (board.isValidPoint(x-1, y-1))
            this.left_up = board.getBloc(x-1, y-1);

        if (board.isValidPoint(x, y+1))
            this.right = board.getBloc(x, y+1);

        if (board.isValidPoint(x, y-1))
            this.left = board.getBloc(x, y-1);
    }

    //getter and setter
    public Cell getRight() {
        return right;
    }

    public Cell getLeft() {
        return left;
    }

    public Cell getUp() {
        return up;
    }

    public Cell getDown() {
        return down;
    }

    public Cell getRight_up() {
        return right_up;
    }

    public Cell getRight_down() {
        return right_down;
    }

    public Cell getLeft_up() {
        return left_up;
    }

    public Cell getLeft_down() {
        return left_down;
    }

}
