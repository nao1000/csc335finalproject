/*
 * Nathan, Jay, Kory, Steven
 * 
 * File: DictionaryTrie.java
 * 
 * Description: This creates the object used to
 * store all of the valid Scrabble words and allow for
 * more efficient word valid word comparison.
 */
package model;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryTrie {
	/**
	 * This class creates a typical Trie data structure (parse tree)
	 * that is used to efficiently validate words made by a player
	 * in Scrabble.
	 */

	private class TrieNode {
		/**
		 * This trie itself is a complex tree of TrieNodes that store
		 * will hold a character and an array of all of it's children.
		 * A word is found if one of those children has their terminal
		 * flag set to true
		 */
		private char c;
		private boolean terminal = false;
		private TrieNode children[];
		
		public TrieNode(char c) {
			this.c = c;
			children = new TrieNode[26];
		}
		
		// getter and setters
		public char getChar() {
			return c;
		}
		
		public boolean isWord() {
			return terminal;
		}
		
		public void setTerminal() {
			terminal = true;
		}
		
		public void addChild(char nextC) {
			/**
			 * This method stores a new Node as child to this node.
			 * Using ASCII to help index, we an easy way to store and look
			 * up Nodes.
			 * 
			 * @param (char) nextC: a character for a new node
			 * 
			 * @pre nextC must be alphabetical
			 */
			children[nextC - 'a'] = new TrieNode(nextC);
		}
		
		public TrieNode getChild(char nextC) {
			/**
			 * This method gets the child that would be the letter given.
			 * Used with the childExist() method.
			 * 
			 * @parameter (char) nextC: the character to retrieve
			 * 
			 * @return (TrieNode): node of the next character
			 * 
			 * @pre nextC must be alphabetical
			 */
			return children[nextC - 'a'];
		}
		
		public boolean childExist(char nextC) {
			/**
			 * This method ensures the child exists by making sure
			 * and instance of the Node is at the index location.
			 * 
			 * @param (nextC) nextC: the character we are looking for
			 * 
			 * @return (boolean): whether or not the character is there
			 * 
			 * @pre nextC must be alphabetical
			 */
			return children[nextC - 'a'] instanceof TrieNode;
		}
	}
	
	private TrieNode root;
	private TrieNode curr;
	
	public DictionaryTrie() {
		root = new TrieNode(' ');
		curr = root;
		initializeDictionary();
	}
	
	private void initializeDictionary() {
		/**
		 * This method initializes the Trie with the Scrabble
		 * dictionary.
		 */
		
		// make sure file exists
		try {
			String fn = "dictionary.txt";
			File myFile = new File(fn);
			Scanner myReader = new Scanner(myFile);
//
//			// Parses through whole file
			while (myReader.hasNextLine()) {
				placeWord(myReader.nextLine());
		}
			myReader.close();
		} catch (Exception FileNotFoundException) {
			System.out.println("Error: File not found");
			System.exit(1);
		}
	}
	
	private void placeWord(String s) {
		/**
		 * This method iterates through a string and puts the
		 * word into the Trie/parse tree character by character.
		 * 
		 * It is set to private because in the context of the game, we
		 * will not be adding words to the dictionary. The words
		 * are given from a text file and there is no need to add words
		 * mid game
		 * 
		 * @param (String) s: a word to be added to the Trie
		 * 
		 * @pre s is only alphabetical
		 *
		 */
		s = s.toLowerCase();
		for (int i = 0; i < s.length(); i++) {
			
			// curr allows us to keep going down the Trie
			// without having to reset each time
			if (!curr.childExist(s.charAt(i))) {
				curr.addChild(s.charAt(i));
			}
			curr = curr.getChild(s.charAt(i));
		}
		
		// mark the last node so we know it is a word
		curr.setTerminal();
		
		// reset curr to the top/rot
		curr = root;
	}
	
	public boolean isWord(String s) {
		/**
		 * This method validates a String by seeing if it
		 * exists in the Trie and is marked as a word
		 * 
		 * @param (String) s: the word to find
		 * 
		 * @return (boolean): whether or not the word exists
		 * 
		 * @pre s must be alphabetical
		 */
		
		// ensure curr is reset before each use
		curr = root;
		s = s.toLowerCase();
		for (int i = 0; i < s.length(); i++) {
			
			// if a child doesn't exist, we know it can't be a word
			if (!curr.childExist(s.charAt(i))) {
				return false;
			}
			curr = curr.getChild(s.charAt(i));
		}
		
		// still have to make sure the last node is a terminal
		return curr.isWord();
	}
}
