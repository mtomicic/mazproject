
public class MazeObject {
	public MazeObject(int x, int y){
		this.posX = x;
		this.posY = y;
	}
	/**
	 * @param tileX the tileX to set
	 */
	public void setX(int x) {
		this.posX = x;
	}

	/**
	 * @param tileY the tileY to set
	 */
	public void setY(int y) {
		this.posY = y;
	}
	
	public int getX() {
		return posX;
	}

	public int getY() {
		return posY;
	}
	
	protected int posX;
	protected int posY;
}
