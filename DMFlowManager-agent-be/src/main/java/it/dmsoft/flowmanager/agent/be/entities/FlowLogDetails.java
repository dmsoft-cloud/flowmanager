package it.dmsoft.flowmanager.agent.be.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class FlowLogDetails {
	public static enum OtgfflogdCoulmn {
		
		OTGFLOGD_PROGR_LOG,
		OTGFLOGD_PROGR_FASE,
		OTGFLOGD_FASE,
		OTGFLOGD_TS,
		OTGFLOGD_ESITO,
		OTGFLOGD_NOTE 	

	}
	

	public BigDecimal  otgflogd_Progr_Log;
	public BigDecimal  otgflogd_Progr_Fase;
	public String  	   otgflogd_Fase;
	public Timestamp   otgflogd_Ts;
	public String      otgflogd_Esito;
	public String      otgflogd_Note;
	
	
	public BigDecimal getLogDetProgrLog() {
		return otgflogd_Progr_Log;
	}
	public void setLogDetProgrLog(BigDecimal otgflogd_Progr_Log) {
		this.otgflogd_Progr_Log = otgflogd_Progr_Log;
	}
	public BigDecimal getLogDetProgrFase() {
		return otgflogd_Progr_Fase;
	}
	public void setLogDetProgrFase(BigDecimal otgflogd_Progr_Fase) {
		this.otgflogd_Progr_Fase = otgflogd_Progr_Fase;
	}
	public String getLogDetFase() {
		return otgflogd_Fase;
	}
	public void setLogDetFase(String otgflogd_Fase) {
		this.otgflogd_Fase = otgflogd_Fase;
	}
	public 	Timestamp getLogDetTs() {
		return otgflogd_Ts;
	}
	public void setLogDetTs(Timestamp otgflogd_Ts) {
		this.otgflogd_Ts = otgflogd_Ts;
	}
	public String getLogDetEsito() {
		return otgflogd_Esito;
	}
	public void setLogDetEsito(String otgflogd_Esito) {
		this.otgflogd_Esito = otgflogd_Esito;
	}
	public String getLogDetNote() {
		return otgflogd_Note;
	}
	public void setLogDetNote(String otgflogd_Note) {
		this.otgflogd_Note = otgflogd_Note;
	}
	
	@Override
	public String toString() {
		return "Otgfflogd [otgflogd_Progr_Log=" + otgflogd_Progr_Log + ", otgflogd_Progr_Fase=" + otgflogd_Progr_Fase
				+ ", otgflogd_Fase=" + otgflogd_Fase + ", otgflogd_Ts=" + otgflogd_Ts + ", otgflogd_Esito="
				+ otgflogd_Esito + ", otgflogd_Note=" + otgflogd_Note + "]";
	}
	
	
	
	
	

	
	
	

}
