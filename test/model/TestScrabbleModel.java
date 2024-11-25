package model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import aggregates.Letter;

class TestScrabbleModel {

	static DictionaryTrie trie = new DictionaryTrie();

	
	// Basic horizontal test cases
	@Test
	void testAddingDirectlyToRight() {
		ScrabbleModel model = new ScrabbleModel();

		// Add the word "con"
		model.makeMove(Letter.getLetter(77), 7, 7);
		model.makeMove(Letter.getLetter(30), 8, 7);
		model.makeMove(Letter.getLetter(38), 9, 7);

		System.out.println(model.toString());

		assertTrue(model.implementCurrentMove());

		// Add word "test" directly to the write of "con"
		model.makeMove(Letter.getLetter(50), 10, 7);
		model.makeMove(Letter.getLetter(0), 11, 7);
		model.makeMove(Letter.getLetter(60), 12, 7);
		model.makeMove(Letter.getLetter(51), 13, 7);
		System.out.println(model.toString());
		assertTrue(model.implementCurrentMove());

	}

	@Test
	void testAddingSingleLetterDirectlyToRight() {
		ScrabbleModel model = new ScrabbleModel();

		// Add the word "con"
		model.makeMove(Letter.getLetter(77), 7, 7);
		model.makeMove(Letter.getLetter(30), 8, 7);
		model.makeMove(Letter.getLetter(38), 9, 7);

		System.out.println(model.toString());

		assertTrue(model.implementCurrentMove());

		// Add letter "e" directly to the write of "con"
		model.makeMove(Letter.getLetter(0), 10, 7);
		System.out.println(model.toString());
		assertTrue(model.implementCurrentMove());

	}

	@Test
	void testAddingSingleLetterDirectlyToLeft() {
		ScrabbleModel model = new ScrabbleModel();

		// Add the word "con"
		model.makeMove(Letter.getLetter(77), 7, 7);
		model.makeMove(Letter.getLetter(30), 8, 7);
		model.makeMove(Letter.getLetter(38), 9, 7);

		System.out.println(model.toString());

		assertTrue(model.implementCurrentMove());

		// Add letter "e" directly to the write of "con"
		model.makeMove(Letter.getLetter(0), 10, 7);
		System.out.println(model.toString());

		// Add letter "s" directly to the left of "cone"
		model.makeMove(Letter.getLetter(60), 6, 7);
		System.out.println(model.toString());

		assertTrue(model.implementCurrentMove());

	}



	
	
	@Test
	void testAddingDirectlyToLeft() {
		ScrabbleModel model = new ScrabbleModel();

		// Add the word "test"
		model.makeMove(Letter.getLetter(50), 7, 7);
		model.makeMove(Letter.getLetter(0), 8, 7);
		model.makeMove(Letter.getLetter(60), 9, 7);
		model.makeMove(Letter.getLetter(51), 10, 7);
		System.out.println(model.toString());
		assertTrue(model.implementCurrentMove());

		// Add the word "con" directly to the right of test
		model.makeMove(Letter.getLetter(77), 4, 7);
		model.makeMove(Letter.getLetter(30), 5, 7);
		model.makeMove(Letter.getLetter(38), 6, 7);
		System.out.println(model.toString());
		assertTrue(model.implementCurrentMove());

	}
	
	@Test
	void testAddingInvalidDirectlyToRight() {
		ScrabbleModel model = new ScrabbleModel();

		// Add the word "con"
		model.makeMove(Letter.getLetter(77), 7, 7);
		model.makeMove(Letter.getLetter(30), 8, 7);
		model.makeMove(Letter.getLetter(38), 9, 7);

		System.out.println(model.toString());

		assertTrue(model.implementCurrentMove());

		// Add word "eeee" directly to the right of "con"
		model.makeMove(Letter.getLetter(0), 10, 7);
		model.makeMove(Letter.getLetter(1), 11, 7);
		model.makeMove(Letter.getLetter(2), 12, 7);
		model.makeMove(Letter.getLetter(3), 13, 7);
		System.out.println(model.toString());
		assertFalse(model.implementCurrentMove());

	}
	
