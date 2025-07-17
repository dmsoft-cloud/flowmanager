package it.dmsoft.flowmanager.agent.engine.generic.authentication.common.output.data;

import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.output.RetrieverDataAbstract;

public class TokenRetrieverData extends RetrieverDataAbstract {
	private String token;

	public TokenRetrieverData(boolean attivo, String token) {
		super(attivo);
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
