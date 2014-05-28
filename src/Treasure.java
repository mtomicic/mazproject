
public class Treasure extends MazeObject {

	public Treasure(int x, int y) {
		super(x, y);
		value = 10;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}
	
	int value;
}
