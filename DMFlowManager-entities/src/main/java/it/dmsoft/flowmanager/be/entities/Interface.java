package it.dmsoft.flowmanager.be.entities;

import it.dmsoft.flowmanager.common.domain.Domains.ConnectionType;
import it.dmsoft.flowmanager.common.domain.Domains.YesNo;
import it.dmsoft.flowmanager.framework.converter.ConnectionTypeConverter;
import it.dmsoft.flowmanager.framework.converter.YesNoConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Interface {

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

	@Id
	@Column(length = 20)
	private String id;
	@Column(length = 255)
	private String description;
	@Convert(converter = ConnectionTypeConverter.class)
	@Column(length = 1)
	private ConnectionType connectionType;
	@Convert(converter = YesNoConverter.class)
	@Column(length = 1)
	private YesNo passiveMode;
	@Convert(converter = YesNoConverter.class)
	@Column(length = 1)
	private YesNo secureFtp;
	@Column(length = 255)
	private String host;
	private Integer port;
	@Column(length = 64)
	private String user;
	@Column(length = 64)
	private String password;
	@Column(length = 64)
	private String sftpAlias;
	@Column(length = 255)
	private String knownHost;
	@Column(length = 255)
	private String keyFile;
	@Convert(converter = YesNoConverter.class)
	@Column(length = 1)
	private YesNo trustHost;
	@Convert(converter = YesNoConverter.class)
	@Column(length = 1)
	private YesNo enabled;
	@Column(length = 2000)
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
