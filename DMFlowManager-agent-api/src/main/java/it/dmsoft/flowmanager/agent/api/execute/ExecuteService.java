package it.dmsoft.flowmanager.agent.api.execute;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.dmsoft.flowmanager.agent.api.flows.FlowDataService;
import it.dmsoft.flowmanager.agent.api.flows.model.ExecutionFlowData;
import it.dmsoft.flowmanager.common.model.FlowExecutionOutcome;
import it.dmsoft.flowmanager.common.model.FullFlowData;
import it.dmsoft.flowmanager.framework.util.BeanUtils;
import jakarta.annotation.Resource;

@Service("executeService")
public class ExecuteService {
	
	@Resource(name = "flowDataService")
    private FlowDataService flowDataService;
	
	
	public FlowExecutionOutcome synch(FullFlowData fullFlowData) {
		//CARICARE il FLUSSO DA JSON
		//FARE IL MERGE CON QUANTO ARRIVATO IN INPUT
		FullFlowData storedFullFlowData = flowDataService.getFullFlowData(fullFlowData.getFlow().getId());
		
		BeanUtils.copyPropertiesNotNull(fullFlowData, storedFullFlowData);
		
		ExecutionFlowData executionFlowData = flowDataService.getExecutionFlowData(storedFullFlowData);
		
		return null;
	}

}
