import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
/*
 * Eric Ma
 * This class calculates the algorithm that the computer can use to play the game of Tetris
 * The algorithm is calculated by analyzing each possible move and doing the move with the best score
 * The scores are calculated by multiplying coefficients/weights to 6 different factors
 * The factors are landing height, rows cleared, row transitions, column transitions, holes, and well sum
 */
public class Comp {
	//Robot simulates pressing the keys
	private Robot robot;
	private Board board;
	private Piece piece;
	private Game game;
	private Game bestGame;
	private Game endMethodGame;
	private boolean firstTime = true;
	
	//returns the robot
	public Robot getRobot() {
		return robot;
	}
	//getter returning the board
	public Board getBoard() {
		return board;
	}
	//getter returning the piece
	public Piece getPiece() {
		return piece;
	}
	//getter returning the game
	public Game getGame() {
		return game;
	}
	//getter returning the best game
	public Game getBestGame() {
		return bestGame;
	}
	//getter returning the endMethodGame
	public Game getEndMethodGame() {
		return endMethodGame;
	}

	//default constructor making a robot
	public Comp(){
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
	}
	//tells the robot to press the up key
	public void pressUpKey(){
		robot.keyPress(KeyEvent.VK_UP);
	}
	//tells the robot to press the down key
	public void pressDownKey(){
		robot.keyPress(KeyEvent.VK_DOWN);
	}
	//tells the robot to press the left key
	public void pressLeftKey(){
		robot.keyPress(KeyEvent.VK_LEFT);
	}
	//tells the robot to press the right key
	public void pressRightKey(){
		robot.keyPress(KeyEvent.VK_RIGHT);
	}
	//finds the height of the column before the piece is placed
	public int findInitialHeightOfCol(){
		int prevColHeight = 20;
		ArrayList<Location> bottomLocs = piece.getBottomLocs();
		for(Location loc:bottomLocs){
			int colNum = loc.getColumn();
			for(int rowNum = loc.getRow()+1; rowNum<board.getLength(); rowNum++){
				if(game.getBoard().getValue(rowNum, colNum)!=0){
					if(prevColHeight > rowNum){
						prevColHeight = rowNum;
					}
				}
			}
		}
		int height = 20-prevColHeight;
		return height;
	}
	//returns the height of the highest column that the piece landed in
	public int findLandingHeight(int prevColHeight){
//		System.out.println(prevColHeight);
//		System.out.println(piece.findHeight());
		return prevColHeight + (piece.findHeight());
	}
	//returns the number of rows cleared
	public int findRowsCleared(){
		int rowsCleared=0;
		for(int i = 0; i < board.getLength(); i++){
			if(board.checkTetris(i)){
				rowsCleared++;
			}
			
		}
		return rowsCleared;
	}
		
	//a row transition is when an unoccupied cell is next to an occupied cell
	//the edges are counted as occupied cells
	public int findRowTransitions(){
		ArrayList<Integer> rowsPartiallyFilled = new ArrayList<Integer>(0);
		for(int row = 0; row<board.getLength(); row++){
			boolean isPartiallyFilled = false;
			for(int col = 0; col< board.getWidth(); col++){
				if(board.getValue(row, col)!= 0){
					isPartiallyFilled = true;
				}
			}
			if(isPartiallyFilled){
				rowsPartiallyFilled.add(row);
			}
		}
		int rowTransitions=0;
		for(int row = board.getLength()-rowsPartiallyFilled.size(); row<board.getLength(); row++){
			for(int col = 0; col< board.getWidth(); col++){
				if(board.getValue(row, col)== 0){
					if(board.newLocationValid(row, col+1)){
						if(board.getValue(row,col+1)!=0){
							rowTransitions++;
						}
					}
					else{
						rowTransitions++;
					}
					if(col==0){
						rowTransitions++;
					}
				}
				else if(board.getValue(row,col)!=0){
					if(board.newLocationValid(row, col+1)){
						if(board.getValue(row, col+1)==0){
							rowTransitions++;
						}
					}
				}
			}
		}
		return rowTransitions;
	}
	
