package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import aggregates.Letter;

public class TestModel2 {

	ScrabbleModel model = new ScrabbleModel();
	
//	@Test
//	void testImplementMove() {
//		model.makeMove(Letter.getLetter(0), 0,0);
//		assertFalse(model.implementCurrentMove());
//	}
	
	
//	@Test
//	void testImplementMove2() {
//		model.makeMove(Letter.getLetter(0), 7,7);
//		model.makeMove(Letter.getLetter(87), 8,7);
//		model.makeMove(Letter.getLetter(1), 9,7);
//		model.makeMove(Letter.getLetter(38), 10,7);
//		assertTrue(model.implementCurrentMove());
//		
//		model.makeMove(Letter.getLetter(2), 0,0);
//		assertFalse(model.implementCurrentMove());
//		
//		model.makeMove(Letter.getLetter(12), 10, 8);
//		model.makeMove(Letter.getLetter(81), 10, 9);
//		assertTrue(model.implementCurrentMove());
//		
//		model.makeMove(Letter.getLetter(56), 9, 8);
//		model.makeMove(Letter.getLetter(93), 9, 9);
//		assertFalse(model.implementCurrentMove());
//		
//		System.out.println(model.toString());
//	}
	@Test
	void testImplementMoveZoology() {
	    model.makeMove(Letter.getLetter(96), 7, 7);  // Z
	    model.makeMove(Letter.getLetter(33), 8, 7);  // O
	    model.makeMove(Letter.getLetter(34), 9, 7);  // O
	    model.makeMove(Letter.getLetter(56), 10, 7); // L
	    model.makeMove(Letter.getLetter(35), 11, 7); // O
	    model.makeMove(Letter.getLetter(72), 12, 7); // G
	    model.makeMove(Letter.getLetter(91), 13, 7); // Y
	    System.out.println(Letter.getLetter(96));
	    System.out.println(Letter.getLetter(33));
	    System.out.println(Letter.getLetter(34));
	    System.out.println(Letter.getLetter(56));
	    System.out.println(Letter.getLetter(35));
	    System.out.println(Letter.getLetter(57));
	    System.out.println(Letter.getLetter(91));
	    
	    assertTrue(model.implementCurrentMove());
	    
	    assertEquals("Player One 92  0 Player Two", model.scoreBoard());
	    System.out.println(model.toString());
	    
	    
	}

}
