package it.dmsoft.flowmanager.agent.engine.generic.authentication.common.parameters.data;

import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.parameters.ClientDataAbstract;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.AppOpePar;

public class ClientDataOauth extends ClientDataAbstract {

	protected String clientId;
	protected String clientSecret;
	protected String scope;

	public ClientDataOauth(AppOpePar params, String url) {
		super(params, url);
	}

	public ClientDataOauth(AppOpePar params, String url, String clientId, String clientSecret, String scope) {
		super(params, url);
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.scope = scope;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

}