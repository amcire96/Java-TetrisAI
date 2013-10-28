import java.util.ArrayList;

/*
 * Eric Ma
 * This class makes the pieces for the game Tetris
 * the pieces can be moved or rotated  
 */
public class Piece {
	
//	cyan = 1 I
//	blue = 2 J
//	orange = 3 L
//	yellow = 4 O
//	green = 5 S
//	purple = 6 T
//	red = 7 Z
	//private variables are 4 locations per piece, the board, whether the piece is illegal, the color, and the direction/orientation
	private int color;
	private Location loc1;
	private Location loc2;
	private Location loc3;
	private Location loc4;
	private Board board;
	private boolean illegal;
	private int direction;
	
	//default constructor
	public Piece(){
		color = -1;
	}
	//constructor taking in values to assign to all the private variables
	public Piece(int color, Location loc1, Location loc2, Location loc3, Location loc4, Board board, int direction){
		this.color = color;
		this.loc1 = loc1;
		this.loc2 = loc2;
		this.loc3 = loc3;
		this.loc4 = loc4;
		this.board = board;
		illegal = false;
		this.direction = direction;
	}
	//a copy of the Piece is returned
	public Piece makeCopy(){
		int newColor = color;
		Location loc1= this.loc1.copyLocation();
		Location loc2= this.loc2.copyLocation();
		Location loc3= this.loc3.copyLocation();
		Location loc4 = this.loc4.copyLocation();
		Board newBoard= board;
		int direction = this.direction;
		Piece newPiece = new Piece(newColor,loc1,loc2,loc3,loc4,newBoard,direction);
		return newPiece;
	}
	//returns the height of the piece
	public int findHeight(){
		int height = 0;
		int[] differences = new int[6];
		differences[0] = Math.abs(loc1.getRow()-loc2.getRow());
		differences[1] = Math.abs(loc1.getRow()-loc3.getRow());
		differences[2] = Math.abs(loc1.getRow()-loc4.getRow());
		differences[3] = Math.abs(loc2.getRow()-loc3.getRow());
		differences[4] = Math.abs(loc2.getRow()-loc4.getRow());
		differences[5] = Math.abs(loc3.getRow()-loc4.getRow());
		//calculates the differences in each location and returns the greatest value
		for(int i:differences){
			if(i>height){
				height = i;
			}
		}
		height++;
		return height;
	}
	//getter returning the board
	public Board getBoard() {
		return board;
	}
	//setter setting the board to a new board
	public void setBoard(Board board) {
		this.board = board;
	}
	//getter returning whether the piece is illegal
	public boolean getIllegal(){
		return illegal;
	}
	//places the piece in the board by setting the locations in the board to the color of the piece
	public void placeInBoard(){
		if(board.getValue(loc1.getRow(), loc1.getColumn()) != 0){
			illegal = true;
		}
		else if(board.getValue(loc2.getRow(), loc2.getColumn())!= 0){
			illegal = true;
		}
		else if(board.getValue(loc3.getRow(), loc3.getColumn())!= 0){
			illegal = true;
		}
		else if(board.getValue(loc4.getRow(), loc4.getColumn())!= 0){
			illegal = true;
		}
		board.setValue(loc1.getRow(),loc1.getColumn(),color);
		board.setValue(loc2.getRow(),loc2.getColumn(),color);
		board.setValue(loc3.getRow(),loc3.getColumn(),color);
		board.setValue(loc4.getRow(),loc4.getColumn(),color);
	}
	

