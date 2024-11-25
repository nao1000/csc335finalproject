package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import aggregates.Player;

import aggregates.Letter;

class TestScrabbleModelScoring {

	@Test
	void testScoreAddingWordsBeforeExistingLetter() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		ScrabbleModel model = new ScrabbleModel();
		Field f1 = ScrabbleModel.class.getDeclaredField("player1");
		f1.setAccessible(true);
		Field f2 = ScrabbleModel.class.getDeclaredField("player2");
		f2.setAccessible(true);

		Player p1 = (Player) f1.get(model);
		Player p2 = (Player) f2.get(model);

		System.out.println(model.toString());

		// Add the words neons
		model.makeMove(Letter.getLetter(38), 7, 6);
		model.makeMove(Letter.getLetter(0), 7, 7);
		model.makeMove(Letter.getLetter(30), 7, 8);
		model.makeMove(Letter.getLetter(39), 7, 9);
		model.makeMove(Letter.getLetter(60), 7, 10);
		assertTrue(model.implementCurrentMove());
		System.out.println(model.toString());
		assertEquals(5, p1.getScore());

		// Add the word sail to make sails
		model.makeMove(Letter.getLetter(61), 3, 10);
		model.makeMove(Letter.getLetter(12), 4, 10);
		model.makeMove(Letter.getLetter(21), 5, 10);
		model.makeMove(Letter.getLetter(56), 6, 10);

		assertTrue(model.implementCurrentMove());
		System.out.println(model.toString());
		assertEquals(5, p2.getScore());

		// Add the word wit to make wits using the first s of sails
		model.makeMove(Letter.getLetter(89), 3, 7);
		model.makeMove(Letter.getLetter(22), 3, 8);
		model.makeMove(Letter.getLetter(50), 3, 9);

		assertTrue(model.implementCurrentMove());
		System.out.println(model.toString());
		assertEquals(16, p1.getScore());


	}
	
	@Test
	void testScoreAddingWordsAfterExistingLetter() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		ScrabbleModel model = new ScrabbleModel();
		Field f1 = ScrabbleModel.class.getDeclaredField("player1");
		f1.setAccessible(true);
		Field f2 = ScrabbleModel.class.getDeclaredField("player2");
		f2.setAccessible(true);

		Player p1 = (Player) f1.get(model);
		Player p2 = (Player) f2.get(model);

		System.out.println(model.toString());

		// Add the word connect horizontally
	    model.makeMove(Letter.getLetter(77), 4, 7); // C
	    model.makeMove(Letter.getLetter(30), 5, 7); // O
	    model.makeMove(Letter.getLetter(38), 6, 7); // N
	    model.makeMove(Letter.getLetter(39), 7, 7); // N
	    model.makeMove(Letter.getLetter(0), 8, 7);  // E
	    model.makeMove(Letter.getLetter(78), 9, 7); // C
	    model.makeMove(Letter.getLetter(50), 10, 7); // T
	    
	    
	    
		assertTrue(model.implementCurrentMove());
		System.out.println(model.toString());
		assertEquals(11, p1.getScore());
		
		// Add the word cook vertically starting at the c from connect
	    model.makeMove(Letter.getLetter(31), 4, 8); // o
	    model.makeMove(Letter.getLetter(32), 4, 9); // o
	    model.makeMove(Letter.getLetter(93), 4, 10); // k
	    assertTrue(model.implementCurrentMove());
		System.out.println(model.toString());
		assertEquals(10, p2.getScore());
		
		
		// Convert cook to uncooked
		
	    model.makeMove(Letter.getLetter(64), 4, 5); // u
	    model.makeMove(Letter.getLetter(40), 4, 6); // n
	    model.makeMove(Letter.getLetter(1), 4, 11); // e
	    model.makeMove(Letter.getLetter(68), 4, 12); // d
	    assertTrue(model.implementCurrentMove());
		System.out.println(model.toString());
		assertEquals(26, p1.getScore());

		

		



	}

}
