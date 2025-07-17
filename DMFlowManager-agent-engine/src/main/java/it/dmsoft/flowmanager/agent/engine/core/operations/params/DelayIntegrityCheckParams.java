package it.dmsoft.flowmanager.agent.engine.core.operations.params;

import java.math.BigDecimal;

import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;

public class DelayIntegrityCheckParams extends GenericAS400Param {

	private BigDecimal delaySecond;

	public BigDecimal getDelaySecond() {
		return delaySecond;
	}



	public void setDelaySecond(BigDecimal delaySecond) {
		this.delaySecond = delaySecond;
	}
	
	@Override
	public String toString() {
		return "DelayIntegrityCheckParams [delaySecond=" + delaySecond + "]";
	}
}
