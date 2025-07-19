package it.dmsoft.flowmanager.agent.engine.core.model;

import java.util.List;

public class MasterdataOverride {

	private ExecutionFlowData executionFlowData;
	private String mailText;
	private List<String> mailDest;
	
	public ExecutionFlowData getExecutionFlowData() {
		return executionFlowData;
	}
	public void setExecutionFlowData(ExecutionFlowData executionFlowData) {
		this.executionFlowData = executionFlowData;
	}
	public String getMailText() {
		return mailText;
	}
	public void setMailText(String mailText) {
		this.mailText = mailText;
	}
	public List<String> getMailDest() {
		return mailDest;
	}
	public void setMailDest(List<String> mailDest) {
		this.mailDest = mailDest;
	}
	@Override
	public String toString() {
		return "MasterdataOverride [fanaData=" + executionFlowData + ", mailText=" + mailText + ", mailDest=" + mailDest + "]";
	}


	
	
}
