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
 *   Jan 3, 2010 (wiswedel): created
 */
package org.FRED;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
//import java.awt.Rectangle;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.FRED.DatePicker;
import org.knime.core.data.DataTableSpec;
import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;
import org.knime.core.node.NotConfigurableException;
import org.knime.core.node.util.FilesHistoryPanel;
import org.knime.core.node.util.FilesHistoryPanel.LocationValidation;
import org.knime.core.node.workflow.FlowVariable;

/**
 * Dialog to CSV Reader node.
 * 
 * @noinstantiate This class is not intended to be instantiated by clients.
 * @noreference This class is not intended to be referenced by clients.
 * @author Bernd Wiswedel, KNIME AG, Zurich, Switzerland
 */
// this class is also used in the wide data extension. Once it's moved to the base bundle the scope if this class
// should be limited again.
public final class QuandlFREDNodeDialog extends NodeDialogPane {

	private final FilesHistoryPanel m_filePanel;

	// Text box to get api key
	private final JTextField m_apikey;
	private final JLabel api;

	// combo box to display currency

	private final JLabel m_curr_display;
	private final JComboBox<String> m_currency;
	private final String[] curr_list;

	// combo box to diplay data desc

	private final JLabel m_data_desc_display;
	private final JComboBox<String> m_data_desc;
	private final String[] data_list;

	// Date
	private final JLabel datelable = new JLabel("Choose Date (Optional): ");
	private JTextField endDate;
	private JTextField startDate;
//    private final JLabel startdatelabel = new JLabel("Start: ");
//    private final JLabel enddatelabel = new JLabel("End: ");
//    private final UtilDateModel startModel;
//	private final UtilDateModel endModel;
//	private final JDatePanelImpl startDatePanel;
//	private final JDatePanelImpl endDatePanel;
//    private JDatePickerImpl endDate;
//	private JDatePickerImpl startDate;
//	private UtilDateModel startModel = new UtilDateModel();
//	private UtilDateModel endModel = new UtilDateModel();
//	private JDatePanelImpl startDatePanel = new JDatePanelImpl(startModel, new Properties());
//	private JDatePanelImpl endDatePanel = new JDatePanelImpl(endModel, new Properties());
//    private final CharsetNamePanel m_encodingPanel;

	/** Create new dialog, init layout. */
	public QuandlFREDNodeDialog() {

		m_filePanel = new FilesHistoryPanel(createFlowVariableModel(CSVReaderConfig.CFG_URL, FlowVariable.Type.STRING),
				"csv_read", LocationValidation.FileInput, ".csv", ".txt");
		m_filePanel.setDialogType(JFileChooser.OPEN_DIALOG);
		m_filePanel.setShowConnectTimeoutField(true);

		// ----------

		api = new JLabel("Enter your APIKEY");
		m_apikey = new JTextField("");

		// creating currency combo box

		curr_list = new String[] { "AUD - Australian Dollar", "GBP - British Pound", "CAD - Canadian Dollar",
				"DKK - Danish Krone", "EUR - Euro", "JPY - Yen", "NZD - New Zealand Dollar", "SEK - Swedish Krona",
				"CHF - Swedish Frank", "USD - US Dollar" };
		m_currency = new JComboBox<>(curr_list);
		m_curr_display = new JLabel("Select Currency");

		// creating data desc combo box

		data_list = new String[] { "1 MONTH", "2 MONTH", "3 MONTH", "4 MONTH", "5 MONTH", "6 MONTH", "7 MONTH",
				"8 MONTH", "9 MONTH", "10 MONTH", "11 MONTH", "12 MONTH", "1 WEEK", "2 WEEK", "SPOT NEXT",
				"OVERNIGHT" };
		m_data_desc = new JComboBox<>(data_list);
		m_data_desc_display = new JLabel("Select Data Frequency ", SwingConstants.LEFT);
//        datelable = new JLabel("Choose Date Range (Optional)");
//        startdatelabel = new JLabel("Start: ");
//        enddatelabel = new JLabel("end: ");
//        System.out.println("Dead here");
//        startModel = new UtilDateModel();
//        endModel = new UtilDateModel();
//        startDatePanel = new JDatePanelImpl(startModel, new Properties());
//        endDatePanel = new JDatePanelImpl(endModel, new Properties());

//        endDate = new JDatePickerImpl(endDatePanel, new DateLabelFormatter());
//        startDate = new JDatePickerImpl(startDatePanel, new DateLabelFormatter());

		addTab("Settings", initLayout());

	}

