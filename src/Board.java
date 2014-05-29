

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.EventObject;

import javax.swing.*;

public class Board extends JPanel implements ActionListener{
	

	private Timer timer;
	private Map map;
	private MazeImp mazeModel;
	private int mapWidth;
	private int mapHeight;
	private static int tileSize = 16;
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
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		//
	}
	
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		super.paint(g);
		g2d.setStroke(new BasicStroke(2));
		
		easyDraw(g);
		
		g.drawImage(map.getEndImg(),
					(mapWidth-1)*tileSize + (tileSize - 12)/2,
					(mapHeight-1)*tileSize + (tileSize - 12)/2, null);
		

		g.drawImage(mazeModel.getPlayer().getPlayer(), 
					mazeModel.getPlayer().getX() * tileSize + (tileSize - playerSize)/2,
					mazeModel.getPlayer().getY() * tileSize + (tileSize - playerSize)/2, null);
		
		ArrayList<Treasure> treasures= mazeModel.getTreasure();
		for(Treasure t: treasures){
		 	g.drawImage(map.getTreasureImg(),
		 				t.getX() *tileSize + (tileSize-treasureSize)/2, 
		 				t.getY() * tileSize + (tileSize-treasureSize)/2, null);
		}
	}
	
	public void easyDraw(Graphics g){
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
	
	
	/*

	public void drawPath(Graphics g) {
		
		ArrayList<Node> path = testMaze.getPath(testMaze.getCell(player.getX(),player.getY()), 
								testMaze.getCell(mapWidth, mapHeight), new ManhattenHeuristic()); 
		int dx;
		int dy;
		for(int i = 1; i < path.size() - 1; i++){
			dx = path.get(i + 1).getxPos() - path.get(i - 1).getxPos();
			dy = path.get(i + 1).getyPos() - path.get(i - 1).getyPos();
			
			if(dx == 0){
				g.drawImage(map.getVerticalPath(), path.get(i).getxPos()*tileSize + 7, path.get(i).getxPos()*tileSize, null);
			}
		}
	
	}
	*/
	
}
