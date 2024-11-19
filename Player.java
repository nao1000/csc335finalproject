package model;

public class Player {

	private String name;
	private int score;
	private int playerNum;
	
	
	
	public Player(String name, int playerNum) {
		this.name = name;
		this.score = 0;
		this.playerNum = playerNum;
	}

	public String getName() {
		return name;
	}

	public int getScore() {
		return score;
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

	public int getPlayerNum() {
		return playerNum;
	}

	public void setPlayerNum(int playerNum) {
		this.playerNum = playerNum;
	}
}
