/*
 * Nathan, Jay, Kory, Steven
 * 
 * File: PlayerTest.java
 * 
 * Description: JUnit for specifically Player.java
 */
package aggregates;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import aggregates.Player.PlayerNum;

public class PlayerTest {

	Player player1 = new Player("Ethan", PlayerNum.ONE);
	ArrayList<Letter> newArrayList = new ArrayList<Letter>();

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

	@Test
	public void addNegativeScore() {
		assertThrows(IllegalArgumentException.class, () -> {
			player1.addScore(-1);
		});
	}

	@Test
	public void addLetter() {
		player1.addLetter(Letter.getLetter(0));
		assertEquals(player1.getHand().get(0), Letter.getLetter(0));
	}

	@Test
	public void discardLetter() {
		player1.addLetter(Letter.getLetter(0));
		assertEquals(player1.getHand().get(0), Letter.getLetter(0));
		newArrayList.add(Letter.getLetter(0));
		player1.discardLetter(newArrayList);
		assertEquals(player1.size(), 0);
	}
}