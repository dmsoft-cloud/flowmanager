package it.dmsoft.flowmanager.common.model.cards;

import java.util.List;

public class AgentStatsData {

    private String agent;
    private Long outbound;  
    private Long outboundError;   
    private Long inbound;  
    private Long inboundError;
	public AgentStatsData(String agent, Long outbound, Long outboundError, Long inbound, Long inboundError) {
		super();
		this.agent = agent;
		this.outbound = outbound;
		this.outboundError = outboundError;
		this.inbound = inbound;
		this.inboundError = inboundError;
	}
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	public Long getOutbound() {
		return outbound;
	}
	public void setOutbound(Long outbound) {
		this.outbound = outbound;
	}
	public Long getOutboundError() {
		return outboundError;
	}
	public void setOutboundError(Long outboundError) {
		this.outboundError = outboundError;
	}
	public Long getInbound() {
		return inbound;
	}
	public void setInbound(Long inbound) {
		this.inbound = inbound;
	}
	public Long getInboundError() {
		return inboundError;
	}
	public void setInboundError(Long inboundError) {
		this.inboundError = inboundError;
	}
	
	@Override
	public String toString() {
		return "AgentStatsData [agent=" + agent + ", outbound=" + outbound + ", outboundError=" + outboundError
				+ ", inbound=" + inbound + ", inboundError=" + inboundError + "]";
	}



	


}
