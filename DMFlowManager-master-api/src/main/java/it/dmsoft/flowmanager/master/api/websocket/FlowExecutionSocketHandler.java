package it.dmsoft.flowmanager.master.api.websocket;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import it.dmsoft.flowmanager.be.entities.FlowLog;
import it.dmsoft.flowmanager.be.entities.FlowLogDetails;
import it.dmsoft.flowmanager.be.keys.FlowLogDetailsId;
import it.dmsoft.flowmanager.common.model.FlowLogData;
import it.dmsoft.flowmanager.common.model.FlowLogDetailsData;
import it.dmsoft.flowmanager.framework.api.base.BaseService;
import it.dmsoft.flowmanager.framework.json.UtilityJson;
import it.dmsoft.flowmanager.master.api.log.DefaultFlowLogService;
import jakarta.annotation.Resource;

@Component
public class FlowExecutionSocketHandler
        extends TextWebSocketHandler {
	
    @Resource(name = "flowLogService")
    private DefaultFlowLogService flowLogService;
    
    @Resource(name = "flowLogDetailsService")
    private BaseService<FlowLogDetails, FlowLogDetailsData, FlowLogDetailsId> flowLogDetailsService;
	

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("CLIENT WS OPEN id=" + session.getId() + ",  hash=" + System.identityHashCode(session));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session,TextMessage message) {
    	System.out.println("CLIENT WS RECV payload=" + 
            message.getPayload());
    	
    	try {
	        if (message.getPayload().contains("logId")) {
				FlowLogData logData = UtilityJson.readValue(message.getPayload(), FlowLogData.class);
				flowLogService.save(logData);

			} else {
				FlowLogDetailsData logDetailsData = UtilityJson.readValue(message.getPayload(), FlowLogDetailsData.class);
				flowLogDetailsService.save(logDetailsData);
			}
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }	

    @Override
    public void afterConnectionClosed( WebSocketSession session, CloseStatus status) {
    	System.out.println("CLIENT WS CLOSED id=" +  session.getId());
    }
}

