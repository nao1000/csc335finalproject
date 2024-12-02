package view;

import javax.swing.*;
import aggregates.Letter;
import controller.ScrabbleController;

import java.awt.Color;
import java.awt.datatransfer.*;
import java.io.IOException;
import java.util.ArrayList;

public class LetterTransferHandler extends TransferHandler {
	private DataFlavor letterFlavor = new DataFlavor(Letter.class, "Letter");
    private ScrabbleController controller;
    private static ArrayList<TileLabel> placedList = new ArrayList<TileLabel>();

    public LetterTransferHandler(ScrabbleController controller) {
        this.controller = controller;
    }
    
    public LetterTransferHandler(ScrabbleController controller, LetterLabel ll) {
        this.controller = controller;
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        
        LetterLabel label = (LetterLabel) c;
        Letter letter = label.getLetter();

        // Check if the letter is in use and prevent transfer if it is
        if (letter.isInUse()) {
            return null;
        }

        return new LetterTransferable(letter);
    }

    @Override
    public int getSourceActions(JComponent c) {
        return COPY;
    }

    @Override
    public boolean canImport(TransferHandler.TransferSupport support) {
        return support.isDataFlavorSupported(letterFlavor);
    }

    @Override
    public boolean importData(TransferHandler.TransferSupport support) {
        if (!canImport(support)) {
            return false;
        }

        try {
            Transferable transferable = support.getTransferable();
            Letter letter = (Letter) transferable.getTransferData(letterFlavor);
            
          
            TileLabel label = (TileLabel) support.getComponent();
            label.setLetter(letter);
       
            // Call method on ScrabbleController
            controller.makeMove(label.getLetter(), label.myGetX(), label.myGetY());
            placedList.add(label);
            return true;
        } catch (UnsupportedFlavorException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static class LetterTransferable implements Transferable {
        private Letter letter;

        public LetterTransferable(Letter letter) {
            this.letter = letter;
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{new DataFlavor(Letter.class, "Letter")};
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return new DataFlavor(Letter.class, "Letter").equals(flavor);
        }

        @Override
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
            if (!isDataFlavorSupported(flavor)) {
                throw new UnsupportedFlavorException(flavor);
            }
            return letter;
        }
    }
    
    public static void clearTileLabels() {
    	for (TileLabel tl : placedList) {
    		tl.getLetter().setInUse(false);
    		tl.emptyTile();
    	}
    	placedList.clear();
    }
    
    public static void printMe() {
    	System.out.println(placedList.toString());
    }
    
    public static void clearPlacedList() {
    	placedList.clear();
    }
}
