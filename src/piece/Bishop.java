package piece;

public class Bishop extends Piece {

    public Bishop(int x, int y, String color) {
        super(x, y, color);
    }

    @Override
    public boolean isValidMove(int toX, int toY, Piece[][] board) {
        if (Math.abs(toX - x) != Math.abs(toY - y)) {
            return false;
        }

        return isPathClear(toX, toY, board) && canCapture(toX, toY, board);
    }
}
