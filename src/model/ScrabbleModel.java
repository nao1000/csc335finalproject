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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import aggregates.Letter;
import aggregates.LetterBag;
import aggregates.Move;
import aggregates.Observer;
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

	private List<Observer> aObservers = new ArrayList<>();
	private List<Observer> letterObservers = new ArrayList<>();
	
	
	
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
	
	public void addObserver(Observer o) {
		aObservers.add(o);
	}
	
	private void notifyObserver(String updateTo, String name) {
		for (Observer o : aObservers) {
			if (o.getName().equals(name)) {
				o.updateInfo(updateTo);
				break;
			}
		}
	}
	
	public void deleteObservers() {
		aObservers.clear();
	}
	
	private void initializeBoard() {
		/**
		 * This private helper for the constructor creates the 15x15 board the game will
		 * be played on. The board (a 2d array) stores Tile objects at each cord. The
		 * board is constructed by reading in a file the represents the board and all
		 * special tiles.
		 */

		// make sure file is there
		try {

			// file that textually is the game board
			File myFile = new File("boardtiles.txt");
			Scanner myReader = new Scanner(myFile);

			int i = 0;
			while (myReader.hasNextLine()) {
				String data[] = myReader.nextLine().split(" ");

				// there were three types of tiles x1, x2, and x3 letters
				for (int k = 0; k < data.length; k++) {
					Tile tile;
					if (data[k].equals(".")) {
						tile = new Tile(1, 1);
					} else if (data[k].equals("DL")) {
						tile = new Tile(2, 1);
					} else if (data[k].equals("TL")) {
						tile = new Tile(3, 1);
					} else if (data[k].equals("DW")) {
						tile = new Tile(1, 2);
					} else {
						tile = new Tile(1, 3);
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

	private void initializeHands() {
		/**
		 * This private helper for the constructor sets up each players' starting hands.
		 * In Scrabble, each player starts with 7 random tiles
		 */
		for (int i = 0; i < 7; i++) {
			player1.addLetter(letterBag.draw(rand.nextInt(letterBag.size())));
			player2.addLetter(letterBag.draw(rand.nextInt(letterBag.size())));
		}
	}

	public void discardLetters(ArrayList<Letter> discardLetters) {
		/**
		 * This method takes in a list of Letters a player wants to discard. The method
		 * passes the list to the player which will remove all of the letters chosen
		 * from their hand. The player is then given random letters to replace the
		 * discard. All discarded letters are added back to the bag.
		 * 
		 * @param (ArrayList<Letter>) discardLetters: list of Letter objects to remove
		 *                            from player and add back to bag.
		 * 
		 * @pre discardLetters != null
		 */
		if (letterBag.size() >= 7) {
			currPlayer.discardLetter(discardLetters);
			for (int i = 0; i < discardLetters.size(); i++) {
				currPlayer.addLetter(letterBag.draw(rand.nextInt(letterBag.size())));
			}
			for (Letter l : discardLetters) {
				letterBag.addTo(l);
			}
		}
	}

	public void drawLetters() {
		/**
		 * This method (version of discard) is used to remove the tiles a player has
		 * placed on the board. It iterates through the list of current moves, extracts
		 * the letter and removes that object from the player's hand. The player
		 * receives the necessary amount of letters back. The removed letters are NOT
		 * added back to the bag.
		 */
		ArrayList<Letter> arr = new ArrayList<Letter>();
		for (Move m : currMoves) {
			arr.add(m.getLetter());
		}
		currPlayer.discardLetter(arr);
		int drawAmount = arr.size();
		if (arr.size() > letterBag.size()) {
			drawAmount = letterBag.size();
		}
		for (int i = 0; i < drawAmount; i++) {
			currPlayer.addLetter(letterBag.draw(rand.nextInt(letterBag.size())));
		}
		notifyObserver("Tiles Remaining: " + String.valueOf(letterBag.size()), "left");
		clearMoves();
	}

	public void makeMove(Letter l, int x, int y) {
		/**
		 * This method makes a Move object for the model to remember what is attempting
		 * to be placed for this turn.
		 * 
		 * @param (Letter) l: a Letter object being placed
		 * @param (int)    x: the x cord for the board
		 * @param (int)    y: the y cord for the board
		 * 
		 * @pre l != null && 0 <= x <= 14 && 0 <= y <= 14
		 */
		currMoves.add(new Move(l, x, y));
	}

	private void doMoves() {
		/**
		 * This method acts on the attempted moves, physically placing them on the game
		 * board. This is done separately so the model can verifying the positioning of
		 * the letters is valid.
		 */
		for (Move m : currMoves) {
			board[m.getY()][m.getX()].placeLetterTile(m.getLetter());
		}
	}

	public void undoMoves() {
		/**
		 * This method undoes all of the moves that were physically placed. It is called
		 * if implementCurrMoves deems a word is invalid. It iterates through the
		 * current moves lists and empties the Tile object of the letter. It will clear
		 * the current moves list at the end
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
		currMoves.clear();
	}

	public boolean implementCurrentMove() {
		/**
		 * This method is the heart of model's functionality. It calls relevant methods
		 * to ensure an attempted word is both placed correctly AND then validates the
		 * word or any words the player may make in their turn. If the word(s) is(are)
		 * in the dictionary, the score will be calculated and added to to the player.
		 * The model resets what is necessary and changes turns after.
		 * 
		 */

		Collections.sort(currMoves);

		// make sure letters touch existing letters or starts at (7,7)
		if (!adjacentCheck()) {
			this.clearMoves();
			return false;
		}

		// place tiles down
		doMoves();

		Move moveStart = currMoves.get(0);
		Move moveEnd = currMoves.get(currMoves.size() - 1);
		int x = moveStart.getX();
		int y = moveStart.getY();
		int localScore = 0;
		boolean valid = true;
		ArrayList<Move> mainWord = new ArrayList<Move>();
		ArrayList<Move> mainWordT1 = new ArrayList<Move>();
		ArrayList<Move> mainWordT2 = new ArrayList<Move>();
		ArrayList<Move> secondaryWord;

		// If the move is a single letter
		if (currMoves.size() == 1) {
			
			// check vertical
			if ((mainWordT1 = checkVertical(x, y, 2)) == null) {
				valid = false;
			}
			else if (mainWordT1.size() > 1){
				localScore += calculateScore(this.checkVertical(x, y, 2));
			}

			// check horizontal
			if ((mainWordT2 = checkHorizontal(x, y, 2)) == null) {
				valid = false;
			}
			else if (mainWordT2.size() > 1){
				localScore += calculateScore(this.checkHorizontal(x, y, 2));
			}
			
			if (localScore == 0) {
				valid = false;
				
			}
			
			// make sure to multiply if necessary
			if (valid) {
				localScore *= board[currMoves.get(0).getY()][currMoves.get(0).getY()].getWordMulti();
				// mark it as used
				board[currMoves.get(0).getY()][currMoves.get(0).getY()].usedWordMulti();
				currPlayer.addScore(localScore);
				notifyObserver(scoreBoard(), "score");
				
				mainWord = mainWordT1.size() > mainWordT2.size() ? mainWordT1 : mainWordT2;
				notifyObserver(currPlayer.getName() + " played the word " + makeStr(mainWord) + " for " 
							+ String.valueOf(localScore) + " points!", "currPlay");
				drawLetters();
				return true;
			}

		} else {

			// determine if the word is horizontal or not
			boolean isHorizontal = moveStart.getY() == moveEnd.getY();

			// check either vertical or horizontal for main word
			mainWord = isHorizontal ? this.checkHorizontal(x, y, 1) : this.checkVertical(x, y, 1);

			// if main word is valid, check everything else relevant
			// this means for a horizontal word, check new vertical words that may have been
			// made
			// and vice versa
			if (mainWord != null && (mainWord.containsAll(currMoves))) {

				// add main words score
				localScore += calculateScore(mainWord);

				// look at all of the indirect words that may have been made
				for (Move m : currMoves) {
					secondaryWord = isHorizontal ? this.checkVertical(m.getX(), y, 2)
							: this.checkHorizontal(x, m.getY(), 2);
					if (secondaryWord == null) {
						valid = false;
						break;
					}
					if (secondaryWord.size() > 1) {
						for (Move m2 : currMoves) {
							if (secondaryWord.contains(m2)) {						
								localScore += calculateScore(secondaryWord);
								break;
							}
						}
					}
				}
			} else {
				valid = false;
			}
		}
		if (!valid) {
			undoMoves();
			notifyObserver(currPlayer.getName() + " played an invalid word " 
				 + "... Try again", "currPlay");
			return false;
		}

		// include the word multipliers
		localScore *= wordMultipliers();

		// using all of the letters is a bingo
		if (currMoves.size() == 7) {
			localScore += 50;
		}

		// change turns
		currPlayer.addScore(localScore);
		notifyObserver(scoreBoard(), "score");
		notifyObserver(currPlayer.getName() + " played the word " + makeStr(mainWord) + " for " 
					+ String.valueOf(localScore) + " points!", "currPlay");
		drawLetters();
		return true;
	}
	
	private String makeStr(ArrayList<Move> moveWord) {
		String s = "";
		for (Move m : moveWord) {
			s += m.getLetter().getChar();
		}
		return s;
	}
	
	public boolean isGameOver() {
		if (this.getCurrHand().size() == 0) {
			winningMessage();
			return true;
		}
		return false;
	}

	private void winningMessage() {
		String winning = "GAME OVER: " + currPlayer.getName() + " has WON!";
		notifyObserver(winning, "currPlay");
	}
	
	private int calculateScore(ArrayList<Move> arr) {
		/**
		 * This method calculates the score for a given word.
		 * 
		 * @param (ArrayList<Move>) arr: an array of move objects that provide the x,y
		 *                          cords for the tiles (to get multipliers) and the
		 *                          letter to add
		 * 
		 * @return (int): the score of the word played
		 */
		int score = 0;
		for (Move m : arr) {
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

	private int wordMultipliers() {
		int multi = 1;
		for (Move m : currMoves) {
			multi *= board[m.getY()][m.getX()].getWordMulti();
			board[m.getY()][m.getX()].usedWordMulti();
			board[m.getY()][m.getX()].usedMulti();
		}
		return multi;
	}

	public boolean adjacentCheck() {
		/**
		 * This method validates the placement of tiles before they are actually placed.
		 * To start a game in Scrabble, the first word must use the center tile. All
		 * subsequent turns must build off of the other tiles.
		 * 
		 * The reason the tiles are placed after this check is so the method can look
		 * above, below, left, and right for any tiles already placed and doesn't just
		 * find tiles of the current player's turn
		 */

		for (Move m : currMoves) {
			System.out.println("adjacentCheck move is (" + m.getX() + ", " + m.getY() + ")");
			if (m.getY() - 1 != -1 && !board[m.getY() - 1][m.getX()].isUnoccupied()) {
				// If not top board edge position above IS occupied, return true
				return true;
			} else if (m.getX() - 1 != -1 && !board[m.getY()][m.getX() - 1].isUnoccupied()) {
				// If not left board edge and position to left IS occupied, return true
				return true;
			} else if (m.getX() + 1 != 15 && !board[m.getY()][m.getX() + 1].isUnoccupied()) {
				// If not right board edge and position to right IS occupied, return true
				return true;
			} else if (m.getY() + 1 != 15 && !board[m.getY() + 1][m.getX()].isUnoccupied()) {
				// If not bottom board edge and position above IS occupied, return true
				return true;
			} else if (m.getX() == 7 && m.getY() == 7) {
				// If position is middle of board, return true
				return true;
			}
		}
		return false;
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
		if (this.checkIfTileArrayIsWord(currentWord)) {
			return currentWord;
		} else {
			return null;
		}
		// return this.checkIfTileArrayIsWord(checkLongerWord);
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
		if (this.checkIfTileArrayIsWord(currentWord)) {
			return currentWord;
		} else {
			return null;
		}
	}

	private boolean checkIfTileArrayIsWord(ArrayList<Move> moves) {
		/**
		 * This method constructs the word from all of the move objects and checks the
		 * dictionary for if it is a word or not
		 * 
		 * @param (ArrayList<Move>) moves: a list of Move objects
		 * 
		 * @return (boolean): whether or not it is a word
		 */
		System.out.println("New  " + moves.toString());
		StringBuffer str = new StringBuffer();
		for (Move m : moves) {
			str.append(m.getLetter().getChar());
		}
		return dictionary.isWord(str.toString());
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
		} else {
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
		 * @return (List<Letter>): an unmodifiable list of the current player's letters
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
		 * This method returns a "banner" String that represents the points associated
		 * with each player
		 * 
		 * @return (String): a score board as "<player1> score score <player2>"
		 */
		return "<html>" + player1.getName() + " " + String.valueOf(player1.getScore()) + "<br>" 
				+ player2.getName() + " " + String.valueOf(player2.getScore()) + "</html>";
	}
	
	public String getCurrPlayerName() {
		return currPlayer.getName();
	}
	
	public void forceEnd() {
		Player winner = currPlayer == player1 ? player2 : player1;
		String endMessage = "<html>" + currPlayer.getName() + " has forfeited! <br>" 
				+ winner.getName() + " has won!</html>";
		notifyObserver(endMessage, "currPlay");
	}

	public void playAgain() {
		initializeBoard();
		letterBag.fillBag();
		player1 = new Player(player1.getName(), Player.PlayerNum.ONE);
		player2 = new Player(player2.getName(), Player.PlayerNum.TWO);
		initializeHands();
		currPlayer = player1;
	}
}