	@Test
	void testAddingInvalidDirectlyToLeft() {
		ScrabbleModel model = new ScrabbleModel();

		// Add the word "con"
		model.makeMove(Letter.getLetter(77), 7, 7);
		model.makeMove(Letter.getLetter(30), 8, 7);
		model.makeMove(Letter.getLetter(38), 9, 7);

		System.out.println(model.toString());

		assertTrue(model.implementCurrentMove());

		// Add word "eeee" directly to the ,left of "con"
		model.makeMove(Letter.getLetter(0), 5, 7);
		model.makeMove(Letter.getLetter(1), 6, 7);
		model.makeMove(Letter.getLetter(2), 7, 7);
		model.makeMove(Letter.getLetter(3), 8, 7);
		System.out.println(model.toString());
		assertFalse(model.implementCurrentMove());

	}
	@Test
	void testAddingValidWordBelow() {
		ScrabbleModel model = new ScrabbleModel();

		// Add the word "con"
		model.makeMove(Letter.getLetter(77), 7, 7);
		model.makeMove(Letter.getLetter(30), 8, 7);
		model.makeMove(Letter.getLetter(38), 9, 7);

		System.out.println(model.toString());

		assertTrue(model.implementCurrentMove());

		// Add the word one below
		model.makeMove(Letter.getLetter(31), 9, 8);
		model.makeMove(Letter.getLetter(39), 10, 8);
		model.makeMove(Letter.getLetter(1), 11, 8);

		System.out.println(model.toString());
		assertTrue(model.implementCurrentMove());

	}
	@Test
	void testAddingInvalidWordBelow() {
		ScrabbleModel model = new ScrabbleModel();

		// Add the word "con"
		model.makeMove(Letter.getLetter(77), 7, 7);
		model.makeMove(Letter.getLetter(30), 8, 7);
		model.makeMove(Letter.getLetter(38), 9, 7);

		System.out.println(model.toString());

		assertTrue(model.implementCurrentMove());

		// Add word "eeee" directly to the ,left of "con"
		model.makeMove(Letter.getLetter(31), 7, 8);
		model.makeMove(Letter.getLetter(39), 8, 8);
		model.makeMove(Letter.getLetter(1), 9, 8);

		System.out.println(model.toString());
		assertFalse(model.implementCurrentMove());

	}
	
	// Basic vertical test cases
	@Test
	void testAddingDriectlyBelow() {
		ScrabbleModel model = new ScrabbleModel();

		// Add the word "con"
		model.makeMove(Letter.getLetter(77), 7, 7);
		model.makeMove(Letter.getLetter(30), 7, 8);
		model.makeMove(Letter.getLetter(38), 7, 9);

		System.out.println(model.toString());

		assertTrue(model.implementCurrentMove());

		// Add word "test" directly to the write of "con"
		model.makeMove(Letter.getLetter(50), 7, 10);
		model.makeMove(Letter.getLetter(0), 7, 11);
		model.makeMove(Letter.getLetter(60), 7, 12);
		model.makeMove(Letter.getLetter(51), 7, 13);
		System.out.println(model.toString());
		assertTrue(model.implementCurrentMove());

	}

	@Test
	void testAddingSingleLetterDirectlyBelow() {
		ScrabbleModel model = new ScrabbleModel();

		// Add the word "con"
		model.makeMove(Letter.getLetter(77), 7, 7);
		model.makeMove(Letter.getLetter(30), 7, 8);
		model.makeMove(Letter.getLetter(38), 7, 9);

		System.out.println(model.toString());

		assertTrue(model.implementCurrentMove());

		// Add letter "e" directly to the write of "con"
		model.makeMove(Letter.getLetter(0), 7, 10);
		System.out.println(model.toString());
		assertTrue(model.implementCurrentMove());

	}
	
