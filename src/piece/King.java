package piece;

import board.Board;

public class King extends Piece {

    public King(int x, int y, String color) {
        super(x, y, color);
    }

    @Override
    public boolean isValidMove(int toX, int toY, Piece[][] board) {
        int dx = Math.abs(toX - x);
        int dy = Math.abs(toY - y);

        // Normal king move (one square in any direction)
        boolean isOneSquare = (dx <= 1 && dy <= 1) && (dx + dy > 0);
        if (isOneSquare && canCapture(toX, toY, board)) {
            return true;
        }

        // Castling
        if (!hasMoved && dy == 0 && dx == 2) {
            return canCastle(toX, toY, board);
        }

        return false;
    }

    private boolean canCastle(int toX, int toY, Piece[][] board) {
        boolean isKingsideCastle = toX > x;
        int rookX = isKingsideCastle ? 7 : 0;

        Piece rook = board[y][rookX];

        if (!(rook instanceof Rook) || rook.hasMoved() || !rook.getColor().equals(color)) {
            return false;
        }

        int direction = isKingsideCastle ? 1 : -1;
        int checkX = x + direction;

        while (checkX != rookX) {
            if (board[y][checkX] != null) {
                return false;
            }
            checkX += direction;
        }

        return true;
    }

    public boolean isCastlingPathSafe(int toX, Piece[][] board, Board gameBoard) {
        if (Math.abs(toX - x) != 2) {
            return true;
        }

        int direction = toX > x ? 1 : -1;

        for (int i = 0; i <= 2; i++) {
            int checkX = x + (i * direction);
            if (gameBoard.isSquareUnderAttack(checkX, y, color)) {
                return false;
            }
        }

        return true;
    }
}
