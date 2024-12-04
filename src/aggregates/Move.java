/*
 * Nathan, Jay, Kory, Steven
 * 
 * File: Move.java
 * 
 * Description: This file creates a Move object that will be found
 * in aggregate in the model. A description of the class is found
 * below.
 */

package aggregates;

public class Move implements Comparable<Move> {
	/**
	 * This class represents a move a player is trying to make in the game. It
	 * really just words as a tuple of (x,y,letter) and used by the model to
	 * remember where things are.
	 */
	private int x;
	private int y;
	private Letter l;

	public Move(Letter l, int x, int y) {
		this.x = x;
		this.y = y;
		this.l = l;
	}

	// getters
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Letter getLetter() {
		return l;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ") " + l.toString();
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		if (other == this) {
			return true;
		}
		if (other.getClass() != getClass()) {
			return false;
		}
		return this.x == ((Move) other).getX() && this.y == ((Move) other).getY()
				&& this.l == ((Move) other).getLetter();
	}

	public int compareTo(Move other) {
		if (this.getX() == other.getX()) {
			return this.getY() - other.getY();
		}
		return this.getX() - other.getX();
	}
}