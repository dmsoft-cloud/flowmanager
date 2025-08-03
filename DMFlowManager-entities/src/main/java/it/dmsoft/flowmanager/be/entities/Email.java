package it.dmsoft.flowmanager.be.entities;

import java.util.List;

import it.dmsoft.flowmanager.common.domain.Domains.YesNo;
import it.dmsoft.flowmanager.framework.converter.YesNoConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;

@Entity
public class Email extends BaseEntity {

	@Id
	@Column(name = "id", nullable = false, length = 20)
	private String id;
	
	@Column(name = "subject", nullable = false, length = 100)
	private String subject;
	
	@Lob
	@Column(name = "body_html", columnDefinition = "BLOB")
	private String bodyHtml;

	@Convert(converter = YesNoConverter.class)
	@Column(name = "enabled", nullable = false, length = 1)
	private YesNo enabled;
	

    @OneToMany(mappedBy = "email", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recipient> recipients;


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public String getBodyHtml() {
		return bodyHtml;
	}


	public void setBodyHtml(String bodyHtml) {
		this.bodyHtml = bodyHtml;
	}


	public YesNo getEnabled() {
		return enabled;
	}


	public void setEnabled(YesNo enabled) {
		this.enabled = enabled;
	}


	public List<Recipient> getRecipients() {
		return recipients;
	}


	public void setRecipients(List<Recipient> recipients) {
		this.recipients = recipients;
	}


	


}
