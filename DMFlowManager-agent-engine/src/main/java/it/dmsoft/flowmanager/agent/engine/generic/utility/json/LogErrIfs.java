package it.dmsoft.flowmanager.agent.engine.generic.utility.json;

import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.ResponseGenericClient;

public class LogErrIfs {
	private String manager;
	private String method;
	private String message;

	public LogErrIfs(ResponseGenericClient response) {
		this.manager = response.getClasseManager();
		this.method = response.getMethod();
		this.message = response.getMessage();
	}

	public LogErrIfs(String manager, String method, String message) {
		this.manager = manager;
		this.method = method;
		this.message = message;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
