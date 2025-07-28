package it.dmsoft.flowmanager.master.app.controller.synch;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.dmsoft.flowmanager.common.model.FullFlowsData;
import it.dmsoft.flowmanager.master.api.synch.SynchDataService;
import jakarta.annotation.Resource;
import jakarta.websocket.server.PathParam;

//LEGGO la lista degli agent e propago se vuota vado su agent default ipotizzandolo sulla stessa macchina
@RestController
@RequestMapping("/synch")
public class SynchController {
	
	@Resource(name = "synchDataService")
    private SynchDataService synchDataService;

	/**
     * Post request to refresh flow information into the agent.
     * @return SynchData synchData
     */
    @PostMapping("/execute")
    public FullFlowsData execute() {
        return synchDataService.synchFullFlowsData();
    }
    
	/**
     * Post request to refresh flow information into the agent.
     * @param String agentId
     * @return SynchData synchData
     */
    @PostMapping("/execute/agent")
    public FullFlowsData executeAgent(@PathParam(value = "agentId") String agentId) {
        return synchDataService.synchFullFlowsData(agentId);
    }
}
