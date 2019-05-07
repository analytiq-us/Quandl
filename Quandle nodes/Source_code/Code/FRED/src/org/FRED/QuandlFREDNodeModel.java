package org.FRED;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.knime.base.node.io.filereader.FileAnalyzer;
import org.knime.base.node.io.filereader.FileReaderExecutionMonitor;
import org.knime.base.node.io.filereader.FileReaderNodeSettings;
import org.knime.base.node.io.filereader.FileTable;
//import org.knime.core.data.DataCell;
//import org.knime.core.data.DataColumnSpec;
//import org.knime.core.data.DataColumnSpecCreator;
//import org.knime.core.data.DataRow;
import org.knime.core.data.DataTableSpec;
//import org.knime.core.data.RowKey;
//import org.knime.core.data.def.DefaultRow;
//import org.knime.core.data.def.DoubleCell;
//import org.knime.core.data.def.IntCell;
//import org.knime.core.data.def.StringCell;
//import org.knime.core.node.BufferedDataContainer;
import org.knime.core.node.BufferedDataTable;
import org.knime.core.node.CanceledExecutionException;
//import org.knime.core.node.defaultnodesettings.SettingsModelIntegerBounded;
import org.knime.core.node.util.CheckUtils;
import org.knime.core.node.workflow.NodeProgress;
import org.knime.core.node.workflow.NodeProgressEvent;
import org.knime.core.node.workflow.NodeProgressListener;
import org.knime.core.util.FileUtil;
import org.knime.core.util.tokenizer.SettingsStatus;
import org.knime.core.node.ExecutionContext;
import org.knime.core.node.ExecutionMonitor;
import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeCreationContext;
//import org.knime.core.node.NodeLogger;
import org.knime.core.node.NodeModel;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;


/**
 * This is the model implementation of QuandlFRED.
 * 
 *
 * @author VIDHYA
 */
public class QuandlFREDNodeModel extends NodeModel {
//	public static CSVReaderConfig m_config_ = new CSVReaderConfig();
    
    // the logger instance
  //  private static final NodeLogger logger = NodeLogger.getLogger(QuandlFREDNodeModel.class);
        
    /** the settings key which is used to retrieve and 
        store the settings (from the dialog or from a settings file)    
       (package visibility to be usable from the dialog). */
//	static final String CFGKEY_COUNT = "Count";

    /** initial default count value. */
  //  static final int DEFAULT_COUNT = 100;

    // example value: the models count variable filled from the dialog 
    // and used in the models execution method. The default components of the
    // dialog work with "SettingsModels".
//    private final SettingsModelIntegerBounded m_count =
//        new SettingsModelIntegerBounded(QuandlFREDNodeModel.CFGKEY_COUNT,
//                    QuandlFREDNodeModel.DEFAULT_COUNT,
//                    Integer.MIN_VALUE, Integer.MAX_VALUE);
//    

    /**
     * Constructor for the node model.
     */
    
	public static CSVReaderConfig m_config = new CSVReaderConfig();
//	private CSVReaderConfig m_config;
//    private GenerateURL gen;
    
    protected QuandlFREDNodeModel() {
    
        // TODO one incoming port and one outgoing port is assumed
        super(0, 1);
    }
    
    
    
