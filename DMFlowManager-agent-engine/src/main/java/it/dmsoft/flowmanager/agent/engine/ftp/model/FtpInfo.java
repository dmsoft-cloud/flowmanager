package it.dmsoft.flowmanager.agent.engine.ftp.model;

public class FtpInfo {
	private FtpInfoCodice codice;
	private String descrizione;
	
	public FtpInfo() {
		super();
	}
	
	public FtpInfo(FtpInfoCodice codice, String descrizione) {
		super();
		this.codice = codice;
		this.descrizione = descrizione;
	}

	public FtpInfoCodice getCodice() {
		return codice;
	}

	public void setCodice(FtpInfoCodice codice) {
		this.codice = codice;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
}
