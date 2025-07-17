package it.dmsoft.flowmanager.agent.engine.core.operations.core;

public abstract class Operation<T> {	
	
	protected T parameters;
	
	public final void setParameters(T parameters) {
		this.parameters = parameters;
	}
	
	public abstract void execute() throws Exception;

}
