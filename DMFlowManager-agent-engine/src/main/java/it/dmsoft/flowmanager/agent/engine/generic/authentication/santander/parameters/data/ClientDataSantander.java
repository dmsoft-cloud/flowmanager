package it.dmsoft.flowmanager.agent.engine.generic.authentication.santander.parameters.data;

import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.parameters.ClientDataAbstract;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.AppOpePar;

public class ClientDataSantander extends ClientDataAbstract{
	private String clientId;
	private String clientSecret;
	public ClientDataSantander(AppOpePar params, String clientId, String clientSecret, String url){
		super(params, url);
		this.clientId = clientId;
		this.clientSecret = clientSecret;
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

}
