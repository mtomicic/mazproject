

import java.awt.*;
import java.io.File;
import java.util.Scanner;

import javax.swing.ImageIcon;

public class Map {
	
	private Scanner sc;
	private char map[][];
	private Image grass, wall, pathhint;
	
	public Map(char[][] charMaze) {
		
		ImageIcon img = new ImageIcon("images/grass_10.jpg");
		grass = img.getImage();
		img = new ImageIcon("images/wall_10.png");
		wall = img.getImage();
		img = new ImageIcon("images/pathhint_10.png");
		pathhint = img.getImage();
		//openFile();
		//readFile();
		//closeFile();
		map = charMaze;
		
	}
	
	public void setMap(char[][] m){
		map = m;
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
	
	public char[][] getMap() {
		return map;
	}
	
	public char getMap(int x, int y) {
		return map[x][y];
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
