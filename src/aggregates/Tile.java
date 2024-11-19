package aggregates;

import java.util.NoSuchElementException;
import java.util.Optional;

public class Tile {

	private Optional<Letter> occupyingL;
	private int multiplier;
	
	public Tile(int multiplier) {
		occupyingL = Optional.empty();
		this.multiplier = multiplier;
	}
	
	public int getMulti() {
		return multiplier;
	}
	
	public boolean placeLetterTile(Letter l) {
		if (occupyingL.isEmpty()) {
			occupyingL = Optional.of(l);
			return true;
		}
		return false;
	}
	
	public boolean removeLetterTile() {
		if (!occupyingL.isEmpty()) {
			occupyingL = Optional.empty();
			return true;
		}
		return false;
	}
	
	public boolean isUnoccupied() {
		return occupyingL.isEmpty();
	}
	
	public Letter getOccupyingLetter() throws NoSuchElementException {
		return occupyingL.get();
	}
	
	public String toString() {
		if (!this.isUnoccupied()) {
			return occupyingL.get().toString();
		}
		return String.valueOf(multiplier) + " ";
	}
}