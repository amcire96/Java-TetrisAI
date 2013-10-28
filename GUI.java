import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/*
 * Eric Ma
 * This class's purpose is to create a graphical user interface for the game of Tetris
 * The class has a JPanel, JFrame, Timer, JLabels, a Game of Tetris, and a Comp artificial intelligence algorithm
 */
public class GUI extends JComponent{
	private static JPanel labelPanel;
	private static JFrame frame;
	private static Game game = new Game();
	private static Comp comp;

	private static Timer timer;
	private static JLabel levelLabel = new JLabel("Level: " + 1);
	private static int levelCount;


	private static int score;
	private static JLabel scoreLabel = new JLabel("Score: " + 0);
	//getter returning the Game
	public static Game getGame() {
		return game;
	}
	//setter setting the Game to a new Game
	public static void setGame(Game game) {
		GUI.game = game;
	}
	
	//getter returning the JLabel scoreLabel
	public static JLabel getScoreLabel() {
		return scoreLabel;
	}
	//setter setting scoreLabel to another JLabel
	public static void setScoreLabel(JLabel scoreLabel) {
		GUI.scoreLabel = scoreLabel;
	}
	//getter returning the score
	public static int getScore() {
		return score;
	}
	//setter setting the score to a new score
	public static void setScore(int score) {
		GUI.score = score;
	}
	//getter returning the JLabel levelLabel
	public static JLabel getLevelLabel() {
		return levelLabel;
	}
	//setter setting the levelLabel to a new JLabel
	public static void setLevelLabel(JLabel levelLabel) {
		GUI.levelLabel = levelLabel;
	}

	
	//getter returning the levelCount
	public static int getLevelCount() {
		return levelCount;
	}
	//setter setting levelCount to a new levelCount
	public static void setLevelCount(int levelCount) {
		GUI.levelCount = levelCount;
	}
	
	//called every time the interval of the Timer passes
	//the method either cycles when the user plays manually or tells the Comp algorithm to make a decision
	private static ActionListener actionListener = new ActionListener() {
	   	public void actionPerformed(ActionEvent actionEvent) {
	   	//	game.cycle();
	   		
	   		comp.chooseAction();
	   		
	   		//if the piece is illegal, the game is over and reset 
			if(game.getPiece().getIllegal()){
				System.out.println(game.getTetrisCount());
				//loseGameMessage();
				reset();
			}
	   	}
	 };
	    
	/*
	 * Instantiates the JFrame, makes it non-resizable, sets the title, and adds the MouseListener
	 */
	public static void frameMaker(){
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Tetris");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		labelPanel = new JPanel();
		frame.add(labelPanel,BorderLayout.SOUTH);
		labelPanel.add(levelLabel,BorderLayout.EAST);
		labelPanel.add(scoreLabel,BorderLayout.WEST);
	}
	
	/*
	 * Calls the class default constructor, adds it to the frame, resizes the frame,
	 * 	sets the frame visible and makes it non-resizable
	 */
	public static void boardMaker(){
		GUI component = new GUI();
		frame.add(component);
		frame.setSize(250, 660);
		frame.setVisible(true);
		frame.setResizable(false);
		//adds a KeyListener to detect if a key is pressed
		frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                arrowDetection(evt);
            }
        });
	}
	//makes Timer with an initial delay of 100 milliseconds
	public static void timerMaker(){
		timer = new Timer(100,actionListener);
	}
	//getter returning the Timer
	public static Timer getTimer(){
		return timer;
	}
	//tells the game what to do when each arrow key is pressed
	public static void arrowDetection(KeyEvent evt){
		  switch (evt.getKeyCode()) {
		  //piece is moved to the bottom
          case KeyEvent.VK_DOWN:
              while(game.getPiece().canMove()){
            	  game.getPiece().descend();
              }
              break;
          //piece is rotated
          case KeyEvent.VK_UP:
              game.getPiece().rotate();
              break;
          //piece is shifted left
          case KeyEvent.VK_LEFT:
              game.getPiece().shiftLeft();
              break;
          //piece is shifted right
          case KeyEvent.VK_RIGHT:
              game.getPiece().shiftRight();
              break;
      }
	//	  timer.stop();
		  
	//	  timer.start();
	}
	//draws the board and fills the filled locations with the respective colors of their pieces
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.black);
		for(int row = 0; row < game.getBoard().getLength(); row++){
			for(int col = 0; col < game.getBoard().getWidth(); col++){
				Rectangle rec = new Rectangle(col *23,row*23,23,23);
				
				if(game.getBoard().getValue(row, col) == 1){
					g2.setColor(Color.cyan);
					g2.fill(rec);
				}
				else if(game.getBoard().getValue(row, col) == 2){
					g2.setColor(Color.blue);
					g2.fill(rec);
				}
				else if(game.getBoard().getValue(row, col) == 3){
					g2.setColor(Color.orange);
					g2.fill(rec);
				}
				else if(game.getBoard().getValue(row, col) == 4){
					g2.setColor(Color.yellow);
					g2.fill(rec);
				}
				else if(game.getBoard().getValue(row, col) == 5){
					g2.setColor(Color.green);
					g2.fill(rec);
				}
				else if(game.getBoard().getValue(row, col) == 6){
					g2.setColor(Color.pink);
					g2.fill(rec);
				}
				else if(game.getBoard().getValue(row, col) == 7){
					g2.setColor(Color.red);
					g2.fill(rec);
				}
				g2.setColor(Color.black);
				g2.draw(rec);
				repaint();
			}
		}
	//	System.out.println(game.getPiece());
	}
	
	//resets the game
	//the board is reset, the level count and score are reset, the timer is stopped, and the game is started again
	public static void reset(){
		resetBoard();
		levelCount = 1;
		game.setTetrisCount(0);
		score = 0;
		comp.setFirstTime(true);
		timer.stop();
		game.playGame();
	}
	
	//resets the board so all the cells have a value of 0
	public static void resetBoard(){
		Board gameBoard = game.getBoard();
		for(int i = 0; i < gameBoard.getLength(); i ++){
			for(int j = 0; j < gameBoard.getWidth(); j++){
				gameBoard.setValue(i, j, 0);
			}
		}
		levelCount = 0;
		levelLabel.setText("Level Count: " + 1);
		score = 0;
		scoreLabel.setText("Score: " + 0);
	}
	
	//a JOptionPane telling user when they lose
	//options are to replay or not
	public static void loseGameMessage(){
		String[] playAgain = {"Yes", "No"};
		int choiceNumber =  JOptionPane.showOptionDialog(frame, "You Lost \n" +
			//	"Time In Seconds: " + timeCount + "\n" +
			//	"Bombs Remaining: " + bombNumber + "\n" +
				"Play Again?", "YOU LOSE!",
				 JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, playAgain, "");
		if(choiceNumber == 0){
			reset();
		//	setIsFirstClick(true);
		}
		if(choiceNumber == 1){
			System.exit(0);
		}
	}
	//makes new Comp which computes the algorithm to play Tetris
	public static void compGenerator(){
		comp = new Comp();
	}
	//starts the GUI by making all the components
	public static void start(){
		resetBoard();
		frameMaker();
		boardMaker();
		timerMaker();
		compGenerator();
		game.playGame();
	}
	
	public static void main(String[] args){
		start();
	}
}
