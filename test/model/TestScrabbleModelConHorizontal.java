/*
 * Nathan, Jay, Kory, Steven
 * 
 * File: TestScrabbleModelConHorizontal.java
 * 
 * Description: JUnit for specifically ScrabbleModel.java
 * Every test uses the word "con" horizontally, so a @BeforeEach is used
 * to set up each test
 */

package model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import aggregates.Letter;

public class TestScrabbleModelConHorizontal {

	ScrabbleModel model = new ScrabbleModel();

	@BeforeEach
	public void placeCon() {
		model.makeMove(Letter.getLetter(77), 7, 7);
		model.makeMove(Letter.getLetter(30), 8, 7);
		model.makeMove(Letter.getLetter(38), 9, 7);
		assertTrue(model.implementCurrentMove());
	}

	@Test
	public void testAddingDirectlyToRight() {

		// Add word "test" directly to the write of "con"
		model.makeMove(Letter.getLetter(50), 10, 7);
		model.makeMove(Letter.getLetter(0), 11, 7);
		model.makeMove(Letter.getLetter(60), 12, 7);
		model.makeMove(Letter.getLetter(51), 13, 7);
		assertTrue(model.implementCurrentMove());

	}

	@Test
	public void testAddingSingleLetterDirectlyToRight() {

		// Add letter "e" directly to the write of "con"
		model.makeMove(Letter.getLetter(0), 10, 7);
		assertTrue(model.implementCurrentMove());

	}

	@Test
	public void testAddingSingleLetterDirectlyToLeft() {

		// Add letter "e" directly to the write of "con"
		model.makeMove(Letter.getLetter(0), 10, 7);

		// Add letter "s" directly to the left of "cone"
		model.makeMove(Letter.getLetter(60), 6, 7);
		assertTrue(model.implementCurrentMove());

	}

	@Test
	public void testAddingInvalidDirectlyToRight() {

		// Add word "eeee" directly to the right of "con"
		model.makeMove(Letter.getLetter(0), 10, 7);
		model.makeMove(Letter.getLetter(1), 11, 7);
		model.makeMove(Letter.getLetter(2), 12, 7);
		model.makeMove(Letter.getLetter(3), 13, 7);

		assertFalse(model.implementCurrentMove());
	}

	@Test
	public void testAddingInvalidDirectlyToLeft() {

		// Add word "eeee" directly to the ,left of "con"
		model.makeMove(Letter.getLetter(0), 5, 7);
		model.makeMove(Letter.getLetter(1), 6, 7);
		model.makeMove(Letter.getLetter(2), 7, 7);
		model.makeMove(Letter.getLetter(3), 8, 7);

		assertFalse(model.implementCurrentMove());
	}

	@Test
	public void testAddingValidWordBelow() {

		// Add the word one below
		model.makeMove(Letter.getLetter(31), 9, 8);
		model.makeMove(Letter.getLetter(39), 10, 8);
		model.makeMove(Letter.getLetter(1), 11, 8);

		assertTrue(model.implementCurrentMove());
	}

	@Test
	public void testAddingInvalidWordBelow() {

		// Add word "eee" directly to the ,left of "con"
		model.makeMove(Letter.getLetter(0), 7, 8);
		model.makeMove(Letter.getLetter(1), 8, 8);
		model.makeMove(Letter.getLetter(2), 9, 8);

		assertFalse(model.implementCurrentMove());
	}

	@Test
	public void testingVerticalButNotHorizontalSingleLetter() {

		// spell nook using the n from con
		model.makeMove(Letter.getLetter(31), 9, 8);
		model.makeMove(Letter.getLetter(32), 9, 9);
		model.makeMove(Letter.getLetter(93), 9, 10);
		assertTrue(model.implementCurrentMove());

		// spell OF vertically but FO horizontally
		model.makeMove(Letter.getLetter(83), 8, 8);
		assertFalse(model.implementCurrentMove());
	}
}