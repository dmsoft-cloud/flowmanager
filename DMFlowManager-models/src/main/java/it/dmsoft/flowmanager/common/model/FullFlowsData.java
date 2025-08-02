package it.dmsoft.flowmanager.common.model;

import java.util.List;

public class FullFlowsData {

	private List<EmailData> emails;

	private List<FlowData> flows;

	private List<GroupData> groups;

	private List<InterfaceData> interfaces;

	private List<ModelData> models;

	private List<OriginData> origins;
	
	private EmailParmsData mailParms;
	
	public FullFlowsData() {
		super();
	}

	public FullFlowsData(List<EmailData> emails, List<FlowData> flows, List<GroupData> groups,
			List<InterfaceData> interfaces, List<ModelData> models, List<OriginData> origins, EmailParmsData mailParms) {
		super();
		this.emails = emails;
		this.flows = flows;
		this.groups = groups;
		this.interfaces = interfaces;
		this.models = models;
		this.origins = origins;
		this.mailParms = mailParms;
	}

	public List<EmailData> getEmails() {
		return emails;
	}

	public void setEmails(List<EmailData> emails) {
		this.emails = emails;
	}

	public List<FlowData> getFlows() {
		return flows;
	}

	public void setFlows(List<FlowData> flows) {
		this.flows = flows;
	}

	public List<GroupData> getGroups() {
		return groups;
	}

	public void setGroups(List<GroupData> groups) {
		this.groups = groups;
	}

	public List<InterfaceData> getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(List<InterfaceData> interfaces) {
		this.interfaces = interfaces;
	}

	public List<ModelData> getModels() {
		return models;
	}

	public void setModels(List<ModelData> models) {
		this.models = models;
	}

	public List<OriginData> getOrigins() {
		return origins;
	}

	public void setOrigins(List<OriginData> origins) {
		this.origins = origins;
	}

	public EmailParmsData getMailParms() {
		return mailParms;
	}

	public void setMailParms(EmailParmsData mailParms) {
		this.mailParms = mailParms;
	}

}