	/**
	 * @param limitRowsTab
	 * @return
	 */
	private JPanel getLimitRowsPanel() {
		JPanel optionsPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridy = 0;
		gbc.gridx = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.weightx = 0;
//        optionsPanel.add(getInFlowLayout(m_skipFirstLinesChecker), gbc);
		gbc.gridx += 1;
//        optionsPanel.add(getInFlowLayout(m_skipFirstLinesSpinner), gbc);
		gbc.gridy += 1;
		gbc.gridx = 0;
//        optionsPanel.add(getInFlowLayout(m_limitRowsChecker), gbc);
		gbc.gridx += 1;
//        optionsPanel.add(getInFlowLayout(m_limitRowsSpinner), gbc);
		gbc.gridy += 1;
		gbc.gridx = 0;
//        optionsPanel.add(getInFlowLayout(m_limitAnalysisChecker), gbc);
		gbc.gridx += 1;
//        optionsPanel.add(getInFlowLayout(m_limitAnalysisSpinner), gbc);

		// empty panel to eat up extra space
		gbc.gridy += 1;
		gbc.gridx = 0;
		gbc.weighty = 1;
		optionsPanel.add(new JPanel(), gbc);

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(Box.createVerticalStrut(5));
		// panel.add(optionsPanel);

		return panel;
	}

