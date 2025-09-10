package it.dmsoft.flowmanager.master.api.agent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import it.dmsoft.flowmanager.common.model.AgentData;
import it.dmsoft.flowmanager.framework.api.base.BaseMapper;
import it.dmsoft.flowmanager.framework.api.base.DefaultBaseService;
import it.dmsoft.flowmanager.master.api.agent.mapper.AgentMapper;
import it.dmsoft.flowmanager.master.repositories.AgentRepository;
import it.dmsoft.flowmanager.be.entities.Agent;

@Service("agentService")
public class DefaultAgentService extends DefaultBaseService<Agent, AgentData, String> {

	    private AgentRepository agentRepository;
	    
	    @Autowired
	    private AgentMapper agentMapper;

	    
	    public DefaultAgentService(AgentRepository agentRepository) {
			super();
			this.agentRepository = agentRepository;
		}


		@Override
		protected BaseMapper<Agent, AgentData> getMapper() {
			return agentMapper;
		}


		@Override
		protected JpaRepository<Agent, String> getRepository() {
			return agentRepository;
		}
	    
}
