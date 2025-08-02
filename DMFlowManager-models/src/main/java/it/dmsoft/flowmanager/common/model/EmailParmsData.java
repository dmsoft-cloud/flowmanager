package it.dmsoft.flowmanager.common.model;

import java.math.BigDecimal;

import it.dmsoft.flowmanager.common.domain.Domains.YesNo;

public class EmailParmsData {
	
	private String smtpHost;

	private String smtpUser;

	private String smtpPassword;

	private YesNo smtpSecure;

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
		return "EmailParmsData [smtpHost=" + smtpHost + ", smtpUser=" + smtpUser + ", smtpPassword=" + smtpPassword
				+ ", smtpSecure=" + smtpSecure + ", smtpPort=" + smtpPort + "]";
	}

}
