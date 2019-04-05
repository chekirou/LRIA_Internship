package com.kMeans;

import java.util.ArrayList;

import lria.Data;
import weka.core.Attribute;
import com.kMeans.Point;

public class Fiche {
	private Data Information;
	protected int numatt;
	protected int numInstance;
	public ArrayList<Attribute> listattributs = new ArrayList<Attribute>();
	protected ArrayList<Point> listpoints = new ArrayList<Point>();
	public Fiche(ArrayList<Attribute> listatt, ArrayList<Point> listpoints, int numatt, int numInstance)
	{
		this.listattributs = listatt;
		this.listpoints = listpoints;
		this.numatt = numatt;
		this.numInstance = numInstance;
		
	}
	public Fiche(Data info)
	{
		this.numatt = info.numattNumerique();
		this.numInstance = info.numinstances(); 
		for(int i=0; i< info.numatt(); i++)
		{
			Attribute att = info.attribut(i);
			if(att.isNumeric())
			{
				listattributs.add(att);
			}
		}
		for(int a =0 ; a< this.numInstance; a++)
		{
			Point pt = new Point(this.numatt, info.valeur(a));
			
			listpoints.add(pt);
			
		}
		Information = info;
	}
	public Point getPoint(int i)
	{
		return this.listpoints.get(i);
	}
	public int getnumatt()
	{
	return this.numatt;
	}
	public int getnuminstances()
	{
		return this.numInstance;
	}
	public double variance()
	{
		int n = this.numatt;
		double[] pos = new double[n];	
		Point pt;
		for(int p =0; p < n; p++)
		{
			pos[p] = 0;
		} 
		int cmpatt = 0;
		for(int i = 0; i < this.numInstance; i++ )
		{
			pt = this.getPoint(i);
			for(cmpatt =0; cmpatt < n; cmpatt++)
			{
				
				pos[cmpatt] += pt.getValue(cmpatt);
				
			}
			
			
		}
		
		
		for(int i = 0; i< n; i++)
		{
			pos[i] /= this.numInstance;
		}
		Point geocluster = new Point(n , pos);
		double variance =0;
		
		
			for(int i=0; i< this.numInstance; i++)
			{
				pt = this.getPoint(i);
				variance += Math.pow(Point.calculate(pt, geocluster, this.numatt), 2);
			}
			variance /= this.numInstance;
		
		return variance;
	
	}
	public Data getdon()
	{
		return this.Information;
	}
	public void actualiser()
	{
		int id;
		this.Information.ajouterAttributCluster();
		for(int i=0; i< this.numInstance; i++)
		{
			id= this.listpoints.get(i).cluster_id;
			Information.getinstance(i).setValue(Information.numatt()-1, id);
		}
	}
}
