
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


import javax.swing.*;

/**
 * Maze View Class
 * @author mtom521
 *
 */
public class Board extends JPanel implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Constructor for board, takes a mazeImp
	 * @param m the mazeImp board will use to paint the maze
	 */
	public Board(MazeImp m) {
		mapWidth = m.getxSize();
		mapHeight = m.getySize();
		mazeModel = m;
		
		map = new Map();
		addKeyListener(new Al());
		setFocusable(true);
		timer = new Timer(25, this);
		timer.start();
		pathDisplay = false;
	}
	
	/**
	 * sets the path to be displayed
	 */
	public void displayPath(){
		pathDisplay = true;
	}
	
	/**
	 * hides the path
	 */
	public void hidePath(){
		pathDisplay = false;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
	}
	
	/**
	 * paint for swing
	 */
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		super.paint(g);

		drawMaze(g);
		drawEnd(g);
		
		drawFuel(g);
		
		if(pathDisplay){
			drawPath(g2d);
		}
		drawPlayer(g);
		if(mazeModel.survivalOn()){
			drawMask(g2d);
		}
		
	}	
	
	/**
	 * draws the end tile
	 * @param g
	 */
	private void drawEnd(Graphics g){
		g.drawImage(map.getEndImg(),
				(mazeModel.getEnd().getxPos()*tileSize + (tileSize - 12)/2),
				(mazeModel.getEnd().getyPos()*tileSize + (tileSize - 12)/2), null);
	}
	
	/**
	 * Draws the fuel around the map
	 * @param g
	 */
	private void drawFuel(Graphics g){
		ArrayList<Fuel> fuels= mazeModel.getFuel();
		for(Fuel t: fuels){
		 	g.drawImage(map.getFuelImg(),
		 				t.getX() *tileSize , 
		 				t.getY() * tileSize , null);
		}
	}
	/**
	 * Draws the player
	 * @param g
	 */
	private void drawPlayer(Graphics g){
		g.drawImage(mazeModel.getPlayer().getPlayer(), 
				mazeModel.getPlayer().getX() * tileSize ,
				mazeModel.getPlayer().getY() * tileSize , null);
	}
	
	/**
	 * Draws the maze by drawing the tiles and then the walls
	 * @param g
	 */
	
	private void drawMaze(Graphics g){
	
		Graphics2D g2d = (Graphics2D) g;
		
		for (int y = 0; y < mapHeight; y++) {
			for (int x = 0; x < mapWidth; x++) {
				g2d.drawImage(map.getWoodImg(),x*tileSize, y*tileSize, null);
			}
		}
		g2d.setStroke(new BasicStroke(4));
		for (int y = 0; y < mapHeight; y++) {
			for (int x = 0; x < mapWidth; x++) {
				Node cell = mazeModel.getCell(x, y);
				if(!cell.isConnected(0, -1)){                                                    //draws the north wall
					g2d.drawLine(x*tileSize, y*tileSize, (x+1)*tileSize , y*tileSize);
				}
				if(!cell.isConnected(0, 1)){
					g2d.drawLine(x*tileSize, (y+1)*tileSize, (x+1)*tileSize, (y+1)*tileSize);	//draws the south wall
				}
				if(!cell.isConnected(1, 0)){
					g2d.drawLine((x+1)*tileSize, (y)*tileSize, (x+1)*tileSize, (y+1)*tileSize);	//draws the east wall
				}
				if(!cell.isConnected(-1, 0)){
					g2d.drawLine((x)*tileSize, (y)*tileSize, (x)*tileSize, (y+1)*tileSize);	    //draws the west wall
				}
			}
		}
		
	}
	
	/**
	 * Draws a black rectangle with a hole of a diameter proportional to the fuel of the player
	 * @param g
	 */
	
	private void drawMask(Graphics g){
		Graphics2D g2d  = (Graphics2D) g;
		BufferedImage image = new BufferedImage(mapWidth*tileSize, mapHeight*tileSize, BufferedImage.TYPE_INT_ARGB);
		
		Player player = mazeModel.getPlayer();
		double topLeftX = player.getX()*tileSize - player.getFuel()/2 + (tileSize - playerSize)/2 + playerSize/2;
		double topLeftY = player.getY()*tileSize - player.getFuel()/2 + (tileSize - playerSize)/2 + playerSize/2; 
		
		Area mask = new Area(new Rectangle2D.Double(0, 0, mapWidth*tileSize, mapHeight*tileSize));
		Area hole = new Area(new Ellipse2D.Double(topLeftX, topLeftY, player.getFuel(), player.getFuel()));
		mask.subtract(hole);
		
		g2d.setColor(Color.black);
		g2d.fill(mask);
		g2d.drawImage(image, 0, 0, this);
	}
	
	/**
	 * Local KeyAdapter for listening to the board
	 * @author mtom521
	 *
	 */
	
	public class Al extends KeyAdapter {
		
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();
			//System.out.println(mazeModel.getCell(mazeModel.getPlayer().getX(), mazeModel.getPlayer().getY()));
			if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
				mazeModel.movePlayer(0, -1);
			} else if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
				mazeModel.movePlayer(0, 1);	
			} else if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
				mazeModel.movePlayer(-1, 0);
			} else if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
				mazeModel.movePlayer(1, 0);		
			}
			
		}
		
		public void keyReleased(KeyEvent e) {
			
		}
		
		public void keyTyped(KeyEvent e) {
			
		}
		
	}


	/**
	 * multiplies the mapWidth by the tileSize
	 * @return the pixel width of the board
	 */
	public int getPixelWidth() {
		return (mapWidth)*tileSize;
	}
	
	/**
	 * multiplies the mapHeight by the tileSize
	 * @return the pixel height of the board
	 */
	public int getPixelHeight() {
		return (mapHeight)*tileSize;
	}
	
	
	/**
	 * Generates a path and draws it using Graphics2D draw Line
	 * @param g Graphics
	 */
	
	public void drawPath(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		ArrayList<Node> path = mazeModel.getPath(mazeModel.getCell(mazeModel.getPlayer().getX(),mazeModel.getPlayer().getY()), 
								mazeModel.getEnd(), new ManhattenHeuristic()); 
	
		for(int i = 0; i < path.size() - 1; i++){
			g2d.setColor(Color.red);
			g2d.drawLine(path.get(i).getxPos()*tileSize + tileSize/2, path.get(i).getyPos()*tileSize + tileSize/2, 
						path.get(i+1).getxPos()*tileSize + tileSize/2, path.get(i+1).getyPos()*tileSize + tileSize/2);
		}
	}
	
	
	private Timer timer;
	private Map map;
	private MazeImp mazeModel;
	
	private boolean pathDisplay;
	
	private static int tileSize = 25;
	private static int playerSize = 10;
	private int mapWidth;
	private int mapHeight;
}
