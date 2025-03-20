package it.dmsoft.flowmanager.common.model;

import it.dmsoft.flowmanager.common.domain.Domains.YesNo;

public class AgentData {

	private String id;
	private String ip;
	private Integer port;
	private String description;
	private String note;
	private YesNo enabled;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
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

}
