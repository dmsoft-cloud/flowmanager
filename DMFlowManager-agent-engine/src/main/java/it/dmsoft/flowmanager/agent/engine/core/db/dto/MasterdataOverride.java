package it.dmsoft.flowmanager.agent.engine.core.db.dto;

import java.math.BigDecimal;
import java.util.List;

public class MasterdataOverride {

	private Otgffana fanaData;
	private String mailText;
	private List<String> mailDest;
	
	public Otgffana getFanaData() {
		return fanaData;
	}
	public void setFanaData(Otgffana fanaData) {
		this.fanaData = fanaData;
	}
	public String getMailText() {
		return mailText;
	}
	public void setMailText(String mailText) {
		this.mailText = mailText;
	}
	public List<String> getMailDest() {
		return mailDest;
	}
	public void setMailDest(List<String> mailDest) {
		this.mailDest = mailDest;
	}
	@Override
	public String toString() {
		return "MasterdataOverride [fanaData=" + fanaData + ", mailText=" + mailText + ", mailDest=" + mailDest + "]";
	}


	
	
}
