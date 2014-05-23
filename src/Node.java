import java.util.ArrayList;


public class Node {
	public Node(int x, int y){
		north = null;
		south = null;
		east = null;
		west = null;
		xPos = x;
		yPos = y;
		inMaze = false;
		treasure = false;
		path = false;
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

	public boolean isTreasure() {
		return treasure;
	}

	public void setTreasure(boolean treasure) {
		this.treasure = treasure;
	}

	/**
	 * @return the inMaze
	 */
	public boolean isInMaze() {
		return inMaze;
	}
	
	public int[] getWalls(){
		//ArrayList<Character> connections = new ArrayList<Character>();
		int[] connections = new int[4];
		if(this.isConnected(0, -1)){//wall
			connections[3] = 0;
		} else {
			connections[3] = 1;
		}
		if(this.isConnected(1, 0)){
			connections[2] = 0;
		} else {
			connections[2] = 1;
		}
		if(this.isConnected(0, 1)){
			connections[1] = 0;
		} else {
			connections[1] = 1;
		}
		if(this.isConnected(-1, 0)){
			connections[0] = 0;
		} else {
			connections[0] = 1;
		}
		return connections;
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
	
	public void setPath(boolean p) {
		path = p;
	}
	
	public boolean isOnPath() {
		return path;
	}
	
	boolean inMaze;
	boolean treasure;
	boolean path;
	Node north;
	Node south;
	Node east;
	Node west;
	int xPos;
	int yPos;
	
	
	
}
