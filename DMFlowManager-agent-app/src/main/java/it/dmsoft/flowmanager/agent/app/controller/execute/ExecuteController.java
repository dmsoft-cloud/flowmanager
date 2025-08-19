package it.dmsoft.flowmanager.agent.app.controller.execute;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.dmsoft.flowmanager.agent.api.execute.ExecuteService;
import it.dmsoft.flowmanager.common.model.FlowExecutionOutcome;
import it.dmsoft.flowmanager.common.model.FullFlowData;
import jakarta.annotation.Resource;

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
	
	@PostMapping("/synch/{flowId}/{flowProgr}")
	public FlowExecutionOutcome synch(@PathVariable(value = "flowId") String flowId, @PathVariable(value = "flowProgr") BigDecimal flowProgr, final @RequestBody(required = false) FullFlowData fullFlowData) {
		return executeService.synch(flowId, flowProgr, fullFlowData);
	}
	
	/*
	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public Greeting greeting(HelloMessage message) throws Exception {
		Thread.sleep(1000); // simulated delay
		return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
	}
	*/
}
