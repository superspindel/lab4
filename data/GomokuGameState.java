/*
 * Created on 2007 feb 8
 */
package lab4.data;

import java.util.Observable;
import java.util.Observer;

import lab4.client.GomokuClient;

/**
 * Represents the state of a game
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
	 * The constructor
	 * 
	 * @param gc The client used to communicate with the other player
	 */
	public GomokuGameState(GomokuClient gc){
		client = gc;
		client.addObserver(this);
		gc.setGameState(this);
		currentState = NOT_STARTED;
		gameGrid = new GameGrid(DEFAULT_SIZE);
	}
	
	public int getDEFAULT_SIZE()
	{
		return DEFAULT_SIZE;
	}
	/**
	 * Returns the message string
	 * 
	 * @return the message string
	 */
	public String getMessageString(){
		return this.message;
	}
	
	/**
	 * Returns the game grid
	 * 
	 * @return the game grid
	 */
	public GameGrid getGameGrid(){
		return this.gameGrid;
	}

	/**
	 * This player makes a move at a specified location
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
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
	 * Starts a new game with the current client
	 */
	public void newGame(){
		
		this.gameGrid.clearGrid();
		this.currentState = this.OTHER_TURN;
		this.message = "Opponents turn";
		client.sendNewGameMessage();
		ChangeNotify();
	}
	
	/**
	 * Other player has requested a new game, so the 
	 * game state is changed accordingly
	 */
	public void receivedNewGame(){
		
		this.gameGrid.clearGrid();
		this.currentState = this.MY_TURN;
		this.message = "Your turn";
		ChangeNotify();
		
	}
	
	/**
	 * The connection to the other player is lost, 
	 * so the game is interrupted
	 */
	public void otherGuyLeft(){
		
		this.gameGrid.clearGrid();
		this.currentState = this.NOT_STARTED;
		this.message = "Opponent disconnected from the game";
		ChangeNotify();
	}
	
	/**
	 * The player disconnects from the client
	 */
	public void disconnect(){
		
		this.gameGrid.clearGrid();
		this.currentState = this.NOT_STARTED;
		this.message = "You disconnected from the game";
		ChangeNotify();
		client.disconnect();
	}
	
	/**
	 * The player receives a move from the other player
	 * 
	 * @param x The x coordinate of the move
	 * @param y The y coordinate of the move
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
	private void ChangeNotify()
	{
		setChanged();
		notifyObservers();
	}
	
}
