/*
 * Nathan, Jay, Kory, Steven
 * 
 * File: TestMove.java
 * 
 * Description: JUnit for specifically Move.java
 */

package aggregates;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class TestMove {

	Move testMove = new Move(Letter.getLetter(0), 0,1);
	Move testMove2 = new Move(Letter.getLetter(1), 1,0);
	Move testMove3 = new Move(Letter.getLetter(0), 1,1);
	Move testMove4 = new Move(Letter.getLetter(1), 0,1);
	Move testMove5 = new Move(Letter.getLetter(0), 0,1);
	
	@Test
	public void testGetX() {
		assertEquals(testMove.getX(), 0);
	}
	
	@Test
	public void testGetY() {
		assertEquals(testMove.getY(), 1);
	}
	
	@Test
	public void testGetLetter() {
		assertEquals(testMove.getLetter(), Letter.getLetter(0));
		assertNotEquals(testMove.getLetter(), Letter.getLetter(1));
	}
	
	@Test
	public void testToString() {
		assertEquals("(0, 1) E : 1", testMove.toString());
	}
	
	@Test
	public void testEquals() {
		assertEquals(testMove.equals(testMove), true);
		assertEquals(testMove.equals(null), false);
		assertEquals(testMove.equals(new ArrayList<Move>()), false);
		assertEquals(testMove.equals(testMove2), false);
		assertEquals(testMove.equals(testMove3), false);
		assertEquals(testMove.equals(testMove4), false);
		assertEquals(testMove.equals(testMove5), true);
		
	}
	
	@Test
	public void testCompareTo() {
		assertEquals(Integer.signum(testMove.compareTo(testMove)), 0);
		assertEquals(Integer.signum(testMove.compareTo(testMove2)), -1);
		assertEquals(Integer.signum(testMove3.compareTo(testMove)), 1);
	}
}
