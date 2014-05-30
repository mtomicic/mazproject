

import java.awt.*;
import java.io.File;
import java.util.Scanner;

import javax.swing.ImageIcon;

public class Map {
	private Image end;
	
	private Image wood;

	private Image fuel;
	
	public Map() {
		ImageIcon img = new ImageIcon("images/end.png");
		end = img.getImage();
		img = new ImageIcon("images/wood_25.png");
		wood = img.getImage();
		img = new ImageIcon("images/potion_custom.png");
		fuel = img.getImage();
	}
	
	/**
	 * @return fuel image
	 */
	public Image getEndImg() {
		return end;
	}
	
	
	/**
	 * @return fuel image
	 */
	public Image getFuelImg() {
		return fuel;
	}
	
	/**
	 * @return empty tile image
	 */
	public Image getWoodImg() {
		return wood;
	}
}
