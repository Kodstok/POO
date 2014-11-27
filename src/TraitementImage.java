
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
		double tmp,l;
		Coord res=null;
		int test = 0;
		System.out.println(ptVertG.size()+":"+ptVertD.size());
		for(Coord a : ptVertG)
		{
			tmp = corelationTest(a,ptVertD.elementAt(0));
			res=ptVertD.elementAt(0);
			for(Coord b: ptVertD)
			{
				l = corelationTest(a,b);
				if(l>tmp)
				{
					tmp = l;
					res = b;
					
				}
				test++;
				System.out.println(""+a + b+" : "+l);
				
			}
			map.put(a, res);
		}
		System.out.println("nb comparaisons : "+test);
	}
	private int moyen(BufferedImage img, int n,int m, Coord a)
	{
		int res=0,c=0;
		
		for(int i =-n; i < n;i++)
		{
			for(int j=-m; j< n;j++ )
			{
				Color d = new Color(imageG.getRGB(a.x+i, a.y+j));
				res += d.getRed() ;
				c++;
			}
		}
		return res/c;
	}
	private double corelationTest(Coord a,Coord b)
	{
		double res=0,sig1=0,sig2=0;
		int n = 40;
		int m = 80;
		int e =moyen(imageG,n,m,a);
		int f =moyen(imageD,n,m,a);
		for(int i =-n; i < n;i++)
		{
			
			for(int j=-m; j< m;j++ )
			{
				Color c = new Color(imageG.getRGB(a.x+i, a.y+j));
				sig1= (c.getRed()-e)*(c.getRed()-e);
			}
		}
		for(int i =-n; i < n;i++)
		{
			
			for(int j=-m; j< m;j++ )
			{
				Color d = new Color(imageD.getRGB(b.x+i, b.y+j));
				
				sig2 +=  (d.getRed()-f)*(d.getRed()-f);
			}
		}
		for(int i =-n; i < n;i++)
		{
			
			for(int j=-m; j< m;j++ )
			{
				Color c = new Color(imageG.getRGB(a.x+i, a.y+j));
				Color d = new Color(imageD.getRGB(b.x+i, b.y+j));
				
				res +=  (c.getRed()-e)*(d.getRed()-e);
			}
		}
		return res*1/(sig1*sig2);
	}
	private double calculCoorelation(Coord a,Coord b)
	{
		
		double res=0;
		int n = 30;
	
		Color e = new Color(imageG.getRGB(a.x, a.y));
		Color f = new Color(imageG.getRGB(b.x, b.y));
		for(int i =-n; i < n;i++)
		{
			
			for(int j=-n; j< n;j++ )
			{
				Color c = new Color(imageG.getRGB(a.x+i, a.y+j));
				Color d = new Color(imageD.getRGB(b.x+i, b.y+j));
				
				res += ( c.getBlue()-e.getBlue())*
						(d.getBlue()-f.getBlue());
			}
		}
		//return res*1/((2*n+1)*(2*n+1)*mimgL*mimgR);
		/* En fait, le prof avait expliqu�� qu'on peut simplifier l'��quation. 
		 * Dans notre cas, puisque la cam��ra reste perpendiculaire au mur pour
		 * les deux photos, on peut mettre K=1, ce qui simplifie pas mal... */
		return res;
	}

}
