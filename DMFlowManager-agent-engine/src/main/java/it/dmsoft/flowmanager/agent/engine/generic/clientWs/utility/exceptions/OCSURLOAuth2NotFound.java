package it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.exceptions;

public class OCSURLOAuth2NotFound extends Throwable {

	private static final long serialVersionUID = -4198441747099184644L;
	private static final String MESSAGE = "Url per reperimento token non impostato";
	
	public OCSURLOAuth2NotFound() {
		super(MESSAGE);
	}

}