    QuandlFREDNodeModel(final NodeCreationContext context){
    	 this();
         m_config = new CSVReaderConfig();
                
    }

    
    protected CSVReaderConfig getConfig() {
        return m_config;
    }

    
    /**
     * {@inheritDoc}
     */
    @Override
    protected BufferedDataTable[] execute(final BufferedDataTable[] inData,
            final ExecutionContext exec) throws Exception {

//        // TODO do something here
//        logger.info("Node Model Stub... this is not yet implemented !");
//
//        
//        // the data table spec of the single output table, 
//        // the table will have three columns:
//        DataColumnSpec[] allColSpecs = new DataColumnSpec[3];
//        allColSpecs[0] = 
//            new DataColumnSpecCreator("Column 0", StringCell.TYPE).createSpec();
//        allColSpecs[1] = 
//            new DataColumnSpecCreator("Column 1", DoubleCell.TYPE).createSpec();
//        allColSpecs[2] = 
//            new DataColumnSpecCreator("Column 2", IntCell.TYPE).createSpec();
//        DataTableSpec outputSpec = new DataTableSpec(allColSpecs);
//        // the execution context will provide us with storage capacity, in this
//        // case a data container to which we will add rows sequentially
//        // Note, this container can also handle arbitrary big data tables, it
//        // will buffer to disc if necessary.
//        BufferedDataContainer container = exec.createDataContainer(outputSpec);
//        // let's add m_count rows to it
//        for (int i = 0; i < m_count.getIntValue(); i++) {
//            RowKey key = new RowKey("Row " + i);
//            // the cells of the current row, the types of the cells must match
//            // the column spec (see above)
//            DataCell[] cells = new DataCell[3];
//            cells[0] = new StringCell("String_" + i); 
//            cells[1] = new DoubleCell(0.5 * i); 
//            cells[2] = new IntCell(i);
//            DataRow row = new DefaultRow(key, cells);
//            container.addRowToTable(row);
//            
//            // check if the execution monitor was canceled
//            exec.checkCanceled();
//            exec.setProgress(i / (double)m_count.getIntValue(), 
//                "Adding row " + i);
//        }
//        // once we are done, we close the container and return its table
//        container.close();
//        BufferedDataTable out = container.getTable();
    	
//    	  return new BufferedDataTable[]{out};
    	
    	
    	FileTable fTable = createFileTable(exec);
        try {
            BufferedDataTable table = exec.createBufferedDataTable(fTable, exec.createSubExecutionContext(0.0));
            return new BufferedDataTable[] {table};
        } finally {
            // fix AP-6127
            fTable.dispose();
     }
     
    }
        protected FileTable createFileTable(final ExecutionContext exec) throws Exception {
            // prepare the settings for the file analyzer
        	//gen = new GenerateURL();
           
       
        	
        	FileReaderNodeSettings settings = new FileReaderNodeSettings();

        //	System.out.println("file---"+m_config.getCurr());
       // 	System.out.println("desc---"+m_config.getDesc());
        	//String loc_setter = gen.generate_url(m_config.getCurr(), m_config.getDesc());
        	m_config.updateLocation();
        	System.out.println("Model"+m_config.getLocation());
        	
       // 	System.out.println("location---"+m_config.getLocation());
        	
            CheckUtils.checkSourceFile(m_config.getLocation());
           
            URL url = FileUtil.toURL(m_config.getLocation());
            
     //       System.out.println("location---"+m_config.getLocation());
            
            settings.setDataFileLocationAndUpdateTableName(url);

//            String colDel = m_config.getColDelimiter();
//            if (colDel != null && !colDel.isEmpty()) {
//                settings.addDelimiterPattern(colDel, false, false, false);
//            }
//            settings.setDelimiterUserSet(true);
//
//            String rowDel = m_config.getRowDelimiter();
//            if (rowDel != null && !rowDel.isEmpty()) {
//                settings.addRowDelimiter(rowDel, true);
//            }
//            String quote = m_config.getQuoteString();
//            if (quote != null && !quote.isEmpty()) {
//                settings.addQuotePattern(quote, quote);
//            }
//            settings.setQuoteUserSet(true);
//
//            String commentStart = m_config.getCommentStart();
//            if (commentStart != null && !commentStart.isEmpty()) {
//                settings.addSingleLineCommentPattern(commentStart, false, false);
//            }
//            settings.setCommentUserSet(true);
//
//            boolean hasColHeader = m_config.hasColHeader();
//            settings.setFileHasColumnHeaders(hasColHeader);
//            settings.setFileHasColumnHeadersUserSet(true);
//
//            boolean hasRowHeader = m_config.hasRowHeader();
//            settings.setFileHasRowHeaders(hasRowHeader);
//            settings.setFileHasRowHeadersUserSet(true);
//
//            settings.setWhiteSpaceUserSet(true);
//
//            boolean supportShortLines = m_config.isSupportShortLines();
//            settings.setSupportShortLines(supportShortLines);
//
//            int skipFirstLinesCount = m_config.getSkipFirstLinesCount();
//            settings.setSkipFirstLines(skipFirstLinesCount);
//
//            final long limitRowsCount = m_config.getLimitRowsCount();
//            settings.setMaximumNumberOfRowsToRead(limitRowsCount);
//
//            settings.setCharsetName(m_config.getCharSetName());
//            settings.setCharsetUserSet(true);

//            settings.setConnectTimeout(m_config.getConnectTimeout());

            final int limitAnalysisCount = m_config.getLimitAnalysisCount();
            final ExecutionMonitor analyseExec = exec.createSubProgress(0.5);
            final ExecutionContext readExec = exec.createSubExecutionContext(0.5);
            exec.setMessage("Analyzing file");
            if (limitAnalysisCount >= 0) {
                final FileReaderExecutionMonitor fileReaderExec = new FileReaderExecutionMonitor();
                fileReaderExec.getProgressMonitor().addProgressListener(new NodeProgressListener() {

                    @Override
                    public void progressChanged(final NodeProgressEvent pe) {
                        try {
                            //if the node was canceled, cancel (interrupt) the analysis
                            analyseExec.checkCanceled();
                            //otherwise update the node progress
                            NodeProgress nodeProgress = pe.getNodeProgress();
                            analyseExec.setProgress(nodeProgress.getProgress(), nodeProgress.getMessage());
                        } catch (CanceledExecutionException e) {
                            fileReaderExec.setExecuteInterrupted();
                        }
                    }
                });
//                fileReaderExec.setShortCutLines(limitAnalysisCount);
                fileReaderExec.setExecuteCanceled();
                settings = FileAnalyzer.analyze(settings, fileReaderExec);
            } else {
                settings = FileAnalyzer.analyze(settings, analyseExec);
            }
            SettingsStatus status = settings.getStatusOfSettings();
            if (status.getNumOfErrors() > 0) {
                throw new IllegalStateException(status.getErrorMessage(0));
            }
            final DataTableSpec tableSpec = settings.createDataTableSpec();
            if (tableSpec == null) {
                final SettingsStatus status2 = settings.getStatusOfSettings(true, null);
                if (status2.getNumOfErrors() > 0) {
                    throw new IllegalStateException(status2.getErrorMessage(0));
                } else {
                    throw new IllegalStateException("Unknown error during file analysis.");
                }
            }
            exec.setMessage("Buffering file");
            return new FileTable(tableSpec, settings, readExec);
    }
    	
    	
    	
    

