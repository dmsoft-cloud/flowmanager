package it.dmsoft.flowmanager.agent.app.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import it.dmsoft.flowmanager.agent.engine.core.utils.FlowLogUtils;
import it.dmsoft.flowmanager.framework.websocket.ExecuteWebSocketHandler;

@Configuration
public class FlowLogBootstrap {
    @Autowired
    public FlowLogBootstrap(
            ExecuteWebSocketHandler handler) {

        FlowLogUtils.setExecuteWebSocketHandler(handler);
    }

}
