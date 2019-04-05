package lria;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.experiment.Stats;
import weka.filters.Filter;

import weka.filters.unsupervised.attribute.ReplaceMissingValues;
import weka.gui.visualize.PlotData2D;
import weka.gui.visualize.VisualizePanel;

public class Data {
	private Instances instances;
	
	 public Data(String filename)
	 {
		 try {
			DataSource src = new DataSource(filename);
			this.instances = src.getDataSet();
			if(this.instances.classIndex() == -1)
			{
				this.instances.setClassIndex(this.instances.numAttributes()-1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 public void remplacer()
	 {
		 try
		 {
			 ReplaceMissingValues replace = new ReplaceMissingValues();
			 replace.setInputFormat(this.instances);
			 this.instances = Filter.useFilter(this.instances, replace);
			 
		 }catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 
	 }
	 public void discretiser()
	 {
			 Instances info = this.instances;
			 int n = info.numAttributes();
			 int m = info.numInstances();
			 int[] tab = new int[m];
			 Attribute att;
			 for(int i = 0; i< n; i++)
			 {
				boolean state = info.attribute(i).isNominal();
				 
				 if(state)
				 {
					att = info.attribute(i);
					int numval = att.numValues();
					
					 for(int v = 0; v< numval; v++)
					 {
						 info.renameAttributeValue(att, att.value(v), ""+v);
					 }
					 for(int l= 0; l< m; l++)
					 {
						 tab[l] = Integer.parseInt(info.get(l).stringValue(i));
						 
					 }
					 
					 info.replaceAttributeAt(new Attribute(att.name()), i);
					
					 for(int l= 0; l< m; l++)
					 {
						 info.get(l).setValue(i, tab[l]);
						 
					 }
					 
					
				 }
				
				 
			 }
			 this.instances = info;
			 
		 
	 }
	 
	 public void normaliser()
	 {
		 Instances info = this.instances;
		 int m = info.numInstances();
		 int n = info.numAttributes();
		 //int m = info.numInstances();
		 
		 for(int i = 0; i< n; i++)
		 {
			 Attribute att = info.attribute(i);
			 if(att.isNumeric())
			 {
				 Stats chiffre = info.attributeStats(i).numericStats;
				 double max = chiffre.max;
				 double min = chiffre.min;
				 double range = max- min;
				 if(range > 0)
				 {
					 for(int j = 0;j<m; j++)
					 {
						 double val = info.instance(j).value(i);
						 double newval = (val -min)/range;
						 info.instance(j).setValue(i, newval);
					 } 
				 }
				 
				 
			 }
		 }
		 this.instances = info;
	 }
	 /*private int transformer(double value) {
		// TODO Auto-generated method stub
		 int df = (int) (value - 65);
		return df;
	}*/
	public void visualiser() throws Exception
	 {
		 Instances info = this.instances;
		 PlotData2D plot = new PlotData2D(info);
		 VisualizePanel vp = new VisualizePanel();
		 vp.setName(info.relationName());
		 plot.setPlotName("name" + info.relationName());
		 vp.addPlot(plot);
		 JFrame fenetre = new JFrame("fenetre");
		 fenetre.setSize(500, 400);
		 fenetre.getContentPane().setLayout(new BorderLayout());
		 fenetre.getContentPane().add(vp, BorderLayout.CENTER);
		 fenetre.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		 fenetre.setLocationRelativeTo(null);
		 fenetre.setVisible(true);
		 
	 }
	 
	 public void pretraitement()
	 {
		 this.remplacer();
		 this.discretiser();
		 this.normaliser();
	 }
	 public int numatt()
	 {
		 return this.instances.numAttributes();
		 
	 }
	 public int numattNumerique()
	 {
		 int a = 0;
		 for(int i=0; i< this.numatt(); i++)
		 {
			 if(instances.attribute(i).isNumeric())
			 {
				 a++;
			 }
		 }
		 return a;
	 }
	 public int numinstances()
	 {
		 return instances.numInstances();
		 
	 }
	 public String nomAttribut(int i)
	 {
		 return instances.attribute(i).name();
	 }
	 public String[] listAttribut()
	 {
		 int n = instances.numAttributes();
		 String[] att = new String[n];
		 for(int i=0; i< n; i++)
		 {
			 att[i] = instances.attribute(i).name();
		 }
		 return att;
	 }
	 public Instance getinstance(int i)
	 {
		 return this.instances.instance(i);
	 }
	 public Stats obtenirstat(int i)
	 {
		 return instances.attributeStats(i).numericStats;
	 }
	 public double meanmode(int i)
	 {
		 return this.instances.meanOrMode(i);
	 }
	 public Attribute attribut(int i)
	 {
		 return instances.attribute(i);
	 }
	 public double[] valeur(int i)
	 {
		 int n = this.numatt();
		 double[] crunches = new double[this.numattNumerique()];
		 Instance cette_instance = this.getinstance(i);
		 int index_valeur = 0;
		 for(int a=0; a< n; a++)
		 {
			 
			 Attribute tab_attribut = this.attribut(a);
			 if(tab_attribut.isNumeric())
			 {
				 crunches[index_valeur] = cette_instance.value(a);
				 index_valeur++;
			 }
		 }
		 return crunches;
	 }
	 public void ajouterAttributCluster()
	 {
		 ArrayList<String> chobaka = new ArrayList<String>();
		 chobaka.add("1");
		 chobaka.add("2");
		 chobaka.add("3");
		 chobaka.add("4");
		 chobaka.add("5");
		 chobaka.add("6");
		 chobaka.add("7");
		 chobaka.add("8");
		 chobaka.add("9");
		 chobaka.add("10");
		 chobaka.add("11");
		 chobaka.add("12");
		this.instances.insertAttributeAt(new Attribute("cluster", chobaka),this.instances.numAttributes());
	 }
	  
	 
	public String toString()
	 {
		 return this.instances.toString();
	 }
	
}
