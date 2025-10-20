package piece;

import java.awt.*;
import java.util.*;

public abstract class Piece {
    protected int x;
    protected int y;
    protected String color;
    protected boolean hasMoved;

    public Piece(int x, int y, String color) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.hasMoved = false;
    }

    public abstract boolean isValidMove(int toX, int toY, Piece[][] board);

    public java.util.List<Point> getPossibleMoves(Piece[][] board) {
        java.util.List<Point> moves = new ArrayList<>();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (isValidMove(col, row, board)) {
                    moves.add(new Point(col, row));
                }
            }
        }
        return moves;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getColor() {
        return color;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    protected boolean isPathClear(int toX, int toY, Piece[][] board) {
        int xDir = Integer.compare(toX, x);
        int yDir = Integer.compare(toY, y);

        int currentX = x + xDir;
        int currentY = y + yDir;

        while (currentX != toX || currentY != toY) {
            if (board[currentY][currentX] != null) {
                return false;
            }
            currentX += xDir;
            currentY += yDir;
        }

        return true;
    }

    protected boolean canCapture(int toX, int toY, Piece[][] board) {
        Piece targetPiece = board[toY][toX];
        return targetPiece == null || !targetPiece.getColor().equals(this.color);
    }
}