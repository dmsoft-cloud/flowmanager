package it.dmsoft.flowmanager.agent.engine.generic.genericWsClient;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

public class CustomAuthenticator extends Authenticator {
	private String user;
	private String password;

	public CustomAuthenticator(String user, String password) {
		this.user = user;
		this.password = password;
	}

	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		PasswordAuthentication auth = new PasswordAuthentication(user, password.toCharArray());
		return auth;
	}
}
