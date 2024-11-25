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
		int localScore = 0;

		// If the move is a single letter
		if ((moveStart.getY() == moveEnd.getY()) && (moveStart.getX() == moveEnd.getX())) {
			System.out.println("This is a special case");
			//return true;
			return (checkHorizontal(x,y,2) != null) && (checkVertical(x,y,2) != null);
		}
		else if (moveStart.getY() == moveEnd.getY()) {
			// Check if valid word across entire horizontal row
			ArrayList<Move> horizontal = this.checkHorizontal(x, y, 1);
			if (horizontal != null) {
				// Then check if all vertical words are valid
				localScore += calculateScoreB(horizontal);
				while (x <= moveEnd.getX()) {
					ArrayList<Move> vertical = this.checkVertical(x, y, 2);
					if (vertical == null) {
						System.out.println("Invalid vertical case in horizontal word");
						this.undoMoves();
						return false;
					}
					if (vertical.size() > 1) {
						localScore += calculateScoreB(vertical);
					}
					x++;
				}
				//currPlayer.addScore(calculateScore());
				currPlayer.addScore(localScore);
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
			ArrayList<Move> vertical = this.checkVertical(x, y, 1);
			
			if (vertical != null) {
				// Then check all new horizontal cases
				localScore += calculateScoreB(vertical);

				while (y <= moveEnd.getY()) {
					ArrayList<Move> horizontal = this.checkHorizontal(x, y, 2);
					if (horizontal == null) {
						// If not valid horizontally then undo moves
						System.out.println("Invalid horitontal word in vertical case");
						
						this.undoMoves();
						return false;
					}
					if (horizontal.size() > 1) {
						localScore += calculateScoreB(horizontal);
					}

					y++;
				}
				// Else valid word
//				currPlayer.addScore(calculateScore());
				currPlayer.addScore(localScore);
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
	
	public int calculateScoreB(ArrayList<Move> arr) {
		// Also calculate score when
		// 		Your placed tile forms a new word with existing tiles
		// 		Your tiles extend an existing word
		//		Your tiles fill a gap to make a new word
		// Need to make sure the new word is valid
		// Remember that every tile placed must contribute to the creation of a new valid word
//		System.out.println("--------- Inside calculateScoreB -----------");
//		for (Move m: arr) {
//			System.out.print(m.getLetter().getChar());
//		}
		int score = 0;
		for (Move m: arr) {
			int x = m.getX();
			int y = m.getY();
			Tile tile = board[y][x];
			Letter l = tile.getOccupyingLetter();
			if (!l.equals(m.getLetter())) {
				System.out.println("ERROR: The letters inside calculateScoreB are not aliging");
			}
			score += l.getPoints() * tile.getMulti();

		}
//		System.out.println("Score for this word is: " + score);
		return score;
	}
	
	
	
	public boolean adjacentCheck() {
		for (Move m : currMoves) {
			System.out.println("adjacentCheck move is (" + m.getX() + ", " + m.getY() + ")");
			if (m.getY()-1 != -1 && !board[m.getY()-1][m.getX()].isUnoccupied()) {
				// If not top board edge position above IS occupied, return true
				return true;
			}
			else if (m.getX()-1 != -1 && !board[m.getY()][m.getX()-1].isUnoccupied()) {
				// If not left board edge and position to left IS occupied, return true
				return true;
			}
			else if (m.getX()+1 != 15 && !board[m.getY()][m.getX()+1].isUnoccupied()) {
				// If not right board edge and position to right IS occupied, return true
				return true;
			}
			else if (m.getY()+1 != 15 && !board[m.getY()+1][m.getX()].isUnoccupied()) {
				// If not bottom board edge and position above IS occupied, return trie
				return true;
			}
			else if (m.getX() == 7 && m.getY() == 7) {
				// If position is middle of board, return true
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
	private ArrayList<Move> checkHorizontal(int x, int y, int minimumLength) {
		ArrayList<Move> currentWord = new ArrayList<Move>(15);
		ArrayList<Tile> checkLongerWord = new ArrayList<Tile>(15);
		while (x >= 0 && !(this.board[y][x].isUnoccupied())) {
			x--;
		}
		x++;
		while (x <= 14 && !(this.board[y][x].isUnoccupied())) {
			checkLongerWord.add(this.board[y][x]);
			currentWord.add(new Move(this.board[y][x].getOccupyingLetter(), x, y));
			x++;
		}
		if (currentWord.size() < minimumLength) {
			return currentWord;
		}
		if (this.checkIfTileArrayIsWord(currentWord)) {
			return currentWord;
		} else {
			return null;
		}
		//return this.checkIfTileArrayIsWord(checkLongerWord);
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
	private ArrayList<Move> checkVertical(int x, int y, int minimumLength) {
		ArrayList<Tile> checkLongerWord = new ArrayList<Tile>(15);
		ArrayList<Move> currentWord = new ArrayList<Move>(15);
		while (y >= 0 && !(this.board[y][x].isUnoccupied())) {
			y--;
		}
		y++;
		while (y <= 14 && !(this.board[y][x].isUnoccupied())) {
			checkLongerWord.add(this.board[y][x]);
			currentWord.add(new Move(this.board[y][x].getOccupyingLetter(), x, y));
			y++;
		}
		if (currentWord.size() < minimumLength) {
			return currentWord;
		}		
		if (this.checkIfTileArrayIsWord(currentWord)) {
			return currentWord;
		} else {
			return null;
		}
	}

	private boolean checkIfTileArrayIsWord(ArrayList<Move> moves) {
		StringBuffer str = new StringBuffer();
		for (Move m : moves ) {
			str.append(m.getLetter().toString().strip());
		}
		return dictionary.isWord(str.toString());
	}
	
	public int calculateScore() {
		int total = 0;
		for (Move m : currMoves) {
			total += board[m.getY()][m.getX()].getMulti() * board[m.getY()][m.getX()].getOccupyingLetter().getPoints();
		}
		// Also calculate score when
		// 		Your placed tile forms a new word with existing tiles
		// 		Your tiles extend an existing word
		//		Your tiles fill a gap to make a new word
		// Need to make sure the new word is valid
		// Remember that every tile placed must contribute to the creation of a new valid word
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