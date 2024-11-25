package controller;

import java.util.ArrayList;
import java.util.List;

import aggregates.Letter;
import aggregates.Move;
import model.ScrabbleModel;

public class ScrabbleController {
	
	final private ScrabbleModel model;
	
	
	public ScrabbleController() {
		model = new ScrabbleModel();
	}
	
	public ScrabbleController(String player1, String player2) {
		model = new ScrabbleModel(player1, player2);
	}
	
	public void makeMove(Letter l, int i, int k) {
		model.makeMove(l, i, k);
	}
	
	public boolean submitMove() {
		return model.implementCurrentMove();
	}
	
	public List<Letter> getCurrHand() {
		return model.getCurrHand();
	}
	
	
	public String currentModel() {
		return model.toString();
	}
	
	public int getScore() {
		return model.currPlayerScore();
	}
	
	public void discardLetters(ArrayList<Letter> arr) {
		model.discardLetters(arr);
	}
}
