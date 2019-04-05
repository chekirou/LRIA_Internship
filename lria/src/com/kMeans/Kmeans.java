package com.kMeans;

import java.util.ArrayList;



public class Kmeans {
	private int k;
	private Fiche fiche;
	private cluster[] clusters;
	private int numatt;
	private int numinst;
	public Kmeans(int numk, Fiche donne)
	{
		this.k = numk;
		this.numatt = donne.numatt;
		this.numinst = donne.numInstance;
		this.fiche = donne;
		this.clusters = new cluster[this.k];
		for(int i =0; i< this.k;i++)
		{
			this.clusters[i] = new cluster(this.numatt);
		}
		
		
	}
	public cluster[] go()
	{
		boolean terminer = false;
		//initialisation 
		
		double[] tab = new double[this.k];
		Point ptn;
		int h, i,j, d, a;
		double distance , asap;
		
		for( i = 0 ; i< this.k ; i++)
		{
			ptn = new Point(this.numatt, Point.valAletaoire(this.numatt));
			
			this.clusters[i].setCentroid(ptn);
			
			
			
		}
		Point pt;
		double mini = 0;
		int  new_id ;
		h=0;
		//calcule
		while(!terminer)
		{
			h++;	
			a = 0;
			for( i=0; i< this.numinst ; i++)
			{
				
				pt=  this.fiche.getPoint(i);
				
				d = pt.getCluster();
				for( j = 0; j<this.k; j++)
				{
					tab[j] = Point.calculate(pt, this.clusters[j].getCentroid(), this.numatt);
					
				}
				
				mini = tab[0];
				new_id = 0;
				for(int u =0 ; u< this.k ; u++){
					
					if(tab[u] < mini)
					{
						mini = tab[u];
						new_id = u;
						
					}
				}
				if(d != new_id){ a++; }
				pt.setCluster(new_id);


			}
			
			for(i=0; i< this.k ; i++)
			{
				clusters[i].liste.clear();
				
			}
			
			
			for( i=0 ; i < this.numinst ; i++)
			{
				pt = this.fiche.getPoint(i);
				
				clusters[pt.getCluster()].addPoint(pt);
				
				
				
			}
			distance = 0;
			for(i=0 ; i< this.k ; i++)
			{
				
				pt = clusters[i].getCentroid();
				
				clusters[i].recalculatepos();
				
				
				
				distance +=Point.calculate(pt, clusters[i].getCentroid(), this.numatt);
				
				
				
			}
			System.out.println(h);
			System.out.println("changes : " + a );
			System.out.println("distances :  " + distance);
			if( a==0)
			{
				terminer = true;
				System.out.println("changes :  " + a);
				
			}
			
			
		}
		
		return clusters;
		
	}
	}


