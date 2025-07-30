package it.dmsoft.flowmanager.agent.engine.core.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import it.dmsoft.flowmanager.be.repositories.ScheduleDateRepository;
import it.dmsoft.flowmanager.common.domain.Domains.YesNo;
import it.dmsoft.flowmanager.agent.engine.core.flow.builder.FlowBuilder;
import it.dmsoft.flowmanager.agent.engine.core.flow.builder.FlowBuilder.ZipOperation;
import it.dmsoft.flowmanager.agent.engine.core.flow.builder.OutboundFlowBuilder;
import it.dmsoft.flowmanager.agent.engine.core.model.ExecutionFlowData;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.OperationParams;
import it.dmsoft.flowmanager.agent.engine.core.properties.PropertiesUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.FlowIdNumeratorUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.FormatUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service("outboundFlowManager")
public class OutboundFlowManager extends FlowManager {
	
	public OutboundFlowManager(ScheduleDateRepository scheduleDateRepository) {
		super(scheduleDateRepository);
		// TODO Auto-generated constructor stub
	}

	public void handleOuputFileNames(OutboundFlowBuilder outboundFlowBuilder, ExecutionFlowData executionFlowData, OperationParams operationParams) throws Exception {
		//CERCO FILE SU IFS
		if(Constants.IFS.equals(executionFlowData.getFlowTipFlusso())) {
			FlowBuilder readOutboundFlowBuilder = new FlowBuilder();
			readOutboundFlowBuilder.readFileNames(executionFlowData, operationParams);
			readOutboundFlowBuilder.build().execute();
			
			if (Constants.OUTBOUND.equals(executionFlowData.getFlowDirezione()) 
					&& executionFlowData.getFlowRemoteFileName().matches(Constants.REGEXP_WILDCARD + Constants.$PR__REGEXP + Constants.REGEXP_WILDCARD)) {
				
				BigDecimal executionDate8 = FormatUtils.date(operationParams.getExecutionDate());
				BigDecimal startProgr = FlowIdNumeratorUtils.getNextId(executionFlowData.getFlowId(), executionDate8, 
						BigDecimal.valueOf(operationParams.getFileNames().size()), entityManager);
				operationParams.setDayStartProgressiveIndex(startProgr);				
				outboundFlowBuilder.addProgressiveSequenceId(executionFlowData, operationParams);			
			} 
		} else if (Constants.SPOOL.equals(executionFlowData.getFlowTipFlusso()) && Constants.OUTBOUND.equals(executionFlowData.getFlowDirezione())){
			FlowBuilder readOutboundFlowBuilder = new FlowBuilder();
			readOutboundFlowBuilder.readSpoolFiles(executionFlowData, operationParams);
			readOutboundFlowBuilder.build().execute();
			
			
		} else if (Constants.DB2.equals(executionFlowData.getFlowTipFlusso())) {
			outboundFlowBuilder.addDbProgressiveSequenceId(executionFlowData, operationParams);
		} 
		
	}

