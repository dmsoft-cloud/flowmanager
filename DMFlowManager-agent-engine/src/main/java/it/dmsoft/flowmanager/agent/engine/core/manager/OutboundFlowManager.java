package it.dmsoft.flowmanager.agent.engine.core.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import it.dmsoft.flowmanager.agent.engine.core.db.dao.DbConstants;
import it.dmsoft.flowmanager.agent.engine.core.db.dao.OtgffprogDAO;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.Otgffana;
import it.dmsoft.flowmanager.agent.engine.core.flow.builder.FlowBuilder;
import it.dmsoft.flowmanager.agent.engine.core.flow.builder.FlowBuilder.ZipOperation;
import it.dmsoft.flowmanager.agent.engine.core.flow.builder.OutboundFlowBuilder;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.OperationParams;
import it.dmsoft.flowmanager.agent.engine.core.properties.PropertiesUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.FormatUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.StringUtils;

public class OutboundFlowManager extends FlowManager {
	
	
	public void handleOuputFileNames(OutboundFlowBuilder outboundFlowBuilder, Otgffana otgffana, OperationParams operationParams) throws Exception {
		//CERCO FILE SU IFS
		if(Constants.IFS.equals(otgffana.getFana_Tip_Flusso())) {
			FlowBuilder readOutboundFlowBuilder = new FlowBuilder();
			readOutboundFlowBuilder.readFileNames(otgffana, operationParams);
			readOutboundFlowBuilder.build().execute();
			
			if (Constants.OUTBOUND.equals(otgffana.getFana_Direzione()) 
					&& otgffana.getFana_Remote_File_Name().matches(Constants.REGEXP_WILDCARD + Constants.$PR__REGEXP + Constants.REGEXP_WILDCARD)) {
				
				BigDecimal executionDate8 = FormatUtils.date(operationParams.getExecutionDate());
				BigDecimal startProgr = OtgffprogDAO.getNextProgressive(otgffana.getFana_Id(), executionDate8, 
						BigDecimal.valueOf(operationParams.getFileNames().size()));
				operationParams.setDayStartProgressiveIndex(startProgr);				
				outboundFlowBuilder.addProgressiveSequenceId(otgffana, operationParams);			
			} 
		} else if (Constants.SPOOL.equals(otgffana.getFana_Tip_Flusso()) && Constants.OUTBOUND.equals(otgffana.getFana_Direzione())){
			FlowBuilder readOutboundFlowBuilder = new FlowBuilder();
			readOutboundFlowBuilder.readSpoolFiles(otgffana, operationParams);
			readOutboundFlowBuilder.build().execute();
			
			
		} else if (Constants.DB2.equals(otgffana.getFana_Tip_Flusso())) {
			outboundFlowBuilder.addDbProgressiveSequenceId(otgffana, operationParams);
		} 
		
	}

