/*
 * Nathan, Jay, Kory, Steven
 * 
 * File: LetterTest.java
 * 
 * Description: JUnit for specifically Letter.java
 */

package aggregates;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.jupiter.api.Test;

public class LetterTest {

	@Test
	public void testGetChar() {
		assertEquals(Letter.getLetter(0).getChar(), "E");
		assertEquals(Letter.getLetter(97).getChar(), "X");
	}

	@Test
	public void testGetPoints() {
		assertEquals(Letter.getLetter(0).getPoints(), 1);
		assertEquals(Letter.getLetter(97).getPoints(), 8);
	}

	@Test
	public void testToString() {
		assertEquals("E : 1", Letter.getLetter(0).toString());
	}

	@Test
	public void testUniqueObjects() {
		assertEquals(Letter.getLetter(0).getChar(), Letter.getLetter(1).getChar());
		assertNotEquals(Letter.getLetter(0), Letter.getLetter(1));
	}

}