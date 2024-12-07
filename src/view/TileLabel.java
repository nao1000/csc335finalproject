/*
 * Nathan, Jay, Kory, Steven
 * 
 * File: TileLabel.java
 * 
 * Description: This class is a personalized JLabel that
 * is used to represent the Tile objects in the board from the model
 * and will also store placed letters. It can not be dragged itself, but 
 * the data from LetterLabels can be dragged to it and stored.
 * 
 */

package view;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

import aggregates.Letter;
import controller.ScrabbleController;

public class TileLabel extends JLabel {

	/**
	 * Eclipse told us to add this
	 */
	private static final long serialVersionUID = 1L;

	private Optional<Letter> l;
	private int x, y;

	public TileLabel(int x, int y, ScrabbleController controller) {
		this.x = x;
		this.y = y;
		this.setText(" ");
		initializeHandler(controller);
		this.l = Optional.empty();

		// setting this ensures the tiles don't shift when data is transferred
		Dimension fixedSize = new Dimension(40, 40);
		this.setMinimumSize(fixedSize);
		this.setPreferredSize(fixedSize);
		this.setMaximumSize(fixedSize);

		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}

	public void setLetter(Letter l) {
		/**
		 * This method sets the letter field when LetterLabel data is dragged over the
		 * tile
		 * 
		 * @param (Letter) l : letter being dropped onto the label
		 */
		this.l = Optional.of(l);
		this.setText(l.getChar());
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setVerticalAlignment(SwingConstants.CENTER);
		this.setForeground(Color.BLACK);
		this.setOpaque(true);
		this.setBackground(Color.LIGHT_GRAY);
	}

	/**
	 * This method removes the data and resets the tile
	 */
	public void emptyTile() {
		this.setBackground(null);
		this.l = Optional.empty();
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.setText(" ");
		this.setOpaque(false);
	}
	
	public boolean isEmpty() {
		/**
		 * This method checks if the Optional<Letter> contains a value
		 * 
		 * @return (boolean): whether or not there is a letter on tile
		 */
		return l.isEmpty();
	}

	public int myGetX() {
		/**
		 * @return (int) : x value of tile
		 */
		return x;
	}

	public int myGetY() {
		/**
		 * @return (int) : y value of tile
		 */
		return y;
	}

	public Letter getLetter() {
		/**
		 * @return (Letter) : letter data stored at the label
		 */
		return l.get();
	}

	private void initializeHandler(ScrabbleController controller) {
		/**
		 * This initializes the handler so the label can accept dropped data
		 * 
		 * @param (ScrabbleController) controller : ctrl used for the game
		 */
		this.setTransferHandler(new LetterTransferHandler(controller));
	}
}
