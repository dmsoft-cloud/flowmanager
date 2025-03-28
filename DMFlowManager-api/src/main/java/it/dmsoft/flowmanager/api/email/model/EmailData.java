package it.dmsoft.flowmanager.api.email.model;

import java.util.List;

import it.dmsoft.flowmanager.api.base.BaseModel;
import it.dmsoft.flowmanager.be.common.BaseEntity.YesNo;

public class EmailData extends BaseModel {
	
    private String id;
    private String subject;
    private String bodyHtml;
	private YesNo enabled;
	private String note;
	private List<RecipientData> recipients;
	
	public  String getId() {
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
	
	public List<RecipientData> getRecipients() {
		return recipients;
	}
	
	public void setRecipients(List<RecipientData> recipients) {
		this.recipients = recipients;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	

	
	

}
