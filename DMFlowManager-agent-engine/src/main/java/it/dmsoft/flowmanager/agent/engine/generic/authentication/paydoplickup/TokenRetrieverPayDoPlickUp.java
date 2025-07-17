package it.dmsoft.flowmanager.agent.engine.generic.authentication.paydoplickup;

import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.AuthenticationProperties;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.AuthenticationProperties.NomeApplicazione;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.output.Retriever;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.output.data.TokenRetrieverDataExpirable;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.parameters.data.ClientDataJWT;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.exception.AuthenticationException;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.paydoplickup.interfacce.TokenPayDoPlickUpRequest;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.paydoplickup.interfacce.TokenPayDoPlickUpResponse;
import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.restful.Invoker;
import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.restful.OcsGenericRestResponse;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.FailableFunction;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.GenericWsManager;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class TokenRetrieverPayDoPlickUp implements Retriever<TokenRetrieverDataExpirable> {
	private static TokenRetrieverDataExpirable tokenOld;

	@Override
	public TokenRetrieverDataExpirable get(Logger logger) throws AuthenticationException {

		FailableFunction<ClientDataJWT, TokenRetrieverDataExpirable> ff = new FailableFunction<ClientDataJWT, TokenRetrieverDataExpirable>() {
			@Override
			public TokenRetrieverDataExpirable apply(ClientDataJWT data, Logger logger) throws Exception {
				URL url = null;
				try {
					url = new URL(data.getUrl());
				} catch (MalformedURLException e) {
					logger.error("errore nella composizione URL", e);
					return new TokenRetrieverDataExpirable(true);
				}
				logger.info("Invocazione servizio di autenticazione con parametri recuperati da PayDoPlickUp");

				// Impostazione headers.
				logger.debug("Impostazione headers");
				HttpHeaders headers = new HttpHeaders();

				// set headers
				headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

				TokenPayDoPlickUpRequest request = new TokenPayDoPlickUpRequest();
				request.setClientId(data.getUsername());
				request.setClientSecret(data.getPassword());

				OcsGenericRestResponse<TokenPayDoPlickUpResponse> restResponse;
				try {
					restResponse = Invoker.callEndpoint(url.toString(), null, HttpMethod.POST, headers, (new ObjectMapper()).writeValueAsString(request), null, 0, false,
							TokenPayDoPlickUpResponse.class, logger);
					Throwable e = restResponse.getError();
					if (e != null) {
						logger.error("Errore richiesta token da servizio PayDoPlickUp", e);
						return new TokenRetrieverDataExpirable(true);
					}

				} catch (Exception e) {
					logger.error("Errore in invocazione servizio di autenticazione" + e);
					return new TokenRetrieverDataExpirable(true);
				}

				return new TokenRetrieverDataExpirable(true, restResponse.getResponse().getAccessToken(), restResponse.getResponse().getExpiresIn(), logger);
			}
		};

		// Recupero parametri per comunicazione.
		ClientDataJWT data = AuthenticationProperties.getData(NomeApplicazione.PAYDOPLICKUPAUTHENTICATIONCLIENT, ClientDataJWT.class);
		if (data == null) {
			logger.warn("Impossibile recuperare dati di autenticazione");
			return new TokenRetrieverDataExpirable();
		}

		if (getTokenOld().scaduto(logger)) {
			try {
				setTokenOld(GenericWsManager.applyFunctionWithParameters(ff, data, data.getParams(), logger));
			} catch (Exception e) {
				throw new AuthenticationException(e.getMessage(), e);
			}
		}

		return getTokenOld();

	}

	private static TokenRetrieverDataExpirable getTokenOld() {
		if (tokenOld == null) {
			tokenOld = new TokenRetrieverDataExpirable();
		}
		return tokenOld;
	}

	private static void setTokenOld(TokenRetrieverDataExpirable tokenOld) {
		TokenRetrieverPayDoPlickUp.tokenOld = tokenOld;
	}

}
