package it.dmsoft.flowmanager.agent.be.entities;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ScheduleDate {

	@Id
	@Column(length = 8)
	private BigDecimal dataDa;
	
	@Column(length = 8)
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
