

import java.awt.Image;

import javax.swing.ImageIcon;

public class Player extends MazeObject{
	

	
	
	public Player() {
		super(0,0);
		ImageIcon img = new ImageIcon("images/bert2_10.jpg");
		player = img.getImage();
		fuel = 100;
	}
	
	public Player(int x, int y) {
		super(x,y);
		ImageIcon img = new ImageIcon("images/bert2_10.jpg");
		player = img.getImage();
		fuel = 100;
		fuelRate = 5;
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
	
	/**
	 * @return the money
	 */
	public int getFuel() {
		return fuel;
	}
	
	public void giveFuel(int amount){
		fuel += amount;
	}
	
	public void fuelDecrement() {
		if(fuel > 50){
			fuel -= fuelRate;
		}
	};
	
	private int fuel;
	private int fuelRate;
	private Image player;
	private double velX = 0;
	private double velY = 0;


}


