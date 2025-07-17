package it.dmsoft.flowmanager.agent.engine.core.db.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Otgfflogd {
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
	
	
	public BigDecimal getOtgflogd_Progr_Log() {
		return otgflogd_Progr_Log;
	}
	public void setOtgflogd_Progr_Log(BigDecimal otgflogd_Progr_Log) {
		this.otgflogd_Progr_Log = otgflogd_Progr_Log;
	}
	public BigDecimal getOtgflogd_Progr_Fase() {
		return otgflogd_Progr_Fase;
	}
	public void setOtgflogd_Progr_Fase(BigDecimal otgflogd_Progr_Fase) {
		this.otgflogd_Progr_Fase = otgflogd_Progr_Fase;
	}
	public String getOtgflogd_Fase() {
		return otgflogd_Fase;
	}
	public void setOtgflogd_Fase(String otgflogd_Fase) {
		this.otgflogd_Fase = otgflogd_Fase;
	}
	public 	Timestamp getOtgflogd_Ts() {
		return otgflogd_Ts;
	}
	public void setOtgflogd_Ts(Timestamp otgflogd_Ts) {
		this.otgflogd_Ts = otgflogd_Ts;
	}
	public String getOtgflogd_Esito() {
		return otgflogd_Esito;
	}
	public void setOtgflogd_Esito(String otgflogd_Esito) {
		this.otgflogd_Esito = otgflogd_Esito;
	}
	public String getOtgflogd_Note() {
		return otgflogd_Note;
	}
	public void setOtgflogd_Note(String otgflogd_Note) {
		this.otgflogd_Note = otgflogd_Note;
	}
	
	@Override
	public String toString() {
		return "Otgfflogd [otgflogd_Progr_Log=" + otgflogd_Progr_Log + ", otgflogd_Progr_Fase=" + otgflogd_Progr_Fase
				+ ", otgflogd_Fase=" + otgflogd_Fase + ", otgflogd_Ts=" + otgflogd_Ts + ", otgflogd_Esito="
				+ otgflogd_Esito + ", otgflogd_Note=" + otgflogd_Note + "]";
	}
	
	
	
	
	

	
	
	

}
