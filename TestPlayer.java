package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestPlayer {
	
	
	

	@Test
	void testGetName() {
		Player k = new Player("Kory", 2);
        assertEquals(k.getName(), "Kory");
	}
	
	@Test
	void testGetScore() {
		Player n = new Player("Nathan", 1);
		assertEquals(n.getScore(), 0);
	}
	
	@Test
	void testSetScore() {
		Player n = new Player("Nathan", 1);
		n.setScore(5);
		assertEquals(n.getScore(), 5);
	}
	
	@Test
	void testAddScore() {
		Player n = new Player("Nathan", 1);
        n.addScore(5);
        assertEquals(n.getScore(), 5);
	}
	
	@Test
	void testAddScoreNeg() {
		Player n = new Player("Nathan", 1);
		assertThrows(IllegalArgumentException.class, () -> n.addScore(-5));
	}
	
	@Test
	void testSetScoreNeg() {
		Player n = new Player("Nathan", 1);
		assertThrows(IllegalArgumentException.class, () -> n.setScore(-5));
	}
	
	@Test
	void testGetPlayerNum() {
		Player n = new Player("Nathan", 1);
        assertEquals(n.getPlayerNum(), 1);
	}
	
	@Test
	void testSetPlayerNum() {
		Player n = new Player("Nathan", 1);
		n.setPlayerNum(2);
		assertEquals(n.getPlayerNum(), 2);
	}
	
	@Test
	void testToString() {
		Player n = new Player("Nathan", 1);
        assertEquals(n.toString(), "Nathan 0");
	}
	

}
