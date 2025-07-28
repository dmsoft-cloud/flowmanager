package it.dmsoft.flowmanager.be.entities;

import java.math.BigDecimal;

import it.dmsoft.flowmanager.common.domain.Domains.YesNo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class MailParms {

	@Id
	@Column(length = 128)
	private String smtphost;
	@Column(length = 64)
	private String smtpuser;
	@Column(length = 64)
	private String smtppassword;
	@Column(length = 1)
	private YesNo smtpsecure;
	@Column(length = 5)
	private BigDecimal smtpport;
	
	public String getSmtpHost() {
		return smtphost;
	}
	public void setSmtpHost(String smtphost) {
		this.smtphost = smtphost;
	}
	public String getSmtpUser() {
		return smtpuser;
	}
	public void setSmtpUser(String smtpuser) {
		this.smtpuser = smtpuser;
	}
	public String getSmtpPassword() {
		return smtppassword;
	}
	public void setSmtpPassword(String smtppassword) {
		this.smtppassword = smtppassword;
	}
	public YesNo getSmtpSecure() {
		return smtpsecure;
	}
	public void setSmtpSecure(YesNo smtpsecure) {
		this.smtpsecure = smtpsecure;
	}
	
	public BigDecimal getSmtpPort() {
		return smtpport;
	}
	public void setSmtpPort(BigDecimal smtpport) {
		this.smtpport = smtpport;
	}
	
	@Override
	public String toString() {
		return "MailParms [smtphost=" + smtphost + ", smtpuser=" + smtpuser + ", smtppassword=" + smtppassword
				+ ", smtpsecure=" + smtpsecure + ", smtpport=" + smtpport + "]";
	}
	
	
	
}
