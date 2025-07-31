package it.dmsoft.flowmanager.agent.engine.core.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import it.dmsoft.flowmanager.be.entities.ExportFlowData;
import it.dmsoft.flowmanager.be.entities.FlowConfig;
import it.dmsoft.flowmanager.be.entities.FlowLog;
import it.dmsoft.flowmanager.be.repositories.ExportFlowDataRepository;
import it.dmsoft.flowmanager.be.repositories.FlowLogDetailsRepository;
import it.dmsoft.flowmanager.be.repositories.FlowLogRepository;
import it.dmsoft.flowmanager.common.domain.Domains.Direction;
import it.dmsoft.flowmanager.common.domain.Domains.YesNo;
import it.dmsoft.flowmanager.agent.engine.core.Main;
import it.dmsoft.flowmanager.agent.engine.core.as400.JdbcConnection;
import it.dmsoft.flowmanager.agent.engine.core.exception.OperationException;
import it.dmsoft.flowmanager.agent.engine.core.exception.ParameterException;
import it.dmsoft.flowmanager.agent.engine.core.model.ExecutionFlowData;
import it.dmsoft.flowmanager.agent.engine.core.model.MasterdataOverride;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.OperationParams;
import it.dmsoft.flowmanager.agent.engine.core.properties.PropertiesConstants;
import it.dmsoft.flowmanager.agent.engine.core.properties.PropertiesUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.ConfigUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.OperationType;
import it.dmsoft.flowmanager.agent.engine.core.utils.DatabaseUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.FlowLogUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.FlowMasterDataUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.FormatUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.StringUtils;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;

@Service("dynamicFlowManager")
public class DynamicFlowManager {
	
	/**
	 * 
	 * @param args
	 * 		- args[0]: json object
	 * @throws IOException
	 */
	
	//private FlowIdNumeratorRepository flowIdNumeratorRepository;
	@PersistenceContext
    private EntityManager entityManager;

	@Resource(name = "flowLogUtils")
	private FlowLogUtils flowLogUtils;
	
	@Autowired
    protected ApplicationContext applicationContext;
	
	//private FlowLogRepository flowLogRepository;
	
	//private FlowLogDetailsRepository flowLogDetailsRepository;
	
	private ExportFlowDataRepository exportFlowDataRepository;
	
	public void main(String[] args, ExecutionFlowData executionFlowData)throws IOException  {
		Logger logger = null;
		
		try {
			System.out.println("PATH -> " + java.lang.System.getProperty("java.library.path"));
			System.out.println(args[0]);
			/*
			File fileCk = new File("/tmp/GFcheckInput.txt");
			for (String arg : args ) {
			
			LocalDateTime now = LocalDateTime.now();
		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		    String timestamp = now.format(formatter);
		    String lineToWrite = timestamp + " " + arg;
		    
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileCk, true))) {
		            writer.write(lineToWrite);
		            writer.newLine(); // Aggiungi una nuova riga
		        } catch (IOException e) {
		            System.err.println("Errore durante la scrittura nel file: " + e.getMessage());
		      }
			
			}
			*/
			String params = args[0];
			String resubmitTransactionId = "";
			BigDecimal executionDate = null;
			FlowConfig config = ConfigUtils.setConfig(params);
			System.out.println("CONFIG: " + config.toString() );

			File file = new File(config.getLogPath());

			if (!file.exists())
				Files.createDirectory(file.toPath());

			String logFile = config.getLogPath() + Constants.PATH_DELIMITER + config.getTransactionName() + Constants.PATH_DELIMITER
					+ config.getTransactionId();
			logger = Logger.getLogger(config.getLogRotation(), config.getLogSizeMB(), logFile, config.getLogLevel(), config.getTransactionId(), Main.class.getName(),
					config.getCliente(), Constants.GESTOREFLUSSI);

			logger.info("start to manage transaction " + config.getTransactionName() + " -> " + config.getTransactionId());
			
			
			if (!StringUtils.isNullOrEmpty(config.getExecutionDateStr())) {
				executionDate = new BigDecimal(config.getExecutionDateStr());				
			}
			
			logger.info("cliente -> " + config.getCliente() + 
					", execution date -> " + (executionDate != null ? executionDate.toString() : FormatUtils.todayDateBigDec().toString()));
			
			try {
				DatabaseUtils.setDBConstants();
				
			} catch (FileNotFoundException e) {
				logger.info("properties file not found");
			}
			
			//ExecutionFlowData executionFlowData = OtgffanaDAO.read(config.getTransactionName());
			
			List<MasterdataOverride> listFlowsOverride = null; 
			//verifico che in input non siano arrivate specifiche per dati di ovverride del flusso rispetto ad executionFlowData
			if (!StringUtils.isNullOrEmpty(config.getMasterdataOverrides())) {
				listFlowsOverride = FlowMasterDataUtils.getMasterData(config.getMasterdataOverrides(), executionFlowData);		
			}
			
			if (listFlowsOverride !=null) {
			   for(MasterdataOverride mo : listFlowsOverride)	{
				   //per la legacy se arriva come libreria QTEMP imposto la libreria temporanei
				   if(YesNo.YES.equals(config.getLegacyModernization()) 
						   	&& mo.getExecutionFlowData().getFlowLibreria().equalsIgnoreCase(Constants.QTEMP)) {
					   mo.getExecutionFlowData().setFlowLibreria(PropertiesUtils.get(PropertiesConstants.TMP_LIBRARY));
				   }
				   executeFlow(config.getTransactionName(), config.getTransactionId(), resubmitTransactionId, config.getBackupPath(), config.getMailFrom(), logFile, executionDate, logger, mo.getExecutionFlowData(), mo.getMailDest(), config); 
			   }
			}
			else executeFlow(config.getTransactionName(), config.getTransactionId(), resubmitTransactionId, config.getBackupPath(), config.getMailFrom(), logFile, executionDate, logger, executionFlowData, null, config);
		} catch (Exception e) {
			if (e instanceof OperationException) {
				OperationException oe = (OperationException) e;
				logger.error(oe.getKeyValueLogs(), oe);
			}

			logger.error("Error on gestoreflussi invocation", e);
			//System.out.println("Errore intercettato : " + e.getMessage());
			//e.printStackTrace();
			try {
				FlowLogUtils.endDetail(OperationType.OPE_KO);
			} catch (Exception e1) {
				//System.out.println(e.getMessage());
				logger.error("Error on log details write", e);
			}
			System.exit(1);

		} finally {
			JdbcConnection.close();
		}


	}
	

