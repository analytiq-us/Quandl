package org.zillow;

import java.awt.*;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.util.List;
import org.knime.core.data.DataTableSpec;
import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;
import org.knime.core.node.NotConfigurableException;


/**
 * <code>NodeDialog</code> for the "Zillow" Node.
 * 
 *
 * 
 * 
 * @author Sampreeth
 */
public class QuandlZILLOWNodeDialog  extends NodeDialogPane {
    static JTextField m_api;
    static JComboBox m_indicator;
    private JPanel options;
    static  String area_code;
    
           JComboBox m_area;
           JComboBox m_state;
           JComboBox m_city;
           JButton get;
           String area;
           JFrame frame;
           JTextField m_zip;
           URL url;
           JTextField startDate;
           JTextField endDate;
           String [] selector= new String[0];
           Integer [] day ;
           String area_type;
           DefaultComboBoxModel model ;
           
          
    /**
     * New pane for configuring Zillow node dialog.
     * This is just a suggestion to demonstrate possible default dialog
     * components.
     */
    protected QuandlZILLOWNodeDialog() {
    	
    	       super();
    	       if (QuandlZILLOWNodeModel.m_config == null)
    				QuandlZILLOWNodeModel.m_config = new QuandlZILLOWConfig();
    	       
    	       String [] s = {"State","County","Metro","City","Zipcode"};
    	       String [] ind = CSVReader("indicators.csv",0);
    	      
    	       options = new JPanel();
    	     
    	       
    	       BoxLayout boxlayout = new BoxLayout(options, BoxLayout.Y_AXIS);
    	       options.setLayout(boxlayout);

 
    	       JPanel panel_Api = new JPanel (new FlowLayout());
    	        	       m_api= new JTextField("",20);
    	       panel_Api.add(new JLabel ("Enter API KEY :  "));
    	       panel_Api.add(m_api);
    	       
    	       JPanel panel_Indicator =new JPanel (new FlowLayout());
    	       m_indicator = new JComboBox(ind);
    	       panel_Indicator.add(new JLabel ("Indicator"));
    	       panel_Indicator.add(m_indicator);
    	       
    	       JPanel panel_area = new JPanel (new FlowLayout());
    	       m_area = new JComboBox(s);
    	       
    	       get = new JButton("Go");
    	       get.addActionListener(new getAction());
    	       panel_area.add(new JLabel ("Area Type :"));
    	       panel_area.add(m_area);
    	       panel_area.add(get);
    	       
    	       JPanel DatePicker = new JPanel( new FlowLayout());
    	       DatePicker.setBorder(BorderFactory.createTitledBorder(BorderFactory
    	                .createEtchedBorder(), "Date Range (Optional):"));
    	       JLabel lblStartDate = new JLabel("Start :");
    			lblStartDate.setBounds(17, 176, 45, 16);
    			DatePicker.add(lblStartDate);

    			startDate = new JTextField(10);
    			//startDate.setBounds(63, 170, 100, 26);
    			startDate.setEditable(false); 
    	        DatePicker.add(startDate);
    	      
    	        JButton startDateBtn = new JButton("-");
    			startDateBtn.addActionListener(new ActionListener() {
    				public void actionPerformed(ActionEvent e) {

    					startDate.setText(new DatePicker().setPickedDate());

    					if (!"".equals(startDate.getText().trim()) && !"".equals(endDate.getText().trim())) {

    						try {

    							Date start = new SimpleDateFormat("yyyy-MM-dd").parse(startDate.getText().trim());
    							Date end = new SimpleDateFormat("yyyy-MM-dd").parse(endDate.getText().trim());
    							if (start.getTime() > end.getTime()) {

    								startDate.setText("");

    								JOptionPane.showMessageDialog(null, "End Date earlier than Start Date", "Wrong Selection",
    										JOptionPane.WARNING_MESSAGE);
    							}

    						} catch (ParseException e1) {
    							e1.printStackTrace();
    						}

    					}
    				}
    			});
    			//startDateBtn.setBounds(163, 175, 29, 16);
    			DatePicker.add(startDateBtn);
    			JLabel lblEndDate = new JLabel("End :");
    			//lblEndDate.setBounds(213, 175, 36, 16);
    			DatePicker.add(lblEndDate);

    			endDate = new JTextField(10);
    			//endDate.setBounds(255, 170, 100, 26);
    			endDate.setEditable(false);
    			DatePicker.add(endDate);
    			JButton endDateBtn = new JButton("-");
    			endDateBtn.addActionListener(new ActionListener() {
    				public void actionPerformed(ActionEvent e) {

    					endDate.setText(new DatePicker().setPickedDate());

    					if (!"".equals(endDate.getText().trim()) && !"".equals(startDate.getText().trim())) {

    						try {

    							Date start = new SimpleDateFormat("yyyy-MM-dd").parse(startDate.getText().trim());
    							Date end = new SimpleDateFormat("yyyy-MM-dd").parse(endDate.getText().trim());
    							if (start.getTime() > end.getTime()) {

    								endDate.setText("");

    								JOptionPane.showMessageDialog(null, "End Date earlier than Start Date", "Wrong Selection",
    										JOptionPane.WARNING_MESSAGE);
    							}

    						} catch (ParseException e1) {
    							e1.printStackTrace();
    						}

    					}

    				}
    			});
    			//endDateBtn.setBounds(355, 175, 29, 16);
    			DatePicker.add(endDateBtn);
    	       
    	       options.add(panel_Api);
    	       options.add(panel_Indicator);
    	       options.add(panel_area);
    	       options.add(DatePicker);
    	   
    	       
    	       
     addTab ("Options",options);
    }
    
