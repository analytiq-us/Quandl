package org.eod;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

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

/**
 * <code>NodeDialog</code> for the "QuandlEOD" Node.
 * 
 *
 * This node dialog derives from {@link DefaultNodeSettingsPane} which allows
 * creation of a simple dialog with standard components. If you need a more
 * complex dialog please derive directly from
 * {@link org.knime.core.node.NodeDialogPane}.
 * 
 * @author I Tang Lo
 */
public class QuandlEod__NodeDialog extends NodeDialogPane {

	private FilesHistoryPanel contentPane;
	private static JTable table;
	private JTextField apiKeyTF;
	private JTextField endDate;
	private JTextField startDate;
	private JComboBox<String> tickersCCB;
	private DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
	private String currentTickers;

	protected QuandlEod__NodeDialog() {
		super();

		if (QuandlEod__NodeModel.m_config == null)
			QuandlEod__NodeModel.m_config = new QuandlEod__Config();
		
		addTab("EOD", initLayout());

	}

	private JPanel initLayout() {

		contentPane = new FilesHistoryPanel(createFlowVariableModel("url", FlowVariable.Type.STRING), "csv_read",
				LocationValidation.FileInput, ".csv", ".txt");
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

		final JComboBox comboBox = new JComboBox(new String[] { "NASDAQ", "NYSE", "NYSE Arca", "NYSE MKT" });
		comboBox.setBounds(178, 35, 191, 26);
		comboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {

				int selectedItem = comboBox.getSelectedIndex();
				if (selectedItem == 0) {

					Object[] obj = new QuandlEODTickerList().getTickerNASDAQ();

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

					Object[] obj = new QuandlEODTickerList().getTickerNYSE();

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

					Object[] obj = new QuandlEODTickerList().getTickerNYSEArca();

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

					Object[] obj = new QuandlEODTickerList().getTickerNYSEMKT();

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

		tickersCCB = new AutoCompleteComboBox(new QuandlEODTickerList().getTickerNASDAQ());
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
//		startDate.addFocusListener(new FocusListener() {
//
//			@Override
//			public void focusLost(FocusEvent e) {
//
//				if (!"".equals(startDate.getText().trim())) {
//					
//					if (dateFormatCheck(startDate.getText())) {
//
//						if (!"".equals(endDate.getText().trim())) {
//
//							try {
//
//								Date start = new SimpleDateFormat("yyyy-MM-dd").parse(startDate.getText().trim());
//								Date end = new SimpleDateFormat("yyyy-MM-dd").parse(endDate.getText().trim());
//								if (start.getTime() > end.getTime()) {
//
//									JOptionPane.showMessageDialog(null, "End Date earlier than Start Date",
//											"Wrong Selection", JOptionPane.WARNING_MESSAGE);
//								}
//
//							} catch (ParseException e1) {
//								e1.printStackTrace();
//							}
//
//						}
//
//					} else {
//
//						startDate.setText("");
//						JOptionPane.showMessageDialog(null, "Date Format : yyyy-MM-dd \n EX : 2018-01-01",
//								"Wrong Typing", JOptionPane.WARNING_MESSAGE);
//
//					}
//				}
//			}
//
//			@Override
//			public void focusGained(FocusEvent e) {
//
//			}
//		});
//		startDate.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {

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
//		endDate.addFocusListener(new FocusListener() {
//
//			@Override
//			public void focusLost(FocusEvent e) {
//
//				if (!"".equals(endDate.getText().trim())) {
//
//					if (dateFormatCheck(endDate.getText())) {
//
//						if (!"".equals(startDate.getText().trim())) {
//
//							try {
//
//								Date start = new SimpleDateFormat("yyyy-MM-dd").parse(startDate.getText().trim());
//								Date end = new SimpleDateFormat("yyyy-MM-dd").parse(endDate.getText().trim());
//								if (start.getTime() > end.getTime()) {
//
//									JOptionPane.showMessageDialog(null, "End Date earlier than Start Date",
//											"Wrong Selection", JOptionPane.WARNING_MESSAGE);
//								}
//
//							} catch (ParseException e1) {
//								e1.printStackTrace();
//							}
//
//						}
//
//					} else {
//
//						endDate.setText("");
//						JOptionPane.showMessageDialog(null, "Date Format : yyyy-MM-dd \n EX : 2018-01-01",
//								"Wrong Typing", JOptionPane.WARNING_MESSAGE);
//
//					}
//				}
//			}
//
//			@Override
//			public void focusGained(FocusEvent e) {
//
//			}
//		});
//		endDate.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				Date end = (Date) endDate.getModel().getValue();
//				Date start = (Date) startDate.getModel().getValue();
//
//				if (end != null && start != null && end.getTime() < start.getTime()) {
//					JOptionPane.showMessageDialog(null, "End Date earlier than Start Date", "Wrong Selection",
//							JOptionPane.WARNING_MESSAGE);
//				}
//			}
//		});
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

				if (!"".equals(apiKeyTF.getText().trim())) {

					String ticker = (String.valueOf(tickersCCB.getSelectedItem())).toUpperCase();

					System.out.println("select " + ticker);

					// if ticker already in chose list
					if (QuandlEod__NodeModel.m_tickers.indexOf(ticker.toUpperCase()) == -1) {

						if (checkData(ticker)) {
							QuandlEod__NodeModel.m_tickers.add(ticker);
							updateTickersString();
						} else
							JOptionPane.showMessageDialog(null, "Data is premium / Unavailable", "Invalid",
									JOptionPane.ERROR_MESSAGE);

					} else {
						JOptionPane.showMessageDialog(null, "Ticker Already in the list", "",
								JOptionPane.WARNING_MESSAGE);
					}
					System.out.println("tickers size = " + QuandlEod__NodeModel.m_tickers.size());

					initJTable();

				} else {
					JOptionPane.showMessageDialog(null, "Please enter an api key in step 1", "Invalid",
							JOptionPane.ERROR_MESSAGE);
				}
			}

		});
		contentPane.add(addBtn);

