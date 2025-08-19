package it.dmsoft.flowmanager.agent.api.execute;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import it.dmsoft.flowmanager.agent.api.flows.FlowDataService;
import it.dmsoft.flowmanager.agent.api.properties.AgentPropertiesService;
import it.dmsoft.flowmanager.agent.api.properties.mapper.FlowConfigMapper;
import it.dmsoft.flowmanager.agent.engine.core.manager.DynamicFlowManager;
import it.dmsoft.flowmanager.agent.engine.core.model.ExecutionFlowData;
import it.dmsoft.flowmanager.agent.engine.core.utils.FormatUtils;
import it.dmsoft.flowmanager.common.engine.FlowConfig;
import it.dmsoft.flowmanager.common.model.FlowData;
import it.dmsoft.flowmanager.common.model.FlowExecutionOutcome;
import it.dmsoft.flowmanager.common.model.FullFlowData;
import it.dmsoft.flowmanager.framework.util.BeanUtils;
import jakarta.annotation.Resource;

@Service("executeService")
public class ExecuteService {
	
	@Resource(name = "flowDataService")
    private FlowDataService flowDataService;
	
	@Resource(name = "dynamicFlowManager")
    private DynamicFlowManager dynamicFlowManager;
	
	@Resource(name = "agentPropertiesService")
    private AgentPropertiesService agentPropertiesService;
	
	@Autowired
	private FlowConfigMapper flowConfigMapper;
	
	public FlowExecutionOutcome synch(String flowId, BigDecimal flowProgr, FullFlowData fullFlowData) {
		return synch(flowId, flowProgr, fullFlowData, false);
	}
	
	private FlowExecutionOutcome synch(String flowId, BigDecimal flowProgr, FullFlowData fullFlowData, boolean asynch) {
		//CARICARE il FLUSSO DA JSON
		//FARE IL MERGE CON QUANTO ARRIVATO IN INPUT
		if (fullFlowData == null) {
			fullFlowData = new FullFlowData();
			FlowData flowData = new FlowData();
			flowData.setId(flowId);
			fullFlowData.setFlow(flowData);
		}
		
		FullFlowData storedFullFlowData = flowDataService.getFullFlowData(fullFlowData.getFlow().getId());
		
		BeanUtils.copyPropertiesNotNull(fullFlowData, storedFullFlowData);
		
		ExecutionFlowData executionFlowData = flowDataService.getExecutionFlowData(storedFullFlowData);
		
		FlowConfig flowConfig = getFlowConfig(storedFullFlowData);
		
		WebSocketSession wss = ExecuteWebSocketHandler.sessionsMap.get(flowProgr);
		
		if (wss == null && asynch) {
			throw new RuntimeException("WebSocket is null in asunch flow");
		}
		
		try {
			dynamicFlowManager.executeFlow(flowProgr, executionFlowData, null, flowConfig, wss);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		return null;
	}
	
	private FlowConfig getFlowConfig(FullFlowData storedFullFlowData) {
		FlowConfig flowConfig = flowConfigMapper.convert(agentPropertiesService, storedFullFlowData);
		flowConfig.setExecutionDateStr(FormatUtils.todayDateBigDec().toString());
		return flowConfig;
	}

	@Async
	public void asynch(String flowId, BigDecimal flowProgr, FullFlowData fullFlowData) {
		synch(flowId, flowProgr, fullFlowData, true);
	}

}
