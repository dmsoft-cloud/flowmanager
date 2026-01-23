package it.dmsoft.flowmanager.framework.websocket;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import it.dmsoft.flowmanager.common.websocket.FlowExecutionRequest;
import it.dmsoft.flowmanager.framework.json.UtilityJson;
import jakarta.annotation.Resource;

@Component("executeWebSocketHandler")
public class ExecuteWebSocketHandler extends TextWebSocketHandler {
	
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
	
    /*il service non serve nell'handler
	@Resource(name = "executeService")
    private ExecuteService executeService;
	*/

	//private static Set<WebSocketSession> sessions = new HashSet<>(); 
	
	@Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		
		System.out.println("WS OPEN  id=" + session.getId() + " , hash=" + System.identityHashCode(session) + " , thread=" + Thread.currentThread().getName()  );
	    String path = session.getUri().getPath();
	    String executionId = path.substring(path.lastIndexOf("/") + 1);
	    sessions.put(executionId, session);
	    System.out.println("WS aperta per executionId=" + executionId);
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
	    sessions.values().remove(session);
	}

	/*non viene più usato
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    	System.out.println(message.getPayload());
    	FlowExecutionRequest fer = UtilityJson.readValue(message.getPayload(), FlowExecutionRequest.class);
    	ExecuteService.sessionsMap.put(fer.getFlowProgr(), session);
    	executeService.asynch(fer.getFlowId(), fer.getFlowProgr(), fer.getFullFlowData());
    }
    */
    
    public WebSocketSession getSession(BigDecimal flowProgr) {
        return sessions.get(flowProgr.toString());
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
