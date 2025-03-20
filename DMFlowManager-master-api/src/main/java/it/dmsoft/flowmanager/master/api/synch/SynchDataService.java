package it.dmsoft.flowmanager.master.api.synch;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import it.dmsoft.flowmanager.common.model.AgentData;
import it.dmsoft.flowmanager.common.model.EmailData;
import it.dmsoft.flowmanager.common.model.FlowData;
import it.dmsoft.flowmanager.common.model.FullFlowsData;
import it.dmsoft.flowmanager.common.model.GroupData;
import it.dmsoft.flowmanager.common.model.InterfaceData;
import it.dmsoft.flowmanager.common.model.ModelData;
import it.dmsoft.flowmanager.common.model.OriginData;
import it.dmsoft.flowmanager.framework.api.base.BaseService;
import it.dmsoft.flowmanager.master.be.entities.Agent;
import it.dmsoft.flowmanager.master.be.entities.ConfigurationGroup;
import it.dmsoft.flowmanager.master.be.entities.Email;
import it.dmsoft.flowmanager.master.be.entities.Flow;
import it.dmsoft.flowmanager.master.be.entities.Interface;
import it.dmsoft.flowmanager.master.be.entities.Model;
import it.dmsoft.flowmanager.master.be.entities.Origin;
import jakarta.annotation.Resource;

@Service("synchDataService")
public class SynchDataService {

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

	public FullFlowsData synchFullFlowsData() {
		List<AgentData> agents = agentService.getAll();
		FullFlowsData flowsData = retrieveFullFlowsData();
		
		if (agents == null || agents.size() == 0) {
			agents = Arrays.asList(AgentData.DEFAULT_AGENT);
		}
		
		for (AgentData agent : agents) {
			synchFullFlowsData(agent, flowsData);
		}
		
		return flowsData;
	}

	public FullFlowsData synchFullFlowsData(String agentId) {
		AgentData agent = agentService.getById(agentId);		
		return synchFullFlowsData(agent, null);
	}
	
	
	public FullFlowsData synchFullFlowsData(AgentData agent, FullFlowsData flowsData) {
		
		return flowsData;
	}
	
	private FullFlowsData retrieveFullFlowsData() {
		FullFlowsData fullFlowsData = new FullFlowsData(
				emailService.getAll(), flowService.getAll(), groupService.getAll(), 
				interfaceService.getAll(), modelService.getAll(), originService.getAll());
		
		return fullFlowsData;
	}

	
}

