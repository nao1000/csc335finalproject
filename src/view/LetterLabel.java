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

    private Letter l;

    public LetterLabel(Letter l, ScrabbleController controller) {
        this.l = l;
        this.setText(l.getChar());
        this.setBackground(Color.GRAY);
        this.setOpaque(true);
        initializeHandler(controller);
    }

    public Letter getLetter() {
        return l;
    }

    private void initializeHandler(ScrabbleController controller) {
        this.setTransferHandler(new LetterTransferHandler(controller));
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                JComponent comp = (JComponent) e.getSource();
                TransferHandler handler = comp.getTransferHandler();
                handler.exportAsDrag(comp, e, TransferHandler.COPY);
            }
            
            public void mouseReleased(MouseEvent e) {
            	if (l.isInUse()) {
            		((LetterLabel)e.getSource()).setBackground(Color.RED);
            	}
            }
        });
    }
}