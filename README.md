# ♟️ Java Chess Game

A fully-featured chess game built with Java Swing, implementing complete chess rules and mechanics.

## Features

✅ **Complete Chess Rules**
- All piece movements (Pawn, Rook, Knight, Bishop, Queen, King)
- Move validation for each piece type
- Turn-based gameplay (White vs Black)

✅ **Special Moves**
- Castling (Kingside and Queenside)
- En Passant captures

✅ **Game Logic**
- Check detection
- Checkmate detection
- Stalemate detection
- Prevents illegal moves that would leave king in check

✅ **User Interface**
- Clean 8x8 chessboard with alternating colors
- Unicode chess piece symbols (♔ ♕ ♖ ♗ ♘ ♙)
- Visual piece selection with yellow border highlight
- Turn indicator showing current player
- Status messages for check/checkmate/stalemate

## Technologies Used

- **Java** - Core programming language
- **Swing** - GUI framework for visual interface
- **AWT** - Event handling and graphics

## How to Run

1. Clone the repository:
```bash
git clone https://github.com/mattheweisenberg6/chess_game.git
```

2. Navigate to the project directory:
```bash
cd chess_game
```

3. Compile and run:
```bash
javac main/Main.java
java main.Main
```

## How to Play

1. Click on a piece to select it (yellow border appears)
2. Click on a destination square to move the piece
3. The game validates moves according to chess rules
4. Players alternate turns (White moves first)
5. The game detects check, checkmate, and stalemate automatically

## Project Structure
```
chess_game/
├── main/
│   └── Main.java          # Entry point
├── board/
│   └── Board.java         # Board logic and UI
└── piece/
    ├── Piece.java         # Abstract piece class
    ├── Pawn.java
    ├── Rook.java
    ├── Knight.java
    ├── Bishop.java
    ├── Queen.java
    └── King.java
```

## Future Enhancements

- [ ] Pawn promotion to Queen/Rook/Bishop/Knight
- [ ] Move history and notation
- [ ] Undo/Redo functionality
- [ ] Timer for each player
- [ ] Save/Load game state
- [ ] AI opponent
- [ ] Online multiplayer

## License

MIT License - feel free to use and modify for your own projects!

## Contributing

Pull requests are welcome! For major changes, please open an issue first to discuss what you would like to change.
