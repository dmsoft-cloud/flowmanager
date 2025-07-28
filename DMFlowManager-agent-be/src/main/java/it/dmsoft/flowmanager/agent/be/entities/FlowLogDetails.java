package it.dmsoft.flowmanager.agent.be.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

import it.dmsoft.flowmanager.common.domain.Domains.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class FlowLogDetails {
	public static enum OtgfflogdCoulmn {
		
		OTGFLOGD_PROGR_LOG,
		OTGFLOGD_PROGR_FASE,
		OTGFLOGD_FASE,
		OTGFLOGD_TS,
		OTGFLOGD_ESITO,
		OTGFLOGD_NOTE 	

	}
	

	@Id
	@Column(length = 12)
	public BigDecimal logProgrLog;
	@Column(length = 3)
	public BigDecimal logProgrFase;
	@Column(length = 10)
	public String logFase;
	public Timestamp logTs;
	@Column(length = 2)
	public Status logEsito;
	@Column(length = 255)
	public String logNote;
	
	public BigDecimal getLogDetProgrLog() {
		return logProgrLog;
	}
	public void setLogDetProgrLog(BigDecimal logProgrLog) {
		this.logProgrLog = logProgrLog;
	}
	public BigDecimal getLogDetProgrFase() {
		return logProgrFase;
	}
	public void setLogDetProgrFase(BigDecimal logProgrFase) {
		this.logProgrFase = logProgrFase;
	}
	public String getLogDetFase() {
		return logFase;
	}
	public void setLogDetFase(String logFase) {
		this.logFase = logFase;
	}
	public 	Timestamp getLogDetTs() {
		return logTs;
	}
	public void setLogDetTs(Timestamp logTs) {
		this.logTs = logTs;
	}
	public Status getLogDetEsito() {
		return logEsito;
	}
	public void setLogDetEsito(Status logEsito) {
		this.logEsito = logEsito;
	}
	public String getLogDetNote() {
		return logNote;
	}
	public void setLogDetNote(String logNote) {
		this.logNote = logNote;
	}
	
	@Override
	public String toString() {
		return "Otgfflogd [logProgrLog=" + logProgrLog + ", logProgrFase=" + logProgrFase
				+ ", logFase=" + logFase + ", logTs=" + logTs + ", logEsito="
				+ logEsito + ", logNote=" + logNote + "]";
	}
	
}
