package it.dmsoft.flowmanager.agent.engine.generic.authentication.santander;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.AuthenticationProperties;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.AuthenticationProperties.NomeApplicazione;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.output.data.TokenRetrieverData;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.exception.AuthenticationException;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.santander.parameters.data.ClientDataSantander;
import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.restful.Invoker;
import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.restful.OcsGenericRestResponse;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.KeyValueLog;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public abstract class TokenRetrieverSantanderAbs {

	protected static TokenRetrieverData authenticationToken(Logger logger, NomeApplicazione nomeApplicazione) throws AuthenticationException {

		// Recupero parametri per comunicazione.
		ClientDataSantander data = AuthenticationProperties.getData(nomeApplicazione, ClientDataSantander.class);

		if (data == null) {
			logger.warn("Impossibile recuperare dati di autenticazione");
			return new TokenRetrieverData(false, null);
		}
		logger.info(new KeyValueLog("ClientDataSantander", data.toString()));
		try {
			URL endPoint = new URL(data.getUrl());
			// Impostazione headers.
			logger.debug("Impostazione headers");
			HttpHeaders headersRequest = new HttpHeaders();
			headersRequest.setContentType(MediaType.APPLICATION_JSON);
			headersRequest.set("X-IBM-Client-Secret", data.getClientSecret());
			headersRequest.set("X-IBM-Client-Id", data.getClientId());
			headersRequest.set("accept", "application/json");

			// Setup per invocazione.
			OcsGenericRestResponse<?> response = null;
			long start = System.currentTimeMillis();

			// Invocazione servizio.
			try {
				// TODO da sistemare per riceverlo come parametro nelle chiamate
				// standard del GenericWsClient
				System.setProperty("https.protocols", "TLSv1.1,TLSv1.2");
				response = Invoker.<Object> callEndpoint(endPoint.toString(), HttpMethod.GET, headersRequest, null, 0, null, logger);
			} catch (Exception e) {
				logger.error("Errore in restituzione dati di output", e);
				return new TokenRetrieverData(true, null);
			} finally {
				long duration = System.currentTimeMillis() - start;
				logger.info("Durata recupero risorsa: " + duration + " ms");
			}

			// Set errore in risposta.
			if (response.isErrori()) {
				logger.error("Ricevuto errore in chiamata del servizio di recupero token");
				if (response.getResponseError() != null) {
					logger.error("Http Status:" + response.getResponseError().getStatusCode().toString());
					logger.error("Response Body:" + response.getResponseError().getBody());
				}
				return new TokenRetrieverData(true, null);
			}

			// Set valore token in uscita.
			List<String> headersResponse = response.getHeaders().get("Authorization");
			if (!headersResponse.isEmpty() && headersResponse.size() == 1) {
				return new TokenRetrieverData(true, headersResponse.get(0));
			} else {
				return new TokenRetrieverData(true, null);
			}
		} catch (MalformedURLException e1) {
			throw new AuthenticationException(e1.getMessage(), e1);
		}
	}

}