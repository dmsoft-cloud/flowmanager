package it.dmsoft.flowmanager.agent.engine.generic.authentication.intesaAuth.parameters.data;

import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.parameters.data.ClientDataJWT;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.AppOpePar;

public class ClientDataIntesa extends ClientDataJWT {
	private String scope;

	public ClientDataIntesa(AppOpePar params, String url, String username, String password, String scope) {
		super(params, url, null, username, password);
		this.scope = scope;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

}