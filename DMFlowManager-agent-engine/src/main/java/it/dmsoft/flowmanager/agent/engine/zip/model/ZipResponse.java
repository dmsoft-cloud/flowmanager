package it.dmsoft.flowmanager.agent.engine.zip.model;

public class ZipResponse {
	public enum Outcome {
		OK("OK"), KO("KO");
		
		private String stringValue;
		
		private Outcome(String stringValue) {
			this.stringValue = stringValue;
		}
		
		public String getStringValue() {
			return stringValue;
		}
	}
	
	private String outcome;
	
	public ZipResponse() {
		this.outcome = Outcome.KO.getStringValue();
	}

	public String getOutcome() {
		return outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}
	
}
