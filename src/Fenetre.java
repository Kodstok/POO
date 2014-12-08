import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Fenetre extends JFrame {

	private imagePanel imageG;
	private imagePanel imageD;
	private int nbPtsAjoutes = 0;
	
	// dimensions pour une image
	private int largeur = 2560, hauteur = 1920; 
	
	// on divise toutes les tailles en pixel par ce nombre 
	// pour pouvoir afficher les images à l'ecran
	private int coeffDeReduction = 4;

	public Fenetre(BufferedImage _g, BufferedImage _d) throws HeadlessException {
		super("Image de fond");
		largeur /= coeffDeReduction;
		hauteur /= coeffDeReduction;
		imageG = new imagePanel(_g);
		imageD = new imagePanel(_d);
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
	
	public void marquerCouplePoints(Entry<Coord, Coord> couple) {
		Coord pointImgG = couple.getKey();
		Coord pointImgD = couple.getValue();
		if (pointImgG != null && pointImgD != null) {
			nbPtsAjoutes++;
			imageG.marquerPoint(pointImgG, nbPtsAjoutes);
			imageD.marquerPoint(pointImgD, nbPtsAjoutes);
		}
	}
	
	public class imagePanel extends JPanel {
		BufferedImage img;
		public imagePanel(BufferedImage _img) {
			super();
			this.img = _img;
			this.setPreferredSize(new Dimension(largeur, hauteur));
		}

		public void paint(Graphics g) {
			g.drawImage(img, 0, 0, largeur, hauteur, this);
		}
		
		public void marquerPoint (Coord point, int numero) {
			System.out.println("Appel a la fonction marquerPoint pour " + point.toString());
			Graphics2D g = img.createGraphics();
			g.setComposite(AlphaComposite.SrcOver);
			
			g.fillOval(point.x-10, point.y-10, 20, 20);
			
			g.setFont(new Font("Serif", Font.PLAIN, 72));
			g.drawString(""+numero, point.x+10, point.y+10);
			g.dispose();
		}
	}
	
	public static String selectionDossier() {
		JFileChooser choix = new JFileChooser();
		choix.setCurrentDirectory(new File("./"));
		choix.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		choix.setDialogTitle("Veuillez selectionner le dossier contenant vos deux images...");
		if( choix.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) 
			System.exit(ABORT);
		return choix.getSelectedFile().getAbsolutePath() + "/";
	}
}
