package it.dmsoft.flowmanager.api.interfaces.model;

import it.dmsoft.flowmanager.be.common.BaseEntity.ConnectionType;
import it.dmsoft.flowmanager.be.common.BaseEntity.YesNo;

public class InterfaceData {

	// NOME
	// DESCRIZIONE
	// NOTE
	// ATTIVO
	// TIPO CONNESSIONE SFTP FTP SCP SMTP IMAP (ricordarsi che nell'otgffana il
	// campo per le mail va messo su tipo trasferimento)
	// PASSIVE MODE
	// SECURE FTP STATUS
	// HOST
	// PORT
	// USER
	// PASSWORD
	// SFTP_USER/ALIAS
	// KNOWN HOST FILE
	// KEY FILE
	// TRUST ALWAYS HOST
	// STATUS ENABLED/DISABLED -> c'è già il flag attivo

	private String id;
	private String description;
	private ConnectionType connectionType;
	private YesNo passiveMode;
	private YesNo secureFtp;
	private String host;
	private Integer port;
	private String user;
	private String password;
	private String sftpAlias;
	private String knownHost;
	private String keyFile;
	private YesNo trustHost;
	private YesNo enabled;
	private String note;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public YesNo getEnabled() {
		return enabled;
	}

	public void setEnabled(YesNo enabled) {
		this.enabled = enabled;
	}


	public ConnectionType getConnectionType() {
		return connectionType;
	}

	public void setConnectionType(ConnectionType connectionType) {
		this.connectionType = connectionType;
	}

	public YesNo getPassiveMode() {
		return passiveMode;
	}

	public void setPassiveMode(YesNo passiveMode) {
		this.passiveMode = passiveMode;
	}

	public YesNo getSecureFtp() {
		return secureFtp;
	}

	public void setSecureFtp(YesNo secureFtp) {
		this.secureFtp = secureFtp;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSftpAlias() {
		return sftpAlias;
	}

	public void setSftpAlias(String sftpAlias) {
		this.sftpAlias = sftpAlias;
	}


	public String getKnownHost() {
		return knownHost;
	}

	public void setKnownHost(String knownHost) {
		this.knownHost = knownHost;
	}

	public YesNo getTrustHost() {
		return trustHost;
	}

	public void setTrustHost(YesNo trustHost) {
		this.trustHost = trustHost;
	}

	public String getKeyFile() {
		return keyFile;
	}

	public void setKeyFile(String keyFile) {
		this.keyFile = keyFile;
	}

	
}
