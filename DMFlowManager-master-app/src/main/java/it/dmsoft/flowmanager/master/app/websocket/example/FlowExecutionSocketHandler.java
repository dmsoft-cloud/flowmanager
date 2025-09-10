package it.dmsoft.flowmanager.master.app.websocket.example;

import java.io.IOException;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import it.dmsoft.flowmanager.common.model.FlowLogData;
import it.dmsoft.flowmanager.common.model.FlowLogDetailsData;
import it.dmsoft.flowmanager.common.websocket.FlowExecutionRequest;
import it.dmsoft.flowmanager.framework.json.UtilityJson;

public class FlowExecutionSocketHandler extends TextWebSocketHandler {
	
	private WebSocketSession session;
	
	private boolean firstMessage = false;

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// TODO Auto-generated method stub
		super.afterConnectionEstablished(session);
		this.session = session;
	}
	
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// TODO Auto-generated method stub
		super.handleMessage(session, message);
		
		if (firstMessage) {
			FlowLogData logData = UtilityJson.readValue(message.getPayload(), FlowLogData.class);
			firstMessage = false;
		} else {
			FlowLogDetailsData logDetailsData = UtilityJson.readValue(message.getPayload(), FlowLogDetailsData.class);
			System.out.println("");
		}
	}

	public void sendMessage(FlowExecutionRequest fer) {
		try {
			TextMessage textMessage = new TextMessage(UtilityJson.writeValueAsString(fer));
			session.sendMessage(textMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
