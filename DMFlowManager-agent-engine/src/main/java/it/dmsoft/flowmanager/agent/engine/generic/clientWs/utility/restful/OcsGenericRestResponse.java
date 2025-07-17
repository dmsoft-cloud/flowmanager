package it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.restful;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

public class OcsGenericRestResponse<T> {
	private Throwable error;
	private HttpHeaders headers;
	private ResponseEntity<String> responseError;
	private boolean timeout;
	private boolean errori = false;

	private T response = null;

	public T getResponse() {
		return response;
	}

	public void setResponse(T response) {
		this.response = response;
	}

	public boolean isTimeout() {
		return timeout;
	}

	public void setTimeout(boolean timeout) {
		this.timeout = timeout;
	}

	public Throwable getError() {
		return error;
	}

	public void setError(Throwable error) {
		this.error = error;
	}

	public boolean isFailed() {
		return error != null;
	}

	public ResponseEntity<String> getResponseError() {
		return responseError;
	}

	protected void setResponseError(ResponseEntity<String> responseError) {
		errori = true;
		this.responseError = responseError;
	}

	public boolean isErrori() {
		if (this.responseError != null || this.error != null) {
			errori = true;
		}
		return errori;
	}

	public void setErrori(boolean errori) {
		this.errori = errori;
	}

	public HttpHeaders getHeaders() {
		if (headers == null) {
			headers = new HttpHeaders();
		}
		return headers;
	}

	public void setHeaders(HttpHeaders headers) {
		this.headers = headers;
	}

}
