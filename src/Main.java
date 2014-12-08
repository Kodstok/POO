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
		BufferedImage left = null;
		BufferedImage right = null;
		
		String folderPath = Fenetre.selectionDossier();

		try {
			left = ImageIO.read(new File(folderPath+"left.jpg"));
			right = ImageIO.read(new File(folderPath+"right.jpg"));
		} catch (IOException e) {
			System.out.println("Probleme dans la lecture des fichiers : " + e.toString());
		}
		
		TraitementImage t = new TraitementImage(left,right);
		System.out.println(t.toString());
		

		Fenetre f = new Fenetre(left, right);

		for(Entry<Coord, Coord> entry : t.map.entrySet())
			f.marquerCouplePoints(entry);
			
		f.afficher();
		
	}

}