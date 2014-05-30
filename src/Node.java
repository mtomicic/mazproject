import java.util.ArrayList;

/**
 * Maze Node
 * @author mtom521
 *
 */
public class Node {
	/**
	 * Node Constructor
	 * @param x
	 * @param y
	 */
	public Node(int x, int y){
		north = null;
		south = null;
		east = null;
		west = null;
		xPos = x;
		yPos = y;
		inMaze = false;
	}
	
	
	
	
	/**
	 * @return the north
	 */
	public Node getNorth() {
		return north;
	}
	/**
	 * @param north the north to set
	 */
	public void setNorth(Node north) {
		this.north = north;
	}
	/**
	 * @return the south
	 */
	public Node getSouth() {
		return south;
	}
	/**
	 * @param south the south to set
	 */
	public void setSouth(Node south) {
		this.south = south;
	}
	/**
	 * @return the east
	 */
	public Node getEast() {
		return east;
	}
	/**
	 * @param east the east to set
	 */
	public void setEast(Node east) {
		this.east = east;
	}
	/**
	 * @return the west
	 */
	public Node getWest() {
		return west;
	}
	/**
	 * @param west the west to set
	 */
	public void setWest(Node west) {
		this.west = west;
	}
	/**
	 * @return the xPos
	 */
	public int getxPos() {
		return xPos;
	}
	/**
	 * @return the yPos
	 */
	public int getyPos() {
		return yPos;
	}
	
	public void setInMaze(){
		inMaze = true;
	}




	/**
	 * @return the inMaze
	 */
	public boolean isInMaze() {
		return inMaze;
	}
	
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append(xPos);
		sb.append(",");
		sb.append(yPos);
		sb.append(")");
		return sb.toString();
	}
	
	/**
	 * @pre dir_x or dir_y are either 1,0,-1 and the other is 0
	 * @param dir_x x direction
	 * @param dir_y y direction
	 * @return whether this node is connected to the node in the given direction
	 */
	public boolean isConnected(int dir_x, int dir_y) {
		if(dir_x == -1){
			if(west != null){
				return true;
			}else{
				return false;
			}
		}
		if(dir_x == 1){
			if(east != null){
				return true;
			}else{
				return false;
			}
		}
		if(dir_x == 0){
			if(dir_y == 1){
				if(south != null){
					return true;
				}else{
					return false;
				}
			}
			if(dir_y == -1){
				if(north != null){
					return true;
				}else{
					return false;
				}
			}
		}
		return false;
	}
	

	@Override
	public boolean equals(Object o){
		if(((Node) o).getxPos() == this.xPos && ((Node) o).getyPos() == this.yPos){
			return true;
		}
		return false;
	}
	/**
	 * checks whether the node is a dead end or not
	 * @return true if this node is a dead end
	 */
	public boolean isDeadEnd() {
		int numberOfWalls = 0;
		if(north == null){
			numberOfWalls++;
		}
		if(south == null){
			numberOfWalls++;
		}
		if(east == null){
			numberOfWalls++;
		}
		if(west == null){
			numberOfWalls++;
		}
		if(numberOfWalls == 3){
			return true;
		}else{
			return false;
		}
	}
	
	boolean inMaze;
	Node north;
	Node south;
	Node east;
	Node west;
	int xPos;
	int yPos;
}
