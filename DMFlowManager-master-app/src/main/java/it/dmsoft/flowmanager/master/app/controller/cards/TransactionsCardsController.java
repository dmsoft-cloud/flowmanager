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
import it.dmsoft.flowmanager.common.model.cards.TransactionStatsData;
import it.dmsoft.flowmanager.framework.api.base.BaseService;
import it.dmsoft.flowmanager.framework.bulk.BulkServiceInterface;
import it.dmsoft.flowmanager.master.api.cards.AgentStatsCardService;
import it.dmsoft.flowmanager.master.api.cards.TransactionStatsCardService;
import jakarta.annotation.Resource;



@RestController
@RequestMapping("/cards/transactionsStats")
@PreAuthorize("hasAnyAuthority('flowmanager_use')")
public class TransactionsCardsController {
	
	@Resource(name = "transactionStatsCardService")
    private TransactionStatsCardService transactionStatsCardService;

	@PostMapping
    public List<TransactionStatsData> getTransactionStats(
    		@RequestBody JsonNode body
    		) {
		
		
	    System.out.println("BODY: " + body.toPrettyString());

	    JsonNode params = body.get("params");

	    LocalDate from = (params != null && params.hasNonNull("fromDate"))
	            ? LocalDate.parse(params.get("fromDate").asText())
	            : LocalDate.now();

	    LocalDate to = (params != null && params.hasNonNull("toDate"))
	            ? LocalDate.parse(params.get("toDate").asText())
	            : null;

        List<TransactionStatsData> lst = transactionStatsCardService.getStats(from, to);
        return lst;
    }


}