		JButton removeBtn = new JButton("Remove");
		removeBtn.setBounds(279, 121, 90, 27);
		removeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				QuandlEod__NodeModel.m_tickers.remove(table.getSelectedRow());
				System.out.println("tickers size = " + QuandlEod__NodeModel.m_tickers.size());
				System.out.println("select " + tickersCCB.getSelectedItem().toString());

				initJTable();

				updateTickersString();
			}

		});
		contentPane.add(removeBtn);

		return contentPane;
	}

//	protected boolean dateFormatCheck(String dateStr) {
//
//		// yyyy-MM-dd
//		String regex = "^[0-9]{4}-(1[0-2]|0[1-9])-(3[01]|[12][0-9]|0[1-9])$";
//
//		Pattern pattern = Pattern.compile(regex);
//
//		return pattern.matcher(dateStr.trim()).matches();
//	}

	/** {@inheritDoc} */
	@Override
	protected void loadSettingsFrom(final NodeSettingsRO settings, final DataTableSpec[] specs)
			throws NotConfigurableException {

		System.out.println("QuandlEODNodeDialog: loadSettingsFrom()");

		QuandlEod__Config config = new QuandlEod__Config();
		config.loadSettingsInDialog(settings);
		contentPane.updateHistory();

//		apiKeyTF.setText(QuandlEODNodeModel.m_config.getm_api_key());

		System.out.println("m_tickers.size() = " + QuandlEod__NodeModel.m_tickers.size());
		System.out.println("m_tickers.getm_api_key() = " + QuandlEod__NodeModel.m_config.getm_api_key()
				+ "m_tickers.getM_start_date() = " + QuandlEod__NodeModel.m_config.getM_start_date()
				+ "m_tickers.getM_end_date() = " + QuandlEod__NodeModel.m_config.getM_end_date());

	}

	/** {@inheritDoc} */
	@Override
	protected void saveSettingsTo(final NodeSettingsWO settings) throws InvalidSettingsException {

		System.out.println("QuandlEODNodeDialog: saveSettingsTo()");

		QuandlEod__Config config = new QuandlEod__Config();

		config.setm_api_key(apiKeyTF.getText());
		String selectedStartDate = startDate.getText();
		if (selectedStartDate != null)
			config.setM_start_date(selectedStartDate);
		String selectedEndDate = endDate.getText();
		if (selectedEndDate != null)
			config.setM_end_date(selectedEndDate);
		if (currentTickers != null)
			config.setM_tickers(currentTickers);

		System.out.println("convertDate(selectedStartDate) = " + selectedStartDate);
		System.out.println("convertDate(selectedEndDate) = " + selectedEndDate);

		System.out.println("m_tickers.size() = " + QuandlEod__NodeModel.m_tickers.size());
		System.out.println("m_tickers.getm_api_key() = " + QuandlEod__NodeModel.m_config.getm_api_key()
				+ "m_tickers.getM_start_date() = " + QuandlEod__NodeModel.m_config.getM_start_date()
				+ "m_tickers.getM_end_date() = " + QuandlEod__NodeModel.m_config.getM_end_date());
		config.saveSettingsTo(settings);
		contentPane.addToHistory();

	}

	public static void initJTable() {

		DefaultTableModel dtm = (DefaultTableModel) table.getModel();

		Object[][] data = new Object[QuandlEod__NodeModel.m_tickers.size()][1];

		for (int i = 0; i < QuandlEod__NodeModel.m_tickers.size(); i++) {

			data[i][0] = QuandlEod__NodeModel.m_tickers.get(i);

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
		if (date == null)
			return "";
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(date);
	}

	private boolean checkData(String ticker) {

		String urlStr = "https://www.quandl.com/api/v3/datasets/EOD/" + ticker
				+ ".json?start_date=2018-10-31&end_date=2018-10-31&api_key="
				+ QuandlEod__NodeModel.m_config.getm_api_key();

		System.out.println("urlStr = " + urlStr);

		JsonObject searchJSONObj = QuandlEod__NodeModel.getJSONObjectFromURL(urlStr);

		if (searchJSONObj == null)
			return false;
		else
			return true;
	}

	private void updateTickersString() {

		currentTickers = "";
		for (String ticker : QuandlEod__NodeModel.m_tickers) {
			currentTickers += ticker;
		}
	}
}

class DateLabelFormatter extends AbstractFormatter {

	private static final long serialVersionUID = 1L;

	private String datePattern = "yyyy-MM-dd";
	private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

	@Override
	public Object stringToValue(String text) throws ParseException {
		return dateFormatter.parseObject(text);
	}

	@Override
	public String valueToString(Object value) throws ParseException {
		if (value != null) {
			GregorianCalendar cal = (GregorianCalendar) value;
			dateFormatter.setCalendar(cal);
			return dateFormatter.format(cal.getTime());
		}

		return "";
	}

}