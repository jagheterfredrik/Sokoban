import java.io.BufferedReader;
import java.io.IOException;
import java.util.Vector;

public class Board {
	public int currX, currY;
	public int width=-1, height=-1;
	Vector<Vector<Character>> board;
	Board BFSParent;
	char parentMove;

	@SuppressWarnings("unchecked")
	public Board(Board board, char move) {

		this.board = new Vector<Vector<Character>>();

		for (int i = 0; i<board.board.size(); ++i){
			this.board.add(new Vector<Character>(board.board.get(i).size()));
			for (int j = 0; j<board.board.get(i).size(); ++j)
				this.board.get(i).add(board.board.get(i).get(j));

		}
		this.currX = board.currX;
		this.currY = board.currY;
		this.width = board.width;
		this.height = board.height;
		BFSParent = null;
		doMove(move);
	}

	@SuppressWarnings("unchecked")
	public Board(Board board) {
		this.board = (Vector<Vector<Character>>) board.board.clone();
		this.currX = board.currX;
		this.currY = board.currY;
		this.width = board.width;
		this.height = board.height;
		BFSParent = null;
	}

	public Board(BufferedReader lIn) throws IOException {
		String lLine=lIn.readLine();

		//read number of rows
		int lNumRows=Integer.parseInt(lLine);
		height = lNumRows;

		board = new Vector<Vector<Character>>(lNumRows);

		//read each row
		for(int i=0;i<lNumRows;i++)
		{
			lLine=lIn.readLine();
			if(width < 0) {
				width = lLine.length();
			}
			assert(width == lLine.length());
			Vector<Character> line = new Vector<Character>(lLine.length()-1);
			for(int j = 0; j<lLine.length(); ++j) {
				char l = lLine.charAt(j);
				if(l=='@') 
				{ 
					// @ is nothing special to the board, it's just you!
					currX = j;
					currY = i;
					line.add(' '); // Replace with empty boardspace
				} 
				else if(l=='+') 
				{ 
					// @ is nothing special to the board, it's just you!
					currX = j;
					currY = i;
					line.add('.'); // Replace with empty boardspace
				} 
				else 
				{
					line.add(l);
				}
			}
			board.add(line);
			//here, we would store the row somewhere, to build our board
			//in this demo, we just print it
			//System.out.println(lLine);
		}
		//System.out.println("NUM: "+findPossibleMoves().size());	
		BFSParent = null;
	}

