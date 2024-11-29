/*
 * Nathan, Jay, Kory, Steven
 * 
 * File: ScrabbleModel.java
 * 
 * Description: This file is the model for the game of Scrabble
 * we developed. It constructs a board, initializes the players,
 * validates and implements moves, calculates score, and so on so the game
 * of Scrabble can be played!
 */

package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Function;

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
	private Comparator<Move> cxy = new Move.CompareXY();
	
	// Two Constructors Made
	// Default Player names + original tests didn't take in the names
	public ScrabbleModel() {
		initializeBoard();
		letterBag.fillBag();
		player1 = new Player("Player One", Player.PlayerNum.ONE);
		player2 = new Player("Player Two", Player.PlayerNum.TWO);
		initializeHands();
		currPlayer = player1;
	}

	// Custom Player Names
	public ScrabbleModel(String name1, String name2) {
		initializeBoard();
		letterBag.fillBag();
		player1 = new Player(name1, Player.PlayerNum.ONE);
		player2 = new Player(name2, Player.PlayerNum.TWO);
		initializeHands();
		currPlayer = player1;
	}

	private void initializeBoard() {
		/**
		 * This private helper for the constructor creates the 15x15 board the game
		 * will be played on. The board (a 2d array) stores Tile objects at each coord.
		 * The board is constructed by reading in a file the represents the board and all
		 * special tiles.
		 */
		
		// make sure file is there
		try {
			
			// file that textually is the gameboard
			File myFile = new File("boardtiles.txt");
			Scanner myReader = new Scanner(myFile);

			int i = 0;
			while (myReader.hasNextLine()) {
				String data[] = myReader.nextLine().split(" ");

				// there were three types of tiles x1, x2, and x3 letters
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
			System.out.println("Gameboard file does not exist!");
			System.exit(1);
		}
	}

	public void initializeHands() {
		/**
		 * This private helper for the constructor sets up each players'
		 * starting hands. In Scrabble, each player starts with 7 random tiles
		 */
		for (int i = 0; i < 7; i++) {
			player1.addLetter(letterBag.draw(rand.nextInt(letterBag.size())));
			player2.addLetter(letterBag.draw(rand.nextInt(letterBag.size())));
		}
	}
	
	public void discardLetters(ArrayList<Letter> discardLetters) {
		/**
		 * This method takes in a list of Letters a player wants to discard.
		 * The method passes the list to the player which will remove all of the letters
		 * chosen from their hand. The player is then given random letters to replace the discard.
		 * All discarded letters are added back to the bag.
		 * 
		 * @param (ArrayList<Letter>) discardLetters: list of Letter objects to remove
		 * from player and add back to bag.
		 * 
		 * @pre discardLetters != null
		 */
		currPlayer.discardLetter(discardLetters);
		for (int i = 0; i < discardLetters.size(); i++) {
			currPlayer.addLetter(letterBag.draw(rand.nextInt(letterBag.size())));
		}
		for (Letter l : discardLetters) {
			letterBag.addTo(l);
		}
	}
	
	public void playerDiscard() {
		/**
		 * This method (version of discard) is used to remove the tiles a player
		 * has placed on the board. It iterates through the list of current moves,
		 * extracts the letter and removes that object from the player's hand. 
		 * The player receives the necessary amount of letters back. The removed
		 * letters are NOT added back to the bag.
		 */
		ArrayList<Letter> arr = new ArrayList<Letter>();
		for (Move m : currMoves) {
			arr.add(m.getLetter());
		}
		currPlayer.discardLetter(arr);
		for (int i = 0; i < arr.size(); i++) {
			currPlayer.addLetter(letterBag.draw(rand.nextInt(letterBag.size())));
		}
	}

//	public boolean testDictionary(String test) {
//		/*
//		 * This method validates a word on the board.
//		 * 
//		 */
//		return dictionary.isWord(test);
//	}
	
	public void makeMove(Letter l, int x, int y) {
		/**
		 * This method makes a Move object for the model
		 * to remember what is attempting to be placed for 
		 * this turn.
		 * 
		 * @param (Letter) l: a Letter object being placed
		 * @param (int) x: the x cord for the board
		 * @param (int) y: the y cord for the board
		 * 
		 * @pre l != null && 0 <= x <= 14 && 0 <= y <= 14
		 */
		currMoves.add(new Move(l, x, y));
	}

	public void doMoves() {
		/**
		 * This method acts on the attempted moves, physically placing them
		 * on the game board. This is done separately so the model can
		 * verifying the positioning of the letters is valid.
		 * 
		 */
		for (Move m : currMoves) {
			board[m.getY()][m.getX()].placeLetterTile(m.getLetter());
			//placeLetter(m.getLetter(), m.getX(), m.getY());
		}
	}
	
//	/**
//	 * Places a Letter L in position i, k if the space is unoccupied. Marks the
//	 * placement as a current move to be checked at the end of the turn
//	 * 
//	 * @param l
//	 * @param i
//	 * @param k
//	 * @return
//	 */
//	public boolean placeLetter(Letter l, int x, int y) {
//		if (board[y][x].isUnoccupied()) {
//			board[y][x].placeLetterTile(l);
////			System.out.println("In placeLetter we have x y is " + +x + ", " + y);
////			currMoves.add(new Move(l, x, y));
//			return true;
//		}
//		return false;
//	}

	public void undoMoves() {
		/**
		 * This method undoes all of the moves that were physically placed.
		 * It is called if implementCurrMoves deems a word is invalid. It iterates
		 * through the current moves lists and empties the Tile object of the letter.
		 * It will clear the current moves list at the end
		 */
		for (Move m : currMoves) {
			m.undoUse();
			board[m.getY()][m.getX()].removeLetterTile();
		}
		currMoves.clear();
	}
	
	public void clearMoves() {
		/**
		 * This method simply clears the current moves after a change in turns
		 */
		for (Move m : currMoves) {
			m.undoUse();
			System.out.println(m.getLetter() + " " + m.getLetter().isInUse());
		}
		currMoves.clear();
	}

	public boolean implementCurrentMove() {
		/**
		 * This method is the heart of model's functionality.
		 * It calls relevant methods to ensure an attempted word
		 * is both placed correctly AND then validates the word or
		 * any words the player may make in their turn. If the word(s) is(are)
		 * in the dictionary, the score will be calculated and added to
		 * to the player. The model resets what is necessary and changes 
		 * turns after.
		 * 
		 */
		
		
		
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
		Collections.sort(currMoves, cxy);
		if (!adjacentCheck()) {
			this.clearMoves();
			return false;
		}
		doMoves();
		Move moveStart = currMoves.get(0);
		Move moveEnd = currMoves.get(currMoves.size() - 1);
		int x = moveStart.getX();
		int y = moveStart.getY();
		int localScore = 0;
		boolean valid = true;

		// If the move is a single letter
		if ((moveStart.getY() == moveEnd.getY()) && (moveStart.getX() == moveEnd.getX())) {
			if (!(checkHorizontal(x,y,2) != null) && (checkVertical(x,y,2) != null)) {
				undoMoves();
				return false;
			}
		}
		boolean isHorizontal = moveStart.getY() == moveEnd.getY();
		System.out.println(isHorizontal);
		ArrayList<Move> mainWord = isHorizontal ? this.checkHorizontal(x, y, 1) : this.checkVertical(x, y, 1);
		if (mainWord != null) {
			localScore += calculateScoreB(mainWord);
			for (Move m : currMoves) {
				ArrayList<Move> secondaryWord = isHorizontal ? this.checkVertical(m.getX(), y, 2) : this.checkHorizontal(x, m.getY(), 2);
				if (secondaryWord == null) {
					valid = false;
					break;
				}
				if (secondaryWord.size() > 1) {
					System.out.println("Bad");
					localScore += calculateScoreB(secondaryWord);
				}
			}
		}
		else {
			valid = false;
		}
		if (!valid) {
			undoMoves();
			return false;
		}
		currPlayer.addScore(localScore);
		playerDiscard();
		changeTurns();
		this.currMoves.clear();
		return true;
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
		/**
		 * This method validates the placement of tiles before they are actually placed.
		 * To start a game in Scrabble, the first word must use the center tile. All subsequent
		 * turns must build off of the other tiles.
		 * 
		 * The reason the tiles are placed after this check is so the method can look above,
		 * below, left, and right for any tiles already placed and doesn't just find tiles
		 * of the current player's turn
		 */
		
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
				// If not bottom board edge and position above IS occupied, return true
				return true;
			}
			else if (m.getX() == 7 && m.getY() == 7) {
				// If position is middle of board, return true
				return true;
			}
		}
		return false;
	}
	
	public boolean consecutiveCheck(ArrayList<Move> mList) {
		int x = mList.get(0).getX();
		int y = mList.get(0).getY();
		int c = 1;
		for (int i = 1; i < mList.size(); i++) {
			if (mList.get(i).getX() == x+i && mList.get(i).getY() == y) {
				c++;
			}
			else {
				c = 1;
				break;
			}
		}
		for (int i = 1; i < mList.size(); i++) {
			if (mList.get(i).getY() == y+i && mList.get(i).getX() == x) {
				c++;
			}
			else {
				c = 1;
				break;
			}
		}
		return c == mList.size();
	}

	
	private ArrayList<Move> checkHorizontal(int x, int y, int minimumLength) {
		/**
		 * Given an x,y location on the board, iterate to the left until the last
		 * character before an empty spot is found, or the edge of the board is reached.
		 * Then iterate all the way to the right until the end of the row of letters or
		 * the end of the board is reached. Return true if this row makes up a valid
		 * word.
		 * 
		 * @param (int) x: x cord for a tile
		 * @param (int) y: y cord for a tile
		 * @param (int) minimumLength: length of word
		 * 
		 * @return
		 */
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
		if (!(currentWord.containsAll(currMoves))) {
			System.out.println(currMoves.size() + " " + currentWord.size());
			System.out.println("Not horizontal consec");
			return null;
		}
		
		if (this.checkIfTileArrayIsWord(currentWord)) {
			return currentWord;
		} else {
			return null;
		}
		//return this.checkIfTileArrayIsWord(checkLongerWord);
	}

	private ArrayList<Move> checkVertical(int x, int y, int minimumLength) {
		/**
		 * Given an x,y location on the board, iterate to the left until the last
		 * character before an empty spot is found, or the edge of the board is reached.
		 * Then iterate all the way to the right until the end of the row of letters or
		 * the end of the board is reached. Return true if this row makes up a valid
		 * word.
		 * 
		 * @param (int) x: x cord for a tile
		 * @param (int) y: y cord for a tile
		 * @param (int) minimumLength: length of word
		 * 
		 * @return
		 */
		
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
		
		if (!(currentWord.containsAll(currMoves))) {
			System.out.println(currMoves.size() + " " + currentWord.size());
			System.out.println("Not vertical consec");
			return null;
		}
		
		if (this.checkIfTileArrayIsWord(currentWord)) {
			return currentWord;
		} else {
			return null;
		}
	}

	private boolean checkIfTileArrayIsWord(ArrayList<Move> moves) {
		/**
		 * This method constructs the word from all of the move objects
		 * and checks the dictionary for if it is a word or not
		 * 
		 * @param (ArrayList<Move>) moves: a list of Move objects
		 * 
		 * @return (boolean): whether or not it is a word
		 */
		StringBuffer str = new StringBuffer();
		for (Move m : moves ) {
			str.append(m.getLetter().toString().strip());
		}
		return dictionary.isWord(str.toString());
	}
	
	public int calculateScore() {
		/**
		 * This method calculates the points only for the moves
		 * a player made in their turn. Points can be earned from
		 * surrounding tiles/tiles the player did not place in this turn
		 * but they need to found first.
		 */
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
	
	public void changeTurns() {
		/**
		 * This method simply switches who the current player is.
		 * 
		 * Used after discarding, submitting a valid move, or skipping a turn.
		 * 
		 */
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
		/**
		 * Simple getter for the current player's hand
		 * 
		 * @return (List<Letter>): an unmodifiable list of the current
		 * player's letters
		 */
		return currPlayer.getHand();
	}
	
	public int tilesLeft() {
		/**
		 * Simple getter for the current size of letterBag
		 * 
		 * @return (int): how many letters are left in the bag
		 */
		return letterBag.size();
	}
	
	public String scoreBoard() {
		/**
		 * This method returns a "banner" String that represents the points associated with each player
		 * 
		 * @return (String): a score board as "<player1> score score <player2>"
		 */
		return player1.getName() + " " + String.valueOf(player1.getScore()) 
		+ "  " + String.valueOf(player2.getScore()) + " " + player2.getName();
	}

}