package com.fenetre;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import lria.Data;
import weka.core.Attribute;
import weka.experiment.Stats;

public class attributStatWindow extends JFrame{
	JLabel nomFichier = new JLabel();
	JLabel statistiques = new JLabel("statistics");
	JLabel stat1 = new JLabel("");
	JLabel stat5 = new JLabel("");
	JLabel stat2 = new JLabel("");
	JLabel stat3 = new JLabel("");
	JLabel stat4 = new JLabel("");
	JPanel container = new JPanel();
	public attributStatWindow(Data info, int numatt, String fileName)
	{
		Attribute att = info.attribut(numatt);
		this.setTitle(fileName);
		this.setSize(500,500);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		nomFichier.setText(fileName + " > " + att.name());
		stat5.setText("type : " + Attribute.typeToString(att));
		stat1.setText("description : " + att.toString());
		if(att.isNumeric())
		{
			Stats chiffre = info.obtenirstat(numatt);
			stat3.setText("variance : " + Math.pow(chiffre.stdDev, 2));
			stat4.setText("max : " + chiffre.max + "- min : " + chiffre.min);
		}
		
		
		
		
		
		stat2.setText("moyenne ou mode : " + info.meanmode(numatt));
		container.setLayout(null);
		nomFichier.setBounds(10, 10, 300, 30);
		stat5.setBounds(10, 50, 500, 30);
		stat1.setBounds(10, 100, 500, 30);
		stat3.setBounds(10,  150, 500,  30);
		stat2.setBounds(10, 200, 500, 30);
		stat4.setBounds(10, 250,  500, 30);
		container.add(nomFichier);
		container.add(stat1);
		container.add(stat2);
		container.add(stat3);
		container.add(stat4);
		container.add(stat5);
		this.setContentPane(container);
		this.setVisible(true);
	}

}