	//same as row transitions except vertically
	public int findColTransitions(){
//		ArrayList<Integer> colsPartiallyFilled = new ArrayList<Integer>(0);
//		for(int col = 0; col<board.getWidth(); col++){
//			boolean isPartiallyFilled = false;
//			for(int row = 0; row< board.getLength(); row++){
//				if(board.getValue(row, col)!= 0){
//					isPartiallyFilled = true;
//				}
//			}
//			if(isPartiallyFilled){
//				colsPartiallyFilled.add(col);
//			}
//		}
		
		int colTransitions=0;
		for(int col = 0; col<board.getWidth(); col++){
			boolean partiallyFilledCol = false;
			for(int row = board.getLength()-1; row>=0; row--){
				if(board.getValue(row, col)!=0){
					partiallyFilledCol = true;
				}
			}
			for(int row = board.getLength()-1; row>=0; row--){
				if(board.getValue(row, col)== 0){
					if(board.newLocationValid(row-1, col)){
						if(board.getValue(row-1,col)!=0){
							colTransitions++;
						}
					}
					if(partiallyFilledCol){
						if(row == board.getLength()-1){
							colTransitions++;
						}
					}
					
				}
//				else if(board.getValue(row,col)!=0){
//					if(board.newLocationValid(row-1, col)){
//						if(board.newLocationValid(row-1, col-1)
//								&& board.newLocationValid(row-1, col+1)){
//							if(board.getValue(row-1, col-1)!=0
//								&& board.getValue(row-1, col+1)!=0){
//								if(board.getValue(row-1, col)==0){
//									colTransitions++;
//								}
//								
//							}
//						}
//						else if(!board.newLocationValid(row-1, col-1)
//								&& board.newLocationValid(row-1, col+1)){
//							if(board.getValue(row-1, col+1)!=0){
//								if(board.getValue(row-1, col)==0){
//									colTransitions++;
//								}
//		
//							}
//						}
//						else if(board.newLocationValid(row-1, col-1)
//								&& !board.newLocationValid(row-1, col+1)){
//							if(board.getValue(row-1, col-1)!=0){
//								if(board.getValue(row-1, col)==0){
//									colTransitions++;
//								}
//		
//							}
//						}
//					}
				else if(board.getValue(row,col)!=0){
					if(board.newLocationValid(row-1, col)){
						if(board.getValue(row-1, col)==0){
							colTransitions++;
						}
					}
				}
			
			}
		}
		return colTransitions;
	}
	//a hole is an unoccupied cell with at least one occupied cell in top of it
	public int findHoleNum(){
		int holes = 0;
		for(int col = 0; col<board.getWidth(); col++){
			int lowestRowFilled = 20;
			for(int row = board.getLength()-1; row>=0; row--){
				if(board.getValue(row, col)!=0){
					if(row<lowestRowFilled){
						lowestRowFilled = row;
					}
				}
			}
			for(int row = board.getLength()-1; row>lowestRowFilled; row--){
				if(board.getValue(row, col)==0){
					holes ++;
				}
			}
		}
		return holes;
	}
	
