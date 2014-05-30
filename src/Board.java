

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.event.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.EventObject;

import javax.swing.*;

public class Board extends JPanel implements ActionListener{
	

	private Timer timer;
	private Map map;
	private MazeImp mazeModel;
	private int mapWidth;
	private int mapHeight;
	private boolean survivalOn;
	private boolean pathDisplay;
	private static int tileSize = 25;
	private static int playerSize = 10;
	private static int treasureSize = 4;

	
	public Board(MazeImp m) {
		mapWidth = m.getxSize();
		mapHeight = m.getySize();
		mazeModel = m;
		
		map = new Map();
		addKeyListener(new Al());
		
		setFocusable(true);
		
		//testMaze.dumpMaze();
		
		timer = new Timer(25, this);
		timer.start();
		survivalOn = false;
		pathDisplay = false;
	}
	
	public void displayPath(){
		pathDisplay = true;
	}
	
	public void hidePath(){
		pathDisplay = false;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		//
	}
	
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		super.paint(g);
		g2d.setStroke(new BasicStroke(4));
		
		drawMaze(g);
		
		g.drawImage(map.getEndImg(),
					(mazeModel.getEnd().getxPos()*tileSize + (tileSize - 12)/2),
					(mazeModel.getEnd().getyPos()*tileSize + (tileSize - 12)/2), null);
		

		g.drawImage(mazeModel.getPlayer().getPlayer(), 
					mazeModel.getPlayer().getX() * tileSize ,
					mazeModel.getPlayer().getY() * tileSize , null);
		
		ArrayList<Fuel> fuels= mazeModel.getFuel();
		for(Fuel t: fuels){
		 	g.drawImage(map.getFuelImg(),
		 				t.getX() *tileSize , 
		 				t.getY() * tileSize , null);
		}
		if(survivalOn){
			drawMask(g2d);
		}
		if(pathDisplay){
			drawPath(g);
		}
	}
	
	
	public void drawMaze(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		for (int y = 0; y < mapHeight; y++) {
			for (int x = 0; x < mapWidth; x++) {
				//System.out.println("printing " + testMaze.getCell(x, y));
				Node cell = mazeModel.getCell(x, y);
				g2d.drawImage(map.getWoodImg(),x*tileSize, y*tileSize, null);
				//g.drawImage(map.getGrass(), x*10, y*10, null);
				if(!cell.isConnected(0, -1)){
					g2d.drawLine(x*tileSize, y*tileSize, (x+1)*tileSize , y*tileSize);
				}
				if(!cell.isConnected(0, 1)){
					g2d.drawLine(x*tileSize, (y+1)*tileSize, (x+1)*tileSize, (y+1)*tileSize);
				}
				if(!cell.isConnected(1, 0)){
					g2d.drawLine((x+1)*tileSize, (y)*tileSize, (x+1)*tileSize, (y+1)*tileSize);
				}
				if(!cell.isConnected(-1, 0)){
					g2d.drawLine((x)*tileSize, (y)*tileSize, (x)*tileSize, (y+1)*tileSize);
				}
					
	
			}
		}
	}
	
	public void drawMask(Graphics g){
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



	public void activateSurvival() {
		survivalOn = true;
		
	}

	public int getPixelWidth() {
		return (mapWidth+1)*tileSize;
	}
	
	public int getPixelHeight() {
		return (mapHeight+1)*tileSize;
	}
	
	
	
	
	public void drawPath(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		ArrayList<Node> path = mazeModel.getPath(mazeModel.getCell(mazeModel.getPlayer().getX(),mazeModel.getPlayer().getY()), 
								mazeModel.getEnd(), new ManhattenHeuristic()); 
	
		for(int i = 0; i < path.size() - 1; i++){
			g2d.setColor(Color.red);
			g2d.drawLine(path.get(i).getxPos()*tileSize + tileSize/2, path.get(i).getyPos()*tileSize + tileSize/2, 
						path.get(i+1).getxPos()*tileSize + tileSize/2, path.get(i+1).getyPos()*tileSize + tileSize/2);
		}
		System.out.println(path);
	}
	
	
}
