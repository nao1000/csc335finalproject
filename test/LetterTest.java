import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import aggregates.Letter;
import model.ScrabbleModel;

public class LetterTest {

//	@Test
//	public void getLettersTest() {
//		for (int i = 0; i < 98; i++) {
//			System.out.println(Letter.getLetter(i));
//			
//		}
//		
	//}
	
	@Test
	public void makeBoard() throws FileNotFoundException {
		ScrabbleModel model = new ScrabbleModel();
		assertTrue(model.placeLetter(Letter.getLetter(0), 0, 0));
		System.out.println(model.toString());
		
	}
	
}
