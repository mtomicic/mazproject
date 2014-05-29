
public class Fuel extends MazeObject {

	public Fuel(int x, int y, int v) {
		super(x, y);
		value = v;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}
	
	int value;
}