	//a well consists of two or more holes directly on top of one another
	public int findWellSums(){
		int wellSum = 0;
		for(int col = 0; col<board.getWidth(); col++){
			int colWellSum = 0;
			for(int row = 0; row< board.getLength(); row++){
				
				if(board.getValue(row, col)==0){
						if(board.newLocationValid(row, col+1)
							&&board.newLocationValid(row, col-1)){
							
							if(board.getValue(row, col-1)!=0
									&& board.getValue(row, col+1)!=0){
								colWellSum++;
//								if(board.newLocationValid(row+2, col)){
//									if(board.getValue(row+1, col-1)!=0
//											&& board.getValue(row+1, col+1)!=0
//											&& board.getValue(row+2, col)==0){
//										
//									}
//								}
								
							}
						}
						
						else if(board.newLocationValid(row, col+1)
							&& !board.newLocationValid(row, col-1)){
							
							if(board.getValue(row, col+1)!=0){
								colWellSum++;
//								if(board.newLocationValid(row+2, col)){
//									if(board.getValue(row+1, col+1)!=0
//											&& board.getValue(row+2, col)==0){
//										
//									}
//								}
								
							}
						}
						
						else if(!board.newLocationValid(row, col+1)
								&& board.newLocationValid(row, col-1)){
								
								if(board.getValue(row, col-1)!=0){
									colWellSum++;
//									if(board.newLocationValid(row+2, col)){
//										if(board.getValue(row+1, col-1)!=0
//												&& board.getValue(row+2, col)==0){
//											
//										}
//									}
									
								}
							}

						}

			}
		//	System.out.println(colWellSum);
				if(colWellSum > 1){
					wellSum+=colWellSum;				
				}
		}
		return wellSum;
	}
	
	
	//chooses the actions of the algorithm
	//calculates the scores of each possible move and performs that move
	public void chooseAction(){
		
	//	System.out.println("GUI beginning of method: \n"+GUI.getGame());
	//	System.out.println("beginning of method: \n"+game);
		if(firstTime){
			game = GUI.getGame().copyGame();
			board = game.getBoard();
			piece = game.getPiece();
			piece.setBoard(board);			
		}
		//default terrible value
		double bestEvaluation = -100000000;
		int bestColumn = 0;
		int bestOrientation = 0;
		//goes through each orientation
		for(int orientation = 0; orientation <4; orientation++){
			
			//goes through each column for the piece
			for(int col= 0; col< board.getWidth(); col++){
				if(firstTime){
					game = GUI.getGame().copyGame();
					board = game.getBoard();
					piece = game.getPiece();
					piece.setBoard(board);			
				}
				else{
						game =endMethodGame.copyGame();
						board = game.getBoard();
						piece = game.getPiece();
						piece.setBoard(board);			
					
				}
				
		//		System.out.println("start loop \n"+game);
		//		System.out.println(piece);
				piece.descend();
				//rotates the piece according to the orientation
				for(int o = 0; o<orientation;o++){
					piece.rotate();
				}
				//moves the piece to the left according to the col number
				if(col < 5){
					for(int c = 5; c>col; c--){
						piece.shiftLeft();
						
					}
				}
				//moves the piece to the right according to the col number
				else if(col>5){
					for(int c = 5; c<col;c++){
						piece.shiftRight();
					}
				}
				int prevColHeight = findInitialHeightOfCol();
				//piece is descended
				while(piece.canMove()){
	            	  piece.descend();
	             }
				//each factor is calculated
				int landingHeight = findLandingHeight(prevColHeight);
				int rowsCleared = findRowsCleared();
				int rowTransitions = findRowTransitions();
				int colTransitions = findColTransitions();
				int holeNum = findHoleNum();
				int wellSums = findWellSums();
//				System.out.println(board);
//				System.out.println(piece);
//				System.out.println("Col: "+col);
//				System.out.println("Orientation: "+orientation);
//				System.out.println("landing height: " + landingHeight);
//				System.out.println("rows cleared: " + rowsCleared);
//				System.out.println("row transitions: " + rowTransitions);
//				System.out.println("col transitions: " + colTransitions);
//				System.out.println("holes: " + holeNum);
//				System.out.println("well sum: " + wellSums);
//				System.out.println(board);
				//calculates the score
				//I'm still seeing if I can change the coefficients to get better results
				double evaluation = -4.500158825082766 * landingHeight +
						3.4181268101392694 * rowsCleared +
						-3.2178882868487753 * rowTransitions +
						-9.348695305445199 * colTransitions +
						-7.899265427351652 * holeNum +
						-9 * wellSums;
				
				//		-3.3855972247263626 * wellSums;	
				//		-7*wellSums;
				//sees if evaluation is better than the best one so far and if so, stores the board, column, and orientation 
				if(evaluation > bestEvaluation){
					bestEvaluation = evaluation;
					bestColumn = col;
					bestOrientation = orientation;
					bestGame = game.copyGame();
				}
				//erases the piece
				game.erasePiece();
//				System.out.println("evaluation:" + evaluation);
//				System.out.println("best eval: " + bestEvaluation  + "\n");				
			}
			
			
		}
		//best move is performed
		performMove(bestColumn, bestOrientation);
		
		
		endMethodGame = GUI.getGame().copyGame();
		board = endMethodGame.getBoard();
		piece = endMethodGame.getPiece();
		piece.setBoard(board);

		game = endMethodGame.copyGame();
		firstTime = false;		
	}
	//getter returning whether the game is in the first iteration
	public boolean isFirstTime() {
		return firstTime;
	}
	//setter setting whether game is in first iteration
	public void setFirstTime(boolean firstTime) {
		this.firstTime = firstTime;
	}
	//sets the game to the best game
	public void performMove(int bestColumn, int bestOrientation){
		GUI.setGame(bestGame);
		
		
			
		
		
		
		
	//	robot.delay(GUI.getTimer().getDelay());
//		for(int o = 0; o<bestOrientation;o++){
//			pressUpKey();
//	//		System.out.println("up");
//		}		
//		if(bestColumn < 5){
//			for(int c = 5; c>bestColumn; c--){
//				pressLeftKey();
//	//			System.out.println("left");
//			}
//		}
//		else if(bestColumn>5){
//			for(int c = 5; c<bestColumn;c++){
//				pressRightKey();
//	//			System.out.println("right");
//			}
//		}

	//	System.out.println("down");
	//	System.out.println("final decision: \n" + GUI.getGame());
		GUI.getGame().cycle();
	}
	
//	public void testMethod(){
//		game = GUI.getGame().copyGame();
//		board = game.getBoard();
//		piece = game.getPiece();
//		piece.setBoard(board);
//		GUI.getGame().getPiece().descend();
//		pressLeftKey();
//		pressUpKey();
//
//		
//		System.out.println("board in GUI: \n" + GUI.getGame().getBoard());
//		System.out.println(board);
//		game.erasePiece();
//	}
	
}
