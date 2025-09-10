package it.dmsoft.flowmanager.master.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.dmsoft.flowmanager.be.entities.FlowLogDetails;
import it.dmsoft.flowmanager.be.keys.FlowLogDetailsId;

@Repository
public interface FlowLogDetailsRepository extends JpaRepository<FlowLogDetails, FlowLogDetailsId> {
	
	List<FlowLogDetails> findByFlowLogDetailsIdLogProgrLog(BigDecimal logProgrLog);
}
