package aggregates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {

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

	public String getName() {
		return name;
	}

	public int getScore() {
		return score;
	}
	
	public Letter getLetter(int i) {
		return hand.get(i);
	}
	
	public List<Letter> getHand() {
		return Collections.unmodifiableList(hand);
	}
	
	public void discardLetter(ArrayList<Letter> discardLetters) {
		for (Letter l : discardLetters) {
			hand.remove(l);
		}
	}
	
	public void addLetter(Letter l) {
		hand.add(l);
	}

	public void setScore(int score) {
		if (score < 0) {
			throw new IllegalArgumentException("Score cannot be negative");
		}
		this.score = score;
	}

	public void addScore(int score) {
		if (score < 0) {
            throw new IllegalArgumentException("Score cannot be negative");
		}
		this.score += score;
	}

	public String toString() {
		return name + " " + score;
	}

	public PlayerNum getPlayerNum() {
		return playerNum;
	}

	public int size() {
		return hand.size();
	}

}
