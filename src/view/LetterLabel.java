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
	 * 
	 */
	private Letter l;

    public LetterLabel(Letter l, ScrabbleController controller) {
        this.l = l;
        this.setText(l.getChar() + " : " + l.getPoints());
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

//if (moveStart.getY() == moveEnd.getY()) {
//// Check if valid word across entire horizontal row
//ArrayList<Move> horizontal = this.checkHorizontal(x, y, 1);
//if (horizontal != null) {
//	// Then check if all vertical words are valid
//	localScore += calculateScoreB(horizontal);
//	for (Move m : currMoves) {
//		ArrayList<Move> vertical = this.checkVertical(m.getX(), y, 2);
//		if (vertical == null) {
//			valid = false;
//		}
//		if (vertical.size() > 1) {
//			localScore += calculateScoreB(vertical);
//		}
//		
//	}	
//} else {
//	valid = false;
//}
//
//} else {
//// If vertical case then check vertical full length
//ArrayList<Move> vertical = this.checkVertical(x, y, 1);
//if (vertical != null) {
//	// Then check all new horizontal cases
//	localScore += calculateScoreB(vertical);
//
//	for (Move m : currMoves) {
//		ArrayList<Move> horizontal = this.checkHorizontal(x, m.getY(), 2);
//		if (horizontal == null) {
//			valid = false;
//		}
//		if (horizontal.size() > 1) {
//			localScore += calculateScoreB(horizontal);
//		}
//	}
//} else {
//	valid = false;
//}
//}