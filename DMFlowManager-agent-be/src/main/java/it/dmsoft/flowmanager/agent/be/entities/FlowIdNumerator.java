package it.dmsoft.flowmanager.agent.be.entities;

import java.math.BigDecimal;

import it.dmsoft.flowmanager.agent.be.keys.FlowIdNumeratorId;

public class FlowIdNumerator {
	
	private FlowIdNumeratorId flowIdNumeratorId;
	
	private BigDecimal idProgressive;
	
	public BigDecimal getIdProgressive() {
		return idProgressive;
	}

	public void setIdProgressive(BigDecimal idProgressive) {
		this.idProgressive = idProgressive;
	}

	public FlowIdNumeratorId getFlowIdNumeratorId() {
		return flowIdNumeratorId;
	}

	public void setFlowIdNumeratorId(FlowIdNumeratorId flowIdNumeratorId) {
		this.flowIdNumeratorId = flowIdNumeratorId;
	}

	

}
