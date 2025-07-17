package it.dmsoft.flowmanager.agent.engine.generic.authentication.common;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

//import it.copergmps.soa.contesto.lite.soap.SoapLiteException;
//import it.copergmps.soa.contesto.lite.soap.SoapUtility;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.active_directory.TokenRetrieverActiveDirectory;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.active_directory.parameters.data.ClientDataActiveDirectory;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.output.Retriever;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.output.RetrieverDataAbstract;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.output.RetrieverFactory;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.output.RetrieverFactory.RetrieverFactoryException;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.output.data.TokenRetrieverData;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.parameters.ClientDataAbstract;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.parameters.data.ClientDataJWT;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.parameters.data.ClientDataOauth;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.compass.TokenRetrieverCompass;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.compass.parameters.data.ClientDataCompass;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.diana.TokenRetrieverDiana;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.diana.output.data.TokenRetrieverDataDiana;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.diana.parameters.data.ClientDataDiana;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.exception.AuthenticationException;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.intesaAuth.TokenRetrieverIntesa;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.intesaAuth.parameters.data.ClientDataIntesa;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.keycloak.TokenRetrieverKeycloak;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.oauth2.TokenRetrieverOAuth2;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.paydoplickup.TokenRetrieverPayDoPlickUp;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.santander.TokenRetrieverSantander;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.santander.TokenRetrieverSantanderScb;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.santander.parameters.data.ClientDataSantander;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.widiba.TokenRetrieverWidiba;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.widiba.output.data.TokenRetrieverDataWidiba;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.widiba.parameters.data.ClientDataWidiba;
import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.OcsBase64;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.AppOpePar;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.GenericWsManager;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.KeyValueLog;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class AuthenticationProperties {
	public static final String REST_HEADERS = "REST_HEADERS";
	public static final String SOAP_HEADERS = "SOAP_HEADERS";
	public static final String SOAP_BINDINGPROVIDER = "SOAP_BINDINGPROVIDER";
	public static final String LOGGER = "LOGGER";
	public static final String TOKEN = "TOKEN";
	public static final String USER = "USER";
	public static final String PWD = "PWD";
	public static final String USA_TOKEN_FISICO = "USA_TOKEN_FISICO";

	private AuthenticationProperties() {

	}

	private static Logger staticLogger;
	private static EnumMap<NomeApplicazione, ClientDataAbstract> authArray = new EnumMap<AuthenticationProperties.NomeApplicazione, ClientDataAbstract>(NomeApplicazione.class);

	public enum NomeApplicazione {
		WIDIBAAUTHENTICATIONCLIENT(TokenRetrieverWidiba.class) {

			// Meotodo richiamato dai client per invocazione
			// dell'autenticazione.
			@Override
			public void authenticate(Logger logger, Map<String, Object> params) throws AuthenticationException, MalformedURLException, RetrieverFactoryException {
				// Se in input riceve il token lo gestisce senza rihiamare il
				// metodo di autenticazione specifico
				String token = calculateToken(params);
				if (params != null && params.containsKey(USA_TOKEN_FISICO) && params.get(USA_TOKEN_FISICO) != null && "S".equals(String.valueOf(params.get(USA_TOKEN_FISICO))) && token != null
						&& !"".equals(token)) {
					logger.info("Ricevuto token da client. Non invocato sistema di autenticazione Widiba");
					TokenRetrieverDataWidiba retrieverData = new TokenRetrieverDataWidiba(token, true);
					super.addHeader(retrieverData, logger, params);
				} else {
					logger.info("Invocato sistema di autenticazione Widiba");
					setTemporaryCredentials(params);
					super.authenticate(logger, params);
				}
			}

			/**
			 * Per alcuni applicativi open bisogna andare in override sulle
			 * credenziali impostata nel WIDIBAAUTHENTICATIONCLIENT.
			 * 
			 * @param params
			 */
			private void setTemporaryCredentials(Map<String, Object> params) {
				String user = null;
				String pwd = null;

				if (params != null && params.containsKey(USER) && params.get(USER) != null && params.get(USER) instanceof String) {
					user = (String) params.get(USER);
				}
				if (params != null && params.containsKey(PWD) && params.get(PWD) != null && params.get(PWD) instanceof String) {
					pwd = (String) params.get(PWD);
				}

				ClientDataWidiba clientData = AuthenticationProperties.getData(NomeApplicazione.WIDIBAAUTHENTICATIONCLIENT, ClientDataWidiba.class);
				if (clientData != null) {
					// Imposta le credenziali di default di
					// WIDIBAAUTHENTICATIONCLIENT.
					// Questo viene fatto sempre per rempistare la situazione
					// corretta che potrebbe essere cambiata dopo chiamate con
					// credenziali forzate. Viene fatta prima e non dopo per
					// evitare casistiche in cui ad esempio la chiamata
					// precedente ha sollevato delel eccezioni è non si è
					// conclusa correttamente

					clientData.resetCredentials();
					if (user != null && pwd != null) {
						clientData.setUsername(user);
						clientData.setPassword(pwd);
					}
				}
			}

			private String calculateToken(Map<String, Object> params) {
				String token = null;
				if (params != null && params.containsKey(TOKEN) && params.get(TOKEN) != null) {
					if (params.get(TOKEN) instanceof String) {
						token = (String) params.get(TOKEN);
					} else if (params.get(TOKEN) instanceof ArrayList<?>) {
						@SuppressWarnings("unchecked")
						ArrayList<Object> arrTmp = (ArrayList<Object>) params.get(TOKEN);
						if (arrTmp != null && !arrTmp.isEmpty() && arrTmp.get(0) instanceof String) {
							StringBuilder build = new StringBuilder();
							for (Object tkTmp : arrTmp) {
								if (tkTmp != null) {
									build.append((String) tkTmp);
								}
							}
							token = build.toString();
						}
					}
				}
				return token;
			}

			@Override
			protected void authenticateREST(RetrieverDataAbstract data, Map<String, Object> params) throws AuthenticationException {
				HttpHeaders headers = (HttpHeaders) params.get(REST_HEADERS);
				TokenRetrieverDataWidiba tokenAuth = (TokenRetrieverDataWidiba) getRetrieverData(data);
				headers.setContentType(MediaType.APPLICATION_JSON);
				String token = tokenAuth.getToken();
				if (token != null && !token.contains("Bearer")) {
					token = "Bearer " + token;
				}
				headers.set("Authorization", token);
				headers.set("accept", "application/json");
			}

			@Override
			protected void authenticateSOAP(RetrieverDataAbstract data, Map<String, Object> params) throws AuthenticationException {
				Logger logger = Logger.getLogger(AuthenticationProperties.class);
				if (params.get(LOGGER) instanceof Logger) {
					logger = (Logger) params.get(LOGGER);
				}
				Map<String, List<String>> headers = new HashMap<String, List<String>>();
				@SuppressWarnings("unchecked")
				Map<String, Object> rc = (Map<String, Object>) params.get(SOAP_HEADERS);
				TokenRetrieverDataWidiba tokenAuth = (TokenRetrieverDataWidiba) getRetrieverData(data);
				List<String> tokenInHeader = Collections.singletonList("Bearer " + tokenAuth.getToken());
				headers.put("Authorization", tokenInHeader);
				rc.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
				BindingProvider provider = (BindingProvider) params.get(SOAP_BINDINGPROVIDER);
				if (provider != null) {
					String[] chunks = tokenAuth.getToken().split("\\.");
					String payload = new String(OcsBase64.decodeBase64(chunks[1]));
					logger.debug(new KeyValueLog("Payload ", payload));
					JSONObject jsonPayload = (JSONObject) JSONValue.parse(payload);
					if (jsonPayload != null) {
						logger.debug(new KeyValueLog("Payload json", jsonPayload.toString()));
						String smToken = (String) jsonPayload.get("smToken");
						logger.debug(new KeyValueLog("smToken", smToken));
						String smAmbiente = (String) jsonPayload.get("smAmbiente");
						logger.debug(new KeyValueLog("smAmbiente", smAmbiente));
						String smAziendaTmp = (String) jsonPayload.get("smAzienda");
						logger.debug(new KeyValueLog("smAziendaTmp", smAziendaTmp));
						long smAzienda = 0;
						if (smAziendaTmp != null) {
							smAzienda = Long.parseLong(smAziendaTmp);
						}
						logger.debug(new KeyValueLog("smAzienda", Long.toString(smAzienda)));
						String smRAC = (String) jsonPayload.get("smRAC");
						logger.debug(new KeyValueLog("smRAC", smRAC));
						//SoapUtility soaputility = new SoapUtility(smToken, smAmbiente, smAzienda, smRAC);
						try {
							//soaputility.addCustomHeaders(provider);
						} catch (Exception /*SoapLiteException*/ e) {
							throw new AuthenticationException("Errore in aggiunta Header", e);
						}
					} else {
						logger.warn("Payload non valorizzato, impostazione header interrotta");
					}

				}

			}

			@Override
			protected RetrieverDataAbstract getRetrieverData(RetrieverDataAbstract data) throws AuthenticationException {
				TokenRetrieverDataWidiba tokenAuth = (TokenRetrieverDataWidiba) data;
				if (tokenAuth.getToken() == null || tokenAuth.getToken().equals("")) {
					throw new AuthenticationException("Errore nel recupero del token di autenticazione.");
				}
				return tokenAuth;
			}
		},
		DIANAAUTHENTICATIONCLIENT(TokenRetrieverDiana.class) {

			private static final String CORRELATION_ID_KEY_IN_HEADER = "x-correlation-id";

			@Override
			protected void authenticateSOAP(RetrieverDataAbstract data, Map<String, Object> params) throws AuthenticationException {
				TokenRetrieverDataDiana tokenRetrieverDataDiana = (TokenRetrieverDataDiana) getRetrieverData(data);
				@SuppressWarnings("unchecked")
				Map<String, Object> rc = (Map<String, Object>) params.get(SOAP_HEADERS);
				List<String> correlationIdInHeader = Collections.singletonList(tokenRetrieverDataDiana.getCorrelationId());
				List<String> tokenInHeader = Collections.singletonList(tokenRetrieverDataDiana.getToken());
				Map<String, List<String>> headers = new HashMap<String, List<String>>();
				headers.put("Authorization", tokenInHeader);
				headers.put(CORRELATION_ID_KEY_IN_HEADER, correlationIdInHeader);
				rc.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
			}

			@Override
			protected RetrieverDataAbstract getRetrieverData(RetrieverDataAbstract data) throws AuthenticationException {
				TokenRetrieverDataDiana tokenRetrieverDataDiana = (TokenRetrieverDataDiana) data;
				// Controllo corretta valorizzazione dati di autenticazione.
				String token = tokenRetrieverDataDiana.getToken();
				if (token == null || token.equals("")) {
					throw new AuthenticationException("Errore nel recupero del token");
				}
				String correlationId = tokenRetrieverDataDiana.getCorrelationId();
				if (correlationId == null || correlationId.equals("")) {
					throw new AuthenticationException("Errore nel recupero del correlationId");
				}
				return tokenRetrieverDataDiana;
			}
		},
		SANTANDERAUTHENTICATIONCLIENT(TokenRetrieverSantander.class) {
			// Implementazione specifica del metodo autenticate per valore
			// dell'ENUM.
			@Override
			protected void authenticateREST(RetrieverDataAbstract data, Map<String, Object> params) throws AuthenticationException {
				TokenRetrieverData tokenAuth = getRetrieverData(data);
				HttpHeaders headers = (HttpHeaders) params.get(REST_HEADERS);
				headers.setContentType(MediaType.APPLICATION_JSON);
				headers.set("Authorization", tokenAuth.getToken());
				headers.set("content-type", "application/json");
				headers.set("accept", "application/json");
			}

			@Override
			protected TokenRetrieverData getRetrieverData(RetrieverDataAbstract data) throws AuthenticationException {
				TokenRetrieverData tokenAuth = (TokenRetrieverData) data;
				if (tokenAuth.getToken() == null || tokenAuth.getToken().equals("")) {
					throw new AuthenticationException("Errore nel recupero del token di autenticazione.");
				}
				return tokenAuth;
			}
		},
		SANTANDERSCBAUTHENTICATIONCLIENT(TokenRetrieverSantanderScb.class) {
			@Override
			protected void authenticateREST(RetrieverDataAbstract data, Map<String, Object> params) throws AuthenticationException {
				SANTANDERAUTHENTICATIONCLIENT.authenticateREST(data, params);
			}

			@Override
			protected RetrieverDataAbstract getRetrieverData(RetrieverDataAbstract data) throws AuthenticationException {
				return SANTANDERAUTHENTICATIONCLIENT.getRetrieverData(data);
			}

		},
		COMPASSAUTHENTICATIONCLIENT(TokenRetrieverCompass.class) {
			// Implementazione specifica del metodo autenticate per valore
			// dell'ENUM.
			@Override
			protected void authenticateREST(RetrieverDataAbstract data, Map<String, Object> params) throws AuthenticationException {
				TokenRetrieverData tokenAuth = getRetrieverData(data);
				HttpHeaders headers = (HttpHeaders) params.get(REST_HEADERS);
				headers.setContentType(MediaType.APPLICATION_JSON);
				String token = tokenAuth.getToken();
				if (token != null && !token.contains("Bearer")) {
					token = "Bearer " + token;
				}
				headers.set("Authorization", token);
				headers.set("content-type", "application/json");
				headers.set("accept", "application/json");
			}

			@Override
			protected TokenRetrieverData getRetrieverData(RetrieverDataAbstract data) throws AuthenticationException {
				TokenRetrieverData tokenAuth = (TokenRetrieverData) data;
				if (tokenAuth.getToken() == null || tokenAuth.getToken().equals("")) {
					throw new AuthenticationException("Errore nel recupero del token di autenticazione.");
				}
				return tokenAuth;
			}

		},
		ACTIVEDIRECTORYCLIENT(TokenRetrieverActiveDirectory.class) {

			@Override
			protected void authenticateREST(RetrieverDataAbstract data, Map<String, Object> params) throws AuthenticationException {
				TokenRetrieverData tokenAuth = (TokenRetrieverData) getRetrieverData(data);
				HttpHeaders headers = (HttpHeaders) params.get(REST_HEADERS);
				headers.setContentType(MediaType.APPLICATION_JSON);
				String token = tokenAuth.getToken();
				if (token != null && !token.contains("Bearer")) {
					token = "Bearer " + token;
				}
				headers.set("Authorization", token);
				headers.set("content-type", "application/json");
				headers.set("accept", "application/json");
			}

			@Override
			protected RetrieverDataAbstract getRetrieverData(RetrieverDataAbstract data) throws AuthenticationException {
				TokenRetrieverData tokenAuth = (TokenRetrieverData) data;
				if (tokenAuth.getToken() == null || tokenAuth.getToken().equals("")) {
					throw new AuthenticationException("Errore nel recupero del token di autenticazione.");
				}
				return tokenAuth;
			}

		},
		KEYCLOAKAUTHORIZATIONCLIENT(TokenRetrieverKeycloak.class) {

			@Override
			protected void authenticateREST(RetrieverDataAbstract data, Map<String, Object> params) throws AuthenticationException {
				TokenRetrieverData tokenAuth = (TokenRetrieverData) getRetrieverData(data);
				HttpHeaders headers = (HttpHeaders) params.get(REST_HEADERS);
				headers.setContentType(MediaType.APPLICATION_JSON);
				String token = tokenAuth.getToken();
				if (token != null && !token.contains("Bearer")) {
					token = "Bearer " + token;
				}
				headers.set("Authorization", token);
				headers.set("Content-Type", "application/json");
			}

			@Override
			protected RetrieverDataAbstract getRetrieverData(RetrieverDataAbstract data) throws AuthenticationException {
				TokenRetrieverData tokenAuth = (TokenRetrieverData) data;
				if (tokenAuth.getToken() == null || tokenAuth.getToken().equals("")) {
					throw new AuthenticationException("Errore nel recupero del token di autenticazione.");
				}
				return tokenAuth;
			}
		},
		OAUTH2CLIENT(TokenRetrieverOAuth2.class) {
			@Override
			protected void authenticateREST(RetrieverDataAbstract data, Map<String, Object> params) throws AuthenticationException {
				TokenRetrieverData tokenAuth = (TokenRetrieverData) getRetrieverData(data);
				HttpHeaders headers = (HttpHeaders) params.get(REST_HEADERS);
				headers.setContentType(MediaType.APPLICATION_JSON);
				String token = tokenAuth.getToken();
				if (token != null && !token.contains("Bearer")) {
					token = "Bearer " + token;
				}
				headers.set("Authorization", token);
				headers.set("Content-Type", "application/json");
			}

			@Override
			protected RetrieverDataAbstract getRetrieverData(RetrieverDataAbstract data) throws AuthenticationException {
				TokenRetrieverData tokenAuth = (TokenRetrieverData) data;
				if (tokenAuth.getToken() == null || tokenAuth.getToken().equals("")) {
					throw new AuthenticationException("Errore nel recupero del token di autenticazione.");
				}
				return tokenAuth;
			}
		},
		PAYDOPLICKUPAUTHENTICATIONCLIENT(TokenRetrieverPayDoPlickUp.class) {
			@Override
			protected void authenticateREST(RetrieverDataAbstract data, Map<String, Object> params) throws AuthenticationException {
				TokenRetrieverData tokenAuth = (TokenRetrieverData) getRetrieverData(data);
				HttpHeaders headers = (HttpHeaders) params.get(REST_HEADERS);
				headers.setContentType(MediaType.APPLICATION_JSON);
				String token = tokenAuth.getToken();
				if (token != null && !token.contains("Bearer")) {
					token = "Bearer " + token;
				}
				headers.set("Authorization", token);
				headers.set("Content-Type", "application/json");
			}

			@Override
			protected RetrieverDataAbstract getRetrieverData(RetrieverDataAbstract data) throws AuthenticationException {
				TokenRetrieverData tokenAuth = (TokenRetrieverData) data;
				if (tokenAuth.getToken() == null || tokenAuth.getToken().equals("")) {
					throw new AuthenticationException("Errore nel recupero del token di autenticazione.");
				}
				return tokenAuth;
			}
		},
		INTESAAUTHENTICATIONCLIENT(TokenRetrieverIntesa.class) {
			@Override
			protected void authenticateREST(RetrieverDataAbstract data, Map<String, Object> params) throws AuthenticationException {
				TokenRetrieverData tokenAuth = (TokenRetrieverData) getRetrieverData(data);
				HttpHeaders headers = (HttpHeaders) params.get(REST_HEADERS);
				headers.setContentType(MediaType.APPLICATION_JSON);
				String token = tokenAuth.getToken();
				if (token != null && !token.contains("Bearer")) {
					token = "Bearer " + token;
				}
				headers.set("Authorization", token);
				headers.set("Content-Type", "application/json");
			}

			@Override
			protected RetrieverDataAbstract getRetrieverData(RetrieverDataAbstract data) throws AuthenticationException {
				TokenRetrieverData tokenAuth = (TokenRetrieverData) data;
				if (tokenAuth.getToken() == null || tokenAuth.getToken().equals("")) {
					throw new AuthenticationException("Errore nel recupero del token di autenticazione.");
				}
				return tokenAuth;
			}
		};

		private Class<? extends Retriever<?>> clazz;

		// Metodo per recuperare classe da invocare per l'autenticazione del
		// client.
		private NomeApplicazione(Class<? extends Retriever<?>> clazz) {
			this.clazz = clazz;
		}

		// Meotodo richiamato dai client per invocazione dell'autenticazione.
		public void authenticate(Logger logger, Map<String, Object> params) throws AuthenticationException, MalformedURLException, RetrieverFactoryException {
			RetrieverDataAbstract retrieverData = (RetrieverDataAbstract) RetrieverFactory.create(clazz).get(logger);
			addHeader(retrieverData, logger, params);
		}

		private void addHeader(RetrieverDataAbstract retrieverData, Logger logger, Map<String, Object> params) throws AuthenticationException {
			if (retrieverData != null && params != null && retrieverData.isAttivo()) {
				if (params.containsKey(REST_HEADERS)) {
					authenticateREST(retrieverData, params);
				} else if (params.containsKey(SOAP_HEADERS)) {
					authenticateSOAP(retrieverData, params);
				} else {
					throw new AuthenticationException("Nessun header SOAP/REST ricevuto");
				}
			}
		}

		protected void authenticateSOAP(RetrieverDataAbstract data, Map<String, Object> params) throws AuthenticationException {
			throw new AuthenticationException("metodo non ancora gestito");
		}

		protected void authenticateREST(RetrieverDataAbstract data, Map<String, Object> params) throws AuthenticationException {
			throw new AuthenticationException("metodo non ancora gestito");
		}

		// Metodo protetto implementato dai valori dell'ENUM.
		protected abstract RetrieverDataAbstract getRetrieverData(RetrieverDataAbstract data) throws AuthenticationException;

		// Implementazione del moetodo contains per controllare che la stringa
		// passata sia un valore dell'ENUM.
		public static boolean contains(String value) {
			for (NomeApplicazione n : NomeApplicazione.values()) {
				if (n.name().equals(value)) {
					return true;
				}
			}
			return false;
		}
	}

	private static void configureLogger(JSONObject jsonParLogger) {
		String sizeLog = (String) jsonParLogger.get("SIZE");
		String maxBackupIndex = (String) jsonParLogger.get("MAX_BCK_INDEX");
		String path = (String) jsonParLogger.get("PATH");
		String logLevel = (String) jsonParLogger.get("LOG_LEVEL");
		String jobNumber = (String) jsonParLogger.get("JOB_NUMBER");
		String cliente = (String) jsonParLogger.get("CLIENTE");
		GenericWsManager.setCliente(cliente);
		String clazz = AuthenticationProperties.class.getCanonicalName();
		staticLogger = Logger.getLogger(sizeLog, maxBackupIndex, path, logLevel, jobNumber, clazz, cliente, "GenericWsClient");
		staticLogger.debug(new KeyValueLog("jobNumberBci", jobNumber));

	}

	public static void init(String parApps) {
		try {

			JSONObject jsonParAppObj = (JSONObject) JSONValue.parse(parApps);
			configureLogger((JSONObject) jsonParAppObj.get("LOGGER"));

			// Controllo presenza APPS_AUTH in JSON in input.
			if (!parApps.contains("APPS_AUTH")) {
				staticLogger.warn("blocco autenticazione non trovato. Istanza authArray annullata.");
				return;
			}
			JSONArray jsonParAppsArray = (JSONArray) jsonParAppObj.get("APPS_AUTH");
			staticLogger.info("Creazione array per client di autenticazione." + parApps);
			HashMap<String, JSONObject> parametri = readAppInfo(jsonParAppObj);
			HashMap<NomeApplicazione, JSONObject> parametriAuth = readAppAuthInfo(parametri);
			for (int i = 0; i < jsonParAppsArray.size(); i++) {
				JSONObject jsonParAppsAuth = (JSONObject) jsonParAppsArray.get(i);
				if (NomeApplicazione.contains((String) jsonParAppsAuth.get("APPLICAZIONE"))) {
					NomeApplicazione applicationName = NomeApplicazione.valueOf((String) jsonParAppsAuth.get("APPLICAZIONE"));
					String urlToken = (String) jsonParAppsAuth.get("URL_TOKEN");
					String urlAuth = (String) jsonParAppsAuth.get("URL");
					String username = (String) jsonParAppsAuth.get("USERNAME");
					String password = (String) jsonParAppsAuth.get("PASSWORD");

					// Parametri per comunicazione in fase di auth
					String keyStoreFileName;
					String keyStorePassword;
					String protocol;
					String proxyIP;
					String proxyPort;
					String proxyUser;
					String proxyPass;
					String truststorePath;
					String truststorePwd;
					if (parametriAuth.containsKey(applicationName)) {
						JSONObject jsonParam = parametriAuth.get(applicationName);
						keyStoreFileName = (String) jsonParam.get("KEYSTORE_SERVER_PATH");
						keyStorePassword = (String) jsonParam.get("KEYSTORE_SERVER_PWD");
						protocol = (String) jsonParam.get("PROTOCOLLO");
						proxyIP = (String) jsonParam.get("PROXY_IP");
						proxyPort = (String) jsonParam.get("PROXY_PORTA");
						proxyUser = (String) jsonParam.get("PROXY_USER");
						proxyPass = (String) jsonParam.get("PROXY_PWD");
						truststorePath = (String) jsonParam.get("TRUSTSTORE_PATH");
						truststorePwd = (String) jsonParam.get("TRUSTSTORE_PWD");
					} else {
						keyStoreFileName = (String) jsonParAppsAuth.get("KEYSTORE_SERVER_PATH");
						keyStorePassword = (String) jsonParAppsAuth.get("KEYSTORE_SERVER_PWD");
						protocol = (String) jsonParAppsAuth.get("PROTOCOLLO");
						proxyIP = (String) jsonParAppsAuth.get("PROXY_IP");
						proxyPort = (String) jsonParAppsAuth.get("PROXY_PORTA");
						proxyUser = (String) jsonParAppsAuth.get("PROXY_USER");
						proxyPass = (String) jsonParAppsAuth.get("PROXY_PWD");
						truststorePath = (String) jsonParAppsAuth.get("TRUSTSTORE_PATH");
						truststorePwd = (String) jsonParAppsAuth.get("TRUSTSTORE_PWD");
					}
					AppOpePar par = new AppOpePar();
					par.setKeyStoreFileName(keyStoreFileName);
					par.setKeyStorePassword(keyStorePassword);
					par.setProtocol(protocol);
					par.setProxyIP(proxyIP);
					par.setProxyPort(proxyPort);
					par.setProxyUser(proxyUser);
					par.setProxyPass(proxyPass);
					par.setTruststorePath(truststorePath);
					par.setTruststorePwd(truststorePwd);

					String clientId = "";
					String clientSecret = "";
					String scope = "";
					ClientDataAbstract data = null;
					switch (applicationName) {
					case SANTANDERAUTHENTICATIONCLIENT:
					case SANTANDERSCBAUTHENTICATIONCLIENT:
						clientId = String.valueOf(jsonParAppsAuth.get("CLIENT_ID"));
						clientSecret = String.valueOf(jsonParAppsAuth.get("CLIENT_SECRET"));
						data = new ClientDataSantander(par, clientId, clientSecret, urlAuth);
						break;
					case COMPASSAUTHENTICATIONCLIENT:
						clientId = String.valueOf(jsonParAppsAuth.get("CLIENT_ID"));
						clientSecret = String.valueOf(jsonParAppsAuth.get("CLIENT_SECRET"));
						scope = String.valueOf(jsonParAppsAuth.get("SCOPE"));
						data = new ClientDataCompass(par, clientId, clientSecret, scope, username, password, urlAuth);
						break;
					case DIANAAUTHENTICATIONCLIENT:
						int timeout = Integer.parseInt((String) jsonParAppsAuth.get("TIMEOUT"));
						data = new ClientDataDiana(par, urlAuth, urlToken, username, password, timeout);
						break;
					case WIDIBAAUTHENTICATIONCLIENT:
						int numeroTentativi = 1;
						clientId = String.valueOf(jsonParAppsAuth.get("CLIENT_ID"));
						clientSecret = String.valueOf(jsonParAppsAuth.get("CLIENT_SECRET"));
						String azienda = String.valueOf(jsonParAppsAuth.get("AZIENDA"));
						String rac = String.valueOf(jsonParAppsAuth.get("RAC"));
						if (!String.valueOf(jsonParAppsAuth.get("N_TENTATIVI")).equals("")) {
							numeroTentativi = Integer.parseInt(String.valueOf(jsonParAppsAuth.get("N_TENTATIVI")));
						}
						data = new ClientDataWidiba(par, urlAuth, username, password, clientId, clientSecret, numeroTentativi, rac, azienda);
						break;
					case ACTIVEDIRECTORYCLIENT:
						clientId = String.valueOf(jsonParAppsAuth.get("CLIENT_ID"));
						clientSecret = String.valueOf(jsonParAppsAuth.get("CLIENT_SECRET"));
						data = new ClientDataActiveDirectory(par, urlAuth, clientId, clientSecret, String.valueOf(jsonParAppsAuth.get("SCOPE")), String.valueOf(jsonParAppsAuth.get("TENANT_ID")));
						break;
					case KEYCLOAKAUTHORIZATIONCLIENT:
						clientId = String.valueOf(jsonParAppsAuth.get("CLIENT_ID"));
						clientSecret = String.valueOf(jsonParAppsAuth.get("CLIENT_SECRET"));
						scope = "";
						data = new ClientDataOauth(par, urlAuth, clientId, clientSecret, scope);
						break;
					case OAUTH2CLIENT:
					case PAYDOPLICKUPAUTHENTICATIONCLIENT:
						data = new ClientDataJWT(par, urlAuth, null, username, password);
						break;
					case INTESAAUTHENTICATIONCLIENT:
						scope = String.valueOf(jsonParAppsAuth.get("SCOPE"));
						data = new ClientDataIntesa(par, urlAuth, username, password, scope);
						break;
					default:
						data = new ClientDataJWT(par, urlAuth, urlToken, username, password);
						break;
					}
					authArray.put(applicationName, data);
					firstCall(applicationName, parametri);
				}
			}
		} catch (Exception e) {
			staticLogger.error("impossibile istanziare HashMap con valori per autenticazione", e);
		}
	}

	/**
	 * Per alcuni applicativi esegue una chimata di log iniziale
	 * 
	 * @param applicationName
	 * @throws RetrieverFactoryException
	 * @throws AuthenticationException
	 * @throws MalformedURLException
	 */
	private static void firstCall(NomeApplicazione applicationName, HashMap<String, JSONObject> parametri) throws MalformedURLException, AuthenticationException, RetrieverFactoryException {
		// Per Widiba non deve essere invocato se
		if (applicationName.equals(NomeApplicazione.WIDIBAAUTHENTICATIONCLIENT) && !parametri.containsKey("CTCPLUSCLIENT")) {
			NomeApplicazione.WIDIBAAUTHENTICATIONCLIENT.authenticate(staticLogger, null);
		}
	}

	private static HashMap<NomeApplicazione, JSONObject> readAppAuthInfo(HashMap<String, JSONObject> appInfo) {
		@SuppressWarnings("rawtypes")
		Iterator it = appInfo.entrySet().iterator();
		HashMap<NomeApplicazione, JSONObject> parametri = new HashMap<AuthenticationProperties.NomeApplicazione, JSONObject>();
		while (it.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry obj = (Map.Entry) it.next();
			String appName = (String) obj.getKey();
			if (NomeApplicazione.contains(appName)) {
				parametri.put(NomeApplicazione.valueOf(appName), (JSONObject) obj.getValue());
			}
		}
		return parametri;
	}

	private static HashMap<String, JSONObject> readAppInfo(JSONObject jsonParAppObj) {
		HashMap<String, JSONObject> parametri = new HashMap<String, JSONObject>();
		JSONArray jsonParAppsArray = (JSONArray) jsonParAppObj.get("APPS");
		for (int i = 0; i < jsonParAppsArray.size(); i++) {
			JSONObject jsonParApp = (JSONObject) jsonParAppsArray.get(i);
			String appName = (String) jsonParApp.get("APP_NAME");
			parametri.put(appName, jsonParApp);
		}
		return parametri;
	}

	// Recupero dati per autenticazione da authArray in base alla classe
	// passata.
	@SuppressWarnings("unchecked")
	public static <T extends ClientDataAbstract> T getData(NomeApplicazione nomeApp, Class<T> clazz) {
		if (clazz.isInstance(authArray.get(nomeApp))) {
			return (T) authArray.get(nomeApp);
		}
		return null;
	}
}
