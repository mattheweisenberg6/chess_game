package piece;

public class Rook extends Piece{
    public Rook(int x, int y, String color) {
        super(x, y, color);
    }

    @Override
    public boolean isValidMove(int toX, int toY, Piece[][] board) {
        if (toX != x  && toY != y) {
            return false;
        }
        return isPathClear(toX, toY, board) && canCapture(toX, toY, board);
    }

}
