package it.dmsoft.flowmanager.agent.engine.core.db.dto;

import java.math.BigDecimal;

public class Otgffprog {
	
	public static enum OtgffprogColumn {

		OTGFPROG_ID_TRANSAZIONE, 
		OTGFPROG_DATA,
		OTGFPROG_PROGRESSIVO

	}

	private String otgfprog_Id_Transazione;
	
	private BigDecimal otgfprog_Data;
	
	private BigDecimal otgfprog_Progressivo;
	

	public String getOtgfprog_Id_Transazione() {
		return otgfprog_Id_Transazione;
	}

	public void setOtgfprog_Id_Transazione(String otgfprog_id_transazione) {
		this.otgfprog_Id_Transazione = otgfprog_id_transazione;
	}

	public BigDecimal getOtgfprog_Data() {
		return otgfprog_Data;
	}

	public void setOtgfprog_Data(BigDecimal otgfprog_data) {
		this.otgfprog_Data = otgfprog_data;
	}

	public BigDecimal getOtgfprog_Progressivo() {
		return otgfprog_Progressivo;
	}

	public void setOtgfprog_Progressivo(BigDecimal otgfprog_progressivo) {
		this.otgfprog_Progressivo = otgfprog_progressivo;
	}

	@Override
	public String toString() {
		return "Otgffprog [otgfprog_id_transazione=" + otgfprog_Id_Transazione + ", otgfprog_data=" + otgfprog_Data
				+ ", otgfprog_progressivo=" + otgfprog_Progressivo + "]";
	}
	
	

}
