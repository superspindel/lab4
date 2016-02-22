package lab4.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import lab4.data.GameGrid;

/**
 * @author Viktor and Jonas.
 * GamePanel is the board of the game. It uses a GameGrid to get the positions of all the
 * squares and then handles that information and transform it into a visual representation of the board.
 * It has a UNIT_SIZE that determines the size of the squares.
 */

public class GamePanel extends JPanel implements Observer{

	private final int UNIT_SIZE = 20;
	private GameGrid grid;
	
	/**
	 * In order to resize the JFrame in GomokoGUI we need the UNIT_SIZE.
	 * @return UNIT_SIZE
	 */
	
	public int getUNIT_SIZE()
	{
		return this.UNIT_SIZE;
	}
	
	/**
	 * The constructor
	 * 
	 * @param grid The grid that is to be displayed
	 */
	
	public GamePanel(GameGrid grid){
		this.grid = grid;
		grid.addObserver(this);
		Dimension d = new Dimension(grid.getSize()*UNIT_SIZE+1, grid.getSize()*UNIT_SIZE+1);
		this.setMinimumSize(d);
		this.setPreferredSize(d);
		this.setBackground(Color.WHITE);
	}

	/**
	 * Returns a grid position given pixel coordinates
	 * of the panel
	 * 
	 * @param x the x coordinates
	 * @param y the y coordinates
	 * @return an integer array containing the [x, y] grid position
	 */
	
	public int[] getGridPosition(int x, int y){
		return new int[] {x/UNIT_SIZE, y/UNIT_SIZE};
	}
	
	public void update(Observable arg0, Object arg1) {
		this.repaint();
	}
	
	/**
	 * @param g the Graphics tool.
	 * Paint rectangles as squares according to the grid and fill in the spot with the color of the marker in that spot.
	 * EMPTY square means a white rectangle that doesn't show, player ME equals black square and red equals player OTHER.
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Color[] playerColor = new Color[] {Color.white, Color.black, Color.red};
		for(int i = 0; i<grid.getSize(); i++)
		{
			for(int k = 0; k<grid.getSize(); k++)
			{
				g.setColor(Color.black);
				g.drawRect(i*UNIT_SIZE, k*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
				g.setColor(playerColor[grid.getLocation(i, k)]);
				g.fillRect(i*UNIT_SIZE+(UNIT_SIZE/4), k*UNIT_SIZE+(UNIT_SIZE/4), UNIT_SIZE/2, UNIT_SIZE/2);
			}
		}
	}
	
}