	private JPanel initLayout() {
		final JPanel filePanel = new JPanel();
		final JPanel DSPanel = new JPanel();
		final JPanel DEPanel = new JPanel();
		final JPanel CPanel = new JPanel();
		final JPanel DPanel = new JPanel();
//        filePanel.setLayout(new BoxLayout(filePanel, BoxLayout.PAGE_AXIS));
//        filePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory
//                .createEtchedBorder(), "Input location:"));
		filePanel.setSize(new Dimension(250, 50));

		// adding api key, currency and data desc components to panel

		api.setMaximumSize(new Dimension(250, 50));

		m_apikey.setMaximumSize(new Dimension(250, 25));
//        api.setMaximumSize(new Dimension(50,0));
		filePanel.setLayout(new BoxLayout(filePanel, BoxLayout.X_AXIS));
		filePanel.add(api);
		filePanel.add(m_apikey);
		// filePanel.setLayout(new BoxLayout(filePanel,BoxLayout.X_AXIS));

		m_curr_display.setMaximumSize(new Dimension(250, 50));

		m_currency.setMaximumSize(new Dimension(250, 50));
		CPanel.setLayout(new BoxLayout(CPanel, BoxLayout.X_AXIS));
		CPanel.add(m_curr_display);
		CPanel.add(m_currency);

		m_data_desc_display.setMaximumSize(new Dimension(250, 50));

		m_data_desc.setMaximumSize(new Dimension(250, 50));
		DPanel.setLayout(new BoxLayout(DPanel, BoxLayout.X_AXIS));
		DPanel.add(m_data_desc_display);
		DPanel.add(m_data_desc);

		// Panel.add(datelable);
		JLabel lblStartDate = new JLabel("Start Date (optional):");
//        		lblStartDate.setBounds(17, 200, 45, 16);
		DSPanel.setLayout(new BoxLayout(DSPanel, BoxLayout.X_AXIS));
		lblStartDate.setMaximumSize(new Dimension(250, 50));
		DSPanel.add(lblStartDate);

		datelable.setBounds(17, 200, 45, 16);
//                filePanel.add(endDate);
//                filePanel.add(startDate);

		//
		startDate = new JTextField();
		startDate.setEditable(false);
		startDate.setMaximumSize(new Dimension(250, 25));
		DSPanel.add(startDate);

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
//		startDateBtn.setBounds(163, 175, 29, 16);
		DSPanel.add(startDateBtn);
		
		JLabel lblEndDate = new JLabel("End Date (optional):");
//    		lblEndDate.setBounds(17, 220, 36, 16);
		lblEndDate.setMaximumSize(new Dimension(250, 50));
		DEPanel.setLayout(new BoxLayout(DEPanel, BoxLayout.X_AXIS));
		DEPanel.add(lblEndDate);

		
		endDate = new JTextField();
		endDate.setMaximumSize(new Dimension(250, 25));
		endDate.setEditable(false);
		DEPanel.add(endDate);

		JButton endDateBtn = new JButton("-");
		endDateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				endDate.setText(new DatePicker().setPickedDate());

				if (!"".equals(startDate.getText().trim()) && !"".equals(endDate.getText().trim())) {

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
//		endDateBtn.setBounds(355, 175, 29, 16);
		DEPanel.add(endDateBtn);
		// --------------

		// filePanel.add(m_filePanel);

		// filePanel.add(Box.createHorizontalGlue());

		JPanel optionsPanel = new JPanel(new GridBagLayout());
		optionsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Reader options:"));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
//        optionsPanel.add(getInFlowLayout(m_colDelimiterField, new JLabel("Column Delimiter ")), gbc);
		gbc.gridx += 1;
//        optionsPanel.add(getInFlowLayout(m_rowDelimiterField, new JLabel("Row Delimiter ")), gbc);
		gbc.gridx += 1;
		gbc.weightx = 1;
		optionsPanel.add(new JPanel(), gbc);

		gbc.gridx = 0;
		gbc.gridy += 1;
		gbc.weightx = 0;
//        optionsPanel.add(getInFlowLayout(m_quoteStringField, new JLabel("Quote Char ")), gbc);
		gbc.gridx += 1;
//        optionsPanel.add(getInFlowLayout(m_commentStartField, new JLabel("Comment Char ")), gbc);

		gbc.gridx = 0;
		gbc.gridy += 1;
//        optionsPanel.add(getInFlowLayout(m_hasColHeaderChecker), gbc);
		gbc.gridx += 1;
//        optionsPanel.add(getInFlowLayout(m_hasRowHeaderChecker), gbc);

		gbc.gridx = 0;
		gbc.gridy += 1;
//        optionsPanel.add(getInFlowLayout(m_supportShortLinesChecker), gbc);

		// empty panel to eat up extra space
		gbc.gridy += 1;
		gbc.gridx = 0;
		gbc.weighty = 1;
		optionsPanel.add(new JPanel(), gbc);

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(filePanel);
		panel.add(CPanel);
		panel.add(DPanel);
		panel.add(DSPanel);
		panel.add(DEPanel);

		return panel;
	}

	private static JPanel getInFlowLayout(final JComponent... comps) {
		JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		for (JComponent c : comps) {
			p.add(c);
		}
		return p;
	}

	/** {@inheritDoc} */
	@Override
	protected void loadSettingsFrom(final NodeSettingsRO settings, final DataTableSpec[] specs)
			throws NotConfigurableException {
		CSVReaderConfig config = new CSVReaderConfig();
		config.loadSettingsInDialog(settings);
		m_filePanel.updateHistory();
		m_filePanel.setSelectedFile(config.getLocation());
		m_filePanel.setConnectTimeout(config.getConnectTimeout());

		// load settings from NodeSettingsRO object

		m_apikey.setText(config.getAPIKey());

	}

	/** {@inheritDoc} */
	@Override
	protected void saveSettingsTo(final NodeSettingsWO settings) throws InvalidSettingsException {
		CSVReaderConfig config = new CSVReaderConfig();

		// Save to config

		config.setAPIKey(m_apikey.getText());

		config.setCurr(m_currency.getSelectedItem().toString());
		config.setDesc(m_data_desc.getSelectedItem().toString());
		config.updateLocation();
		String selectedStartDate = startDate.getText();
		if (selectedStartDate != null)
			config.setM_start_date(selectedStartDate);
		String selectedEndDate = endDate.getText();
		if (selectedEndDate != null)
			config.setM_end_date(selectedEndDate);
		
		

//        config.setConnectTimeout(m_filePanel.getConnectTimeout().orElse(null));

		config.saveSettingsTo(settings);
		m_filePanel.addToHistory();

	}

}

