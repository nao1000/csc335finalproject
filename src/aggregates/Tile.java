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
	 * This class represents a Tile object. When the model is made, a game board is
	 * constructed (a 2D arr) which is really an aggregation of these tiles.
	 * 
	 * Tiles may or may not store a Letter object but will have some kind of
	 * multiplier that may increase the initial point value of a letter
	 */

	// Optional used because a Tile may not have a letter
	private Optional<Letter> occupyingL;
	private int multiplier;
	private int wordMultiplier;
	private boolean multiUsed = false;
	private boolean wordMultiUsed = false;

	public Tile(int multiplier, int wordMultiplier) {
		occupyingL = Optional.empty();
		this.multiplier = multiplier;
		this.wordMultiplier = wordMultiplier;
	}

	// getter
	public int getMulti() {
		if (multiUsed) {
			return 1;
		}
		return multiplier;
	}

	public int getWordMulti() {
		if (wordMultiUsed) {
			return 1;
		}
		return wordMultiplier;
	}

	public boolean isUnoccupied() {
		return occupyingL.isEmpty();
	}

	public Letter getOccupyingLetter() throws NoSuchElementException {
		return occupyingL.get();
	}

	// "setter"
	public void usedMulti() {
		multiUsed = true;
	}

	public void usedWordMulti() {
		wordMultiUsed = true;
	}

	public boolean placeLetterTile(Letter l) {
		/**
		 * This method provides the Tile with a Letter to store. You can not place a
		 * Letter on an occupied tile
		 * 
		 * @param (Letter) l: a letter object that is tored at the given tile.
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
		 * This method removes the letter from the Tile. You can not remove a letter
		 * from a tile if there is none.
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