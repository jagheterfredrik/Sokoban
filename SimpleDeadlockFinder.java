import java.util.LinkedList;
import java.util.Vector;
import java.util.HashSet;


public class SimpleDeadlockFinder {

        public static  void notDeadLockSquare(Board board)
        {
        	Board.DEADLOCKS = new int[board.width][board.height];

    		for(int x = 0; x < board.width; ++x)
    		{
    			for(int y = 0; y < board.height; ++y)
    			{
    					Board.DEADLOCKS[x][y] = 0;
    			}
    		}
        	
        	HashSet<BoardPos> visited = new HashSet<BoardPos>();;
                LinkedList<BoardPos> q = new LinkedList<BoardPos>();
                BoardPos boardPos;
                for (int x = 0; x< board.width; ++x){
                        for (int y = 0; y< board.height; ++y){        
                                if (board.board[x][y] == '$')
                                        board.board[x][y] = ' ';
                                if (board.board[x][y] == '*')
                                        board.board[x][y] = '.';
                                if (board.board[x][y] == '@')
                                        board.board[x][y] = ' ';
                                if (board.board[x][y] == '+')
                                    board.board[x][y] = '.';
                        }
                }
                for (int x = 0; x< board.width; ++x){
                        for (int y = 0; y< board.height; ++y){ 
                                if (board.board[x][y] == '.'){
                                	
                                       	boardPos = new BoardPos(x, y);
                                       	Board.DEADLOCKS[boardPos.x][boardPos.y] = 1;
                                        visited.add(boardPos);
                                        q.addLast(boardPos);
                                                                 
                                        while(!q.isEmpty()){
                                                boardPos = q.poll();
                                                board.currX = boardPos.x;
                                                board.currY = boardPos.y;  
                                                
                                                for(BoardPos newBoardPos : findPossiblePulls(board, boardPos)){                                                        
                                                        if(visited.add(newBoardPos)){
                                                        	
                                                                Board.DEADLOCKS[newBoardPos.x][newBoardPos.y] = 1;
                                                                q.addLast(newBoardPos);
                                                        }
                                                }
                                        }
                                }
                        }
                }
        }
        /*
         * Help method for method notDeadLockSquare, objective is to find possible pulls
         * from a boardPos and return these in a vector of characters.
         */
        public static Vector<BoardPos> findPossiblePulls(Board board, BoardPos boardPos) {
                Vector<BoardPos> ret = new Vector<BoardPos>();
                board.currY = boardPos.y;
                board.currX = boardPos.x;
                if(board.currY-2 > 0){
                        //UP
                        if(board.board[board.currX][board.currY-2] == ' ' || board.board[board.currX][board.currY-2] == '.')
                                if(board.board[board.currX][board.currY-1] == ' ' || board.board[board.currX][board.currY-1] == '.')
                                        ret.add(new BoardPos(board.currX, board.currY-1));
                }
                if(board.currY+2 < board.height){
                        //DOWN
                        if(board.board[board.currX][board.currY+2] == ' ' || board.board[board.currX][board.currY+2] == '.')
                                if(board.board[board.currX][board.currY+1] == ' ' || board.board[board.currX][board.currY+1] == '.')
                                	ret.add(new BoardPos(board.currX, board.currY+1));
                }
                if(board.currX-2 > 0){
                        //LEFT
                        if(board.board[board.currX-2][board.currY] == ' ' || board.board[board.currX-2][board.currY] == '.')
                                if(board.board[board.currX-1][board.currY] == ' ' || board.board[board.currX-1][board.currY] == '.')
                                	ret.add(new BoardPos(board.currX-1, board.currY));
                }
                if(board.currX+2 < board.width){
                        //RIGHT
                        if(board.board[board.currX+2][board.currY] == ' ' || board.board[board.currX+2][board.currY] == '.')
                                if(board.board[board.currX+1][board.currY] == ' ' || board.board[board.currX+1][board.currY] == '.')
                                	ret.add(new BoardPos(board.currX+1, board.currY));
                }
                assert(ret.size() <= 4);
                return ret;
        }

        /*
         * Help method for notDeadLockSquare, objective is to perform
         * a pull(u, d, l, r) based on current positions on the board.
         * This way notDeadLockSquare gets a new board with different
         * positions. 
         */
        public void doPull(Board board, char pull)
        {
                switch(pull){
                case 'U':
                        board.board[board.currX][board.currY] = ' ';
                        board.currY--;
                        if(board.board[board.currX][board.currY] == ' ')
                                board.board[board.currX][board.currY] ='$';
                        if(board.board[board.currX][board.currY-1] == '.')
                                board.board[board.currX][board.currY-1] ='$';
                        break;
                case 'D':
                        board.board[board.currX][board.currY] = ' ';
                        board.currY++;
                        if(board.board[board.currX][board.currY] == ' ')
                                board.board[board.currX][board.currY] ='$';

                        if(board.board[board.currX][board.currY+1] == '.')
                                board.board[board.currX][board.currY+1] ='$';
                        break;
                case 'L':
                        board.board[board.currX][board.currY] = ' ';
                        board.currX--;
                        if(board.board[board.currX][board.currY] == ' ')
                                board.board[board.currX][board.currY] ='$';

                        if(board.board[board.currX-1][board.currY] == '.')
                                board.board[board.currX-1][board.currY] ='$';
                        break;
                case 'R':
                        board.board[board.currX][board.currY] = ' ';
                        board.currX++;
                        if(board.board[board.currX][board.currY] == ' ')
                                board.board[board.currX][board.currY] ='$';

                        if(board.board[board.currX+1][board.currY] == '.')
                                board.board[board.currX+1][board.currY] ='$';
                        break;
                }
        }
}
