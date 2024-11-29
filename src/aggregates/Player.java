/*
 * Nathan, Jay, Kory, Steven
 * 
 * File: Player.java
 * 
 * Description: This class creates a Player object used for the game
 * of Scrabble. A description of it is provided below
 * 
 */

package aggregates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
	/**
	 * This class represents a player in the game of Scrabble
	 * 
	 * It contains who the player is, their score in the current game, and
	 * their hand
	 */

	// Small enum to repr which player someone is (this is only implementing
	// a two player Scrabble game
	public enum PlayerNum {
		ONE, TWO
	};
	
	private String name;
	private int score;
	private PlayerNum playerNum;
	private ArrayList<Letter> hand;
	
	public Player(String name, PlayerNum playerNum) {
		this.name = name;
		this.score = 0;
		this.playerNum = playerNum;
		this.hand = new ArrayList<Letter>();
	}

	//getters
	public String getName() {
		return name;
	}

	public int getScore() {
		return score;
	}
	
	public List<Letter> getHand() {
		return Collections.unmodifiableList(hand);
	}
	
	public PlayerNum getPlayerNum() {
		return playerNum;
	}

	public int size() {
		return hand.size();
	}
	
	public void discardLetter(ArrayList<Letter> discardLetters) {
		/**
		 * This method removes all tiles designated to be 
		 * discarded
		 * 
		 * @param (ArrayList<Letter>) discardLetters: a list of Letter
		 * objects to be removed from hand.
		 */
		for (Letter l : discardLetters) {
			hand.remove(l);
		}
	}
	
	public void addLetter(Letter l) {
		/**
		 * This method adds a letter to a player's hand
		 * 
		 * @param (Letter) l: a letter to be added to a
		 * hand
		 */
		hand.add(l);
	}

	public void addScore(int score) {
		/**
		 * This method adds to a player's score in the game
		 * 
		 * @param (int) score: the score of the player's turn
		 */
		if (score < 0) {
            throw new IllegalArgumentException("Score cannot be negative");
		}
		this.score += score;
	}

	@Override
	public String toString() {
		return name + " " + score;
	}
}
