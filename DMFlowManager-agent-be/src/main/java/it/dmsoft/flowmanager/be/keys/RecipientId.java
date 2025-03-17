package it.dmsoft.flowmanager.be.keys;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class RecipientId implements Serializable {
	
	@Column(name = "email_id", length = 20) 
	private String emailId; // ID della Email (FK)
	
	@Column(name = "email_address", length = 100)
    private String emailAddress; // Indirizzo email del destinatario (PK)
	
    public RecipientId() {}

    public RecipientId(String emailId, String emailAddress) {
        this.emailId = emailId;
        this.emailAddress = emailAddress;
    }
    
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
    
	// Equals e HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipientId that = (RecipientId) o;
        return Objects.equals(emailId, that.emailId) &&
               Objects.equals(emailAddress, that.emailAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emailId, emailAddress);
    }
    

}
