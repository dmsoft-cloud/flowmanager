package it.dmsoft.flowmanager.agent.engine.generic.authentication.diana;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.AuthenticationProperties;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.AuthenticationProperties.NomeApplicazione;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.output.Retriever;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.diana.output.data.TokenRetrieverDataDiana;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.diana.parameters.data.ClientDataDiana;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.exception.AuthenticationException;
import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.exceptions.OCSAuthenticationException;
import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.exceptions.OCSURLOAuth2NotFound;
import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.restful.Authentication;
import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.restful.Invoker;
import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.restful.OcsGenericRestResponse;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.FailableFunction;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.GenericWsManager;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class TokenRetrieverDiana implements Retriever<TokenRetrieverDataDiana> {

	private static TokenRetrieverDataDiana authenticationToken(ClientDataDiana data, Logger logger) throws AuthenticationException {
		if (data == null) {
			logger.error("Impossibile recuperare dati di autenticazione");
			return new TokenRetrieverDataDiana(null, false, null);
		}
		try {
			URL urlCorrelationId = new URL(data.getUrl());
			URL urlToken = new URL(data.getUrlToken());
			List<String> tokenInHeaderList = null;
			String tokenInHeader = null;
			String correlationId = getCorrellationId(urlCorrelationId, data.getTimeout(), logger);
			if (correlationId != null && !correlationId.equals("")) {
				tokenInHeaderList = getTokenValueInHeader(urlToken.toString(), data.getUsername(), data.getPassword(), logger);
				if (tokenInHeaderList != null && !tokenInHeaderList.isEmpty() && tokenInHeaderList.size() == 1) {
					tokenInHeader = tokenInHeaderList.get(0);
				}
			} else {
				logger.error("Errore nel recupero durate invocazione servizio di autenticazione.");
				return new TokenRetrieverDataDiana(null, true, null);
			}
			// Set valore token in uscita.
			return new TokenRetrieverDataDiana(tokenInHeader, true, correlationId);
		} catch (MalformedURLException e) {
			throw new AuthenticationException(e.getMessage(), e);
		}
	}

	private static String getCorrellationId(URL url, int timeout, Logger logger) {
		String correlationId = null;
		try {
			logger.debug("Contatto " + url + " per correlation_id");
			OcsGenericRestResponse<String> restResponse = Invoker.callEndpoint(url.toString(), HttpMethod.GET, new HttpHeaders(), "", timeout, logger);
			Throwable e = restResponse.getError();
			if (e != null) {
				logger.error("Errore richiesta correlation id", e);
				return correlationId;
			}
			correlationId = restResponse.getResponse();
		} catch (Exception e) {
			logger.error("" + e);
			return correlationId;
		}
		return correlationId;
	}

	private static List<String> getTokenValueInHeader(String url, String username, String password, Logger logger) {
		// Get token
		HttpHeaders testata = new HttpHeaders();
		List<String> tokenValueInHeaderList = null;
		try {
			testata = Authentication.getOAuth2Headers(MediaType.APPLICATION_JSON, url, username, password, logger);
			tokenValueInHeaderList = testata.get("Authorization");
		} catch (OCSURLOAuth2NotFound e) {
			logger.warn("Url per autenticazione per OAuth2 non valido");
			return tokenValueInHeaderList;
		} catch (OCSAuthenticationException e) {
			logger.error("Errore generico", e);
			return tokenValueInHeaderList;
		}
		return tokenValueInHeaderList;

	}

	@Override
	public TokenRetrieverDataDiana get(Logger logger) throws AuthenticationException {
		FailableFunction<ClientDataDiana, TokenRetrieverDataDiana> ff = new FailableFunction<ClientDataDiana, TokenRetrieverDataDiana>() {
			@Override
			public TokenRetrieverDataDiana apply(ClientDataDiana t, Logger logger) throws Exception {
				return authenticationToken(t, logger);
			}
		};
		ClientDataDiana data = AuthenticationProperties.getData(NomeApplicazione.DIANAAUTHENTICATIONCLIENT, ClientDataDiana.class);

		if (data == null) {
			logger.error("Impossibile recuperare dati di autenticazione");
			return new TokenRetrieverDataDiana(null, false, null);
		}

		try {
			return GenericWsManager.applyFunctionWithParameters(ff, data, data.getParams(), logger);
		} catch (Exception e) {
			throw new AuthenticationException(e.getMessage(), e);
		}
	}
}
