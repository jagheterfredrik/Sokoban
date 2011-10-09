import java.util.*;

public class Agent {
	boolean debug;
	HashSet<Board> visited;

	Agent(boolean d)
	{
		debug = d;
		visited = new HashSet<Board>();
	}

	public String solveDFS(Board board, int depth) {
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
		//if(moves.size() > 0)
		//moves = randomize(moves);
		if(debug)
			System.out.println(moves);
		for(Character move : moves) {
			String res = solveDFS(new Board(board, move), depth-1);
			if(res!=null)
				return move+res;
		}
		return null;
	}

	public String solveBFS(Board board)
	{
		long time1 = System.nanoTime();
		board.BFSParent = null;
		Board b = board;

		if(debug)
			System.out.println(board);

		Board newBoard;
		LinkedList<Board> q = new LinkedList<Board>();
		q.addLast(b);
		visited.add(b);
		boolean sluta = false;
		while(!q.isEmpty())
		{
			b = q.pollFirst();
			Vector<Character> moves = b.findPossibleMoves();
			if(moves.size() == 0)
				continue;
			for(Character move : moves)
			{
				newBoard = new Board(b, move);

				if(debug){
					System.out.println(moves);
					System.out.println(newBoard);
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				if(visited.add(newBoard))
					q.addLast(newBoard);


				newBoard.BFSParent = b;
				newBoard.parentMove = move;		

				if(newBoard.unsolvedBoxes() == 0)
				{
					b = newBoard;
					sluta = true;
					break;
				}
			}

			if(debug)
			{
				System.out.println("Q: " + q.size());
				System.out.println("V: " + visited.size());
			}
			if(sluta)
				break;	
		}

		StringBuilder sb = new StringBuilder();

		while(b.BFSParent != null)
		{
			sb.insert(0, b.parentMove);
			b = b.BFSParent;
		}
		double time2 = (double) (System.nanoTime() - time1);
		time2 = time2 / 1000 / 1000 / 1000;
		
		//System.out.println("Solotion found in " + time2 + "s");
		
		return sb.toString();
	}


	private Vector<Character> randomize(Vector<Character> m)
	{
		Random rand = new Random();
		Vector<Character> randomized = new Vector<Character>();
		for(int i = m.size(); i > 0; --i)
		{
			int take = rand.nextInt(i);
			randomized.add(m.get(take));
			m.remove(take);
		}
		return randomized;
	}

	public static void printSolution(Board board,String sol, int waittime)
	{
		Board b = board;
		System.out.println(b.findPossibleMoves());
		System.out.println(b);
		for(int i = 0;i < sol.length(); ++i)
		{
			b = new Board(b, sol.charAt(i));
			System.out.println(i+1);
			System.out.println(b.findPossibleMoves());
			System.out.println(b);

			try {
				Thread.sleep(waittime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
