package aggregates;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class LetterBagTest {

	
	LetterBag lb = new LetterBag();
	
	@Test
	public void testEmpty() {
		assertEquals(lb.size(), 0);
	}
	
	@Test
	public void testFill() {
		lb.fillBag();
		assertEquals(lb.size(), 98);
	}
	
	@Test
	public void testDraw() {
		lb.fillBag();
		for (int i = 0; i < 98; i++) {
			assertEquals(Letter.getLetter(i), lb.draw(0));
		}
	}
	
	@Test
	public void testAddTo() {
		lb.addTo(Letter.getLetter(0));
		assertEquals(lb.size(), 1);
	}
}
