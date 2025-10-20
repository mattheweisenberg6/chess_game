package piece;

public class Knight extends Piece {

    public Knight(int x, int y, String color) {
        super(x, y, color);
    }

    @Override
    public boolean isValidMove(int toX, int toY, Piece[][] board) {
        int dx = Math.abs(toX - x);
        int dy = Math.abs(toY - y);

        boolean isLShape = (dx == 2 && dy == 1) || (dx == 1 && dy == 2);

        return isLShape && canCapture(toX, toY, board);
    }
}