	//rotates the piece
	//checks each case (type of piece and orientation) and manually reassigns the locations of the piece
	public void rotate(){
		clear();
		if(color == 1){
			if(direction == 0){
				if(!loc1.newLocationValid(loc1.getRow()+2, loc1.getColumn()+2,board) || 
						!loc2.newLocationValid(loc2.getRow() + 1, loc2.getColumn() + 1,board) ||
						!loc4.newLocationValid(loc4.getRow() -1, loc4.getColumn() - 1,board)){
					placeInBoard();
					return;
				}
				clear();
				loc1.setNewLocation(loc1.getRow()+2, loc1.getColumn()+2);
				loc2.setNewLocation(loc2.getRow() + 1, loc2.getColumn() + 1);
				loc4.setNewLocation(loc4.getRow() -1, loc4.getColumn() - 1);
				direction++;
				placeInBoard();
				
			}
			else if(direction == 1){
				if(!loc1.newLocationValid(loc1.getRow()-2, loc1.getColumn()-2,board) || 
						!loc2.newLocationValid(loc2.getRow()-1, loc2.getColumn()-1,board) ||
						!loc4.newLocationValid(loc4.getRow()+1, loc4.getColumn()+1,board)){
					placeInBoard();
					return;
				}
				clear();
				loc1.setNewLocation(loc1.getRow()-2, loc1.getColumn()-2);
				loc2.setNewLocation(loc2.getRow()-1, loc2.getColumn()-1);
				loc4.setNewLocation(loc4.getRow()+1, loc4.getColumn()+1);
				direction=0;
				placeInBoard();
			}
		}
		else if(color == 2){
			if(direction == 0){
				if(!loc1.newLocationValid(loc1.getRow()-2, loc1.getColumn(),board) ||
						!loc2.newLocationValid(loc2.getRow() - 1, loc2.getColumn()- 1,board) ||
						!loc4.newLocationValid(loc4.getRow() + 1, loc4.getColumn() + 1,board)){
					placeInBoard();
					return;
				}
				clear();
				loc1.setNewLocation(loc1.getRow() -2, loc1.getColumn());
				loc2.setNewLocation(loc2.getRow() - 1, loc2.getColumn() - 1);
				loc4.setNewLocation(loc4.getRow() + 1, loc4.getColumn() + 1);
				direction++;
				placeInBoard();
			}
			else if(direction == 1){
				if(!loc1.newLocationValid(loc1.getRow(), loc1.getColumn()-2,board) ||
						!loc2.newLocationValid(loc2.getRow()+1, loc2.getColumn()-1,board) ||
						!loc4.newLocationValid(loc4.getRow()-1, loc4.getColumn()+1,board)){
					placeInBoard();
					return;
				}
				clear();
				loc1.setNewLocation(loc1.getRow(), loc1.getColumn()-2);
				loc2.setNewLocation(loc2.getRow()+1, loc2.getColumn()-1);
				loc4.setNewLocation(loc4.getRow()-1, loc4.getColumn()+1);
				direction++;
				placeInBoard();
			}
			else if(direction == 2){
				if(!loc1.newLocationValid(loc1.getRow() +2, loc1.getColumn(),board) ||
						!loc2.newLocationValid(loc2.getRow() +1, loc2.getColumn() +1,board) ||
						!loc4.newLocationValid(loc4.getRow() -1, loc4.getColumn() -1,board)){
					placeInBoard();
					return;
				}
				clear();
				loc1.setNewLocation(loc1.getRow() +2, loc1.getColumn());
				loc2.setNewLocation(loc2.getRow() +1, loc2.getColumn() +1);
				loc4.setNewLocation(loc4.getRow() -1, loc4.getColumn() -1);
				direction++;
				placeInBoard();
			}
			else if(direction == 3){
				if(!loc1.newLocationValid(loc1.getRow(), loc1.getColumn()+2,board) ||
						!loc2.newLocationValid(loc2.getRow()-1, loc2.getColumn()+1,board) ||
						!loc4.newLocationValid(loc4.getRow()+1, loc4.getColumn()-1,board)){
					placeInBoard();
					return;
				}
				clear();
				loc1.setNewLocation(loc1.getRow(), loc1.getColumn()+2);
				loc2.setNewLocation(loc2.getRow()-1, loc2.getColumn()+1);
				loc4.setNewLocation(loc4.getRow()+1, loc4.getColumn()-1);
				direction=0;
				placeInBoard();
			}
		}
		
		else if(color == 3){
			if(direction == 0){
				if(!loc1.newLocationValid(loc1.getRow(), loc1.getColumn()+2,board) || 
						!loc2.newLocationValid(loc2.getRow() + 1, loc2.getColumn() + 1,board) || 
						!loc4.newLocationValid(loc4.getRow() - 1, loc4.getColumn() - 1,board)){
					placeInBoard();
					return;
				}
				clear();
				loc1.setNewLocation(loc1.getRow(), loc1.getColumn()+2);
				loc2.setNewLocation(loc2.getRow() + 1, loc2.getColumn() + 1);
				loc4.setNewLocation(loc4.getRow() - 1, loc4.getColumn() - 1);
				direction++;
				placeInBoard();
			}
			else if(direction == 1){
				if(!loc1.newLocationValid(loc1.getRow()-2, loc1.getColumn(),board) ||
						!loc2.newLocationValid(loc2.getRow()-1, loc2.getColumn()+1,board) ||
						!loc4.newLocationValid(loc4.getRow()+1, loc4.getColumn()-1,board)){
					placeInBoard();
					return;
				}
				clear();
				loc1.setNewLocation(loc1.getRow()-2, loc1.getColumn());
				loc2.setNewLocation(loc2.getRow()-1, loc2.getColumn()+1);
				loc4.setNewLocation(loc4.getRow()+1, loc4.getColumn()-1);
				direction++;
				placeInBoard();
			}
			else if(direction == 2){
				if(!loc1.newLocationValid(loc1.getRow(), loc1.getColumn()-2,board) ||
						!loc2.newLocationValid(loc2.getRow() -1, loc2.getColumn() -1,board) ||
						!loc4.newLocationValid(loc4.getRow() +1, loc4.getColumn() +1,board)){
					placeInBoard();
					return;
				}
				clear();
				loc1.setNewLocation(loc1.getRow(), loc1.getColumn()-2);
				loc2.setNewLocation(loc2.getRow() -1, loc2.getColumn() -1);
				loc4.setNewLocation(loc4.getRow() +1, loc4.getColumn() +1);
				direction++;
				placeInBoard();
			}
			else if(direction == 3){
				if(!loc1.newLocationValid(loc1.getRow()+2, loc1.getColumn(),board) ||
						!loc2.newLocationValid(loc2.getRow()+1, loc2.getColumn()-1,board) ||
						!loc4.newLocationValid(loc4.getRow()-1, loc4.getColumn()+1,board)){
					placeInBoard();
					return;
				}
				clear();
				loc1.setNewLocation(loc1.getRow()+2, loc1.getColumn());
				loc2.setNewLocation(loc2.getRow()+1, loc2.getColumn()-1);
				loc4.setNewLocation(loc4.getRow()-1, loc4.getColumn()+1);
				direction=0;
				placeInBoard();
			}
		}
		else if(color == 4){
			clear();
			placeInBoard();
		}
		else if(color == 5){
			if(direction == 0){
				if(!loc1.newLocationValid(loc1.getRow(), loc1.getColumn()+2,board) ||
						!loc2.newLocationValid(loc2.getRow() - 1, loc2.getColumn() + 1,board) ||
						!loc4.newLocationValid(loc4.getRow() - 1, loc4.getColumn() - 1,board)){
					placeInBoard();
					return;
				}
				clear();
				loc1.setNewLocation(loc1.getRow(), loc1.getColumn()+2);
				loc2.setNewLocation(loc2.getRow() - 1, loc2.getColumn() + 1);
				loc4.setNewLocation(loc4.getRow() - 1, loc4.getColumn() - 1);
				direction++;
				placeInBoard();
			}
			else if(direction == 1){
				if(!loc1.newLocationValid(loc1.getRow(), loc1.getColumn()-2,board) ||
						!loc2.newLocationValid(loc2.getRow()+1, loc2.getColumn()-1,board) ||
						!loc4.newLocationValid(loc4.getRow()+1, loc4.getColumn()+1,board)){
					placeInBoard();
					return;
				}
				clear();
				loc1.setNewLocation(loc1.getRow(), loc1.getColumn()-2);
				loc2.setNewLocation(loc2.getRow()+1, loc2.getColumn()-1);
				loc4.setNewLocation(loc4.getRow()+1, loc4.getColumn()+1);
				direction=0;
				placeInBoard();
			}
		}
		else if(color == 6){
			if(direction == 0){
				if(!loc1.newLocationValid(loc1.getRow()+1, loc1.getColumn()+1,board) ||
						!loc2.newLocationValid(loc2.getRow() - 1, loc2.getColumn() + 1,board) ||
						!loc4.newLocationValid(loc4.getRow() - 1, loc4.getColumn() - 1,board)){
					placeInBoard();
					return;
				}
				clear();
				loc1.setNewLocation(loc1.getRow()+1, loc1.getColumn()+1);
				loc2.setNewLocation(loc2.getRow() - 1, loc2.getColumn() + 1);
				loc4.setNewLocation(loc4.getRow() - 1, loc4.getColumn() - 1);
				direction++;
				placeInBoard();
			}
			else if(direction == 1){
				if(!loc1.newLocationValid(loc1.getRow()-1, loc1.getColumn()+1,board) ||
						!loc2.newLocationValid(loc2.getRow()-1, loc2.getColumn()-1,board) ||
						!loc4.newLocationValid(loc4.getRow()+1, loc4.getColumn()-1,board)){
					placeInBoard();
					return;
				}
				clear();
				loc1.setNewLocation(loc1.getRow()-1, loc1.getColumn()+1);
				loc2.setNewLocation(loc2.getRow()-1, loc2.getColumn()-1);
				loc4.setNewLocation(loc4.getRow()+1, loc4.getColumn()-1);
				direction++;
				placeInBoard();
			}
			else if(direction == 2){
				if(!loc1.newLocationValid(loc1.getRow()-1, loc1.getColumn()-1,board) ||
						!loc2.newLocationValid(loc2.getRow() +1, loc2.getColumn() -1,board) ||
						!loc4.newLocationValid(loc4.getRow() +1, loc4.getColumn() +1,board)){
					placeInBoard();
					return;
				}
				clear();
				loc1.setNewLocation(loc1.getRow()-1, loc1.getColumn()-1);
				loc2.setNewLocation(loc2.getRow() +1, loc2.getColumn() -1);
				loc4.setNewLocation(loc4.getRow() +1, loc4.getColumn() +1);
				direction++;
				placeInBoard();
			}
			else if(direction == 3){
				if(!loc1.newLocationValid(loc1.getRow()+1, loc1.getColumn()-1,board) ||
						!loc2.newLocationValid(loc2.getRow()+1, loc2.getColumn()+1,board) ||
						!loc4.newLocationValid(loc4.getRow()-1, loc4.getColumn()+1,board)){
					placeInBoard();
					return;
				}
				clear();
				loc1.setNewLocation(loc1.getRow()+1, loc1.getColumn()-1);
				loc2.setNewLocation(loc2.getRow()+1, loc2.getColumn()+1);
				loc4.setNewLocation(loc4.getRow()-1, loc4.getColumn()+1);
				direction=0;
				placeInBoard();
			}
		}
		else if(color == 7){
			if(direction == 0){
				if(!loc1.newLocationValid(loc1.getRow()-2, loc1.getColumn(),board) ||
						!loc2.newLocationValid(loc2.getRow() - 1, loc2.getColumn() + 1,board) ||
						!loc4.newLocationValid(loc4.getRow() +1, loc4.getColumn() + 1,board)){
					placeInBoard();
					return;
				}
				clear();
				loc1.setNewLocation(loc1.getRow()-2, loc1.getColumn());
				loc2.setNewLocation(loc2.getRow() - 1, loc2.getColumn() + 1);
				loc4.setNewLocation(loc4.getRow() + 1, loc4.getColumn() + 1);
				direction++;
				placeInBoard();
			}
			else if(direction == 1){
				if(!loc1.newLocationValid(loc1.getRow()+2, loc1.getColumn(),board) ||
						!loc2.newLocationValid(loc2.getRow()+1, loc2.getColumn()-1,board) ||
						!loc4.newLocationValid(loc4.getRow()-1, loc4.getColumn()-1,board)){
					placeInBoard();
					return;
				}
				clear();
				loc1.setNewLocation(loc1.getRow()+2, loc1.getColumn());
				loc2.setNewLocation(loc2.getRow()+1, loc2.getColumn()-1);
				loc4.setNewLocation(loc4.getRow()-1, loc4.getColumn()-1);
				direction=0;
				placeInBoard();
			}
		}
	}
	
