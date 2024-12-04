/*
 * Nathan, Jay, Kory, Steven
 * 
 * File: TileTest.java
 * 
 * Description: JUnit for specifically Tile.java
 */

package aggregates;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

public class TileTest {

	Tile firstTile = new Tile(3, 1);
	Tile secondTile = new Tile(4, 2);

	@Test
	public void getMult() {
		assertEquals(3, firstTile.getMulti());
	}

	@Test
	public void toStringTest() {
		assertEquals("3 ", firstTile.toString());
	}

	@Test
	public void insertLetter() {
		assertTrue(firstTile.placeLetterTile(Letter.getLetter(0)));
	}

	@Test
	public void insertOnFilledLetter() {
		assertTrue(firstTile.placeLetterTile(Letter.getLetter(0)));
		assertFalse(firstTile.placeLetterTile(Letter.getLetter(35)));
	}

	@Test
	public void removeEmpty() {
		assertFalse(firstTile.removeLetterTile());
	}

	@Test
	public void removeLetterPresent() {
		firstTile.placeLetterTile(Letter.getLetter(0));
		assertTrue(firstTile.removeLetterTile());
	}

	@Test
	public void getTile() {
		firstTile.placeLetterTile(Letter.getLetter(0));
		assertEquals(Letter.getLetter(0), firstTile.getOccupyingLetter());
	}

	@Test
	public void toStringFull() {
		firstTile.placeLetterTile(Letter.getLetter(0));
		assertEquals("E : 1", firstTile.toString());
	}

	@Test
	public void getMulti() {
		secondTile.placeLetterTile(Letter.getLetter(0));
		assertEquals(4, secondTile.getMulti());
	}

	@Test
	public void getWordMulti() {
		secondTile.placeLetterTile(Letter.getLetter(0));
		assertEquals(2, secondTile.getWordMulti());
	}

	@Test
	public void usedMulti() {
		secondTile.placeLetterTile(Letter.getLetter(0));
		secondTile.usedMulti();
		assertEquals(1, secondTile.getMulti());
	}

	@Test
	public void usedWordMulti() {
		secondTile.placeLetterTile(Letter.getLetter(0));
		secondTile.usedWordMulti();
		assertEquals(1, secondTile.getWordMulti());
	}
}