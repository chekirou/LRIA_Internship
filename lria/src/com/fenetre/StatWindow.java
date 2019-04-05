package com.fenetre;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.kMeans.Fiche;
import com.kMeans.Kmeans;
import com.kMeans.cluster;

import lria.Data;

public class StatWindow extends JFrame{
	private String name;
	private Data information;
	JLabel nomFichier = new JLabel();
	JLabel statistiques = new JLabel("statistics");
	JLabel stat1 = new JLabel("");
	JLabel stat2 = new JLabel("");
	JLabel stat3 = new JLabel("Statistics by attribute");
	JLabel stat4 = new JLabel("select an attribute");
	JTextField selectionK = new JTextField("number of clusters");
	JComboBox<String> attcombo = new JComboBox<String>();
	JLabel division = new JLabel("--------------------------------------------------------------------------");
	JLabel pretraite = new JLabel("pre-process (replace missing values, discretisize, normalize )");
	JButton appliquer = new JButton("apply");
	JButton valider = new JButton("validate");
	JButton visualiser = new JButton("vizualise");
	JButton appKmeans = new JButton("K-means");
	JPanel container = new JPanel();
	public StatWindow(String fileName)
	{
		name = fileName;
		this.setTitle("L.R.I.A");
		this.setSize(500,500);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		
		
		
		this.nomFichier.setText(fileName + ": ");
		
		Data info = new Data( fileName + ".arff");
		information = info;
		int n = info.numatt();
		stat1.setText("number of attributes : "+n);
		stat2.setText("number of instances : " + info.numinstances());
		attcombo.setPreferredSize(new Dimension(200,30));
		String[] listatt = info.listAttribut();
		for(int i=0; i< n; i++)
		{
			attcombo.addItem(listatt[i]);
		}
		valider.addActionListener(new actionBouton1());
		appliquer.addActionListener(new actionBouton2());
		visualiser.addActionListener(new actionBouton3());
		appKmeans.addActionListener(new actionBouton4());
		container.setLayout(null);
		nomFichier.setBounds(10, 10, 300, 20);
		stat1.setBounds(10, 30, 300, 20);
		stat2.setBounds(10, 50, 300, 20);
		stat3.setBounds(10, 70, 300, 20);
		stat4.setBounds(10, 90, 150, 30);
		attcombo.setBounds(160, 90, 250, 30);
		valider.setBounds(10,130, 300, 30);
		division.setBounds(10, 160, 500, 20);
		pretraite.setBounds(10, 190, 500, 20);
		appliquer.setBounds(10, 210, 100, 30);
		visualiser.setBounds(10, 300, 100, 30);
		selectionK.setBounds(10, 340, 100, 30);
		appKmeans.setBounds(10,380,100,30);
		
		container.add(nomFichier);
		container.add(stat1);
		container.add(stat2);
		container.add(stat3);
		container.add(stat4);
		container.add(attcombo);
		container.add(valider);
		container.add(division);
		container.add(pretraite);
		container.add(appliquer);
		container.add(visualiser);
		container.add(selectionK);
		container.add(appKmeans);
		this.setContentPane(container);
		this.setVisible(true);
		
	}
	public void setinfo(Data donne)
	{
		this.information = donne;
	}
	class actionBouton1 implements ActionListener
	{

		
		public void actionPerformed(ActionEvent arg0) {
			attributStatWindow fen3 = new attributStatWindow(information, attcombo.getSelectedIndex(), name);
			
			
			
		}
		
	}
	class actionBouton2 implements ActionListener
	{

		
		public void actionPerformed(ActionEvent arg0) {
			information.pretraitement();
			
			
			
		}
		
	}
	class actionBouton3 implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			try {
				information.visualiser();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	class actionBouton4 implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			String k = selectionK.getText();
			int ko = Integer.parseInt(k);
			Fiche unef = new Fiche(information);
			Kmeans km= new Kmeans(ko, unef);
			cluster[] cl = km.go();
			unef.actualiser();
			information = unef.getdon();
			kmeanswindow fenkm = new kmeanswindow(information, cl, unef, 0);
			
		}
	}
	

}












