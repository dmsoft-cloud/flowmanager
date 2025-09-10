package it.dmsoft.flowmanager.be.entities;

import it.dmsoft.flowmanager.common.domain.Domains.YesNo;
import it.dmsoft.flowmanager.framework.converter.YesNoConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Flow extends BaseEntity {

	// NOME
	// DESCRIZIONE
	// NOTE
	// ATTIVO
	// MODELLO
	// ORIGINE
	// INTERFACCIA
	// MAIL
	// MAILOk
	// MAILKO
	// SEMAFORO
	// TABLE
	// FOLDER
	// FILE
	// REMOTE_FILE
	// REMOTE FOLDER
	// Lunghezza fixed
	
	

	@Id
	@Column(length = 20)
	private String id;
	@Column(length = 255)
	private String description;
	@Column(length = 20)
	private String agentId;
	@Column(length = 20)
	private String groupId;
	@Column(length = 2000)
	private String note;
	@Convert(converter = YesNoConverter.class)
	@Column(length = 1)
	private YesNo enabled;
	
	@Column(length = 20)
	private String model;
	@Column(length = 20)	
	private String origin;
	@Column(length = 20)
	private String interfaceId;
	@Column(length = 20)
	private String agent;
	
	@Column(length = 20)
	private String notificationFlow;
	@Column(length = 20)
	private String notificationOk;
	@Column(length = 20)
	private String notificationKo;
	
	@Column(length = 255)
	private String integrityFileName;	
	@Column(length = 50)
	private String dbTable;	
	@Column(length = 255)
	private String folder;
	@Column(length = 255)
	private String file;
	@Column(length = 255)
	private String remoteFolder;
	@Column(length = 255)
	private String remoteFile;
	@Column(length = 5)
	private Integer lengthFixed;
	
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
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getInterfaceId() {
		return interfaceId;
	}
	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}	
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	public String getNotificationFlow() {
		return notificationFlow;
	}
	public void setNotificationFlow(String notificationFlow) {
		this.notificationFlow = notificationFlow;
	}
	public String getNotificationOk() {
		return notificationOk;
	}
	public void setNotificationOk(String notificationOk) {
		this.notificationOk = notificationOk;
	}
	public String getNotificationKo() {
		return notificationKo;
	}
	public void setNotificationKo(String notificationKo) {
		this.notificationKo = notificationKo;
	}
	public String getIntegrityFileName() {
		return integrityFileName;
	}
	public void setIntegrityFileName(String integrityFileName) {
		this.integrityFileName = integrityFileName;
	}

	public String getDbTable() {
		return dbTable;
	}
	public void setDbTable(String dbTable) {
		this.dbTable = dbTable;
	}
	public String getFolder() {
		return folder;
	}
	public void setFolder(String folder) {
		this.folder = folder;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getRemoteFolder() {
		return remoteFolder;
	}
	public void setRemoteFolder(String remoteFolder) {
		this.remoteFolder = remoteFolder;
	}
	public String getRemoteFile() {
		return remoteFile;
	}
	public void setRemoteFile(String remoteFile) {
		this.remoteFile = remoteFile;
	}
	public Integer getLengthFixed() {
		return lengthFixed;
	}
	public void setLengthFixed(Integer lengthFixed) {
		this.lengthFixed = lengthFixed;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}	




}
