package view;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

import aggregates.Letter;
import controller.ScrabbleController;

public class TileLabel extends JLabel {

	private Optional<Letter> l;
    private int x, y;

    public TileLabel(int x, int y, ScrabbleController controller) {
        this.x = x;
        this.y = y;
        this.setText(" ");
        initializeHandler(controller);
        this.l = Optional.empty();

        Dimension fixedSize = new Dimension(40,40);
        this.setMinimumSize(fixedSize);
        this.setPreferredSize(fixedSize);
        this.setMaximumSize(fixedSize);
        
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    public void setLetter(Letter l) {
        this.l = Optional.of(l);
        this.setText(l.getChar());
        this.setForeground(Color.BLACK);
        this.setOpaque(true); 
        this.setBackground(Color.LIGHT_GRAY);
    }
    
    public void emptyTile() {
    	this.l = Optional.empty();
    	this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    	this.setText(" ");
    	this.setOpaque(false); 
    }
    
    public int myGetX() {
    	return x;
    }
    
    public int myGetY() {
    	return y;
    }
    
    public Letter getLetter() {
    	return l.get();
    }

    private void initializeHandler(ScrabbleController controller) {
        this.setTransferHandler(new LetterTransferHandler(controller));
    }
}
