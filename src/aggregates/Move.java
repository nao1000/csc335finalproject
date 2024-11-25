package aggregates;

public class Move {
	private int x;
	private int y;
	private Letter l;
	
	public Move(Letter l ,int x,int y) {
		this.x = x;
		this.y = y;
		this.l = l;
		this.l.setInUse();
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Letter getLetter() {
		return l;
	}
	
	public void undoUse() {
		l.setInUse();
	}
	
	public String toString() {
		return "(" + x + ", " + y + ") " + l.toString();
	}
}