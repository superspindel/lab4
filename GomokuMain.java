package lab4;
import lab4.data.*;
import lab4.client.*;
import lab4.gui.*;

/**
 * 
 * @author Viktor and Jonas
 * Try and get the values from the arguments and parse them to int values, if not possible then set standard values
 * setup two windows for a local client game, can use one of the windows for a online game or just comment out clientTwo.
 */
public class GomokuMain {
	public static void main(String[] args){ //Port Number as argument.
		int port1;
		int port2;
		try {
			port1 = Integer.parseInt(args[0]);
		}
		catch(Exception e){
			port1 = 5000;
		}
		try {
			port2 = Integer.parseInt(args[1]);
		}
		catch(Exception e){
			port2 = 5001;
		}
		GomokuClient clientOne = new GomokuClient(port1);
		GomokuGameState gameStateOne = new GomokuGameState(clientOne);
		GomokuGUI GUIone = new GomokuGUI(gameStateOne, clientOne);
		
		GomokuClient clientTwo = new GomokuClient(port2);
		GomokuGameState gameStateTwo = new GomokuGameState(clientTwo);
		GomokuGUI GUItwo = new GomokuGUI(gameStateTwo, clientTwo);
		
	}
	
}
