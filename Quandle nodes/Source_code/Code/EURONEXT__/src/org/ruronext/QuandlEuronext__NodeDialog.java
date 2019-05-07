package org.ruronext;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

import javax.json.JsonObject; //
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.knime.core.data.DataTableSpec;
import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;
import org.knime.core.node.NotConfigurableException;
import org.knime.core.node.defaultnodesettings.DefaultNodeSettingsPane;
import org.knime.core.node.util.FilesHistoryPanel;
import org.knime.core.node.util.FilesHistoryPanel.LocationValidation;
import org.knime.core.node.workflow.FlowVariable;
import org.ruronext.AutoCompleteComboBox;
import org.ruronext.DatePicker;
import org.ruronext.QuandlEURONEXTTickerList;
import org.ruronext.QuandlEuronext__Config;
import org.ruronext.QuandlEuronext__NodeModel;

/**
 * <code>NodeDialog</code> for the "QuandlEURONEXT" Node.
 * 
 *
 * This node dialog derives from {@link DefaultNodeSettingsPane} which allows
 * creation of a simple dialog with standard components. If you need a more
 * complex dialog please derive directly from
 * {@link org.knime.core.node.NodeDialogPane}.
 * 
 * @author Heng Chi
 */
public class QuandlEuronext__NodeDialog extends NodeDialogPane {

//	private JPanel contentPane;
	private FilesHistoryPanel contentPane;
	private static JTable table;
	private JTextField apiKeyTF;
	private JTextField endDate;
	private JTextField startDate;
	private JComboBox<String> tickersCCB;
//	private ArrayList<String> currentTickerList;
	private String currentTickers;
	private DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();

	protected QuandlEuronext__NodeDialog() {
		super();

		addTab("EURONEXT", initLayout());

	}

