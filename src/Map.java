

import java.awt.*;
import java.io.File;
import java.util.Scanner;

import javax.swing.ImageIcon;

public class Map {
	
	private Scanner sc;
	
	private Image grass, wall, pathhint;
	private Image[][][][] imageBank;
	
	public Map() {
		
		ImageIcon img = new ImageIcon("images/grass_10.jpg");
		grass = img.getImage();
		img = new ImageIcon("images/wall_10.png");
		wall = img.getImage();
		img = new ImageIcon("images/pathhint_10.png");
		pathhint = img.getImage();
		img = new ImageIcon("");
		imageBank = new Image[2][2][2][2];//[n][e][s][w]
		
		//openFile();
		//readFile();
		//closeFile();
		
	}
	
	
	

	public Image getImage(int[] walls){
		//if there is a wall
		return imageBank[walls[0]][walls[1]][walls[2]][walls[3]];
	}
	
	public Image getGrass() {
		return grass;
	}
	
	public Image getWall() {
		return wall;
	}
	
	public Image getPathHint() {
		return pathhint;
	}
	
/*	
	public void openFile() {
		
		try {
			sc = new Scanner(new File("map.txt"));
		} catch (Exception e) {
			System.out.println("Error loading map");
		}
	}
	
	public void readFile() {
		while(sc.hasNext()) {
			for (int i = 0; i < MAP_WIDTH; i++) {
				Map[i] = sc.next();
			}
		}
	}
	
	public void closeFile() {
		sc.close();
	}
*/
}
