package it.dmsoft.flowmanager.agent.app.controller.execute;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import it.dmsoft.flowmanager.agent.api.execute.ExecuteService;
import it.dmsoft.flowmanager.common.websocket.FlowExecutionRequest;
import it.dmsoft.flowmanager.framework.json.UtilityJson;
import jakarta.annotation.Resource;

@Component("executeWebSocketHandler")
public class ExecuteWebSocketHandler extends TextWebSocketHandler {
	
	@Resource(name = "executeService")
    private ExecuteService executeService;

	//private static Set<WebSocketSession> sessions = new HashSet<>(); 
	
	@Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("Connection established");
	}

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    	System.out.println(message.getPayload());
    	FlowExecutionRequest fer = UtilityJson.readValue(message.getPayload(), FlowExecutionRequest.class);
    	ExecuteService.sessionsMap.put(fer.getFlowProgr(), session);
    	executeService.asynch(fer.getFlowId(), fer.getFlowProgr(), fer.getFullFlowData());
    }
    
    
    
    /*
     * for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen()) {
                try {
                    webSocketSession.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	*/
     
    
}
