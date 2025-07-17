package it.dmsoft.flowmanager.agent.engine.core.operations.params;

public class InteractiveProgramCallParam extends GenericAS400Param {

	private String pgm;
	private String result;
	
	public String getPgm() {
		return pgm;
	}

	public void setPgm(String pgm) {
		this.pgm = pgm;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "InteractiveProgramCallParam [pgm=" + pgm + ", result=" + result + "]";
	}


}
