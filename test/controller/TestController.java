package controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import aggregates.Letter;
//import aggregates.Observer;
import model.ScrabbleModel;

import java.util.ArrayList;
import java.util.List;

public class TestController {

    ScrabbleController ctrl;

    @BeforeEach
    public void setUp() {
        ctrl = new ScrabbleController("Player One", "Player Two");
    }

    @Test
    public void testMakeAndSubmitWord() {
        ctrl.makeMove(Letter.getLetter(0), 7, 7);
        ctrl.makeMove(Letter.getLetter(87), 8, 7);
        ctrl.makeMove(Letter.getLetter(1), 9, 7);
        ctrl.makeMove(Letter.getLetter(38), 10, 7);
        assertTrue(ctrl.submitMove());
    }

    @Test
    public void testClearMoves() {
        ctrl.makeMove(Letter.getLetter(0), 7, 7);
        ctrl.makeMove(Letter.getLetter(1), 8, 7);
        ctrl.clearMoves();
        assertEquals(ctrl.currentModel(), new ScrabbleModel("Player One", "Player Two").toString());
    }

    @Test
    public void testGetScore() {
        assertEquals("<html>Player One 0<br>Player Two 0</html>", ctrl.getScoreBoard());
    }

    @Test
    public void testGetHand() {
        List<Letter> hand = ctrl.getCurrHand();
        assertNotNull(hand);
        assertEquals(7, hand.size());
    }

    @Test
    public void testSwapTurns() {
        String initialPlayer = ctrl.getCurrName();
        ctrl.swapTurns();
        assertNotEquals(initialPlayer, ctrl.getCurrName());
    }

    @Test
    public void testTilesLeft() {
        int tilesBefore = ctrl.tilesLeft();
//        System.out.println(tilesBefore);
        ctrl.makeMove(Letter.getLetter(0), 7, 7);
        ctrl.submitMove();
        int tilesAfter = ctrl.tilesLeft();
//        System.out.println(tilesAfter);
        assertTrue(tilesAfter == tilesBefore);
    }

    @Test
    public void testDiscardLetters() {
        ArrayList<Letter> discard = new ArrayList<>(ctrl.getCurrHand());
        ctrl.discardLetters(discard);
        assertEquals(7, ctrl.getCurrHand().size()); // New letters should replace the discarded ones
    }

    @Test
    public void testQuitGame() {
        ctrl.quitGame();
        assertEquals(84, ctrl.tilesLeft());
    }

    @Test
    public void testStartOver() {
        ctrl.startOver();
        assertEquals(84, ctrl.tilesLeft()); // Assuming a full set of tiles is 100
    }



    @Test
    public void testDelObservers() {
        ctrl.delObservers();
        // Similar to addObserver, ensure no exceptions are thrown
    }

    @Test
    public void testConstructorNoArgs() {
        ScrabbleController defaultCtrl = new ScrabbleController();
        assertNotNull(defaultCtrl.getCurrHand());
    }

    @Test
    public void testCurrentModel() {
        String board = ctrl.currentModel();
        assertNotNull(board);
        assertFalse(board.isEmpty());
    }
    
    @Test
	public void testAddObserver() {
		// Not sure how to test this
	}
    
    @Test
	public void testIsGameOver() {
		assertFalse(ctrl.isGameOver());
	}
}
