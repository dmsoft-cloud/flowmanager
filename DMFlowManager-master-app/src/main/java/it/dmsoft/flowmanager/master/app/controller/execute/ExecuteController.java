package it.dmsoft.flowmanager.master.app.controller.execute;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.dmsoft.flowmanager.common.model.FlowExecutionOutcome;
import it.dmsoft.flowmanager.master.api.execute.ExecuteService;
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
	
	@PostMapping("/synch")
	public FlowExecutionOutcome synch(@PathParam(value = "flowId") String flowId) {
		return executeService.synch(flowId, null);
	}
}