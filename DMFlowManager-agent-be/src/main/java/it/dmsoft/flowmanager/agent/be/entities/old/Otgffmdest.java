package it.dmsoft.flowmanager.agent.be.entities.old;

public class Otgffmdest {
	public static enum OtgffmdestCoulmn {

		MDEST_CODICE_MAIL, 
		MDEST_DESTINATARIO,
		MDEST_TIPO_DEST

	}

	private String mdest_Codice_Mail;
	private String mdest_Destinatario;
	private String mdest_Tipo_Dest;
	
	
	public String getMdest_Codice_Mail() {
		return mdest_Codice_Mail;
	}
	public void setMdest_Codice_Mail(String mdest_Codice_Mail) {
		this.mdest_Codice_Mail = mdest_Codice_Mail;
	}
	public String getMdest_Destinatario() {
		return mdest_Destinatario;
	}
	public void setMdest_Destinatario(String mdest_Destinatario) {
		this.mdest_Destinatario = mdest_Destinatario;
	}
	public String getMdest_Tipo_Dest() {
		return mdest_Tipo_Dest;
	}
	public void setMdest_Tipo_Dest(String mdest_Tipo_Dest) {
		this.mdest_Tipo_Dest = mdest_Tipo_Dest;
	}
	
	
	@Override
	public String toString() {
		return "Otgffmdest [mdest_Codice_Mail=" + mdest_Codice_Mail + ", mdest_Destinatario=" + mdest_Destinatario
				+ ", mdest_Tipo_Dest=" + mdest_Tipo_Dest + "]";
	}
	
	
	

	

}
