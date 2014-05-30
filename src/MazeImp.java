
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EventObject;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Random;
import javax.swing.event.EventListenerList;
/**
 * Maze Model Class
 * @author mtom521
 *
 */
public class MazeImp{
	
	/**
	 * MazeImp Constructor
	 * Generates a maze of size x,y 
	 * adds a player at position 0,0
	 * sets the start to 0,0
	 * sets the end to x-1,y-1
	 * If you want to change any of these settings use the various setters
	 * @param x xSize
	 * @param y ySize
	 */
	public MazeImp(int x, int y){
		xSize = x;
		ySize = y;
		grid = new Node[xSize+1][ySize+1];
		
		for(int i = 0; i < xSize; i++){
			for(int j = 0; j < ySize; j++){
				grid[i][j] = new Node(i, j);
			}
		}
		frontier = new ArrayList<Node>();
		this.generate();
		mazeListeners = new EventListenerList();
		
		player = new Player(0,0);
		
		fuels = new ArrayList<Fuel>();
		survival = false;
		
		start = grid[0][0];
		end = grid[xSize-1][ySize-1];
	}
	/**
	 * generates the maze using a Randomised Primm's algorithm
	 * Steps
	 * choose a random cell and mark it
	 * add all its neighbours to the frontier
	 * pick a random node in the frontier, add its neighbours to the frontier
	 * connect it to the maze
	 * if there is more than 1 way to connect it to the maze randomly choose
	 * choose another random node from the frontier and repeat
	 */
	private void generate(){
		Random rn = new Random();
		int x = rn.nextInt(xSize);
		int y = rn.nextInt(ySize);
		
		//System.out.println("start" +grid[x][y]);
		
		
		markCell(x, y);
		//System.out.println("frontier" +frontier);
		
		while(!frontier.isEmpty()){
			Node curr = frontier.remove(rn.nextInt(frontier.size())); //order n change to constant time
			//System.out.println("current" +curr);
			markCell(curr.getxPos(), curr.getyPos());
			//System.out.println("frontier" +frontier);
			ArrayList<Node> adj = getAdjacent(curr.getxPos(), curr.getyPos());
			
			if (adj.size() != 0){
				Node neighbour = adj.get(rn.nextInt(adj.size()));
			
				
				int dir_x =  neighbour.getxPos() - curr.getxPos();
				int dir_y =  neighbour.getyPos() - curr.getyPos();
				//System.out.println("node " + curr + " is connected to " + neighbour);
				if(dir_x == -1){
					curr.setWest(neighbour);
					neighbour.setEast(curr);
				}
				if(dir_x == 1){
					curr.setEast(neighbour);
					neighbour.setWest(curr);
				}
				if(dir_x == 0){
					if(dir_y == 1){
						curr.setSouth(neighbour);
						neighbour.setNorth(curr);
					}
					if(dir_y == -1){
						curr.setNorth(neighbour);
						neighbour.setSouth(curr);
					}
				}
				//System.out.println("neighbour :" + neighbour);
			}
		}
	}
	
	/**
	 * picks a random number nodes(based on the percentage) and removes a random wall from them(even if there was no wall to begin with)
	 * @param percentage number 1-100 
	 */
	public void removeWalls(int percentage){
		Random rn = new Random();
		for(int i = 1; i < xSize-1; i++){
			for(int j = 1; j < ySize-1; j++){
				if(rn.nextInt(200) < percentage){
					int side = rn.nextInt(4);
					if(side == 0){
						grid[i][j].setNorth(grid[i][j-1]);
						grid[i][j-1].setSouth(grid[i][j]);
					}
					if(side == 1){
						grid[i][j].setEast(grid[i+1][j]);
						grid[i+1][j].setWest(grid[i][j]);
					}
					if(side == 2){
						grid[i][j].setSouth(grid[i][j+1]);
						grid[i][j+1].setNorth(grid[i][j]);
					}
					if(side == 3){
						grid[i][j].setWest(grid[i-1][j]);
						grid[i-1][j].setEast(grid[i][j]);
					}
				}
			}
		}
	}
	
