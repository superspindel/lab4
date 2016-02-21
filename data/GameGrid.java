package lab4.data;

import java.util.Observable;

/**
 * Represents the 2-d game grid
 */

public class GameGrid extends Observable {

	public static int EMPTY;
	public static int ME;
	public static int OTHER;
	public static int player;
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
			ME = board[x][y];
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
			for (int j = 0; i < board[i].length; j++) {
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

				for (int i = 0; x + i < board.length && getLocation(x + i++, y) == player;) {
					counter++;
					if (counter == INROW) {
						return true;
					}
				}
				for (int i = 0; y + i < board[x].length && getLocation(x, y + i++) == player;) {
					counter++;
					if (counter == INROW) {
						return true;
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

				int z = 0;
				for (int i = 0; x + i < board.length && getLocation(x + i++, y + z++) == player;) {
					counter++;
					if (counter == INROW) {
						return true;
					}

				}
				z = 0;
				for (int i = 0; y + i < board[x].length && getLocation(x + i++, y - z++) == player;) {
					counter++;
					if (counter == INROW) {
						return true;
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
