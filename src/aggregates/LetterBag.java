package aggregates;

import java.util.ArrayList;

public class LetterBag {
	
	private ArrayList<Letter> letterBag;
	
	public LetterBag() {
		letterBag = new ArrayList<Letter>();
	}
	
	public void fillBag() {
		letterBag.clear();
		for (int i = 0; i < 97; i++) {
			letterBag.add(Letter.getLetter(i));
		}
	}
	
	public Letter draw(int letterI) {
		Letter l = letterBag.get(letterI);
		letterBag.remove(letterI);
		return l;
	}
	
	public void addTo(Letter l) {
		letterBag.add(l);
	}
}
