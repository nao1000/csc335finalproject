/*
 * Nathan, Jay, Kory, Steven
 * File: DictionaryTrie.java
 * Description: This creates the object used to
 * store all of the valid Scrabble words and allow for
 * more efficient word valid word comparison.
 * 
 */
package model;

import java.util.ArrayList;

public class DictionaryTrie {

	private class TrieNode {
		char c;
		boolean terminal = false;
		
		TrieNode children[] = new TrieNode[26];
		
		public TrieNode(char c) {
			this.c = c;
		}
		
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
			children[nextC - 'a'] = new TrieNode(nextC);
		}
		
		public TrieNode getChild(char nextC) {
			return children[nextC - 'a'];
		}
		
		public boolean childExist(char nextC) {
			return children[nextC - 'a'] instanceof TrieNode;
		}
	}
	
	private TrieNode root;
	private TrieNode curr;
	
	public DictionaryTrie() {
		root = new TrieNode(' ');
		curr = root;
	}
	
	public void placeWord(String s) {
		/*
		 * @pre s is only alphabetical
		 *
		 */
		s = s.toLowerCase();
		for (int i = 0; i < s.length(); i++) {
			if (!curr.childExist(s.charAt(i))) {
				curr.addChild(s.charAt(i));
			}
			curr = curr.getChild(s.charAt(i));
		}
		curr.setTerminal();
		curr = root;
	}
	
	public boolean isWord(String s) {
		curr = root;
		s = s.toLowerCase();
		for (int i = 0; i < s.length(); i++) {
			if (!curr.childExist(s.charAt(i))) {
				return false;
			}
			curr = curr.getChild(s.charAt(i));
		}
		return curr.isWord();
	}
	
}
