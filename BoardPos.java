import java.math.*;

class BoardPos {
	int x, y;
	public BoardPos(int x, int y) {
		this.x = x; this.y = y;
	}

	int distance(BoardPos bp) {
		return Math.abs(x-bp.x) + Math.abs(y-bp.y);
	}
}