	@Test
	void testAddingSingleLetterDirectlyAbove() {
		ScrabbleModel model = new ScrabbleModel();

		// Add the word "con"
		model.makeMove(Letter.getLetter(77), 7, 7);
		model.makeMove(Letter.getLetter(30), 7, 8);
		model.makeMove(Letter.getLetter(38), 7, 9);

		System.out.println(model.toString());

		assertTrue(model.implementCurrentMove());

		// Add letter "e" directly to the bottom of "con"
		model.makeMove(Letter.getLetter(0), 7, 10);
		System.out.println(model.toString());
		assertTrue(model.implementCurrentMove());

		// Add letter "s" directly to the top of "cone"
		model.makeMove(Letter.getLetter(60), 7, 6);
		System.out.println(model.toString());
		assertTrue(model.implementCurrentMove());
	}
	
	@Test
	void testAddingInvalidSingleLetterDirectlyToBelow() {
		ScrabbleModel model = new ScrabbleModel();

		// Add the word "con"
		model.makeMove(Letter.getLetter(77), 7, 7);
		model.makeMove(Letter.getLetter(30), 7, 8);
		model.makeMove(Letter.getLetter(38), 7, 9);

		System.out.println(model.toString());

		assertTrue(model.implementCurrentMove());

		// Add letter "z" directly below "con"
		model.makeMove(Letter.getLetter(96), 7, 10);
		System.out.println(model.toString());
		assertFalse(model.implementCurrentMove());

	}
	
	@Test
	void testAddingInvalidSingleLetterDirectlyAbove() {
		ScrabbleModel model = new ScrabbleModel();

		// Add the word "con"
		model.makeMove(Letter.getLetter(77), 7, 7);
		model.makeMove(Letter.getLetter(30), 7, 8);
		model.makeMove(Letter.getLetter(38), 7, 9);

		System.out.println(model.toString());

		assertTrue(model.implementCurrentMove());

		// Add letter "e" directly to the bottom of "con"
		model.makeMove(Letter.getLetter(0), 7, 10);
		System.out.println(model.toString());
		assertTrue(model.implementCurrentMove());

		// Add letter "s" directly to the top of "cone"
		model.makeMove(Letter.getLetter(96), 7, 6);
		System.out.println(model.toString());
		assertFalse(model.implementCurrentMove());
	}

