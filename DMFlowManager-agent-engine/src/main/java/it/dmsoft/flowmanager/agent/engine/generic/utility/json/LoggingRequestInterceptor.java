package it.dmsoft.flowmanager.agent.engine.generic.utility.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {
	private Logger log;

	public LoggingRequestInterceptor(Logger log) {
		super();
		this.log = log;
	}

	/**
	 * @deprecated
	 */
	/*
	@Deprecated
	public LoggingRequestInterceptor(org.apache.log4j.Logger log) {
		this(Logger.log4j2Logger(log));
	}
	*/

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
		try {
			traceRequest(request, body);
		} catch (Exception e) {
			log.error("Intercept: Eccezione in fase di log della richiesta: " + e);
		}
		ClientHttpResponse response = null;
		try {
			response = execution.execute(request, body);
		} catch (IOException e) {
			if (e instanceof SocketTimeoutException) {
				throw e;
			}
			log.error("Intercept: Eccezione in fase di chiamata: " + e);
			if (response == null) {
				throw e;
			}
		}
		try {
			traceResponse(response);
		} catch (Exception e) {
			log.error("Intercept: Eccezione in fase di log della risposta: " + e);
		}
		return response;
	}

	private void traceRequest(HttpRequest request, byte[] body) throws IOException {
		log.debug("===========================request begin================================================");
		log.debug("URI         : " + request.getURI());
		log.debug("Method      : " + request.getMethod());
		log.debug("Headers     : " + request.getHeaders());
		log.debug("Request body: " + new String(body, "UTF-8"));
		log.debug("==========================request end===================================================");
	}

	private void traceResponse(ClientHttpResponse response) throws IOException {
		StringBuilder inputStringBuilder = new StringBuilder();
		if (response.getBody() != null) {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), "UTF-8"));
			String line = bufferedReader.readLine();
			while (line != null) {
				inputStringBuilder.append(line);
				inputStringBuilder.append('\n');
				line = bufferedReader.readLine();
			}
		}
		log.debug("============================response begin==========================================");
		log.debug("Status code  : " + response.getStatusCode());
		log.debug("Status text  : " + response.getStatusText());
		log.debug("Headers      : " + response.getHeaders());
		log.debug("Response body: " + inputStringBuilder.toString());
		log.debug("=======================response end=================================================");
	}

}