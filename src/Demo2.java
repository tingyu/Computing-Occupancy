
import java.awt.*;


import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.JTextArea;               

/** Simple JList example illustrating
 *  <UL>
 *    <LI>The creation of a JList by passing values directly to
 *        the JList constructor, rather than using a ListModel, and
 *    <LI>Attaching a listener to determine when values change.
 *  </UL>
 *  1998-99 Marty Hall, http://www.apl.jhu.edu/~hall/java/
 */

public class Demo2 extends JFrame{
	  public static void main(String[] args) {
		    new Demo2();
		  }

	  private JList sampleJList;
		  private JTextField valueField;
			private static double pep;
			private static double transient_pep;
			private static double steady_pep; 
			private static double V; //the space air volume,ft^3
			private static double N; //the space CO2 concentration at the present time step, ppm
			private static double N_back; //the space CO2 concentration one time step back, ppm
			private static double time_step; //the time step, min
			private static double SA; //the supply airflow rate, scfm
			private static double Ci; //the CO2 concentration in the supply air, ppn
			private static double G; // the CO2 generation rate per person, scfm

			public static void inputdata_initial(double air_space, double present_CO2, double timeback_CO2,double time_step_min, double supply_airflow_rate, double supply_air_CO2, double generation_CO2){
				V = air_space;
				N = present_CO2;
				N_back = timeback_CO2;
				time_step = time_step_min;
				SA = supply_airflow_rate;
				Ci = supply_air_CO2;
				G = generation_CO2;
			}
			
			public static void transient_term()
			{
				transient_pep = V * (N - N_back) / (time_step* G * 1000000);
			}
			
			public static void steady_term()
			{
				steady_pep = SA * (N - Ci)/(G * 1000000);
			}
			/*
			public static void steady_state(double supply_airflow_rate, double present_CO2, double supply_air_CO2 )
			{
				double steady_state = supply_airdlow_rate * (present_CO2 - supply_air_CO2);
			}*/
			
			public static void compute_occupancy(){
				//pep = (V * (N - N_back) / time_step + SA * (N - Ci))/(G * 1000000);
				pep = transient_pep + steady_pep;
			}
			
		  
		  public Demo2() {
		    super("Computing Occupancy Demo");
		   // addWindowListener(new ExitListener());
		    Container content = getContentPane();
		    content.setBounds(0, 0, 5000, 10000);

		    // Create the JList, set the number of visible rows, add a
		    // listener, and put it in a JScrollPane.
		    String[] entries = { "Case 1 Simulator", "Case 2 Simulator", "Entry 3",
		                         "Entry 4", "Entry 5", "Entry 6" };
		    sampleJList = new JList(entries);
		    sampleJList.setVisibleRowCount(2);
		    Font displayFont = new Font("Serif", Font.BOLD, 18);
		    sampleJList.setFont(displayFont);
		    sampleJList.addListSelectionListener(new ValueReporter());
		    JScrollPane listPane = new JScrollPane(sampleJList);

		    

		   // JLabel label1 = new JLabel("Image and Text",
            //        JLabel.CENTER);
		    //add(label1);
		    
	      
	        JLabel label1, label2, label3;



	        //Create the first label.
	        label1 = new JLabel("Image and Text",
	                            JLabel.CENTER);
	        //Set the position of its text, relative to its icon:
	        label1.setVerticalTextPosition(JLabel.BOTTOM);
	        label1.setHorizontalTextPosition(JLabel.CENTER);

	        //Create the other labels.
	        label2 = new JLabel("Text-Only Label");

	        //Create tool tips, for the heck of it.
	        label1.setToolTipText("A label containing both image and text");
	        label2.setToolTipText("A label containing only text");
	     

	        //Add the labels.
	        add(label1);
	        add(label2);
	        content.add(label1, BorderLayout.SOUTH);
	    
		    
		    JPanel listPanel = new JPanel();
		    listPanel.setBackground(Color.white);
		    Border listPanelBorder =
		      BorderFactory.createTitledBorder("Test case simulator");
		    listPanel.setBorder(listPanelBorder);  

		    listPanel.add(listPane);
		    content.add(listPanel, BorderLayout.CENTER);
		    JLabel valueLabel = new JLabel("Computing Occupancy:");
		    valueLabel.setFont(displayFont);
		    valueField = new JTextField("None", 7);
		    valueField.setFont(displayFont);
		    JPanel valuePanel = new JPanel();
		    valuePanel.setBackground(Color.white);
		    Border valuePanelBorder =
		      BorderFactory.createTitledBorder("Test case Selection");
		    valuePanel.setBorder(valuePanelBorder);
		    valuePanel.add(valueLabel);
		    valuePanel.add(valueField);
		    content.add(valuePanel, BorderLayout.SOUTH);
		    content.add(valuePanel, BorderLayout.SOUTH);
		    pack();
		    

		    setVisible(true);
		  }

		  private class ValueReporter implements ListSelectionListener {
		    /** You get three events in many cases -- one for the deselection
		     *  of the originally selected entry, one indicating the selection
		     *  is moving, and one for the selection of the new entry. In
		     *  the first two cases, getValueIsAdjusting returns true,
		     *  thus the test below when only the third case is of interest.
		     */
		    public void valueChanged(ListSelectionEvent event) {
		      if (!event.getValueIsAdjusting()) 
		      //  valueField.setText(sampleJList.getSelectedValue().toString());
		    	  if(sampleJList.getSelectedValue().toString() =="Case 1 Simulator"){
		    		  transient_pep = 0;
		    		  SA = 850;
		    		  N = 644;
		    		  Ci = 386;
		    		  G = 0.01;
		    		  steady_term();
		    		  compute_occupancy();
		  		    JTextArea text = new JTextArea("the transient term of the equation were set to 0");
				   // listPanel.add(text);
		  		    add(text);
		    	  }
		      //  valueField.setText(sampleJList.getSelectedValue().toString());
	    	  if(sampleJList.getSelectedValue().toString() =="Case 2 Simulator"){
	    		  transient_pep=0;
	    		  SA = 850;
	    		  N = 633;
	    		  Ci = 386.9;
	    		  G = 0.01;
	    		  steady_term();
	    		  compute_occupancy();	    		  
	    	  }
		    	  valueField.setText(Double.toString(pep));
		    	//  valueField.setText(sampleJList.getSelectedValue().toString());
		    }
		  }
}
