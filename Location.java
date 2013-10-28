/*
 * Eric Ma
 * 6/3/12
 * This Location class stores two ints, a row and a column of a location or spot of a board/grid
 */
public class Location {
	//private class variables are the location's row and column ints
	private int row;
	private int column;
	//constructor takes in the location's row and column ints
	public Location(int row, int column){
		this.row = row;
		this.column = column;
	}
	//returns a copy of a Location
	public Location copyLocation(){
		return new Location(row,column);
	}
	
	//getter that returns the row number
	public int getRow(){
		return row;
	}
	
	//getter that returns the column number
	public int getColumn(){
		return column;
	}
	//setter that sets new row number
	public void setRow(int newRow){
		row = newRow;
	}
	//setter that sets new column number
	public void setColumn(int newCol){
		column = newCol;
	}
	//checks if location given is in the board
	public boolean newLocationValid(int newRow, int newCol, Board board){
		if(newRow>=board.getLength() || newRow<0 || newCol >=board.getWidth() || newCol<0){
			return false;
		}
		if(board.getValue(newRow, newCol) != 0){
			return false;
		}
		return true;
	}
	//sets location given a new row and new column
	public boolean setNewLocation(int newRow, int newCol){
		row = newRow;
		column = newCol;
		
		return true;
	}
	//returns a String representation of the location by having the row comma column
	public String toString(){
		return "(" + row + "," + column + ")";
	}
}
