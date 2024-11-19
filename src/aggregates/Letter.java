package aggregates;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Letter {
	
	private static final Letter[] LETTER_PILE =
			new Letter[98];
	
	static {
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
			System.out.println("Nothing here");
		}
	};
	
	
	private char letter;
	private int points;
	private boolean inUse = false;
	
	private Letter(char c, int points) {
		this.letter = c;
		this.points = points;
		
	}
	
	public int getPoints() {
		return points;
	}
	
	public static Letter getLetter(int i) {
		return LETTER_PILE[i];
	}
	
	public void setInUse() {
		inUse = !inUse;
	}
	
	@Override
	public String toString() {
		return letter + " ";
	}
}
