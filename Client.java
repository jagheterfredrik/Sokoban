import java.io.*;
import java.util.*;
import java.net.*;

public class Client {

	public static void main(String[] pArgs) {
		if (pArgs.length < 3) {
			System.out.println("usage: java Client host port boardnum");
			return;
		}

		try {
			Socket lSocket = new Socket(pArgs[0], Integer.parseInt(pArgs[1]));
			PrintWriter lOut = new PrintWriter(lSocket.getOutputStream());
			BufferedReader lIn = new BufferedReader(new InputStreamReader(
					lSocket.getInputStream()));

			lOut.println(pArgs[2]);
			lOut.flush();

			Board board = new Board(lIn);

			/*
			String[] lol = new String[8];
			lol[0] =  "#######";
			lol[1] =  "#  .  #";
			lol[2] =  "#     #";
			lol[3] =  "#  .  #";
			lol[4] =  "#     #";
			lol[5] =  "#  $$ #";
			lol[6] =  "#@    #";
			lol[7] =  "#######";
			Board board = new Board(lol);
			*/
			
			System.out.println(board);
			//System.out.println(board.boardWeightToString());
			System.out.println(board.deadlocksToString());

			long time1 = System.nanoTime();
			String res = new Agent(false).solveAStar(board);
			double time2 = (double) (System.nanoTime() - time1);
			time2 = time2 / 1000 / 1000 / 1000;
			System.out.println("Solotion found in " + time2 + "s");

			//Agent.printSolution(board, res, 200);
			System.out.println(res);
			System.out.println("Solution lenght: " + res.length());

			// now, we should find a solution to the sokoban

			// we've found our solution (this is actually the solution to board
			// 1)
			// String
			// lMySol="U R R U U L D L L U L L D R R R R L D D R U R U D L L U R";
			// these formats are also valid:
			// String lMySol="URRUULDLLULLDRRRRLDDRURUDLLUR";
			// System.out.println(lMySol);
			// System.out.println(lMySol.length());
			// String
			// lMySol="0 3 3 0 0 2 1 2 2 0 2 2 1 3 3 3 3 2 1 1 3 0 3 0 1 2 2 0 3";

			// send the solution to the server
			lOut.println(res);
			lOut.flush();

			// read answer from the server
			String lLine = lIn.readLine();

			System.out.println(lLine);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
