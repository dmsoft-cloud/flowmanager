package it.dmsoft.flowmanager.agent.engine.generic.authentication.compass;

import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.AuthenticationProperties;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.AuthenticationProperties.NomeApplicazione;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.output.Retriever;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.compass.interfacce.CompassAuthenticationClientResponse;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.compass.output.data.TokenRetrieverDataCompass;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.compass.parameters.data.ClientDataCompass;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.exception.AuthenticationException;
import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.restful.Invoker;
import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.restful.OcsGenericRestResponse;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.KeyValueLog;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class TokenRetrieverCompass implements Retriever<TokenRetrieverDataCompass> {
	private static TokenRetrieverDataCompass tokenOld;

	private static TokenRetrieverDataCompass authenticationToken(Logger logger) throws AuthenticationException {

		// Recupero parametri per comunicazione.
		ClientDataCompass data = AuthenticationProperties.getData(NomeApplicazione.COMPASSAUTHENTICATIONCLIENT, ClientDataCompass.class);
		if (data == null) {
			logger.warn("Impossibile recuperare dati di autenticazione");
			return new TokenRetrieverDataCompass(false, null, null, logger);
		}
		logger.info(new KeyValueLog("ClientDataCompass", data.toString()));

		try {
			URL endPoint = new URL(data.getUrl());
			// Impostazione headers.
			logger.debug("Impostazione headers");
			HttpHeaders headersRequest = new HttpHeaders();
			headersRequest.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

			// Setup per invocazione.
			OcsGenericRestResponse<CompassAuthenticationClientResponse> tokenResponse = null;
			long start = System.currentTimeMillis();

			// Invocazione servizio.
			try {
				// TODO da sistemare per riceverlo come parametro nelle chiamate
				// standard del GenericWsClient
				System.setProperty("https.protocols", "TLSv1.1,TLSv1.2");
				tokenResponse = Invoker.<CompassAuthenticationClientResponse> callEndpoint(endPoint.toString(), null, HttpMethod.POST, headersRequest, data.toParamString(logger), 0, false,
						CompassAuthenticationClientResponse.class, logger);
			} catch (Exception e) {
				logger.error("Errore in restituzione dati di output", e);
				return new TokenRetrieverDataCompass(true, null, null, logger);
			} finally {
				long duration = System.currentTimeMillis() - start;
				logger.info("Durata recupero risorsa: " + duration + " ms");
			}

			// Set valore token in uscita.
			if (tokenResponse.getResponse() != null && tokenResponse.getResponse().getAccessToken() != null) {
				return new TokenRetrieverDataCompass(true, tokenResponse.getResponse().getAccessToken(), tokenResponse.getResponse().getExpiresIn(), logger);
			}
			// Set errore in risposta.
			if (tokenResponse.isErrori()) {
				logger.error("Ricevuto errore in chiamata del servizio di recupero token");
				logger.error("Http Status:" + tokenResponse.getResponseError().getStatusCode().toString());
				logger.error("Response Body:" + tokenResponse.getResponseError().getBody());
				return new TokenRetrieverDataCompass(true, null, null, logger);
			} else {
				return new TokenRetrieverDataCompass(false, null, null, logger);
			}

		} catch (MalformedURLException e1) {
			throw new AuthenticationException(e1.getMessage(), e1);
		}
	}

	@Override
	public TokenRetrieverDataCompass get(Logger logger) throws AuthenticationException {

		if (getTokenOld().scaduto(logger)) {
			logger.info("Token non più valido: invocazione per rinnovare il token");
			setTokenOld(authenticationToken(logger));
		} else {
			logger.info("Token ancora valido");
		}
		return tokenOld;
	}

	private static TokenRetrieverDataCompass getTokenOld() {
		if (tokenOld == null) {
			tokenOld = new TokenRetrieverDataCompass();
		}
		return tokenOld;
	}

	private static void setTokenOld(TokenRetrieverDataCompass tokenOld) {
		TokenRetrieverCompass.tokenOld = tokenOld;
	}
}
