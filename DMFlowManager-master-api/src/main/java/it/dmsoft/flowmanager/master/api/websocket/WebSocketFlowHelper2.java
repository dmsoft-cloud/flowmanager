package it.dmsoft.flowmanager.master.api.websocket;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import it.dmsoft.flowmanager.common.model.AgentData;
import it.dmsoft.flowmanager.framework.oauth.SecurityConfig;
import it.dmsoft.flowmanager.master.api.execute.ExecuteWebSocketClient;
import jakarta.websocket.ClientEndpointConfig;
import jakarta.websocket.ClientEndpointConfig.Builder;
import jakarta.websocket.ClientEndpointConfig.Configurator;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.Session;
import jakarta.websocket.WebSocketContainer;

public class WebSocketFlowHelper2 {

	public static Session openSocket(ExecuteWebSocketClient wsclient, AgentData agentData, BigDecimal executionId) {
		WebSocketContainer container = ContainerProvider.getWebSocketContainer();

		String token = SecurityConfig.threadLocalJwt.get().getTokenValue();
        String uri = "ws://" + agentData.getHost() + ":" + agentData.getPort() + "/flow-websocket/" + executionId ; // Example WebSocket server

        Builder configBuilder = ClientEndpointConfig.Builder.create();
        configBuilder.configurator(new Configurator() {
            @Override
            public void beforeRequest(Map<String, List<String>> headers) {
            headers.put("Authorization", Arrays.asList("Bearer " + token));
            }
        });
        ClientEndpointConfig clientConfig = configBuilder.build();

        try {
            Session session = container.connectToServer(wsclient, clientConfig, URI.create(uri));
            System.out.println("Connected to server");
            System.out.println("WS OPEN  id=" + session.getId() + " , hash=" + System.identityHashCode(session) + " , thread=" + Thread.currentThread().getName()  );
    	    
            return session;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

	}

	public static void openSocket (ExecuteWebSocketClient wsclient, BigDecimal executionId) {
		openSocket(wsclient, AgentData.DEFAULT_AGENT, executionId );
	}
	


}
