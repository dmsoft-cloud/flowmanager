package it.dmsoft.flowmanager.agent.engine.core.operations;

import java.io.File;
import java.io.IOException;

import it.dmsoft.flowmanager.agent.engine.core.operations.core.DependentOperation;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.DeleteFileParam;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.OperationType;
import it.dmsoft.flowmanager.agent.engine.core.utils.LogDb;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class DeleteFile extends DependentOperation<DeleteFileParam> {

	private static final Logger logger = Logger.getLogger(DeleteFile.class.getName());

	private void deleteFile(String source) throws Exception {
		LogDb.start(OperationType.DLT_FILE);
		
		File sourceFile= new File(source);
		
		if (sourceFile.exists()) {
			 if (sourceFile.delete()) logger.info("File deleted : " + source);
			 else throw new IOException("Error deleting file: " + source);
		} else {
			logger.info("File not found : " + source);
		}
		
		LogDb.end(OperationType.DLT_FILE);
	}

	@Override
	public void updateParameters() throws Exception {
		if (operationParams.getDeleteFiles() != null) {
			parameters.getSources().addAll(operationParams.getDeleteFiles());
		}
	}

	@Override
	public void executeOperation() throws Exception {
		logger.info("start execution of " + DeleteFile.class.getName());
		logger.info("parameters: " + parameters.toString());
		
		for (String source : parameters.getSources()) {
			deleteFile(source);
		}
		
		logger.info("end execution of " + DeleteFile.class.getName());		
	}

}
