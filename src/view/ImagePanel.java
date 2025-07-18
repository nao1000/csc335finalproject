/*
 * Nathan, Jay, Kory, Steven
 * 
 * File: ImagePanel.java
 * 
 * Description: This file creates a personalized JPanel
 * that uses an image files as it's background
 */

package view;

import java.awt.Graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	/**
	 * Eclipse told us to add this
	 */
	private static final long serialVersionUID = 1L;

	private BufferedImage image;

	public ImagePanel(String imagePath) {

		try {
			this.image = ImageIO.read(new File(imagePath));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null) {
			g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		}
	}
}