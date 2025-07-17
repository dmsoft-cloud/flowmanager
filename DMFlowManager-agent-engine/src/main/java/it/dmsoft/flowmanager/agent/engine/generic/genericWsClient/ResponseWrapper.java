package it.dmsoft.flowmanager.agent.engine.generic.genericWsClient;

public class ResponseWrapper<T> {
	private ResponseGenericClient responseWs;
	private T response;

	public ResponseWrapper() {
		super();
	}

	public ResponseGenericClient getResponseWs() {
		return responseWs;
	}

	public void setResponseWs(ResponseGenericClient responseWs) {
		this.responseWs = responseWs;
	}

	public T getResponse() {
		return response;
	}

	public void setResponse(T response) {
		this.response = response;
	}

}
