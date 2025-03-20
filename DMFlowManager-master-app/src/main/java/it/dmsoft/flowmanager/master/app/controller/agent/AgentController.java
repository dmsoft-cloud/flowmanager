package it.dmsoft.flowmanager.master.app.controller.agent;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.dmsoft.flowmanager.common.model.AgentData;
import it.dmsoft.flowmanager.framework.api.base.BaseService;
import it.dmsoft.flowmanager.master.be.entities.Agent;
import jakarta.annotation.Resource;

//@CrossAgent(agents = "http://localhost:4200") 
//@CrossAgent(agents = "#{'${cors.allowed.agents}'}")
@RestController
@RequestMapping("/agents")
public class AgentController {
	
	@Value("${cors.allowed.agents}")
    private String allowedAgents;

    @Resource(name = "agentService")
    private BaseService<Agent, AgentData, String> agentService;

    /**
     * <p>Get all agent data in the system.For production system you many want to use
     * pagination.</p>
     * @return List<AgentData>
     */
    @GetMapping
    public List<AgentData> getAgents() {
        return agentService.getAll();
    }

    /**
     * Method to get the agent data based on the ID.
     * @param id
     * @return AgentData
     */
    @GetMapping("/agent/{id}")
    public AgentData getAgent(@PathVariable String id) {
        return agentService.getById(id);
    }

    /**
     * Post request to create agent information int the system.
     * @param agentData
     * @return
     */
    @PostMapping("/agent")
    public AgentData saveAgent(final @RequestBody AgentData agentData) {
        return agentService.save(agentData);
    }

    /**
     * <p>Delete agent from the system based on the ID. The method mapping is like the getAgent with difference of
     * @DeleteMapping and @GetMapping</p>
     * @param id
     * @return
     */
    @DeleteMapping("/agent/{id}")
    public Boolean deleteAgent(@PathVariable String id) {
        return agentService.delete(id);
    }

}