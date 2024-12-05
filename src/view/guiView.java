/*
 * Nathan, Jay, Kory, Steven
 * 
 * File: guiView.java
 * 
 * Description: This class creates the view for a game of Scrabble to be
 * played
 * 
 */

package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.swing.*;

import aggregates.Letter;
import controller.ScrabbleController;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;

public class guiView extends JFrame {

	/**
	 * Eclipse told us to add this
	 */
	private static final long serialVersionUID = 1L;

	private ScrabbleController ctrl;

	private JPanel cards = new JPanel(new CardLayout());
	private CardLayout cl = (CardLayout) (cards.getLayout());
	private Random rand = new Random();

	private ArrayList<Letter> discardList = new ArrayList<Letter>();

	private ImagePanel startPanel;
	private ImagePanel mainPanel;
	private ImagePanel boardPanel;
	private JPanel wrapperPanel;
	private JPanel handPanel;
	private JLabel score;
	private JLabel left;
	private JButton submitButton;
	private JButton discardButton;
	private JButton clearButton;
	private JButton quitButton;
	private JButton skipButton;
	private InfoLabel playedWord;

	private guiView() {
		setUp();
		start();
	}

	private void setUp() {
		this.setName("Scrabble");
		this.setSize(1800, 800);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
	}

	private void start() {
		/**
		 * This method just initializes the two main panels that will be used for the
		 * game (the start menu and the actual game)
		 */
		startPanel = new ImagePanel("backgroundForMain.jpg");
		startPanel.setName("start");

		mainPanel = new ImagePanel("backgroundForMain.jpg");
		mainPanel.setName("main");

		cards.add(startPanel, startPanel.getName());
		cards.add(mainPanel, mainPanel.getName());

		this.add(cards, BorderLayout.CENTER);
		cl.show(cards, "start");

		startMenu();
	}

