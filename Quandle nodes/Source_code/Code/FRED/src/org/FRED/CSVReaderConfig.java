/*
 * ------------------------------------------------------------------------
 *  Copyright by KNIME AG, Zurich, Switzerland
 *  Website: http://www.knime.com; Email: contact@knime.com
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License, Version 3, as
 *  published by the Free Software Foundation.
 *
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, see <http://www.gnu.org/licenses>.
 *
 *  Additional permission under GNU GPL version 3 section 7:
 *
 *  KNIME interoperates with ECLIPSE solely via ECLIPSE's plug-in APIs.
 *  Hence, KNIME and ECLIPSE are both independent programs and are not
 *  derived from each other. Should, however, the interpretation of the
 *  GNU GPL Version 3 ("License") under any applicable laws result in
 *  KNIME and ECLIPSE being a combined program, KNIME AG herewith grants
 *  you the additional permission to use and propagate KNIME together with
 *  ECLIPSE with only the license terms in place for ECLIPSE applying to
 *  ECLIPSE and the GNU GPL Version 3 applying for KNIME, provided the
 *  license terms of ECLIPSE themselves allow for the respective use and
 *  propagation of ECLIPSE together with KNIME.
 *
 *  Additional permission relating to nodes for KNIME that extend the Node
 *  Extension (and in particular that are based on subclasses of NodeModel,
 *  NodeDialog, and NodeView) and that only interoperate with KNIME through
 *  standard APIs ("Nodes"):
 *  Nodes are deemed to be separate and independent programs and to not be
 *  covered works.  Notwithstanding anything to the contrary in the
 *  License, the License does not apply to Nodes, you are not required to
 *  license Nodes under the License, and you are granted a license to
 *  prepare and propagate Nodes, in each case even if such Nodes are
 *  propagated with or for interoperation with KNIME.  The owner of a Node
 *  may freely choose the license terms applicable to such Node, including
 *  when such Node is propagated with or for interoperation with KNIME.
 * ------------------------------------------------------------------------
 *
 * History
 *   Dec 4, 2009 (wiswedel): created
 */
package org.FRED;


import java.time.Duration;

import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;

/**
 * Config for CSV reader.
 * @noinstantiate This class is not intended to be instantiated by clients.
 * @noreference This class is not intended to be referenced by clients.
 * @author Bernd Wiswedel, KNIME AG, Zurich, Switzerland
 */
// public scope as it's used in the widedata bundle. Scope to be reduced once both bundles are merged.





public final class CSVReaderConfig {
	
	
	/** Config key for the URL property. */
    static final String CFG_URL = "url";


    private String m_location;
   
    
    private String m_apikey;
    private Duration m_connectTimeout;
    private int m_limitAnalysisCount;
    private String m_curr;
    private String m_desc;
    private String FRED;
    private String m_start_date;
    private String m_end_date;
   
   
    /**
     * Creates a new CSVReaderConfig with default values for all settings
     * except the url.
     */
    public CSVReaderConfig() {
    		m_apikey = "";  //mdJ3-9txxdtZdZRH4HXJ
        m_limitAnalysisCount = -1;
        m_curr= "";
        m_desc="";
        m_start_date = "";
        m_end_date = "";

        FRED = "https://www.quandl.com/api/v3/datasets/FRED/";
   
       
    }

    /** Load settings, used in dialog (no errors).
     * @param settings To load from.
     */
    public void loadSettingsInDialog(final NodeSettingsRO settings) {
       
        m_location = "";
    		m_apikey = settings.getString("apikey",m_apikey);
        m_curr = settings.getString("currency",m_curr);
        m_desc = settings.getString("desc",m_desc);
        m_start_date = settings.getString("start_date", m_start_date);
		m_end_date = settings.getString("end_date", m_end_date);
        try {
            m_connectTimeout = Duration.ofSeconds(settings.getInt("connectTimeoutInSeconds"));
        } catch (InvalidSettingsException ex) {
            m_connectTimeout = null; // use default value
        }
        m_limitAnalysisCount = settings.getInt("limitAnalysisCount", m_limitAnalysisCount);
    }

    /** Load in model, fail if settings are invalid.
     * @param settings To load from.
     * @throws InvalidSettingsException If invalid.
     */
    public void loadSettingsInModel(final NodeSettingsRO settings)
        throws InvalidSettingsException {
       
        m_location = "";
        m_apikey = settings.getString("apikey");
        m_curr = settings.getString("currency");
        m_desc = settings.getString("desc");
        m_start_date = settings.getString("start_date", m_start_date);
		m_end_date = settings.getString("end_date", m_end_date);
     
        try {
            m_connectTimeout = Duration.ofSeconds(50);
        } catch (Exception ex) {
            m_connectTimeout = null; // use default value
        }
        m_limitAnalysisCount = settings.getInt("limitAnalysisCount", m_limitAnalysisCount);
        
    }

