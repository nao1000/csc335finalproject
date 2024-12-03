package model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import aggregates.Letter;

class TestScrabbleModel {

	ScrabbleModel model = new ScrabbleModel();

	@Test
	public void testWordAllTheWayDown() {

		// Add the word "counterbalanced"
		model.makeMove(Letter.getLetter(77), 7, 0);
		model.makeMove(Letter.getLetter(30), 7, 1);
		model.makeMove(Letter.getLetter(64), 7, 2);
		model.makeMove(Letter.getLetter(38), 7, 3);
		model.makeMove(Letter.getLetter(50), 7, 4);
		model.makeMove(Letter.getLetter(0), 7, 5);
		model.makeMove(Letter.getLetter(44), 7, 6);
		model.makeMove(Letter.getLetter(75), 7, 7);
		model.makeMove(Letter.getLetter(12), 7, 8);
		model.makeMove(Letter.getLetter(56), 7, 9);
		model.makeMove(Letter.getLetter(13), 7, 10);
		model.makeMove(Letter.getLetter(39), 7, 11);
		model.makeMove(Letter.getLetter(78), 7, 12);
		model.makeMove(Letter.getLetter(1), 7, 13);
		model.makeMove(Letter.getLetter(68), 7, 14);

		assertTrue(model.implementCurrentMove());

	}

	@Test
	public void testWordAllTheWayDownInPartsStartTop() {

		// Add the word "counterbalanced"
		model.makeMove(Letter.getLetter(77), 7, 0);
		model.makeMove(Letter.getLetter(30), 7, 1);
		model.makeMove(Letter.getLetter(64), 7, 2);
		model.makeMove(Letter.getLetter(38), 7, 3);
		model.makeMove(Letter.getLetter(50), 7, 4);
		model.makeMove(Letter.getLetter(0), 7, 5);
		model.makeMove(Letter.getLetter(44), 7, 6);
		model.makeMove(Letter.getLetter(75), 7, 7);
		model.makeMove(Letter.getLetter(12), 7, 8);
		model.makeMove(Letter.getLetter(56), 7, 9);
		model.makeMove(Letter.getLetter(13), 7, 10);
		model.makeMove(Letter.getLetter(39), 7, 11);
		model.makeMove(Letter.getLetter(78), 7, 12);
		model.makeMove(Letter.getLetter(1), 7, 13);
		model.makeMove(Letter.getLetter(68), 7, 14);

		assertTrue(model.implementCurrentMove());

	}

	@Test
	public void testWordAllTheWayDownInPartsStartBottom() {

		// Add the word "counterbalanced"

		model.makeMove(Letter.getLetter(75), 7, 7);
		model.makeMove(Letter.getLetter(12), 7, 8);
		model.makeMove(Letter.getLetter(56), 7, 9);
		model.makeMove(Letter.getLetter(13), 7, 10);
		model.makeMove(Letter.getLetter(39), 7, 11);
		model.makeMove(Letter.getLetter(78), 7, 12);
		model.makeMove(Letter.getLetter(1), 7, 13);
		model.makeMove(Letter.getLetter(68), 7, 14);

		assertTrue(model.implementCurrentMove());

		model.makeMove(Letter.getLetter(77), 7, 0);
		model.makeMove(Letter.getLetter(30), 7, 1);
		model.makeMove(Letter.getLetter(64), 7, 2);
		model.makeMove(Letter.getLetter(38), 7, 3);
		model.makeMove(Letter.getLetter(50), 7, 4);
		model.makeMove(Letter.getLetter(0), 7, 5);
		model.makeMove(Letter.getLetter(44), 7, 6);

		assertTrue(model.implementCurrentMove());

	}

	@Test
	public void testWordAllTheWayAcross() {

		// Add the word "counterbalanced"
		model.makeMove(Letter.getLetter(77), 0, 7);
		model.makeMove(Letter.getLetter(30), 1, 7);
		model.makeMove(Letter.getLetter(64), 2, 7);
		model.makeMove(Letter.getLetter(38), 3, 7);
		model.makeMove(Letter.getLetter(50), 4, 7);
		model.makeMove(Letter.getLetter(0), 5, 7);
		model.makeMove(Letter.getLetter(44), 6, 7);
		model.makeMove(Letter.getLetter(75), 7, 7);
		model.makeMove(Letter.getLetter(12), 8, 7);
		model.makeMove(Letter.getLetter(56), 9, 7);
		model.makeMove(Letter.getLetter(13), 10, 7);
		model.makeMove(Letter.getLetter(39), 11, 7);
		model.makeMove(Letter.getLetter(78), 12, 7);
		model.makeMove(Letter.getLetter(1), 13, 7);
		model.makeMove(Letter.getLetter(68), 14, 7);

		assertTrue(model.implementCurrentMove());

	}