	private void startMenu() {
		/**
		 * This method sets up the start menu for the view
		 */

		// GridBag because it is the best layout
		startPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 0;

		// button that takes you into the game
		JButton startButton = new JButton("Start Game");
		startPanel.add(startButton, gbc);

		// text fields for people to add custom names for the model
		// this is optional
		gbc.gridy++;
		JLabel player1 = new JLabel("Enter Player Name");
		startPanel.add(player1, gbc);

		gbc.gridx++;
		JTextField player1Name = new JTextField();
		player1Name.setPreferredSize(new Dimension(200, 30));
		startPanel.add(player1Name, gbc);

		gbc.gridy++;
		gbc.gridx = 0;
		JLabel player2 = new JLabel("Enter Player Name");
		startPanel.add(player2, gbc);

		gbc.gridx++;
		JTextField player2Name = new JTextField();
		player2Name.setPreferredSize(new Dimension(200, 30));
		startPanel.add(player2Name, gbc);

		// this button takes you the main game
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				// the GUI is what randomly selects the first player
				// also don't need to add names as there is a default controller and model
				// constructor
				if (player1Name.getText().isEmpty() && player2Name.getText().isEmpty()) {
					ctrl = new ScrabbleController();
				} else {
					String name1 = rand.nextBoolean() ? player1Name.getText() : player2Name.getText();
					String name2 = name1.equals(player1Name.getText()) ? player2Name.getText() : player1Name.getText();
					ctrl = new ScrabbleController(name1, name2);
				}
				makeBoard();
				currHand();
				addButtons();
				cl.show(cards, "main");
			}
		});

		startPanel.revalidate();
		startPanel.repaint();
	}

	private void makeBoard() {
		/**
		 * This method sets up the visual 15x15 board of TileLabels plus the info panel
		 * off to the side as well
		 */
		String boardStr = ctrl.currentModel();
		Scanner boardScanner = new Scanner(boardStr);

		// Create the board panel with a background image
		boardPanel = new ImagePanel("background.jpg");
		boardPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;

		// sets up a 15 by 15 board
		int i = 0;
		while (boardScanner.hasNextLine()) {
			boardScanner.nextLine();

			gbc.gridx = i;
			for (int k = 0; k < 15; k++) {
				gbc.gridy = k;
				boardPanel.add(new TileLabel(i, k, ctrl), gbc);
			}
			i++;
		}
		boardScanner.close();
		boardPanel.setPreferredSize(new Dimension(600, 600));

		// info labels that are stored in the model... have default texts
		score = new InfoLabel(ctrl, "score");
		left = new InfoLabel(ctrl, "left");
		score.setText(ctrl.getScoreBoard());
		left.setText("Tiles Remaining: " + String.valueOf(ctrl.tilesLeft()));

		// Wrap the board panel in another panel to center it
		wrapperPanel = new JPanel();
		wrapperPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
		wrapperPanel.setOpaque(true);
		wrapperPanel.add(boardPanel, BorderLayout.WEST);

		// place the info labels in a visually pleasing way
		JPanel infoPanel = new JPanel(new GridBagLayout());
		GridBagConstraints infoGBC = new GridBagConstraints();
		infoPanel.setSize(new Dimension(400, 600));
		infoGBC.insets = new Insets(5, 5, 5, 5);
		infoGBC.gridx = 0;
		infoGBC.gridy = 0;
		infoGBC.fill = GridBagConstraints.HORIZONTAL;
		infoGBC.anchor = GridBagConstraints.CENTER;
		TitledBorder border = BorderFactory.createTitledBorder("GameInfo");
		border.setTitleFont(new Font("Arial", Font.BOLD, 16));
		infoPanel.setBorder(border);

		// played word does not have a default message, waits for play
		playedWord = new InfoLabel(ctrl, "currPlay");
		infoPanel.add(score, infoGBC);
		infoGBC.gridy++;
		infoPanel.add(left, infoGBC);
		infoGBC.gridy++;

		infoPanel.add(playedWord, infoGBC);

		wrapperPanel.add(infoPanel, BorderLayout.EAST);
		mainPanel.add(wrapperPanel, BorderLayout.CENTER);
	}

	private void currHand() {
		/**
		 * This method creates the player hands visually with LetterLabels
		 */
		handPanel = new JPanel();
		TitledBorder border = BorderFactory.createTitledBorder(ctrl.getCurrName());
		border.setTitleFont(new Font("Arial", Font.BOLD, 16));
		handPanel.setBorder(border);
		handPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;

		List<Letter> hand = ctrl.getCurrHand();
		gbc.gridx = 0;
		gbc.gridy = 0;
		for (Letter l : hand) {
			LetterLabel label = new LetterLabel(l, ctrl);
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setVerticalAlignment(SwingConstants.CENTER);
			label.setPreferredSize(new Dimension(50, 50));

			handPanel.add(label, gbc);
			gbc.gridx++;
		}
		if (hand.size() < 7) {
			int i = hand.size();
			while (i < 8) {
				JLabel label = new JLabel();
				label.setPreferredSize(new Dimension(50, 50));
				label.setBackground(Color.BLACK);
				label.setOpaque(true);
				handPanel.add(label, gbc);
				gbc.gridx++;
				i++;
			}
		}
		mainPanel.add(handPanel, BorderLayout.SOUTH);
	}

	private void addButtons() {
		/**
		 * This method adds all of the buttons to GUI
		 */
		makeSubmitButton();
		makeDiscardButton();
		makeClearButton();
		makeQuitButton();
		makeSkipButton();

		handPanel.add(submitButton);
		handPanel.add(discardButton);
		handPanel.add(skipButton);
		handPanel.add(clearButton);
		handPanel.add(quitButton);
		revalidate();

	}

	private void makeSubmitButton() {
		/**
		 * This method sets up the submit button. It will take a user's play and have
		 * the model validate it.
		 */
		submitButton = new JButton("Submit");
		submitButton.setSize(new Dimension(50, 50));
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				// call the subitMove method
				boolean response = ctrl.submitMove();

				// if true, valid word
				if (response) {
					colorFlash(new Color[] { Color.GREEN, Color.BLACK });
					// check if game is over
					if (ctrl.isGameOver()) {
						restartGame();

						// otherwise just swap turns
					} else {
						ctrl.swapTurns();
						mainPanel.remove(handPanel);
						LetterTransferHandler.clearPlacedList();
						currHand();
						addButtons();
					}
				} else {
					for (Component c : handPanel.getComponents()) {
						if (c instanceof LetterLabel) {
							c.setBackground(Color.CYAN);
						}
					}
					colorFlash(new Color[] { Color.RED, Color.BLACK });
					LetterTransferHandler.clearTileLabels();

				}
			}
		});
	}

	private void makeDiscardButton() {
		/**
		 * This method makes the discard button for the GUI.
		 */
		discardButton = new JButton("Discard");
		discardButton.setSize(new Dimension(50, 50));

		// we can't discard if there are less than 7 tiles, so lock the button
		// if so
		if (ctrl.tilesLeft() < 7) {
			discardButton.setBackground(Color.RED);
		} else {
			discardButton.addActionListener(new ActionListener() {

				int count = 0;

				public void actionPerformed(ActionEvent e) {

					// first time the button is clicked, it has the LetterLabels
					// listen for clicks... click labels will be discarded
					if (count == 0) {
						discardButton.setBackground(Color.GREEN);
						removeListeners(submitButton);
						removeListeners(clearButton);
						removeListeners(quitButton);
						removeListeners(skipButton);
						LetterTransferHandler.clearTileLabels();
						for (Component c : handPanel.getComponents()) {
							if (c instanceof LetterLabel) {

								// reset the colors back and remove the listeners so you can't drag
								c.setBackground(Color.CYAN);
								for (MouseListener ml : c.getMouseListeners()) {
									c.removeMouseListener(ml);
								}
								c.addMouseListener(new MouseAdapter() {
									public void mousePressed(MouseEvent e) {
										discardList.add(((LetterLabel) c).getLetter());
										c.setBackground(Color.MAGENTA);
									}

								});

							}
						}
						count++;

						// the second time discard is clicked is when they get discarded.
						// turns change after this
					} else {
						colorFlash(new Color[] { Color.MAGENTA, Color.BLACK, Color.PINK });
						ctrl.discardLetters(discardList);
						LetterTransferHandler.clearPlacedList();
						discardList.clear();
						count = 0;
						mainPanel.remove(handPanel);
						ctrl.swapTurns();
						currHand();
						addButtons();
						revalidate();
					}
				}

			});
		}
	}

	private void removeListeners(JButton b) {
		/**
		 * This method removes the listeners from the buttons It is meant for when
		 * discard is used
		 */
		b.setBackground(Color.RED);
		for (ActionListener al : b.getActionListeners()) {
			b.removeActionListener(al);
		}
	}

	private void makeClearButton() {
		/**
		 * This makes the clear button which removes all placed but not submitted tiles
		 * from the board
		 */
		clearButton = new JButton("Clear");
		clearButton.setSize(new Dimension(50, 50));
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				colorFlash(new Color[] { Color.YELLOW, Color.BLACK });
				for (Component c : handPanel.getComponents()) {
					if (c instanceof LetterLabel) {
						c.setBackground(Color.CYAN);
					}
				}
				ctrl.clearMoves();
				LetterTransferHandler.clearTileLabels();
			}
		});
	}

	private void makeQuitButton() {
		/**
		 * This button allows a user to forfeit the game
		 */
		quitButton = new JButton("Quit");
		quitButton.setSize(new Dimension(50, 50));
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrl.quitGame();
				restartGame();
			}
		});
	}

	private void makeSkipButton() {
		/**
		 * This button allows a user to skip their turn.
		 * 
		 * Hitting the discard button twice without selecting any tiles is effectively a
		 * skip as well, however, the button is locked when tiles are < 7, so a skip
		 * button is necessary
		 */
		skipButton = new JButton("Skip Turn");
		skipButton.setSize(new Dimension(50, 50));
		skipButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				colorFlash(new Color[] { Color.RED, Color.GREEN, Color.BLUE });
				playedWord.updateInfo(ctrl.getCurrName()+ " skipped their turn!");
				ctrl.swapTurns();
				mainPanel.remove(handPanel);
				LetterTransferHandler.clearTileLabels();
				ctrl.clearMoves();
				currHand();
				addButtons();
			}
		});
	}

	private void restartGame() {
		/**
		 * This prompts the users to play again or exit the application
		 */
		int response = JOptionPane.showConfirmDialog(mainPanel, "Do you want to play again?", "Play Again",
				JOptionPane.YES_NO_OPTION);

		if (response == JOptionPane.YES_OPTION) {
			// Handle playing again
			ctrl.startOver();
			mainPanel.remove(handPanel);
			mainPanel.remove(wrapperPanel);
			ctrl.delObservers();
			makeBoard();
			currHand();
			addButtons();
			revalidate();
		} else if (response == JOptionPane.NO_OPTION) {
			// Handle not playing again
			System.exit(0);
		}
	}

	private void colorFlash(Color[] colorArr) {
		Timer timer = new Timer(100, null);

		// timer added just to make it a bit clearer when the turn is skipped
		timer.addActionListener(new ActionListener() {
			private int count = 0;
			private Color[] colors = colorArr;

			@Override
			public void actionPerformed(ActionEvent e) {
				wrapperPanel.setBorder(BorderFactory.createLineBorder(colors[count % colors.length], 5));
				count++;
				if (count >= colors.length * 3) {
					timer.stop();
					wrapperPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
				}
			}
		});
		timer.start();
	}

	public static void main(String args[]) {
		guiView view = new guiView();
		view.setVisible(true);
	}
}
