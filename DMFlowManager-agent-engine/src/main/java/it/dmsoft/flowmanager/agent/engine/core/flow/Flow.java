package it.dmsoft.flowmanager.agent.engine.core.flow;

import java.util.ArrayList;
import java.util.List;

import it.dmsoft.flowmanager.agent.engine.core.operations.core.Operation;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class Flow {
	
	private static final Logger logger = Logger.getLogger(Flow.class.getName());
	
	private List<Operation<?>> operations;
			
	public Flow() {
		super();
		operations = new ArrayList<Operation<?>>();
	}

	protected List<Operation<?>> getOperations() {
		return operations;
	}

	protected void setOperations(List<Operation<?>> operations) {
		this.operations = operations;
	}
	
	public void addOperation(Operation<?> operation) {
		this.operations.add(operation);
	}
	
	public void addOperations(List<Operation<?>> operations) {
		this.operations.addAll(operations);
	}
	
	public void execute() throws Exception {
		logger.info("Start to Execute operations");
		if (operations == null) {
			return;
		}
		for (Operation<?> operation : operations) {
			operation.execute();
		}
		
		logger.info("Finish to Execute operations");
	}

}
