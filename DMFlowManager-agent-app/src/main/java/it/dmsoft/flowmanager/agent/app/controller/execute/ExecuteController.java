package it.dmsoft.flowmanager.agent.app.controller.execute;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.dmsoft.flowmanager.agent.api.execute.ExecuteService;
import it.dmsoft.flowmanager.common.model.FlowData;
import it.dmsoft.flowmanager.common.model.FlowExecutionOutcome;
import it.dmsoft.flowmanager.common.model.FullFlowData;
import jakarta.annotation.Resource;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/execute")
public class ExecuteController {
	
	@Resource(name = "executeService")
    private ExecuteService executeService;

	//esecuzione sincrona
	
	//esecuzione asincrona
	
	//L'invio mail potremmo pensare di farlo remoto (dal master) o locale dall'agent, in prima mbattuta implementerei solo dall'agent
	
	//possibilità di ovverride dei dati
	
	//storicizzazione o log dei dati di lancio
	
	@PostMapping("/synch/{flowId}")
	public FlowExecutionOutcome synch(@PathVariable(value = "flowId") String flowId, final @RequestBody(required = false) FullFlowData fullFlowData) {
		FullFlowData _fullFlowData = fullFlowData;
		if (fullFlowData == null) {
			_fullFlowData = new FullFlowData();
			FlowData flowData = new FlowData();
			flowData.setId(flowId);
			_fullFlowData.setFlow(flowData);
		}
			
		return executeService.synch(_fullFlowData);
	}
}
