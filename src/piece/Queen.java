package piece;

public class Queen extends Piece{
    public Queen(int x, int y, String color) {
        super(x, y, color);
    }

    public boolean isValidMove(int toX, int toY, Piece[][] board) {

        boolean isRookMove = (toX == x || toY == y);
        boolean isBishopMove = (Math.abs(toX - x) == Math.abs(toY - y));

        if (!isRookMove && !isBishopMove) {
            return false;
        }

        return isPathClear(toX, toY, board) && canCapture(toX, toY, board);
    }

}
