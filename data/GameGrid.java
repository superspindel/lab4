package lab4.data;

import java.util.Observable;

/**
 * 
 * @author Jonas and Viktor
 * represents the 2D grid for the game Gomoku.
 * Has values for players that determine who has taken a square on the board.
 * Value INROW for how many squares in a row needs to be taken to win.
 *
 */

public class GameGrid extends Observable {

	public static final int EMPTY = 0;
	public static final int ME = 1;
	public static final int OTHER = 2;
	private int INROW = 5;
	private int board[][];
	/**
	 * Constructor
	 * 
	 * @param size of the gaming board which is the width and height of the board.
	 * will create a board and fill it with empty squares.
	 *       
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
	 * @param player is the player that wants to perform the move.
	 * @return true if the insertion worked, false otherwhise.
	 * If true, perform the move and setChanged and notifyObservers.
	 * 
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
				for (int i = 0; x + i < board.length;) {
					if(board[x + i++][y] == player){
						counter++;
						if (counter == INROW) 
						{
							return true;
						}
					} 
					else
					{
						break;
					}
				}
				counter = 0;
				for (int i = 0; y + i < board[x].length;) {
					if(board[x][y + i++] == player)
					{
						counter++;
						if (counter == INROW) {
							return true;
						}
					}
					else
					{
						break;
					}

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
				for (int i = 0; x + i < board.length && y + z < board.length;) {
					if(board[x + i++][y + z++] == player){
						counter++;
						if (counter == INROW) {
							return true;
						}
					}
					else
					{
						break;
					}

				}
				counter = 0;
				z = 0;
				for (int i = 0; y - z >= 0 && x+i < board.length;) {
					if(board[x + i++][y - z++] == player)
					{
						counter++;
						if (counter == INROW) {
							return true;
						}
					}
					else
					{
						break;
					}
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
