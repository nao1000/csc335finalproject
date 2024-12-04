/*
 * Nathan, Jay, Kory, Steven
 * 
 * File: InfoLabel.java
 * 
 * Description: This class creates a personalized JLabel used to
 * display relevant information of the current state of the game.
 */

package view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import controller.ScrabbleController;

public class InfoLabel extends JLabel {

	/**
	 * Eclipse told us to add this
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * InfoLabel (as well as the observer-like stuff in controller and model) were
	 * inspired by the Observer design pattern and CardLayout where I can store
	 * something of the GUI in the model to update when necessary, but also have the
	 * have associated "names" to make sure only the correct one is updated.
	 * 
	 * This DOES NOT follow the observer pattern, but it was inspired by it
	 */

	private String name;

	public InfoLabel(ScrabbleController ctrl, String name) {
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		ctrl.addObserver(this);
		this.name = name;
	}

	public void updateInfo(String nStr) {
		/**
		 * This method updates the label as desired
		 * 
		 * @param (String) nStr : information string
		 */
		this.setText("<html><body style='width: 180px'>" + nStr + "</body></html>");
	}

	public String getName() {
		/**
		 * To locate which label to use, they have IDs
		 * 
		 * @return (String) : name of the label
		 */
		return name;
	}

	@Override
	public Dimension getPreferredSize() {
		Dimension size = super.getPreferredSize();
		size.width = 250;
		size.height = 100;
		return size;
	}

}