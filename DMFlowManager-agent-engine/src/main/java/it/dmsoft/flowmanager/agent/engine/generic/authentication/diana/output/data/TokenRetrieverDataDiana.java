package it.dmsoft.flowmanager.agent.engine.generic.authentication.diana.output.data;

import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.output.data.TokenRetrieverData;

public class TokenRetrieverDataDiana extends TokenRetrieverData {
	private static final long serialVersionUID = -8108324118789410757L;

	public TokenRetrieverDataDiana(String token, boolean attivo) {
		this(token, attivo, null);
	}

	public TokenRetrieverDataDiana(String token, boolean attivo, String correlationId) {
		super(attivo, token);
		this.correlationId = correlationId;
	}

	public TokenRetrieverDataDiana() {
		super(false, "");
	}

	private String correlationId;

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

}
