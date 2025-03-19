package it.dmsoft.flowmanager.agent.api.flows.model;

import it.dmsoft.flowmanager.common.model.EmailData;
import it.dmsoft.flowmanager.common.model.FlowData;
import it.dmsoft.flowmanager.common.model.GroupData;
import it.dmsoft.flowmanager.common.model.InterfaceData;
import it.dmsoft.flowmanager.common.model.ModelData;
import it.dmsoft.flowmanager.common.model.OriginData;

public class FullFlowData {
	
	private EmailData email;

	private FlowData flow;

	private GroupData group;

	private InterfaceData _interface;

	private ModelData model;

	private OriginData origin;

	public EmailData getEmail() {
		return email;
	}

	public void setEmail(EmailData email) {
		this.email = email;
	}

	public FlowData getFlow() {
		return flow;
	}

	public void setFlow(FlowData flow) {
		this.flow = flow;
	}

	public GroupData getGroup() {
		return group;
	}

	public void setGroup(GroupData group) {
		this.group = group;
	}

	public InterfaceData getInterface() {
		return _interface;
	}

	public void setInterface(InterfaceData _interface) {
		this._interface = _interface;
	}

	public ModelData getModel() {
		return model;
	}

	public void setModel(ModelData model) {
		this.model = model;
	}

	public OriginData getOrigin() {
		return origin;
	}

	public void setOrigin(OriginData origin) {
		this.origin = origin;
	}
	
	

}
