package it.dmsoft.flowmanager.agent.engine.core.operations.core;

import java.util.List;

import it.dmsoft.flowmanager.agent.engine.core.exception.OperationException;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.TrasmissionParams;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.OperationType;
import it.dmsoft.flowmanager.agent.engine.core.utils.FlowLogUtils;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public abstract class Trasmission extends Operation<TrasmissionParams>{
	
	private static Logger logger = Logger.getLogger(Trasmission.class);
	
	@Override
	public final void execute() throws Exception {
		logger.info("Start of execute constraint trasmission");
		Exception lastExc = null;
		for(int i=0; i < parameters.getRetryCount().intValue() + 1;i++) {
			
			try {
				logger.info("Start of execute trasmission");
				executeTrasmission();
				return;
			} catch(Exception e) {
				lastExc = e;
				handleError(e);
			}
		}
		
		//BYPASS LAUNCH OF EXCEPTION IN CASE OF NO SUCH FILE 
		if (lastExc == null  || (!parameters.isLaunchErrorIfNoFileFound() && getNoSuchFileException().equals(lastExc))) {
			return;
		}
		
		throw lastExc;
		
	}
		
	private void handleError(Exception e) throws Exception{
		logger.error("Error on trasmission execution:", e);
		FlowLogUtils.endDetail(OperationType.TRASM_KO);
		Thread.sleep(parameters.getRetryIntervall().intValue() * 1000);
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
	
	public abstract void executeTrasmission() throws Exception;
	
}
