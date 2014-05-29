import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/*
public class Mask {
	public Mask(){
	BufferedImage image = new BufferedImage(mapWidth*tileSize, mapHeight*tileSize, BufferedImage.TYPE_INT_ARGB);
		
		Player player = mazeModel.getPlayer();
		int topLeftX = player.getX() - 25;
		int topLeftY = player.getY() - 25;
		Area mask = new Area(new Rectangle2D.Double(0,0, mapWidth*tileSize, mapHeight*tileSize));
		Area hole = new Area(new Ellipse2D.Double(50,50,topLeftX, topLeftY));
		//mask.subtract(hole);
		g2d = image.createGraphics();
		g2d.setColor(Color.red);
		g2d.fill(mask);
		g2d.drawImage(image,0,0,null);
	}
}
*/