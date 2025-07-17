package it.dmsoft.flowmanager.agent.engine.core.operations;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import it.dmsoft.flowmanager.agent.engine.core.exception.OperationException;
import it.dmsoft.flowmanager.agent.engine.core.flow.builder.FlowBuilder.ZipOperation;
import it.dmsoft.flowmanager.agent.engine.core.operations.core.ConstraintDependentOperation;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.ZipParam;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.OperationType;
import it.dmsoft.flowmanager.agent.engine.core.utils.ExceptionUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.LogDb;
import it.dmsoft.flowmanager.agent.engine.core.utils.StringUtils;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.ResponseWrapper;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;
import it.dmsoft.flowmanager.agent.engine.zip.ZipManager;
import it.dmsoft.flowmanager.agent.engine.zip.model.ZipResponse;

public class CreateZip extends ConstraintDependentOperation<ZipParam, ResponseWrapper<ZipResponse>> {

	private static final Logger logger = Logger.getLogger(CreateZip.class.getName());

	//TODO IDEA DI SPOSTARE IL SETTAGGIO DEI PARAMETRI ALL'INTERNO DI OGNI OPERAZIONE (PRATICAMENTE I FLOW BUILDER SPARIREBEBRO)
	@Override
	public void updateParameters() throws Exception {		
		if (Constants.INBOUND.equals(otgffana.getFana_Direzione())) {
			setFiles(operationParams.getRemoteTrasmissionFiles());					
		}		
	}
	
	private void setFiles(List<String> fileNames) {
		List<String> sourceFiles = new ArrayList<String>();
		List<String> destinationFiles = new ArrayList<String>();
		
		for (String fileName : operationParams.getTrasmissionFiles()) {
			sourceFiles.add(fileName);
			String destinationFile = otgffana.getFana_Folder();
			
			if(ZipOperation.ZIP.equals(parameters.getOperation())) {
				destinationFile += Constants.PATH_DELIMITER + StringUtils.removePath(fileName)
						+ Constants.ZIP_EXTENSION;
			}
			
			logger.info("Addind to Zip " + fileName + " -> " + destinationFile);
			destinationFiles.add(destinationFile);
		}
		
		parameters.setSourceFiles(sourceFiles);
		parameters.setDestinationFiles(destinationFiles);
		operationParams.setZipFiles(destinationFiles);
	}

	@Override
	public ResponseWrapper<ZipResponse> executeOperation() throws Exception {
		try {			
			ResponseWrapper<ZipResponse> resp = null;
			logger.info("start execution of " + CreateZip.class.getName());
			logger.info("parameters: " + parameters.toString());
			LogDb.start(OperationType.ZIP);

			for (int index = 0; index < parameters.getDestinationFiles().size(); index++) {
				String destinationPath = parameters.getDestinationFiles().get(index);
				String backupFile = parameters.getSourceFiles().get(index);

				File f = new File(destinationPath);
				if (!f.exists() && !f.isDirectory()) {
					f.createNewFile();

				}

				resp = ZipManager.exposedRun(null, null,
						backupFile, destinationPath, parameters.getOperation().getCode());
				ExceptionUtils.throwExceptionIfNecessary(resp);
				logger.info("Zip file has been created");

			}

			logger.info("end execution of " + CreateZip.class.getName());
			LogDb.end(OperationType.ZIP);
			
			return resp;
		} catch (OperationException e1) {
			throw e1;
		} catch (Throwable e) {
			throw new Exception("Error on zip execution: ", e);
		}
		
	}

	@Override
	public void updateOperationParams(ResponseWrapper<ZipResponse> data) throws Exception {
		if (Constants.INBOUND.equals(otgffana.getFana_Direzione())) {
			operationParams.setBackupFiles(operationParams.getZipFiles());
			List<String> deleteFiles = operationParams.getDeleteFiles() != null ? operationParams.getDeleteFiles() 
					: new ArrayList<String>(operationParams.getZipFiles().size());
			
			deleteFiles.addAll(ZipOperation.ZIP.equals(parameters.getOperation()) ? operationParams.getZipFiles() : parameters.getSourceFiles());
			
			for (String file : deleteFiles) {
				logger.info("Zip Delete File -> " + file);
			}
			
			operationParams.setDeleteFiles(deleteFiles);
		}
		
	}

}
