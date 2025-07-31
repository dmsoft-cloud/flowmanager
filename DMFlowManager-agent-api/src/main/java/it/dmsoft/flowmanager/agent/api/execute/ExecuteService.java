package it.dmsoft.flowmanager.agent.api.execute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.dmsoft.flowmanager.agent.api.flows.FlowDataService;
import it.dmsoft.flowmanager.agent.api.properties.AgentPropertiesService;
import it.dmsoft.flowmanager.agent.api.properties.mapper.FlowConfigMapper;
import it.dmsoft.flowmanager.agent.engine.core.manager.DynamicFlowManager;
import it.dmsoft.flowmanager.agent.engine.core.model.ExecutionFlowData;
import it.dmsoft.flowmanager.agent.engine.core.utils.FormatUtils;
import it.dmsoft.flowmanager.be.entities.FlowConfig;
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
	
	public FlowExecutionOutcome synch(FullFlowData fullFlowData) {
		//CARICARE il FLUSSO DA JSON
		//FARE IL MERGE CON QUANTO ARRIVATO IN INPUT
		FullFlowData storedFullFlowData = flowDataService.getFullFlowData(fullFlowData.getFlow().getId());
		
		BeanUtils.copyPropertiesNotNull(fullFlowData, storedFullFlowData);
		
		ExecutionFlowData executionFlowData = flowDataService.getExecutionFlowData(storedFullFlowData);
		
		FlowConfig flowConfig = getFlowConfig(storedFullFlowData);
		try {
			dynamicFlowManager.executeFlow(executionFlowData, null, flowConfig);
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

}
