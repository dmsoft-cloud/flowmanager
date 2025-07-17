package it.dmsoft.flowmanager.agent.engine.generic.authentication.widiba;

import java.util.HashMap;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.AuthenticationProperties;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.AuthenticationProperties.NomeApplicazione;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.output.Retriever;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.exception.AuthenticationException;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.widiba.interfaccie.TokenJWTResponseData;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.widiba.output.data.TokenRetrieverDataWidiba;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.widiba.parameters.data.ClientDataWidiba;
import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.restful.Invoker;
import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.restful.OcsGenericRestResponse;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.FailableFunction;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.GenericWsManager;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.KeyValueLog;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class TokenRetrieverWidiba implements Retriever<TokenRetrieverDataWidiba> {
	private static HashMap<String, TokenRetrieverDataWidiba> tokenOld = new HashMap<String, TokenRetrieverDataWidiba>();

	private static synchronized void setTokenOld(TokenRetrieverDataWidiba token, Logger logger) {
		ClientDataWidiba data = AuthenticationProperties.getData(NomeApplicazione.WIDIBAAUTHENTICATIONCLIENT, ClientDataWidiba.class);
		logger.info("Impostazione token");
		if (data != null) {
			logger.info(new KeyValueLog("Impostazione token per user", data.getUsername()));
			tokenOld.put(data.getUsername(), token);
		}
	}

	private static synchronized TokenRetrieverDataWidiba getTokenOld(Logger logger) {
		ClientDataWidiba data = AuthenticationProperties.getData(NomeApplicazione.WIDIBAAUTHENTICATIONCLIENT, ClientDataWidiba.class);
		if (data == null) {
			logger.warn("Data is null");
			return new TokenRetrieverDataWidiba();
		}
		logger.info(new KeyValueLog("data", data.toString()));
		if (tokenOld.get(data.getUsername()) == null) {
			tokenOld.put(data.getUsername(), new TokenRetrieverDataWidiba());
		}
		logger.info(new KeyValueLog("recupero tokenOld per username ", data.getUsername()));
		logger.info(new KeyValueLog("recupero tokenOld", tokenOld.get(data.getUsername()).toString()));

		return tokenOld.get(data.getUsername());
	}

	private static TokenRetrieverDataWidiba authenticationToken(Logger logger) throws AuthenticationException {
		ClientDataWidiba data = AuthenticationProperties.getData(NomeApplicazione.WIDIBAAUTHENTICATIONCLIENT, ClientDataWidiba.class);
		TokenRetrieverDataWidiba response = new TokenRetrieverDataWidiba();
		if (data == null) {
			logger.error("Impossibile recuperare dati di autenticazione");
			response.setAttivo(false);
			return response;
		} else {
			response.setAttivo(true);
		}

		logger.info(new KeyValueLog("endPoint", data.getUrl()));
		logger.info(new KeyValueLog("username", data.getUsername()));

		// Generazione Json body.
		logger.debug("Creazione Json Body");
		String body;
		try {
			body = createJson(data);
		} catch (JsonProcessingException e) {
			logger.error("Errore in restituzione dati di output", e);
			return response;
		}

		// Impostazione headers
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);

		// Setup per invocazione.
		OcsGenericRestResponse<TokenJWTResponseData> responseTokenJWT = null;
		long start = System.currentTimeMillis();
		int tentativoN = 0;
		// Invocazione servizio e
		// verifica se numero tentativi è stato impostato e re-invocazione
		// chiamata per n tentativi.
		boolean errore400 = false;
		do {
			tentativoN++;
			if (errore400) {
				logger.info("Rieseguo la chiamata di autenticazione");
			}
			errore400 = false;
			logger.debug(new KeyValueLog("Tentativo", tentativoN + "/" + data.getNumeroTentativi()));
			try {
				responseTokenJWT = Invoker.<TokenJWTResponseData> callEndpoint(data.getUrl(), null, HttpMethod.POST, headers, body, 0, false, TokenJWTResponseData.class, logger);
			} catch (Exception e) {
				logger.error("Errore in restituzione dati di output", e);
				long duration = System.currentTimeMillis() - start;
				logger.info("Durata recupero risorsa: " + duration + " ms");
				throw new AuthenticationException(e.getMessage(), e);
			}

			if (responseTokenJWT != null && responseTokenJWT.isErrori()) {
				int statusCode = 0; 
				try {
					statusCode = responseTokenJWT.getResponseError().getStatusCodeValue();
				}catch(Exception e){
					logger.error("errore in recupero StatusCode");
					throw new AuthenticationException("errore in recupero StatusCode");
				}
				if (statusCode == 400) {
					if (tentativoN < data.getNumeroTentativi()) {
						logger.info("Ricevuto statusCode 400");
					}
					errore400 = true;
				} else {
					throw new AuthenticationException("ricevuto statusCode diverso da 400");
				}
			}
		} while (tentativoN < data.getNumeroTentativi() && errore400);

		// Verifica se raggiunto numero massimo di tentativi
		if (tentativoN >= data.getNumeroTentativi() && errore400) {
			String msgErr = "Raggiunto numero massimo di tentativi per loggatura";
			logger.warn(msgErr);
			throw new AuthenticationException(msgErr);
		}
		// set Token se ricevuta risposta corretta.
		if (responseTokenJWT != null && responseTokenJWT.getResponse() != null && responseTokenJWT.getResponse().getIdToken() != null) {
			response.setToken(responseTokenJWT.getResponse().getIdToken());
			response.calcTimestampExpire(logger);
		}

		return response;
	}

	@Override
	public TokenRetrieverDataWidiba get(final Logger logger) throws AuthenticationException {
		if (getTokenOld(logger).scaduto(logger)) {
			logger.info("Token non più valido: invocazione per rinnovare il token");

			ClientDataWidiba data = AuthenticationProperties.getData(NomeApplicazione.WIDIBAAUTHENTICATIONCLIENT, ClientDataWidiba.class);
			TokenRetrieverDataWidiba response = new TokenRetrieverDataWidiba();
			if (data == null) {
				logger.error("Impossibile recuperare dati di autenticazione");
				response.setAttivo(false);
				return response;
			}

			FailableFunction<Void, TokenRetrieverDataWidiba> ff = new FailableFunction<Void, TokenRetrieverDataWidiba>() {
				@Override
				public TokenRetrieverDataWidiba apply(Void nulla, Logger logger) throws Exception {
					return authenticationToken(logger);
				}
			};

			try {
				GenericWsManager.applyFunctionWithParameters(ff, null, data.getParams(), logger);
			} catch (Exception e) {
				throw new AuthenticationException(e.getMessage(), e);
			}

			setTokenOld(authenticationToken(logger), logger);
			logger.info("Token rinnovato");
		} else {
			logger.info("Token ancora valido");
		}
		return getTokenOld(logger);
	}

	private static String createJson(ClientDataWidiba data) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode rootNode = mapper.createObjectNode();

		if (data.getUsername() != null && !data.getUsername().equals("")) {
			rootNode.put("username", data.getUsername());
		}
		if (data.getPassword() != null && !data.getPassword().equals("")) {
			rootNode.put("password", data.getPassword());
			rootNode.put("fgPswEncrypted", true);
		}
		if (data.getClientId() != null && !data.getClientId().equals("")) {
			rootNode.put("clientid", data.getClientId());
		}
		if (data.getClientSecret() != null && !data.getClientSecret().equals("")) {
			rootNode.put("clientsecret", data.getClientSecret());
		}
		if (data.getAzienda() != null && !data.getAzienda().equals("")) {
			rootNode.put("azienda", data.getAzienda());
		}
		if (data.getRac() != null && !data.getRac().equals("")) {
			rootNode.put("rac", data.getRac());
		}

		return mapper.writeValueAsString(rootNode);
	}
}
