
import static org.junit.Assert.assertEquals;


import org.junit.jupiter.api.Test;

import aggregates.Player;
import aggregates.Player.PlayerNum;

public class PlayerTest {

	Player player1 = new Player("Ethan", PlayerNum.ONE);
	
	@Test
	public void getName() {
		assertEquals(player1.getName(), "Ethan");
	}
	
	@Test
	public void getScore() {
		assertEquals(player1.getScore(), 0);
	}
	
	@Test
	public void getHandEmpty() {
		assertEquals(player1.size(), 0);
	}
	
	@Test
	public void getPlayerNum() {
		assertEquals(player1.getPlayerNum(), PlayerNum.ONE);
	}
	
	@Test
	public void toStringTest() {
		assertEquals(player1.toString(), "Ethan 0");
	}
	
	@Test
	public void addScore() {
		player1.addScore(1);
		assertEquals(player1.toString(), "Ethan 1");
	}
}

