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
import java.util.Optional;
import java.util.Scanner;

import javax.swing.*;

import aggregates.Letter;
import controller.ScrabbleController;

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
    JPanel handPanel;
    JLabel score;
    JLabel left;

    public guiView() {
        ctrl = new ScrabbleController();
        setUp();
        start();
    }

    private void setUp() {
    	GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		gd.setFullScreenWindow(this);
        this.setName("Scrabble");
  
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
        makeBoard();
        currHand();
        addButtons();
    }

    private void startMenu() {
        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(cards, "main");
            }
        });

        startPanel.add(startButton);
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
            gbc.gridx = i;
            for (int k = 0; k < 15; k++) {
                gbc.gridy = k;
                boardPanel.add(new TileLabel(i, k, ctrl), gbc);
            }
            i++;
        }
        boardScanner.close();

        boardPanel.setPreferredSize(new Dimension(600,600));
        score = new JLabel(ctrl.getScoreBoard());
        left = new JLabel(String.valueOf(ctrl.tilesLeft()));
        
        // Wrap the board panel in another panel to center it
        JPanel wrapperPanel = new JPanel(new GridBagLayout());
        GridBagConstraints wrapperGbc = new GridBagConstraints();
        wrapperGbc.gridx = 0;
        wrapperGbc.gridy = 0;
        wrapperGbc.weightx = 1.0;
        wrapperGbc.weighty = 1.0;
        wrapperGbc.anchor = GridBagConstraints.CENTER;

        // Add the board panel to the wrapper panel
        wrapperPanel.add(boardPanel, wrapperGbc);

        // Add the wrapper panel to the main panel
        mainPanel.add(score, BorderLayout.PAGE_START);
        mainPanel.add(left, BorderLayout.WEST);
        mainPanel.add(wrapperPanel, BorderLayout.CENTER);
    }


    private void currHand() {
        handPanel = new JPanel();
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
                	score.setText(ctrl.getScoreBoard());
                	left.setText(String.valueOf(ctrl.tilesLeft()));
                	currHand();
                	addButtons();	
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
    	handPanel.add(submitButton);
    	handPanel.add(discardButton);
    	handPanel.add(clearButton);
    	revalidate();
 
    }


    public static void main(String args[]) {
        guiView view = new guiView();
        view.setVisible(true);
    }
}
