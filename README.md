♟️ Java Chess Game
A fully-featured chess game built with Java Swing, implementing complete chess rules and mechanics.

Technologies Used

Java - Core programming language
Swing - GUI framework for visual interface
AWT - Event handling and graphics

How to Run

Clone the repository:

bashgit clone https://github.com/yourusername/java-chess-game.git

Navigate to the project directory:

bashcd java-chess-game

Compile and run:

bashjavac main/Main.java
java main.Main

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
