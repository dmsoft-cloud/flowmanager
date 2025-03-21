package it.dmsoft.flowmanager.framework.rest;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import it.dmsoft.flowmanager.common.model.AgentData;

public class RestClientHelper {
	
	public static RestClient getDefaultAgentRestClient() {
		return getAgentRestClient(AgentData.DEFAULT_AGENT);
	}
	
	public static RestClient getAgentRestClient(AgentData agentData) {
		return RestClient.builder()
			    .baseUrl("http://" + agentData.getIp() + ":" + agentData.getPort())
			    .build();

	}
	
	public static RestClient getAgentRestClient(String baseUrl) {
		return RestClient.builder()
				.requestFactory(new HttpComponentsClientHttpRequestFactory())
			    .baseUrl(baseUrl)
			    .build();
	}
	
	
}
