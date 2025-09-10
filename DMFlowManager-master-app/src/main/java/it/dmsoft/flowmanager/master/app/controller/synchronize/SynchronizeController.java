package it.dmsoft.flowmanager.master.app.controller.synchronize;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.dmsoft.flowmanager.common.model.FullFlowsData;
import it.dmsoft.flowmanager.master.api.synchronize.SynchronizeDataService;
import jakarta.annotation.Resource;

//LEGGO la lista degli agent e propago se vuota vado su agent default ipotizzandolo sulla stessa macchina
@RestController
@RequestMapping("/synchronize")
@PreAuthorize("hasAnyAuthority('flowmanager_use')")
public class SynchronizeController {
	
	@Resource(name = "synchDataService")
    private SynchronizeDataService synchDataService;

	/**
     * Post request to refresh flow information into the agent.
     * @return SynchData synchData
     */
    @PostMapping("/perform")
    public FullFlowsData perform() {
        return synchDataService.synchronizeFullFlowsData();
    }
    
	/**
     * Post request to refresh flow information into the agent.
     * @param String agentId
     * @return SynchData synchData
     */
    @PostMapping("/perform/{agentId}")
    public FullFlowsData performAgent(@PathVariable(value = "agentId") String agentId) {
        return synchDataService.synchronizeFullFlowsData(agentId);
    }
}
