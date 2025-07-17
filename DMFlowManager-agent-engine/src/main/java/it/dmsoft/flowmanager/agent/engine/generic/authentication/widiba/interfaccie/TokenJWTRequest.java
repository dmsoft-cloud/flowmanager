package it.dmsoft.flowmanager.agent.engine.generic.authentication.widiba.interfaccie;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.restful.Invoker;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "scope", "client_id", "client_secret", "auth_chain", "username", "password", "grant_type" })
public class TokenJWTRequest {

	private Logger logger = Logger.getLogger(TokenJWTRequest.class);
	@JsonProperty("scope")
	private String scope;
	@JsonProperty("client_id")
	private String clientId;
	@JsonProperty("client_secret")
	private String clientSecret;
	@JsonProperty("auth_chain")
	private String authChain;
	@JsonProperty("username")
	private String username;
	@JsonProperty("password")
	private String password;
	@JsonProperty("grant_type")
	private String grantType;

	@JsonProperty("scope")
	public String getScope() {
		return scope;
	}

	@JsonProperty("scope")
	public void setScope(String scope) {
		this.scope = scope;
	}

	@JsonProperty("client_id")
	public String getClientId() {
		return clientId;
	}

	@JsonProperty("client_id")
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	@JsonProperty("client_secret")
	public String getClientSecret() {
		return clientSecret;
	}

	@JsonProperty("client_secret")
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	@JsonProperty("auth_chain")
	public String getAuthChain() {
		return authChain;
	}

	@JsonProperty("auth_chain")
	public void setAuthChain(String authChain) {
		this.authChain = authChain;
	}

	@JsonProperty("username")
	public String getUsername() {
		return username;
	}

	@JsonProperty("username")
	public void setUsername(String username) {
		this.username = username;
	}

	@JsonProperty("password")
	public String getPassword() {
		return password;
	}

	@JsonProperty("password")
	public void setPassword(String password) {
		this.password = password;
	}

	@JsonProperty("grant_type")
	public String getGrantType() {
		return grantType;
	}

	@JsonProperty("grant_type")
	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}

	public String toParamString() throws UnsupportedEncodingException {
		HashMap<String, String> params = new HashMap<String, String>(7);
		params.put("scope", scope);
		params.put("client_id", clientId);
		params.put("client_secret", clientSecret);
		params.put("auth_chain", authChain);
		params.put("username", username);
		params.put("password", password);
		params.put("grant_type", grantType);
		return Invoker.getParamsString(params, logger);

	}

}
