/*
 * Nathan, Jay, Kory, Steven
 * 
 * File: Letter.java
 * 
 * Description: This class creates a Letter object which are the playable
 * tiles a player has to make words in the game of Scrabble. More info is found
 * below
 */

package aggregates;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Letter {
	/**
	 * This class represents a Letter object for the game of Scrabble.
	 * The letters in the game are known in advance and each tile is unique
	 * so the class is structured as a flyweight. No one should be able to 
	 * create new Letters during the game, it should be strictly the letters
	 * that are provided with the game.
	 */
	
	private static final Letter[] LETTER_PILE =
			new Letter[98];
	
	static {
		
		/** 
		 * the letters are constructed via a file that lists all of the letters,
		 * how many of them there are, and the points associated with them.
		 */
		
		// make sure file exists
		try {
			String fn = "letters.txt";
			File myFile = new File(fn);
			Scanner myReader = new Scanner(myFile);

			// Parses through whole file
			int i = 0;
			while (myReader.hasNextLine()) {
				String data[] = myReader.nextLine().split(" ");
				for (int k = Integer.valueOf(data[1]); k > 0; k--) {
					LETTER_PILE[i] = new Letter(data[0].charAt(0), Integer.valueOf(data[2]));
					i++;
				}
			}
			myReader.close();
		} catch (Exception FileNotFoundException) {
			System.out.println("Letter file is missing");
			System.exit(1);
		}
	};
	
	
	private char letter;
	private int points;
	private boolean inUse = false;
	
	private Letter(char c, int points) {
		this.letter = c;
		this.points = points;	
	}
	
	//getters and setters
	public String getChar() {
		return String.valueOf(letter);
	}
	
	public int getPoints() {
		return points;
	}
	
	public static Letter getLetter(int i) {
		return LETTER_PILE[i];
	}
	
	public void setInUse(boolean inUse) {
		this.inUse = inUse;
	}
	
	public boolean isInUse() {
		return inUse;
	}
	
	@Override
	public String toString() {
		return letter + " ";
	}
}
