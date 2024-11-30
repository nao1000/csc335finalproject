/*
 * Nathan, Jay, Kory, Steven
 * 
 * File: LetterBag.java
 * 
 * Description: This class creates a LetterBag object for the game of Scrabble.
 * A description of the class is given below.
 * 
 */

package aggregates;

import java.util.ArrayList;

public class LetterBag {
	/**
	 * This class represents a LetterBag which is an ArrayList of 
	 * Letters available during the game.
	 * 
	 * The bag can draw tiles, add tiles, refill itself.
	 */
	
	private ArrayList<Letter> letterBag;
	
	public LetterBag() {
		letterBag = new ArrayList<Letter>();
	}
	
	//getter
	public int size() {
		return letterBag.size();
	}
	
	public void fillBag() {
		/**
		 * This method refills the letter bag with all of the letters
		 * used in a complete game of Scrabble
		 */
		letterBag.clear();
		for (int i = 0; i < 98; i++) {
			letterBag.add(Letter.getLetter(i));
		}
	}
	
	public Letter draw(int letterI) {
		/**
		 * This method randomly selects (random because the index passed in
		 * should be random) a Letter for the player.
		 * 
		 * @param (int) letterI: a random index value provided via the model
		 * to select a Letter
		 * 
		 * @return (Letter): the Letter that is drawn
		 */
		Letter l = letterBag.get(letterI);
		letterBag.remove(letterI);
		return l;
	}
	
	public void addTo(Letter l) {
		/**
		 * This method adds a letter back (after a discard) to 
		 * the LetterBag to keep it in play
		 */
		letterBag.add(l);
	}
}
