/*
 * Eric Ma
 * this class sets up the game of Tetris
 * it includes all the rules and contains the board, the current piece, and the number of rows cleared(tetrisCount)
 */
public class Game {
	Board board;
	Piece piece;
	int tetrisCount;
	//default constructor with 16 rows and 10 columns
	public Game(){
		board = new Board(16,10);
		tetrisCount = 0;
	}
	//constructor taking a board, a piece, and the tetrisCount
	public Game(Board newBoard, Piece newPiece,int newTetrisCount){
		board = newBoard;
		piece = newPiece;
		tetrisCount = newTetrisCount;
	}
	//returns a copy of the game
	public Game copyGame(){
		Board board = copyBoard();
		Piece piece = this.piece.makeCopy();
		int tetrisCount = this.tetrisCount;
		return new Game(board,piece,tetrisCount);
	}
	//sets the orientation, locations, and color of the pieces depending on which type of piece it is
	public void resetPiece(){
		if(piece.getColor() == 1){
			piece = new Piece(piece.getColor(), new Location(0,3), new Location(0,4), new Location(0,5), new Location(0,6), board,0);
		}
		else if(piece.getColor() == 2){
			piece = new Piece(piece.getColor(), new Location(1,6),new Location(0,6),new Location(0,5),new Location(0,4),board,0);
		}
		else if(piece.getColor() == 3){
			piece = new Piece(piece.getColor(), new Location(1,4), new Location(0,4), new Location(0,5),  new Location(0,6), board,0);
		}
		else if(piece.getColor() == 4){
			piece = new Piece(piece.getColor(), new Location(0,4), new Location(1,4), new Location(0,5), new Location(1,5), board,0);
		}
		else if(piece.getColor() == 5){
			piece = new Piece(piece.getColor(), new Location(1,4), new Location(1,5), new Location(0,5), new Location(0,6), board,0);
		}
		else if(piece.getColor() == 6){
			piece = new Piece(piece.getColor(), new Location(0,4), new Location(1,5), new Location(0,5), new Location(0,6), board,0);
		}
		else if(piece.getColor() == 7){
			piece = new Piece(piece.getColor(), new Location(1,6), new Location(1,5), new Location(0,5), new Location(0,4), board,0);
		}
	}
	//sets each piece's locations to the default 0
	public void erasePiece(){
		board.setValue(piece.getLoc1().getRow(), piece.getLoc1().getColumn(),0);
		board.setValue(piece.getLoc2().getRow(), piece.getLoc2().getColumn(),0);
		board.setValue(piece.getLoc3().getRow(), piece.getLoc3().getColumn(),0);
		board.setValue(piece.getLoc4().getRow(), piece.getLoc4().getColumn(),0);
	}
	//generates a random int between 1 and 7 to determine which type of piece is made
	public static int randomInt(){
		int randomInt = (int)(Math.random() * 7) + 1;
		return randomInt;
	}
	//getter returning the piece
	public Piece getPiece(){
		return piece;
	}
	//setter setting the piece to a new Piece
	public void setPiece(Piece newPiece){
		piece = newPiece;
	}
	//returns a copy of the board
	public Board copyBoard(){
		Board newBoard = new Board(board.getLength(),board.getWidth());
		for(int i = 0; i < board.getLength(); i ++){
			for(int j = 0; j < board.getWidth(); j++){
				newBoard.setValue(i, j, board.getValue(i, j));
			}
		}
		return newBoard;
	}
	//getter returning the board
	public Board getBoard(){
		return board;
	}
	//setter setting board to a new Board
	public void setBoard(Board newBoard){
		board = newBoard;
	}
	//gets the random int to determine which piece will be made
	//the places the piece by setting the locations to the color int of the piece
	public void placePiece(){
		int randomInt = randomInt();
		if(randomInt == 1){
			piece = new Piece(randomInt, new Location(0,3), new Location(0,4), new Location(0,5), new Location(0,6), board,0);
		}
		else if(randomInt == 2){
			piece = new Piece(randomInt, new Location(1,6),new Location(0,6),new Location(0,5),new Location(0,4),board,0);
		}
		else if(randomInt == 3){
			piece = new Piece(randomInt, new Location(1,4), new Location(0,4), new Location(0,5),  new Location(0,6), board,0);
		}
		else if(randomInt == 4){
			piece = new Piece(randomInt, new Location(0,4), new Location(1,4), new Location(0,5), new Location(1,5), board,0);
		}
		else if(randomInt == 5){
			piece = new Piece(randomInt, new Location(1,4), new Location(1,5), new Location(0,5), new Location(0,6), board,0);
		}
		else if(randomInt == 6){
			piece = new Piece(randomInt, new Location(0,4), new Location(1,5), new Location(0,5), new Location(0,6), board,0);
		}
		else if(randomInt == 7){
			piece = new Piece(randomInt, new Location(1,6), new Location(1,5), new Location(0,5), new Location(0,4), board,0);
		}
		piece.placeInBoard();
	}
	//cycles through the game
	//score increased by one, the piece is checked if it can descend one spot
	//if the piece cannot descend: the game checks whether any row is completely filled, and places the next piece
	//if there is a tetris, the tetrisCount increases, the level might increase, the score increases, and the board eliminates that row
	public void cycle(){
		GUI.setScore(GUI.getScore()+1);
		GUI.getScoreLabel().setText("Score: " + GUI.getScore());
		if(piece.canMove()){
			piece.descend();
		}
		else{
			
			
			for(int i = 0; i < board.getLength(); i++){
				if(board.checkTetris(i)){
					tetrisCount++;
					GUI.setLevelCount(1+((tetrisCount-1)/10));
					GUI.getLevelLabel().setText("Level: " + GUI.getLevelCount());
		//			GUI.getTimer().setDelay(500-(50*(GUI.getLevelCount()-1)));
					GUI.setScore(GUI.getScore()+10);
					GUI.getScoreLabel().setText("Score: " + GUI.getScore());
					for(int j = 0; j < board.getWidth(); j++ ){
						board.setValue(i, j, 0);
						
					}
					board.shiftDown(i);
				}
			}
			placePiece();
		}
	}
	//getter returning the number of rows cleared (tetrisCount)
	public int getTetrisCount() {
		return tetrisCount;
	}
	//setter setting the tetrisCount to a new tetrisCount
	public void setTetrisCount(int tetrisCount) {
		this.tetrisCount = tetrisCount;
	}
	//returns a String representation of the game by returning the board
	public String toString(){
		return board.toString();
	}
	//starts the game by placing the first piece and starting the timer
	public void playGame(){

		placePiece();
		GUI.getTimer().start();
//		while(!getPiece().getIllegal()){
//		//for(int i = 0; i < 200; i++){
//			//System.out.println(board);
//			
//			cycle();
//		//}
//			
//		}
		
	}
	
//	public static void main(String [] args){
//		Game game = new Game();
//		game.placePiece();
//		
//		System.out.println(game);
//		System.out.println(game.piece.findHeight());
//	}

}
