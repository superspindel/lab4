package lab4;
import lab4.data.*;
import lab4.client.*;
import lab4.gui.*;

public class GomokuMain {
	static int standartPort = 4000;
	static int usedPort;
	public static void main(String[] args){
		if(args.length > 1){
			// to long
			System.out.println("to many arguments");
		} else if(args.length < 1){
			// no port argument
			usedPort = standartPort;
		} else {
			// exactly one port argument
			usedPort = Integer.parseInt(args[0]);
		}
		GomokuClient gmClient = new GomokuClient(usedPort);
		GomokuGameState gmState = new GomokuGameState(gmClient);
		GomokuGUI gmGUI = new GomokuGUI(gmState, gmClient);
	}
}
