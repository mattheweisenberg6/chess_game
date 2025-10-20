package board;

import piece.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class Board {
    private Piece[][] board;
    private JPanel[][] squares;
    private JFrame window;
    private JLabel selectedPieceLabel;
    private Point selectedPiecePosition;
    private String currentTurn;
    private JLabel turnLabel;
    private JLabel statusLabel;
    private int moveCount;  // Track move number for en passant

    public Board() {
        board = new Piece[8][8];
        squares = new JPanel[8][8];
        currentTurn = "white";
        moveCount = 0;
    }

    // make this method public so King can use it
    public boolean isSquareUnderAttack(int x, int y, String kingColor) {
        String opponentColor = kingColor.equals("white") ? "black" : "white";

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece != null && piece.getColor().equals(opponentColor)) {
                    // For pawns, check diagonal attacks only (not forward moves)
                    if (piece instanceof Pawn) {
                        int direction = opponentColor.equals("white") ? -1 : 1;
                        if (Math.abs(x - col) == 1 && y == row + direction) {
                            return true;
                        }
                    } else if (piece.isValidMove(x, y, board)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void initializeBoard() {
        window = new JFrame("Simple Chess");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(750, 850);
        window.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        topPanel.setBackground(Color.LIGHT_GRAY);

        turnLabel = new JLabel("White's Turn", SwingConstants.CENTER);
        turnLabel.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(turnLabel);

        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 20));
        statusLabel.setForeground(Color.RED);
        topPanel.add(statusLabel);

        window.add(topPanel, BorderLayout.NORTH);

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(8, 8));

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JPanel square = new JPanel();
                square.setLayout(new BorderLayout());

                if ((row + col) % 2 == 0) {
                    square.setBackground(Color.WHITE);
                } else {
                    square.setBackground(Color.GRAY);
                }

                squares[row][col] = square;
                addSquareClickListener(square, row, col);
                boardPanel.add(square);
            }
        }

        window.add(boardPanel, BorderLayout.CENTER);
        window.setLocationRelativeTo(null);

        setupPieces();
        window.setVisible(true);
    }

    private void setupPieces() {
        // BLACK pieces
        board[0][0] = new Rook(0, 0, "black");
        addPieceToSquare(squares[0][0], board[0][0], "♜");

        board[0][1] = new Knight(1, 0, "black");
        addPieceToSquare(squares[0][1], board[0][1], "♞");

        board[0][2] = new Bishop(2, 0, "black");
        addPieceToSquare(squares[0][2], board[0][2], "♝");

        board[0][3] = new Queen(3, 0, "black");
        addPieceToSquare(squares[0][3], board[0][3], "♛");

        board[0][4] = new King(4, 0, "black");
        addPieceToSquare(squares[0][4], board[0][4], "♚");

        board[0][5] = new Bishop(5, 0, "black");
        addPieceToSquare(squares[0][5], board[0][5], "♝");

        board[0][6] = new Knight(6, 0, "black");
        addPieceToSquare(squares[0][6], board[0][6], "♞");

        board[0][7] = new Rook(7, 0, "black");
        addPieceToSquare(squares[0][7], board[0][7], "♜");

        for (int col = 0; col < 8; col++) {
            Pawn blackPawn = new Pawn(col, 1, "black");
            board[1][col] = blackPawn;
            addPieceToSquare(squares[1][col], blackPawn, "♟");
        }

        // WHITE pieces
        for (int col = 0; col < 8; col++) {
            Pawn whitePawn = new Pawn(col, 6, "white");
            board[6][col] = whitePawn;
            addPieceToSquare(squares[6][col], whitePawn, "♙");
        }

        board[7][0] = new Rook(0, 7, "white");
        addPieceToSquare(squares[7][0], board[7][0], "♖");

        board[7][1] = new Knight(1, 7, "white");
        addPieceToSquare(squares[7][1], board[7][1], "♘");

        board[7][2] = new Bishop(2, 7, "white");
        addPieceToSquare(squares[7][2], board[7][2], "♗");

        board[7][3] = new Queen(3, 7, "white");
        addPieceToSquare(squares[7][3], board[7][3], "♕");

        board[7][4] = new King(4, 7, "white");
        addPieceToSquare(squares[7][4], board[7][4], "♔");

        board[7][5] = new Bishop(5, 7, "white");
        addPieceToSquare(squares[7][5], board[7][5], "♗");

        board[7][6] = new Knight(6, 7, "white");
        addPieceToSquare(squares[7][6], board[7][6], "♘");

        board[7][7] = new Rook(7, 7, "white");
        addPieceToSquare(squares[7][7], board[7][7], "♖");
    }

    private void addPieceToSquare(JPanel square, Piece piece, String symbol) {
        JLabel pieceLabel = new JLabel(symbol, SwingConstants.CENTER);
        pieceLabel.setFont(new Font("Serif", Font.PLAIN, 60));
        pieceLabel.putClientProperty("piece", piece);
        square.add(pieceLabel, BorderLayout.CENTER);
    }

    private void addSquareClickListener(JPanel square, int row, int col) {
        square.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleSquareClick(square, row, col);
            }
        });
    }

    private Point findKing(String color) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece instanceof King && piece.getColor().equals(color)) {
                    return new Point(col, row);
                }
            }
        }
        return null;
    }

    private boolean isInCheck(String color) {
        Point kingPos = findKing(color);
        if (kingPos == null) return false;

        return isSquareUnderAttack(kingPos.x, kingPos.y, color);
    }

    private boolean wouldBeInCheck(int fromX, int fromY, int toX, int toY, String color) {
        Piece movingPiece = board[fromY][fromX];
        Piece capturedPiece = board[toY][toX];
        int oldX = movingPiece.getX();
        int oldY = movingPiece.getY();

        board[toY][toX] = movingPiece;
        board[fromY][fromX] = null;
        movingPiece.setX(toX);
        movingPiece.setY(toY);

        boolean inCheck = isInCheck(color);

        board[fromY][fromX] = movingPiece;
        board[toY][toX] = capturedPiece;
        movingPiece.setX(oldX);
        movingPiece.setY(oldY);

        return inCheck;
    }

    private boolean hasLegalMoves(String color) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece != null && piece.getColor().equals(color)) {
                    List<Point> possibleMoves = piece.getPossibleMoves(board);
                    for (Point move : possibleMoves) {
                        if (!wouldBeInCheck(col, row, move.x, move.y, color)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private void checkGameState() {
        boolean inCheck = isInCheck(currentTurn);
        boolean hasLegalMoves = hasLegalMoves(currentTurn);

        if (!hasLegalMoves) {
            if (inCheck) {
                String winner = currentTurn.equals("white") ? "Black" : "White";
                statusLabel.setText("CHECKMATE! " + winner + " wins!");
                disableBoard();
                JOptionPane.showMessageDialog(window,
                        winner + " wins by checkmate!",
                        "Game Over",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                statusLabel.setText("STALEMATE! It's a draw!");
                disableBoard();
                JOptionPane.showMessageDialog(window,
                        "The game is a draw by stalemate!",
                        "Game Over",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (inCheck) {
            statusLabel.setText("CHECK!");
        } else {
            statusLabel.setText("");
        }
    }

    private void disableBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                for (MouseListener ml : squares[row][col].getMouseListeners()) {
                    squares[row][col].removeMouseListener(ml);
                }
            }
        }
    }

    private void handleSquareClick(JPanel clickedSquare, int row, int col) {
        if (selectedPieceLabel == null) {
            Component[] components = clickedSquare.getComponents();
            if (components.length > 0 && components[0] instanceof JLabel label) {
                Piece piece = (Piece) label.getClientProperty("piece");

                if (piece != null && piece.getColor().equals(currentTurn)) {
                    selectedPieceLabel = label;
                    selectedPiecePosition = new Point(col, row);
                    clickedSquare.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                } else {
                    showInvalidTurnMessage();
                }
            }
        } else {
            int fromRow = selectedPiecePosition.y;
            int fromCol = selectedPiecePosition.x;

            Piece movingPiece = board[fromRow][fromCol];

            if (movingPiece != null && movingPiece.isValidMove(col, row, board)) {
                // Special check for castling - king cannot move through check
                if (movingPiece instanceof King king && Math.abs(col - fromCol) == 2) {
                    if (!king.isCastlingPathSafe(col, board, this)) {
                        squares[fromRow][fromCol].setBorder(null);
                        selectedPieceLabel = null;
                        selectedPiecePosition = null;
                        JOptionPane.showMessageDialog(window,
                                "Cannot castle through check!",
                                "Illegal Move",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }

                if (wouldBeInCheck(fromCol, fromRow, col, row, currentTurn)) {
                    squares[fromRow][fromCol].setBorder(null);
                    selectedPieceLabel = null;
                    selectedPiecePosition = null;
                    JOptionPane.showMessageDialog(window,
                            "This move would leave your king in check!",
                            "Illegal Move",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Check if this is an en passant capture
                boolean isEnPassant = false;
                Pawn capturedEnPassantPawn = null;
                if (movingPiece instanceof Pawn && Math.abs(col - fromCol) == 1 && board[row][col] == null) {
                    Piece adjacentPiece = board[fromRow][col];
                    if (adjacentPiece instanceof Pawn) {
                        isEnPassant = true;
                        capturedEnPassantPawn = (Pawn) adjacentPiece;
                    }
                }

                // Make the move
                squares[fromRow][fromCol].remove(selectedPieceLabel);
                squares[fromRow][fromCol].setBorder(null);

                Component[] components = clickedSquare.getComponents();
                if (components.length > 0) {
                    clickedSquare.remove(components[0]);
                }

                // Handle en passant capture
                if (isEnPassant && capturedEnPassantPawn != null) {
                    Component[] capturedComponents = squares[fromRow][col].getComponents();
                    if (capturedComponents.length > 0) {
                        squares[fromRow][col].remove(capturedComponents[0]);
                    }
                    board[fromRow][col] = null;
                    squares[fromRow][col].revalidate();
                    squares[fromRow][col].repaint();
                }

                clickedSquare.add(selectedPieceLabel, BorderLayout.CENTER);

                // handle castling - move the rook
                if (movingPiece instanceof King && Math.abs(col - fromCol) == 2) {
                    boolean isKingside = col > fromCol;
                    int rookFromCol = isKingside ? 7 : 0;
                    int rookToCol = isKingside ? col - 1 : col + 1;

                    Piece rook = board[fromRow][rookFromCol];
                    if (rook instanceof Rook) {
                        // Get rook's visual component
                        Component[] rookComponents = squares[fromRow][rookFromCol].getComponents();
                        if (rookComponents.length > 0) {
                            JLabel rookLabel = (JLabel) rookComponents[0];
                            squares[fromRow][rookFromCol].remove(rookLabel);
                            squares[fromRow][rookToCol].add(rookLabel, BorderLayout.CENTER);

                            // Update rook position in board array
                            board[fromRow][rookToCol] = rook;
                            board[fromRow][rookFromCol] = null;
                            rook.setX(rookToCol);
                            rook.setHasMoved(true);

                            squares[fromRow][rookFromCol].revalidate();
                            squares[fromRow][rookFromCol].repaint();
                            squares[fromRow][rookToCol].revalidate();
                            squares[fromRow][rookToCol].repaint();
                        }
                    }
                }

                board[row][col] = movingPiece;
                board[fromRow][fromCol] = null;

                movingPiece.setX(col);
                movingPiece.setY(row);
                movingPiece.setHasMoved(true);

                // Handle pawn double push for en passant
                if (movingPiece instanceof Pawn && Math.abs(row - fromRow) == 2) {
                    ((Pawn) movingPiece).markDoublePush(moveCount);
                }

                // clear en passant for all other pawns of the same color
                clearOldEnPassant(currentTurn);

                squares[fromRow][fromCol].revalidate();
                squares[fromRow][fromCol].repaint();
                clickedSquare.revalidate();
                clickedSquare.repaint();

                moveCount++;
                switchTurn();
                checkGameState();
            } else {
                squares[fromRow][fromCol].setBorder(null);
            }

            selectedPieceLabel = null;
            selectedPiecePosition = null;
        }
    }

    private void clearOldEnPassant(String color) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece instanceof Pawn pawn && piece.getColor().equals(color)) {
                    if (pawn.getLastMoveDoublePush() < moveCount - 1) {
                        pawn.clearEnPassant();
                    }
                }
            }
        }
    }

    private void switchTurn() {
        currentTurn = currentTurn.equals("white") ? "black" : "white";
        updateTurnLabel();
    }

    private void updateTurnLabel() {
        String displayTurn = currentTurn.substring(0, 1).toUpperCase() + currentTurn.substring(1);
        turnLabel.setText(displayTurn + "'s Turn");

        if (currentTurn.equals("white")) {
            turnLabel.setForeground(Color.BLACK);
        } else {
            turnLabel.setForeground(Color.DARK_GRAY);
        }
    }

    private void showInvalidTurnMessage() {
        String wrongColor = currentTurn.equals("white") ? "Black" : "White";
        JOptionPane.showMessageDialog(window,
                "It's " + currentTurn + "'s turn! You cannot move " + wrongColor + " pieces.",
                "Invalid Turn",
                JOptionPane.WARNING_MESSAGE);
    }
}