package com.kMeans;


import java.util.Random;



public class Point {
	protected int n;
	protected double[] valueTab;
	protected int cluster_id;
	
	public Point(int num)
	{
		this.n = num;
		this.valueTab = new double[this.n];
		this.cluster_id = -1;
	}
	public Point(int num, double[] valeur){
		this.n = num;
		this.valueTab = new double[this.n];
		this.valueTab = valeur;
		this.cluster_id = -1;
	}
	public double getValue(int i)
	{
		return this.valueTab[i];
	}
	
	public void setCluster(int idi)
	{
		this.cluster_id = idi;
	}
	public double[] getvaltab()
	{
		return this.valueTab;
	}
	public int getCluster()
	{
		return this.cluster_id;
	}
	public void setpos(double[]table)
	{
		if(table.length == this.n)
		{
			this.valueTab = table;
		}
	}
	public static double calculate(Point a , Point b, int n)
	{
		double delta = 0;
		for(int i=0; i< n; i++)
		{
			 delta += Math.pow(a.getValue(i) - b.getValue(i), 2);
			 
			 
		}
		delta = Math.sqrt(delta);
		
		return delta;
	}
	public static double[] valAletaoire( int n)
	{
		Random k = new Random();
		double[] val = new double[n];
		for(int i=0 ; i< n; i++)
		{
			val[i] = k.nextDouble();
		}
		
		return val;
		
	}
	
}