    /** Save configuration to argument.
     * @param settings To save to.
     */
    public void saveSettingsTo(final NodeSettingsWO settings) {
    
    		settings.addString("apikey",m_apikey);
    		System.out.println("Saving--"+m_apikey);
        settings.addString("currency", m_curr);
        settings.addString("desc", m_desc);
        settings.addString("start_date", m_start_date);
		settings.addString("end_date", m_end_date);    
    	
    	if (m_location != null) {
            settings.addString(CFG_URL, m_location.toString());
        }
        if(m_location == null) {
        	
        	updateLocation();
        	System.out.println("SAVE-----"+m_location);
        	settings.addString(CFG_URL,m_location.toString());
        }
        

        if (m_connectTimeout != null) {
            settings.addInt("connectTimeoutInSeconds", (int) (m_connectTimeout.toMillis() / 1000));
        }
        settings.addInt("limitAnalysisCount", m_limitAnalysisCount);
    }

    /** @return the location */
    

    public String getDesc()
    {
    	return m_desc;    	
    }
    
    public void setDesc(final String desc) {
    	
    	switch(desc)
    	{
    	case "1 MONTH":
    		m_desc = "1MT";
    		break;
    	case "2 MONTH":
    		m_desc = "2MT";
    		break;
    	case "3 MONTH":
    		m_desc = "3MT";
    		break;
    	case "4 MONTH":
    		m_desc = "4MT";
    		break;
    	case "5 MONTH":
    		m_desc = "5MT";
    		break;
    	case "6 MONTH":
    		m_desc = "6MT";
    		break;
    	case "7 MONTH":
    		m_desc = "7MT";
    		break;
    	case "8 MONTH":
    		m_desc = "8MT";
    		break;
    	case "9 MONTH":
    		m_desc = "9MT";
    		break;
    	case "10 MONTH":
    		m_desc = "10M";
    		break;
    	case "11 MONTH":
    		m_desc = "11M";
    		break;
    	case "12 MONTH":
    		m_desc = "12M";
    		break;
    	case "1 WEEK":
    		m_desc = "1WK";
    		break;
    	case "2 WEEK":
    		m_desc = "2WK";
    		break;
    	case "SPOT NEXT":
    		m_desc = "ONT";
    		break;
    	case "OVERNIGHT":
    		m_desc = "ONT";
    		break;
    	}
    	
    }
    
    public String getCurr() {
    	
    	return m_curr;
    }
   
    public void setCurr(final String currency)
    {
    	String curr = new String(currency.substring(0, 3));
    	m_curr = curr;
    }
    
    public String getLocation() {
    		updateLocation();
        return m_location;
    }
    public void setLocation(String loaction) {
    		this.m_location = loaction;
    }

    /** @param location the location to set */
    public void updateLocation() {
    	   String datasetcode= new String(m_curr+m_desc);
    	   m_location = FRED +datasetcode+"D156N.csv?api_key=" + getAPIKey()+("".equals(getM_start_date()) ? ""
					: "&start_date=" + getM_start_date())
			+ ("".equals(getM_end_date()) ? "" : "&end_date=" + getM_end_date());
//    	   m_location = FRED +datasetcode+"D156N.csv?api_key=" + getAPIKey();
           System.out.println("URL_____"+m_location);
    }

    public String getM_start_date() {
		return m_start_date;
	}

	public void setM_start_date(String m_start_date) {
		this.m_start_date = m_start_date;
//		System.out.println("m_start_date"+m_start_date);
	}

	public String getM_end_date() {
		return m_end_date;
	}
	

	public void setM_end_date(String m_end_date) {
		this.m_end_date = m_end_date;
//		System.out.println("m_end_date:"+m_end_date);
	}
	
    public String getAPIKey() {
        return m_apikey;
    }
    
    void setAPIKey(final String apikey) {
        m_apikey = apikey;
    }


    /** @return the timeout for remote files or null if the default value should be used */
    public Duration getConnectTimeout(){
        return m_connectTimeout;
    }

    /** @param value the connect timeout to set or <code>null</code> to use default value. */
    void setConnectTimeout(final Duration value){
        m_connectTimeout = value;
    }

    /** @return the limitAnalysisCount (smaller 0 if unlimited). */
    public int getLimitAnalysisCount() {
        return m_limitAnalysisCount;
    }


}
