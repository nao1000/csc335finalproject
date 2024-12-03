package aggregates;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.junit.jupiter.api.Test;

class TestMove {

	@Test
	void testConstructor() {
		Move m = new Move(Letter.getLetter(77), 7, 8);
		assertTrue(m != null);
		assertTrue(m.getX() == 7);
		assertTrue(m.getY() == 8);
		assertTrue(m.getLetter() == Letter.getLetter(77));
		assertTrue(Letter.getLetter(77).isInUse());

	}
	
	@Test
	void testUndoUse() {
		Move m = new Move(Letter.getLetter(77), 7, 8);
		assertTrue(Letter.getLetter(77).isInUse());
		m.undoUse();
		assertFalse(Letter.getLetter(77).isInUse());

		
	}
	
	@Test
	void testToString() {
		Move m = new Move(Letter.getLetter(77), 7, 8);
		String toStr = m.toString();
		System.out.println(toStr);
		assertTrue(toStr.compareTo("(7, 8) C ") == 0);
		
	}
	
	@Test
	void testComparator() {
		Comparator<Move> cxy = new Move.CompareXY();
		// Add word "test" directly to the write of "con"
		Move m1 = new Move(Letter.getLetter(50), 7,7);
		Move m2 = new Move(Letter.getLetter(0), 8,7);
		Move m3 = new Move(Letter.getLetter(50), 7, 8);
		Move m4 = m1;
		ArrayList<Move> notEqualMoves = new ArrayList<>();
		
		notEqualMoves.add(m1);
		notEqualMoves.add(m2);
		notEqualMoves.add(m3);
		Collections.sort(notEqualMoves, cxy);
		for (int i = 0; i < notEqualMoves.size() - 1; i ++) {
			assertFalse(notEqualMoves.get(i).equals(notEqualMoves.get(i+1)));
		}
	}
	
	@Test
	void testEquals() {
		Move m1 = new Move(Letter.getLetter(0), 7,7);
		Move m2 = new Move(Letter.getLetter(50), 8,7);
		Move m3 = new Move(Letter.getLetter(1), 7, 8);
		Move m4 = m1;
		Move m5 = new Move(Letter.getLetter(2), 7, 7);
		Move m6 = new Move(Letter.getLetter(0), 7, 7);


		assertTrue(m1.equals(m1));
		assertTrue(m1.equals(m4));
		assertFalse(m1.equals(Letter.getLetter(50)));
		assertFalse(m1.equals(m2));
		assertFalse(m1.equals(m3));
		assertFalse(m1.equals(m5));
		assertTrue(m1.equals(m6));
		assertFalse(m1.equals(null));


		
		
	}

}
