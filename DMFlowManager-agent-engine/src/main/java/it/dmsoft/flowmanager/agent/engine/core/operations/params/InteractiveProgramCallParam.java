package it.dmsoft.flowmanager.agent.engine.core.operations.params;

import it.dmsoft.flowmanager.common.domain.Domains.YesNo;

public class InteractiveProgramCallParam extends GenericConnectionParams {

	private String pgm;
	private YesNo result;
	
	public String getPgm() {
		return pgm;
	}

	public void setPgm(String pgm) {
		this.pgm = pgm;
	}

	public YesNo getResult() {
		return result;
	}

	public void setResult(YesNo result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "InteractiveProgramCallParam [pgm=" + pgm + ", result=" + result + "]";
	}


}
