package it.dmsoft.flowmanager.be.entities;

import java.math.BigDecimal;

import it.dmsoft.flowmanager.common.domain.Domains.YesNo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class EmailParms {

	@Id
	@Column(length = 128)
	private String smtpHost;
	@Column(length = 64)
	private String smtpUser;
	@Column(length = 64)
	private String smtpPassword;
	@Column(length = 1)
	private YesNo smtpSecure;
	@Column(length = 5)
	private BigDecimal smtpPort;
	public String getSmtpHost() {
		return smtpHost;
	}
	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}
	public String getSmtpUser() {
		return smtpUser;
	}
	public void setSmtpUser(String smtpUser) {
		this.smtpUser = smtpUser;
	}
	public String getSmtpPassword() {
		return smtpPassword;
	}
	public void setSmtpPassword(String smtpPassword) {
		this.smtpPassword = smtpPassword;
	}
	public YesNo getSmtpSecure() {
		return smtpSecure;
	}
	public void setSmtpSecure(YesNo smtpSecure) {
		this.smtpSecure = smtpSecure;
	}
	public BigDecimal getSmtpPort() {
		return smtpPort;
	}
	public void setSmtpPort(BigDecimal smtpPort) {
		this.smtpPort = smtpPort;
	}
	@Override
	public String toString() {
		return "EmailParms [smtpHost=" + smtpHost + ", smtpUser=" + smtpUser + ", smtpPassword=" + smtpPassword
				+ ", smtpSecure=" + smtpSecure + ", smtpPort=" + smtpPort + "]";
	}
	
}
