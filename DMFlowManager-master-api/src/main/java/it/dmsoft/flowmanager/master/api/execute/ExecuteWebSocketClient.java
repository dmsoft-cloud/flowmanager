package it.dmsoft.flowmanager.master.api.execute;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;

import it.dmsoft.flowmanager.be.entities.FlowLog;
import it.dmsoft.flowmanager.be.entities.FlowLogDetails;
import it.dmsoft.flowmanager.be.keys.FlowLogDetailsId;
import it.dmsoft.flowmanager.common.model.FlowLogData;
import it.dmsoft.flowmanager.common.model.FlowLogDetailsData;
import it.dmsoft.flowmanager.common.model.FullFlowData;
import it.dmsoft.flowmanager.common.websocket.FlowExecutionRequest;
import it.dmsoft.flowmanager.framework.api.base.BaseService;
import it.dmsoft.flowmanager.framework.json.UtilityJson;
import jakarta.annotation.Resource;
import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.WebSocketContainer;


@ClientEndpoint
@Component("executeWebSocketClient")
public class ExecuteWebSocketClient {
	
	private boolean firstMessage = false;

	private Session session;
	
    @Resource(name = "flowLogService")
    private BaseService<FlowLog, FlowLogData, String> flowLogService;
    
    @Resource(name = "flowLogDetailsService")
    private BaseService<FlowLogDetails, FlowLogDetailsData, FlowLogDetailsId> flowLogDetailsService; 
	
    @OnMessage
    public void onMessage(String message) {
        System.out.println("Received message: " + message);
        
        try {
	        if (firstMessage || message.contains("logId")) {
				FlowLogData logData = UtilityJson.readValue(message, FlowLogData.class);
				flowLogService.save(logData);
				
				if(!firstMessage) {
					session.close();
				} else {
					firstMessage = false;
				}	
			} else {
				FlowLogDetailsData logDetailsData = UtilityJson.readValue(message, FlowLogDetailsData.class);
				flowLogDetailsService.save(logDetailsData);
			}
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        System.out.println("Connected to server");
    }
    
	public void sendMessage(String flowId, BigDecimal flowProgr, FullFlowData fullFlowData) throws JsonProcessingException, InterruptedException, IOException {
		FlowExecutionRequest fer = new FlowExecutionRequest();
		fer.setFlowId(flowId);
		fer.setFlowProgr(flowProgr);
		fer.setFullFlowData(fullFlowData);
		
		sendMessage(fer);
	}  
    	
    public void sendMessage(FlowExecutionRequest fer) throws InterruptedException, JsonProcessingException, IOException {
        while(session == null) {
        	Thread.sleep(10);
        }
    	
        session.getBasicRemote().sendText(UtilityJson.writeValueAsString(fer));
    }

    public static void main(String[] args) {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        String uri = "ws://127.0.0.1:8081/flow-websocket"; // Example WebSocket server
        try {
            container.connectToServer(ExecuteWebSocketClient.class, URI.create(uri));
            System.out.println("Connected to server");
            new Scanner(System.in).nextLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

}
