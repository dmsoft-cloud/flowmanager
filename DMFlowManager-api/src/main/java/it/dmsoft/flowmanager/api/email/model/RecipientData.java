package it.dmsoft.flowmanager.api.email.model;

import it.dmsoft.flowmanager.be.common.BaseEntity.RecipientType;
import it.dmsoft.flowmanager.be.common.BaseEntity.YesNo;
import it.dmsoft.flowmanager.be.keys.RecipientId;

public class RecipientData {
	
    private String emailId;      // Chiave dell'email
    private String emailAddress; // Indirizzo email del destinatario
    private String type;  // Tipo di destinatario (TO, CC, BCC)
    
    public String getEmailId() {
		return emailId;
	}
    public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
    public String getEmailAddress() {
		return emailAddress;
	}
    public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
    public String getType() {
		return type;
	}
    public void setType(String type) {
		this.type = type;
	}

	
    	

}