	public Board(String[] b) throws IOException {
		//read number of rows
		height = b.length;

		board = new Vector<Vector<Character>>(height);

		//read each row
		for(int i=0;i<height;i++)
		{
			if(width < 0) {
				width = b[i].length();
			}
			assert(width == b[i].length());
			Vector<Character> line = new Vector<Character>(width);
			for(int j = 0; j<width; ++j) {
				char l = b[i].charAt(j);
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
			//System.out.println(lLine);
		}
		//System.out.println("NUM: "+findPossibleMoves().size());	
		BFSParent = null;
	}

	/*
	 * Counts the number of unsolvedbo*es. When 0 a solution is found.
	 */
	public int unsolvedBoxes() {
		int sum = 0;
		for (int i = 0; i<board.size(); ++i)
			for (int j = 0; j<board.get(i).size(); ++j) 
				if (board.get(i).get(j) == '.')
					++sum;
		return sum;
	}

	/*
	 * Moves the little warehouse keeper; U up - D down - L left - R right
	 */
	public void doMove(char move) {
		assert(findPossibleMoves().contains(move));
		switch(move){
		case 'U':
			currY--;
			if(board.get(currY).get(currX) == '$'){
				board.get(currY).set(currX, ' ');

				if(board.get(currY-1).get(currX) == '.')
					board.get(currY-1).set(currX, '*');
				else
					board.get(currY-1).set(currX, '$');	
			}
			else if(board.get(currY).get(currX) == '*'){
				board.get(currY).set(currX, '.');

				if(board.get(currY-1).get(currX) == '.')
					board.get(currY-1).set(currX, '*');
				else
					board.get(currY-1).set(currX, '$');	
			}
			break;
		case 'D':
			currY++;
			if(board.get(currY).get(currX) == '$'){
				board.get(currY).set(currX, ' ');

				if(board.get(currY+1).get(currX) == '.')
					board.get(currY+1).set(currX, '*');
				else
					board.get(currY+1).set(currX, '$');	
			}
			else if(board.get(currY).get(currX) == '*'){
				board.get(currY).set(currX, '.');

				if(board.get(currY+1).get(currX) == '.')
					board.get(currY+1).set(currX, '*');
				else
					board.get(currY+1).set(currX, '$');	
			}
			break;
		case 'L':
			currX--;
			if(board.get(currY).get(currX) == '$'){
				board.get(currY).set(currX, ' ');

				if(board.get(currY).get(currX-1) == '.')
					board.get(currY).set(currX-1, '*');
				else
					board.get(currY).set(currX-1, '$');	
			}
			else if(board.get(currY).get(currX) == '*'){
				board.get(currY).set(currX, '.');

				if(board.get(currY).get(currX-1) == '.')
					board.get(currY).set(currX-1, '*');
				else
					board.get(currY).set(currX-1, '$');	
			}
			break;
		case 'R':
			currX++;
			if(board.get(currY).get(currX) == '$'){
				board.get(currY).set(currX, ' ');

				if(board.get(currY).get(currX+1) == '.')
					board.get(currY).set(currX+1, '*');
				else
					board.get(currY).set(currX+1, '$');	
			}
			else if(board.get(currY).get(currX) == '*'){
				board.get(currY).set(currX, '.');

				if(board.get(currY).get(currX+1) == '.')
					board.get(currY).set(currX+1, '*');
				else
					board.get(currY).set(currX+1, '$');	
			}
			break;
		}
	}
	
	class BoardPos {
		public BoardPos(int x, int y) {
			this.x = x; this.y = y;
		}
		int x, y;
		int distance(BoardPos bp) {
			return Math.abs(x-bp.x) + Math.abs(y-bp.y);
		}
	}
	
	public int score() {
		Vector<BoardPos> boxes = new Vector<BoardPos>();
		Vector<BoardPos> holders = new Vector<BoardPos>();
		
		for(int i=0; i<width; ++i) {
			for(int j=0; j<height; ++j) {
				char c = board.get(j).get(i);
				if(c == '$')
					boxes.add(new BoardPos(i, j));
				else if(c == '.')
					holders.add(new BoardPos(i, j));
			}
		}

		int tot = 0;
		for(BoardPos box : boxes) {
			int last = Integer.MAX_VALUE;
			for(BoardPos holder : holders) {
				int len = holder.distance(box);
				last = Math.min(len, last);
			}
			tot += last;
		}
		return tot;
	}
	
	boolean isDeadlock() {
		for(int i=0; i<width-1; ++i) {
			for(int j=0; j<height-1; ++j) {
				char a = board.get(j).get(i);
				char b = board.get(j+1).get(i);
				char c = board.get(j).get(i+1);
				char d = board.get(j+1).get(i+1);
				//CHECKA grejer bredvid varann
			}
		}
		return false;
	}

	/*
	 * Finds all possible moves from our current location.
	 * @return Vector<Character> with all possible moves.
	 */
	public Vector<Character> findPossibleMoves() {
		Vector<Character> ret = new Vector<Character>();

		//UP
		if(board.get(currY-1).get(currX) == ' ' || board.get(currY-1).get(currX) == '.')
			ret.add('U');
		else if(board.get(currY-1).get(currX) == '$' || board.get(currY-1).get(currX) == '*')
			if(board.get(currY-2).get(currX) == ' ' || board.get(currY-2).get(currX) == '.')
				ret.add('U');
		//DOWN
		if(board.get(currY+1).get(currX) == ' ' || board.get(currY+1).get(currX) == '.')
			ret.add('D');
		else if(board.get(currY+1).get(currX) == '$' || board.get(currY+1).get(currX) == '*')
			if(board.get(currY+2).get(currX) == ' ' || board.get(currY+2).get(currX) == '.')
				ret.add('D');
		//LEFT
		if(board.get(currY).get(currX-1) == ' ' || board.get(currY).get(currX-1) == '.')
			ret.add('L');
		else if(board.get(currY).get(currX-1) == '$' || board.get(currY).get(currX-1) == '*')
			if(board.get(currY).get(currX-2) == ' ' || board.get(currY).get(currX-2) == '.')
				ret.add('L');
		//RIGHT
		if(board.get(currY).get(currX+1) == ' ' || board.get(currY).get(currX+1) == '.')
			ret.add('R');
		else if(board.get(currY).get(currX+1) == '$' || board.get(currY).get(currX+1) == '*')
			if(board.get(currY).get(currX+2) == ' ' || board.get(currY).get(currX+2) == '.')
				ret.add('R');

		assert(ret.size() <= 4);
		return ret;
	}

	// TODO fix this so be able to prune more.
	public boolean hasDeadlock()
	{
		return false;
	}

	
	@Override
	public int hashCode() 
	{
		int sum = 0;
		//System.out.println("H: "+height+", W: "+width);
		StringBuilder sb = new StringBuilder(""+currX+currY);
		for (int i = 0; i<height; ++i)
			for (int j = 0; j<width; ++j)
				//@TODO: CHECK HASH FUNCTION!!
				sb.append(board.get(i).get(j)+i+j);
		//System.out.println(sb.toString().hashCode());
		return sb.toString().hashCode();
	}

	@Override
	public boolean equals(Object b)
	{	

		if(this == b)
			return true;
		if(!(b instanceof Board))
			return false;

		Board that = (Board) b;


		for(int i = 0; i < height; ++i)
		{
			for(int j = 0; j < width; ++j)
			{
				if(this.board.get(i).get(j) != that.board.get(i).get(j))
					return false;
			}
		}
		if(this.currY != that.currY)
			return false;

		if(this.currX != that.currX)
			return false;

		return true;
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
				{
					if(board.get(i).get(j) == '.')
						res.append('+');
					else
						res.append('@');
				}
				else
				{
					res.append(board.get(i).get(j));
				}
			}
			res.append('\n');
		}
		return res.toString();
	}
}
