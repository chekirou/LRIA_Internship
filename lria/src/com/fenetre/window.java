package com.fenetre;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import lria.Data;


public class window extends JFrame {
	
	JPanel container = new JPanel();
	
	JComboBox<String> combo = new JComboBox<String>();
	JLabel pres1 = new JLabel("USTHB ");
	JLabel pres2 = new JLabel("internship DATA Mining ");
	JLabel pres3 = new JLabel("k-means algorithm for clustering ");
	JLabel pres4 = new JLabel("chekirou hakim");
	JLabel pres5 = new JLabel("L.R.I.A  S baba ALI");
	JLabel text = new JLabel("select the file");
	
	public window(){
		this.setTitle("L.R.I.A");
		
		this.setSize(500,500);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		//container.setBackground(Color.white);
		container.setLayout(null);
		combo.addActionListener(new ItemAction());
		combo.setPreferredSize(new Dimension(150,30));
		combo.addItem("airline");
		combo.addItem("autos");
		combo.addItem("breast-cancer");
		combo.addItem("contact-lenses");
		combo.addItem("cpu");
		combo.addItem("cpu.with.vendor");
		combo.addItem("credit-g");
		combo.addItem("diabetes");
		combo.addItem("glass");
		combo.addItem("horse_colic");
		combo.addItem("hypothyroid");
		combo.addItem("ionosphere");
		combo.addItem("iris.2D");
		combo.addItem("iris");
		combo.addItem("kdd_train");
		combo.addItem("labor");
		combo.addItem("lymph");
		combo.addItem("ReutersCorn-test");
		combo.addItem("ReutersCorn-train");
		combo.addItem("ReutersGrain-test");
		combo.addItem("ReutersGrain-train");
		combo.addItem("Segment-challenge");
		combo.addItem("Segment-test");
		combo.addItem("soybean");
		combo.addItem("supermarket");
		combo.addItem("unbalanced");
		combo.addItem("vote");
		combo.addItem("vowel");
		combo.addItem("water_treatment_plant");
		combo.addItem("weather.nominal");
		combo.addItem("weather.numeric");
		combo.addItem("zoo");
		
		
		
		
		JPanel top = new JPanel();
		top.add(text);
		top.add(combo);
		
		top.setBounds(50, 200, 300, 50);
		
	    JButton btnValider = new JButton("valider");
	    btnValider.addActionListener(new actionbouton());
		btnValider.setBounds(230, 300, 100, 50);
		pres1.setBounds(10, 10, 300, 30);
		pres2.setBounds(10, 40, 300, 30);
		pres3.setBounds(10, 70, 300, 30);
		pres4.setBounds(10, 100, 300, 30);
		pres5.setBounds(10, 130, 300, 30);
		 
	    container.add(btnValider); 
		container.add(pres1);
		container.add(pres2);
		container.add(pres3);
		container.add(pres4);
		container.add(pres5);
		container.add(top);
		
		this.setContentPane(container);
		this.setVisible(true);
	}
	class actionbouton implements ActionListener
	{

		
		public void actionPerformed(ActionEvent arg0) {
			
			StatWindow fen2 = new StatWindow((String) combo.getSelectedItem());
			
			
		}
		
	}
	class ItemAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.out.println("action sur " + combo.getSelectedItem());
		}
	}

}
