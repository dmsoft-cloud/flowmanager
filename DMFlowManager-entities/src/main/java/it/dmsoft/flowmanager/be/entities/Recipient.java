package it.dmsoft.flowmanager.be.entities;

import it.dmsoft.flowmanager.be.keys.RecipientId;
import it.dmsoft.flowmanager.common.domain.Domains.RecipientType;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@IdClass(RecipientId.class) // Chiave composta
public class Recipient {

	//@EmbeddedId
    //private RecipientId id; // Chiave composta (emailId + emailAddress)
	
	@Id
    @Column(name = "email_id")
    private String emailId;

    @Id
    @Column(name = "email_address")
    private String emailAddress;	

	//@Convert(converter = RecipientType.class)
	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false, length = 3)
    private RecipientType type; // TO o CC


	@ManyToOne
	@JoinColumn(name = "email_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Email email; // Relazione con Email


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


	public RecipientType getType() {
		return type;
	}


	public void setType(RecipientType type) {
		this.type = type;
	}


	public Email getEmail() {
		return email;
	}


	public void setEmail(Email email) {
		this.email = email;
	}

	

}
