package model;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import aggregates.Letter;
import controller.ScrabbleController;

public class TestController {
	
	ScrabbleController ctrl = new ScrabbleController();
	
	@Test
	public void testMakeAndSubmtWord() {
		ctrl.makeMove(Letter.getLetter(0), 7, 7);
		ctrl.makeMove(Letter.getLetter(87), 8, 7);
		ctrl.makeMove(Letter.getLetter(1), 9, 7);
		ctrl.makeMove(Letter.getLetter(38), 10, 7);
		assertEquals(ctrl.submitMove(), true);
	}
	
	@Test
	public void testGetScore() {
		assertEquals(ctrl.getScore(), 0);
	}
	
	@Test
	public void testGetHand() {
		assertEquals(7, ctrl.getCurrHand().size());
	}
}