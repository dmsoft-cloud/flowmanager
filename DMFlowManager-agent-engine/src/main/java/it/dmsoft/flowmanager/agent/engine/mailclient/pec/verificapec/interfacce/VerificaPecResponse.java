package it.dmsoft.flowmanager.agent.engine.mailclient.pec.verificapec.interfacce;

import java.util.ArrayList;
import java.util.List;

public class VerificaPecResponse {
	private List<CheckResult> result;

	public List<CheckResult> getResult() {
		if (result == null) {
			result = new ArrayList<CheckResult>();
		}
		return result;
	}

	public void addResult(CheckResult result) {
		getResult().add(result);
	}
	
	public void setResult(List<CheckResult> result) {
		this.result = result;
	}
}