	private JPanel initLayout() {

//    	String[] list = new String[] { "arthur", "brian", "chet", "danny", "dave",  "don", "edmond", "eddy", "glen", "gregory", "john",  "ken", "malcolm", "pete", "stephanie", "yvonne" };

//		contentPane = new JPanel();
		contentPane = new FilesHistoryPanel(createFlowVariableModel("url", FlowVariable.Type.STRING),
                "csv_read", LocationValidation.FileInput, ".csv", ".txt");
		contentPane.setBorder(new EmptyBorder(50, 50, 50, 50));
		contentPane.setLayout(null);
		contentPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
		contentPane.setSize(1000, 1000);

		table = new JTable();
		initJTable();

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(17, 93, 250, 53);
		scrollPane.setViewportView(table);
		contentPane.add(scrollPane);

		JLabel lblStep_1 = new JLabel("Step 1 : Enter an API Key");
		lblStep_1.setBounds(17, 11, 158, 16);
		contentPane.add(lblStep_1);

		apiKeyTF = new JTextField();
		apiKeyTF.setBounds(178, 6, 191, 26);
		contentPane.add(apiKeyTF);
		apiKeyTF.setColumns(10);

		JLabel lblStep_2 = new JLabel("Step 2 : Choose Market");
		lblStep_2.setBounds(17, 38, 158, 16);
		contentPane.add(lblStep_2);

		JComboBox comboBox = new JComboBox(new String[] { "USD", "CHF", "NOK", "XXX", "GBP", "SEK", "DKK", "GBX", "EUR" });
		comboBox.setBounds(178, 35, 191, 26);
		comboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				//Change Currency will be 9 of them
				int selectedItem = comboBox.getSelectedIndex();
				if (selectedItem == 0) {

					Object[] obj = new QuandlEURONEXTTickerList().getTickerUSD();

					model.removeAllElements();

					for (Object s : obj) {
						model.addElement(String.valueOf(s));
					}
					tickersCCB.setModel(model);

					tickersCCB.revalidate();
					contentPane.revalidate();
					tickersCCB.repaint();
					contentPane.repaint();

				} else if (selectedItem == 1) {

					Object[] obj = new QuandlEURONEXTTickerList().getTickerCHF();

					model.removeAllElements();

					for (Object s : obj) {
						model.addElement(String.valueOf(s));
					}
					tickersCCB.setModel(model);
					
					tickersCCB.revalidate();
					contentPane.revalidate();
					tickersCCB.repaint();
					contentPane.repaint();

				} else if (selectedItem == 2) {

					Object[] obj = new QuandlEURONEXTTickerList().getTickerNOK();

					model.removeAllElements();

					for (Object s : obj) {
						model.addElement(String.valueOf(s));
					}
					tickersCCB.setModel(model);

					tickersCCB.revalidate();
					contentPane.revalidate();
					tickersCCB.repaint();
					contentPane.repaint();

				} else if (selectedItem == 3) {

					Object[] obj = new QuandlEURONEXTTickerList().getTickerXXX();

					model.removeAllElements();

					for (Object s : obj) {
						model.addElement(String.valueOf(s));
					}
					tickersCCB.setModel(model);

					tickersCCB.revalidate();
					contentPane.revalidate();
					tickersCCB.repaint();
					contentPane.repaint();
				}else if (selectedItem == 4) {

					Object[] obj = new QuandlEURONEXTTickerList().getTickerGBP();

					model.removeAllElements();

					for (Object s : obj) {
						model.addElement(String.valueOf(s));
					}
					tickersCCB.setModel(model);

					tickersCCB.revalidate();
					contentPane.revalidate();
					tickersCCB.repaint();
					contentPane.repaint();
				}else if (selectedItem == 5) {

					Object[] obj = new QuandlEURONEXTTickerList().getTickerSEK();

					model.removeAllElements();

					for (Object s : obj) {
						model.addElement(String.valueOf(s));
					}
					tickersCCB.setModel(model);

					tickersCCB.revalidate();
					contentPane.revalidate();
					tickersCCB.repaint();
					contentPane.repaint();
				}else if (selectedItem == 6) {

					Object[] obj = new QuandlEURONEXTTickerList().getTickerDKK();

					model.removeAllElements();

					for (Object s : obj) {
						model.addElement(String.valueOf(s));
					}
					tickersCCB.setModel(model);

					tickersCCB.revalidate();
					contentPane.revalidate();
					tickersCCB.repaint();
					contentPane.repaint();
				}else if (selectedItem == 7) {

					Object[] obj = new QuandlEURONEXTTickerList().getTickerGBX();

					model.removeAllElements();

					for (Object s : obj) {
						model.addElement(String.valueOf(s));
					}
					tickersCCB.setModel(model);

					tickersCCB.revalidate();
					contentPane.revalidate();
					tickersCCB.repaint();
					contentPane.repaint();
				}else if (selectedItem == 8) {

					Object[] obj = new QuandlEURONEXTTickerList().getTickerEUR();

					model.removeAllElements();

					for (Object s : obj) {
						model.addElement(String.valueOf(s));
					}
					tickersCCB.setModel(model);

					tickersCCB.revalidate();
					contentPane.revalidate();
					tickersCCB.repaint();
					contentPane.repaint();
				}

			}
		});
		contentPane.add(comboBox);

		JLabel lblStep_3 = new JLabel("Step 3 : Choose Tickers");
		lblStep_3.setBounds(17, 66, 158, 16);
		contentPane.add(lblStep_3);

		tickersCCB = new AutoCompleteComboBox(new QuandlEURONEXTTickerList().getTickerUSD());
		tickersCCB.setBounds(182, 66, 184, 18);
		contentPane.add(tickersCCB);

		JLabel lblStep_4 = new JLabel("Step 4 : Choose Date Range (Optional)");
		lblStep_4.setBounds(17, 152, 248, 16);
		contentPane.add(lblStep_4);

		JLabel lblStartDate = new JLabel("Start :");
		lblStartDate.setBounds(17, 176, 45, 16);
		contentPane.add(lblStartDate);

		startDate = new JTextField();
		startDate.setBounds(56, 170, 100, 26);
		startDate.setEditable(false); //
