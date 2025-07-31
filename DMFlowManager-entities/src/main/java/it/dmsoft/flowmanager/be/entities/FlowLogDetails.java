package it.dmsoft.flowmanager.be.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

import it.dmsoft.flowmanager.be.keys.FlowLogDetailsId;
import it.dmsoft.flowmanager.common.domain.Domains.Status;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
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
	
	@EmbeddedId
	private FlowLogDetailsId flowLogDetailsId;
	
	@Column(length = 10)
	public String logFase;
	public Timestamp logTs;
	@Column(length = 2)
	public Status logEsito;
	@Column(length = 255)
	public String logNote;
	

	public FlowLogDetailsId getFlowLogDetailsId() {
		return flowLogDetailsId;
	}
	public void setFlowLogDetailsId(FlowLogDetailsId flowLogDetailsId) {
		this.flowLogDetailsId = flowLogDetailsId;
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
		return "FlowLogDetails [flowLogDetailsId=" + flowLogDetailsId + ", logFase=" + logFase + ", logTs=" + logTs
				+ ", logEsito=" + logEsito + ", logNote=" + logNote + "]";
	}
	

	
}
