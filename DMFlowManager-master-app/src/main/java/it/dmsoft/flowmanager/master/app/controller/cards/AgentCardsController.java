package it.dmsoft.flowmanager.master.app.controller.cards;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import it.dmsoft.flowmanager.be.entities.Interface;
import it.dmsoft.flowmanager.be.filters.FlowFilterDTO;
import it.dmsoft.flowmanager.common.model.FlowData;
import it.dmsoft.flowmanager.common.model.InterfaceData;
import it.dmsoft.flowmanager.common.model.ModelData;
import it.dmsoft.flowmanager.common.model.cards.AgentStatsData;
import it.dmsoft.flowmanager.framework.api.base.BaseService;
import it.dmsoft.flowmanager.framework.bulk.BulkServiceInterface;
import it.dmsoft.flowmanager.master.api.cards.AgentStatsCardService;
import jakarta.annotation.Resource;



@RestController
@RequestMapping("/cards/agentStats")
@PreAuthorize("hasAnyAuthority('flowmanager_use')")
public class AgentCardsController {
	
	@Resource(name = "agentStatsCardService")
    private AgentStatsCardService agentStatsCardService;

	@PostMapping
    public List<AgentStatsData> getAgentStats(
    		@RequestBody JsonNode body
    		) {
		
		// agents opzionale (array)
        List<String> agents = null;

        if (body.has("agents") && body.get("agents").isArray()) {

            List<String> tmpAgents = new ArrayList<>();

            for (JsonNode node : body.get("agents")) {
                if (!node.isNull()) {
                    tmpAgents.add(node.asText());
                }
            }

            if (!tmpAgents.isEmpty()) {
                agents = tmpAgents;
            }
        }
		 // agent lo teniamo opzionale
        String agent = body.hasNonNull("agent") ? body.get("agent").asText() : null;

        // from: se null o mancante → oggi
        LocalDate from = body.hasNonNull("from") ? LocalDate.parse(body.get("from").asText()) : LocalDate.now();

        // to opzionale
        LocalDate to = body.hasNonNull("to") ? LocalDate.parse(body.get("to").asText()) : null;
        return agentStatsCardService.getStats(agents, from, to);
    }


}