	@Test
	void testAddingSingleLetterDirectlyToLeftVerticalCase() {
		ScrabbleModel model = new ScrabbleModel();

		// Add the word "con"
		model.makeMove(Letter.getLetter(77), 7, 7);
		model.makeMove(Letter.getLetter(30), 7, 8);
		model.makeMove(Letter.getLetter(38), 7, 9);

		System.out.println(model.toString());

		assertTrue(model.implementCurrentMove());
		// Add letter "n" directly to the left of o in "con"
		model.makeMove(Letter.getLetter(39), 6, 8);
		System.out.println(model.toString());
		assertTrue(model.implementCurrentMove());

	}
//
//
//
//	
//	
//	@Test
//	void testAddingDirectlyToLeft() {
//		ScrabbleModel model = new ScrabbleModel();
//
//		// Add the word "test"
//		model.makeMove(Letter.getLetter(50), 7, 7);
//		model.makeMove(Letter.getLetter(0), 8, 7);
//		model.makeMove(Letter.getLetter(60), 9, 7);
//		model.makeMove(Letter.getLetter(51), 10, 7);
//		System.out.println(model.toString());
//		assertTrue(model.implementCurrentMove());
//
//		// Add the word "con" directly to the right of test
//		model.makeMove(Letter.getLetter(77), 4, 7);
//		model.makeMove(Letter.getLetter(30), 5, 7);
//		model.makeMove(Letter.getLetter(38), 6, 7);
//		System.out.println(model.toString());
//		assertTrue(model.implementCurrentMove());
//
//	}
//	
//	@Test
//	void testAddingInvalidDirectlyToRight() {
//		ScrabbleModel model = new ScrabbleModel();
//
//		// Add the word "con"
//		model.makeMove(Letter.getLetter(77), 7, 7);
//		model.makeMove(Letter.getLetter(30), 8, 7);
//		model.makeMove(Letter.getLetter(38), 9, 7);
//
//		System.out.println(model.toString());
//
//		assertTrue(model.implementCurrentMove());
//
//		// Add word "eeee" directly to the right of "con"
//		model.makeMove(Letter.getLetter(0), 10, 7);
//		model.makeMove(Letter.getLetter(1), 11, 7);
//		model.makeMove(Letter.getLetter(2), 12, 7);
//		model.makeMove(Letter.getLetter(3), 13, 7);
//		System.out.println(model.toString());
//		assertFalse(model.implementCurrentMove());
//
//	}
//	
//	@Test
//	void testAddingInvalidDirectlyToLeft() {
//		ScrabbleModel model = new ScrabbleModel();
//
//		// Add the word "con"
//		model.makeMove(Letter.getLetter(77), 7, 7);
//		model.makeMove(Letter.getLetter(30), 8, 7);
//		model.makeMove(Letter.getLetter(38), 9, 7);
//
//		System.out.println(model.toString());
//
//		assertTrue(model.implementCurrentMove());
//
//		// Add word "eeee" directly to the ,left of "con"
//		model.makeMove(Letter.getLetter(0), 5, 7);
//		model.makeMove(Letter.getLetter(1), 6, 7);
//		model.makeMove(Letter.getLetter(2), 7, 7);
//		model.makeMove(Letter.getLetter(3), 8, 7);
//		System.out.println(model.toString());
//		assertFalse(model.implementCurrentMove());
//
//	}
//	@Test
//	void testAddingValidWordBelow() {
//		ScrabbleModel model = new ScrabbleModel();
//
//		// Add the word "con"
//		model.makeMove(Letter.getLetter(77), 7, 7);
//		model.makeMove(Letter.getLetter(30), 8, 7);
//		model.makeMove(Letter.getLetter(38), 9, 7);
//
//		System.out.println(model.toString());
//
//		assertTrue(model.implementCurrentMove());
//
//		// Add word "eeee" directly to the ,left of "con"
//		model.makeMove(Letter.getLetter(31), 9, 8);
//		model.makeMove(Letter.getLetter(39), 10, 8);
//		model.makeMove(Letter.getLetter(1), 11, 8);
//
//		System.out.println(model.toString());
//		assertTrue(model.implementCurrentMove());
//
//	}
	@Test
	void testAddingInvalidWordToRight() {
		ScrabbleModel model = new ScrabbleModel();

		// Add the word "con"
		model.makeMove(Letter.getLetter(77), 7, 7);
		model.makeMove(Letter.getLetter(30), 7, 8);
		model.makeMove(Letter.getLetter(38), 7, 9);

		System.out.println(model.toString());

		assertTrue(model.implementCurrentMove());

		// Add word "eeee" directly to the ,left of "con"
		model.makeMove(Letter.getLetter(31), 8, 7);
		model.makeMove(Letter.getLetter(39), 8, 8);
		model.makeMove(Letter.getLetter(1), 8, 9);

		System.out.println(model.toString());
		assertFalse(model.implementCurrentMove());

	}
	
	@Test
	void testAddingInvalidWordToLeft() {
		ScrabbleModel model = new ScrabbleModel();

		// Add the word "con"
		model.makeMove(Letter.getLetter(77), 7, 7);
		model.makeMove(Letter.getLetter(30), 7, 8);
		model.makeMove(Letter.getLetter(38), 7, 9);

		System.out.println(model.toString());

		assertTrue(model.implementCurrentMove());

		// Add word "eeee" directly to the ,left of "con"
		model.makeMove(Letter.getLetter(31), 6, 7);
		model.makeMove(Letter.getLetter(39), 6, 8);
		model.makeMove(Letter.getLetter(1), 6, 9);

		System.out.println(model.toString());
		assertFalse(model.implementCurrentMove());

	}
	
