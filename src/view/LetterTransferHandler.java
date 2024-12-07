/*
 * Nathan, Jay, Kory, Steven
 * 
 * File: LetterTransferHandler.java
 * 
 * Description: This class provides the handler for labels which enables
 * the necessary drag and drop feature for the game to be played
 * 
 */

/*
 * AI CITATION: The drag and drop feature was completely foreign, and evidently
 * not super easy to learn. Copilot was utilized to help make most of this
 * file. The importData() method was altered by us to do as we wanted. The 
 * two static methods were also us, but the actual drag and drop feature was via AI.
 * This was cleared by the professor as well!
 */

package view;

import javax.swing.*;
import aggregates.Letter;
import controller.ScrabbleController;

import java.awt.Color;
import java.awt.datatransfer.*;
import java.io.IOException;
import java.util.ArrayList;

public class LetterTransferHandler extends TransferHandler {
	/**
	 * Eclipse told us to add
	 */
	private static final long serialVersionUID = 1L;
	private DataFlavor letterFlavor = new DataFlavor(Letter.class, "Letter");
	private ScrabbleController controller;
	private static ArrayList<TileLabel> placedList = new ArrayList<TileLabel>();

	public LetterTransferHandler(ScrabbleController controller) {
		this.controller = controller;
	}

	@Override
	protected Transferable createTransferable(JComponent c) {

		LetterLabel label = (LetterLabel) c;
		Letter letter = label.getLetter();

		// Check if the letter is in use and prevent transfer if it is
		// This locks the LetterLabel until it is cleared
		System.out.println(letter.getChar());
		if (controller.letterInUse(letter)) {
			return null;
		}

		return new LetterTransferable(letter, c);
	}

	@Override
	public int getSourceActions(JComponent c) {
		return COPY;
	}

	@Override
	public boolean canImport(TransferHandler.TransferSupport support) {
		return support.isDataFlavorSupported(letterFlavor);
	}

	@Override
	public boolean importData(TransferHandler.TransferSupport support) {

		// another super smart fix by me. We don't want to drop onto other
		// LetterLabels so make sure the component isn't an instance of that
		if (!canImport(support) || support.getComponent() instanceof LetterLabel) {
			return false;
		}

		try {

			// final super smart fix for me... if it is already yellow, we shouldn't
			// override it (if you want to replace a tile, hit clear!)
			TileLabel label = (TileLabel) support.getComponent();
			if (label.getBackground() == Color.YELLOW || !label.isEmpty()) {
				return false;
			}
			Transferable transferable = support.getTransferable();
			Letter letter = (Letter) transferable.getTransferData(letterFlavor);
			label.setLetter(letter);
			label.setBackground(Color.YELLOW);
			// Call method on ScrabbleController
			controller.makeMove(label.getLetter(), label.myGetX(), label.myGetY());
			placedList.add(label);
			return true;
		} catch (UnsupportedFlavorException | IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	private static class LetterTransferable implements Transferable {
		private Letter letter;
		private LetterLabel draggedLabel;

		public LetterTransferable(Letter letter, JComponent c) {
			this.letter = letter;
			draggedLabel = (LetterLabel) c;
		}

		@Override
		public DataFlavor[] getTransferDataFlavors() {
			return new DataFlavor[] { new DataFlavor(Letter.class, "Letter") };
		}

		@Override
		public boolean isDataFlavorSupported(DataFlavor flavor) {
			return new DataFlavor(Letter.class, "Letter").equals(flavor);
		}

		@Override
		public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
			if (!isDataFlavorSupported(flavor)) {
				throw new UnsupportedFlavorException(flavor);
			}

			// I am super smart and figured this out
			// Want labels to turn red when they can't be dragged for
			// visual purposes. Change it hear makes it line up properly
			draggedLabel.setBackground(Color.RED);
			return letter;
		}
	}

	public static void clearTileLabels() {
		/**
		 * This static method clears out all of the TileLabels This means a word was not
		 * actually placed after it was submitted.
		 */
		for (TileLabel tl : placedList) {
			tl.emptyTile();
		}
		placedList.clear();
	}

	public static void clearPlacedList() {
		/**
		 * This static method only clears the lists. The word was placed
		 */
		for (TileLabel tl : placedList) {
			tl.setForeground(Color.BLACK);
			tl.setOpaque(true);
			tl.setBackground(Color.LIGHT_GRAY);
		}
		placedList.clear();
	}
}
