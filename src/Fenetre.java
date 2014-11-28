import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;



import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class Fenetre extends JFrame {

	private imagePanel imageG;
	private imagePanel imageD;
	// dimensions pour une image
	private int largeur = 480, hauteur = 360; 

	public Fenetre() throws HeadlessException {
		super("Image de fond");
		
		imageG = new imagePanel("book/book_left.jpg");
		imageD = new imagePanel("book/book_right.jpg");
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(imageG, BorderLayout.WEST);
		this.getContentPane().add(imageD, BorderLayout.EAST);
	}

	public void afficher() {
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public void marquerPointG(Coord a) {
		this.imageG.marquerPoint(a);
	}
	
	public void marquerPointD(Coord a) {
		this.imageD.marquerPoint(a);
	}
	
	public class imagePanel extends JPanel {
		BufferedImage img;
		public imagePanel(String cheminDuFichier) {
			super();
			try {
				img = ImageIO.read(new File(cheminDuFichier));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.setPreferredSize(new Dimension(largeur, hauteur));
		}

		public void paint(Graphics g) {
			System.out.println("appel a la fonction paint");
			g.drawImage(img, 0, 0, largeur, hauteur, this);
		}
		
		public void marquerPoint (Coord a) {
			if (a.y > hauteur || a.x >largeur*2) {
				System.out.println("Attention, les coordonnes "+ a.toString() +" sont hors de la fenetre.");
				return;
			}
			System.out.println("appel a la fonction marquerPoint");
			Graphics2D g = img.createGraphics();
			g.fillOval(a.x, a.y, 20, 20);
			g.dispose();
		}
	}
}