	@Test
	void testWordAllTheWayDown() {
		ScrabbleModel model = new ScrabbleModel();

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

		System.out.println(model.toString());

		assertTrue(model.implementCurrentMove());

	}
	
	@Test
	void testWordAllTheWayDownInPartsStartTop() {
		ScrabbleModel model = new ScrabbleModel();

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

		System.out.println(model.toString());

		assertTrue(model.implementCurrentMove());


	}
	
	@Test
	void testWordAllTheWayDownInPartsStartBottom() {
		ScrabbleModel model = new ScrabbleModel();

		// Add the word "counterbalanced"

		model.makeMove(Letter.getLetter(75), 7, 7);
		model.makeMove(Letter.getLetter(12), 7, 8);
		model.makeMove(Letter.getLetter(56), 7, 9);
		model.makeMove(Letter.getLetter(13), 7, 10);
		model.makeMove(Letter.getLetter(39), 7, 11);
		model.makeMove(Letter.getLetter(78), 7, 12);
		model.makeMove(Letter.getLetter(1), 7, 13);
		model.makeMove(Letter.getLetter(68), 7, 14);

		System.out.println(model.toString());

		assertTrue(model.implementCurrentMove());
		
		model.makeMove(Letter.getLetter(77), 7, 0);
		model.makeMove(Letter.getLetter(30), 7, 1);
		model.makeMove(Letter.getLetter(64), 7, 2);
		model.makeMove(Letter.getLetter(38), 7, 3);
		model.makeMove(Letter.getLetter(50), 7, 4);
		model.makeMove(Letter.getLetter(0), 7, 5);
		model.makeMove(Letter.getLetter(44), 7, 6);
		System.out.println(model.toString());

		assertTrue(model.implementCurrentMove());



	}
	
	@Test
	void testWordAllTheWayAcross() {
		ScrabbleModel model = new ScrabbleModel();

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

		System.out.println(model.toString());

		assertTrue(model.implementCurrentMove());

	}
	
	@Test
	void testWordAllTheWayAcrossInPartsStartLeft() {
		ScrabbleModel model = new ScrabbleModel();

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


		System.out.println(model.toString());

		assertTrue(model.implementCurrentMove());

	}
	
	@Test
	void testWordAllTheWayAcrossInPartsStartRight() {
		ScrabbleModel model = new ScrabbleModel();

		// Add the word "counterbalanced"
		
		
		model.makeMove(Letter.getLetter(75), 7, 7);
		model.makeMove(Letter.getLetter(12), 8, 7);
		model.makeMove(Letter.getLetter(56), 9, 7);
		model.makeMove(Letter.getLetter(13), 10, 7);
		model.makeMove(Letter.getLetter(39), 11, 7);
		model.makeMove(Letter.getLetter(78), 12, 7);
		model.makeMove(Letter.getLetter(1), 13, 7);
		model.makeMove(Letter.getLetter(68), 14, 7);
		System.out.println(model.toString());

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
		System.out.println(model.toString());




	}
	
	
	@Test
	void testAddingInWrongOrder() {
		ScrabbleModel model = new ScrabbleModel();

		// Add the word "con"
		model.makeMove(Letter.getLetter(38), 9, 7);
		model.makeMove(Letter.getLetter(30), 8, 7);
		model.makeMove(Letter.getLetter(77), 7, 7);

		System.out.println(model.toString());

		assertTrue(model.implementCurrentMove());
		// Add word "test" directly to the write of "con"
		model.makeMove(Letter.getLetter(50), 10, 7);
		model.makeMove(Letter.getLetter(0), 11, 7);
		model.makeMove(Letter.getLetter(60), 12, 7);
		model.makeMove(Letter.getLetter(51), 13, 7);
		System.out.println(model.toString());
		assertTrue(model.implementCurrentMove());

	}


	@Test
	void testDictionaryInModel() {
		ScrabbleModel model = new ScrabbleModel();
		assertTrue(model.testDictionary("con"));

	}

}
