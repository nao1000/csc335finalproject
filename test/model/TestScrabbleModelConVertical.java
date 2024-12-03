package model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.Suite;

import aggregates.Letter;


public class TestScrabbleModelConVertical {
	
	
	ScrabbleModel model = new ScrabbleModel();

	@BeforeEach
	public void placeCon() {
		model.makeMove(Letter.getLetter(77), 7, 7);
		model.makeMove(Letter.getLetter(30), 7, 8);
		model.makeMove(Letter.getLetter(38), 7, 9);
		assertTrue(model.implementCurrentMove());
	}
	
	// Basic vertical test cases
	@Test
	public void testAddingDriectlyBelow() {

		// Add word "test" directly to the write of "con"
		model.makeMove(Letter.getLetter(50), 7, 10);
		model.makeMove(Letter.getLetter(0), 7, 11);
		model.makeMove(Letter.getLetter(60), 7, 12);
		model.makeMove(Letter.getLetter(51), 7, 13);

		assertTrue(model.implementCurrentMove());

	}

	@Test
	public void testAddingSingleLetterDirectlyBelow() {

		// Add letter "e" directly to the write of "con"
		model.makeMove(Letter.getLetter(0), 7, 10);

		assertTrue(model.implementCurrentMove());

	}

	@Test
	public void testAddingSingleLetterDirectlyAbove() {
		
		// Add letter "e" directly to the bottom of "con"
		model.makeMove(Letter.getLetter(0), 7, 10);

		assertTrue(model.implementCurrentMove());

		// Add letter "s" directly to the top of "cone"
		model.makeMove(Letter.getLetter(60), 7, 6);

		assertTrue(model.implementCurrentMove());
	}

	@Test
	public void testAddingInvalidSingleLetterDirectlyToBelow() {

		// Add letter "z" directly below "con"
		model.makeMove(Letter.getLetter(96), 7, 10);

		assertFalse(model.implementCurrentMove());

	}

	@Test
	public void testAddingInvalidSingleLetterDirectlyAbove() {

		// Add letter "e" directly to the bottom of "con"
		model.makeMove(Letter.getLetter(0), 7, 10);

		assertTrue(model.implementCurrentMove());

		// Add letter "s" directly to the top of "cone"
		model.makeMove(Letter.getLetter(96), 7, 6);

		assertFalse(model.implementCurrentMove());
	}

	@Test
	public void testAddingSingleLetterDirectlyToLeftVerticalCase() {

		// Add letter "n" directly to the left of o in "con"
		model.makeMove(Letter.getLetter(39), 6, 8);

		assertTrue(model.implementCurrentMove());

	}

	@Test
	public void testAddingInvalidWordToRight() {

		// Add word "eeee" directly to the ,left of "con"
		model.makeMove(Letter.getLetter(31), 8, 7); 
		model.makeMove(Letter.getLetter(39), 8, 8);
		model.makeMove(Letter.getLetter(1), 8, 9);

		assertFalse(model.implementCurrentMove());

	}

	@Test
	public void testAddingInvalidWordToLeft() {

		// Add word "eeee" directly to the ,left of "con"
		model.makeMove(Letter.getLetter(31), 6, 7);
		model.makeMove(Letter.getLetter(39), 6, 8);
		model.makeMove(Letter.getLetter(1), 6, 9);

		assertFalse(model.implementCurrentMove());

	}
}


