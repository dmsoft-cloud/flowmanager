package it.dmsoft.flowmanager.agent.engine.mailclient.pec.verificapec;

import javax.mail.MessagingException;

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
import it.dmsoft.flowmanager.agent.engine.mailclient.pec.verificapec.interfacce.MoveInfo;
import it.dmsoft.flowmanager.agent.engine.mailclient.pec.verificapec.interfacce.MovePecRequest;
import it.dmsoft.flowmanager.agent.engine.mailclient.pec.verificapec.interfacce.MovePecResponse;

public class MovePecManager {
	static String tls = "";
	static int timeout = 0;
	static String truststorePath = "";
	static String truststorePwd = "";

	public static ResponseGenericClient runWs(JSONObject parGen, JSONObject parCli, JSONObject output) {
		ResponseGenericClient responseWs = new ResponseGenericClient(ResponseGenericClient.RISPOSTA_KO);

		String jarPath = "";
		Logger logger = Logger.getLogger(MovePecManager.class);
		logger.debug("Chiamato runWs di " + MovePecManager.class.getSimpleName() + ".class");

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

		MovePecRequest request = null;
		try {
			request = createRequest(parCli);
		} catch (Exception e) {
			logger.error("Errore generico", e);
			responseWs.setException(e);
			return responseWs;
		}

		Long start = System.currentTimeMillis();
		// Response
		MovePecResponse response = null;

		try {
			response = move(request, logger);
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

	private static MovePecResponse move(MovePecRequest request, Logger logger) throws Exception {
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

		MovePecResponse response = VerificaPecUtility.moveMessages(request, logger);

		return response;
	}

	private static MovePecRequest createRequest(JSONObject jsonMovePecRequest01) {
		MovePecRequest movePecRequest01 = new MovePecRequest();
		if (jsonMovePecRequest01.containsKey("Hostname")) {
			movePecRequest01.setHostname((String) jsonMovePecRequest01.get("Hostname"));
		}
		if (jsonMovePecRequest01.containsKey("ImapPwd")) {
			movePecRequest01.setImapPwd((String) jsonMovePecRequest01.get("ImapPwd"));
		}
		if (jsonMovePecRequest01.containsKey("ImapUser")) {
			movePecRequest01.setImapUser((String) jsonMovePecRequest01.get("ImapUser"));
		}
		if (jsonMovePecRequest01.containsKey("InboxFolder")) {
			movePecRequest01.setInboxFolder((String) jsonMovePecRequest01.get("InboxFolder"));
		}
		if (jsonMovePecRequest01.containsKey("MoveInfo")) {
			JSONArray jsonMoveInfo02 = (JSONArray) jsonMovePecRequest01.get("MoveInfo");
			for (int iMoveInfo02 = 0; iMoveInfo02 < jsonMoveInfo02.size(); iMoveInfo02++) {
				JSONObject jsonMoveInfo03 = (JSONObject) jsonMoveInfo02.get(iMoveInfo02);
				MoveInfo moveInfo03 = new MoveInfo();
				movePecRequest01.getMoveInfo().add(moveInfo03);
				if (jsonMoveInfo03.containsKey("MessageId")) {
					moveInfo03.setMessageId((String) jsonMoveInfo03.get("MessageId"));
				}
				if (jsonMoveInfo03.containsKey("MoveFolder")) {
					moveInfo03.setMoveFolder((String) jsonMoveInfo03.get("MoveFolder"));
				}
			}
		}
		if (jsonMovePecRequest01.containsKey("Port")) {
			movePecRequest01.setPort(NumericManager.parseInt(((String) jsonMovePecRequest01.get("Port"))));
		}
		if (jsonMovePecRequest01.containsKey("Secure")) {
			movePecRequest01.setSecure("True".equals((String) jsonMovePecRequest01.get("Secure")));
		}
		return movePecRequest01;
	}

	@SuppressWarnings("unchecked")
	private static void setResponse(MovePecResponse movePecResponse01, JSONObject parCli,
			JSONObject jsonMovePecResponse01) {
		OcsBase64.setFileNames(parCli);
		OcsXmlConverter.setNameSpaces(parCli);
		if (movePecResponse01.getResult() != null) {
			jsonMovePecResponse01.put("Result", movePecResponse01.getResult());
		}
	}
}
