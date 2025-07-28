package it.dmsoft.flowmanager.be.entities.old;

public class Otgffmail {
	public static enum OtgffmailCoulmn {

		MAIL_CODICE, 
		MAIL_TESTO,
		MAIL_OGGETTO

	}

	private String mail_Codice;
	private String mail_Testo;
	private String mail_Oggetto;
	
	
	public String getMail_Codice() {
		return mail_Codice;
	}
	public void setMail_Codice(String mail_Codice) {
		this.mail_Codice = mail_Codice;
	}
	public String getMail_Testo() {
		return mail_Testo;
	}
	public void setMail_Testo(String mail_Testo) {
		this.mail_Testo = mail_Testo;
	}
	public String getMail_Oggetto() {
		return mail_Oggetto;
	}
	public void setMail_Oggetto(String mail_Oggetto) {
		this.mail_Oggetto = mail_Oggetto;
	}
	
	
	@Override
	public String toString() {
		return "Otgffmail [mail_Codice=" + mail_Codice + ", mail_Testo=" + mail_Testo + ", mail_Oggetto=" + mail_Oggetto
				+ "]";
	}

		

}
