import java.util.HashSet;
import java.util.LinkedList;
import java.util.Vector;


public class NearestBoxFinder {
	
	public static int calcNearBox(Board board) {
		
		BoardPos boardPos = new BoardPos(board.currX, board.currY);
		HashSet<BoardPos> visited = new HashSet<BoardPos>();
		LinkedList<BoardPos> queue = new LinkedList<BoardPos>();

		visited.add(boardPos);
		queue.add(boardPos);
		boardPos.depth = 0;

		while (!queue.isEmpty()) {
			BoardPos bp = queue.removeFirst();

			if(board.board[bp.x][bp.y] == '$')
			{
				//System.out.println(board);
				//System.out.println(bp.depth);
				return bp.depth;
			}
			
			for (BoardPos newBp : findIncidentBoardPosses(board, bp)) {

				if (visited.add(newBp)) {
					newBp.depth = bp.depth + 1;
					queue.add(newBp);
				}

			}
		}

		for (BoardPos bp : visited) {
			if (bp.depth < Board.BOARDWEIGHT[bp.x][bp.y])
				Board.BOARDWEIGHT[bp.x][bp.y] = bp.depth;
		}
		return 0;
	}

	private static Vector<BoardPos> findIncidentBoardPosses(Board board,
			BoardPos bp) {
		Vector<BoardPos> ret = new Vector<BoardPos>(4);
		int currX = bp.x;
		int currY = bp.y;

		// UP
		if (!(isBlocking(board.board[currX][currY - 1])))
			ret.add(new BoardPos(currX, currY - 1));
		// DOWN
		if (!(isBlocking(board.board[currX][currY + 1])))
			ret.add(new BoardPos(currX, currY + 1));
		// LEFT
		if (!(isBlocking(board.board[currX - 1][currY])))
			ret.add(new BoardPos(currX - 1, currY));
		// RIGHT
		if (!(isBlocking(board.board[currX + 1][currY])))
			ret.add(new BoardPos(currX + 1, currY));

		assert (ret.size() <= 4);
		return ret;
	}


	private static boolean isBlocking(char c) {
		if (c == '#')
			return true;
		
		if (c == '*')
			return true;
		
		return false;
	}

}
