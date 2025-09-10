package it.dmsoft.flowmanager.master.app.websocket.example;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonProcessingException;

import it.dmsoft.flowmanager.common.websocket.FlowExecutionRequest;
import it.dmsoft.flowmanager.framework.json.UtilityJson;
import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.WebSocketContainer;

@ClientEndpoint
public class MyWebSocketClient {

	private Session session;
	
    @OnMessage
    public void onMessage(String message) {
        System.out.println("Received message: " + message);
    }
    
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        System.out.println("Connected to server");
        
        FlowExecutionRequest fer = new FlowExecutionRequest();
		fer.setFlowId("TES1");
		fer.setFlowProgr(BigDecimal.valueOf(5));
		
        try {
			sendMessage(UtilityJson.writeValueAsString(fer));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void sendMessage(String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        String uri = "ws://127.0.0.1:8081/flow-websocket"; // Example WebSocket server
        try {
            container.connectToServer(MyWebSocketClient.class, URI.create(uri));
            System.out.println("Connected to server");
            new Scanner(System.in).nextLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
