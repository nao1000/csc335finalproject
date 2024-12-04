/*
 * Nathan, Jay, Kory, Steven
 * 
 * File: TestTrie.java
 * 
 * Description: JUnit for specifically DictionaryTrie.java
 */

package aggregates;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;

public class TestTrie {

	static DictionaryTrie trie = new DictionaryTrie();

	@Test
	public void test_isWord() {
		assertEquals(trie.isWord("ZYMES"), true);
		assertEquals(trie.isWord("CATS"), true);
		assertEquals(trie.isWord("q"), false);
		assertEquals(trie.isWord("TEST"), true);
	}
}
