/*
 * Nathan, Jay, Kory, Steven
 * 
 * File: LetterLabel.java
 * 
 * Description: This class creates a personalized JLabel that
 * represents the Letters in a players hand. They are equipped with
 * a drag a drop feature to move data around.
 * 
 */

package view;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.TransferHandler;

import aggregates.Letter;
import controller.ScrabbleController;

public class LetterLabel extends JLabel {

	/**
	 * Eclipse told us to add this
	 */
	private static final long serialVersionUID = 1L;

	private Letter l;

	public LetterLabel(Letter l, ScrabbleController controller) {
		this.l = l;
		this.setText(l.toString());
		this.setBackground(Color.CYAN);
		this.setOpaque(true);
		initializeHandler(controller);
	}

	public Letter getLetter() {
		/**
		 * Simple getter to get the data stored in the label
		 * 
		 * @return (Letter) : the letter stored in the label
		 */
		return l;
	}

	private void initializeHandler(ScrabbleController controller) {
		/**
		 * This method is a helper for the constructor. It initializes the handler that
		 * enables the drag and feature to let the game work.
		 */
		this.setTransferHandler(new LetterTransferHandler(controller));
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				JComponent comp = (JComponent) e.getSource();
				TransferHandler handler = comp.getTransferHandler();
				handler.exportAsDrag(comp, e, TransferHandler.COPY);
			}
		});
	}
}