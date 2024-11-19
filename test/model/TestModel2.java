package model;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import aggregates.Letter;

public class TestModel2 {

	ScrabbleModel model = new ScrabbleModel();
	
	@Test
	void testCalculatePoints() {
		model.placeLetter(Letter.getLetter(0), 0, 0);
		model.placeLetter(Letter.getLetter(87), 0, 1);
		model.placeLetter(Letter.getLetter(1), 0, 2);
		model.placeLetter(Letter.getLetter(38), 0, 3);
		assertEquals(model.calculateScore(), 8);
	}
	
}
