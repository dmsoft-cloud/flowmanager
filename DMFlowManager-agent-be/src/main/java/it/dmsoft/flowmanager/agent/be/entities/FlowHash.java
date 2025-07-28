package it.dmsoft.flowmanager.agent.be.entities;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class FlowHash {
	public static enum OtgffhashCoulmn {

		OTGFHASH_PROGR_LOGT,
		OTGFHASH_ID,
		OTGFHASH_FOOTPRINT
	}

	@Id
	@Column(length = 20)
	private String hashId;
	@Column(length = 64)
	private String hashFootprint;
	@Column(length = 12)
	private BigDecimal hashProgrLogt;
	
	public BigDecimal getHashProgrLogt() {
		return hashProgrLogt;
	}
	public void setHashProgrLogt(BigDecimal hashProgrLogt) {
		this.hashProgrLogt = hashProgrLogt;
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
		return "Otgffhash [hashProgr_Logt=" + hashProgrLogt + ", hashId=" + hashId
				+ ", hashFootprint=" + hashFootprint + "]";
	}

	
	
}
