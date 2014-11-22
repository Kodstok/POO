import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;


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
			
			bottleLeft = ImageIO.read(new File("bottle/bottle_left.jpg"));
			bottleRight = ImageIO.read(new File("bottle/bottle_right.jpg"));
			
			emptyLeft = ImageIO.read(new File("empty/empty_right.jpg"));
			emptyRight = ImageIO.read(new File("empty/empty_right.jpg"));
			
			
		} catch (IOException e) {
		}
		
		TraitementImage i = new TraitementImage(bookLeft,bookRight);
		System.out.println(i.toString());
		//TraitementImage j = new TraitementImage(bottleLeft,bottleRight);

		//TraitementImage k = new TraitementImage(emptyLeft,emptyRight);
		
		

	}

}
