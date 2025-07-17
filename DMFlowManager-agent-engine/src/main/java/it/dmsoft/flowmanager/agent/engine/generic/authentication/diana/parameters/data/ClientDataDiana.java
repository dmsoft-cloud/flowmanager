package it.dmsoft.flowmanager.agent.engine.generic.authentication.diana.parameters.data;

import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.parameters.data.ClientDataJWT;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.AppOpePar;

public class ClientDataDiana extends ClientDataJWT{
	private int timeout;
	public ClientDataDiana(AppOpePar params, String url, String urlToken, String username, String password, int timeout){
		super(params, url, urlToken, username, password);
		this.timeout = timeout;
	}
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

}
