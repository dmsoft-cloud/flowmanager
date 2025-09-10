package it.dmsoft.flowmanager.common.model;

import java.util.List;

public class FlowExecutionOutcome {
	
	private FlowLogData logData;
	
	private List<FlowLogDetailsData> logDetailsData;

	public FlowLogData getLogData() {
		return logData;
	}

	public void setLogData(FlowLogData logData) {
		this.logData = logData;
	}

	public List<FlowLogDetailsData> getLogDetailsData() {
		return logDetailsData;
	}

	public void setLogDetailsData(List<FlowLogDetailsData> logDetailsData) {
		this.logDetailsData = logDetailsData;
	}

}
