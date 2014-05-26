

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.EventObject;

import javax.swing.*;

public class Board extends JPanel implements ActionListener{
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
		public void run(){
			maze = new MazeFrame();
		}
		
		});
	}
	private Timer timer;
	private Map map;
	private MazeImp mazeModel;
	private int mapWidth;
	private int mapHeight;
	private static MazeFrame maze;

	
	public Board(MazeImp m) {
		mapWidth = m.getxSize();
		mapHeight = m.getySize();
		mazeModel = m;
		
		map = new Map();
		addKeyListener(new Al());
		mazeModel.addMazeListener(new boardListener());
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
				Node cell = mazeModel.getCell(x, y);
				g.drawImage(map.getImage(cell.getWalls()),x*14, y*14, null);
				//g.drawImage(map.getGrass(), x*10, y*10, null);
				
	
			}
		}
		//System.out.println("fin");
		g.drawImage(mazeModel.getPlayer().getPlayer(), mazeModel.getPlayer().getX() * 14 + 2, mazeModel.getPlayer().getY() * 14 + 2, null);
		
		ArrayList<Treasure> treasures= mazeModel.getTreasure();
		for(Treasure t: treasures){
		 	g.drawImage(map.getTreasureImg(), t.getX() * 14 + 7, t.getY() * 14 + 7, null);
		 	//System.out.println("treasure");
		}
	}
	
	public class Al extends KeyAdapter {
		
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();
			System.out.println(mazeModel.getCell(mazeModel.getPlayer().getX(), mazeModel.getPlayer().getY()));
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
	
	public class boardListener implements MazeListener{

		@Override
		public void playerMoved(EventObject e) {
			repaint();
		}

		@Override
		public void mazeRestarted(EventObject e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void playerFinished(EventObject e) {
			// TODO Auto-generated method stub
			System.out.println("winner");
			maze.showWin();
		}

		@Override
		public void treasureCollected(EventObject e) {
			// TODO Auto-generated method stub
			maze.removeTime(2);
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
				g.drawImage(map.getVerticalPath(), path.get(i).getxPos()*14 + 7, path.get(i).getxPos()*14, null);
			}
		}
	
	}
	*/
	
}
