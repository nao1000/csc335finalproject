/*
 * Nathan, Jay, Kory, Steven
 * 
 * File: ScrabbleController.java
 * 
 * Description: This file creates the controller that enables
 * a view to interact with the model indirectly but providing it all
 * of the functionality it needs.
 * 
 */

package controller;

import java.util.ArrayList;
import java.util.List;

import aggregates.Letter;
import aggregates.Move;
import aggregates.Observer;
import model.ScrabbleModel;

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
		 * This method passes along the letter and cords
		 * of a player's individual tile placement to the model
		 * 
		 * @param (letter) l: a Letter object
		 * @param (int) x: x cord of placement
		 * @param (int) y: y cord of placement
		 * 
		 * @pre l != null && 0 <= (x,y) <= 14
		 */
		model.makeMove(l, x, y);
	}
	
	public boolean submitMove() {
		/**
		 * This method tells the model to validate and score
		 * a players turn
		 * 
		 * @return (boolean): whether or not the attempted play was 
		 * successful
		 */
		return model.implementCurrentMove();
	}
	
	public List<Letter> getCurrHand() {
		/**
		 * @return (List<Letter>): an unmodifiable list of the current
		 * player's letters
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
		 * This method takes in the letters a player wants
		 * to discard
		 * 
		 * @param (ArrayList<Letter>) arr: a list of Letter objects
		 * a player wants to discard back into the bag
		 */
		model.discardLetters(arr);
	}
	
	public void clearMoves() {
		/**
		 * This method simply clears all of the move objects
		 * from the model. 
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
	
	public void addObserver(Observer o) {
		model.addObserver(o);
	}
	
	public String getCurrName() {
		return model.getCurrPlayerName();
	}
	
	public void quitGame() {
		model.forceEnd();
	}
	
	public void startOver() {
		model.playAgain();
	}
	
	public void delObservers() {
		model.deleteObservers();
	}
}