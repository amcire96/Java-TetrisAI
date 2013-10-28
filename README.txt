Computer Tetris Game
---------------------
My TetrisAI project has 6 classes: Board, Comp, Game, GUI, Location, and Piece class.

The Board class creates a 2-dimensional array of integers and has methods specialized to play the game of Tetris.

The Comp class implements the algorithm that the computer uses to play the game of Tetris.
  The algorithm analyzes all the possible moves and performs the move with the best scores.
  The scores are calculated by multiplying weights to 6 factors: landing height, rows cleared, row transitions, column transitions, holes, and well sum

The Game class sets up the game of Tetris. It has the rules and contains the board, current piece, and tetris count.

The GUI class creates the graphics for the game. It has a JPanel, JFrame, Timer, JLabels and a JComponent to show the game
  In this class, the game can either be played manually or with the artificial intelligence algorithm.

The Location class stores two integers, a row and column of a location of a board.

The Piece class makes the pieces for the game Tetris and contains methods to move/rotate the pieces.