package it.dmsoft.flowmanager.agent.be.entities;

import java.math.BigDecimal;

public class FlowHash {
	public static enum OtgffhashCoulmn {

		OTGFHASH_PROGR_LOGT,
		OTGFHASH_ID,
		OTGFHASH_FOOTPRINT
	}

	
	private String hashFootprint;
	private BigDecimal hashProgr_Logt;
	private String hashId;
	
	public BigDecimal getHashProgrLogt() {
		return hashProgr_Logt;
	}
	public void setHashProgrLogt(BigDecimal hashProgr_Logt) {
		this.hashProgr_Logt = hashProgr_Logt;
	}
	public String getHashId() {
		return hashId;
	}
	public void setHashId(String hashId) {
		this.hashId = hashId;
	}
	public String getHashFootprint() {
		return hashFootprint;
	}
	public void setHashFootprint(String hashFootprint) {
		this.hashFootprint = hashFootprint;
	}
	
	@Override
	public String toString() {
		return "Otgffhash [hashProgr_Logt=" + hashProgr_Logt + ", hashId=" + hashId
				+ ", hashFootprint=" + hashFootprint + "]";
	}

	
	
}
