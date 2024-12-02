package view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import aggregates.Letter;
import controller.ScrabbleController;

public class guiView2 extends JFrame {

    private static final long serialVersionUID = 1L;

    private ScrabbleController ctrl;

    private JPanel cards = new JPanel(new CardLayout());
    private CardLayout cl = (CardLayout) (cards.getLayout());

    private ArrayList<Letter> discardList = new ArrayList<>();
    //private ArrayList<TileLabel> placedList = new ArrayList<>();

    private JPanel startPanel;
    private JPanel mainPanel;
    private ImagePanel boardPanel;
    private JPanel handPanel;
    private JLabel score;
    private JLabel left;

    public guiView2() {
        ctrl = new ScrabbleController();
        setUp();
        start();
    }

    private void setUp() {
        // Set cross-platform Look and Feel
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set basic window properties
        this.setTitle("Scrabble");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600); // Default window size
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize window
        this.setLocationRelativeTo(null); // Center window on screen
    }

    private void start() {
        startPanel = new JPanel();
        startPanel.setName("start");

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setName("main");
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

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
        startButton.addActionListener(e -> cl.show(cards, "main"));

        startPanel.add(startButton);
    }

    private void makeBoard() {
        String boardStr = ctrl.currentModel();
        Scanner boardScanner = new Scanner(boardStr);

        // Create board panel with a background image
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

        boardPanel.setPreferredSize(new Dimension(600, 600));
        score = new InfoLabel(ctrl, "score");
        left = new InfoLabel(ctrl, "left");
        score.setText(ctrl.getScoreBoard());
        left.setText(String.valueOf(ctrl.tilesLeft()));

        // Center board panel in wrapper panel
        JPanel wrapperPanel = new JPanel(new GridBagLayout());
        GridBagConstraints wrapperGbc = new GridBagConstraints();
        wrapperGbc.gridx = 0;
        wrapperGbc.gridy = 0;
        wrapperGbc.weightx = 1.0;
        wrapperGbc.weighty = 1.0;
        wrapperGbc.anchor = GridBagConstraints.CENTER;

        wrapperPanel.add(boardPanel, wrapperGbc);

        mainPanel.add(score, BorderLayout.PAGE_START);

        // Info panel
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setPreferredSize(new Dimension(200, 100));
        GridBagConstraints infoGBC = new GridBagConstraints();
        infoGBC.insets = new Insets(5, 5, 5, 5);
        infoGBC.gridx = 0;
        infoGBC.gridy = 0;
        infoGBC.anchor = GridBagConstraints.WEST;
        infoPanel.setBorder(BorderFactory.createTitledBorder("Game Info"));
        InfoLabel playedWord = new InfoLabel(ctrl, "currPlay");
        infoPanel.add(left, infoGBC);
        infoGBC.gridy++;
        infoPanel.add(playedWord, infoGBC);

        JPanel emptyPanel = new JPanel();
        emptyPanel.setPreferredSize(new Dimension(200, 50));
        infoGBC.gridy++;
        infoPanel.add(emptyPanel, infoGBC);

        mainPanel.add(infoPanel, BorderLayout.EAST);
        mainPanel.add(wrapperPanel, BorderLayout.CENTER);
    }

    private void currHand() {
        handPanel = new JPanel();
        handPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
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
            label.setPreferredSize(new Dimension(50, 50)); // Adjust size

            handPanel.add(label, gbc);
            gbc.gridx++;
        }
        mainPanel.add(handPanel, BorderLayout.SOUTH);
    }

    private void addButtons() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            if (ctrl.submitMove()) {
                mainPanel.remove(handPanel);
                LetterTransferHandler.clearPlacedList();
                currHand();
                addButtons();
            } else {
                LetterTransferHandler.clearTileLabels();
            }
        });

        JButton discardButton = new JButton("Discard");
        if (ctrl.tilesLeft() < 7) {
            discardButton.setBackground(Color.RED);
        } else {
            discardButton.addActionListener(new DiscardListener());
        }

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> {
            ctrl.clearMoves();
            LetterTransferHandler.clearTileLabels();
        });

        handPanel.add(submitButton);
        handPanel.add(discardButton);
        handPanel.add(clearButton);
        revalidate();
    }

    private class DiscardListener implements ActionListener {
        int count = 0;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (count == 0) {
                LetterTransferHandler.clearTileLabels();
                for (Component c : handPanel.getComponents()) {
                    if (c instanceof LetterLabel) {
                        c.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mousePressed(MouseEvent e) {
                                discardList.add(((LetterLabel) c).getLetter());
                                c.setBackground(Color.GREEN);
                            }
                        });
                    }
                }
                count++;
            } else {
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
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(guiView::new);
    }
}
