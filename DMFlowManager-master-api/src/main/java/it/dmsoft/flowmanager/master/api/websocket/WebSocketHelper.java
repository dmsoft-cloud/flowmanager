package it.dmsoft.flowmanager.master.api.websocket;

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

public class WebSocketHelper {
	
	public static Session openSocket(ExecuteWebSocketClient wsclient, AgentData agentData) {
		WebSocketContainer container = ContainerProvider.getWebSocketContainer();
	
		String token = SecurityConfig.threadLocalJwt.get().getTokenValue();
        String uri = "ws://" + agentData.getIp() + ":" + agentData.getPort() + "/flow-websocket"; // Example WebSocket server
        
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
            return session;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
        
	}
	
	public static void openSocket (ExecuteWebSocketClient wsclient) {
		openSocket(wsclient, AgentData.DEFAULT_AGENT);
	}

}
