import java.io.BufferedReader;
import java.io.IOException;
import java.util.Vector;


public class Board {
	public int currX, currY;
	public int width=-1, height=-1;
	Vector<Vector<Character> > board;
	
	@SuppressWarnings("unchecked")
	public Board(Board board, int move) {
		this.board = (Vector<Vector<Character>>) board.board.clone();
		doMove(move);
	}
	
	public Board(BufferedReader lIn) throws IOException {
		String lLine=lIn.readLine();

        //read number of rows
        int lNumRows=Integer.parseInt(lLine);
        height = lNumRows;
        
        board = new Vector<Vector<Character> >(lNumRows);

        //read each row
        for(int i=0;i<lNumRows;i++)
        {
            lLine=lIn.readLine();
            if(width < 0) {
            	width = lLine.length()-1;
            }
            assert(width == lLine.length()-1);
            Vector<Character> line = new Vector<Character>(lLine.length()-1);
            for(int j = 0; j<lLine.length(); ++j) {
            	char l = lLine.charAt(j);
            	if(l=='@') { // @ is nothing special to the board, it's just you!
            		currX = j;
            		currY = i;
            		line.add(' '); // Replace with empty boardspace
            	} else {
            		line.add(l);
            	}
            }
            board.add(line);
            //here, we would store the row somewhere, to build our board
            //in this demo, we just print it
            System.out.println(lLine);
        }
        System.out.println("NUM: "+findPossibleMoves().size());	
	}
	
	/*
	 * Moves the little warehouse keeper; 0/1 up/down. 2/3 left/right.
	 */
	public void doMove(int move) {
		assert(findPossibleMoves().contains(move));
		currX += (move/2 > 0) ? (move%2 < 1 ? -1 : 1) : 0;
		currY += (move/2 < 1) ? (move%2 < 1 ? -1 : 1) : 0;
	}
	
	/*
	 * Finds all possible moves from our current location.
	 * @return Vector<Integer> with all possible moves.
	 */
	public Vector<Integer> findPossibleMoves() {
		Vector<Integer> ret = new Vector<Integer>();
		
		for(int i=0;i<4; ++i) {
			switch(i) {
			case 0: //UP
				if(currY>=2) {
					int val = board.get(currX).get(currY-1);
					if (val == ' ' || val == '.') ret.add(i);
					break;
				}
				if (currY>=3) {
					int val = board.get(currX).get(currY-1);
					int push = board.get(currX).get(currY-2);
					if (val == '#' && (push == ' ' || push == '.')) {
						ret.add(i);
					}
				}
				break;
			case 1: //DOWN
				if(currY<=height-2) {
					int val = board.get(currX).get(currY+1);
					if (val == ' ' || val == '.') ret.add(i);
					break;
				}
				if(currY<=height-3) {
					int val = board.get(currX).get(currY+1);
					int push = board.get(currX).get(currY+2);
					if (val == '#' && (push == ' ' || push == '.')) {
						ret.add(i);
					}
				}
			case 2: //LEFT
				if(currX>=2) {
					int val = board.get(currX-1).get(currY);
					if (val == ' ' || val == '.') ret.add(i);
					break;
				}
				if (currX>=3) {
					int val = board.get(currX-1).get(currY);
					int push = board.get(currX-2).get(currY);
					if (val == '#' && (push == ' ' || push == '.')) {
						ret.add(i);
					}
				}
				break;
			case 3: //RIGHT
				if(currX<=width-2) {
					int val = board.get(currX+1).get(currY);
					if (val == ' ' || val == '.') ret.add(i);
					break;
				}
				if(currX<=width-3) {
					int val = board.get(currX+1).get(currY);
					int push = board.get(currX+2).get(currY);
					if (val == '#' && (push == ' ' || push == '.')) {
						ret.add(i);
					}
				}
			}
		}
		
		return ret;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder res = new StringBuilder();
		for (int i = 0; i<board.size(); ++i) {
			for (int j = 0; j<board.get(i).size(); ++j) {
				if(currX == j && currY == i)
					res.append('@');
				else
					res.append(board.get(i).get(j));
			}
			res.append('\n');
		}
		return res.toString();
	}
}
