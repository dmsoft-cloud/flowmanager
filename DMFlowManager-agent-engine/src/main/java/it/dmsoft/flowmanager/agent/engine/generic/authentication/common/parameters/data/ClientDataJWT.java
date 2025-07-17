package it.dmsoft.flowmanager.agent.engine.generic.authentication.common.parameters.data;

import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.parameters.ClientDataAbstract;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.AppOpePar;

public class ClientDataJWT extends ClientDataAbstract {
	private String urlToken;
	private String username;
	private String password;
	private String defaultUsername;
	private String defaultPassword;

	// Da lasciare sempre un costruttore con username e password per impostare
	// le credenziali di default
	public ClientDataJWT(AppOpePar params, String url, String urlToken, String username, String password) {
		super(params, url);
		this.username = username;
		this.password = password;
		this.defaultUsername = username;
		this.defaultPassword = password;
		this.urlToken = urlToken;
	}

	public String getUrlToken() {
		return urlToken;
	}

	public void setUrlToken(String urlToken) {
		this.urlToken = urlToken;
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

	public void resetCredentials() {
		this.username = this.defaultUsername;
		this.password = this.defaultPassword;

	}

	@Override
	public String toString() {
		return "ClientDataJWT [urlToken=" + urlToken + ", username=" + username + ", password=" + password + ", defaultUsername=" + defaultUsername + ", defaultPassword=" + defaultPassword
				+ ", toString()=" + super.toString() + "]";
	}
}
