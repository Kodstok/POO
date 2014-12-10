
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;


public class TraitementImage {
	ImagePointVert imageG;
	ImagePointVert imageD;
	HashMap<Coord,Coord> map = new HashMap<Coord,Coord>();
	ArrayList<Couple> lst = new ArrayList<Couple>();
	
	public TraitementImage( BufferedImage imageG, BufferedImage imageD)
	{
		this.imageG = new ImagePointVert(imageG);
		this.imageD = new ImagePointVert(imageD);
		this.imageG.chercherPtVert();
		this.imageD.chercherPtVert();
		//chercherCorespondance();
		chercherCorespondanceSurLigne();
	}
	
	public String toString()
	{
		return map.toString();
	}
	
	private void chercherCorespondanceSurLigne()
	{
		double tmp,l;
		Coord res=null;
		int test = 0;
		System.out.println(imageG.ptVert.size()+":"+imageD.ptVert.size());
		for(Coord a : imageG.ptVert)
		{
			Vector<Coord> ptLigne = imageD.getPtLigne(a);
			tmp = 3;
			res=null;
			System.out.println("points sur la même ligne : "+ptLigne.size());
			for(Coord b: ptLigne)
			{
				l = corelationGrey(a,b);
				if(l>=tmp )
				{
					tmp = l;
					res = b;
					
				}
				test++;
				System.out.println(""+a + b+" : "+l);
				
			}
			System.out.println();
			map.put(a, res);
		}
		System.out.println("nb comparaisons : "+test);
	}
	
	private void chercherCorespondance()
	{
		double tmp,l;
		Coord res=null;
		int test = 0;
		System.out.println(imageG.ptVert.size()+":"+imageD.ptVert.size());
		for(Coord a : imageG.ptVert)
		{
			tmp = corelationRed(a,imageD.ptVert.elementAt(0));
			res=imageD.ptVert.elementAt(0);
			for(Coord b: imageD.ptVert)
			{
				l = corelationRed(a,b);
				if(l>tmp)
				{
					tmp = l;
					res = b;
					
				}
				test++;
				System.out.println(""+a + b+" : "+l);
				
			}
			System.out.println();
			map.put(a, res);
		}
		System.out.println("nb comparaisons : "+test);
	}
	
	public ArrayList<Integer> calculerDecalagePourChaquePoints() {
		return null;
	}

