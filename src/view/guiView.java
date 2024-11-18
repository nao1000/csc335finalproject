package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

import javax.swing.*;

import controller.ScrabbleController;

public class guiView extends JFrame {

	private ScrabbleController ctrl;

	public guiView() {
		ctrl = new ScrabbleController();
		setUp();
	}

	private void setUp() {
		this.setName("Scrabble");
		this.setSize(500, 500);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
	}

	private void start() {
		String boardStr = ctrl.currentModel();
		Scanner boardScanner = new Scanner(boardStr);

		JPanel boardPanel = new JPanel();
		this.add(boardPanel);
		boardPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);

		int i = 0;
		while (boardScanner.hasNextLine()) {
			String data[] = boardScanner.nextLine().split(" ");

			gbc.gridx = i;
			for (int k = 0; k < 15; k++) {
				gbc.gridy = k;
				boardPanel.add(new JButton(data[k]), gbc);
			}
			i++;
		}
	}

	public static void main(String args[]) {
		guiView view = new guiView();
		view.start();
		view.setVisible(true);
	}
}
