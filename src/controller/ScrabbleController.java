/*
 * Nathan, Jay, Kory, Steven
 * 
 * File: ScrabbleController.java
 * 
 * Description: This file creates the controller that enables
 * a view to interact with the model indirectly but providing it all
 * of the functionality it needs.
 */

package controller;

import java.util.ArrayList;
import java.util.List;

import aggregates.Letter;
import model.ScrabbleModel;
import view.InfoLabel;

public class ScrabbleController {

	// there should only be one model, shouldn't get changed
	final private ScrabbleModel model;

	// two constructors like the model
	public ScrabbleController() {
		model = new ScrabbleModel();
	}

	public ScrabbleController(String player1, String player2) {
		model = new ScrabbleModel(player1, player2);
	}

	public void makeMove(Letter l, int x, int y) {
		/**
		 * This method passes along the letter and cords of a player's individual tile
		 * placement to the model
		 * 
		 * @param (letter) l: a Letter object
		 * @param (int)    x: x cord of placement
		 * @param (int)    y: y cord of placement
		 * 
		 * @pre l != null && 0 <= (x,y) <= 14
		 */
		model.makeMove(l, x, y);
	}

	public boolean submitMove() {
		/**
		 * This method tells the model to validate and score a players turn
		 * 
		 * @return (boolean): whether or not the attempted play was successful
		 */
		return model.implementCurrentMove();
	}

	public List<Letter> getCurrHand() {
		/**
		 * @return (List<Letter>): an unmodifiable list of the current player's letters
		 */
		return model.getCurrHand();
	}

	public String currentModel() {
		/**
		 * @return (String): the string repr of the game board
		 */
		return model.toString();
	}

	public void discardLetters(ArrayList<Letter> arr) {
		/**
		 * This method takes in the letters a player wants to discard
		 * 
		 * @param (ArrayList<Letter>) arr: a list of Letter objects a player wants to
		 *                            discard back into the bag
		 * 
		 * @pre arr != null
		 */
		model.discardLetters(arr);
	}

	public void clearMoves() {
		/**
		 * This method simply clears all of the move objects from the model.
		 */
		model.undoMoves();
	}

	public void swapTurns() {
		/**
		 * This method changes who's turn it is
		 */
		model.changeTurns();
	}

	public int tilesLeft() {
		/**
		 * @return (int): how many tiles remain in the bag
		 */
		return model.tilesLeft();
	}

	public String getScoreBoard() {
		/**
		 * @return (String): the String repr of the score for each player
		 */
		return model.scoreBoard();
	}

	public String getCurrName() {
		/**
		 * @return (String) : the name of the current player
		 */
		return model.getCurrPlayerName();
	}

	public void addObserver(InfoLabel il) {
		/**
		 * This method adds an InfoLabel to our model. It is a similar idea to the
		 * Observer pattern but is not directly that. This just allows the model to
		 * update part of the view if the client desires
		 */
		model.addObserver(il);
	}

	// End game methods
	public void quitGame() {
		/**
		 * This method will tell the model to end the game in a forfeit
		 */
		model.forceEnd();
	}

	public void startOver() {
		/**
		 * This will tell the model to reset itself
		 */
		model.playAgain();
	}

	public boolean isGameOver() {
		/**
		 * This method asks the model if the game is over
		 */
		return model.isGameOver();
	}

	public void delObservers() {
		/**
		 * This method removes the InfoLables from the model as they will now be reset
		 */
		model.deleteObservers();
	}

	public boolean letterInUse(Letter l) {
		/**
		 * @param (Letter) l : the letter we want to see if is used
		 * 
		 * @return (boolean) : whether or not the letter is in use
		 */
		return model.letterInUse(l);
	}
}