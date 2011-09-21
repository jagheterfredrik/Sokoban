import java.util.*;

public class Agent {
	boolean debug;
	HashSet<Board> visited;
	
	Agent(boolean d)
	{
		debug = d;
		visited = new HashSet<Board>();
	}
	
	public String solve(Board board, int depth) {
		if(debug)
			System.out.println("D: " + depth);
		
		if(depth == 0){
			return null;
		}
		
		if(visited.contains(board))
		{
			if(debug)
				System.out.println("VISITED");
			return null;
		}
		else
			visited.add(board);
		
		if(debug)
			System.out.println(board);
		
		if(debug)
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		if (board.unsolvedBoxes() == 0)
		{
			System.out.println("SOLUTION FOUND");
			return "";
		}
		
		Vector<Character> moves = board.findPossibleMoves();
		if(debug)
			System.out.println(moves);
		for(Character move : moves) {
			String res = solve(new Board(board, move), depth-1);
			if(res!=null)
				return move+res;
		}
		return null;
	}
	
	public static void printSolution(Board board,String sol)
	{
		Board b = board;
		System.out.println(b);
		for(int i = 0;i < sol.length(); ++i)
		{
			b = new Board(b, sol.charAt(i));
			System.out.println(b);
		}
	}
}
