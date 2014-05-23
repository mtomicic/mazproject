

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class Board extends JPanel implements ActionListener{
	
	private Timer timer;
	private Map map;
	private MazeImp testMaze;
	private int mapWidth;
	private int mapHeight;
	private Player player;
	
	
	public Board(int x, int y) {
		mapWidth = x;
		mapHeight = y;
		testMaze = new MazeImp(x,y);
		
		map = new Map();
		player = new Player(0,0);
		addKeyListener(new Al());
		setFocusable(true);
		
		//testMaze.dumpMaze();
		
		timer = new Timer(25, this);
		timer.start();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		repaint();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		for (int y = 0; y < mapHeight; y++) {
			for (int x = 0; x < mapWidth; x++) {
				//System.out.println("printing " + testMaze.getCell(x, y));
				Node cell = testMaze.getCell(x, y);
				g.drawImage(map.getImage(cell.getWalls()),x*14, y*14, null);
				//g.drawImage(map.getGrass(), x*10, y*10, null);
				
	
			}
		}
		//System.out.println("fin");
		g.drawImage(player.getPlayer(), player.getX() * 14 + 2, player.getY() * 14 + 2, null);
	}
	
	public class Al extends KeyAdapter {
		
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();
			System.out.println(testMaze.getCell(player.getX(), player.getY()));
			if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
				
				if ((testMaze.getCell(player.getX(), player.getY()).getNorth() != null)) {
					player.move(0, -1);			
				}
			} else if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
				//VER1player.move(0, 100, 0, 1);
				
				if ((testMaze.getCell(player.getX(), player.getY()).getSouth() != null)) {
					player.move(0, 1);			
				}
			} else if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
				
				if ((testMaze.getCell(player.getX(), player.getY()).getWest() != null)) {
					player.move(-1, 0);			
				}
			} else if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
				
				if ((testMaze.getCell(player.getX(), player.getY()).getEast() != null)) {
					player.move(1, 0);			
				}
			}
		}
		
		public void keyReleased(KeyEvent e) {
			
		}
		
		public void keyTyped(KeyEvent e) {
			
		}
		
	}
	
	


	public void drawPath(Graphics g) {
		
		ArrayList<Node> path = testMaze.getPath(testMaze.getCell(player.getX(),player.getY()), 
								testMaze.getCell(mapWidth, mapHeight), new ManhattenHeuristic()); 
		int dx;
		int dy;
		for(int i = 1; i < path.size() - 1; i++){
			dx = path.get(i + 1).getxPos() - path.get(i - 1).getxPos();
			dy = path.get(i + 1).getyPos() - path.get(i - 1).getyPos();
			
			if(dx == 0){
				g.drawImage(map.getVerticalPath(), path.get(i).getxPos()*14 + 7, path.get(i).getxPos()*14, null);
			}
		}
	
	}
	
	
}
