/*
 * Nathan, Jay, Kory, Steven
 * 
 * File: Tile.java
 * 
 * Description: This class creates a Tile object which is what the board
 * of the Scrabble game is made up of. More info provided below
 * 
 */

package aggregates;

import java.util.NoSuchElementException;
import java.util.Optional;

public class Tile {
	/**
	 * This class represents a Tile object. When the model is made,
	 * a game board is constructed (a 2D arr) which is really an aggregation
	 * of these tiles.
	 * 
	 * Tiles may or may not store a Letter object but will have some kind of multiplier
	 * that may increase the initial point value of a letter
	 */

	// Optional used because a Tile may not have a letter
	private Optional<Letter> occupyingL;
	private int multiplier;
	
	public Tile(int multiplier) {
		occupyingL = Optional.empty();
		this.multiplier = multiplier;
	}
	
	// getter
	public int getMulti() {
		return multiplier;
	}
	
	public boolean isUnoccupied() {
		return occupyingL.isEmpty();
	}
	
	public Letter getOccupyingLetter() throws NoSuchElementException {
		return occupyingL.get();
	}
	
	
	public boolean placeLetterTile(Letter l) {
		/**
		 * This method provides the Tile with a Letter to store.
		 * You can not place a Letter on an occupied tile
		 * 
		 * @param (Letter) l: a letter object that is tored at
		 * the given tile.
		 * 
		 * @return (boolean): whether or not the letter was placed
		 */
		if (occupyingL.isEmpty()) {
			occupyingL = Optional.of(l);
			return true;
		}
		return false;
	}
	
	public boolean removeLetterTile() {
		/**
		 * This method removes the letter from the Tile.
		 * You can not remove a letter from a tile if there is
		 * none.
		 * 
		 * @return (boolean): whether or not a letter was removed
		 */
		if (!occupyingL.isEmpty()) {
			occupyingL = Optional.empty();
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		if (!this.isUnoccupied()) {
			return occupyingL.get().toString();
		}
		return String.valueOf(multiplier) + " ";
	}
}