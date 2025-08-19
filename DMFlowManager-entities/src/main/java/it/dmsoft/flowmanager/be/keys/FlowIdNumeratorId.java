package it.dmsoft.flowmanager.be.keys;

import java.math.BigDecimal;
import java.util.Objects;

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

	@Override
	public int hashCode() {
		return Objects.hash(date, flowId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FlowIdNumeratorId other = (FlowIdNumeratorId) obj;
		return Objects.equals(date, other.date) && Objects.equals(flowId, other.flowId);
	}
	
}
