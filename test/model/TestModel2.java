package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import aggregates.Letter;

public class TestModel2 {

	ScrabbleModel model = new ScrabbleModel("Nathan", "Jay");
	
	@Test
	void testImplementMove() {
		model.makeMove(Letter.getLetter(0), 0,0);
		assertFalse(model.implementCurrentMove());
	}
	
	
	@Test
	void testImplementMove2() {
		model.makeMove(Letter.getLetter(0), 7,7);
		model.makeMove(Letter.getLetter(87), 8,7);
		model.makeMove(Letter.getLetter(1), 9,7);
		model.makeMove(Letter.getLetter(38), 10,7);
		assertTrue(model.implementCurrentMove());
		
		model.makeMove(Letter.getLetter(2), 0,0);
		assertFalse(model.implementCurrentMove());
		
		model.makeMove(Letter.getLetter(12), 10, 8);
		model.makeMove(Letter.getLetter(81), 10, 9);
		assertTrue(model.implementCurrentMove());
		
		model.makeMove(Letter.getLetter(56), 9, 8);
		model.makeMove(Letter.getLetter(93), 9, 9);
		assertFalse(model.implementCurrentMove());
		
		System.out.println(model.toString());
	}
}
