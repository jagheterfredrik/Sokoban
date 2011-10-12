import java.math.*;
import java.util.*;
class BoardPos {
	int x, y;
	int depth;
	public BoardPos(int x, int y) {
		this.x = x; this.y = y;
		depth = 0;
	}

	int distance(BoardPos bp) {
		return Math.abs(x-bp.x) + Math.abs(y-bp.y);
	}

	@Override
	public int hashCode() 
	{
		return x + 1000*y;
	}

	@Override
	public boolean equals(Object b)
	{        

		if(this == b)
			return true;
		if(!(b instanceof BoardPos))
			return false;

		BoardPos that = (BoardPos) b;

		if(this.x != that.x)
			return false;

		if(this.y != that.y)
			return false;


		return true;
	}
}