

import java.awt.Image;

import javax.swing.ImageIcon;

public class Player extends MazeObject{
	

	private Image player;
	
	private double velX = 0;
	private double velY = 0;;
	
	public Player() {
		super(0,0);
		ImageIcon img = new ImageIcon("images/bert2_10.jpg");
		player = img.getImage();
	}
	
	public Player(int x, int y) {
		super(x,y);
		ImageIcon img = new ImageIcon("images/bert2_10.jpg");
		player = img.getImage();
	}
	
	public Image getPlayer() {
		return player;
	}

	public void move(int dx, int dy) {		
		this.posX += dx;
		this.posY += dy;
	}
	
	public void moveSmooth(int dx, int dy) {
		
	}
}


