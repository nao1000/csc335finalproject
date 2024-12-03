package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.*;

import aggregates.Letter;
import controller.ScrabbleController;
import javax.swing.BorderFactory;



public class guiView extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ScrabbleController ctrl;

    private JPanel cards = new JPanel(new CardLayout());
    private CardLayout cl = (CardLayout) (cards.getLayout());
    
    ArrayList<Letter> discardList = new ArrayList<Letter>();
    ArrayList<TileLabel> placedList = new ArrayList<TileLabel>();

    JPanel startPanel;
    JPanel mainPanel;
    ImagePanel boardPanel;
    JPanel wrapperPanel;
    JPanel handPanel;
    JLabel score;
    JLabel left;

    public guiView() {
        setUp();
        start();
    }

    private void setUp() {
//    	GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
//		gd.setFullScreenWindow(this);
        this.setName("Scrabble");
        this.setSize(1800,800);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
    }

    private void start() {
        startPanel = new JPanel();
        startPanel.setName("start");

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setName("main");
        

        cards.add(startPanel, startPanel.getName());
        cards.add(mainPanel, mainPanel.getName());

        this.add(cards, BorderLayout.CENTER);
        cl.show(cards, "start");

        startMenu();
        
    }

    private void startMenu() {
        startPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JButton startButton = new JButton("Start Game");
        startPanel.add(startButton, gbc);

        gbc.gridy++;
        JLabel player1 = new JLabel("Enter Player One Name");
        startPanel.add(player1, gbc);

        gbc.gridx++;
        JTextField player1Name = new JTextField();
        player1Name.setPreferredSize(new Dimension(200, 30)); // Set preferred size
        startPanel.add(player1Name, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        JLabel player2 = new JLabel("Enter Player Two Name");
        startPanel.add(player2, gbc);

        gbc.gridx++;
        JTextField player2Name = new JTextField();
        player2Name.setPreferredSize(new Dimension(200, 30)); // Set preferred size
        startPanel.add(player2Name, gbc);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player1Name.getText().isEmpty() && player2Name.getText().isEmpty()) {
                    ctrl = new ScrabbleController();
                } else {
                    ctrl = new ScrabbleController(player1Name.getText(), player2Name.getText());
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

        boardPanel.setPreferredSize(new Dimension(600,600));
        score = new InfoLabel(ctrl, "score");
        left = new InfoLabel(ctrl, "left");
        score.setText(ctrl.getScoreBoard());
        left.setText("Tiles Remaining: " + String.valueOf(ctrl.tilesLeft()));
        
        // Wrap the board panel in another panel to center it
        wrapperPanel = new JPanel();
        wrapperPanel.add(boardPanel, BorderLayout.WEST);  
        JPanel infoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints infoGBC = new GridBagConstraints();
        infoPanel.setSize(new Dimension(400,600));
        infoGBC.insets = new Insets(5,5,5,5);
        infoGBC.gridx=0;
        infoGBC.gridy=0;
        infoGBC.fill = GridBagConstraints.HORIZONTAL;
        infoGBC.anchor = GridBagConstraints.CENTER;
        infoPanel.setBorder(BorderFactory.createTitledBorder("GameInfo"));
        InfoLabel playedWord = new InfoLabel(ctrl, "currPlay");
        infoPanel.add(score, infoGBC);
        infoGBC.gridy++;
        infoPanel.add(left, infoGBC);
        infoGBC.gridy++;
        
        infoPanel.add(playedWord, infoGBC);
        
        wrapperPanel.add(infoPanel, BorderLayout.EAST);
        mainPanel.add(wrapperPanel, BorderLayout.CENTER);
    }


    private void currHand() {
        handPanel = new JPanel();
        handPanel.setBorder(BorderFactory.createTitledBorder(ctrl.getCurrName()));
        handPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 1.0; // Set weights to ensure proper distribution
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        List<Letter> hand = ctrl.getCurrHand();
        gbc.gridx = 0;
        gbc.gridy = 0;
        for (Letter l : hand) {
            LetterLabel label = new LetterLabel(l, ctrl);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);
            
            // Set preferred size to make labels square-shaped and bigger
            label.setPreferredSize(new Dimension(50, 50)); // Adjust the dimensions as needed
            
            handPanel.add(label, gbc);
            gbc.gridx++;
        }
        mainPanel.add(handPanel, BorderLayout.SOUTH);
    }

    
    private void addButtons() {
    	GridBagConstraints gbc = new GridBagConstraints();
    	gbc.insets = new Insets(5,5,5,5);
    	
    	gbc.gridy = 0;
    	gbc.gridx = 0;
    	
    	JButton submitButton = new JButton("Submit");
    	submitButton.setSize(new Dimension(50,50));
    	submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean response = ctrl.submitMove();
                if (response) {
                	mainPanel.remove(handPanel);
                	LetterTransferHandler.clearPlacedList();
                	currHand();
                	addButtons();
                	if (ctrl.isGameOver()) {
                		restartGame();
                	}
                	else {
                		ctrl.swapTurns();
                	}
                } 
                else {
                	LetterTransferHandler.clearTileLabels();
    
                }
            }
        });
    	
    	JButton discardButton = new JButton("Discard");
    	discardButton.setSize(new Dimension(50,50));
    	if (ctrl.tilesLeft() < 7) {
    		discardButton.setBackground(Color.RED);
    	}
    	else {
	    	discardButton.addActionListener(new ActionListener() {
	    		
	    		int count = 0;
	    		
	    		public void actionPerformed(ActionEvent e ) {
	    			if (count == 0) {
	    				LetterTransferHandler.clearTileLabels();
	    				for (Component c : handPanel.getComponents()) {
	    					if (c instanceof LetterLabel) {
	    						c.addMouseListener(new MouseAdapter() {
	    							
	    							public void mousePressed(MouseEvent e) {
	    								discardList.add(((LetterLabel) c).getLetter());
	    								c.setBackground(Color.GREEN);
	    							}
	    							
	    						});
	    					}
	    				}
	    				count++;
	    			}
	    			else {
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
    	JButton clearButton = new JButton("Clear");
    	clearButton.setSize(new Dimension(50,50));
    	clearButton.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			ctrl.clearMoves();
    			LetterTransferHandler.clearTileLabels();
    		}
    	});
    	
    	JButton quitButton = new JButton("Quit");
    	quitButton.setSize(new Dimension(50,50));
    	quitButton.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			ctrl.quitGame();
    			restartGame();
    		}	
    	});
    	handPanel.add(submitButton);
    	handPanel.add(discardButton);
    	handPanel.add(clearButton);
    	handPanel.add(quitButton);
    	revalidate();
 
    }

    private void restartGame() {
    	int response = JOptionPane.showConfirmDialog(
			    mainPanel, 
			    "Do you want to play again?", 
			    "Play Again", 
			    JOptionPane.YES_NO_OPTION
			);

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

    public static void main(String args[]) {
        guiView view = new guiView();
        view.setVisible(true);
    }
}
