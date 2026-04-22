package it.dmsoft.flowmanager.master.api.cards;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import it.dmsoft.flowmanager.common.model.cards.AgentStatsData;
import it.dmsoft.flowmanager.common.model.cards.TransactionStatsData;
import it.dmsoft.flowmanager.master.api.interfaces.mapper.InterfaceMapper;
import it.dmsoft.flowmanager.master.repositories.FlowLogRepository;

@Service("transactionStatsCardService")
public class TransactionStatsCardService {

		private final FlowLogRepository flowLogRepository;

	    public TransactionStatsCardService(FlowLogRepository flowLogRepository) {
	    	 this.flowLogRepository = flowLogRepository;
		}


	    public List<TransactionStatsData> getStats(
	            LocalDate fromDate,
	            LocalDate toDate
	        ) {
	    	

	            LocalDateTime from = fromDate.atStartOfDay();
	            LocalDateTime to = toDate != null
	                ? toDate.atTime(23, 59, 59)
	                : LocalDate.now().atTime(23, 59, 59);

	            return flowLogRepository.aggregateByDayDirection(
	                from,
	                to
	            );
	     }

}
