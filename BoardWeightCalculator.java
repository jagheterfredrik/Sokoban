import java.util.*;

public class BoardWeightCalculator {
	Board board;
	
	BoardWeightCalculator(Board board)
	{
		this.board = board;
	}
	
	public void calculateBoardWeight()
	{
		for(int x = 0; x < board.width; ++x){
			for(int y = 0; y < board.height; ++y){
				if(board.board[x][y] == '.' || board.board[x][y] == '*' || board.board[x][y] == '+'){
					if(board.board[x][y] == '.')
						Board.BOARDWEIGHT[x][y] = 0;
					if(board.board[x][y] == '*')
						Board.BOARDWEIGHT[x][y] = 10;
					BFS(new BoardPos(x,y));
				}
			}
		}
	}
	
	public void BFS(BoardPos boardPos)
	{
		HashSet<BoardPos> visited = new HashSet<BoardPos>();
		LinkedList<BoardPos> queue = new LinkedList<BoardPos>();
				
		visited.add(boardPos);
		queue.add(boardPos);
		boardPos.d = 0;
		
		while(!queue.isEmpty())
		{
			BoardPos bp = queue.pollFirst();
			
			for(BoardPos newBp : findIncidentBoardPosses(bp)){
				
				if(visited.add(newBp)){
					newBp.d = bp.d + 1;
					queue.add(newBp);
				}
					
			}
		}
		
		for(BoardPos bp : visited)
		{
			if(bp.d < Board.BOARDWEIGHT[bp.x][bp.y])
				if(bp.d == 0 && board.board[bp.x][bp.y] == '*')
					Board.BOARDWEIGHT[bp.x][bp.y] = 1;
				else
					Board.BOARDWEIGHT[bp.x][bp.y] = bp.d;
		}
	
	}
	
	public Vector<BoardPos> findIncidentBoardPosses(BoardPos bp) {
		Vector<BoardPos> ret = new Vector<BoardPos>();
		int currX = bp.x;
		int currY = bp.y;

		//UP
		if(!(board.board[currX][currY - 1] == '#'))
			ret.add(new BoardPos(currX, currY - 1));
		//DOWN
		if(!(board.board[currX][currY + 1] == '#'))
			ret.add(new BoardPos(currX, currY + 1));
		//LEFT
		if(!(board.board[currX - 1][currY] == '#'))
			ret.add(new BoardPos(currX - 1, currY));
		//RIGHT
		if(!(board.board[currX + 1][currY] == '#'))
			ret.add(new BoardPos(currX + 1, currY));

		assert(ret.size() <= 4);
		return ret;
	}

}
