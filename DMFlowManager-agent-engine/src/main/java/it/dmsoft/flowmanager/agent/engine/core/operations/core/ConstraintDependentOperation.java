package it.dmsoft.flowmanager.agent.engine.core.operations.core;

import it.dmsoft.flowmanager.agent.engine.core.model.ExecutionFlowData;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.OperationParams;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public abstract class ConstraintDependentOperation<T, V> extends Operation<T>{	
	
	private static Logger logger = Logger.getLogger(ConstraintOperation.class);
	
	protected OperationParams operationParams;
	
	protected ExecutionFlowData executionFlowData;
	
	@Override
	public void execute() throws Exception {
		logger.info("Start of update Parameters");
		updateParameters();
		logger.info("End of update Parameters");
		logger.info("Start of execute Operations");
		V data = executeOperation();
		logger.info("End of execute Operations");
		logger.info("Start of update OperationParams");
		updateOperationParams(data);
		logger.info("End of of update OperationParams");
		
	}
	
	protected OperationParams getOperationParams() {
		return operationParams;
	}

	public void setOperationParams(OperationParams operationParams) {
		this.operationParams = operationParams;
	}

	protected ExecutionFlowData getOtgffana() {
		return executionFlowData;
	}

	public void setOtgffana(ExecutionFlowData executionFlowData) {
		this.executionFlowData = executionFlowData;
	}
	
	public abstract void updateOperationParams(V data) throws Exception;
	
	public abstract V executeOperation() throws Exception;
	
	public abstract void updateParameters() throws Exception;
	
}
