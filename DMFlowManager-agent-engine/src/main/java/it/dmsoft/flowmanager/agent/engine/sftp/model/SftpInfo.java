package it.dmsoft.flowmanager.agent.engine.sftp.model;

public class SftpInfo {
	private SftpInfoCodice codice;
	private String descrizione;
	
	public SftpInfo() {
		super();
	}
	
	public SftpInfo(SftpInfoCodice codice, String descrizione) {
		super();
		this.codice = codice;
		this.descrizione = descrizione;
	}

	public SftpInfoCodice getCodice() {
		return codice;
	}

	public void setCodice(SftpInfoCodice codice) {
		this.codice = codice;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
}
