
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Vector;


public class TraitementImage {
	BufferedImage imageG;
	Vector<Coord> ptVertG;
	BufferedImage imageD;
	Vector<Coord> ptVertD;
	HashMap<Coord,Coord> map = new HashMap<Coord,Coord>();
	
	public TraitementImage( BufferedImage imageG, BufferedImage imageD)
	{
		this.imageG = imageG;
		this.imageD = imageD;
		ptVertG = chercherPtVert(imageG);
		ptVertD = chercherPtVert(imageD);
		chercherCorespondance();
	}
	
	public String toString()
	{
		return map.toString();
	}
	
	private Vector<Coord> chercherPtVert(BufferedImage img)
	{
		Vector<Coord> res = new Vector<Coord>();
		for(int i =0;i< img.getWidth();i++)
		{
			for(int j = 0; j < img.getHeight();j++)
			{
				if( estVert(img,i,j) && estCentre(img,i,j))
				{
					res.add(new Coord(i,j));

				}
			}
		}
		return res;
	}

	private boolean estCentre(BufferedImage img, int i, int j) {
		int haut,bas,gauche,droite;
		haut = cpt(img,i,j,0,1);
		bas = cpt(img,i,j,0,-1);
		gauche = cpt(img,i,j,-1,0);
		droite = cpt(img,i,j,1,0);
		if(((haut-bas)== 0 || (haut-bas)== 1) && 
				((gauche-droite)==0 ||(gauche-droite)==1))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	private int cpt(BufferedImage img, int i, int j,int incrementi,int incrementj) {
		int res=0;
		i=i+incrementi;
		j=j+ incrementj;
		while(estVert(img,i,j))
		{
			res++;
			i=i+incrementi;
			j=j+incrementj;
		}
		return res;
	}

	private boolean estVert(BufferedImage img, int i, int j) {
		Color c = new Color(img.getRGB(i, j));
		if(c.getBlue()< 10 && c.getGreen()>200 && c.getRed()<10)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private void chercherCorespondance()
	{
		double tmp;
		Coord res=null;
		for(Coord a : ptVertG)
		{
			tmp = calculCoorelation(a,ptVertD.elementAt(0));
			for(Coord b: ptVertD)
			{
				if(calculCoorelation(a,b)>tmp)
				{
					tmp = calculCoorelation(a,b);
					res = b;
				}
				
			}
			map.put(a, res);
		}
	}
	
	private double calculCoorelation(Coord a,Coord b)
	{
		double res=0;
		int n = 30;
		for(int i =-n; i < n;i++)
		{
			for(int j=-n; j< n;j++ )
			{
				res = (imageG.getRGB(a.x+i, a.y+j)-imageG.getRGB(a.x,a.y))*
						(imageD.getRGB(b.x+i, b.y+j)-imageD.getRGB(b.x,b.y));
			}
		}
		return res;
		
	}

}
