package com.fenetre;

import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.kMeans.Fiche;
import com.kMeans.cluster;
import weka.core.Attribute;
public class ClusterStatWindow extends JFrame{
	JLabel phrase = new JLabel("les attributs les plus similaires sont : ");
	
	JComboBox<String> names = new JComboBox<String>();
	JLabel variances = new JLabel();
	JPanel container = new JPanel();
	public ClusterStatWindow(cluster cl, ArrayList<Attribute> listatts)
	{
		this.setTitle("analyse du cluster");
		this.setSize(500,300);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		container.setLayout(null);
		if(cl.getnuminst() != 0)
		{
			String[] nas = cl.analyser(listatts);
		}
		
		String[] nas = cl.analyser(listatts);
		
		for(int i=0; i< nas.length; i++)
		{
			names.addItem(nas[i]);
		}
		variances.setText("la variance : " + cl.variance());
		phrase.setBounds(10, 10, 500, 20);
		names.setBounds(10, 40, 300, 20);
		variances.setBounds(10 , 100, 300, 20);
		container.add(phrase);
		container.add(names);
		container.add(variances);
		this.setContentPane(container);
		this.setVisible(true);
		
	}

}
