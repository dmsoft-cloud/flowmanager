package it.dmsoft.flowmanager.master.api.execute;

import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import it.dmsoft.flowmanager.common.model.AgentData;
import it.dmsoft.flowmanager.common.model.FlowData;
import it.dmsoft.flowmanager.common.model.FlowExecutionOutcome;
import it.dmsoft.flowmanager.common.model.FullFlowData;
import it.dmsoft.flowmanager.common.model.FullFlowsData;
import it.dmsoft.flowmanager.framework.api.base.BaseService;
import it.dmsoft.flowmanager.framework.rest.RestClientHelper;
import it.dmsoft.flowmanager.master.be.entities.Agent;
import it.dmsoft.flowmanager.master.be.entities.Flow;
import jakarta.annotation.Resource;

public class ExecuteService {
	
	@Resource(name = "flowService")
    private BaseService<Flow, FlowData, String> flowService;
	
    @Resource(name = "agentService")
    private BaseService<Agent, AgentData, String> agentService;

	public FlowExecutionOutcome synch(String flowId, FullFlowData fullFlowData) {
		FlowData flowData = flowService.getById(flowId);
		//TODO Aggiungere reperimento agent in base al flusso
		AgentData agent = null;
		
		RestClient rc = agent != null ? RestClientHelper.getAgentRestClient(agent) : RestClientHelper.getDefaultAgentRestClient();
		
		return rc.post()
				.uri(uriBuilder -> uriBuilder
			      .path("/execute/synchs")
			      .path(flowId)
			      .build())
				.contentType(MediaType.APPLICATION_JSON)
				.body(fullFlowData)
				.retrieve()
				.body(FlowExecutionOutcome.class);
	}
}
