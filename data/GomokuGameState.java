package lab4.data;

import java.util.Observable;
import java.util.Observer;

import lab4.client.GomokuClient;

/**
 * @author Viktor and Jonas
 * Represents the state of a game
 * Uses integer values to determine the state of the game and the appropriate actions that can take place.
 */

public class GomokuGameState extends Observable implements Observer{

   // Game variables
	private final int DEFAULT_SIZE = 15;
	private GameGrid gameGrid;
	
    //Possible game states
	private final int NOT_STARTED = 0;
	private final int MY_TURN = 1;
	private final int OTHER_TURN = 2;
	private final int FINISHED_GAME = 3;
	private int currentState;
	
	private GomokuClient client;
	
	private String message;

	/**
	 * 
	 * @param gc the Client of the game
	 * creates a new GameGrid with the default size,
	 * sets current state to not started.
	 */
	public GomokuGameState(GomokuClient gc){
		client = gc;
		client.addObserver(this);
		gc.setGameState(this);
		currentState = NOT_STARTED;
		gameGrid = new GameGrid(DEFAULT_SIZE);
	}
	/**
	 * 
	 * @return the Default size for the GUI JFrame settings.
	 */
	public int getDEFAULT_SIZE()
	{
		return DEFAULT_SIZE;
	}
	/**
	 * 
	 * @return the message to display on the messageLabel in the GUI.
	 */
	public String getMessageString(){
		return this.message;
	}
	
	/**
	 * 
	 * @return this gameGrid so that the GamePanel can be setup with the correct grid in the GUI.
	 */
	public GameGrid getGameGrid(){
		return this.gameGrid;
	}
	/**
	 * 
	 * @param x the coordinate
	 * @param y the coordinate
	 * will perform move if possible otherwise display a message that shows why that move can't be performed.
	 */

	public void move(int x, int y){
		if((this.currentState != this.MY_TURN))
		{
			if(this.currentState == this.NOT_STARTED)
			{
				this.message = "No game started";
			} 
			else if(this.currentState == this.OTHER_TURN)
			{
				this.message = "The opponents turn";
			} 
			else 
			{
				this.message = "Game finished";
			}
			ChangeNotify();
		} 
		else 
		{
			if(this.gameGrid.move(x, y, GameGrid.ME))
			{
				this.client.sendMoveMessage(x, y);
				if(this.gameGrid.isWinner(GameGrid.ME))
				{
					this.message = "Game finished you won";
					this.currentState = this.FINISHED_GAME;
				}
				else
				{
					this.message = "The opponents turn";
					this.currentState = this.OTHER_TURN;
				}
			} 
			else
			{
				this.message = "Invalid move";
			}
			ChangeNotify();
		}
	}
	/**
	 * Setup gamestate for a new game by clearing the grid, setting current state and displaying the message.
	 * also sends notification to the client aswell as to the GamePanel to repaint the board.
	 */

	public void newGame(){
		
		this.gameGrid.clearGrid();
		this.currentState = this.OTHER_TURN;
		this.message = "Opponents turn";
		client.sendNewGameMessage();
		ChangeNotify();
	}
	/**
	 * same as above only now you receive the new game from another client,
	 * clear grid, set state and display message aswell as the notify of repaint.
	 */

	public void receivedNewGame(){
		
		this.gameGrid.clearGrid();
		this.currentState = this.MY_TURN;
		this.message = "Your turn";
		ChangeNotify();
		
	}
	/**
	 * Opponent disconnects
	 * clear grid, set state and display message aswell as setChanged and notify observers.
	 */

	public void otherGuyLeft(){
		
		this.gameGrid.clearGrid();
		this.currentState = this.NOT_STARTED;
		this.message = "Opponent disconnected from the game";
		ChangeNotify();
	}
	/**
	 * Disconnect from game
	 * clear grid, set message and state, notify and send information to the client to disconnect.
	 */

	public void disconnect(){
		
		this.gameGrid.clearGrid();
		this.currentState = this.NOT_STARTED;
		this.message = "You disconnected from the game";
		ChangeNotify();
		client.disconnect();
	}
	/**
	 * 
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 * Perform a move for the opposing player and check if he has won.
	 */
	
	public void receivedMove(int x, int y){
		this.gameGrid.move(x, y, GameGrid.OTHER);
		if(this.gameGrid.isWinner(GameGrid.OTHER)){
			this.message = "The other player has won";
			this.currentState = this.FINISHED_GAME;
		}
		else
		{
			this.message = "Your turn";
			this.currentState = this.MY_TURN;
		}
		ChangeNotify();
	}
	/**
	 * what to do if this class gets notified of a change from someone it observs.
	 */
	
	public void update(Observable o, Object arg) {
		
		switch(client.getConnectionStatus()){
		case GomokuClient.CLIENT:
			message = "Game started, it is your turn!";
			currentState = MY_TURN;
			break;
		case GomokuClient.SERVER:
			message = "Game started, waiting for other player...";
			currentState = OTHER_TURN;
			break;
		}
		ChangeNotify();	
	}
	/**
	 * condensed the setChanged and notifyObservers to one function.
	 */
	private void ChangeNotify()
	{
		setChanged();
		notifyObservers();
	}
	
}
