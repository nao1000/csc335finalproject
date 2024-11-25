package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import aggregates.Letter;
import aggregates.LetterBag;
import aggregates.Move;
import aggregates.Player;
import aggregates.Tile;

public class ScrabbleModel {

	private Tile[][] board = new Tile[15][15];
	private LetterBag letterBag = new LetterBag();
	private DictionaryTrie dictionary = new DictionaryTrie();
	private ArrayList<Move> currMoves = new ArrayList<Move>();
	private Player player1, player2;
	private Player currPlayer;
	private Random rand = new Random();
	
	public ScrabbleModel() {
		initializeBoard();
		initializeDictionary();
		letterBag.fillBag();
		player1 = new Player("Player One", Player.PlayerNum.ONE);
		player2 = new Player("Player Two", Player.PlayerNum.TWO);
		initializeHands();
		currPlayer = player1;
	}

	public ScrabbleModel(String name1, String name2) {
		initializeBoard();
		initializeDictionary();
		letterBag.fillBag();
		player1 = new Player(name1, Player.PlayerNum.ONE);
		player2 = new Player(name2, Player.PlayerNum.TWO);
		initializeHands();
		currPlayer = player1;
	}

	private void initializeBoard() {
		try {
			File myFile = new File("boardtiles.txt");
			Scanner myReader = new Scanner(myFile);

			int i = 0;
			while (myReader.hasNextLine()) {
				String data[] = myReader.nextLine().split(" ");

				for (int k = 0; k < data.length; k++) {
					Tile tile;
					if (data[k].equals(".")) {
						tile = new Tile(1);
					} else if (data[k].equals("DL")) {
						tile = new Tile(2);
					} else {
						tile = new Tile(3);
					}
					board[i][k] = tile;
				}
				i++;

			}
			myReader.close();
		} catch (Exception e) {

		}
	}

	private void initializeDictionary() {

		try {
			String fn = "dictionary.txt";
			File myFile = new File(fn);
			Scanner myReader = new Scanner(myFile);

			// Parses through whole file
			while (myReader.hasNextLine()) {
				dictionary.placeWord(myReader.nextLine());
			}
			myReader.close();
		} catch (Exception FileNotFoundException) {
			// throw new FileNotFoundException();
			System.out.println("Error: File not found");
		}
	}
	
	public void initializeHands() {
		for (int i = 0; i < 7; i++) {
			player1.addLetter(letterBag.draw(rand.nextInt(letterBag.size())));
			player2.addLetter(letterBag.draw(rand.nextInt(letterBag.size())));
		}
	}
	
	public void discardLetters(ArrayList<Letter> discardLetters) {
		currPlayer.discardLetter(discardLetters);
		for (int i = 0; i < discardLetters.size(); i++) {
			currPlayer.addLetter(letterBag.draw(rand.nextInt(letterBag.size())));
		}
		for (Letter l : discardLetters) {
			letterBag.addTo(l);
		}
	}
	
	public void playerDiscard() {
		ArrayList<Letter> arr = new ArrayList<Letter>();
		for (Move m : currMoves) {
			arr.add(m.getLetter());
		}
		currPlayer.discardLetter(arr);
		for (int i = 0; i < arr.size(); i++) {
			currPlayer.addLetter(letterBag.draw(rand.nextInt(letterBag.size())));
		}
	}

	public boolean testDictionary(String test) {
		return dictionary.isWord(test);
	}
	
	public void makeMove(Letter l, int x, int y) {
		currMoves.add(new Move(l, x, y));
	}

	
	public void doMoves() {
		for (Move m : currMoves) {
			placeLetter(m.getLetter(), m.getX(), m.getY());
		}
	}
	/**
	 * Places a Letter L in position i, k if the space is unoccupied. Marks the
	 * placement as a current move to be checked at the end of the turn
	 * 
	 * @param l
	 * @param i
	 * @param k
	 * @return
	 */
	public boolean placeLetter(Letter l, int x, int y) {
		if (board[y][x].isUnoccupied()) {
			board[y][x].placeLetterTile(l);
//			System.out.println("In placeLetter we have x y is " + +x + ", " + y);
//			currMoves.add(new Move(l, x, y));
			return true;
		}
		return false;
	}

	/**
	 * Undo all the moves in currMoves if turn is decided to be non-valid placement
	 */
	public void undoMoves() {
		for (Move m : currMoves) {
			m.undoUse();
			board[m.getY()][m.getX()].removeLetterTile();
		}
		currMoves.clear();
	}

