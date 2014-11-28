import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Vector;


public class ImagePointVert  {
	public BufferedImage img;
	public Vector<Coord> ptVert;
	
	public ImagePointVert(BufferedImage img) {
		this.img = img;
	}
	public Color getColor(int i,int j)
	{
		return new Color(img.getRGB(i, j));
	}
	
	public int getBlue(int i,int j)
	{
		Color c = new Color(img.getRGB(i, j));
		return c.getBlue();
	}
	public int getRed(int i,int j)
	{
		Color c = new Color(img.getRGB(i, j));
		return c.getRed();
	}
	public int getGreen(int i,int j)
	{
		Color c = new Color(img.getRGB(i, j));
		return c.getGreen();
	}
	public int getGrey(int i, int j)
	{
		Color c = new Color(img.getRGB(i, j));
		return (c.getBlue()+c.getRed()+c.getGreen())/3;
	}
	private boolean estVert( int i, int j) 
	{
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
	
	public void chercherPtVert()
	{
		Vector<Coord> res = new Vector<Coord>();
		for(int i =0;i< img.getWidth();i++)
		{
			for(int j = 0; j < img.getHeight();j++)
			{
				if( estVert(i,j) && estCentre(i,j))
				{
					res.add(new Coord(i,j));

				}
			}
		}
		ptVert = res;
	}
	
	private boolean estCentre(int i, int j) {
		int haut,bas,gauche,droite;
		haut = cpt(i,j,0,1);
		bas = cpt(i,j,0,-1);
		gauche = cpt(i,j,-1,0);
		droite = cpt(i,j,1,0);
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
	
	private int cpt( int i, int j,int incrementi,int incrementj) {
		int res=0;
		i=i+incrementi;
		j=j+ incrementj;
		while(estVert(i,j))
		{
			res++;
			i=i+incrementi;
			j=j+incrementj;
		}
		return res;
	}
	
	public int cptptvg(Coord a)
	{
		int s = 25;
		int res=0;
		for(Coord c : ptVert)
		{
			if(Math.abs(a.y-c.y)<s && a.x>c.y)
				res++;
		}
		return res;
	}
	
	public int moyen( int n,int m, Coord a)
	{
		int res=0,c=0;
		
		for(int i =-n; i < n;i++)
		{
			for(int j=-m; j< n;j++ )
			{
				res += getGrey(a.x+i,a.y+j) ;
				c++;
			}
		}
		return res/c;
	}

}
