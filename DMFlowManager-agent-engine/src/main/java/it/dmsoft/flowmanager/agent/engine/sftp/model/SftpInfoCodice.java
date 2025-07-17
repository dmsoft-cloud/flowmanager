package it.dmsoft.flowmanager.agent.engine.sftp.model;

public enum SftpInfoCodice {
	NON_SCARICATO("NON_SCARICATO"), NON_ELIMINATO("NON_ELIMINATO");

	private String as400Codice;
	
	private SftpInfoCodice(String as400Codice) {
		this.as400Codice = as400Codice;
	}
	
	public String value() {
		return this.as400Codice;
	}
}