	//checks if the piece can be shifted to the right by one space
	public boolean canShiftRight(){
		if(loc1.getColumn() == board.getWidth()-1 || loc2.getColumn() == board.getWidth()-1|| loc3.getColumn() == board.getWidth()-1
				|| loc4.getColumn() == board.getWidth()-1){
			return false;
		}
		//gets all of the rightmost locations of the piece
		ArrayList<Location> rightLocs = getRightLocs();
		for(int i = 0; i < rightLocs.size(); i++){
			if(board.getValue(rightLocs.get(i).getRow(), rightLocs.get(i).getColumn() + 1) !=0){
				return false;
			}
			
		}
		return true;
	}
	//shifts the piece one spot to the right
	public void shiftRight(){
		if(!canShiftRight()){
			return;
		}
		clear();
		loc1.setNewLocation(loc1.getRow(), loc1.getColumn()+1);
		loc2.setNewLocation(loc2.getRow(), loc2.getColumn()+1);
		loc3.setNewLocation(loc3.getRow(), loc3.getColumn()+1);
		loc4.setNewLocation(loc4.getRow(), loc4.getColumn()+1);
		placeInBoard();
	}
	//checks if the piece can be shifted to the left by one space
	public boolean canShiftLeft(){
		if(loc1.getColumn() == 0 || loc2.getColumn() == 0|| loc3.getColumn() == 0
				|| loc4.getColumn() == 0){
			return false;
		}
		//gets all of the rightmost locations of the piece
		ArrayList<Location> leftLocs = getLeftLocs();
		for(int i = 0; i < leftLocs.size(); i++){
			if(board.getValue(leftLocs.get(i).getRow(), leftLocs.get(i).getColumn() - 1) !=0){
				return false;
			}
			
		}
		return true;
	}
	//shifts the piece one spot to the right
	public void shiftLeft(){
		if(!canShiftLeft()){
			return;
		}
		
		clear();
		loc1.setNewLocation(loc1.getRow(), loc1.getColumn()-1);
		loc2.setNewLocation(loc2.getRow(), loc2.getColumn()-1);
		loc3.setNewLocation(loc3.getRow(), loc3.getColumn()-1);
		loc4.setNewLocation(loc4.getRow(), loc4.getColumn()-1);
		placeInBoard();
	}
	//finds all the rightmost locations of the piece
	public ArrayList<Location> getRightLocs(){
		ArrayList<Location> rightLocs = new ArrayList<Location>();
		rightLocs.add(loc1);
		rightLocs.add(loc2);
		rightLocs.add(loc3);
		rightLocs.add(loc4);
		if((loc1.getRow()  == loc2.getRow() && loc1.getColumn() + 1== loc2.getColumn()) || 
				(loc1.getRow()  == loc3.getRow() && loc1.getColumn() + 1== loc3.getColumn()) || 
				(loc1.getRow()  == loc4.getRow() && loc1.getColumn() + 1== loc4.getColumn())){
					rightLocs.remove(loc1);
		}
		if((loc2.getRow()  == loc1.getRow() && loc2.getColumn() + 1== loc1.getColumn()) || 
				(loc2.getRow()  == loc3.getRow() && loc2.getColumn()+ 1 == loc3.getColumn()) || 
				(loc2.getRow()  == loc4.getRow() && loc2.getColumn() + 1== loc4.getColumn())){
					rightLocs.remove(loc2);
		}
		if((loc3.getRow()  == loc2.getRow() && loc3.getColumn()+ 1 == loc2.getColumn()) || 
				(loc3.getRow()  == loc1.getRow() && loc3.getColumn()+ 1 == loc1.getColumn()) || 
				(loc3.getRow()  == loc4.getRow() && loc3.getColumn()+ 1 == loc4.getColumn())){
					rightLocs.remove(loc3);
		}
		if((loc4.getRow()  == loc2.getRow() && loc4.getColumn()+ 1 == loc2.getColumn()) || 
				(loc4.getRow()  == loc3.getRow() && loc4.getColumn()+ 1 == loc3.getColumn()) || 
				(loc4.getRow() == loc1.getRow() && loc4.getColumn()  + 1== loc1.getColumn())){
					rightLocs.remove(loc4);
		}
		return rightLocs;
	}
	//finds all the leftmost locations of the piece
	public ArrayList<Location> getLeftLocs(){
		ArrayList<Location> leftLocs = new ArrayList<Location>();
		leftLocs.add(loc1);
		leftLocs.add(loc2);
		leftLocs.add(loc3);
		leftLocs.add(loc4);
		if((loc1.getRow()  == loc2.getRow() && loc1.getColumn() - 1== loc2.getColumn()) || 
				(loc1.getRow()  == loc3.getRow() && loc1.getColumn() - 1== loc3.getColumn()) || 
				(loc1.getRow()  == loc4.getRow() && loc1.getColumn()- 1== loc4.getColumn())){
					leftLocs.remove(loc1);
		}
		if((loc2.getRow()  == loc1.getRow() && loc2.getColumn() - 1== loc1.getColumn()) || 
				(loc2.getRow()  == loc3.getRow() && loc2.getColumn()- 1 == loc3.getColumn()) || 
				(loc2.getRow()  == loc4.getRow() && loc2.getColumn() - 1== loc4.getColumn())){
					leftLocs.remove(loc2);
		}
		if((loc3.getRow()  == loc2.getRow() && loc3.getColumn()- 1 == loc2.getColumn()) || 
				(loc3.getRow()  == loc1.getRow() && loc3.getColumn()- 1 == loc1.getColumn()) || 
				(loc3.getRow()  == loc4.getRow() && loc3.getColumn()- 1 == loc4.getColumn())){
					leftLocs.remove(loc3);
		}
		if((loc4.getRow()  == loc2.getRow() && loc4.getColumn()- 1 == loc2.getColumn()) || 
				(loc4.getRow()  == loc3.getRow() && loc4.getColumn()- 1 == loc3.getColumn()) || 
				(loc4.getRow() == loc1.getRow() && loc4.getColumn()  - 1== loc1.getColumn())){
					leftLocs.remove(loc4);
		}
		return leftLocs;
	}
	//finds the bottom-most locations of the piece
	public ArrayList<Location> getBottomLocs(){
		ArrayList<Location> bottomLocs = new ArrayList<Location>();
		bottomLocs.add(loc1);
		bottomLocs.add(loc2);
		bottomLocs.add(loc3);
		bottomLocs.add(loc4);
		if((loc1.getRow() + 1 == loc2.getRow() && loc1.getColumn() == loc2.getColumn()) || 
				(loc1.getRow() + 1 == loc3.getRow() && loc1.getColumn() == loc3.getColumn()) || 
				(loc1.getRow() + 1 == loc4.getRow() && loc1.getColumn() == loc4.getColumn())){
					bottomLocs.remove(loc1);
		}
		if((loc2.getRow() + 1 == loc1.getRow() && loc2.getColumn() == loc1.getColumn()) || 
				(loc2.getRow() + 1 == loc3.getRow() && loc2.getColumn() == loc3.getColumn()) || 
				(loc2.getRow() + 1 == loc4.getRow() && loc2.getColumn() == loc4.getColumn())){
					bottomLocs.remove(loc2);
		}
		if((loc3.getRow() + 1 == loc2.getRow() && loc3.getColumn() == loc2.getColumn()) || 
				(loc3.getRow() + 1 == loc1.getRow() && loc3.getColumn() == loc1.getColumn()) || 
				(loc3.getRow() + 1 == loc4.getRow() && loc3.getColumn() == loc4.getColumn())){
					bottomLocs.remove(loc3);
		}
		if((loc4.getRow() + 1 == loc2.getRow() && loc4.getColumn() == loc2.getColumn()) || 
				(loc4.getRow() + 1 == loc3.getRow() && loc4.getColumn() == loc3.getColumn()) || 
				(loc4.getRow() + 1 == loc1.getRow() && loc4.getColumn() == loc1.getColumn())){
					bottomLocs.remove(loc4);
		}
		return bottomLocs;
	}
	//sets all the locations of the piece to the default 0
	public void clear(){
		board.setValue(loc1.getRow(),loc1.getColumn(),0);
		board.setValue(loc2.getRow(),loc2.getColumn(),0);
		board.setValue(loc3.getRow(),loc3.getColumn(),0);
		board.setValue(loc4.getRow(),loc4.getColumn(),0);
	}
	//descends the piece by 1 spot
	public void descend(){
		clear();
		loc1.setNewLocation(loc1.getRow()+1, loc1.getColumn());
		loc2.setNewLocation(loc2.getRow()+1, loc2.getColumn());
		loc3.setNewLocation(loc3.getRow()+1, loc3.getColumn());
		loc4.setNewLocation(loc4.getRow()+1, loc4.getColumn());
		placeInBoard();
	}
	//getter returning location 1
	public Location getLoc1() {
		return loc1;
	}
	//setter setting the location 1 to a new location
	public void setLoc1(Location loc1) {
		this.loc1 = loc1;
	}
	//getter returning location 2
	public Location getLoc2() {
		return loc2;
	}
	//setter setting the location 2 to a new location
	public void setLoc2(Location loc2) {
		this.loc2 = loc2;
	}
	//getter returning location 3
	public Location getLoc3() {
		return loc3;
	}
	//setter setting the location 3 to a new location
	public void setLoc3(Location loc3) {
		this.loc3 = loc3;
	}
	//getter returning location 4
	public Location getLoc4() {
		return loc4;
	}
	//setter setting the location 4 to a new location
	public void setLoc4(Location loc4) {
		this.loc4 = loc4;
	}
	//checks if the piece can move/descend
	public boolean canMove(){
		if(loc1.getRow() == board.getLength() - 1 || loc2.getRow() == board.getLength() - 1 || loc3.getRow() == board.getLength() - 1
				|| loc4.getRow() == board.getLength() - 1){
			return false;
		}
		ArrayList<Location> bottomLocs = getBottomLocs();
		for(int i = 0; i < bottomLocs.size(); i++){
			if(board.getValue(bottomLocs.get(i).getRow() + 1, bottomLocs.get(i).getColumn()) != 0){
				return false;
			}
			
		}
		return true;
	}
	//getter returning the int corresponding to a color
	public int getColor(){
		return color;
	}
	//returns string representation of the piece by returning the 4 locations
	public String toString(){
		String string = "";
		string+= "Loc1: " + loc1 + "\n" 
				+ "Loc2: " + loc2 + "\n" 
				+ "Loc3: " + loc3 + "\n" 
				+ "Loc4: " + loc4 + "\n" ;
		return string;
	}
	
}
