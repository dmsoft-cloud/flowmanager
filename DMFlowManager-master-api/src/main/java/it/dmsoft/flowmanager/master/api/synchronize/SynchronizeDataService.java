package it.dmsoft.flowmanager.master.api.synchronize;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import it.dmsoft.flowmanager.be.entities.Agent;
import it.dmsoft.flowmanager.be.entities.ConfigurationGroup;
import it.dmsoft.flowmanager.be.entities.Email;
import it.dmsoft.flowmanager.be.entities.Flow;
import it.dmsoft.flowmanager.be.entities.Interface;
import it.dmsoft.flowmanager.be.entities.Model;
import it.dmsoft.flowmanager.be.entities.Origin;
import it.dmsoft.flowmanager.common.model.AgentData;
import it.dmsoft.flowmanager.common.model.EmailData;
import it.dmsoft.flowmanager.common.model.FlowData;
import it.dmsoft.flowmanager.common.model.FullFlowsData;
import it.dmsoft.flowmanager.common.model.GroupData;
import it.dmsoft.flowmanager.common.model.InterfaceData;
import it.dmsoft.flowmanager.common.model.ModelData;
import it.dmsoft.flowmanager.common.model.OriginData;
import it.dmsoft.flowmanager.framework.api.base.BaseService;
import it.dmsoft.flowmanager.framework.rest.RestClientHelper;
import jakarta.annotation.Resource;

@Service("synchDataService")
public class SynchronizeDataService {

    @Resource(name = "agentService")
    private BaseService<Agent, AgentData, String> agentService;
    
    @Resource(name = "emailService")
    private BaseService<Email, EmailData, String> emailService;
    
	@Resource(name = "flowService")
    private BaseService<Flow, FlowData, String> flowService;

	@Resource(name = "groupService")
    private BaseService<ConfigurationGroup, GroupData, String> groupService;

 	@Resource(name = "interfaceService")
    private BaseService<Interface, InterfaceData, String> interfaceService;
 	
    @Resource(name = "modelService")
    private BaseService<Model, ModelData, String> modelService;
    
    @Resource(name = "originService")
    private BaseService<Origin, OriginData, String> originService;

	public FullFlowsData synchronizeFullFlowsData() {
		List<AgentData> agents = agentService.getAll();
		FullFlowsData flowsData = retrieveFullFlowsData();
		
		if (agents == null || agents.size() == 0) {
			agents = Arrays.asList(AgentData.DEFAULT_AGENT);
		}
		
		agents.stream().forEach(x -> synchronizeFullFlowsData(x, flowsData));
		
		return flowsData;
	}

	public FullFlowsData synchronizeFullFlowsData(String agentId) {
		AgentData agent = agentService.getById(agentId);
		FullFlowsData flowsData = retrieveFullFlowsData();
		return synchronizeFullFlowsData(agent, flowsData);
	}
	
	
	public FullFlowsData synchronizeFullFlowsData(AgentData agent, FullFlowsData flowsData) {
		return RestClientHelper.getAgentRestClient(agent).post()
				.uri(uriBuilder -> uriBuilder
			      .path("/synchronize/perform")
			      .build())
				.contentType(MediaType.APPLICATION_JSON)
				.body(flowsData)
				.retrieve()
				.body(FullFlowsData.class);
				
	}
	
	private FullFlowsData retrieveFullFlowsData() {
		FullFlowsData fullFlowsData = new FullFlowsData(
				emailService.getAll(), flowService.getAll(), groupService.getAll(), 
				interfaceService.getAll(), modelService.getAll(), originService.getAll());
		
		return fullFlowsData;
	}

	
}

