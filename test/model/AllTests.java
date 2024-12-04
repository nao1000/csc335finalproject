/*
 * Nathan, Jay, Kory, Steven
 * 
 * File: AllTests.java
 * 
 * Description: Convenient file to run all of the test files at once
 * The test leads to near 100% coverage on the non GUI related items.
 * Some of the methods in the model are difficult to test (such as the end
 * game scenarios) and were better tested via playing the game.
 */

package model;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import aggregates.LetterBagTest;
import aggregates.LetterTest;
import aggregates.PlayerTest;
import aggregates.TestTrie;
import aggregates.TileTest;
import controller.TestController;
import aggregates.TestMove;

// Replace `YourFirstTest` and `YourSecondTest` with the actual class names of your test files.
@Suite
@SelectClasses({ TestScrabbleModelConVertical.class, TestScrabbleModelConHorizontal.class, TestScrabbleModel.class,
		LetterBagTest.class, LetterTest.class, PlayerTest.class, TestMove.class, TestTrie.class, TileTest.class,
		TestController.class

})
public class AllTests {
	// This class remains empty; it's used only as a holder for the above
	// annotations.
}
