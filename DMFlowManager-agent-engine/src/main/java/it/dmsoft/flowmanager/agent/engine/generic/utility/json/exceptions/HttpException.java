package it.dmsoft.flowmanager.agent.engine.generic.utility.json.exceptions;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class HttpException extends IOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1233583675450341545L;
	private HttpStatusCode statusCode;
	private String statusText;
	private String body;

	public HttpException() {
		super();
	}

	public HttpException(HttpStatusCode statusCode, String statusText, String body) {
		super();
		this.statusCode = statusCode;
		this.statusText = statusText;
		this.body = body;
	}


	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	public HttpStatusCode getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(HttpStatusCode statusCode) {
		this.statusCode = statusCode;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
