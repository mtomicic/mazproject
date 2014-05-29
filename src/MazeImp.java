import java.util.ArrayList;
import java.util.Comparator;
import java.util.EventObject;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Random;

import javax.swing.event.EventListenerList;




public class MazeImp{
	

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
		start = grid[0][0];
		player = new Player(0,0);
		Random rn = new Random();
		int i = rn.nextInt(xSize);
		int j = rn.nextInt(ySize);
		end = grid[xSize-1][ySize-1];
		treasure = new ArrayList<Treasure>();
		addTreasure();
	}
	
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
		//addTreasure();
	}
	
	public void addLoops(int percentage){
		Random rn = new Random();
		for(int i = 1; i < xSize-1; i++){
			for(int j = 1; j < ySize-1; j++){
				if(rn.nextInt(100) < percentage){
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
	
	public void markCell(int x, int y){
		grid[x][y].setInMaze();
		addFrontier(x, y + 1);
		addFrontier(x, y - 1);
		addFrontier(x + 1, y);
		addFrontier(x - 1, y);
		frontier.remove(grid[x][y]);
	}
	
	private void addFrontier(int x, int y){
		if(isValid(x,y)){
			if(!frontier.contains(grid[x][y]) && !grid[x][y].isInMaze()){
				frontier.add(grid[x][y]);
			}
		}
	} 
	
	private boolean isValid(int x, int y){
		if(x >= 0 && y >= 0 && x < xSize && y < ySize){
			return true;
		}
		return false;
	}
	
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

	public char[][] getCharMaze(){
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
	
	public void showPath(ArrayList<Node> path){
		for(int i = 0; i < path.size() - 1; i++){
			path.get(i).setPath(true);
		}
	}
	
	public void clearPath(ArrayList<Node> path){
		for(int i = 0; i < path.size() - 1; i++){
			path.get(i).setPath(false);
		}
	}
	
	

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
		
		
		
		open.add(new State(start, 0, h.getH(start, goal), null));
		
		while(!open.isEmpty()){
			State curr = open.poll();
			//System.out.println(curr.getNode());
			if(curr.getNode().equals(goal)){
				return constructPath(curr);
			}else{
				if(curr.getNode().getNorth() != null){
					open.add(new State(curr.getNode().getNorth(), curr.getG() + 1, 
							h.getH(curr.getNode(), goal), curr));
				}
				
				if(curr.getNode().getSouth() != null){
					open.add(new State(curr.getNode().getSouth(), curr.getG() + 1, 
							h.getH(curr.getNode(), goal), curr));
				}
				
				if(curr.getNode().getEast() != null){
					open.add(new State(curr.getNode().getEast(), curr.getG() + 1, 
							h.getH(curr.getNode(), goal), curr));
				}
				
				if(curr.getNode().getWest() != null){
					open.add(new State(curr.getNode().getWest(), curr.getG() + 1, 
							h.getH(curr.getNode(), goal), curr));
				}
			}
		}
		
		
		return null;
		
			
	}
	
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
	
	private void addTreasure(){
		Random rn = new Random();
		
		for(int i = 0; i < xSize; i++){
			for(int j = 0; j < ySize; j++){
				if(grid[i][j].isDeadEnd()){
					if(rn.nextInt(100) >= 75){
						treasure.add(new Treasure(i,j));
					}
				}
			}
		}
		
	}
	
	public void movePlayer(int dx, int dy){
		if (playerCanMove(dx, dy)){
			player.move(dx, dy);
			firePlayerMoved();
			if(grid[player.getX()][player.getY()].equals(end)){
				firePlayerFinished();
			}
			for(Treasure t:treasure){
				if(player.getX() == t.getX() && player.getY() == t.getY()){
					treasure.remove(t);
					player.giveMoney(t.getValue());
					fireTreasureCollected();
					break;
				}
			}
			
		}
	}
	
	private void fireTreasureCollected() {
		MazeListener[] listeners = mazeListeners.getListeners(MazeListener.class);
		EventObject e = new EventObject(this);
		for(MazeListener listener:listeners){
			listener.treasureCollected(e);
		}
	}

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
	
	public int[] getWallType(int xPos, int yPos){  
		return grid[xPos][yPos].getWalls();
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

	public Node getCell(int x, int y){
		return grid[x][y];
	}
	
	public Player getPlayer() {
		// TODO Auto-generated method stub
		return player;
	}
	
	public ArrayList<Treasure> getTreasure() {
		return (ArrayList<Treasure>) treasure.clone();
	}

	Node start;
	Node end;
	int xSize;
	int ySize;
	ArrayList<Node> frontier;
	Node[][] grid;
	EventListenerList mazeListeners;
	private Player player;
	private ArrayList<Treasure> treasure;

	
}
