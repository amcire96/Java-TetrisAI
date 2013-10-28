/*
 * Eric Ma
 * This class makes a two dimensional board of ints
 * This board is specialized to play the game of Tetris
 *
 */
public class Board {
	private Board newBoard;
	private int[][] board;
	private int length;
	private int width;
	//default constructor
	public Board(){
		length = -1;
		width = -1;
	}
	//constructor setting the 2D array of ints to a certain length and width
	public Board(int length, int width){
		this.length = length;
		this.width = width;
		board = new int[length][width];
		for(int i = 0; i < length; i ++){
			for(int j = 0; j < width; j++){
				board[i][j] = 0;
			}
		}
	}
	//makes a copy of the board
	public Board makeCopy(){
		newBoard = new Board(length,width);
		for(int i = 0; i < length; i ++){
			for(int j = 0; j < width; j++){
				newBoard.setValue(i, j, board[i][j]);
			}
		}
		return newBoard;
	}
	//sees if a row and column are valid
	public boolean newLocationValid(int newRow, int newCol){
		if(newRow>=board.length || newRow<0 || newCol >=width || newCol<0){
			return false;
		}
		return true;
	}
	//shifts all cells up to a certain row number down by one spot
	public void shiftDown(int upToRowNum){
		for(int i = upToRowNum-1; i >-1; i--){
			for(int j = 0; j < width; j++){
				if(board[i][j] != 0){
					if(newLocationValid(i+2,j)){
						if(board[i+2][j]==0){
							board[i+2][j]=board[i][j];
							board[i][j]=0;
						}	
						else{
							board[i+1][j] = board[i][j];
							board[i][j] = 0;
						}
					}

					else{
					board[i+1][j] = board[i][j];
					board[i][j] = 0;						
					}

					
				}
				
			}
		}
	}
	//checks if a certain row is all filled
	public boolean checkTetris(int rowNum){
		for(int j = 0; j < width; j++){
			if(board[rowNum][j] == 0){
				return false;
			}
		}
		return true;
	}
	//getter returning the length
	public int getLength(){
		return length;
	}
	//getter returning the width
	public int getWidth(){
		return width;
	}
	//setter setting the value of a cell to an int value
	public void setValue(int row, int col, int color){
		board[row][col] = color;
	}
	//getter returning the value of a cell
	public int getValue(int row, int col){
		return board[row][col];
	}
	//returns a String representation of the board
	public String toString(){
		String print = new String();
		for (int i = 0; i < length; i++) {
	 		for (int j = 0; j < width; j++) {
	 			if(board[i][j] != 0){
	 				print += "c ";
	 			}
	 			else{
	 				print += "f ";
	 			}
        //	   print += board[i][j] + " ";
     		}
     	print += "\n";
		}
		return print;
	}
	
	public static void main(String[] args){
		Board board = new Board(23,10);
		
		board.setValue(0,4,0);
		board.setValue(20,3,0);
		for(int i = 0; i < board.width; i++){
			board.setValue(22,i,1);
		}
		for(int i = 0; i < board.width; i++){
			board.setValue(21,i,1);
		}
		System.out.println(board);
		
		for(int i = 0; i < board.getLength(); i++){
			if(board.checkTetris(i)){
				for(int j = 0; j < board.getWidth(); j++ ){
					board.setValue(i, j, 0);
					
				}
				board.shiftDown(i);
			}
		}
	//	board.shiftDown(22);
		System.out.println(board);
	}
}
