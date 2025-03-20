package it.dmsoft.flowmanager.common.model;

import it.dmsoft.flowmanager.common.domain.Domains.YesNo;
import it.dmsoft.flowmanager.common.util.Constants;

public class AgentData {

	public static final AgentData DEFAULT_AGENT = new AgentData(Constants.DEFAULT_AGENT_ID, Constants.LOCALHOST, Constants.DEFAULT_AGENT_PORT,
				Constants.DEFAULT_AGENT_DESCRIPTION, null, YesNo.YES);
	
	private String id;
	private String ip;
	private Integer port;
	private String description;
	private String note;
	private YesNo enabled;
	
	public AgentData() {
		super();
	}

	public AgentData(String id, String ip, Integer port, String description, String note, YesNo enabled) {
		super();
		this.id = id;
		this.ip = ip;
		this.port = port;
		this.description = description;
		this.note = note;
		this.enabled = enabled;
	}

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
