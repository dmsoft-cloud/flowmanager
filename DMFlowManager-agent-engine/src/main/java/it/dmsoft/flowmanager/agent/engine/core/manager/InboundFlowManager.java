package it.dmsoft.flowmanager.agent.engine.core.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import it.dmsoft.flowmanager.agent.engine.core.db.dao.DbConstants;
import it.dmsoft.flowmanager.agent.engine.core.db.dao.OtgffhashDAO;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.Otgffana;
import it.dmsoft.flowmanager.agent.engine.core.exception.OperationException;
import it.dmsoft.flowmanager.agent.engine.core.flow.builder.FlowBuilder.ConversionOperation;
import it.dmsoft.flowmanager.agent.engine.core.flow.builder.FlowBuilder.ZipOperation;
import it.dmsoft.flowmanager.agent.engine.core.flow.builder.InboundFlowBuilder;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.OperationParams;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.HashUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.StringUtils;

public class InboundFlowManager extends FlowManager {

	@Override
	public void process(Otgffana otgffana, OperationParams operationParams) throws Exception {
		InboundFlowBuilder inboundFlowBuilder = new InboundFlowBuilder();
		
		super.process(otgffana, operationParams);
		
		// ricezione
		if (!StringUtils.isNullOrEmpty(otgffana.getFana_Tipo_Trasferimento())) {
			inboundFlowBuilder.trasmit(otgffana, operationParams);
		}

		// unzippo
		if (Constants.SI.equals(otgffana.getFana_De_Compression())) {
			// TODO SISTEMARE FLOWBUILDER UNZIP done
			inboundFlowBuilder.zipOperation(otgffana, operationParams, ZipOperation.UNZIP);

		}
		
		 // Verifica hash unico
	    if (Constants.SI.equals(otgffana.getFana_Hash_Unico())) {
	        inboundFlowBuilder.checkHashFile(otgffana, operationParams, false);
	    }

		// converto opzionale
		boolean bypassConversion = operationParams.isBypassConversion() ||
									StringUtils.isNullOrEmpty(otgffana.getFana_File()) ||
									 !Constants.DB2.equals(otgffana.getFana_Tip_Flusso());
		
		
		
		if (!bypassConversion) {
			
			// verifico se file vuoto su ifs e se devo forzare ok saltando step successivi
			// vado a forzare il parametro bypassConversion a true in esecuzione del flow 
			// se file vuoto
			if(Constants.SI.equals(otgffana.getFana_Flag_Ok_Vuoto()) && 
					Constants.DB2.equals(otgffana.getFana_Tip_Flusso()) ) {
				inboundFlowBuilder.checkIfsFileEmpty(otgffana, operationParams);
				
			}
			
			operationParams.setMemberOptionAddReplace(otgffana.getFana_Mod_Acquisizione());
			inboundFlowBuilder.conversionToDestFile(otgffana, operationParams);

			// TRAVASO IN FILE DESTINAZIONE
			if(!Constants.SI.equals(otgffana.getFana_Bypass_Qtemp()) 
					&& !Optional.ofNullable(DbConstants.REMOTE_HOST).filter(Constants.SI::equals).isPresent()
					&& ConversionOperation.CSV.name().equals(otgffana.getFana_Formato())) {
				inboundFlowBuilder.fromQtempToDestinationFileOperation(otgffana, operationParams);
				inboundFlowBuilder.cpyFile(otgffana, operationParams);
			}
			
			if (Constants.SI.equals(otgffana.getFana_Esistenza_File())) {
				inboundFlowBuilder.checkDb2Obj(otgffana, operationParams);
				inboundFlowBuilder.checkDbFileEmpty(otgffana, operationParams);
			}

		}
		
		 // Scrivo hash unico
	    if (Constants.SI.equals(otgffana.getFana_Hash_Unico())) {
	        inboundFlowBuilder.checkHashFile(otgffana, operationParams, true);
	    }

		// zippo
		if (Constants.SI.equals(otgffana.getFana_Compression())) {
			inboundFlowBuilder.zipOperation(otgffana, operationParams, ZipOperation.ZIP);
		}

		if (BigDecimal.ZERO.compareTo(otgffana.getFana_Retention()) < 0) {
			operationParams
					.setPathBackup(StringUtils.setDefault(otgffana.getFana_Backup(), operationParams.getPathBackup()));
			inboundFlowBuilder.createBackup(otgffana, operationParams);

		}

		inboundFlowBuilder.deleteSources(getDeleteList(otgffana, operationParams), otgffana, operationParams);

		super.completeProcess(inboundFlowBuilder, otgffana, operationParams);

	}
	
	private List<String> getDeleteFileNames(String trasmissionFile, Otgffana otgffana, OperationParams operationParams) {
		List<String> deleteFiles = new ArrayList<String>();

		if (!StringUtils.isNullOrEmpty(trasmissionFile)) {
			if (Constants.DB2.equals(otgffana.getFana_Tip_Flusso())
					&& Constants.SI.equals(otgffana.getFana_Cancella_File())) {
				deleteFiles.add(otgffana.getFana_Folder() + Constants.PATH_DELIMITER + trasmissionFile);
			}
			
			if (!Constants.NO.equals(otgffana.getFana_Compression())) {
				deleteFiles.add(otgffana.getFana_Folder() + Constants.PATH_DELIMITER + trasmissionFile
						+ Constants.ZIP_EXTENSION);
			}
			
			if (Constants.SI.equals(otgffana.getFana_De_Compression())) {
				deleteFiles.add(otgffana.getFana_Folder() + Constants.PATH_DELIMITER + trasmissionFile);
			}
		}

		if (Constants.SI.equals(otgffana.getFana_Intergiry_Check())
				&& !StringUtils.isNullOrEmpty(otgffana.getFana_Fl_Name_Semaforo())) {
			deleteFiles.add(otgffana.getFana_Folder() + Constants.PATH_DELIMITER + operationParams.getSempahoreFile());
		}
		
		return deleteFiles;
	}
	
	private List<String> getDeleteList(Otgffana otgffana, OperationParams operationParams) {
		List<String> deleteFiles = new ArrayList<String>();

		for (String trasmissionFile : operationParams.getTrasmissionFiles()) {
			deleteFiles.addAll(getDeleteFileNames(trasmissionFile, otgffana, operationParams));
		}
		
		return deleteFiles;
	}

}
