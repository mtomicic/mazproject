
public class GameMaze {
	public GameMaze(int xSize, int ySize){
		maze = new MazeImp(xSize, ySize);
		initialiseGame();
	}
	
	private void initialiseGame(){
		simpleStartEnd();
		player = new Player(start.getxPos(), start.getyPos());
	}
	
	private void simpleStartEnd(){
		start = maze.getCell(0,0);
		end = maze.getCell(maze.getxSize() -1, maze.getySize()- 1);
	}
	
	private Node start;
	private Node end;
	private MazeImp maze;
	private Player player;
}
