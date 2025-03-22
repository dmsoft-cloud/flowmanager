package it.dmsoft.flowmanager.common.model;

public class FullFlowData {

	private EmailData emailOk;

	private EmailData emailKo;
	
	private FlowData flow;

	private GroupData group;

	private InterfaceData _interface;

	private ModelData model;

	private OriginData origin;
	
	public FullFlowData() {
		super();
	}

	public FullFlowData(FlowData flow, GroupData group, EmailData emailOk, EmailData emailKo, InterfaceData _interface,
			ModelData model, OriginData origin) {
		this.flow = flow;
		this.group = group;
		this.emailOk = emailOk;
		this.emailKo = emailKo;
		this._interface = _interface;
		this.model = model;
		this.origin = origin;
	}

	public EmailData getEmailOk() {
		return emailOk;
	}

	public void setEmailOk(EmailData emailOk) {
		this.emailOk = emailOk;
	}

	public EmailData getEmailKo() {
		return emailKo;
	}

	public void setEmailKo(EmailData emailKo) {
		this.emailKo = emailKo;
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

	public InterfaceData get_interface() {
		return _interface;
	}

	public void set_interface(InterfaceData _interface) {
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
