package it.dmsoft.flowmanager.agent.be.entities;

import java.math.BigDecimal;

public class ScheduleDate {

	private BigDecimal dataDa;
	
	private BigDecimal data;

	public BigDecimal getDataDa() {
		return dataDa;
	}

	public void setDataDa(BigDecimal dataDa) {
		this.dataDa = dataDa;
	}

	public BigDecimal getData() {
		return data;
	}

	public void setData(BigDecimal data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ScheduleDate [dataDa=" + dataDa + ", data=" + data + "]";
	}
	
	
}
