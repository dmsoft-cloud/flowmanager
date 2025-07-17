package it.dmsoft.flowmanager.agent.engine.mailclient.pec.exception;

public class PecDataException extends Exception {
	private static final long serialVersionUID = 1L;

	public PecDataException(String message) {
		super(message);
	}

	public PecDataException(String message, Exception e) {
		super(message, e);
	}
}
