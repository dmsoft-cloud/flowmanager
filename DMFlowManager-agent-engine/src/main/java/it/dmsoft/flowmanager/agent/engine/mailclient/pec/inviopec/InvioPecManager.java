package it.dmsoft.flowmanager.agent.engine.mailclient.pec.inviopec;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.NumericManager;
import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.OcsBase64;
import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.OcsXmlConverter;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.ResponseGenericClient;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.KeyValueLog;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;
import it.dmsoft.flowmanager.agent.engine.mailclient.interfacce.FileMailInfo;
import it.dmsoft.flowmanager.agent.engine.mailclient.pec.inviopec.interfacce.ImapEmailInfo;
import it.dmsoft.flowmanager.agent.engine.mailclient.pec.inviopec.interfacce.InvioPecResponse;
import it.dmsoft.flowmanager.agent.engine.mailclient.pec.inviopec.interfacce.InvioPecWithBody;
import it.dmsoft.flowmanager.agent.engine.mailclient.pec.utility.InvioPecUtility;
import it.dmsoft.flowmanager.agent.engine.mailclient.pec.utility.PecDataMail;
import it.dmsoft.flowmanager.agent.engine.mailclient.utility.Allegato;
import it.dmsoft.flowmanager.agent.engine.mailclient.utility.Mappatura;

public class InvioPecManager {
	static String tls = "";
	static int timeout = 0;

	private InvioPecManager() {

	}