	@Test
	public void testWordAllTheWayAcrossInPartsStartLeft() {

		// Add the word "counterbalanced"
		model.makeMove(Letter.getLetter(77), 0, 7);
		model.makeMove(Letter.getLetter(30), 1, 7);
		model.makeMove(Letter.getLetter(64), 2, 7);
		model.makeMove(Letter.getLetter(38), 3, 7);
		model.makeMove(Letter.getLetter(50), 4, 7);
		model.makeMove(Letter.getLetter(0), 5, 7);
		model.makeMove(Letter.getLetter(44), 6, 7);
		model.makeMove(Letter.getLetter(75), 7, 7);
		model.makeMove(Letter.getLetter(12), 8, 7);
		model.makeMove(Letter.getLetter(56), 9, 7);
		model.makeMove(Letter.getLetter(13), 10, 7);
		model.makeMove(Letter.getLetter(39), 11, 7);
		model.makeMove(Letter.getLetter(78), 12, 7);
		model.makeMove(Letter.getLetter(1), 13, 7);
		model.makeMove(Letter.getLetter(68), 14, 7);

		assertTrue(model.implementCurrentMove());

	}

	@Test
	public void testWordAllTheWayAcrossInPartsStartRight() {

		// Add the word "counterbalanced"

		model.makeMove(Letter.getLetter(75), 7, 7);
		model.makeMove(Letter.getLetter(12), 8, 7);
		model.makeMove(Letter.getLetter(56), 9, 7);
		model.makeMove(Letter.getLetter(13), 10, 7);
		model.makeMove(Letter.getLetter(39), 11, 7);
		model.makeMove(Letter.getLetter(78), 12, 7);
		model.makeMove(Letter.getLetter(1), 13, 7);
		model.makeMove(Letter.getLetter(68), 14, 7);

		assertTrue(model.implementCurrentMove());

		model.makeMove(Letter.getLetter(77), 0, 7);
		model.makeMove(Letter.getLetter(30), 1, 7);
		model.makeMove(Letter.getLetter(64), 2, 7);
		model.makeMove(Letter.getLetter(38), 3, 7);
		model.makeMove(Letter.getLetter(50), 4, 7);
		model.makeMove(Letter.getLetter(0), 5, 7);
		model.makeMove(Letter.getLetter(44), 6, 7);
		
		System.out.println(model.toString());

		assertTrue(model.implementCurrentMove());

	}

	@Test
	public void testAddingInWrongOrder() {

		// Add the word "con"
		model.makeMove(Letter.getLetter(38), 9, 7);
		model.makeMove(Letter.getLetter(30), 8, 7);
		model.makeMove(Letter.getLetter(77), 7, 7);

		assertTrue(model.implementCurrentMove());
		// Add word "test" directly to the write of "con"
		model.makeMove(Letter.getLetter(50), 10, 7);
		model.makeMove(Letter.getLetter(0), 11, 7);
		model.makeMove(Letter.getLetter(60), 12, 7);
		model.makeMove(Letter.getLetter(51), 13, 7);

		assertTrue(model.implementCurrentMove());

	}

	@Test
	public void testGetCurrHand() {
		assertEquals(model.getCurrHand().size(), 7);

	}
	
	@Test
	public void testAddNotInCenter() {
		model.makeMove(Letter.getLetter(0), 0, 0);
		assertFalse(model.implementCurrentMove());
	}
	
	@Test
	public void adjacencyTest1() {
		
	}

	@Test
	public void testDiscardLetter() {

		List<Letter> hand1 = model.getCurrHand();
		ArrayList<Letter> toDiscard = new ArrayList<>(hand1);
		System.out.println(hand1.toString());
		model.discardLetters(toDiscard);
		List<Letter> hand2 = model.getCurrHand();
		System.out.println(hand2.toString());
		for (int i = 0; i < hand1.size(); i++) {
			System.out.print(hand1.get(i));
			System.out.print(hand2.get(i));
		}

	}

	@Test
	public void testDrawLetters() {

		List<Letter> original = model.getCurrHand();
		List<Letter> hand1 = List.copyOf(original);
		model.tilesLeft();
		ArrayList<Letter> toDiscard = new ArrayList<>(hand1);
		model.discardLetters(toDiscard);
		List<Letter> hand2 = model.getCurrHand();
		System.out.println(hand1.toString());
		System.out.println(hand2.toString());
		for (int i = 0; i < hand1.size(); i++) {
			System.out.print(hand1.get(i));
			System.out.print(hand2.get(i));
			assertNotEquals(hand1.get(i), hand2.get(i));
		}

	}
	
	@Test
	public void testGetCurrName() {
		assertEquals("Player One", model.getCurrPlayerName());
	}
	
	@Test
	public void testConstructorCustomNames() {
		ScrabbleModel model = new ScrabbleModel("Steven", "Jay");

		assertTrue(model.getCurrPlayerName().compareTo("Steven") == 0);	
		model.makeMove(Letter.getLetter(77), 7, 7);
		model.makeMove(Letter.getLetter(30), 8, 7);
		model.makeMove(Letter.getLetter(38), 9, 7);
		assertTrue(model.implementCurrentMove());
		model.changeTurns();
		assertTrue(model.getCurrPlayerName().compareTo("Jay") == 0);	
		assertFalse(model.isGameOver());
	}
	
	@Test
	public void testForceEnd() {
		ScrabbleModel model = new ScrabbleModel("Steven", "Jay");
		model.makeMove(Letter.getLetter(77), 7, 7);
		model.makeMove(Letter.getLetter(30), 8, 7);
		model.makeMove(Letter.getLetter(38), 9, 7);
		model.forceEnd();
	}
	
}
