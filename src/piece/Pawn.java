package piece;

public class Pawn extends Piece {
    private int lastMoveDoublePush;

    public Pawn(int x, int y, String color) {
        super(x, y, color);
        this.lastMoveDoublePush = -1;
    }

    @Override
    public boolean isValidMove(int toX, int toY, Piece[][] board) {
        int direction = color.equals("white") ? -1 : 1;
        int startRow = color.equals("white") ? 6 : 1;

        if (toX == x && toY == y + direction && board[toY][toX] == null) {
            return true;
        }

        if (toX == x && y == startRow && toY == y + (2 * direction)
                && board[toY][toX] == null && board[y + direction][x] == null) {
            return true;
        }

        if (Math.abs(toX - x) == 1 && toY == y + direction) {
            Piece target = board[toY][toX];
            if (target != null && !target.getColor().equals(color)) {
                return true;
            }

            if (target == null) {
                Piece adjacentPiece = board[y][toX];
                if (adjacentPiece instanceof Pawn adjacentPawn && !adjacentPiece.getColor().equals(color)) {
                    return adjacentPawn.canBeEnPassanted();
                }
            }
        }

        return false;
    }

    public void markDoublePush(int moveNumber) {
        this.lastMoveDoublePush = moveNumber;
    }

    public boolean canBeEnPassanted() {
        return lastMoveDoublePush != -1;
    }

    public void clearEnPassant() {
        this.lastMoveDoublePush = -1;
    }

    public int getLastMoveDoublePush() {
        return lastMoveDoublePush;
    }
}