	@Override
	public void process(ExecutionFlowData executionFlowData, OperationParams operationParams) throws Exception {
		super.process(executionFlowData, operationParams);
		OutboundFlowBuilder outboundFlowBuilder = new OutboundFlowBuilder();
		
		String fileListFolder = PropertiesUtils.get(Constants.FILE_LIST_FOLDER);
		fileListFolder = StringUtils.isNullOrEmpty(fileListFolder) ? executionFlowData.getFlowFolder() : fileListFolder;
		operationParams.setListFileFolder(fileListFolder);
		
		handleOuputFileNames(outboundFlowBuilder, executionFlowData, operationParams);
			
		if(YesNo.YES.equals(executionFlowData.getFlowCreaVuoto()) ) {
			if(Constants.DB2.equals(executionFlowData.getFlowTipFlusso()) && !YesNo.YES.equals(operationParams.getLegacyModernization())) {
				outboundFlowBuilder.crtDb2FileIfNotExist(executionFlowData, operationParams);
			} else if (Constants.DB2.equals(executionFlowData.getFlowTipFlusso()) && YesNo.YES.equals(operationParams.getLegacyModernization())) {
				System.out.println("impossibile creare db vuoto in questa modalità");
			}
			else {
				outboundFlowBuilder.createIfsFile(executionFlowData, operationParams);
			}
		}
		
		// converto opzionale - indica se devo esportare da db
		boolean bypassConversion = /*operationParams.isBypassConversion() ||*/
									StringUtils.isNullOrEmpty(executionFlowData.getFlowFile()) ||
									 !Constants.DB2.equals(executionFlowData.getFlowTipFlusso());

		if (!bypassConversion) {

			operationParams.setLibrary(executionFlowData.getFlowLibreria());
			operationParams.setMemberOptionAddReplace(executionFlowData.getFlowModAcquisizione());
			
			//eseguo i metodi per gli export se configurato un export sul flusso
			if (!StringUtils.isNullOrEmpty(executionFlowData.getFlowExportCode()) 
						&& Optional.ofNullable(executionFlowData.getFlowAggNomiCol()).filter(Constants.EXF::equals).isPresent()) {
				outboundFlowBuilder.selectFileColumns(executionFlowData, operationParams);
			}
			
			if (YesNo.YES.equals(executionFlowData.getFlowEsistenzaFile())) {
				outboundFlowBuilder.checkDb2Obj(executionFlowData, operationParams);
				outboundFlowBuilder.checkDbFileEmpty(executionFlowData, operationParams);
			}
			outboundFlowBuilder.conversion(executionFlowData, operationParams);
		}

		// Verifica hash unico
	    if (YesNo.YES.equals(executionFlowData.getFlowHashUnico())) {
	        outboundFlowBuilder.checkHashFile(executionFlowData, operationParams, false);
	    }
		
		// TODO creazione file TEST
		//outboundFlowBuilder.readFileNames(executionFlowData, operationParams);
		
	    if (YesNo.YES.equals(executionFlowData.getFlowCompression())
	    		/*|| Constants.SPEDIZIONE.equals(executionFlowData.getFlowCompression())*/) {	    	
		   outboundFlowBuilder.zipOperation(executionFlowData, operationParams, ZipOperation.ZIP); 
		}
		 

		// create File Semaforo
		outboundFlowBuilder.createFile(executionFlowData, operationParams);

		if (!StringUtils.isNullOrEmpty(executionFlowData.getFlowTipoTrasferimento())) {
			outboundFlowBuilder.trasmit(executionFlowData, operationParams);
		}

		// Scrivo hash unico
	    if (YesNo.YES.equals(executionFlowData.getFlowHashUnico())) {
	        outboundFlowBuilder.checkHashFile(executionFlowData, operationParams, true);
	    }
		
		if (executionFlowData.getFlowRetention() != null && BigDecimal.ZERO.compareTo(executionFlowData.getFlowRetention()) < 0) {
			// operationParams.setBackupFolder(StringUtils.setDefault(executionFlowData.getFlowBackup(),
			// operationParams.getBackupFolder()));
			operationParams
					.setPathBackup(StringUtils.setDefault(executionFlowData.getFlowBackup(), operationParams.getPathBackup()));
			outboundFlowBuilder.createBackup(executionFlowData, operationParams);
		}

		List<String> deleteFiles = new ArrayList<String>();

		if (Constants.IFS.equals(executionFlowData.getFlowTipFlusso()) && Constants.SPOOL.equals(executionFlowData.getFlowTipFlusso())) {
			deleteFiles.add(operationParams.getListFileFolder() + Constants.PATH_DELIMITER +  operationParams.getListFile());
		}
		
		if (YesNo.YES.equals(executionFlowData.getFlowCancellaFile())) {
			for(String fileName: operationParams.getFileNames())
			{
				deleteFiles.add(executionFlowData.getFlowFolder() + Constants.PATH_DELIMITER + fileName);
			}
			
		}

		if (!YesNo.NO.equals(executionFlowData.getFlowCompression())) {
			for(String fileName: operationParams.getBackupFiles())
			{
				deleteFiles.add(executionFlowData.getFlowFolder() + Constants.PATH_DELIMITER + fileName);
			}
			
		}

		if (YesNo.YES.equals(executionFlowData.getFlowIntegrityCheck())
				&& !StringUtils.isNullOrEmpty(executionFlowData.getFlowFlNameSemaforo())) {
			deleteFiles.add(executionFlowData.getFlowFolder() + Constants.PATH_DELIMITER + operationParams.getSempahoreFile());
		}

		outboundFlowBuilder.deleteSources(deleteFiles, executionFlowData, operationParams);
		
		if(!StringUtils.isNullOrEmpty(executionFlowData.getFlowExportCode()) 
				&& Constants.DB2.equals(executionFlowData.getFlowTipFlusso())
				&& Optional.ofNullable(executionFlowData.getFlowAggNomiCol()).filter(Constants.EXF::equals).isPresent()){
			outboundFlowBuilder.dropTmpExportTable(executionFlowData, operationParams);
		}
		
		super.completeProcess(outboundFlowBuilder, executionFlowData, operationParams);

	}

}
