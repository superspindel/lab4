package lab4;
import lab4.data.*;
import lab4.client.*;
import lab4.gui.*;

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
