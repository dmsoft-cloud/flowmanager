package it.dmsoft.flowmanager.master.api.cards;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import it.dmsoft.flowmanager.common.model.cards.AgentStatsData;
import it.dmsoft.flowmanager.master.api.interfaces.mapper.InterfaceMapper;
import it.dmsoft.flowmanager.master.repositories.FlowLogRepository;

@Service("agentStatsCardService")
public class AgentStatsCardService {

		private final FlowLogRepository flowLogRepository;

	    public AgentStatsCardService(FlowLogRepository flowLogRepository) {
	    	 this.flowLogRepository = flowLogRepository;
		}


	    public List<AgentStatsData> getStats(
	    		List<String> agents,
	            LocalDate fromDate,
	            LocalDate toDate
	        ) {
	    	
		    	if (agents != null && agents.isEmpty()) {
		            agents = null; // forza comportamento "tutti gli agent"
		        }

	            LocalDateTime from = fromDate.atStartOfDay();
	            LocalDateTime to = toDate != null
	                ? toDate.atTime(23, 59, 59)
	                : null;

	            return flowLogRepository.aggregateByAgentDirectionStatus(
	            	agents,
	                from,
	                to
	            );
	     }

}