//		UtilDateModel startModel = new UtilDateModel();
//		UtilDateModel endModel = new UtilDateModel();
//		JDatePanelImpl startDatePanel = new JDatePanelImpl(startModel, new Properties());
//		JDatePanelImpl endDatePanel = new JDatePanelImpl(endModel, new Properties());

//		JLabel lblStartDate = new JLabel("Start :");
//		lblStartDate.setBounds(17, 200, 45, 16);
//		contentPane.add(lblStartDate);
//		startDate.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//
//				Date end = (Date) endDate.getModel().getValue();
//				Date start = (Date) startDate.getModel().getValue();
//
//				if (end != null && start != null && end.getTime() < start.getTime()) {
//					JOptionPane.showMessageDialog(null, "End Date earlier than Start Date", "Wrong Selection",
//							JOptionPane.WARNING_MESSAGE);
//				}
//			}
//		});
		contentPane.add(startDate);

		JButton startDateBtn = new JButton("-");
		startDateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				startDate.setText(new DatePicker().setPickedDate());

				if (!"".equals(endDate.getText().trim()) && !"".equals(startDate.getText().trim())) {

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
		startDateBtn.setBounds(156, 175, 29, 16);
		contentPane.add(startDateBtn);

		JLabel lblEndDate = new JLabel("End :");
		lblEndDate.setBounds(200, 175, 36, 16);
		contentPane.add(lblEndDate);

		endDate = new JTextField();
		endDate.setBounds(232, 170, 100, 26);
		endDate.setEditable(false);//
		contentPane.add(lblEndDate);

		contentPane.add(endDate);

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
		endDateBtn.setBounds(332, 175, 29, 16);
		contentPane.add(endDateBtn);

		JButton addBtn = new JButton("Add");
		addBtn.setBounds(279, 91, 90, 27);
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String ticker = (String.valueOf(tickersCCB.getSelectedItem())).toUpperCase();

				System.out.println("select " + ticker);

				// if ticker already in chose list
				if (QuandlEuronext__NodeModel.m_tickers.indexOf(ticker.toUpperCase()) == -1) {

					if (checkData(ticker)) {
						QuandlEuronext__NodeModel.m_tickers.add(ticker);
						updateTickersString(); 
					}
					else
						JOptionPane.showMessageDialog(null, "Data is premium / Unavailable", "Invalid",
								JOptionPane.ERROR_MESSAGE);

				} else {
					JOptionPane.showMessageDialog(null, "Ticker Already in the list", "", JOptionPane.WARNING_MESSAGE);
				}
				System.out.println("tickers size = " + QuandlEuronext__NodeModel.m_tickers.size());

				initJTable();
			}

		});
		contentPane.add(addBtn);

		JButton removeBtn = new JButton("Remove");
		removeBtn.setBounds(279, 121, 90, 27);
		removeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				QuandlEuronext__NodeModel.m_tickers.remove(table.getSelectedRow());
				System.out.println("tickers size = " + QuandlEuronext__NodeModel.m_tickers.size());
				System.out.println("select " + tickersCCB.getSelectedItem().toString());

				initJTable();

			}

		});
		contentPane.add(removeBtn);

		return contentPane;
	}

	/** {@inheritDoc} */
	@Override
	protected void loadSettingsFrom(final NodeSettingsRO settings, final DataTableSpec[] specs)
			throws NotConfigurableException {

		System.out.println("QuandlEURONEXTNodeDialog: loadSettingsFrom()");

//		apiKeyTF.setText(QuandlEURONEXTNodeModel.m_config.getm_api_key());
		
		QuandlEuronext__Config config = new QuandlEuronext__Config();
		config.loadSettingsInDialog(settings);
		contentPane.updateHistory();

		System.out.println("m_tickers.size() = " + QuandlEuronext__NodeModel.m_tickers.size());
		System.out.println("m_tickers.getm_api_key() = " + QuandlEuronext__NodeModel.m_config.getm_api_key()
				+ "m_tickers.getM_start_date() = " + QuandlEuronext__NodeModel.m_config.getM_start_date()
				+ "m_tickers.getM_end_date() = " + QuandlEuronext__NodeModel.m_config.getM_end_date());
	}

	/** {@inheritDoc} */
	@Override
	protected void saveSettingsTo(final NodeSettingsWO settings) throws InvalidSettingsException {

		System.out.println("QuandlEURONEXTNodeDialog: saveSettingsTo()");
		
		QuandlEuronext__Config config = new QuandlEuronext__Config();
		
		config.setm_api_key(apiKeyTF.getText());
		String selectedStartDate = startDate.getText();
		if (selectedStartDate != null)
			config.setM_start_date(selectedStartDate);
		String selectedEndDate = endDate.getText();
		if (selectedEndDate != null)
			config.setM_end_date(selectedEndDate);
		if (currentTickers != null)
			config.setM_tickers(currentTickers);
		
		System.out.println("m_tickers.getm_api_key() = " + config.getm_api_key()
		+ "m_tickers.getM_start_date() = " + config.getM_start_date()
		+ "m_tickers.getM_end_date() = " + config.getM_end_date());
		config.saveSettingsTo(settings);
		contentPane.addToHistory();
	}

	public static void initJTable() {

		DefaultTableModel dtm = (DefaultTableModel) table.getModel();

		Object[][] data = new Object[QuandlEuronext__NodeModel.m_tickers.size()][1];

		for (int i = 0; i < QuandlEuronext__NodeModel.m_tickers.size(); i++) {

			data[i][0] = QuandlEuronext__NodeModel.m_tickers.get(i);

		}

		Object[] columnName = new Object[] { "Ticker" };

		dtm = new DefaultTableModel(data, columnName) {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setTableHeader(null);
		table.setModel(dtm);

//		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
//		centerRenderer.setHorizontalAlignment(SwingConstants.LEFT);
//		TableModel tableModel = table.getModel();
//		for (int columnIndex = 0; columnIndex < tableModel.getColumnCount(); columnIndex++) {
//			table.getColumnModel().getColumn(columnIndex).setCellRenderer(centerRenderer);
//		}

	}

	public String convertDate(Date date) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(date);
	}

	private boolean checkData(String ticker) {

		String errorMsg = "";

		String urlStr = "https://www.quandl.com/api/v3/datasets/EURONEXT/" + ticker
				+ ".json?start_date=2018-10-31&end_date=2018-10-31&api_key="
				+  apiKeyTF.getText();

		System.out.println("urlStr = " + urlStr);

		JsonObject searchJSONObj = QuandlEuronext__NodeModel.getJSONObjectFromURL(urlStr);

		if (searchJSONObj == null)
			return false;
		else
			return true;
	}
	private void updateTickersString() {
		
		currentTickers="";
		for(String ticker : QuandlEuronext__NodeModel.m_tickers) {
			currentTickers+=ticker;
		}
	}
}

//class DateLabelFormatter extends AbstractFormatter {
//
//	private String datePattern = "yyyy-MM-dd";
//	private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
//
//	@Override
//	public Object stringToValue(String text) throws ParseException {
//		return dateFormatter.parseObject(text);
//	}
//
//	@Override
//	public String valueToString(Object value) throws ParseException {
//		if (value != null) {
//			GregorianCalendar cal = (GregorianCalendar) value;
//			dateFormatter.setCalendar(cal);
//			return dateFormatter.format(cal.getTime());
//		}
//
//		return "";
//	}
//
//}