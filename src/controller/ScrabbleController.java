package controller;

import java.util.ArrayList;

import aggregates.Letter;
import aggregates.Move;
import model.ScrabbleModel;

public class ScrabbleController {
	
	final private ScrabbleModel model;
	
	
	public ScrabbleController() {
		model = new ScrabbleModel();
	}
	
	public void placeLetter(Letter l, int i, int k) {
		model.placeLetter(l, i, k);
	}
	
	public String currentModel() {
		return model.toString();
	}
}
