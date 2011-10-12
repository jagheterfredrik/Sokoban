import java.util.LinkedList;
import java.util.Vector;
import java.util.HashSet;


public class SimpleDeadlockFinder {
        Board board;
        HashSet<BoardPos> visited;
        
        SimpleDeadlockFinder(Board board)
        {
                this.board = board;
                visited = new HashSet<BoardPos>();
                notDeadLockSquare();
        }
        /*
         * Method to find simple dead-locks and store them in 
         * a HashSet notSimpleDeadLock.
         */
        public void notDeadLockSquare()
        {
                LinkedList<BoardPos> q = new LinkedList<BoardPos>();
                BoardPos newBoardPos;
                for (int x = 0; x< this.board.width; ++x){
                        for (int y = 0; y< this.board.height; ++y){        
                                if (this.board.board[x][y] == '$')
                                        this.board.board[x][y] = ' ';
                                if (this.board.board[x][y] == '*')
                                        this.board.board[x][y] = '.';
                                if (this.board.board[x][y] == '@')
                                        this.board.board[x][y] = ' ';
                        }
                }
                for (int x = 0; x< this.board.width; ++x){
                        for (int y = 0; y< this.board.height; ++y){ 
                                if (this.board.board[x][y] == '.'){
                                        newBoardPos = new BoardPos(x, y);
                                        visited.add(newBoardPos);
                                        q.addLast(newBoardPos);
                                        this.board.currX = x;
                                        this.board.currY = y;
                                        while(!q.isEmpty()){
                                                newBoardPos = q.poll();
                                                this.board.currX = newBoardPos.x;
                                                this.board.currY = newBoardPos.y;        
                                                for(BoardPos pull : findPossiblePulls(newBoardPos)){
                                                        newBoardPos.x = this.board.currX;
                                                        newBoardPos.y = this.board.currY;
                                                        
                                                        if(visited.add(newBoardPos)){
                                                        	
                                                                Board.BOARDWEIGHT[newBoardPos.x][newBoardPos.y] = 5;
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
        public Vector<BoardPos> findPossiblePulls(BoardPos boardPos) {
                Vector<BoardPos> ret = new Vector<BoardPos>();
                board.currY = boardPos.y;
                board.currX = boardPos.x;
                if(board.currY-2 >= 0){
                        //UP
                        if(board.board[board.currX][board.currY-2] == ' ' || board.board[board.currX][board.currY-2] == '.')
                                if(board.board[board.currX][board.currY-1] == ' ' || board.board[board.currX][board.currY-1] == '.')
                                        ret.add(new BoardPos(board.currX, board.currY-1));
                }
                if(board.currY+2 <= board.height){
                        //DOWN
                        if(board.board[board.currX][board.currY+2] == ' ' || board.board[board.currX][board.currY+2] == '.')
                                if(board.board[board.currX][board.currY+1] == ' ' || board.board[board.currX][board.currY+1] == '.')
                                	ret.add(new BoardPos(board.currX, board.currY+1));
                }
                if(board.currX-2 >= 0){
                        //LEFT
                        if(board.board[board.currX-2][board.currY] == ' ' || board.board[board.currX-2][board.currY] == '.')
                                if(board.board[board.currX-1][board.currY] == ' ' || board.board[board.currX-1][board.currY] == '.')
                                	ret.add(new BoardPos(board.currX-1, board.currY));
                }
                if(board.currX <= board.width){
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
