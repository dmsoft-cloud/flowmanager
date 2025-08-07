package it.dmsoft.flowmanager.common.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import it.dmsoft.flowmanager.common.domain.Domains.Status;




public class FlowLogDetailsData {
	
	public BigDecimal logProgrLog;
	public BigDecimal logProgrFase;
	public String logFase;
	public Timestamp logTs;
	public Status logEsito;
	public String logNote;
	
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
	public String getLogFase() {
		return logFase;
	}
	public void setLogFase(String logFase) {
		this.logFase = logFase;
	}
	public Timestamp getLogTs() {
		return logTs;
	}
	public void setLogTs(Timestamp logTs) {
		this.logTs = logTs;
	}
	public Status getLogEsito() {
		return logEsito;
	}
	public void setLogEsito(Status logEsito) {
		this.logEsito = logEsito;
	}
	public String getLogNote() {
		return logNote;
	}
	public void setLogNote(String logNote) {
		this.logNote = logNote;
	}
	
	@Override
	public String toString() {
		return "FlowLogDetailsData [logProgrLog=" + logProgrLog + ", logProgrFase=" + logProgrFase + ", logFase="
				+ logFase + ", logTs=" + logTs + ", logEsito=" + logEsito + ", logNote=" + logNote + "]";
	}
	


	
}