	public void executeFlow(ExecutionFlowData executionFlowData, List<String> overrideMailDests, FlowConfig config) throws Exception{
		
		String transactionName = executionFlowData.getFlowId();
		
		
		BigDecimal executionDate = FormatUtils.todayDateBigDec();
				
		FlowLog headLog = FlowLogUtils.insertFlowLog(executionFlowData);
		BigDecimal transactionId = headLog.getLogProgrLog();
		config.setTransactionId(transactionId.toString());
		
		String logFile = config.getLogPath() + Constants.PATH_DELIMITER + config.getTransactionName() + Constants.PATH_DELIMITER
				+ config.getTransactionId();
		
		Logger logger = Logger.getLogger(config.getLogRotation(), config.getLogSizeMB(), logFile, config.getLogLevel(), config.getTransactionId(), Main.class.getName(),
				config.getCliente(), Constants.GESTOREFLUSSI);
		
		FlowLogUtils.updateLogPath(headLog, logFile);
		
		String resubmitTransactionId = null;
		
		String backupPath = config.getBackupPath();
		String mailFrom = config.getMailFrom();
		
		executeFlow(transactionName, transactionId.toString(), resubmitTransactionId, backupPath, mailFrom, logFile, executionDate, logger, executionFlowData, overrideMailDests, config);
	}
		

	public void executeFlow(String transactionName, String transactionId, String resubmitTransactionId, String backupPath, String mailFrom,
			String logFile, BigDecimal executionDate, Logger logger, ExecutionFlowData executionFlowData, List<String> overrideMailDests, FlowConfig config) throws Exception{
		
		logger.info(executionFlowData.toString());
		
		if (!StringUtils.isNullOrEmpty(resubmitTransactionId)) {
			executionFlowData = resubmit(executionFlowData, resubmitTransactionId);
		}
		
		ExportFlowData exportFlowData = null;
		//verifico se c'è un export e se è di tipo new o old. in tal caso devo vedere se applicare la selezione colonne
		if (!StringUtils.isNullOrEmpty(executionFlowData.getFlowExportCode()) 
				&& (executionFlowData.getFlowExportFlag().equals("S") || executionFlowData.getFlowExportFlag().equals("O")) ) {
				exportFlowData = exportFlowDataRepository.findById(executionFlowData.getFlowExportCode()).orElseThrow(() -> new EntityNotFoundException("Entity not found"));
		}

		OperationParams inputParam = new OperationParams();
		inputParam.setPathBackup(backupPath);
		inputParam.setMailAccount(mailFrom);
		inputParam.setTransactionId(new BigDecimal(transactionId));
		inputParam.setTransactionName(transactionName);
		inputParam.setListFile(transactionName + transactionId);
		inputParam.setOverrideMailDests(overrideMailDests);
		inputParam.setTmpLibrary(PropertiesUtils.get(PropertiesConstants.TMP_LIBRARY));
		inputParam.setLegacyModernization(config.getLegacyModernization());
		if (exportFlowData != null) inputParam.setExportFileHeaders(exportFlowData.getExportTestata());
		if (Optional.ofNullable(executionFlowData.getFlowAggNomiCol())
				.filter(Constants.EXF::equals).isPresent()) inputParam.setExportFileHeaders(YesNo.YES);				
					
		if(executionDate != null && BigDecimal.ZERO.compareTo(executionDate) < 0) {
			inputParam.setExecutionDate(FormatUtils.date(executionDate));
		}
		inputParam.setExternalJob(config.getExtJob());
		inputParam.setExternalUser(config.getExtUser());
		if (!StringUtils.isNullOrEmpty(config.getExtNumber())) inputParam.setExternalNumber(new BigDecimal(config.getExtNumber()));
		inputParam.setExternalTask(config.getExtTask());

		logger.debug("masterdata of " + executionFlowData.getFlowId() + " loaded, staring flow manager");

		if (Direction.OUTBOUND.equals(executionFlowData.getFlowDirezione())) {
			OutboundFlowManager outboundManger = (OutboundFlowManager) applicationContext.getBean("outboundFlowManager");
			outboundManger.process(executionFlowData, inputParam);
		} else {
			InboundFlowManager inboundManager = (InboundFlowManager) applicationContext.getBean("inboundFlowManager");
			inboundManager.process(executionFlowData, inputParam);
		}
	}
	
	private static ExecutionFlowData resubmit(ExecutionFlowData executionFlowData, String resubmitTransactionId) throws Exception {
		if (!YesNo.YES.equals(executionFlowData.getFlowRisottomettibile())) {
			throw new ParameterException("Transaction not resubmittable");
		}
		
		//TODO need to be reimplemented
		ExecutionFlowData originalOtgffana = null;//= OtgfflogtDAO.readForResubmit(new BigDecimal(resubmitTransactionId));
		//TODO COMPLETE MERGE ACTIONS
		//originalOtgffana.setFlowRisottomettibile(executionFlowData.getFlowRisottomettibile());
		
		return originalOtgffana;
		
	}
}
