

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
		mapWidth = 2*x + 1;
		mapHeight = 2*y + 1;
		testMaze = new MazeImp(x,y);
		
		map = new Map(testMaze.getCharMaze());
		player = new Player(1,1);
		addKeyListener(new Al());
		setFocusable(true);
		
		timer = new Timer(25, this);
		timer.start();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		repaint();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		//g.setColor(Color.red);
		//g.fillRect(45, 60, 32, 32);
		for (int y = 0; y < mapHeight; y++) {
			for (int x = 0; x < mapWidth; x++) {
				if (map.getMap(x, y) == (' ')) {
					g.drawImage(map.getGrass(), x*10, y*10, null);
				} else if (map.getMap(x, y) == ('#')) {
					g.drawImage(map.getWall(), x*10, y*10, null);
				}else if(map.getMap(x,y) == ('p')){
					g.drawImage(map.getPathHint(), x*10, y*10, null);
				}
			}
		}
		
		g.drawImage(player.getPlayer(), player.getX() * 10, player.getY() * 10, null);
	}
	
	public class Al extends KeyAdapter {
		
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();
			
			if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
				erasePath();
				if (!(map.getMap(player.getX(), player.getY() - 1) == '#')) {
					player.move(0, -1);			
				}
			} else if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
				//VER1player.move(0, 100, 0, 1);
				erasePath();
				if (!(map.getMap(player.getX(), player.getY() + 1) == '#')) {
					player.move(0, 1);			
				}
			} else if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
				erasePath();
				if (!(map.getMap(player.getX() - 1, player.getY()) == '#')) {
					player.move(-1, 0);			
				}
			} else if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
				erasePath();
				if (!(map.getMap(player.getX() + 1, player.getY()) == '#')) {
					player.move(1, 0);			
				}
			}
		}
		
		public void keyReleased(KeyEvent e) {
			
		}
		
		public void keyTyped(KeyEvent e) {
			
		}
		
	}
	
	private void erasePath(){
		map.setMap(testMaze.getCharMaze());
		repaint();
	}

	public void drawPath() {
		ArrayList<Node> path = testMaze.getPath(testMaze.getCell((player.getX() - 1)/2,(player.getY() - 1)/2), 
								testMaze.getCell((mapWidth - 1)/2 - 1, (mapHeight - 1)/2 - 1), 
								new ManhattenHeuristic());
		testMaze.showPath(map.getMap(),path);
		repaint();
	}
	
}
