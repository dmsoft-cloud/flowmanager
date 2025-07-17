package it.dmsoft.flowmanager.agent.engine.core.db.dto;

import java.math.BigDecimal;

public class Ccschdt {
	
	public static enum CcschdtColumn {

		CCSCH_DATA_DA, 
		CCSCH_DATA

	}

	private BigDecimal ccscch_Data_Da;
	
	private BigDecimal ccscch_Data;

	public BigDecimal getCcscch_Data_Da() {
		return ccscch_Data_Da;
	}

	public void setCcscch_Data_Da(BigDecimal ccscch_Data_Da) {
		this.ccscch_Data_Da = ccscch_Data_Da;
	}

	public BigDecimal getCcscch_Data() {
		return ccscch_Data;
	}

	public void setCcscch_Data(BigDecimal ccscch_Data) {
		this.ccscch_Data = ccscch_Data;
	}

	@Override
	public String toString() {
		return "Ccschdt [ccscch_Data_Da=" + ccscch_Data_Da + ", ccscch_Data=" + ccscch_Data + "]";
	}
	

	

}
