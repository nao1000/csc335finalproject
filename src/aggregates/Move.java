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

import java.util.Comparator;

public class Move {
	/**
	 * This class represents a move a player is trying to make in the game.
	 * It really just words as a tuple of (x,y,letter) and used by the model
	 * to remember where things are.
	 */
	private int x;
	private int y;
	private Letter l;
	
	public Move(Letter l ,int x,int y) {
		this.x = x;
		this.y = y;
		this.l = l;
		this.l.setInUse(true);
	}
	
	//getters
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
		/**
		 * By default, when a move is created the letter is considered in use.
		 * When we clear letters via an invalid move, we need to flip this back off
		 * before we lose the Move object
		 */
		l.setInUse(false);
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ") " + l.toString();
	}
	
	public static class CompareXY implements Comparator<Move> {
		/** 
		 * This Comparator<Move> compares Move objects via their (x,y) cord values
		 */
		
		public int compare(Move m1, Move m2) {
			if (m1.getX() == m2.getX()) {
				return m1.getY() - m2.getY();
			}
			return m1.getX() - m2.getX();	
		}
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
		return this.x == ((Move) other).getX() && this.y == ((Move) other).getY() && this.l == ((Move) other).getLetter();
	}
}