	@Override
	public void process(Otgffana otgffana, OperationParams operationParams) throws Exception {
		super.process(otgffana, operationParams);
		OutboundFlowBuilder outboundFlowBuilder = new OutboundFlowBuilder();
		
		String fileListFolder = PropertiesUtils.get(Constants.FILE_LIST_FOLDER);
		fileListFolder = StringUtils.isNullOrEmpty(fileListFolder) ? otgffana.getFana_Folder() : fileListFolder;
		operationParams.setListFileFolder(fileListFolder);
		
		handleOuputFileNames(outboundFlowBuilder, otgffana, operationParams);
			
		if(Constants.SI.equals(otgffana.getFana_Crea_Vuoto()) ) {
			if(Constants.DB2.equals(otgffana.getFana_Tip_Flusso()) && !Constants.SI.equals(operationParams.getLegacyModernization())) {
				outboundFlowBuilder.crtDb2FileIfNotExist(otgffana, operationParams);
			} else if (Constants.DB2.equals(otgffana.getFana_Tip_Flusso()) && Constants.SI.equals(operationParams.getLegacyModernization())) {
				System.out.println("impossibile creare db vuoto in questa modalità");
			}
			else {
				outboundFlowBuilder.createIfsFile(otgffana, operationParams);
			}
		}
		
		// converto opzionale - indica se devo esportare da db
		boolean bypassConversion = /*operationParams.isBypassConversion() ||*/
									StringUtils.isNullOrEmpty(otgffana.getFana_File()) ||
									 !Constants.DB2.equals(otgffana.getFana_Tip_Flusso());

		if (!bypassConversion) {

			operationParams.setLibrary(otgffana.getFana_Libreria());
			operationParams.setMemberOptionAddReplace(otgffana.getFana_Mod_Acquisizione());
			
			//eseguo i metodi per gli export se configurato un export sul flusso
			if (!StringUtils.isNullOrEmpty(otgffana.getFana_Export_Code()) 
						&& Optional.ofNullable(otgffana.getFana_Agg_Nomi_Col()).filter(Constants.EXF::equals).isPresent()) {
				outboundFlowBuilder.selectFileColumns(otgffana, operationParams);
			}
			
			if (Constants.SI.equals(otgffana.getFana_Esistenza_File())) {
				outboundFlowBuilder.checkDb2Obj(otgffana, operationParams);
				outboundFlowBuilder.checkDbFileEmpty(otgffana, operationParams);
			}
			outboundFlowBuilder.conversion(otgffana, operationParams);
		}

		// Verifica hash unico
	    if (Constants.SI.equals(otgffana.getFana_Hash_Unico())) {
	        outboundFlowBuilder.checkHashFile(otgffana, operationParams, false);
	    }
		
		// TODO creazione file TEST
		//outboundFlowBuilder.readFileNames(otgffana, operationParams);
		
	    if (Constants.SI.equals(otgffana.getFana_Compression())
	    		|| Constants.SPEDIZIONE.equals(otgffana.getFana_Compression())) {	    	
		   outboundFlowBuilder.zipOperation(otgffana, operationParams, ZipOperation.ZIP); 
		}
		 

		// create File Semaforo
		outboundFlowBuilder.createFile(otgffana, operationParams);

		if (!StringUtils.isNullOrEmpty(otgffana.getFana_Tipo_Trasferimento())) {
			outboundFlowBuilder.trasmit(otgffana, operationParams);
		}

		// Scrivo hash unico
	    if (Constants.SI.equals(otgffana.getFana_Hash_Unico())) {
	        outboundFlowBuilder.checkHashFile(otgffana, operationParams, true);
	    }
		
		if (BigDecimal.ZERO.compareTo(otgffana.getFana_Retention()) < 0) {
			// operationParams.setBackupFolder(StringUtils.setDefault(otgffana.getFana_Backup(),
			// operationParams.getBackupFolder()));
			operationParams
					.setPathBackup(StringUtils.setDefault(otgffana.getFana_Backup(), operationParams.getPathBackup()));
			outboundFlowBuilder.createBackup(otgffana, operationParams);
		}

		List<String> deleteFiles = new ArrayList<String>();

		if (Constants.IFS.equals(otgffana.getFana_Tip_Flusso()) && Constants.SPOOL.equals(otgffana.getFana_Tip_Flusso())) {
			deleteFiles.add(operationParams.getListFileFolder() + Constants.PATH_DELIMITER +  operationParams.getListFile());
		}
		
		if (Constants.SI.equals(otgffana.getFana_Cancella_File())) {
			for(String fileName: operationParams.getFileNames())
			{
				deleteFiles.add(otgffana.getFana_Folder() + Constants.PATH_DELIMITER + fileName);
			}
			
		}

		if (!Constants.NO.equals(otgffana.getFana_Compression())) {
			for(String fileName: operationParams.getBackupFiles())
			{
				deleteFiles.add(otgffana.getFana_Folder() + Constants.PATH_DELIMITER + fileName);
			}
			
		}

		if (Constants.SI.equals(otgffana.getFana_Intergiry_Check())
				&& !StringUtils.isNullOrEmpty(otgffana.getFana_Fl_Name_Semaforo())) {
			deleteFiles.add(otgffana.getFana_Folder() + Constants.PATH_DELIMITER + operationParams.getSempahoreFile());
		}

		outboundFlowBuilder.deleteSources(deleteFiles, otgffana, operationParams);
		
		if(!StringUtils.isNullOrEmpty(otgffana.getFana_Export_Code()) 
				&& Constants.DB2.equals(otgffana.getFana_Tip_Flusso())
				&& Optional.ofNullable(otgffana.getFana_Agg_Nomi_Col()).filter(Constants.EXF::equals).isPresent()){
			outboundFlowBuilder.dropTmpExportTable(otgffana, operationParams);
		}
		
		super.completeProcess(outboundFlowBuilder, otgffana, operationParams);

	}

}
