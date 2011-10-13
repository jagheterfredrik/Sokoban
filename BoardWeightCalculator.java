import java.util.*;

public class BoardWeightCalculator {

	public static void calculateBoardWeight(Board board) {
		Board.BOARDWEIGHT = new int[board.width][board.height];

		for (int x = 0; x < board.width; ++x) {
			for (int y = 0; y < board.height; ++y) {
				if (board.board[x][y] == '#')
					Board.BOARDWEIGHT[x][y] = -1;
				else if (board.board[x][y] == '.' || board.board[x][y] == '*' || board.board[x][y] == '+')
					Board.BOARDWEIGHT[x][y] = 0;
				else
					Board.BOARDWEIGHT[x][y] = 10000;
			}
		}

		for (int x = 0; x < board.width; ++x) {
			for (int y = 0; y < board.height; ++y) {
				if (board.board[x][y] == '.' || board.board[x][y] == '+') {
					BFS(board, new BoardPos(x, y));
				}
			}
		}
	}

	private static void BFS(Board board, BoardPos boardPos) {
		HashSet<BoardPos> visited = new HashSet<BoardPos>();
		LinkedList<BoardPos> queue = new LinkedList<BoardPos>();

		visited.add(boardPos);
		queue.add(boardPos);
		boardPos.depth = 0;

		while (!queue.isEmpty()) {
			BoardPos bp = queue.pollFirst();

			for (BoardPos newBp : findIncidentBoardPosses(board, bp)) {

				if (visited.add(newBp)) {
					if(bp.depth < (Board.width + Board.height)/2)
						newBp.depth = bp.depth + 1;
					else
						newBp.depth = bp.depth + 1;
					queue.add(newBp);
				}

			}
		}

		for (BoardPos bp : visited) {
			if (bp.depth < Board.BOARDWEIGHT[bp.x][bp.y])
				Board.BOARDWEIGHT[bp.x][bp.y] = bp.depth;
		}
	}

	private static Vector<BoardPos> findIncidentBoardPosses(Board board,
			BoardPos bp) {
		Vector<BoardPos> ret = new Vector<BoardPos>();
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
