import java.util.*;

public class BoardWeightCalculator {

	public static void calculateBoardWeight(Board board) {
		Board.BOARDWEIGHT = new int[board.width][board.height];

		for (int x = 1; x < board.width-1; ++x) {
			for (int y = 1; y < board.height-1; ++y) {
				if (board.board[x][y] == '#')
					Board.BOARDWEIGHT[x][y] = -1;
				else if (board.board[x][y] == '.' || board.board[x][y] == '*' || board.board[x][y] == '+')
					Board.BOARDWEIGHT[x][y] = 0;
				else
					Board.BOARDWEIGHT[x][y] = 10000;
			}
		}

		for (int x = 1; x < board.width-1; ++x) {
			for (int y = 1; y < board.height-1; ++y) {
				if (board.board[x][y] == '.' || board.board[x][y] == '+') {
					BFS(board, new BoardPos(x, y));
				}
			}
		}
	}

	private static void BFS(Board board, BoardPos boardPos) {
		HashSet<BoardPos> visited = new HashSet<BoardPos>(500);
		LinkedList<BoardPos> queue = new LinkedList<BoardPos>();

		visited.add(boardPos);
		queue.add(boardPos);
		boardPos.depth = 0;

		while (!queue.isEmpty()) {
			BoardPos bp = queue.removeFirst();

			for (BoardPos newBp : findIncidentBoardPosses(board, bp)) {
				if (visited.add(newBp)) {
					newBp.depth = bp.depth + 1;
					if (newBp.depth < Board.BOARDWEIGHT[newBp.x][newBp.y])
						Board.BOARDWEIGHT[newBp.x][newBp.y] = newBp.depth;
					queue.add(newBp);
				}
			}
		}
	}

	private static Vector<BoardPos> findIncidentBoardPosses(Board board, BoardPos bp) {
		Vector<BoardPos> ret = new Vector<BoardPos>(4);
		int x = bp.x;
		int y = bp.y;

		// UP
		if (board.board[x][y-1] != '#' && board.board[x][y-1] != '*')
			ret.add(new BoardPos(x, y - 1));
		// DOWN
		if (board.board[x][y+1] != '#' && board.board[x][y+1] != '*')
			ret.add(new BoardPos(x, y + 1));
		// LEFT
		if (board.board[x-1][y] != '#' && board.board[x-1][y] != '*')
			ret.add(new BoardPos(x - 1, y));
		// RIGHT
		if (board.board[x+1][y] != '#' && board.board[x+1][y] != '*')
			ret.add(new BoardPos(x + 1, y));

		return ret;
	}

}
