package it.dmsoft.flowmanager.agent.engine.core.operations.core;

import it.dmsoft.flowmanager.agent.engine.core.operations.params.OperationParams;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public abstract class ConstraintOperation<T, V> extends Operation<T>{	
	
	private static Logger logger = Logger.getLogger(ConstraintOperation.class);
	
	protected OperationParams operationParams;
	
	@Override
	public final void execute() throws Exception {	
		logger.info("Start of execute Operations");
		V ret = executeOperation();
		logger.info("End of execute Operations");
		logger.info("Start of update Parameters");
		updateOperationParams(ret);		
		logger.info("End of update Parameters");
	}

	public void setOperationParams(OperationParams operationParams) {
		this.operationParams = operationParams;
	}
	
	public abstract void updateOperationParams(V data) throws Exception;
	
	public abstract V executeOperation() throws Exception;
	
	
	
}
