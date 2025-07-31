package it.dmsoft.flowmanager.be.keys;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class FlowLogDetailsId {
	
	@Column(length = 12)
	public BigDecimal logProgrLog;

	@Column(length = 3)
	public BigDecimal logProgrFase;

	public BigDecimal getLogProgrLog() {
		return logProgrLog;
	}

	public void setLogProgrLog(BigDecimal logProgrLog) {
		this.logProgrLog = logProgrLog;
	}

	public BigDecimal getLogProgrFase() {
		return logProgrFase;
	}

	public void setLogProgrFase(BigDecimal logProgrFase) {
		this.logProgrFase = logProgrFase;
	}

	@Override
	public String toString() {
		return "FlowLogDetailsId [logProgrLog=" + logProgrLog + ", logProgrFase=" + logProgrFase + "]";
	}

	
}
