/*
package lab4.data;

import java.util.Observable;

public class GameGrid extends Observable{
	private int[][] gridArray;
	private int size;
	public static final int EMPTY = 0;
	public static final int ME = 1;
	public static final int OTHER = 2;
	public static int INROW = 5;
	

	public GameGrid(int size)
	{
		this.size = size;
		this.gridArray = new int[size][size];
		this.clearGrid();
	}

	public int getLocation(int x, int y){
		return this.gridArray[x][y];
	}
	

	public int getSize(){
		return this.size;
	}
	

	public boolean move(int x, int y, int player){
		if(this.gridArray[x][y] == EMPTY){
			this.gridArray[x][y] = player;
			ChangeNotify();
			return true;
		} 
		else 
		{
			return false;
		}
	}
	

	public void clearGrid()
	{
		for(int i = 0; i<this.size; i++)
		{
			for(int k = 0; k<this.size; k++)
			{
				this.gridArray[i][k] = EMPTY;
			}
		}
		ChangeNotify();
	}
	

	public boolean isWinner(int player)
	{
		for(int i = 0; i<this.size; i++)
		{
			for(int k = 0; k<this.size; k++){
				int winning = 0;
				if(getLocation(i, k) == player){
					for(int j = k; j<this.size; j++)
					{
						winning++;
						if(getLocation(i, j) != player)
						{
							break;
						}
						else if(winning == INROW)
						{
							return true;
						}
					}
					winning = 0;
					for(int p = i; p<this.size; p++)
					{
						winning++;
						if(getLocation(p, k) != player)
						{
							break;
						}
						else if(winning == INROW)
						{
							return true;
						}
					}
					int diagUpX = i;
					int diagUpY = k;
					winning = 0;
					while(diagUpX < this.size && diagUpY >= 0)
					{
						winning++;
						if(getLocation(diagUpX, diagUpY) != player)
						{
							break;
						}
						else if(winning == INROW)
						{
							return true;
						}
						diagUpX++;
						diagUpY--;
					}
					int diagDownX = i;
					int diagDownY = k;
					winning = 0;
					while(diagDownX < this.size && diagDownY < this.size)
					{
						winning++;
						if(getLocation(diagDownX, diagDownY) != player)
						{
							break;
						}
						else if(winning == INROW)
						{
							return true;
						}
						diagDownX++;
						diagDownY++;
					}
				}
			}
		}
		return false;
	}
	private void ChangeNotify()
	{
		setChanged();
		notifyObservers();
	}
	
	
}
*/

package lab4.data;

import java.util.Observable;

/**
 * Represents the 2-d game grid
 */

public class GameGrid extends Observable {

	public static int EMPTY = 0;
	public static int ME = 1;
	public static int OTHER = 2 ;
	private int INROW = 5;
	private int board[][];

	// private boolean draw;
	/**
	 * Constructor
	 * 
	 * @param size
	 *            The width/height of the game grid
	 */
	public GameGrid(int size) {
		board = new int[size][size];
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[x].length; y++) {
				board[x][y] = EMPTY;
			}

		}

	}

	/**
	 * Reads a location of the grid
	 * 
	 * @param x
	 *            The x coordinate
	 * @param y
	 *            The y coordinate
	 * @return the value of the specified location
	 */
	public int getLocation(int x, int y) {
		return board[x][y];
	}

	/**
	 * Returns the size of the grid
	 * 
	 * @return the grid size
	 */
	public int getSize() {
		return board.length;
	}

	/**
	 * Enters a move in the game grid
	 * 
	 * @param x
	 *            the x position
	 * @param y
	 *            the y position
	 * @param player
	 * @return true if the insertion worked, false otherwise
	 */
	public boolean move(int x, int y, int player) {

		if (board[x][y] != EMPTY) {
			return false;

		} else {
			board[x][y] = player;
			setChanged();
			notifyObservers();
		}
		return true;
	}

	/**
	 * Clears the grid of pieces
	 */
	public void clearGrid() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = EMPTY;
			}
		}
		setChanged();
		notifyObservers();
	}

	/**
	 * {@link #isWinner(int)}
	 * 
	 * @param player
	 * @return true if there's 5 in a row, horizontally or vertically, otherwise
	 *         false.
	 */
	private boolean isWinnerXY(int player) {
		int counter = 0;
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[x].length; y++) {
				
				counter = 0;
				for (int i = 0; x + i< board.length;) {
					if(board[x + i++][y] == player){
						counter++;
						if (counter == INROW) {
							return true;
						}
					}
					break;
				
				}
				counter = 0;
				for (int i = 0; y + i < board[x].length;) {
					if(board[x][y + i++] == player){
						counter++;
						if (counter == INROW) {
							return true;
						}
					}
					break;
				}
			}
		}
		return false;

	}

	/**
	 * checks if there's 5 in a row on the board diagonally
	 * {@link #isWinner(int)}
	 * 
	 * @param player
	 * @return true if there's 5 in a row diagonally, otherwise false.
	 */
	private boolean isWinnerDia(int player) {
		int counter = 0;
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[x].length; y++) {
				
				counter = 0;
				int z = 0;
				for (int i = 0; x + i < board.length && y + z < board[x].length;) {
					if(board[x + i++][y + z++] == player){
						counter++;
						if (counter == INROW) {
							return true;
						}
					}
					break;

				}
				counter = 0;
				z = 0;
				for (int i = 0; y + i < board[x].length && y + z < board[x].length;) {
					if(board[x + i++][y - z++] == player){
						counter++;
						if (counter == INROW) {
							return true;
						}
					}
					break;
					
				}
			}
		}
		return false;
	}

	/**
	 * Checks if a player has 5 in a row
	 * 
	 * @param player
	 *            the player to check for
	 * @return true if player has 5 in row, false otherwise
	 */
	public boolean isWinner(int player) {
		if (isWinnerXY(player) || isWinnerDia(player)) {
			return true;
		}
		return false;

	}
}