    /**
     * {@inheritDoc}
     */
    @Override
    protected void reset() {
        // TODO Code executed on reset.
        // Models build during execute are cleared here.
        // Also data handled in load/saveInternals will be erased here.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected DataTableSpec[] configure(final DataTableSpec[] inSpecs)
            throws InvalidSettingsException {
        
        // TODO: check if user settings are available, fit to the incoming
        // table structure, and the incoming types are feasible for the node
        // to execute. If the node can execute in its current state return
        // the spec of its output data table(s) (if you can, otherwise an array
        // with null elements), or throw an exception with a useful user message

        return new DataTableSpec[]{null};
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void saveSettingsTo(final NodeSettingsWO settings) {

        // TODO save user settings to the config object.
        
//        m_count.saveSettingsTo(settings);
    	
    	  if (m_config != null) {
    		  	System.out.println("QuandlEURONEXTNodeModel: Enter into saveSettingsTo()");
			m_config.saveSettingsTo(settings);
//    				System.out.println("m_tickers.getm_api_key() = " + m_config.getAPIKey() + "m_tickers.getM_start_date() = "
//    								+ m_config.getM_start_date() + "m_tickers.getM_end_date() = " + m_config.getM_end_date());
    	  }
    	

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void loadValidatedSettingsFrom(final NodeSettingsRO settings)
            throws InvalidSettingsException {
            
        // TODO load (valid) settings from the config object.
        // It can be safely assumed that the settings are valided by the 
        // method below.
        
  //      m_count.loadSettingsFrom(settings);
    	
    	 	CSVReaderConfig config = new CSVReaderConfig();
    		config.loadSettingsInModel(settings);
    		m_config = config;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void validateSettings(final NodeSettingsRO settings)
            throws InvalidSettingsException {
            
        // TODO check if the settings could be applied to our model
        // e.g. if the count is in a certain range (which is ensured by the
        // SettingsModel).
        // Do not actually set any values of any member variables.

    //    m_count.validateSettings(settings);

//    		QuandlFREDNodeModel.m_config.loadSettingsInModel(settings);
    	new CSVReaderConfig().loadSettingsInModel(settings);
    	
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void loadInternals(final File internDir,
            final ExecutionMonitor exec) throws IOException,
            CanceledExecutionException {
        
        // TODO load internal data. 
        // Everything handed to output ports is loaded automatically (data
        // returned by the execute method, models loaded in loadModelContent,
        // and user settings set through loadSettingsFrom - is all taken care 
        // of). Load here only the other internals that need to be restored
        // (e.g. data used by the views).

    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void saveInternals(final File internDir,
            final ExecutionMonitor exec) throws IOException,
            CanceledExecutionException {
       
        // TODO save internal models. 
        // Everything written to output ports is saved automatically (data
        // returned by the execute method, models saved in the saveModelContent,
        // and user settings saved through saveSettingsTo - is all taken care 
        // of). Save here only the other internals that need to be preserved
        // (e.g. data used by the views).

    }

}

