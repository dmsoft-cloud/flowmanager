package it.dmsoft.flowmanager.agent.engine.mailclient.pec.verificapec;

import java.util.Iterator;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.NumericManager;
import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.OcsBase64;
import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.OcsXmlConverter;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.ResponseGenericClient;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.KeyValueLog;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;
import it.dmsoft.flowmanager.agent.engine.mailclient.pec.exception.PecDataException;
import it.dmsoft.flowmanager.agent.engine.mailclient.pec.utility.VerificaPecUtility;
import it.dmsoft.flowmanager.agent.engine.mailclient.pec.verificapec.interfacce.CheckResult;
import it.dmsoft.flowmanager.agent.engine.mailclient.pec.verificapec.interfacce.VerificaPecRequest;
import it.dmsoft.flowmanager.agent.engine.mailclient.pec.verificapec.interfacce.VerificaPecResponse;

public class VerificaPecManager {
	static String tls = "";
	static int timeout = 0;
	static String truststorePath = "";
	static String truststorePwd = "";

	private VerificaPecManager() {

	}

	// Metodo chiamato da BCI
	public static ResponseGenericClient runWs(JSONObject parGen, JSONObject parCli, JSONObject output) {

		String jarPath = "";
		Logger logger = Logger.getLogger(VerificaPecManager.class);
		logger.debug("Chiamato runWs di " + VerificaPecManager.class.getSimpleName() + ".class");

		ResponseGenericClient responseWs = new ResponseGenericClient(ResponseGenericClient.RISPOSTA_KO);

		// Parametri generali
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(new KeyValueLog("Parametri generali", parGen.toString()));
			}
			JSONObject comuni = (JSONObject) parGen.get("comuni");
			jarPath = (String) comuni.get("CLASSE");
			tls = (String) comuni.get("PROTOCOLLO");
			String timeoutStr = (String) comuni.get("TIMEOUT");
			try {
				timeout = Integer.parseInt(timeoutStr);
			} catch (NumberFormatException e) {
				logger.warn("Timeout non numerico");
			}
			truststorePath = (String) comuni.get("TRUSTSTORE_PATH");
			truststorePwd = (String) comuni.get("TRUSTSTORE_PWD");
			logger.info(new KeyValueLog("jarPath", jarPath));
		} catch (Exception e) {
			logger.error("Si sono verificati problemi nel parsing dei parametri generali", e);
			responseWs.setException(e);
			return responseWs;
		}

		// Parametri specifici
		if (logger.isDebugEnabled()) {
			logger.debug(new KeyValueLog("Parametri specifici", parCli.toString()));
		}

		VerificaPecRequest request = null;
		try {
			request = createRequest(parCli);
		} catch (Exception e) {
			logger.error("Errore generico", e);
			responseWs.setException(e);
			return responseWs;
		}

		Long start = System.currentTimeMillis();
		// Response
		VerificaPecResponse response = null;
		try {
			response = retrieveResult(request, responseWs, logger);
		} catch (Exception e) {
			logger.error("Errore in restituzione dati di output", e);
			responseWs.setException(e);
			return responseWs;
		} finally {
			long duration = System.currentTimeMillis() - start;
			logger.info("Durata servizio Pec : " + duration + " ms");
			responseWs.setServiceInvocationDuration(duration);
		}

		try {
			setResponse(response, parCli, output);
		} catch (Exception e1) {
			logger.error("Errore in restituzione dati di output", e1);
			responseWs.setException(e1);
			return responseWs;
		}

		responseWs.setResponse(ResponseGenericClient.RISPOSTA_OK);
		return responseWs;
	}

	private static VerificaPecResponse retrieveResult(VerificaPecRequest request, ResponseGenericClient responseWs,
			Logger logger) throws Exception {
		// Init connessione
		logger.info("Stabilisco connessione");
		try {
			VerificaPecUtility.init(request, timeout, tls, logger);
		} catch (MessagingException e) {
			logger.error("Errore nello stabilire la connessione");
			throw e;
		} catch (PecDataException e) {
			logger.error("Errore nei dati necessari per stabilire la connessione");
			throw e;
		}
		logger.info("Connessione effettuata");

		// Recupero interator
		Iterator<Message> iterator = VerificaPecUtility.retrieveIterator(request.getInboxFolder(), logger);
		if (iterator == null) {
			logger.error("Errore nel recupero iterator");
			throw new PecDataException("Errore nel recupero iterator");
		}
		logger.info(new KeyValueLog("Messaggi recuperati da InboxFolder", request.getInboxFolder()));
		String msgId;
		VerificaPecResponse response = new VerificaPecResponse();
		do {
			Message message = iterator.next();
			if (!(message instanceof MimeMessage)) {
				continue;
			}
			CheckResult result = null;
			MimeMessage mimeMessage = (MimeMessage) message;
			result = VerificaPecUtility.checkResult(mimeMessage, truststorePath, truststorePwd, logger);
			if (result == null) {
				continue;
			}
			logger.info("Ricevuta trovata");
			msgId = result.getMessageId();
			logger.debug("Messaggio ritornato per chiamata cobol: " + msgId);

			response.addResult(result);
		} while (iterator.hasNext());
		return response;
	}

	private static VerificaPecRequest createRequest(JSONObject jsonVerificaPecRequest01) {
		VerificaPecRequest verificaPecRequest01 = new VerificaPecRequest();
		if (jsonVerificaPecRequest01.containsKey("Hostname")) {
			verificaPecRequest01.setHostname((String) jsonVerificaPecRequest01.get("Hostname"));
		}
		if (jsonVerificaPecRequest01.containsKey("ImapPwd")) {
			verificaPecRequest01.setImapPwd((String) jsonVerificaPecRequest01.get("ImapPwd"));
		}
		if (jsonVerificaPecRequest01.containsKey("ImapUser")) {
			verificaPecRequest01.setImapUser((String) jsonVerificaPecRequest01.get("ImapUser"));
		}
		if (jsonVerificaPecRequest01.containsKey("InboxFolder")) {
			verificaPecRequest01.setInboxFolder((String) jsonVerificaPecRequest01.get("InboxFolder"));
		}
		if (jsonVerificaPecRequest01.containsKey("Port")) {
			verificaPecRequest01.setPort(NumericManager.parseInt(((String) jsonVerificaPecRequest01.get("Port"))));
		}
		if (jsonVerificaPecRequest01.containsKey("Secure")) {
			verificaPecRequest01.setSecure("True".equals((String) jsonVerificaPecRequest01.get("Secure")));
		}
		return verificaPecRequest01;
	}

	@SuppressWarnings("unchecked")
	private static void setResponse(VerificaPecResponse verificaPecResponse01, JSONObject parCli,
			JSONObject jsonVerificaPecResponse01) {
		OcsBase64.setFileNames(parCli);
		OcsXmlConverter.setNameSpaces(parCli);
		if (verificaPecResponse01.getResult() != null) {
			JSONArray jsonResult02 = new JSONArray();
			jsonVerificaPecResponse01.put("Result", jsonResult02);
			for (int iResult02 = 0; iResult02 < verificaPecResponse01.getResult().size(); iResult02++) {
				JSONObject jsonCheckResult03 = new JSONObject();
				CheckResult checkResult03 = verificaPecResponse01.getResult().get(iResult02);
				jsonResult02.add(jsonCheckResult03);
				if (checkResult03.getConsegna() != null) {
					jsonCheckResult03.put("Consegna", checkResult03.getConsegna());
				}
				if (checkResult03.getErrore() != null) {
					jsonCheckResult03.put("Errore", checkResult03.getErrore());
				}
				if (checkResult03.getErroreEsteso() != null) {
					jsonCheckResult03.put("ErroreEsteso", checkResult03.getErroreEsteso());
				}
				if (checkResult03.getGestoreEmittente() != null) {
					jsonCheckResult03.put("GestoreEmittente", checkResult03.getGestoreEmittente());
				}
				if (checkResult03.getGiorno() != null) {
					jsonCheckResult03.put("Giorno", checkResult03.getGiorno());
				}
				if (checkResult03.getMessageId() != null) {
					jsonCheckResult03.put("MessageId", checkResult03.getMessageId());
				}
				if (checkResult03.getOra() != null) {
					jsonCheckResult03.put("Ora", checkResult03.getOra());
				}
				if (checkResult03.getRemoteMessageId() != null) {
					jsonCheckResult03.put("RemoteMessageId", checkResult03.getRemoteMessageId());
				}
				if (checkResult03.getRicezione() != null) {
					JSONArray jsonRicezione05 = new JSONArray();
					jsonCheckResult03.put("Ricezione", jsonRicezione05);
					for (int iRicezione05 = 0; iRicezione05 < checkResult03.getRicezione().size(); iRicezione05++) {
						JSONObject jsonRicezione06 = new JSONObject();
						jsonRicezione05.add(jsonRicezione06);
						jsonRicezione06.put("Ricezione", checkResult03.getRicezione().get(iRicezione05));
					}
				}
				if (checkResult03.getTipo() != null) {
					jsonCheckResult03.put("Tipo", checkResult03.getTipo());
				}
				if (checkResult03.isValid()) {
					jsonCheckResult03.put("Valid", "True");
				} else {
					jsonCheckResult03.put("Valid", "False");
				}
			}
		}
	}

}
