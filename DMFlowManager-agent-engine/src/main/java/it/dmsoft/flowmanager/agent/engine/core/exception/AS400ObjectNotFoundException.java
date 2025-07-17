package it.dmsoft.flowmanager.agent.engine.core.exception;

public class AS400ObjectNotFoundException extends Exception {
	private static final long serialVersionUID = 1869789521099411510L;

	public AS400ObjectNotFoundException(String message) {
		super(message);
	}

	public AS400ObjectNotFoundException(String message, Throwable t) {
		super(message, t);
	}
}