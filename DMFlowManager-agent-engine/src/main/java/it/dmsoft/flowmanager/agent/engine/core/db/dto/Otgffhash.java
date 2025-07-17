package it.dmsoft.flowmanager.agent.engine.core.db.dto;

import java.math.BigDecimal;

public class Otgffhash {
	public static enum OtgffhashCoulmn {

		OTGFHASH_PROGR_LOGT,
		OTGFHASH_ID,
		OTGFHASH_FOOTPRINT
	}

	
	private BigDecimal otgfhash_Progr_Logt;
	private String otgfhash_Id;
	private String otgfhash_Footprint;
	
	public BigDecimal getOtgfhash_Progr_Logt() {
		return otgfhash_Progr_Logt;
	}
	public void setOtgfhash_Progr_Logt(BigDecimal otgfhash_Progr_Logt) {
		this.otgfhash_Progr_Logt = otgfhash_Progr_Logt;
	}
	public String getOtgfhash_Id() {
		return otgfhash_Id;
	}
	public void setOtgfhash_Id(String otgfhash_Id) {
		this.otgfhash_Id = otgfhash_Id;
	}
	public String getOtgfhash_Footprint() {
		return otgfhash_Footprint;
	}
	public void setOtgfhash_Footprint(String otgfhash_Footprint) {
		this.otgfhash_Footprint = otgfhash_Footprint;
	}
	
	@Override
	public String toString() {
		return "Otgffhash [otgfhash_Progr_Logt=" + otgfhash_Progr_Logt + ", otgfhash_Id=" + otgfhash_Id
				+ ", otgfhash_Footprint=" + otgfhash_Footprint + "]";
	}

	
	
}
