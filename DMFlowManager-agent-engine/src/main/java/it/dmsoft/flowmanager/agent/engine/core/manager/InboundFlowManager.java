package it.dmsoft.flowmanager.agent.engine.core.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import it.dmsoft.flowmanager.agent.engine.core.db.DbConstants;
import it.dmsoft.flowmanager.agent.engine.core.flow.builder.FlowBuilder.ConversionOperation;
import it.dmsoft.flowmanager.agent.engine.core.flow.builder.FlowBuilder.ZipOperation;
import it.dmsoft.flowmanager.agent.engine.core.flow.builder.InboundFlowBuilder;
import it.dmsoft.flowmanager.agent.engine.core.model.ExecutionFlowData;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.OperationParams;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.StringUtils;

@Service("inboundFlowManager")
public class InboundFlowManager extends FlowManager {

	@Override
	public void process(ExecutionFlowData executionFlowData, OperationParams operationParams) throws Exception {
		InboundFlowBuilder inboundFlowBuilder = new InboundFlowBuilder();
		
		super.process(executionFlowData, operationParams);
		
		// ricezione
		if (!StringUtils.isNullOrEmpty(executionFlowData.getFlowTipoTrasferimento())) {
			inboundFlowBuilder.trasmit(executionFlowData, operationParams);
		}

		// unzippo
		if (Constants.SI.equals(executionFlowData.getFlowDeCompression())) {
			// TODO SISTEMARE FLOWBUILDER UNZIP done
			inboundFlowBuilder.zipOperation(executionFlowData, operationParams, ZipOperation.UNZIP);

		}
		
		 // Verifica hash unico
	    if (Constants.SI.equals(executionFlowData.getFlowHashUnico())) {
	        inboundFlowBuilder.checkHashFile(executionFlowData, operationParams, false);
	    }

		// converto opzionale
		boolean bypassConversion = operationParams.isBypassConversion() ||
									StringUtils.isNullOrEmpty(executionFlowData.getFlowFile()) ||
									 !Constants.DB2.equals(executionFlowData.getFlowTipFlusso());
		
		
		
		if (!bypassConversion) {
			
			// verifico se file vuoto su ifs e se devo forzare ok saltando step successivi
			// vado a forzare il parametro bypassConversion a true in esecuzione del flow 
			// se file vuoto
			if(Constants.SI.equals(executionFlowData.getFlowFlagOkVuoto()) && 
					Constants.DB2.equals(executionFlowData.getFlowTipFlusso()) ) {
				inboundFlowBuilder.checkIfsFileEmpty(executionFlowData, operationParams);
				
			}
			
			operationParams.setMemberOptionAddReplace(executionFlowData.getFlowModAcquisizione());
			inboundFlowBuilder.conversionToDestFile(executionFlowData, operationParams);

			// TRAVASO IN FILE DESTINAZIONE
			if(!Constants.SI.equals(executionFlowData.getFlowBypassQtemp()) 
					&& !Optional.ofNullable(DbConstants.REMOTE_HOST).filter(Constants.SI::equals).isPresent()
					&& ConversionOperation.CSV.name().equals(executionFlowData.getFlowFormato())) {
				inboundFlowBuilder.fromQtempToDestinationFileOperation(executionFlowData, operationParams);
				inboundFlowBuilder.cpyFile(executionFlowData, operationParams);
			}
			
			if (Constants.SI.equals(executionFlowData.getFlowEsistenzaFile())) {
				inboundFlowBuilder.checkDb2Obj(executionFlowData, operationParams);
				inboundFlowBuilder.checkDbFileEmpty(executionFlowData, operationParams);
			}

		}
		
		 // Scrivo hash unico
	    if (Constants.SI.equals(executionFlowData.getFlowHashUnico())) {
	        inboundFlowBuilder.checkHashFile(executionFlowData, operationParams, true);
	    }

		// zippo
		if (Constants.SI.equals(executionFlowData.getFlowCompression())) {
			inboundFlowBuilder.zipOperation(executionFlowData, operationParams, ZipOperation.ZIP);
		}

		if (BigDecimal.ZERO.compareTo(executionFlowData.getFlowRetention()) < 0) {
			operationParams
					.setPathBackup(StringUtils.setDefault(executionFlowData.getFlowBackup(), operationParams.getPathBackup()));
			inboundFlowBuilder.createBackup(executionFlowData, operationParams);

		}

		inboundFlowBuilder.deleteSources(getDeleteList(executionFlowData, operationParams), executionFlowData, operationParams);

		super.completeProcess(inboundFlowBuilder, executionFlowData, operationParams);

	}
	
	private List<String> getDeleteFileNames(String trasmissionFile, ExecutionFlowData executionFlowData, OperationParams operationParams) {
		List<String> deleteFiles = new ArrayList<String>();

		if (!StringUtils.isNullOrEmpty(trasmissionFile)) {
			if (Constants.DB2.equals(executionFlowData.getFlowTipFlusso())
					&& Constants.SI.equals(executionFlowData.getFlowCancellaFile())) {
				deleteFiles.add(executionFlowData.getFlowFolder() + Constants.PATH_DELIMITER + trasmissionFile);
			}
			
			if (!Constants.NO.equals(executionFlowData.getFlowCompression())) {
				deleteFiles.add(executionFlowData.getFlowFolder() + Constants.PATH_DELIMITER + trasmissionFile
						+ Constants.ZIP_EXTENSION);
			}
			
			if (Constants.SI.equals(executionFlowData.getFlowDeCompression())) {
				deleteFiles.add(executionFlowData.getFlowFolder() + Constants.PATH_DELIMITER + trasmissionFile);
			}
		}

		if (Constants.SI.equals(executionFlowData.getFlowIntergiryCheck())
				&& !StringUtils.isNullOrEmpty(executionFlowData.getFlowFlNameSemaforo())) {
			deleteFiles.add(executionFlowData.getFlowFolder() + Constants.PATH_DELIMITER + operationParams.getSempahoreFile());
		}
		
		return deleteFiles;
	}
	
	private List<String> getDeleteList(ExecutionFlowData executionFlowData, OperationParams operationParams) {
		List<String> deleteFiles = new ArrayList<String>();

		for (String trasmissionFile : operationParams.getTrasmissionFiles()) {
			deleteFiles.addAll(getDeleteFileNames(trasmissionFile, executionFlowData, operationParams));
		}
		
		return deleteFiles;
	}

}
