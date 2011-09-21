import java.util.Vector;

public class Agent {
	Vector<Integer> visited;
	
	public void solve(Board board, int depth) {
		if(depth == 0) return;
		if (visited == null)
			visited = new Vector<Integer>();
		if(visited.contains(board.hashCode())) {
			System.out.println("*cont: "+visited);
			return;
		} else {
			visited.add(board.hashCode());
		}
		System.out.println(board);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (board.unsolvedBoxes() == 0) System.out.println("SOLUTION FOUND");
		Vector<Integer> moves = board.findPossibleMoves();
		System.out.println(moves);
		for(Integer move : moves) {
			//System.out.println(new Board(board, move));
			solve(new Board(board, move), depth-1);
		}
	}
}
