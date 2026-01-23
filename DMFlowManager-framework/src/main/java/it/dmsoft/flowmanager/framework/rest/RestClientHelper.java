package it.dmsoft.flowmanager.framework.rest;

import java.util.concurrent.atomic.AtomicReference;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.client.RestClient;

import it.dmsoft.flowmanager.common.model.AgentData;

public class RestClientHelper {
	
    private static final AtomicReference<OAuth2AuthorizedClientManager> MANAGER = new AtomicReference<>();

    /** chiamato dalla config all'avvio */
    public static void setAuthorizedClientManager(OAuth2AuthorizedClientManager manager) {
        MANAGER.set(manager);
    }

	public static RestClient getDefaultAgentRestClient() {
		return getAgentRestClientRelayToken(AgentData.DEFAULT_AGENT);
	}

	public static RestClient getAgentRestClient(AgentData agentData) {
		return RestClient.builder()
				.baseUrl("http://" + agentData.getHost() + ":" + agentData.getPort())
				.requestInterceptor((request, body, execution) -> {
					 OAuth2AuthorizedClientManager mgr = MANAGER.get();
					 if (mgr == null) {
		                    throw new IllegalStateException(
		                        "OAuth2AuthorizedClientManager non inizializzato. " +
		                        "Hai aggiunto spring-boot-starter-oauth2-client e la config AgentOAuth2ClientConfig?"
		                    );
		             }
					 OAuth2AuthorizeRequest authRequest =
			                    OAuth2AuthorizeRequest.withClientRegistrationId("agent")
			                        .principal("flowmanager-master") // per client_credentials basta una stringa
			                        .build();

		                OAuth2AuthorizedClient client = mgr.authorize(authRequest);
	
		                if (client == null || client.getAccessToken() == null) {
		                    throw new IllegalStateException("Impossibile ottenere access token client_credentials per 'agent'");
		                }
					 
		              request.getHeaders().setBearerAuth(client.getAccessToken().getTokenValue());
				      System.out.println("OUT >>> " + request.getMethod() + " " + request.getURI());
				      request.getHeaders().forEach((k,v) -> System.out.println(k + ": " + v));
				      return execution.execute(request, body);
				  })
			    .build();

	}
	
	public static RestClient getAgentRestClientRelayToken(AgentData agentData) {
		return RestClient.builder()
				.baseUrl("http://" + agentData.getHost() + ":" + agentData.getPort())
			       .requestInterceptor((request, body, execution) -> {

		                // 1) prova a recuperare il Bearer token corrente (utente)
		                String token = currentBearerTokenOrNull();
		                if (token != null) {
		                    request.getHeaders().setBearerAuth(token);
		                }
		                // 2) logging
		                System.out.println("OUT >>> " + request.getMethod() + " " + request.getURI());
		                request.getHeaders().forEach((k, v) -> System.out.println(k + ": " + v));

		                return execution.execute(request, body);
		            })
			    .build();

	}

	public static RestClient getAgentRestClient(String baseUrl) {
		return RestClient.builder()
				.requestFactory(new HttpComponentsClientHttpRequestFactory())
			    .baseUrl(baseUrl)
			    .build();
	}
	
    private static String currentBearerTokenOrNull() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof JwtAuthenticationToken jwtAuth) {
            return jwtAuth.getToken().getTokenValue();
        }
        return null;
    }


}
