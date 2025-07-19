package it.dmsoft.flowmanager.agent.engine.core.utils;

import java.math.BigDecimal;

import it.dmsoft.flowmanager.agent.be.entities.FlowIdNumerator;
import it.dmsoft.flowmanager.agent.be.keys.FlowIdNumeratorId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;

public class FlowIdNumeratorUtils {
	
	public static BigDecimal getNextId(String transactionId, BigDecimal executionDate, BigDecimal transactionFilesCount, EntityManager entityManager) {
		FlowIdNumeratorId id = new FlowIdNumeratorId();
		id.setFlowId(transactionId);
		id.setDate(executionDate);
		
		FlowIdNumerator flowIdNumerator = entityManager.find(FlowIdNumerator.class, id, LockModeType.PESSIMISTIC_WRITE);
		
		if(flowIdNumerator == null) {
			flowIdNumerator = new FlowIdNumerator();
			flowIdNumerator.setFlowIdNumeratorId(id);
			flowIdNumerator.setIdProgressive(BigDecimal.ZERO);
			entityManager.persist(flowIdNumerator);
			entityManager.flush();
			return getNextId(transactionId, executionDate, transactionFilesCount, entityManager);
		}
		
		BigDecimal newId = flowIdNumerator.getIdProgressive().add(transactionFilesCount);
		flowIdNumerator.setIdProgressive(newId);
		entityManager.persist(flowIdNumerator);
		entityManager.flush();
		return newId;	
	}

}
