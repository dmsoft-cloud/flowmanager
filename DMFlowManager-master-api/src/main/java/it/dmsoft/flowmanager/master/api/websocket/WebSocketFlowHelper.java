package it.dmsoft.flowmanager.master.api.websocket;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import it.dmsoft.flowmanager.common.model.AgentData;
import it.dmsoft.flowmanager.framework.oauth.SecurityConfig;
import it.dmsoft.flowmanager.master.api.execute.ExecuteWebSocketClient;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.WebSocketHttpHeaders;

@Component
public class WebSocketFlowHelper {

    private WebSocketConnectionManager manager;

    public synchronized void openSocket(FlowExecutionSocketHandler handler, AgentData agentData, BigDecimal executionId) {

        String token = SecurityConfig.threadLocalJwt.get().getTokenValue();

        String uri = "ws://" + agentData.getHost() + ":" + agentData.getPort() + "/flow-websocket/" + executionId;

        WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
        headers.put("Authorization", Arrays.asList("Bearer " + token));

        StandardWebSocketClient client = new StandardWebSocketClient();

        this.manager = new WebSocketConnectionManager(client, handler, uri);

        this.manager.setHeaders(headers);
        this.manager.setAutoStartup(false);

        this.manager.start();

        System.out.println(
            "CLIENT WS CONNECTING uri=" + uri
            + " thread=" + Thread.currentThread().getName()
        );
    }

    public synchronized void closeSocket() {
        if (manager != null) {
            manager.stop();
        }
    }

    public void openSocket( FlowExecutionSocketHandler handler, BigDecimal executionId) {
        openSocket(handler, AgentData.DEFAULT_AGENT, executionId);
    }

}
