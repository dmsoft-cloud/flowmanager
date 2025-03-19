package it.dmsoft.flowmanager.agent.app.controller.synch;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.dmsoft.flowmanager.agent.api.flows.FlowDataService;
import it.dmsoft.flowmanager.common.model.FullFlowsData;
import jakarta.annotation.Resource;

@RestController
@RequestMapping("/synch")
public class SynchController {
	
	@Resource(name = "synchService")
    private FlowDataService synchService;
	
	//scarico configurazioni flussi
	//NOTA in fase di update, blocco l'esecuzione di nuovi flussi
	
	//recupero log di esecuzione (delta, totale) magari in futuro gestire modalità più strutturate
	
	/**
     * Post request to refresh flow information into the agent.
     * @param FullFlowsData synchData
     * @return SynchData synchData
     */
    @PostMapping("/execute")
    public FullFlowsData execute(final @RequestBody FullFlowsData flowsData) {
        return synchService.storeFullFlowsData(flowsData);
    }

    //uuid per progressivo log
    //recupero totale o parziale con uuid (se non trovo errore)
    
}