	/**
	 * marks a cell as in the maze and adds its neighbours to the frontier
	 * @param x x position
	 * @param y y position
	 */
	public void markCell(int x, int y){
		grid[x][y].setInMaze();
		addFrontier(x, y + 1);
		addFrontier(x, y - 1);
		addFrontier(x + 1, y);
		addFrontier(x - 1, y);
		frontier.remove(grid[x][y]);
	}
	
	/**
	 * Adds the node at x,y to the frontier if it is valid, not already in the maze, and not already in the frontier
	 * @param x x position
	 * @param y y position
	 */
	private void addFrontier(int x, int y){
		if(isValid(x,y)){
			if(!frontier.contains(grid[x][y]) && !grid[x][y].isInMaze()){
				frontier.add(grid[x][y]);
			}
		}
	} 
	
	/**
	 * checks if the node at x,y exists
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean isValid(int x, int y){
		if(x >= 0 && y >= 0 && x < xSize && y < ySize){
			return true;
		}
		return false;
	}
	
	/**
	 * gets all the adjacent nodes to the node at (x,y) that are not in the maze
	 * @param x x position
	 * @param y y position
	 * @return An ArrayList of Nodes containing the adjacent nodes
	 */
	private ArrayList<Node> getAdjacent(int x, int y){
		ArrayList<Node> adj = new ArrayList<Node>();
		//System.out.println("getting adjacent node to:" + grid[x][y]);
		if(isValid(x, y + 1) && grid[x][y+1].isInMaze()){
			adj.add(grid[x][y+1]);
		}
		if(isValid(x, y - 1) && grid[x][y-1].isInMaze()){
			adj.add(grid[x][y-1]);
		}
		if(isValid(x + 1, y) && grid[x+1][y].isInMaze()){
			adj.add(grid[x+1][y]);
		}
		if(isValid(x - 1, y) && grid[x-1][y].isInMaze()){
			adj.add(grid[x-1][y]);
		}
		//System.out.println("adj" +adj);
		return adj;
	}
	/**
	 * Outputs the maze as a 2d array of chars with explicit walls
	 * @return a 2d array of chars
	 */
	private char[][] getCharMaze(){
		char[][] charMaze = new char[2*xSize+1][2*ySize+1];
		for(int i = 0; i < 2*xSize + 1; i++){
			for(int j = 0; j < 2*ySize + 1; j++){
				if(i == 0 || i == 2*xSize || j == 0 || j == 2*ySize){
					charMaze[i][j] = '#';
					continue;
				}
				if(i%2 == 0 || j%2 == 0){
					charMaze[i][j] = '#';
				}else{
					charMaze[i][j] = ' ';
				}
			}
		}
		
		for(int i = 0; i < xSize; i++){
			for(int j = 0; j< ySize; j++){
				if(grid[i][j].isConnected(0,-1)){ //North
					charMaze[2*i+1][2*j] = ' ';
				}
				if(grid[i][j].isConnected(0,1)){ //South
					charMaze[2*i+1][2*j+2] = ' ';
				}
				if(grid[i][j].isConnected(1,0)){ //East
					charMaze[2*i+2][2*j+1] = ' ';
				}
				if(grid[i][j].isConnected(-1,0)){ //West
					charMaze[2*i][2*j+1] = ' ';
				}
			}
		}
		
		
		return charMaze;
	}
	
