package it.dmsoft.flowmanager.agent.engine.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import it.dmsoft.flowmanager.agent.engine.core.as400.JdbcConnection;
import it.dmsoft.flowmanager.agent.engine.core.db.dao.OtgffanaDAO;
import it.dmsoft.flowmanager.agent.engine.core.db.dao.OtgfflogtDAO;
import it.dmsoft.flowmanager.agent.engine.core.db.dao.OttsfexptDAO;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.ConfigDto;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.MasterdataOverride;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.Otgffana;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.Ottsfexpt;
import it.dmsoft.flowmanager.agent.engine.core.exception.OperationException;
import it.dmsoft.flowmanager.agent.engine.core.exception.ParameterException;
import it.dmsoft.flowmanager.agent.engine.core.manager.InboundFlowManager;
import it.dmsoft.flowmanager.agent.engine.core.manager.OutboundFlowManager;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.OperationParams;
import it.dmsoft.flowmanager.agent.engine.core.properties.PropertiesConstants;
import it.dmsoft.flowmanager.agent.engine.core.properties.PropertiesUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.ConfigUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.OperationType;
import it.dmsoft.flowmanager.agent.engine.core.utils.DatabaseUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.FlowMasterDataUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.FormatUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.LogDb;
import it.dmsoft.flowmanager.agent.engine.core.utils.StringUtils;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class DynamicManager {

	/**
	 * 
	 * @param args
	 * 		- args[0]: json object
	 * @throws IOException
	 */
	public static void main(String[] args)throws IOException  {
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
			ConfigDto config = ConfigUtils.setConfig(params);
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
			
			Otgffana otgffana = OtgffanaDAO.read(config.getTransactionName());
			
			List<MasterdataOverride> listFlowsOverride = null; 
			//verifico che in input non siano arrivate specifiche per dati di ovverride del flusso rispetto ad otgffana
			if (!StringUtils.isNullOrEmpty(config.getMasterdataOverrides())) {
				listFlowsOverride = FlowMasterDataUtils.getMasterData(config.getMasterdataOverrides(), otgffana);		
			}
			
			if (listFlowsOverride !=null) {
			   for(MasterdataOverride mo : listFlowsOverride)	{
				   //per la legacy se arriva come libreria QTEMP imposto la libreria temporanei
				   if(Optional.ofNullable(config.getLegacyModernization()).filter(Constants.SI::equals).isPresent() 
						   	&& mo.getFanaData().getFana_Libreria().equalsIgnoreCase(Constants.QTEMP)) {
					   mo.getFanaData().setFana_Libreria(PropertiesUtils.get(PropertiesConstants.TMP_LIBRARY));
				   }
				   executeFlow(config.getTransactionName(), config.getTransactionId(), resubmitTransactionId, config.getBackupPath(), config.getMailFrom(), logFile, executionDate, logger, mo.getFanaData(), mo.getMailDest(), config); 
			   }
			}
			else executeFlow(config.getTransactionName(), config.getTransactionId(), resubmitTransactionId, config.getBackupPath(), config.getMailFrom(), logFile, executionDate, logger, otgffana, null, config);
		} catch (Exception e) {
			if (e instanceof OperationException) {
				OperationException oe = (OperationException) e;
				logger.error(oe.getKeyValueLogs(), oe);
			}

			logger.error("Error on gestoreflussi invocation", e);
			//System.out.println("Errore intercettato : " + e.getMessage());
			//e.printStackTrace();
			try {
				LogDb.end(OperationType.OPE_KO);
			} catch (Exception e1) {
				//System.out.println(e.getMessage());
				logger.error("Error on log details write", e);
			}
			System.exit(1);

		} finally {
			JdbcConnection.close();
		}


	}
	
	static void executeFlow(String transactionName, String transactionId, String resubmitTransactionId, String backupPath, String mailFrom,
			String logFile, BigDecimal executionDate, Logger logger, Otgffana otgffana, List<String> overrideMailDests, ConfigDto config) throws Exception{
		
		logger.info(otgffana.toString());
		
		if (!StringUtils.isNullOrEmpty(resubmitTransactionId)) {
			otgffana = resubmit(otgffana, resubmitTransactionId);
		}
		
		Ottsfexpt ottsfexpt = null;
		//verifico se c'è un export e se è di tipo new o old. in tal caso devo vedere se applicare la selezione colonne
		if (!StringUtils.isNullOrEmpty(otgffana.getFana_Export_Code()) 
				&& (otgffana.getFana_Export_Flag().equals("S") || otgffana.getFana_Export_Flag().equals("O")) ) {
				ottsfexpt = OttsfexptDAO.read(otgffana.getFana_Export_Code());
		}

		OtgfflogtDAO.updateLogPath(new BigDecimal(transactionId), logFile);


		OperationParams inputParam = new OperationParams();
		inputParam.setPathBackup(backupPath);
		inputParam.setMailAccount(mailFrom);
		inputParam.setTransactionId(new BigDecimal(transactionId));
		inputParam.setTransactionName(transactionName);
		inputParam.setListFile(transactionName + transactionId);
		inputParam.setOverrideMailDests(overrideMailDests);
		inputParam.setTmpLibrary(PropertiesUtils.get(PropertiesConstants.TMP_LIBRARY));
		inputParam.setLegacyModernization(Optional.ofNullable(config.getLegacyModernization())
                .filter(Constants.SI::equals)
                .map(op -> Constants.SI)
                .orElse(Constants.NO));
		if (ottsfexpt != null) inputParam.setExportFileHeaders(ottsfexpt.getExpt_Testata());
		if (Optional.ofNullable(otgffana.getFana_Agg_Nomi_Col())
				.filter(Constants.EXF::equals).isPresent()) inputParam.setExportFileHeaders(Constants.SI);				
					
		if(executionDate != null && BigDecimal.ZERO.compareTo(executionDate) < 0) {
			inputParam.setExecutionDate(FormatUtils.date(executionDate));
		}
		inputParam.setExternalJob(config.getExtJob());
		inputParam.setExternalUser(config.getExtUser());
		if (!StringUtils.isNullOrEmpty(config.getExtNumber())) inputParam.setExternalNumber(new BigDecimal(config.getExtNumber()));
		inputParam.setExternalTask(config.getExtTask());

		logger.debug("masterdata of " + otgffana.getFana_Id() + " loaded, staring flow manager");

		LogDb.instantiate(inputParam);

		if (Constants.OUTBOUND.equals(otgffana.getFana_Direzione())) {
			OutboundFlowManager outboundManger = new OutboundFlowManager();
			outboundManger.process(otgffana, inputParam);
		} else {
			InboundFlowManager inboundManager = new InboundFlowManager();
			inboundManager.process(otgffana, inputParam);
		}
	}
	
	private static Otgffana resubmit(Otgffana otgffana, String resubmitTransactionId) throws Exception {
		if (!Constants.SI.equals(otgffana.getFana_Risottomettibile())) {
			throw new ParameterException("Transaction not resubmittable");
		}
		
		Otgffana originalOtgffana = OtgfflogtDAO.readForResubmit(new BigDecimal(resubmitTransactionId));
		//TODO COMPLETE MERGE ACTIONS
		originalOtgffana.setFana_Risottomettibile(otgffana.getFana_Risottomettibile());
		
		return originalOtgffana;
		
	}
}
