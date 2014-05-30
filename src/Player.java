

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
		ImageIcon img = new ImageIcon("images/player_new.png");
		player = img.getImage();
		fuel = 100;
		fuelRate = 1;
	}
	
	public Player(int x, int y, double fr) {
		super(x,y);
		ImageIcon img = new ImageIcon("images/player_new.png");
		player = img.getImage();
		fuel = 200;
		fuelRate = fr;
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
	public double getFuel() {
		return fuel;
	}
	
	public void giveFuel(int amount){
		fuel += amount;
	}
	
	public void fuelDecrement() {
		if(fuel >= 81){
			fuel -= fuelRate*fuel;
		}
	};
	
	private double fuel;
	private double fuelRate;
	private Image player;
	private double velX = 0;
	private double velY = 0;
}