	/**
	 * draws the maze in the console
	 */
	public void dumpMaze() {
		int x = xSize;
		int y = ySize;
		char[][] charMaze = this.getCharMaze();
	
		System.out.println();
		for(int j = 0; j < 2*y+1; j++){
			for(int i = 0; i< 2*x+1; i++){
				System.out.print(charMaze[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
		
		for(int j = 0; j < y; j++){
			
			for(int i = 0; i< x; i++){
				System.out.print(grid[j][i] + " ");
				System.out.print("north" + grid[i][j].north + " ");
				System.out.print("east" + grid[i][j].east  + " ");
				System.out.print("south" + grid[i][j].south  + " ");
				System.out.print("west" + grid[i][j].west  + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	
	/**
	 * Randomises the start tile to be in the upper left hand quadrant of the maze;
	 */
	public void randomStart(){
		Random rn = new Random();
		int x = rn.nextInt(xSize/2);
		int y = rn.nextInt(ySize/2);
		
		start = grid[x][y];
		player = new Player(x,y);
	}
	
	/**
	 * Randomises the end tile to be in the lower right hand quadrant of the maze;
	 */
	public void randomEnd(){
		Random rn = new Random();
		int x = rn.nextInt(xSize/2) + xSize/2;
		int y = rn.nextInt(ySize/2) + ySize/2;
		
		end = grid[x][y];
		
	}

	/**
	 * A star search implementation which finds the shortest path to the goal node
	 * @param start starting node
	 * @param goal goal node
	 * @param h heuristic
	 * @return an ArrayList of nodes containing the path 
	 */
	public ArrayList<Node> getPath(Node start, Node goal, Heuristic h){
		PriorityQueue<State> open = new PriorityQueue<State>(11, new Comparator<State>(){
		
		public int compare(State arg0, State arg1) {
			if(arg0.getF() > arg1.getF()){
				return 1;
			}
			if(arg0.getF() < arg1.getF()){
				return -1;
			}
			return 0;
			}
			
		});
		HashSet<Node> closedSet = new HashSet<Node>();
		
		
		open.add(new State(start, 0, h.getH(start, goal), null));
		
		while(!open.isEmpty()){
			State curr = open.poll();
			closedSet.add(curr.getNode());
			//System.out.println(curr.getNode());
			if(curr.getNode().equals(goal)){
				return constructPath(curr);
			}else{
				if(curr.getNode().getNorth() != null && !closedSet.contains(curr.getNode().getNorth())){
					open.add(new State(curr.getNode().getNorth(), curr.getG() + 1,
							h.getH(curr.getNode().getNorth(), goal), curr));
				}
			
				if(curr.getNode().getSouth() != null && !closedSet.contains(curr.getNode().getSouth())){
					open.add(new State(curr.getNode().getSouth(), curr.getG() + 1,
							h.getH(curr.getNode().getSouth(), goal), curr));
				}
			
				if(curr.getNode().getEast() != null && !closedSet.contains(curr.getNode().getEast())){
					open.add(new State(curr.getNode().getEast(), curr.getG() + 1,
							h.getH(curr.getNode().getEast(), goal), curr));
				}
			
				if(curr.getNode().getWest() != null && !closedSet.contains(curr.getNode().getWest())){
					open.add(new State(curr.getNode().getWest(), curr.getG() + 1,
							h.getH(curr.getNode().getWest(), goal), curr));
				}
			}
		}
	
	
		return null;
	
	
	}
	
	/**
	 * Constructs the path from the last state
	 * @param s the last state
	 * @return an ArrayList of nodes containing the path 
	 */
	private ArrayList<Node> constructPath(State s){
		ArrayList<Node> reversedPath = new ArrayList<Node>();
		reversedPath.add(s.getNode());
		State p = s.getPrevious();
		while(p != null){
			reversedPath.add(p.getNode());
			p = p.getPrevious();
		}
		ArrayList<Node> path = new ArrayList<Node>();
		for(int i = reversedPath.size()-1; i >= 0 ; i--){	 
			path.add(reversedPath.get(i));
		}
		return path;
	}
	
	/**
	 * randomly distributes fuel across the maze based on the percentage
	 * @param value of the fuel to be added
	 */
	public void addFuel(int value){
		Random rn = new Random();
		
		for(int i = 0; i < xSize; i++){
			for(int j = 0; j < ySize; j++){
				if(grid[i][j].isDeadEnd() && !grid[i][j].equals(start) && !grid[i][j].equals(end)){
					if(rn.nextInt(100) >= 75){
						fuels.add(new Fuel(i,j, value));
					}
				}
			}
		}
		
	}
	/**
	 * moves the player
	 * @param dx direction x
	 * @param dy direction y
	 */
	public void movePlayer(int dx, int dy){
		if (playerCanMove(dx, dy)){
			player.move(dx, dy);
			firePlayerMoved();
			if(grid[player.getX()][player.getY()].equals(end)){
				firePlayerFinished();
			}
			for(Fuel t:fuels){
				if(player.getX() == t.getX() && player.getY() == t.getY()){
					fuels.remove(t);
					player.giveFuel(t.getValue());
					fireTreasureCollected();
					break;
				}
			}
			
		}
	}
	
	
	
	/**
	 * checks if player can move in direction dx, dy
	 * @param dx
	 * @param dy
	 * @return true if move is possible
	 */
	private boolean playerCanMove(int dx, int dy) {
		Node pos = getCell(player.getX(), player.getY());
		if(pos.isConnected(dx,dy)){
			return true;
		}else{
			return false;
		}
	}

	public void addMazeListener(MazeListener listener){
		mazeListeners.add(MazeListener.class, listener);
	}
	
	public void removeMazeListener(MazeListener listener){
		mazeListeners.remove(MazeListener.class, listener);
	}
	
	public void firePlayerMoved(){
		MazeListener[] listeners = mazeListeners.getListeners(MazeListener.class);
		EventObject e = new EventObject(this);
		for(MazeListener listener:listeners){
			listener.playerMoved(e);
		}
	}
	
	public void fireMazeRestarted(){
		MazeListener[] listeners = mazeListeners.getListeners(MazeListener.class);
		EventObject e = new EventObject(this);
		for(MazeListener listener:listeners){
			listener.mazeRestarted(e);
		}
	}
	
	public void firePlayerFinished(){
		MazeListener[] listeners = mazeListeners.getListeners(MazeListener.class);
		EventObject e = new EventObject(this);
		for(MazeListener listener:listeners){
			listener.playerFinished(e);
		}
	}
	
	public void fireFuelConsumed(){
		MazeListener[] listeners = mazeListeners.getListeners(MazeListener.class);
		EventObject e = new EventObject(this);
		for(MazeListener listener:listeners){
			listener.fuelConsumed(e);
		}
	}
	
	private void fireTreasureCollected() {
		MazeListener[] listeners = mazeListeners.getListeners(MazeListener.class);
		EventObject e = new EventObject(this);
		for(MazeListener listener:listeners){
			listener.treasureCollected(e);
		}
	}
	
	/**
	 * @return the xSize
	 */
	public int getxSize() {
		return xSize;
	}

	/**
	 * @return the ySize
	 */
	public int getySize() {
		return ySize;
	}
	
	/**
	 * gets the node at (x,y)
	 * @param x x position
	 * @param y y position
	 * @return the node at (x,y)
	 */
	public Node getCell(int x, int y){
		return grid[x][y];
	}
	
	/**
	 * @return the player of this maze
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * sets the player for this maze
	 * @param player player to be set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	/**
	 * makes the player consume fuel
	 */
	
	public void consumeFuel(){
		player.fuelDecrement();
		fireFuelConsumed();
	}
	
	/**
	 * gets the ArrayList of fuels
	 * @return fuels
	 */
	public ArrayList<Fuel> getFuel() {
		return (ArrayList<Fuel>) fuels.clone();
	}

	/**
	 * gets the end node
	 * @return the end node
	 */
	public Node getEnd() {
		return end;
	}
	
	/**
	 * turn survival on
	 */
	
	public void activateSurvival(){
		survival = true;
	}
	
	/**
	 * turn survival off
	 */
	public void deactivateSurvival(){
		survival = false;
	}
	
	/**
	 * checks whether survival mode is on or off
	 * @return true if survival is on
	 */
	public boolean survivalOn(){
		return survival;
	}
	
	boolean survival;
	Node start;
	Node end;
	int xSize;
	int ySize;
	ArrayList<Node> frontier;
	Node[][] grid;
	EventListenerList mazeListeners;
	private Player player;
	private ArrayList<Fuel> fuels;
}
