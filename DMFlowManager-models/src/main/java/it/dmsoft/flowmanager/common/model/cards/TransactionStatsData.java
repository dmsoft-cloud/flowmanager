package it.dmsoft.flowmanager.common.model.cards;

import java.time.LocalDate;
import java.sql.Date;
import java.util.List;

public class TransactionStatsData {

    private LocalDate date;
    private Long outbound;  
    private Long outboundError;   
    private Long inbound;  
    private Long inboundError;
    
	/*public TransactionStatsData(LocalDate date, Long outbound, Long outboundError, Long inbound, Long inboundError) {
		super();
		this.date = date;
		this.outbound = outbound;
		this.outboundError = outboundError;
		this.inbound = inbound;
		this.inboundError = inboundError;
	}
	*/
    public TransactionStatsData(Date date, Number outbound, Number outboundError, Number inbound, Number inboundError) {
        this.date = date != null ? date.toLocalDate() : null;
        this.outbound = outbound != null ? outbound.longValue() : 0L;
        this.outboundError = outboundError != null ? outboundError.longValue() : 0L;
        this.inbound = inbound != null ? inbound.longValue() : 0L;
        this.inboundError = inboundError != null ? inboundError.longValue() : 0L;
    }
	

	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
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
		return "TransactionStatsData [date=" + date + ", outbound=" + outbound + ", outboundError=" + outboundError
				+ ", inbound=" + inbound + ", inboundError=" + inboundError + "]";
	}
	




	


}
