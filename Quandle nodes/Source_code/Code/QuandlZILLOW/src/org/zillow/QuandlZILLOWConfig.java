package org.zillow;

import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;

public class QuandlZILLOWConfig {
	private String m_api_key; 
	private String m_start_date;
	private String m_area_code;
	private String m_indicator;

	private String m_end_date;
	
	public QuandlZILLOWConfig() {
		m_api_key = "";// sJahsYaf2o4i_PaFEBWP
		m_start_date = "";
		m_end_date = "";
		m_area_code="";
	}
	
	public void loadSettingsInDialog(final NodeSettingsRO settings) {
		m_api_key = settings.getString("api_key", m_api_key);
		m_start_date = settings.getString("start_date", m_start_date);
		m_end_date = settings.getString("end_date", m_end_date);
		m_area_code= settings.getString("area_code",m_area_code);
		m_indicator= settings.getString("m_indicator",m_indicator);
	}
		public void loadSettingsInModel(final NodeSettingsRO settings) throws InvalidSettingsException {
			
			m_api_key = settings.getString("api_key", m_api_key);
			m_start_date = settings.getString("start_date", m_start_date);
			m_end_date = settings.getString("end_date", m_end_date);
			m_area_code= settings.getString("area_code",m_area_code);
			m_indicator= settings.getString("m_indicator",m_indicator);
	}
		public void saveSettingsTo(final NodeSettingsWO settings) {
			settings.addString("api_key", m_api_key);
			settings.addString("start_date", m_start_date);
			settings.addString("end_date", m_end_date);
			settings.addString("area_code", m_area_code);
			settings.addString("m_indicator", m_indicator);
			

		}
		
		public String getm_indicator() {
			return m_indicator;
		}
		
		public void setm_indicator(String m_indicator) {
			this.m_indicator= m_indicator;
		}
		
		
		public String getm_area_code() {
			return m_area_code;
		}
		public void setm_area_code(String m_area_code) {
			this.m_area_code= m_area_code;
		}
		
		public String getm_api_key() {
			return m_api_key;
		}
		public void setm_api_key(String m_api_key) {
			this.m_api_key = m_api_key;
		}
		public String getM_start_date() {
			return m_start_date;
		}

		public void setM_start_date(String m_start_date) {
			this.m_start_date = m_start_date;
		}

		public String getM_end_date() {
			return m_end_date;
		}

		public void setM_end_date(String m_end_date) {
			this.m_end_date = m_end_date;
		}
}