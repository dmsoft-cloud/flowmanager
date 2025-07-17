package it.dmsoft.flowmanager.agent.engine.core.db.dto;

public class MailParms {

	private String smtp_host;
	private String smtp_user;
	private String smtp_password;
	private String smtp_secure;
	private String smtp_port;
	
	public String getSmtp_host() {
		return smtp_host;
	}
	public void setSmtp_host(String smtp_host) {
		this.smtp_host = smtp_host;
	}
	public String getSmtp_user() {
		return smtp_user;
	}
	public void setSmtp_user(String smtp_user) {
		this.smtp_user = smtp_user;
	}
	public String getSmtp_password() {
		return smtp_password;
	}
	public void setSmtp_password(String smtp_password) {
		this.smtp_password = smtp_password;
	}
	public String getSmtp_secure() {
		return smtp_secure;
	}
	public void setSmtp_secure(String smtp_secure) {
		this.smtp_secure = smtp_secure;
	}
	
	public String getSmtp_port() {
		return smtp_port;
	}
	public void setSmtp_port(String smtp_port) {
		this.smtp_port = smtp_port;
	}
	
	@Override
	public String toString() {
		return "MailParms [smtp_host=" + smtp_host + ", smtp_user=" + smtp_user + ", smtp_password=" + smtp_password
				+ ", smtp_secure=" + smtp_secure + ", smtp_port=" + smtp_port + "]";
	}
	
	
	
}
