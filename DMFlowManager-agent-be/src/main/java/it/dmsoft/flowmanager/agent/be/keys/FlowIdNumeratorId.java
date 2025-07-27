package it.dmsoft.flowmanager.agent.be.keys;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class FlowIdNumeratorId {
	
	@Column(length = 8)
	private BigDecimal date;
	@Column(length = 20)
	private String flowId;

	public BigDecimal getDate() {
		return date;
	}

	public void setDate(BigDecimal date) {
		this.date = date;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
	
	

}
