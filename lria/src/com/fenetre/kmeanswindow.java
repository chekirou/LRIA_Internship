package com.fenetre;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


import com.kMeans.Fiche;
import com.kMeans.Kmeans;
import com.kMeans.Point;
import com.kMeans.cluster;

import lria.Data;
import weka.core.Attribute;


public class kmeanswindow extends JFrame{
	private Data donne;
	private cluster[] cls;
	private Fiche fenchui;
	private cluster[] supremum;
	private double boncote = 0;
	private ArrayList<Attribute> listatt;
	private ArrayList<Point> leftovers1 = new ArrayList<Point>(); 
	private ArrayList<Point> leftovers2 = new ArrayList<Point>(); 
 	JLabel nomFichier = new JLabel();
	JLabel inter = new JLabel();
	JLabel intra = new JLabel();
	JLabel silouhette = new JLabel();
	
	JComboBox<Double> clanalysis = new JComboBox<Double>();
	JButton analyser = new JButton("analyse");
	JButton visualiser = new JButton("vizualise");
	
	JButton optimiser =  new JButton("improve");
	JPanel container = new JPanel();
	public kmeanswindow(Data info, cluster[] cl, Fiche lafiche, int optionvis)
	{
		
		this.donne = info;
		this.fenchui = lafiche;
		this.cls = cl;
		this.listatt = lafiche.listattributs;
		this.setTitle("K-means");
		this.setSize(600,500);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		container.setLayout(null);
		nomFichier.setText("number of clusters : " + cl.length);
		
		intra.setText("sum of squared errors: " + cluster.distanceintra(cl, lafiche.getnuminstances()));
		for(int i=0; i< cl.length; i++)
		{
			clanalysis.addItem(cl[i].variance());
		}
		
		double var = lafiche.variance();
		silouhette.setText("silouhette coeffcicient > 0.3: " + this.silhouette(this.cls, lafiche, 0)  + "% var : " + var);
		inter.setText("correctly clustered :" + this.boncote +"%");
		visualiser.addActionListener(new actionBouton3());
		analyser.addActionListener(new ActionBouton4());
		
		if(optionvis ==0 )
		{
			this.optimiser(cl, lafiche);
		}
		
		optimiser.addActionListener(new ActionBouton8());
		nomFichier.setBounds(10, 10, 300, 20);
		visualiser.setBounds(10, 380, 100, 30);
		inter.setBounds(10, 120, 300, 30);
		intra.setBounds(10, 60, 300, 30);
		silouhette.setBounds(10, 90, 450, 30);
		optimiser.setBounds(10, 420, 100 , 30);
		
		clanalysis.setBounds(10, 300, 300, 30);
		analyser.setBounds(10,350, 300, 30);
		container.add(nomFichier);
		if(optionvis == 0 )
		{
			container.add(visualiser);
			
			container.add(optimiser);
			
			
			
			
		}
		
		container.add(inter);
		container.add(intra);
		container.add(silouhette);
		container.add(clanalysis);	
		container.add(analyser);	
		this.setContentPane(container);
		this.setVisible(true);
		
		
	}
	
	public double silhouette(cluster[] cl, Fiche lafiche, int optionplus)
	{
		double silouette= 0, asap;
		int i, j, k, p;
		double ai, bi, min;
		double[] tab = new double[cl.length -1];
		Point pt;
		//int g = lafiche.getnuminstances();
		int h=0;
		while(h< cl.length)
		{
			for(i =0; i< cl[h].getnuminst(); i++)
			{
				ai=0;
				bi =0;
				pt = cl[h].liste.get(i);
				
				ai = cluster.distance_in(pt, cl[h]);
				j=0;
				k =0;
				while(j< cl.length)
				{
					
					if(j != h && cl[j].getnuminst() != 0)
					{
						tab[k] = cluster.distance_in(pt, cl[j]);
						k++;
					}
					j++;
				}
				min = tab[0];
				
				for( p=1; p< k; p++)
				{
					
					if(min > tab[p])
					{
						min = tab[p];
					}
					
				}
				bi= min;
				
				
				if(bi > ai)
				{
					asap = (bi - ai) / bi;
				}
				else
				{
					
					asap = (bi - ai) / ai;
				}
				
				if(asap > 0)
				{
					this.boncote++;
				}
				
				if(asap > 0.3)
				{
					silouette++;
					
				}
				else if( optionplus == 1 && asap <0.2 && asap > -0.1)
				{
					this.leftovers1.add(pt);
					this.cls[h].liste.remove(i);
				}
				else if(optionplus== 1 && asap <-0.1)
				{
					this.leftovers2.add(pt);
					this.cls[h].liste.remove(i);
				}
			}
			h++;
			
		}
		for(int p1 = 0; p1< this.cls.length; p1++)
		{
			this.cls[p1].recalculatepos();
		}
		int m= lafiche.getnuminstances();
		this.boncote /= m;
		this.boncote *= 100;
		silouette /= m;
		return silouette *100;
	}
	public void optimiser(cluster[] cl1, Fiche lafiche )
	{
		
		cluster[] cl2;
		int kl = cl1.length;
		kl = Math.round(kl/2);
		
		this.silhouette(cl1, lafiche, 1);
		Fiche newfiche = new Fiche(this.listatt, this.leftovers1, lafiche.getnumatt(), this.leftovers1.size());
		
		Kmeans bestkmeans = new Kmeans(kl, newfiche);
		
		cl2 = bestkmeans.go();
		
		cluster[] supremum = new cluster[this.cls.length + cl2.length];
		int j=0;
		int k=0;
		while(j < this.cls.length)
		{
			supremum[j] = this.cls[j];
			j++;
		}
		k=j;
		j=0;
		while(j < cl2.length)
		{
			supremum[k] = cl2[j];
			j++;
			k++;
		}
		this.supremum = supremum;
		this.reattribuer();
		
		/*j=0;
		while(j < cl3.length)
		{
			supremum[k] = cl3[j];
			j++;
			k++;
		}*/
		
		//return supremum;
	}
	private int facto(int kl) {
		// TODO Auto-generated method stub
		if(kl == 0)
		{
			return 1;
		}
		else
		{
			int facto =1;
			for(int i=2; i<= kl; i++)
			{
				kl*= i;
			}
			return facto;
		}
		
	}
	private void reattribuer()
	{
		Point pt;
		int u, h = this.supremum.length, new_id;
		double mini, g;
		for(int i=0; i< this.leftovers2.size(); i++)
		{
			pt=  this.leftovers2.get(i);
			mini = Point.calculate(pt, this.supremum[0].getCentroid(), this.fenchui.getnumatt());
			new_id = 0;
			for( u =1 ; u < h ; u++){
				g = Point.calculate(pt, this.supremum[u].getCentroid(), this.fenchui.getnumatt());
				if(mini > g)
				{
					mini = g;
					new_id = u;
					
				}
			}
			pt.setCluster(new_id);
			this.supremum[new_id].liste.add(pt);
			
		
		}
		for(u =1 ; u < h ; u++)
		{
			this.supremum[u].recalculatepos();
		}
	}
	class actionBouton3 implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			try {
				donne.visualiser();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	class ActionBouton4 implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			ClusterStatWindow showCl = new ClusterStatWindow(cls[ (int) clanalysis.getSelectedItem()], listatt);
		}
	}
	class ActionBouton8 implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			
			kmeanswindow meilleur = new kmeanswindow(donne, supremum, fenchui, 1);
		}
	}

}
