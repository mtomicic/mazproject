

import java.awt.*;
import java.io.File;
import java.util.Scanner;

import javax.swing.ImageIcon;

public class Map {
	
	private Scanner sc;
	
	private Image grass, wall, pathhint;
	private Image[][][][] imageBank;

	private Image verticalPath;

	private Image horizontalPath;

	private Image topLeftPath;

	private Image topRightPath;

	private Image bottomLeftPath;

	private Image bottomRightPath;

	private Image treasure;

	private Image end;

	private Image empty;

	private Image wood;
	
	public Map() {
		ImageIcon img = new ImageIcon("images/grass_10.jpg");
		grass = img.getImage();
		img = new ImageIcon("images/wall_10.png");
		wall = img.getImage();
		img = new ImageIcon("images/pathhint_10.png");
		pathhint = img.getImage();

		imageBank = new Image[2][2][2][2];//[w][s][e][n]
		
		img = new ImageIcon("images/sprites/tile0.jpg");
		imageBank[0][0][0][0] = img.getImage(); 
		empty = img.getImage();
		img = new ImageIcon("images/sprites/tile1.jpg");
		imageBank[0][0][0][1] = img.getImage();
		img = new ImageIcon("images/sprites/tile2.jpg");
		imageBank[0][0][1][0] = img.getImage();
		img = new ImageIcon("images/sprites/tile3.jpg");
		imageBank[0][0][1][1] = img.getImage();
		img = new ImageIcon("images/sprites/tile4.jpg");
		imageBank[0][1][0][0] = img.getImage();
		img = new ImageIcon("images/sprites/tile5.jpg");
		imageBank[0][1][0][1] = img.getImage();
		img = new ImageIcon("images/sprites/tile6.jpg");
		imageBank[0][1][1][0] = img.getImage();
		img = new ImageIcon("images/sprites/tile7.jpg");
		imageBank[0][1][1][1] = img.getImage();
		img = new ImageIcon("images/sprites/tile8.jpg");
		imageBank[1][0][0][0] = img.getImage(); 
		img = new ImageIcon("images/sprites/tile9.jpg");
		imageBank[1][0][0][1] = img.getImage();
		img = new ImageIcon("images/sprites/tile10.jpg");
		imageBank[1][0][1][0] = img.getImage();
		img = new ImageIcon("images/sprites/tile11.jpg");
		imageBank[1][0][1][1] = img.getImage();
		img = new ImageIcon("images/sprites/tile12.jpg");
		imageBank[1][1][0][0] = img.getImage();
		img = new ImageIcon("images/sprites/tile13.jpg");
		imageBank[1][1][0][1] = img.getImage();
		img = new ImageIcon("images/sprites/tile14.jpg");
		imageBank[1][1][1][0] = img.getImage();
		img = new ImageIcon("images/sprites/tile0.jpg");
		imageBank[1][1][1][1] = img.getImage();
		
		img = new ImageIcon("images/path_3_16_vertical");
		verticalPath = img.getImage();
		img = new ImageIcon("images/path_16_3_horizonta");
		horizontalPath = img.getImage();
		img = new ImageIcon("images/path_10_10_top_left");
		topLeftPath = img.getImage();
		img = new ImageIcon("images/path_10_10_top_right");
		topRightPath = img.getImage();
		img = new ImageIcon("images/path_10_10_bottom_left");
		bottomLeftPath = img.getImage();
		img = new ImageIcon("images/path_10_10_bottom_right");
		bottomRightPath = img.getImage();
		
		
		img = new ImageIcon("images/treasure.png");
		treasure = img.getImage();
		img = new ImageIcon("images/end.png");
		end = img.getImage();
		
		img = new ImageIcon("images/wood.png");
		wood = img.getImage();
	}
	
	
	

	public Image getImage(int[] walls){
		//System.out.println(walls[0] + " " + walls[1] +  " " + walls[2] + " " + walls[3]);
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
	
	public Image getTreasureImg() {
		return treasure;
	}

	/**
	 * @return the pathhint
	 */
	public Image getPathhint() {
		return pathhint;
	}





	/**
	 * @return the verticalPath
	 */
	public Image getVerticalPath() {
		return verticalPath;
	}




	/**
	 * @return the horizontalPath
	 */
	public Image getHorizontalPath() {
		return horizontalPath;
	}




	/**
	 * @return the topLeftPath
	 */
	public Image getTopLeftPath() {
		return topLeftPath;
	}




	/**
	 * @return the topRightPath
	 */
	public Image getTopRightPath() {
		return topRightPath;
	}




	/**
	 * @return the bottomLeftPath
	 */
	public Image getBottomLeftPath() {
		return bottomLeftPath;
	}




	/**
	 * @return the bottomRightPath
	 */
	public Image getBottomRightPath() {
		return bottomRightPath;
	}


	/**
	 * @return the end image
	 */
	public Image getEndImg() {
		return end;
	}
	
	/**
	 * @return empty tile
	 */
	public Image getEmptyImg() {
		return empty;
	}
	
	/**
	 * @return empty tile
	 */
	public Image getWoodImg() {
		return wood;
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