	// Metodo chiamato da BCI
	public static ResponseGenericClient runWs(JSONObject parGen, JSONObject parCli, JSONObject output) {

		String jarPath = "";

		Logger logger = Logger.getLogger(InvioPecManager.class);
		logger.debug("Chiamato runWs di " + InvioPecManager.class.getSimpleName() + ".class");

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

		InvioPecWithBody request;
		try {
			request = createRequest(parCli);
		} catch (Exception e) {
			logger.error("Errore generico", e);
			responseWs.setException(e);
			return responseWs;
		}

		Long start = System.currentTimeMillis();
		// Response
		InvioPecResponse response = null;
		try {
			PecDataMail pecDataMail = new PecDataMail(request);
			response = InvioPecUtility.responsePec(pecDataMail, tls, timeout, logger);
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

	private static InvioPecWithBody createRequest(JSONObject jsonInvioPecWithBody01) {
		InvioPecWithBody invioPecWithBody01 = new InvioPecWithBody();
		if (jsonInvioPecWithBody01.containsKey("Allegati")) {
			JSONArray jsonAllegati02 = (JSONArray) jsonInvioPecWithBody01.get("Allegati");
			for (int iAllegati02 = 0; iAllegati02 < jsonAllegati02.size(); iAllegati02++) {
				JSONObject jsonAllegato03 = (JSONObject) jsonAllegati02.get(iAllegati02);
				Allegato allegato03 = new Allegato();
				invioPecWithBody01.getAllegati().add(allegato03);
				if (jsonAllegato03.containsKey("ContentId")) {
					allegato03.setContentId((String) jsonAllegato03.get("ContentId"));
				}
				if (jsonAllegato03.containsKey("Path")) {
					allegato03.setPath((String) jsonAllegato03.get("Path"));
				}
			}
		}
		if (jsonInvioPecWithBody01.containsKey("Bccs")) {
			JSONArray jsonBccs02 = (JSONArray) jsonInvioPecWithBody01.get("Bccs");
			for (int iBccs02 = 0; iBccs02 < jsonBccs02.size(); iBccs02++) {
				JSONObject jsonBccs03 = (JSONObject) jsonBccs02.get(iBccs02);
				if (jsonBccs03.containsKey("Bccs")) {
					invioPecWithBody01.getBccs().add((String) jsonBccs03.get("Bccs"));
				}
			}
		}
		if (jsonInvioPecWithBody01.containsKey("Body")) {
			invioPecWithBody01.setBody((String) jsonInvioPecWithBody01.get("Body"));
		}
		if (jsonInvioPecWithBody01.containsKey("Ccs")) {
			JSONArray jsonCcs02 = (JSONArray) jsonInvioPecWithBody01.get("Ccs");
			for (int iCcs02 = 0; iCcs02 < jsonCcs02.size(); iCcs02++) {
				JSONObject jsonCcs03 = (JSONObject) jsonCcs02.get(iCcs02);
				if (jsonCcs03.containsKey("Ccs")) {
					invioPecWithBody01.getCcs().add((String) jsonCcs03.get("Ccs"));
				}
			}
		}
		if (jsonInvioPecWithBody01.containsKey("From")) {
			invioPecWithBody01.setFrom((String) jsonInvioPecWithBody01.get("From"));
		}
		if (jsonInvioPecWithBody01.containsKey("Hostname")) {
			invioPecWithBody01.setHostname((String) jsonInvioPecWithBody01.get("Hostname"));
		}
		if (jsonInvioPecWithBody01.containsKey("ImapEmailInfo")) {
			JSONObject jsonImapEmailInfo02 = (JSONObject) jsonInvioPecWithBody01.get("ImapEmailInfo");
			ImapEmailInfo imapEmailInfo02 = new ImapEmailInfo();
			invioPecWithBody01.setImapEmailInfo(imapEmailInfo02);
			if (jsonImapEmailInfo02.containsKey("FileMail")) {
				imapEmailInfo02.setFileMail((String) jsonImapEmailInfo02.get("FileMail"));
			}
			if (jsonImapEmailInfo02.containsKey("PathMail")) {
				imapEmailInfo02.setPathMail((String) jsonImapEmailInfo02.get("PathMail"));
			}
		}
		if (jsonInvioPecWithBody01.containsKey("ImapHost")) {
			invioPecWithBody01.setImapHost((String) jsonInvioPecWithBody01.get("ImapHost"));
		}
		if (jsonInvioPecWithBody01.containsKey("ImapPort")) {
			invioPecWithBody01.setImapPort(NumericManager.parseInt(((String) jsonInvioPecWithBody01.get("ImapPort"))));
		}
		if (jsonInvioPecWithBody01.containsKey("ImapPwd")) {
			invioPecWithBody01.setImapPwd((String) jsonInvioPecWithBody01.get("ImapPwd"));
		}
		if (jsonInvioPecWithBody01.containsKey("ImapSecure")) {
			invioPecWithBody01.setImapSecure("True".equals((String) jsonInvioPecWithBody01.get("ImapSecure")));
		}
		if (jsonInvioPecWithBody01.containsKey("ImapUser")) {
			invioPecWithBody01.setImapUser((String) jsonInvioPecWithBody01.get("ImapUser"));
		}
		if (jsonInvioPecWithBody01.containsKey("Mappature")) {
			JSONArray jsonMappature02 = (JSONArray) jsonInvioPecWithBody01.get("Mappature");
			for (int iMappature02 = 0; iMappature02 < jsonMappature02.size(); iMappature02++) {
				JSONObject jsonMappatura03 = (JSONObject) jsonMappature02.get(iMappature02);
				Mappatura mappatura03 = new Mappatura();
				invioPecWithBody01.getMappature().add(mappatura03);
				if (jsonMappatura03.containsKey("Anchor")) {
					mappatura03.setAnchor((String) jsonMappatura03.get("Anchor"));
				}
				if (jsonMappatura03.containsKey("Valore")) {
					mappatura03.setValore((String) jsonMappatura03.get("Valore"));
				}
			}
		}
		if (jsonInvioPecWithBody01.containsKey("PathSaveMail")) {
			JSONObject jsonPathSaveMail02 = (JSONObject) jsonInvioPecWithBody01.get("PathSaveMail");
			if (jsonPathSaveMail02.containsKey("FileMailInfo")) {
				JSONObject jsonFileMailInfo03 = (JSONObject) jsonPathSaveMail02.get("FileMailInfo");
				FileMailInfo fileMailInfo03 = new FileMailInfo();
				invioPecWithBody01.setPathSaveMail(fileMailInfo03);
				if (jsonFileMailInfo03.containsKey("FileMail")) {
					fileMailInfo03.setFileMail((String) jsonFileMailInfo03.get("FileMail"));
				}
				if (jsonFileMailInfo03.containsKey("PathMail")) {
					fileMailInfo03.setPathMail((String) jsonFileMailInfo03.get("PathMail"));
				}
			}
		}
		if (jsonInvioPecWithBody01.containsKey("Port")) {
			invioPecWithBody01.setPort(NumericManager.parseInt(((String) jsonInvioPecWithBody01.get("Port"))));
		}
		if (jsonInvioPecWithBody01.containsKey("Secure")) {
			invioPecWithBody01.setSecure("True".equals((String) jsonInvioPecWithBody01.get("Secure")));
		}
		if (jsonInvioPecWithBody01.containsKey("SmtpPassword")) {
			invioPecWithBody01.setSmtpPassword((String) jsonInvioPecWithBody01.get("SmtpPassword"));
		}
		if (jsonInvioPecWithBody01.containsKey("SmtpUsername")) {
			invioPecWithBody01.setSmtpUsername((String) jsonInvioPecWithBody01.get("SmtpUsername"));
		}
		if (jsonInvioPecWithBody01.containsKey("Subject")) {
			invioPecWithBody01.setSubject((String) jsonInvioPecWithBody01.get("Subject"));
		}
		if (jsonInvioPecWithBody01.containsKey("TestoHtml")) {
			invioPecWithBody01.setTestoHtml("True".equals((String) jsonInvioPecWithBody01.get("TestoHtml")));
		}
		if (jsonInvioPecWithBody01.containsKey("Timeout")) {
			invioPecWithBody01.setTimeout(NumericManager.parseInt(((String) jsonInvioPecWithBody01.get("Timeout"))));
		}
		if (jsonInvioPecWithBody01.containsKey("Tos")) {
			JSONArray jsonTos02 = (JSONArray) jsonInvioPecWithBody01.get("Tos");
			for (int iTos02 = 0; iTos02 < jsonTos02.size(); iTos02++) {
				JSONObject jsonTos03 = (JSONObject) jsonTos02.get(iTos02);
				if (jsonTos03.containsKey("Tos")) {
					invioPecWithBody01.getTos().add((String) jsonTos03.get("Tos"));
				}
			}
		}
		return invioPecWithBody01;
	}

	@SuppressWarnings("unchecked")
	private static void setResponse(InvioPecResponse invioPecResponse01, JSONObject parCli,
			JSONObject jsonInvioPecResponse01) {
		OcsBase64.setFileNames(parCli);
		OcsXmlConverter.setNameSpaces(parCli);
		if (invioPecResponse01.getMsgId() != null) {
			jsonInvioPecResponse01.put("MsgId", invioPecResponse01.getMsgId());
		}
		if (invioPecResponse01.getPathSavedMail() != null) {
			JSONObject jsonPathSavedMail02 = new JSONObject();
			jsonInvioPecResponse01.put("PathSavedMail", jsonPathSavedMail02);
			JSONObject jsonFileMailInfo03 = new JSONObject();
			FileMailInfo fileMailInfo03 = invioPecResponse01.getPathSavedMail();
			jsonPathSavedMail02.put("FileMailInfo", jsonFileMailInfo03);
			if (fileMailInfo03.getFileMail() != null) {
				jsonFileMailInfo03.put("FileMail", fileMailInfo03.getFileMail());
			}
			if (fileMailInfo03.getPathMail() != null) {
				jsonFileMailInfo03.put("PathMail", fileMailInfo03.getPathMail());
			}
		}
	}

}
