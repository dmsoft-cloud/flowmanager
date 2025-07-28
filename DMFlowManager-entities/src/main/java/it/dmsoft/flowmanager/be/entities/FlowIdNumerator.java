package it.dmsoft.flowmanager.be.entities;

import java.math.BigDecimal;

import it.dmsoft.flowmanager.be.keys.FlowIdNumeratorId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class FlowIdNumerator {
	
	@EmbeddedId
	private FlowIdNumeratorId flowIdNumeratorId;
	
	@Column(length = 12)
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
