package it.dmsoft.flowmanager.agent.engine.generic.authentication.widiba.parameters.data;

import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.parameters.data.ClientDataJWT;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.AppOpePar;

public class ClientDataWidiba extends ClientDataJWT {
	private String clientId;
	private String clientSecret;
	private int numeroTentativi;
	private String azienda;
	private String rac;

	public ClientDataWidiba(AppOpePar params, String url, String username, String password, String clientId, String clientSecret, int numeroTentativi, String rac, String azienda) {
		super(params, url, null, username, password);
		this.numeroTentativi = numeroTentativi;
		this.azienda = azienda;
		this.rac = rac;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
	}

	public int getNumeroTentativi() {
		return numeroTentativi;
	}

	public void setNumeroTentativi(int numeroTentativi) {
		this.numeroTentativi = numeroTentativi;
	}

	public String getAzienda() {
		return azienda;
	}

	public void setAzienda(String azienda) {
		this.azienda = azienda;
	}

	public String getRac() {
		return rac;
	}

	public void setRac(String rac) {
		this.rac = rac;
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
