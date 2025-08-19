package it.dmsoft.flowmanager.master.api.websocket;

import java.net.URI;

import it.dmsoft.flowmanager.common.model.AgentData;
import it.dmsoft.flowmanager.master.api.execute.ExecuteWebSocketClient;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.WebSocketContainer;

public class WebSocketHelper {
	
	public static void openSocket(ExecuteWebSocketClient wsclient, AgentData agentData) {
		WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        String uri = "ws://" + agentData.getIp() + ":" + agentData.getPort() + "/flow-websocket"; // Example WebSocket server
        try {
            container.connectToServer(wsclient, URI.create(uri));
            System.out.println("Connected to server");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
	}
	
	public static void openSocket (ExecuteWebSocketClient wsclient) {
		openSocket(wsclient, AgentData.DEFAULT_AGENT);
	}

}
