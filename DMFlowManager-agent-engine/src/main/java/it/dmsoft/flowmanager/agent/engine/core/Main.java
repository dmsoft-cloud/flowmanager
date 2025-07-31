package it.dmsoft.flowmanager.agent.engine.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;

import it.dmsoft.flowmanager.be.entities.ExportFlowData;
import it.dmsoft.flowmanager.agent.engine.core.as400.JdbcConnection;
//import it.dmsoft.flowmanager.agent.engine.core.db.dao.DbConstants;
//import it.dmsoft.flowmanager.agent.engine.core.db.dao.OtgffanaDAO;
//import it.dmsoft.flowmanager.agent.engine.core.db.dao.OtgfflogtDAO;
//import it.dmsoft.flowmanager.agent.engine.core.db.dao.ExportFlowDataDAO;
import it.dmsoft.flowmanager.agent.engine.core.exception.OperationException;
import it.dmsoft.flowmanager.agent.engine.core.exception.ParameterException;
import it.dmsoft.flowmanager.agent.engine.core.manager.InboundFlowManager;
import it.dmsoft.flowmanager.agent.engine.core.manager.OutboundFlowManager;
import it.dmsoft.flowmanager.agent.engine.core.model.ExecutionFlowData;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.OperationParams;
import it.dmsoft.flowmanager.agent.engine.core.properties.PropertiesUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.FormatUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.OperationType;
import it.dmsoft.flowmanager.agent.engine.core.utils.DatabaseUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.FlowLogUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.StringUtils;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class Main {

//	public static void main(String[] args) throws IOException {
//
//		Logger logger = null;
//		try {
//			System.out.println("PATH -> " + java.lang.System.getProperty("java.library.path"));
//			// args[0] nome transazione
//			// args[1] path
//			// args[2] livello di log
//			// args[3] transaction Id, che utilizzo come correlation nei log
//			// args[4] dimensione in MB del file
//			// args[5] numero di file per rotazione
//			// args[6] path di archiviazione dei flussi
//			// args[7] mail account
//			// args[8] cliente
//			// args[9] data di esecuzione
//
//			String transactionName = args[0];
//			//String backupPath = "/home/diegoa/gestoreflussi/backup/";
//			String backupPath = "C:\\GF\\backup";
//			String transactionId = "1325";
//			//String logPath = "/home/diegoa/gestoreflussi/log";
//			String logPath = "C:\\GF\\log";
//			String logLevel = "DEBUG";
//			String logSizeMB = "1";
//			String logRotation = "5";
//			String cliente = "OCS";
//			String mailFrom = "";
//			String resubmitTransactionId = "";
//			String executionDateStr = "";
//			BigDecimal executionDate = null;
//
//			if (args.length > 8) {
//				
//				transactionId = args[1];
//				logPath = args[2];
//				logLevel = args[3];
//				logSizeMB = args[4];
//				logRotation = args[5];
//				backupPath = args[6];
//				mailFrom = args[7];
//				cliente = args[8];
//				executionDateStr = args[9];
//				
//				// transactionId = new
//				// BigDecimal(transactionId).subtract(BigDecimal.ONE).toString();
//			}
//			File file = new File(logPath);
//
//			if (!file.exists())
//				Files.createDirectory(file.toPath());
//
//			String logFile = logPath + Constants.PATH_DELIMITER + transactionName + Constants.PATH_DELIMITER
//					+ transactionId;
//			logger = Logger.getLogger(logRotation, logSizeMB, logFile, logLevel, transactionId, Main.class.getName(),
//					cliente, Constants.GESTOREFLUSSI);
//
//			logger.info("start to manage transaction " + transactionName + " -> " + transactionId);
//						
//			if (!StringUtils.isNullOrEmpty(executionDateStr)) {
//				executionDate = new BigDecimal(executionDateStr);				
//			}
//			
//			logger.info("cliente -> " + cliente + 
//					", execution date -> " + (executionDate != null ? executionDate.toString() : FormatUtils.todayDateBigDec().toString()));
//			
//			try {
//				DatabaseUtils.setDBConstants();				
//			} catch (FileNotFoundException e) {
//				logger.info("properties file not found");
//			}
//			
//			ExecutionFlowData executionFlowData = OtgffanaDAO.read(transactionName);
//			executeFlow(transactionId, resubmitTransactionId, backupPath, mailFrom, logFile, executionDate, logger, executionFlowData);
//		} catch (Exception e) {
//			if (e instanceof OperationException) {
//				OperationException oe = (OperationException) e;
//				logger.error(oe.getKeyValueLogs(), oe);
//			}
//
//			logger.error("Error on gestoreflussi invocation", e);
//			try {
//				LogDb.end(OperationType.OPE_KO);
//			} catch (Exception e1) {
//				logger.error("Error on log details write", e);
//			}
//			System.exit(1);
//
//		} finally {
//			JdbcConnection.close();
//		}
//
//		/*
//		 * AS400JDBCConnectionPoolDataSource as400jdbcConnectionPoolDataSource = new
//		 * AS400JDBCConnectionPoolDataSource();
//		 * 
//		 * Connection conn = null; try { conn =
//		 * as400jdbcConnectionPoolDataSource.getConnection();
//		 * 
//		 * System.out.println(conn.getClientInfo());
//		 * 
//		 * PreparedStatement ps =
//		 * conn.prepareStatement("SELECT * FROM B2BDATI.cccomuni"); ResultSet rs =
//		 * ps.executeQuery();
//		 * 
//		 * while (rs.next()) { System.out.println(rs.getString(2)); }
//		 * 
//		 * } catch (SQLException e) { // TODO Auto-generated catch block
//		 * e.printStackTrace(); } finally { try { conn.close(); } catch (SQLException e)
//		 * { // TODO Auto-generated catch block e.printStackTrace(); } }
//		 */
//		/*
//		 * // Register both drivers. try {
//		 * Class.forName("com.ibm.as400.access.AS400JDBCDriver"); } catch
//		 * (ClassNotFoundException cnf) {
//		 * System.out.println("ERROR: One of the JDBC drivers did not load.");
//		 * System.exit(0); }
//		 * 
//		 * try { // Obtain a connection with each driver. Connection conn2 =
//		 * DriverManager.getConnection("jdbc:as400://localhost", "*CURRENT",
//		 * "*CURRENT");
//		 * 
//		 * if (conn2 instanceof com.ibm.as400.access.AS400JDBCConnection) System.out.
//		 * println("conn2 is running under the IBM Toolbox for Java JDBC driver."); else
//		 * System.out.println("There is something wrong with conn2.");
//		 * 
//		 * conn2.close(); } catch (SQLException e) { System.out.println("ERROR: " +
//		 * e.getMessage()); }
//		 */
//	}
//	
//	public static void executeFlow(String transactionId, String resubmitTransactionId, String backupPath, String mailFrom,
//			String logFile, BigDecimal executionDate, Logger logger, ExecutionFlowData executionFlowData) throws Exception{
//		
//		String transactionName = executionFlowData.getFlowId();
//		
//		logger.info("start to manage transaction " + transactionName + " -> " + transactionId);
//		logger.info(executionFlowData.toString());
//		
//		//TO BE REEVALUATED
//		/*
//		if (!StringUtils.isNullOrEmpty(resubmitTransactionId)) {
//			executionFlowData = resubmit(executionFlowData, resubmitTransactionId);
//		}
//		*/
//		
//
//		ExportFlowData exportFlowData = null;
//		//verifico se c'è un export e se è di tipo new o old. in tal caso devo vedere se applicare la selezione colonne
//		if (!StringUtils.isNullOrEmpty(executionFlowData.getFlowExportCode()) && executionFlowData.getFlowExportFlag().equals("S")) {
//				exportFlowData = ExportFlowDataDAO.read(executionFlowData.getFlowExportCode());
//		}
//
//
//		OtgfflogtDAO.updateLogPath(new BigDecimal(transactionId), logFile);
//
//		OperationParams inputParam = new OperationParams();
//		inputParam.setPathBackup(backupPath);
//		inputParam.setMailAccount(mailFrom);
//		inputParam.setTransactionId(new BigDecimal(transactionId));
//		inputParam.setTransactionName(transactionName);
//		inputParam.setListFile(transactionName + transactionId);
//		inputParam.setLegacyModernization(Constants.NO);
//		if (exportFlowData != null) inputParam.setExportFileHeaders(exportFlowData.getExportTestata());
//		if (executionFlowData.getFlowAggNomiCol().equals("*EXF")) inputParam.setExportFileHeaders(executionFlowData.getFlowAggNomiCol());
//		
//		
//		if(executionDate != null && BigDecimal.ZERO.compareTo(executionDate) < 0) {
//			inputParam.setExecutionDate(FormatUtils.date(executionDate));
//		}
//
//		logger.debug("masterdata of " + executionFlowData.getFlowId() + " loaded, staring flow manager");
//
//		LogDb.instantiate(inputParam);
//
//		if (Direction.OUTBOUND.equals(executionFlowData.getFlowDirezione())) {
//			OutboundFlowManager outboundManger = new OutboundFlowManager();
//			outboundManger.process(executionFlowData, inputParam);
//		} else {
//			InboundFlowManager inboundManager = new InboundFlowManager();
//			inboundManager.process(executionFlowData, inputParam);
//		}
//	}
//	
//	private static ExecutionFlowData resubmit(ExecutionFlowData executionFlowData, String resubmitTransactionId) throws Exception {
//		if (!YesNo.YES.equals(executionFlowData.getFlowRisottomettibile())) {
//			throw new ParameterException("Transaction not resubmittable");
//		}
//		
//		ExecutionFlowData originalOtgffana = OtgfflogtDAO.readForResubmit(new BigDecimal(resubmitTransactionId));
//		//TODO COMPLETE MERGE ACTIONS
//		originalOtgffana.setFlowRisottomettibile(executionFlowData.getFlowRisottomettibile());
//		
//		return originalOtgffana;
//		
//	}
}
