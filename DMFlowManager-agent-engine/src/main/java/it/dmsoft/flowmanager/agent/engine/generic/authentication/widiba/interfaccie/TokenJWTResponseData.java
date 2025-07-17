package it.dmsoft.flowmanager.agent.engine.generic.authentication.widiba.interfaccie;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "id_token" })
public class TokenJWTResponseData {

	@JsonProperty("id_token")
	private String idToken;

	@JsonProperty("id_token")
	public String getIdToken() {
		return idToken;
	}

	@JsonProperty("id_token")
	public void setIdToken(String idToken) {
		this.idToken = idToken;
	}

}
