package it.dmsoft.flowmanager.common.websocket;

import java.math.BigDecimal;

import it.dmsoft.flowmanager.common.model.FullFlowData;

public class FlowExecutionRequest {
	
	private String flowId;
	
	private BigDecimal flowProgr;
	
	private FullFlowData fullFlowData;

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public BigDecimal getFlowProgr() {
		return flowProgr;
	}

	public void setFlowProgr(BigDecimal flowProgr) {
		this.flowProgr = flowProgr;
	}

	public FullFlowData getFullFlowData() {
		return fullFlowData;
	}

	public void setFullFlowData(FullFlowData fullFlowData) {
		this.fullFlowData = fullFlowData;
	}

	@Override
	public String toString() {
		return "FlowExecutionRequest [flowId=" + flowId + ", flowProgr=" + flowProgr + ", fullFlowData=" + fullFlowData
				+ "]";
	}

}
