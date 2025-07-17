package it.dmsoft.flowmanager.agent.engine.generic.utility.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import it.dmsoft.flowmanager.agent.engine.generic.utility.json.exceptions.HttpException;

public class OcsResponseErrorHandler implements ResponseErrorHandler {

	@Override
	public void handleError(ClientHttpResponse clienthttpresponse) throws IOException {
		throw new HttpException(clienthttpresponse.getStatusCode(), clienthttpresponse.getStatusText(),
				getStringFromInputStream(clienthttpresponse.getBody()));

	}

	@Override
	public boolean hasError(ClientHttpResponse clienthttpresponse) throws IOException {
		return !clienthttpresponse.getStatusCode().is2xxSuccessful();
	}

	private static String getStringFromInputStream(InputStream is) {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		String line;
		try {
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

	}

}
