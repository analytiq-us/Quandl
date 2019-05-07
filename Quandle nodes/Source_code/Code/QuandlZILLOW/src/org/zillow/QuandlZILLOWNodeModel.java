package org.zillow;

import java.net.URL;

import java.io.*;


import org.knime.core.data.DataCell;
import org.knime.core.data.DataColumnSpec;
import org.knime.core.data.DataColumnSpecCreator;
import org.knime.core.data.DataTableSpec;
import org.knime.core.data.def.DefaultRow;
import org.knime.core.data.def.DoubleCell;
import org.knime.core.data.def.StringCell;
import org.knime.core.node.BufferedDataContainer;
import org.knime.core.node.BufferedDataTable;
import org.knime.core.node.CanceledExecutionException;
import org.knime.core.node.ExecutionContext;
import org.knime.core.node.ExecutionMonitor;
import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeModel;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;
import org.knime.core.data.RowKey;


/**
 * This is the model implementation of Zillow.
 * 
 *
 * @author Sampreeth
 */
public class QuandlZILLOWNodeModel extends NodeModel {
	   public static QuandlZILLOWConfig m_config = new QuandlZILLOWConfig();
       //static String area_code;
       String k;
       URL url;
	    /** No input, one output. */
	    protected QuandlZILLOWNodeModel() {
	        super(0, 1);
	    }
	    
	    
	    @Override
		protected BufferedDataTable[] execute(final BufferedDataTable[] inData, final ExecutionContext exec)
				throws Exception {
	    	 k = Search("indicators.csv",QuandlZILLOWNodeDialog.m_indicator.getSelectedItem().toString()); 
	    	BufferedDataContainer container = null;
	    	container = exec.createDataContainer(createDataSpec());
//	    	System.out.print("here"); QuandlZILLOWNodeDialog.area_code
	    	String link = "https://www.quandl.com/api/v3/datasets/ZILLOW/"+m_config.getm_area_code() +
	    			"_" +k+".csv?"+ ("".equals(m_config.getM_start_date()) ? "" 
	    													: "start_date=" + m_config.getM_start_date() + "&")
	    				+ ("".equals(m_config.getM_end_date()) ? "" : "end_date=" + m_config.getM_end_date() + "&")
	    				+"api_key="+ m_config.getm_api_key();
	    	
	    	URL url = new URL(link);
	    	//System.out.print("here");
	    	 String line = "";
	         String cvsSplitBy = ",";
	         Long count = 0L;

 
	         try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
	             while ((line = br.readLine()) != null) {

	                 // use comma as separator
	                 String[] country = line.split(cvsSplitBy);

	                // System.out.println("Country [code= " + country[1] + " , name="  + "]");
	                 DataCell[] cells = new DataCell[2];
	                 
	                	 
	                 
	                  
	                if (country[1].equals("Value"))	
	                {}
	                else
	                {  cells[0]= new StringCell (country[0]  );
	                
	                 cells [1]= new DoubleCell (Double.parseDouble(country[1]));
	                 

                    container.addRowToTable(new DefaultRow(RowKey.createRowKey(count),cells));
	                 	                 count++;}
	             }

	         } 
	         catch (IOException e) {
	             e.printStackTrace();
	             throw new RuntimeException(e);
	              
	         
	    }
	         container.close();
	         BufferedDataTable result = container.getTable();
	         container = null;
	         return new BufferedDataTable[] { result };

	    }
	    
	    @Override
		protected DataTableSpec[] configure(final DataTableSpec[] inSpecs) throws InvalidSettingsException {
             
			if (m_config == null) {
				throw new InvalidSettingsException(" No settings available");
			}

			return new DataTableSpec[] { createDataSpec() };
		}
	    
	    @Override
		protected void reset() {
			// k= null;
			// area_code = null;
			System.out.println("Enter into reset()");
		}
	    @Override
		protected void saveSettingsTo(final NodeSettingsWO settings) {

			//System.out.println("QuandlEURONEXTNodeModel: Enter into saveSettingsTo()");
			if (m_config != null) {
				m_config.saveSettingsTo(settings);
				
			}
		}
	    
	    @Override
		protected void loadValidatedSettingsFrom(final NodeSettingsRO settings) throws InvalidSettingsException {
	    	QuandlZILLOWConfig config = new QuandlZILLOWConfig();
			config.loadSettingsInModel(settings);
			
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected void validateSettings(final NodeSettingsRO settings) throws InvalidSettingsException {
			
			QuandlZILLOWConfig config = new QuandlZILLOWConfig();
			config.loadSettingsInModel(settings);
			m_config = config;
			
		}


	    
	    @Override
		protected void loadInternals(final File internDir, final ExecutionMonitor exec)
				throws IOException, CanceledExecutionException {

			
		}
	    @Override
		protected void saveInternals(final File internDir, final ExecutionMonitor exec)
				throws IOException, CanceledExecutionException {
	    // System.out.print ("err");
	    }
	    
	   
	   
		private DataTableSpec  createDataSpec(){
	    	
	    	DataColumnSpec[] allColSpecs = new DataColumnSpec[2];
	    	allColSpecs[0] = new DataColumnSpecCreator("Date", StringCell.TYPE).createSpec();
	    	allColSpecs[1] = new DataColumnSpecCreator("index", DoubleCell.TYPE).createSpec();
	    	
	    	return new DataTableSpec(allColSpecs);
	    			
	    }
	    
	    String Search(String s, String m){
	    	//String csvFile = "C://Users//sampr//Desktop//capstone//zillow//"+s+".csv";
	    	String link= "https://s3.amazonaws.com/quandl-production-static/zillow/"+s ; 
	        String line = "";
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
	                String[] country = line.split("[|]");

	             //  System.out.println(m);
	                
	                if (m.equals (country[0])) {
	                	String g= country[1];
	                	return g;
	                }
	                
	                 
	            }

	        } catch (IOException e) {
	            //e.printStackTrace();
	        }
	        
	    	return null;
	    }
	    
	   

}