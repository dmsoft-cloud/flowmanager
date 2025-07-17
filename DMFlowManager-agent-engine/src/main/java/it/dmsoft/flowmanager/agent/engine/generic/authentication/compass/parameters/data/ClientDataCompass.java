package it.dmsoft.flowmanager.agent.engine.generic.authentication.compass.parameters.data;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.parameters.ClientDataAbstract;
import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.restful.Invoker;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.AppOpePar;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class ClientDataCompass extends ClientDataAbstract {
	private String clientId;
	private String clientSecret;
	private String scope;
	private String username;
	private String password;

	public ClientDataCompass(AppOpePar params, String clientId, String clientSecret, String scope, String username, String password, String url) {
		super(params, url);
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.scope = scope;
		this.username = username;
		this.password = password;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String toParamString(Logger logger) throws UnsupportedEncodingException {
		HashMap<String, String> params = new HashMap<String, String>(7);
		params.put("scope", scope);
		params.put("client_id", clientId);
		params.put("client_secret", clientSecret);
		params.put("username", username);
		params.put("password", password);
		params.put("grant_type", "password");
		return Invoker.getParamsString(params, logger);
	}

	@Override
	public String toString() {
		return "ClientDataCompass [clientId=" + clientId + ", clientSecret=" + clientSecret + ", scope=" + scope + ", username=" + username + ", password=" + password + ", toString()="
				+ super.toString() + "]";
	}
	
	
}
