package model;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestTrie {

	static DictionaryTrie trie;

	@BeforeAll 
	static void setUp() throws FileNotFoundException {
		trie = new DictionaryTrie();
		try {
			String fn = "dictionary.txt";
			File myFile = new File(fn);
			Scanner myReader = new Scanner(myFile);

			// Parses through whole file
			while (myReader.hasNextLine()) {
				trie.placeWord(myReader.nextLine());
			}
			myReader.close();
		} catch (Exception FileNotFoundException) {
			throw new FileNotFoundException();
		}

	}

	@Test
	void test_isWord() {
		assertEquals(trie.isWord("ZYMES"), true);
		assertEquals(trie.isWord("CATS"), true);
		assertEquals(trie.isWord("q"), false);
	}
	
	void test_getChar() {
		
	}

}