	private double corelationGrey(Coord a,Coord b)
	{
		double res=0,sig1=0,sig2=0;
		int n = 100;
		int m = 200;
		int c1=0,c2=0;
		int e =imageG.moyen(n,m,a);
		int f =imageD.moyen(n,m,a);
		for(int i =-n; i < n;i++)
		{
			
			for(int j=-m; j< m;j++ )
			{
				if(imageG.isValidCoord(a.x+i,a.y+j))
				{
					sig1+= (imageG.getGrey(a.x+i, a.y+j)-e)*(imageG.getGrey(a.x+i,a.y+ j)-e);
					c2++;
				}
			}
			if(imageG.isValidCoord(a.x+i,a.y))
				c1++;
		}
		sig1=Math.sqrt(sig1)/((c1)*(c2));
		c1=0;c2=0;
		for(int i =-n; i < n;i++)
		{
			
			for(int j=-m; j< m;j++ )
			{
				if(imageD.isValidCoord(b.x+i,b.y+j))
				{
					sig2 +=  (imageD.getGrey(b.x+i,b.y+ j)-f)*(imageD.getGrey(b.x+i,b.y+ j)-f);
					c1++;
				}
			}
			if(imageD.isValidCoord(b.x+i,b.y))
				c2++;
		}
		sig2=Math.sqrt(sig2)/((c1)*(c2));
		for(int i =-n; i < n;i++)
		{
			
			for(int j=-m; j< m;j++ )
			{
				if(imageG.isValidCoord(a.x+i,a.y+j)&&imageD.isValidCoord(b.x+i,b.y+j))
					res +=  (imageG.getGrey(a.x+i,a.y+ j)-e)*(imageD.getGrey(b.x+i, b.y+j)-e);
			}
		}
		return res*1/(sig1*sig2);
	}
	private double calculCoorelation(Coord a,Coord b)
	{
		
		double res=0;
		int n = 30;
	
		Color e = new Color(imageG.img.getRGB(a.x, a.y));
		Color f = new Color(imageG.img.getRGB(b.x, b.y));
		for(int i =-n; i < n;i++)
		{
			
			for(int j=-n; j< n;j++ )
			{
				Color c = new Color(imageG.img.getRGB(a.x+i, a.y+j));
				Color d = new Color(imageD.img.getRGB(b.x+i, b.y+j));
				
				res += ( c.getBlue()-e.getBlue())*
						(d.getBlue()-f.getBlue());
				
			}
		}
		//return res*1/((2*n+1)*(2*n+1)*mimgL*mimgR);
		/* En fait, le prof avait expliqu������ qu'on peut simplifier l'������quation. 
		 * Dans notre cas, puisque la cam������ra reste perpendiculaire au mur pour
		 * les deux photos, on peut mettre K=1, ce qui simplifie pas mal... */
		return res;
	}
	private double corelationBlue(Coord a,Coord b)
	{
		double res=0,sig1=0,sig2=0;
		int n = 50;
		int m = 100;
		int c1=0,c2=0;
		int e =imageG.moyen(n,m,a);
		int f =imageD.moyen(n,m,a);
		for(int i =-n; i < n;i++)
		{
			
			for(int j=-m; j< m;j++ )
			{
				sig1+= (imageG.getBlue(a.x+i, a.y+j)-e)*(imageG.getBlue(a.x+i,a.y+ j)-e);
			}
		}
		sig1=sig1/((2*n+1)*(2*m+1));
		for(int i =-n; i < n;i++)
		{
			
			for(int j=-m; j< m;j++ )
			{
				
				sig2 +=  (imageD.getBlue(b.x+i,b.y+ j)-f)*(imageD.getBlue(b.x+i,b.y+ j)-f);
			}
		}
		sig2=sig2/((2*n+1)*(2*m+1));
		for(int i =-n; i < n;i++)
		{
			
			for(int j=-m; j< m;j++ )
			{
				res +=  (imageG.getBlue(a.x+i,a.y+ j)-e)*(imageD.getBlue(b.x+i, b.y+j)-e);
			}
		}
		return res*1/(sig1*sig2);
	}
	private double corelationGreen(Coord a,Coord b)
	{
		double res=0,sig1=0,sig2=0;
		int n = 50;
		int m = 100;
		int e =imageG.moyen(n,m,a);
		int f =imageD.moyen(n,m,a);
		for(int i =-n; i < n;i++)
		{
			
			for(int j=-m; j< m;j++ )
			{
				sig1+= (imageG.getGreen(a.x+i, a.y+j)-e)*(imageG.getGreen(a.x+i,a.y+ j)-e);
			}
		}
		sig1=sig1/((2*n+1)*(2*m+1));
		for(int i =-n; i < n;i++)
		{
			
			for(int j=-m; j< m;j++ )
			{
				
				sig2 +=  (imageD.getGreen(b.x+i,b.y+ j)-f)*(imageD.getGreen(b.x+i,b.y+ j)-f);
			}
		}
		sig2=sig2/((2*n+1)*(2*m+1));
		for(int i =-n; i < n;i++)
		{
			
			for(int j=-m; j< m;j++ )
			{
				res +=  (imageG.getGreen(a.x+i,a.y+ j)-e)*(imageD.getGreen(b.x+i, b.y+j)-e);
			}
		}
		return res*1/(sig1*sig2);
	}
	private double corelationRed(Coord a,Coord b)
	{
		double res=0,sig1=0,sig2=0;
		int n = 100;
		int m = 100;
		int c1=0,c2=0;
		int e =imageG.moyen(n,m,a);
		int f =imageD.moyen(n,m,a);
		for(int i =-n; i < n;i++)
		{
			
			for(int j=-m; j< m;j++ )
			{
				if(imageG.isValidCoord(a.x+i,a.y+j))
				{
					sig1+= (imageG.getRed(a.x+i, a.y+j)-e)*(imageG.getRed(a.x+i,a.y+ j)-e);
					c2++;
				}
			}
			if(imageG.isValidCoord(a.x+i,a.y))
				c1++;
		}
		sig1=Math.sqrt(sig1)/((c1)*(c2));
		c1=0;c2=0;
		for(int i =-n; i < n;i++)
		{
			
			for(int j=-m; j< m;j++ )
			{
				if(imageD.isValidCoord(b.x+i,b.y+j))
				{
					sig2 +=  (imageD.getRed(b.x+i,b.y+ j)-f)*(imageD.getRed(b.x+i,b.y+ j)-f);
					c1++;
				}
			}
			if(imageD.isValidCoord(b.x+i,b.y))
				c2++;
		}
		sig2=Math.sqrt(sig2)/((c1)*(c2));
		for(int i =-n; i < n;i++)
		{
			
			for(int j=-m; j< m;j++ )
			{
				if(imageG.isValidCoord(a.x+i,a.y+j)&&imageD.isValidCoord(b.x+i,b.y+j))
					res +=  (imageG.getRed(a.x+i,a.y+ j)-e)*(imageD.getRed(b.x+i, b.y+j)-e);
			}
		}
		return res*1/(sig1*sig2);
	}

}