    class getAction implements ActionListener {
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		String a =m_area.getSelectedItem().toString();
    		//System.out.println(a);
    		GUI(a);
    		
    	}
    }   
    
    void GUI(String a){
    	  frame = new JFrame ();
    	 frame.setSize(400,400);
    	 frame.setLocationRelativeTo(null);
    	 
    	 if (a == "State") {
    		 JPanel n = new JPanel (new FlowLayout());
    		 
  	         area_type = "S";
  	        String[] S = CSVReader("areas_state.txt",0);
  	        
  	          m_state = new JComboBox (S);
  	         n.add(new JLabel("Select State"));
  	         n.add(m_state);
  	         JButton k = new JButton("OK");
  	         k.addActionListener(new action());
  	         n.add(k);
  	         frame.add(n);
  	         frame.setVisible(true);
    	 }
    	 
    	 else if (a == "Zipcode") {
    		 JPanel n = new JPanel (new FlowLayout());
    		 area_type= "Z";
    		 
    		 n.add(new JLabel("Enter 5-digit Zip Code :"));
    		 m_zip = new JTextField("",10);
    		 n.add(m_zip);
    		 JButton z = new JButton ("OK");
    		 z.addActionListener (new act());
    		 n.add(z);
    		 frame.add(n);
    		 frame.setVisible(true);
    	 }
    	 else  if (a== "County") {
    		 JPanel pane = new JPanel ();
    		 
    		 area_type= "CO";
    		 BoxLayout boxlayout = new BoxLayout(pane, BoxLayout.Y_AXIS);
  	         pane.setLayout(boxlayout);
    		 String[] S = {
    				 " AK"," AL"," AR"," AZ"," CA"," CO"," CT"," DC"," DE"," FL"," GA"," HI"," IA"," ID"," IL"," IN"," KS"," KY"," LA"," MA"," MD"," ME",
    				 " MI"," MN"," MO"," MS"," MT"," NC"," ND"," NE"," NH"," NJ"," NM"," NV"," NY"," OH"," OK"," OR"," PA"," RI"," SC"," SD"," TN"," TX",
    				 " UT"," VA"," VT"," WA"," WI"," WV"," WY"};
    		 area= "county";
    		 String [] def = StateSearch(" AK","areas_county");
             
    		 JPanel k1 = new JPanel (new FlowLayout());
    		 JPanel k2 =new JPanel (new FlowLayout());
    		 JPanel k3 = new JPanel (new FlowLayout());
    		 m_state = new JComboBox(S);
    		 m_city = new JComboBox(def);
    		 
    		 JButton b1= new JButton ("ok");
    		 b1.addActionListener(new GetAreaCode());
    		 m_state.addActionListener (new ActionListener () {
    			    public void actionPerformed(ActionEvent e) {
    			    	String symbol= m_state.getSelectedItem().toString();
    			    	//System.out.print(symbol);
    			        selector = StateSearch(symbol,"areas_county") ;
    			        model = new DefaultComboBoxModel(selector);
    			        m_city.setModel(model);
    			    }
    			});
    		 
    		
    		 k1.add(new JLabel("Select State :"));
    		 k1.add(m_state);
    		 k2.add(new JLabel ("Select County :"));
    		 k2.add(m_city);
    		 k3.add(b1);
    		 pane.add(k1);
    		 pane.add(k2);
    		 pane.add(k3);
    		
    		 frame.add(pane);
    		 frame.setVisible(true);
    		 
    	 }
    	 
    	 else if(a== "Metro") {
    		 JPanel pane = new JPanel ();
    		 area_type= "M";
    		 
    		 BoxLayout boxlayout = new BoxLayout(pane, BoxLayout.Y_AXIS);
  	         pane.setLayout(boxlayout);
    		 String[] S = {
    				 " AK"," AL"," AR"," AZ"," CA"," CO"," CT"," DC"," DE"," FL"," GA"," HI"," IA"," ID"," IL"," IN"," KS"," KY"," LA"," MA"," MD"," ME",
    				 " MI"," MN"," MO"," MS"," MT"," NC"," ND"," NE"," NH"," NJ"," NM"," NV"," NY"," OH"," OK"," OR"," PA"," RI"," SC"," SD"," TN"," TX",
    				 " UT"," VA"," VT"," WA"," WI"," WV"," WY"};
    		 area= "metro";
    		 String [] def = StateSearch(" AK","areas_metro");
    		 JPanel k1 = new JPanel (new FlowLayout());
    		 JPanel k2 =new JPanel (new FlowLayout());
    		 JPanel k3 = new JPanel (new FlowLayout());
    		 m_state = new JComboBox(S);
    		 m_city = new JComboBox(def);
    		 
    		 JButton b1= new JButton ("ok");
    		 b1.addActionListener(new GetAreaCode());
    		 m_state.addActionListener (new ActionListener () {
    			    public void actionPerformed(ActionEvent e) {
    			    	String symbol= m_state.getSelectedItem().toString();
    			    	//System.out.print(symbol);
    			        selector = StateSearch(symbol,"areas_metro") ;///
    			        model = new DefaultComboBoxModel(selector);
    			        m_city.setModel(model);
    			    }
    			});
    		 
    		
    		 k1.add(new JLabel("Select State :"));
    		 k1.add(m_state);
    		 k2.add(new JLabel ("Select Metro :"));
    		 k2.add(m_city);
    		 k3.add(b1);
    		 pane.add(k1);
    		 pane.add(k2);
    		 pane.add(k3);
    		
    		 frame.add(pane);
    		 frame.setVisible(true);
    		 
    		 
    		 
    	 }
    	 
    	 else if (a =="City") {
    		 JPanel pane = new JPanel ();
    		 area_type = "C";
    		 
    		 BoxLayout boxlayout = new BoxLayout(pane, BoxLayout.Y_AXIS);
  	         pane.setLayout(boxlayout);
    		 String[] S = {
    				 " AK"," AL"," AR"," AZ"," CA"," CO"," CT"," DC"," DE"," FL"," GA"," HI"," IA"," ID"," IL"," IN"," KS"," KY"," LA"," MA"," MD"," ME",
    				 " MI"," MN"," MO"," MS"," MT"," NC"," ND"," NE"," NH"," NJ"," NM"," NV"," NY"," OH"," OK"," OR"," PA"," RI"," SC"," SD"," TN"," TX",
    				 " UT"," VA"," VT"," WA"," WI"," WV"," WY"};
    		 area= "city";
    		 String [] def = StateSearch(" AK","areas_city");
    		 JPanel k1 = new JPanel (new FlowLayout());
    		 JPanel k2 =new JPanel (new FlowLayout());
    		 JPanel k3 = new JPanel (new FlowLayout());
    		 m_state = new JComboBox(S);
    		 
    		 m_city = new JComboBox(def);
    		
    		 JButton b1= new JButton ("ok");
    		 b1.addActionListener(new GetAreaCode());
    		 m_state.addActionListener (new ActionListener () {
    			    public void actionPerformed(ActionEvent e) {
    			    	String symbol= m_state.getSelectedItem().toString();
    			    	//System.out.print(symbol);
    			        selector = StateSearch(symbol,"areas_city") ;///
    			        model = new DefaultComboBoxModel(selector);
    			        m_city.setModel(model);
    			    }
    			});
    		 
    		
    		 k1.add(new JLabel("Select State :"));
    		 k1.add(m_state);
    		 k2.add(new JLabel ("Select City :"));
    		 k2.add(m_city);
    		 k3.add(b1);
    		 pane.add(k1);
    		 pane.add(k2);
    		 pane.add(k3);
    		
    		 frame.add(pane);
    		 frame.setVisible(true);
    		 
    	 }
    }
    class GetAreaCode implements ActionListener  {
    	@Override
    	public void actionPerformed(ActionEvent e) {	
    		String g1 = m_state.getSelectedItem().toString();
    		String g2 = m_city.getSelectedItem().toString();
    		String k1 = area;
    		area_code=area_type + Code_Search (g1,g2, k1);
    		//System.out.println(area_code);
    		
    		frame.dispose();
    	}
    }
      
    class act implements ActionListener  {
    	@Override
    	public void actionPerformed(ActionEvent e) {	
    		area_code ="Z"+m_zip.getText();
    		//if(QuandlZILLOWNodeModel.area_code.length()!= 5) {
    			//throw new java.lang.RuntimeException("zip code is not five digits");
    		//}
    		
    		frame.dispose();
    	}
    }
    
    class action implements ActionListener {
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		 String k1 =m_state.getSelectedItem().toString();
    		//System.out.println(k1);
    		area_code="S"+Search("areas_state",k1);
    		 
    	//	System.out.println(area_code);
    		frame.dispose(); 		
    		
    	}
    }
    
    
    String Code_Search (String g1,String g2, String g3 )  {
    	//String csvFile = "C://Users//sampr//Desktop//capstone//zillow//"+g3+".csv";
    	 String link= "https://s3.amazonaws.com/quandl-production-static/zillow/areas_"+g3+".txt";
        String line = "";
        String code;
        //String cvsSplitBy = ",";
        try {
            url= new URL(link);}
            catch (IOException e) {
            	e.printStackTrace();
            }
       
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
            br.readLine(); 
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] country = line.split("[,|\\|]");

               // System.out.println("Country [code= " + country[0] + " , name="  + "]");
                
                if (g1.equals (country[1]) && g2.equals(country[0])) {
                	code= country[2];
                	return code;
                }
                
                 
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        
    	return null;
    	
    }
    
    String [] CSVReader(String s, int i){
    	//String csvFile = "C://Users//sampr//Desktop//capstone//zillow//"+s+".csv";
    	 String link= "https://s3.amazonaws.com/quandl-production-static/zillow/"+s; 
               
        String S[];
        List<String> zoom = new ArrayList<>();
        try {
        url= new URL(link);}
        catch (IOException e) {
        	e.printStackTrace();
        }
        
        
             
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
            br.readLine();  
        	String line = null;
            while ((line = br.readLine()) != null) {

                // use comma as separator
              //  String[] country = line.split(cvsSplitBy +"|");
                  String[] country = line.split("[|]");
                //System.out.println("Country [code= " + country[1] + " , name="  + "]");
                zoom.add(country[i]);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
         S = zoom.toArray(new String[zoom.size()]);
        return S;
    }
    
    String Search(String s, String m){
    	//String csvFile = "C://Users//sampr//Desktop//capstone//zillow//"+s+".csv";
    	 String link= "https://s3.amazonaws.com/quandl-production-static/zillow/"+s +".txt"; 
        String line = "";
        String code;
       // String cvsSplitBy = ",";
        try {
            url= new URL(link);}
            catch (IOException e) {
            	e.printStackTrace();
            }
        
        
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
        	br.readLine(); 
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] country = line.split( "\\|");

               // System.out.println("Country [code= " + country[0] + " , name="  + "]");
                
                if (m.equals (country[0])) {
                	code= country[1];
                	return code;
                }
                
                 
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        
    	return null;
    }
    
       
    String[] StateSearch(String m, String s){
    	//String csvFile = "C://Users//sampr//Desktop//capstone//zillow//"+s+".csv";
    	 String link= "https://s3.amazonaws.com/quandl-production-static/zillow/"+s+".txt"; 
        String line = "";
        //String cvsSplitBy = ",";
        String S[];
        
        try {
            url= new URL(link);}
            catch (IOException e) {
            	e.printStackTrace();
            }
        List<String> zoom = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
        	br.readLine(); 
            while ((line = br.readLine()) != null) {
                 
                // use comma as separator
                String[] country = line.split("[,|\\|]");

               
               // System.out.println(m);
                
                if (m.equals (country[1])) {
                	 zoom.add(country[0]);
                	
                }
                
                 
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        S = zoom.toArray(new String[zoom.size()]);
        return S;
    }
    
    @Override
    protected void loadSettingsFrom(final NodeSettingsRO settings,
            final DataTableSpec[] specs) throws NotConfigurableException {
    	QuandlZILLOWConfig config = new QuandlZILLOWConfig();
		config.loadSettingsInDialog(settings);
    }
    
    @Override
    protected void saveSettingsTo(final NodeSettingsWO settings)
            throws InvalidSettingsException {
    	QuandlZILLOWConfig config = new QuandlZILLOWConfig();
    	
    	config.setm_api_key(m_api.getText());
    	config.setm_area_code(area_code);
    	config.setm_indicator(m_indicator.getSelectedItem().toString());
    	String selectedStartDate = startDate.getText();
		if (selectedStartDate != null)
			config.setM_start_date(selectedStartDate);
		String selectedEndDate = endDate.getText();
		if (selectedEndDate != null)
			config.setM_end_date(selectedEndDate);
		config.saveSettingsTo(settings);
		//options.addToHistory();
 	    	
    }
}

