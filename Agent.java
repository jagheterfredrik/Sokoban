import java.util.*;

public class Agent {
	boolean debug;
	HashSet<Board> visited;

	Agent(boolean d)
	{
		debug = d;
		visited = new HashSet<Board>();
	}

	class IdaSolution {
		public IdaSolution(int costLimit, String solution) {
			this.costLimit = costLimit;
			this.solution = solution;
		}
		public int costLimit;
		public String solution;
	}
	
	public String solveAStar(Board start){
		int deadCount = 0;
		
		
		PriorityQueue<Board> priorityQueue = new PriorityQueue<Board>();
		priorityQueue.add(start);
		
		start.gScore = 0;
		start.hScore = start.score();
		
		while(!priorityQueue.isEmpty())
		{
			Board x = priorityQueue.poll();
			if(x.unsolvedBoxes() == 0)
			{
				StringBuilder sb = new StringBuilder();

				while(x.BFSParent != null)
				{
					sb.insert(0, x.parentMove);
					x = x.BFSParent;
				}
				
				//System.out.println("Nr of deadlocks: " + deadCount);
				return sb.toString();
			}
			
			
			if(x.hasDeadlock()){
				deadCount++;
				//System.out.println("DEADLOCK");
				continue;
			}
			
			
			visited.add(x);
			for(Character c : x.findPossibleMoves()){
				Board y = new Board(x, c);
				
				if(visited.contains(y))
					continue;
				
				y.gScore = x.gScore + 1;
				y.hScore = y.score();
				
				if(!priorityQueue.contains(y)){
					priorityQueue.add(y);
				}
				else{
					continue;
				}
				y.BFSParent = x;
				y.parentMove = c;
			}
		}
		return "";
	}
	

	public String solveIDAStar(Board board) {
		int costLimit = board.score();
		System.out.println(""+costLimit);
		IdaSolution ir;
		while(true){

			ir = idaDFS(board, 0, costLimit);
			if(ir.solution != null)
				return ir.solution;

			if(ir.costLimit == Integer.MAX_VALUE)
				return null;
			
			costLimit = ir.costLimit;
			System.out.printf("cost limit is %d\n", costLimit);
		}
	}


	public IdaSolution idaDFS(Board board, int startCost, int costLimit) {
		int minimumCost = startCost + board.score();
		if(minimumCost > costLimit) return new IdaSolution(minimumCost, null);
		if(board.unsolvedBoxes() == 0) return new IdaSolution(startCost, "");

		int nextLimit = Integer.MAX_VALUE;
		for(Character move : board.findPossibleMoves()) {
			Board newBoard = new Board(board, move);
			visited.add(newBoard);
			int newCost = startCost + newBoard.score();
			IdaSolution res = idaDFS(newBoard, newCost, costLimit);
			if(res.solution != null) {
				
				return new IdaSolution(res.costLimit, move + res.solution);
			}
			nextLimit = Math.min(nextLimit, res.costLimit);
		}
		return new IdaSolution(nextLimit, null);
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
		board.BFSParent = null;
		Board b = board;

		if(debug)
			System.out.println(board);

		Board newBoard;
		LinkedList<Board> q = new LinkedList<Board>();
		q.addLast(b);
		visited.add(b);
		boolean stop = false;
		while(!q.isEmpty())
		{
			b = q.pollFirst();
			Vector<Character> moves = b.findPossibleMoves();
			if(moves.size() == 0)
				continue;
			for(Character move : moves)
			{
				newBoard = new Board(b, move);

				if(newBoard.hasDeadlock())
				{
					visited.add(newBoard);
					continue;
				}
				
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
					stop = true;
					break;
				}
			}

			if(debug)
			{
				System.out.println("Q: " + q.size());
				System.out.println("V: " + visited.size());
			}
			if(stop)
				break;
		}

		StringBuilder sb = new StringBuilder();

		while(b.BFSParent != null)
		{
			sb.insert(0, b.parentMove);
			b = b.BFSParent;
		}		
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
			
			if(b.hasDeadlock())
				System.out.println("DEADLOCK");
			
			System.out.println(i+1 + "/" + sol.length());
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