	public boolean implementCurrentMove() {
		// Use a function that just take a start and end point
		// First check if the word formed by the current move is valid.
		// Then, iterate through each move.
		// For each move,
		// If horizontal, go all the way to the left until the board is empty or over,
		// then check if the letters all the way to the right form a valid word
		// Then start at the first letter. Go all the way up until end of board or empty
		// then cehck if letters all the way down form a valid word

		// Error check if empty;
		// Get the start and end move

		// Check if the current move is a valid word

		// ONLY HAS TO BE VALID IF STANDALONE SO CHECK IN THAT CASE
//		StringBuffer moveString = new StringBuffer();
//		for (Move m : currMoves) {
//			moveString.append(m.getLetter().toString().strip());
//		}
//		if (!dictionary.isWord(moveString.toString())) {
//			this.undoMoves();
//			return false;
//		}

		// Get the start end end of the current move and the x,y of the first letter
		
		if (!adjacentCheck()) {
			this.undoMoves();
			return false;
		}
		doMoves();
		Move moveStart = currMoves.get(0);
		Move moveEnd = currMoves.get(currMoves.size() - 1);
		int x = moveStart.getX();
		int y = moveStart.getY();

		// If the move is a horizontal word
		if ((moveStart.getY() == moveEnd.getY()) && (moveStart.getX() == moveEnd.getX())) {
			System.out.println("This is a special case");
			//return true;
			return checkHorizontal(x,y,2) && checkVertical(x,y,2);
		}
		else if (moveStart.getY() == moveEnd.getY()) {
			// Check if valid word across entire horizontal row
			if (this.checkHorizontal(x, y, 1)) {
				// Then check if all vertical words are valid
				while (x <= moveEnd.getX()) {
					if (!checkVertical(x, y, 2)) {
						System.out.println("Invalid vertical case in horizontal word");
						this.undoMoves();
						return false;
					}
					x++;
				}
				currPlayer.addScore(calculateScore());
				playerDiscard();
				changeTurns();
				this.currMoves.clear();
				return true;
			} else {
				System.out.println("Invalid horitontal word");
				this.undoMoves();
				return false;
			}

		} else {
			// If vertical case then check vertical full length
			if (this.checkVertical(x, y, 1)) {
				// Then check all new horizontal cases
				while (y <= moveEnd.getY()) {
					if (!checkHorizontal(x, y, 2)) {
						// If not valid horizontally then undo moves
						System.out.println("Invalid horitontal word in vertical case");
						
						this.undoMoves();
						return false;
					}
					y++;
				}
				// Else valid word
				currPlayer.addScore(calculateScore());
				playerDiscard();
				changeTurns();
				this.currMoves.clear();
				return true;

			} else {
				// Else not valid vertically and undo moves
				System.out.println("Invalid vertical word");

				this.undoMoves();
				return false;
			}
		}
	}
	
	public boolean adjacentCheck() {
		for (Move m : currMoves) {
			if (m.getY()-1 != -1 && !board[m.getY()-1][m.getX()].isUnoccupied()) {
				return true;
			}
			else if (m.getX()-1 != -1 && !board[m.getY()][m.getX()-1].isUnoccupied()) {
				return true;
			}
			else if (m.getX()+1 != 15 && !board[m.getY()][m.getX()+1].isUnoccupied()) {
				return true;
			}
			else if (m.getY()+1 != 15 && !board[m.getY()+1][m.getX()].isUnoccupied()) {
				return true;
			}
			else if (m.getX() == 7 && m.getY() == 7) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Given an x,y location on the board, iterate to the left until the last
	 * character before an empty spot is found, or the edge of the board is reached.
	 * Then iterate all the way to the right until the end of the row of letters or
	 * the end of the board is reached. Return true if this row makes up a valid
	 * word.
	 * 
	 * @param x
	 * @param y
	 * @param minimumLength
	 * @return
	 */
	private boolean checkHorizontal(int x, int y, int minimumLength) {
		ArrayList<Tile> checkLongerWord = new ArrayList<Tile>(15);
		while (x >= 0 && !(this.board[y][x].isUnoccupied())) {
			x--;
		}
		x++;
		while (x <= 14 && !(this.board[y][x].isUnoccupied())) {
			checkLongerWord.add(this.board[y][x]);
			x++;
		}
		System.out.println("Checking word horizontally" + checkLongerWord.toString());
		if (checkLongerWord.size() < minimumLength) {
			return true;
		}
		return this.checkIfTileArrayIsWord(checkLongerWord);
	}

	/**
	 * Given an x,y location on the board, iterate to the left until the last
	 * character before an empty spot is found, or the edge of the board is reached.
	 * Then iterate all the way to the right until the end of the row of letters or
	 * the end of the board is reached. Return true if this row makes up a valid
	 * word.
	 * 
	 * @param x
	 * @param y
	 * @param minimumLength
	 * @return
	 */
	private boolean checkVertical(int x, int y, int minimumLength) {
		ArrayList<Tile> checkLongerWord = new ArrayList<Tile>(15);
		while (y >= 0 && !(this.board[y][x].isUnoccupied())) {
			y--;
		}
		y++;
		while (y <= 14 && !(this.board[y][x].isUnoccupied())) {
			checkLongerWord.add(this.board[y][x]);
			y++;
		}
		if (checkLongerWord.size() < minimumLength) {
			return true;
		}
		System.out.println("Checking word vertically" + checkLongerWord.toString());
		return this.checkIfTileArrayIsWord(checkLongerWord);
	}

	private boolean checkIfTileArrayIsWord(ArrayList<Tile> tiles) {
		StringBuffer str = new StringBuffer();
		for (Tile t : tiles) {
			str.append(t.getOccupyingLetter().toString().strip());
		}
		return dictionary.isWord(str.toString());
	}
	
	public int calculateScore() {
		int total = 0;
		for (Move m : currMoves) {
			total += board[m.getY()][m.getX()].getMulti() * board[m.getY()][m.getX()].getOccupyingLetter().getPoints();
		}
		return total;
	}
	
	
	private void changeTurns() {
		if (currPlayer == player1) {
			currPlayer = player2;
		}
		else {
			currPlayer = player1;
		}
	}

	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < 15; i++) {
			for (int k = 0; k < 15; k++) {
				s.append(board[i][k].toString());
			}
			s.append("\n");
		}
		return s.toString();
	}
	
	
	public List<Letter> getCurrHand() {
		return currPlayer.getHand();
	}
	
	public int currPlayerScore() {
		return currPlayer.getScore();
	}
	

}