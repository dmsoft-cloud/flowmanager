package it.dmsoft.flowmanager.agent.api.execute;

import org.springframework.stereotype.Service;

import it.dmsoft.flowmanager.agent.api.flows.FlowDataService;
import it.dmsoft.flowmanager.agent.engine.core.DynamicFlowManager;
import it.dmsoft.flowmanager.agent.engine.core.model.ExecutionFlowData;
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
	
	public FlowExecutionOutcome synch(FullFlowData fullFlowData) {
		//CARICARE il FLUSSO DA JSON
		//FARE IL MERGE CON QUANTO ARRIVATO IN INPUT
		FullFlowData storedFullFlowData = flowDataService.getFullFlowData(fullFlowData.getFlow().getId());
		
		BeanUtils.copyPropertiesNotNull(fullFlowData, storedFullFlowData);
		
		ExecutionFlowData executionFlowData = flowDataService.getExecutionFlowData(storedFullFlowData);
		
		try {
			dynamicFlowManager.executeFlow(executionFlowData, null, null, null, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		return null;
	}

}
