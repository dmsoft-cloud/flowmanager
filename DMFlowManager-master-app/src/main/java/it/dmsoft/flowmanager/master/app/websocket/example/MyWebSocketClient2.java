package it.dmsoft.flowmanager.master.app.websocket.example;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import it.dmsoft.flowmanager.common.websocket.FlowExecutionRequest;
import jakarta.websocket.ClientEndpoint;

@ClientEndpoint
public class MyWebSocketClient2 {


    public static void main(String[] args) {
        WebSocketClient wsc = new StandardWebSocketClient();
        String uri = "ws://127.0.0.1:8081/flow-websocket";
        URI uriObj = null;
		try {
			uriObj = new URI(uri);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        FlowExecutionSocketHandler feh = new FlowExecutionSocketHandler();
        wsc.execute(feh, (WebSocketHttpHeaders) null, uriObj);
        
        FlowExecutionRequest fer = new FlowExecutionRequest();
		fer.setFlowId("TES1");
		fer.setFlowProgr(BigDecimal.valueOf(5));
		
        feh.sendMessage(fer);
        new Scanner(System.in).nextLine();
    }
}
