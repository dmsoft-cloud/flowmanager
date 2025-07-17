package it.dmsoft.flowmanager.agent.engine.generic.authentication.intesaAuth;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.AuthenticationProperties;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.AuthenticationProperties.NomeApplicazione;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.output.Retriever;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.output.data.TokenRetrieverData;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.exception.AuthenticationException;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.intesaAuth.interfacce.TokenIntesaResponse;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.intesaAuth.parameters.data.ClientDataIntesa;
import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.OcsBase64;
import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.restful.Invoker;
import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.restful.OcsGenericRestResponse;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.FailableFunction;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.GenericWsManager;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class TokenRetrieverIntesa implements Retriever<TokenRetrieverData> {

	@Override
	public TokenRetrieverData get(Logger logger) throws AuthenticationException {

		FailableFunction<ClientDataIntesa, TokenRetrieverData> ff = new FailableFunction<ClientDataIntesa, TokenRetrieverData>() {
			@Override
			public TokenRetrieverData apply(ClientDataIntesa data, Logger logger) throws Exception {
				String token = null;
				String encoding = null;
				URL url = null;
				try {
					url = new URL(data.getUrl());
				} catch (MalformedURLException e) {
					logger.error("errore nella composizione URL", e);
					return new TokenRetrieverData(true, null);
				}
				logger.info(
						"Invocazione servizio di autenticazione con parametri recuperati da IntesaAuthenticationClient");
				HashMap<String, String> map = new HashMap<String, String>();
				// Impostazione headers.
				logger.debug("Impostazione headers");
				HttpHeaders headers = new HttpHeaders();
				try {
					encoding = OcsBase64.encodeBase64(data.getUsername() + ":" + data.getPassword());
				} catch (UnsupportedEncodingException e) {
					logger.error("Errore in encoding", e);
					return new TokenRetrieverData(true, null);
				}
				// set headers
				headers.add(HttpHeaders.AUTHORIZATION, "Basic " + encoding);
				headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);

				// Creazione di request body in formato URL encoded data
				// (x-www-form-urlencoded)
				map.put("grant_type", "client_credentials");
				map.put("scope", data.getScope());

				try {
					OcsGenericRestResponse<TokenIntesaResponse> restResponse = Invoker.callEndpoint(url.toString(),
							null, HttpMethod.POST, headers, map, null, 0, false, TokenIntesaResponse.class, logger);
					Throwable e = restResponse.getError();
					if (e != null) {
						logger.error("Errore richiesta token da servizio IntesaAuthentication", e);
						return new TokenRetrieverData(true, null);
					}
					token = restResponse.getResponse().getAccessToken();
				} catch (Exception e) {
					logger.error("Errore in invocazione servizio di autenticazione" + e);
					return new TokenRetrieverData(true, null);
				}

				return new TokenRetrieverData(true, token);
			}
		};

		// Recupero parametri per comunicazione.
		ClientDataIntesa data = AuthenticationProperties.getData(NomeApplicazione.INTESAAUTHENTICATIONCLIENT,
				ClientDataIntesa.class);
		if (data == null) {
			logger.warn("Impossibile recuperare dati di autenticazione");
			return new TokenRetrieverData(false, null);
		}

		try {
			return GenericWsManager.applyFunctionWithParameters(ff, data, data.getParams(), logger);
		} catch (Exception e) {
			throw new AuthenticationException(e.getMessage(), e);
		}
	}

}
