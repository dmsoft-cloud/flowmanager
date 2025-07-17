package it.dmsoft.flowmanager.agent.engine.generic.authentication.exception;

public class AuthenticationException extends Exception {

	private static final long serialVersionUID = 1L;

	public AuthenticationException(String message) {
		super(message);
	}
	
	public AuthenticationException(String message, Exception e) {
		super(message, e);
	}
}
