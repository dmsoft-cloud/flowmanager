package it.dmsoft.flowmanager.agent.engine.generic.authentication.widiba.interfaccie;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "tokenId", "successUrl" })
public class AuthenticationResponseData {

	@JsonProperty("tokenId")
	private String tokenId;
	@JsonProperty("successUrl")
	private String successUrl;

	@JsonProperty("tokenId")
	public String getTokenId() {
		return tokenId;
	}

	@JsonProperty("tokenId")
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	@JsonProperty("successUrl")
	public String getSuccessUrl() {
		return successUrl;
	}

	@JsonProperty("successUrl")
	public void setSuccessUrl(String seccussUrl) {
		this.successUrl = seccussUrl;
	}

}
