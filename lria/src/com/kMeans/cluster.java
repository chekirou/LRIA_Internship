package com.kMeans;

import java.util.ArrayList;

import weka.core.Attribute;
import weka.core.Instance;



public class cluster {
	protected int id;
	public ArrayList<Point> liste = new ArrayList<Point>();
	protected Point centroid;
	protected int numatt;
	
	public cluster(int n)
	{
		this.numatt =n;
		this.id= -1; 
		
		
	}
	public cluster(int identificateur, int n)
	{
		this.numatt =n;
		this.id = identificateur;
		this.id= -1; 
		
	}
	public cluster( int n, ArrayList<Point> liste)
	{
		this.numatt =n;
		
		this.liste = liste;
	}
	public void setId(int idi)
	{
		this.id = idi;
		
	}
	public void setCentroid(Point centre)
	{
		this.centroid = centre;
	}
	public Point getCentroid()
	{
		return this.centroid;
	}
	public void clear()
	{
		this.liste.clear();
	}
	public void addPoint(Point lepoint)
	{
		this.liste.add(lepoint);
		
	}
	
	public int getnumatt()
	{
		return this.numatt;
	}
	public int getnuminst()
	{
		return this.liste.size();
	}
	public void recalculatepos()
	{
		int  nmbinstances = this.liste.size();
		
			if(nmbinstances > 0)
			{
				double[] pos = new double[this.numatt];	
				Point pt;
				for(int p =0; p < this.numatt; p++)
				{
					pos[p] = 0;
				} 
				int cmpatt = 0;
				for(int i = 0; i < nmbinstances; i++ )
				{
					pt = this.liste.get(i);
					for(cmpatt =0; cmpatt < this.numatt; cmpatt++)
					{
						
						pos[cmpatt] += pt.getValue(cmpatt);
						
					}
					
					
				}
				
				
				for(int i = 0; i< this.numatt; i++)
				{
					pos[i] /= nmbinstances;
				}
					this.setCentroid(new Point(this.numatt, pos));
			}
			else
			{
				this.setCentroid(new Point(this.numatt, Point.valAletaoire(this.numatt)));
			}
		}
		
		
		public static double distanceintra(cluster[] cl, int m)
		{
			
			double dist = 0;
			int i,d;
			Point pt, ct;
			for(i=0; i< cl.length; i++)
			{
				ct = cl[i].getCentroid();
				for(d=0; d< cl[i].liste.size(); d++)
				{
					pt= cl[i].liste.get(d);
					dist += Math.pow(Point.calculate(ct, pt, pt.n), 2);
				}
			}
			//dist /= m;
			return dist;
			
		}
		public static double distanceinter(cluster[] cl, Fiche lafiche)
		{
			int  nmbinstances = lafiche.numInstance;
			int n = lafiche.numatt;
				double[] pos = new double[n];	
				Point pt;
				for(int p =0; p < n; p++)
				{
					pos[p] = 0;
				} 
				int cmpatt = 0;
				for(int i = 0; i < nmbinstances; i++ )
				{
					pt = lafiche.getPoint(i);
					for(cmpatt =0; cmpatt < n; cmpatt++)
					{
						
						pos[cmpatt] += pt.getValue(cmpatt);
						
					}
					
					
				}
				
				
				for(int i = 0; i< n; i++)
				{
					pos[i] /= nmbinstances;
				}
			Point geocluster = new Point(n , pos); 
			double dist = 0;
			int i,z = cl.length;
			Point ct;
			
			for(i=0; i< z; i++)
			{
				ct = cl[i].getCentroid();
				
				
					
					dist += Point.calculate(ct, geocluster, n);
			}
			//dist /= cl.length;
			return dist;
			
		}
		
		public static double distance_in(Point pt, cluster cs)
		{
			double distance = 0;
			Point test;
			int p = cs.liste.size();
			for(int i =0; i< p; i++)
			{
				test = cs.liste.get(i);
				distance += Point.calculate(pt, test, cs.numatt);
				
			}
			if(p> 0)
			{
				distance /= p ;
			}
			
			return distance;
		}
		public double[] moyenne()
		{
			double[] moyenne = new double[this.getnumatt()];
			for(int i=0; i< this.numatt; i++)
				{
					moyenne[i] = 0;
				}
			
				int i;
				
				for(int p=0; p< this.getnuminst(); p++)
				 {
					 for(i=0; i< this.numatt; i++)
					 {
						 moyenne[i] += this.liste.get(p).getValue(i);
						 
						 
					 }
					 
				 }
				for(i =0; i <this.numatt; i++)
				{
					moyenne[i] /= this.getnuminst();
				}
			
			return moyenne;
		}
		public String[] analyser(ArrayList<Attribute> names)
		 {
			String[] lesnoms = new String[this.numatt];
			 int p, i ,n = this.numatt;
			 
			 double[] variances = new double[this.numatt];
			 double[] moyenne = this.moyenne();
			 
			 for(i=0; i< this.numatt; i++)
				{
					variances[i] = 0;
				}
			 for(p=0; p< this.getnuminst(); p++)
			 {
				 for(i=0; i< n; i++)
				 {
					 variances[i] += Math.pow(this.liste.get(p).getValue(i), 2);
					 
				 }
				 
			 }
			 p=0;
			 for(i =0; i< n;i++)
			 {
				 variances[i] /= this.getnuminst();
				 variances[i] -= Math.pow(moyenne[i], 2);
				 if(variances[i] < 0.1)
				 {
					 lesnoms[p] = names.get(i).name();
					 p++;
				 }
			 }
			 
			 return lesnoms;
			
		 }
		public double variance()
		{
			double variance =0;
			Point pt , geo = this.centroid;
			if(this.getnuminst() != 0)
			{
				for(int i=0; i< this.getnuminst(); i++)
				{
					pt = this.liste.get(i);
					variance += Math.pow(Point.calculate(pt, geo, this.numatt), 2);
				}
				variance /= this.getnuminst();
			}
			
			return variance;
		}
	public void description(int ido)
	{
		int i;
		Point pt;
		String position = "(";
		for( i = 0; i< this.numatt; i++)
		{
			position += this.centroid.valueTab[i] + ",";
		}
		position += ")";
		 System.out.println(" cluster_id" + ido + "position" + position);
		 
		 System.out.println("liste de points");
		
		 for( i = 0 ; i < this.liste.size() ; i++)
		 {
			 pt= this.liste.get(i);
			  position = " point" + i +"(";
			 for(int h = 0;h < this.numatt; h++)
				{
					position += pt.valueTab[h];
				}
			 position += " ";
			 System.out.println(position);
			 
		 }
	
	}
}