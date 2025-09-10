package it.dmsoft.flowmanager.agent.engine.core.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import it.dmsoft.flowmanager.agent.engine.core.flow.builder.FlowBuilder.ConversionOperation;
import it.dmsoft.flowmanager.agent.engine.core.flow.builder.FlowBuilder.ZipOperation;
import it.dmsoft.flowmanager.agent.engine.core.flow.builder.InboundFlowBuilder;
import it.dmsoft.flowmanager.agent.engine.core.model.ExecutionFlowData;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.OperationParams;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.StringUtils;
import it.dmsoft.flowmanager.common.domain.Domains.Type;
import it.dmsoft.flowmanager.common.domain.Domains.YesNo;

@Service("inboundFlowManager")
public class InboundFlowManager extends FlowManager {

	@Override
	public void process(ExecutionFlowData executionFlowData, OperationParams operationParams) throws Exception {
		InboundFlowBuilder inboundFlowBuilder = (InboundFlowBuilder) applicationContext.getBean("inboundFlowBuilder");
		
		super.process(executionFlowData, operationParams);
		
		// ricezione
		if (executionFlowData.getFlowTipoTrasferimento() != null) {
			inboundFlowBuilder.trasmit(executionFlowData, operationParams);
		}

		// unzippo
		if (YesNo.YES.equals(executionFlowData.getFlowDeCompression())) {
			// TODO SISTEMARE FLOWBUILDER UNZIP done
			inboundFlowBuilder.zipOperation(executionFlowData, operationParams, ZipOperation.UNZIP);

		}
		
		 // Verifica hash unico
	    if (YesNo.YES.equals(executionFlowData.getFlowHashUnico())) {
	        inboundFlowBuilder.checkHashFile(executionFlowData, operationParams, false);
	    }

		// converto opzionale
		boolean bypassConversion = operationParams.isBypassConversion() ||
									StringUtils.isNullOrEmpty(executionFlowData.getFlowFile()) ||
									 !Type.ORIGIN.equals(executionFlowData.getFlowTipFlusso());
		
		
		
		if (!bypassConversion) {
			
			// verifico se file vuoto su ifs e se devo forzare ok saltando step successivi
			// vado a forzare il parametro bypassConversion a true in esecuzione del flow 
			// se file vuoto
			if(YesNo.YES.equals(executionFlowData.getFlowFlagOkVuoto()) && 
					Type.ORIGIN.equals(executionFlowData.getFlowTipFlusso()) ) {
				inboundFlowBuilder.checkIfsFileEmpty(executionFlowData, operationParams);
				
			}
			
			operationParams.setMemberOptionAddReplace(executionFlowData.getFlowModAcquisizione());
			inboundFlowBuilder.conversionToDestFile(executionFlowData, operationParams);

			// TRAVASO IN FILE DESTINAZIONE
			if(!YesNo.YES.equals(executionFlowData.getFlowBypassQtemp()) 
					&& YesNo.YES.equals(operationParams.getIBMi())
					&& ConversionOperation.CSV.name().equals(executionFlowData.getFlowFormato())) {
				inboundFlowBuilder.fromQtempToDestinationFileOperation(executionFlowData, operationParams);
				inboundFlowBuilder.cpyFile(executionFlowData, operationParams);
			}
			
			if (YesNo.YES.equals(executionFlowData.getFlowEsistenzaFile())) {
				inboundFlowBuilder.checkDb2Obj(executionFlowData, operationParams);
				inboundFlowBuilder.checkDbFileEmpty(executionFlowData, operationParams);
			}

		}
		
		 // Scrivo hash unico
	    if (YesNo.YES.equals(executionFlowData.getFlowHashUnico())) {
	        inboundFlowBuilder.checkHashFile(executionFlowData, operationParams, true);
	    }

		// zippo
		if (YesNo.YES.equals(executionFlowData.getFlowCompression())) {
			inboundFlowBuilder.zipOperation(executionFlowData, operationParams, ZipOperation.ZIP);
		}

		if (executionFlowData.getFlowRetention() != null && BigDecimal.ZERO.compareTo(executionFlowData.getFlowRetention()) < 0) {
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
			if (Type.ORIGIN.equals(executionFlowData.getFlowTipFlusso())
					&& YesNo.YES.equals(executionFlowData.getFlowCancellaFile())) {
				deleteFiles.add(executionFlowData.getFlowFolder() + Constants.PATH_DELIMITER + trasmissionFile);
			}
			
			if (!YesNo.NO.equals(executionFlowData.getFlowCompression())) {
				deleteFiles.add(executionFlowData.getFlowFolder() + Constants.PATH_DELIMITER + trasmissionFile
						+ Constants.ZIP_EXTENSION);
			}
			
			if (YesNo.YES.equals(executionFlowData.getFlowDeCompression())) {
				deleteFiles.add(executionFlowData.getFlowFolder() + Constants.PATH_DELIMITER + trasmissionFile);
			}
		}

		if (YesNo.YES.equals(executionFlowData.getFlowIntegrityCheck())
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
