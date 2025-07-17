package it.dmsoft.flowmanager.agent.engine.core.operations.core;

import java.util.List;

import it.dmsoft.flowmanager.agent.engine.core.exception.OperationException;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.TrasmissionParams;
import it.dmsoft.flowmanager.agent.engine.core.utils.LogDb;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.OperationType;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public abstract class ConstraintTrasmission<V> extends ConstraintOperation<TrasmissionParams, V> {
	
	private static Logger logger = Logger.getLogger(ConstraintTrasmission.class);
		
	@Override
	public V executeOperation() throws Exception {
		logger.info("Start of execute constraint trasmission");
		Exception lastExc = null;
		
		int retryCount = parameters.getRetryCount().intValue();
		for(int i=0; i < retryCount + 1;i++) {
			
			try {
				logger.info("Start of execute trasmission");
				if (i > 0) {
					logger.info("Waiting Retry interval");
					Thread.sleep(parameters.getRetryIntervall().intValue() * 1000);
				}
				
				return executeTrasmission();
			} catch(Exception e) {
				lastExc = e;
				handleError(e);				
			}
		}
		
		//BYPASS LAUNCH OF EXCEPTION IN CASE OF NO SUCH FILE 
		if (lastExc == null  || (!parameters.isLaunchErrorIfNoFileFound() && getNoSuchFileException().equals(lastExc))) {
			return null;
		}
		
		throw lastExc;
	}
	
	private void handleError(Exception e) throws Exception{
		logger.error("Error on trasmission execution:", e);
		LogDb.end(OperationType.TRASM_KO);		
	}
	
	public void launchExceptionIfNoFileFound(List<?> downloadFiles) throws OperationException {
		if (parameters.isLaunchErrorIfNoFileFound() && (downloadFiles == null || downloadFiles.size() == 0)) {
			launchNoSuchFileException();
		}
	}
	
	public void launchNoSuchFileException() throws OperationException {
		throw getNoSuchFileException();
	}
	
	public OperationException getNoSuchFileException() {
		return new OperationException("No such file");
	}
	
	public abstract V executeTrasmission() throws Exception;
		
}
