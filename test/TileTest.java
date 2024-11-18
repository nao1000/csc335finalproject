import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import aggregates.Letter;
import aggregates.Tile;

// Will do more later, just wanted to set up

public class TileTest {
	
	
	Tile firstTile = new Tile(3);
	
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
		firstTile.placeLetterTile(Letter.getLetter(0));
		assertEquals("E ", firstTile.toString());
	}

}
