package it.dmsoft.flowmanager.master.app.websocket.example;

import java.lang.reflect.Type;
import java.math.BigDecimal;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;

import it.dmsoft.flowmanager.common.websocket.FlowExecutionRequest;
import it.dmsoft.flowmanager.framework.json.UtilityJson;

public class FlowExecutionSessionHandler implements StompSessionHandler {

	@Override
	public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
		//session.subscribe("/topic/messages", this);
		FlowExecutionRequest fer = new FlowExecutionRequest();
		fer.setFlowId("TES1");
		fer.setFlowProgr(BigDecimal.valueOf(5));
		
		try {
			session.send("/flow-websocket", UtilityJson.writeValueAsString(fer));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void handleFrame(StompHeaders headers, Object payload) {
		System.out.println("Received : " + payload);
	}

	@Override
	public Type getPayloadType(StompHeaders headers) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload,
			Throwable exception) {
		// TODO Auto-generated method stub
		exception.printStackTrace();
		System.out.println(exception);

	}

	@Override
	public void handleTransportError(StompSession session, Throwable exception) {
		// TODO Auto-generated method stub
		exception.printStackTrace();
	}

}
