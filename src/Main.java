import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map.Entry;

import javax.imageio.ImageIO;


public class Main {
	public static BufferedImage resize(BufferedImage image, int width, int height) {
		BufferedImage i = new BufferedImage(width, height, image.getType());
		Graphics2D g = i.createGraphics();
		g.drawImage(image, 0, 0, width, height, null);
		g.dispose();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		return i;
	}
	
	public static void main(String[] args) 
	{
		BufferedImage bookLeft =null;
		BufferedImage bookRight=null;
		
		BufferedImage bottleLeft=null;
		BufferedImage bottleRight=null;
		
		BufferedImage emptyLeft=null;
		BufferedImage emptyRight=null;
		

		try {
			bookLeft = ImageIO.read(new File("book/book_left.jpg"));
			bookRight = ImageIO.read(new File("book/book_right.jpg"));
/*			
			bottleLeft = ImageIO.read(new File("bottle/bottle_left.jpg"));
			bottleRight = ImageIO.read(new File("bottle/bottle_right.jpg"));
			
			emptyLeft = ImageIO.read(new File("empty/empty_right.jpg"));
			emptyRight = ImageIO.read(new File("empty/empty_right.jpg"));
*/			
			
		} catch (IOException e) {
			System.out.println("Probleme dans la lecture des fichiers : " + e.toString());
		}
		
		TraitementImage t = new TraitementImage(bookLeft,bookRight);
		System.out.println(t.toString());
		

		Fenetre f = new Fenetre();

		for(Entry<Coord, Coord> entry : t.map.entrySet())
			f.marquerCouplePoints(entry);
			
		f.afficher();
		
		
		//TraitementImage j = new TraitementImage(bottleLeft,bottleRight);

		//TraitementImage k = new TraitementImage(emptyLeft,emptyRight);
		

	}

}
