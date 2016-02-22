package lab4.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import lab4.data.GameGrid;
import lab4.data.GomokuGameState;

/**
 * A panel providing a graphical view of the game board
 */

public class GamePanel extends JPanel implements Observer{

	/**
	 * 
	 */
	/**
	 * 
	 */
	private final int UNIT_SIZE = 20;
	private GameGrid grid;
	private GomokuGameState gameState;
	
	/**
	 * The constructor
	 * 
	 * @param grid The grid that is to be displayed
	 */
	public int getUNIT_SIZE()
	{
		return this.UNIT_SIZE;
	}
	
	public GamePanel(GameGrid grid){
		this.grid = grid;
		grid.addObserver(this);
		Dimension d = new Dimension(grid.getSize()*UNIT_SIZE+1, grid.getSize()*UNIT_SIZE+1);
		this.setMinimumSize(d);
		this.setPreferredSize(d);
		this.setBackground(Color.WHITE);
		this.setVisible(true);
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
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Color[] moveColor = new Color[] {Color.gray, Color.black, Color.red};
		for(int i = 0; i<grid.getSize(); i++)
		{
			for(int k = 0; k<grid.getSize(); k++)
			{
				g.setColor(Color.gray);
				g.fillRect(i*UNIT_SIZE, k*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
				g.setColor(Color.black);
				g.drawRect(i*UNIT_SIZE, k*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
				g.setColor(moveColor[grid.getLocation(i, k)]);
				g.fillRect(i*UNIT_SIZE+(UNIT_SIZE/4), k*UNIT_SIZE+(UNIT_SIZE/4), UNIT_SIZE/2, UNIT_SIZE/2);
			}
		}
	}
	
}
