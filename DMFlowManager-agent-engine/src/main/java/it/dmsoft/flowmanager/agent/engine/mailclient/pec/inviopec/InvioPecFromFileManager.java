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
import it.dmsoft.flowmanager.agent.engine.mailclient.pec.inviopec.interfacce.InvioPecWithFile;
import it.dmsoft.flowmanager.agent.engine.mailclient.pec.utility.InvioPecUtility;
import it.dmsoft.flowmanager.agent.engine.mailclient.pec.utility.PecDataMail;
import it.dmsoft.flowmanager.agent.engine.mailclient.utility.Allegato;
import it.dmsoft.flowmanager.agent.engine.mailclient.utility.Mappatura;

public class InvioPecFromFileManager {
	static String tls = "";
	static int timeout = 0;

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

		InvioPecWithFile request;
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

	private static InvioPecWithFile createRequest(JSONObject jsonInvioPecWithFile01) {
		InvioPecWithFile invioPecWithFile01 = new InvioPecWithFile();
		if (jsonInvioPecWithFile01.containsKey("Allegati")) {
			JSONArray jsonAllegati02 = (JSONArray) jsonInvioPecWithFile01.get("Allegati");
			for (int iAllegati02 = 0; iAllegati02 < jsonAllegati02.size(); iAllegati02++) {
				JSONObject jsonAllegato03 = (JSONObject) jsonAllegati02.get(iAllegati02);
				Allegato allegato03 = new Allegato();
				invioPecWithFile01.getAllegati().add(allegato03);
				if (jsonAllegato03.containsKey("ContentId")) {
					allegato03.setContentId((String) jsonAllegato03.get("ContentId"));
				}
				if (jsonAllegato03.containsKey("Path")) {
					allegato03.setPath((String) jsonAllegato03.get("Path"));
				}
			}
		}
		if (jsonInvioPecWithFile01.containsKey("Bccs")) {
			JSONArray jsonBccs02 = (JSONArray) jsonInvioPecWithFile01.get("Bccs");
			for (int iBccs02 = 0; iBccs02 < jsonBccs02.size(); iBccs02++) {
				JSONObject jsonBccs03 = (JSONObject) jsonBccs02.get(iBccs02);
				if (jsonBccs03.containsKey("Bccs")) {
					invioPecWithFile01.getBccs().add((String) jsonBccs03.get("Bccs"));
				}
			}
		}
		if (jsonInvioPecWithFile01.containsKey("Ccs")) {
			JSONArray jsonCcs02 = (JSONArray) jsonInvioPecWithFile01.get("Ccs");
			for (int iCcs02 = 0; iCcs02 < jsonCcs02.size(); iCcs02++) {
				JSONObject jsonCcs03 = (JSONObject) jsonCcs02.get(iCcs02);
				if (jsonCcs03.containsKey("Ccs")) {
					invioPecWithFile01.getCcs().add((String) jsonCcs03.get("Ccs"));
				}
			}
		}
		if (jsonInvioPecWithFile01.containsKey("From")) {
			invioPecWithFile01.setFrom((String) jsonInvioPecWithFile01.get("From"));
		}
		if (jsonInvioPecWithFile01.containsKey("Hostname")) {
			invioPecWithFile01.setHostname((String) jsonInvioPecWithFile01.get("Hostname"));
		}
		if (jsonInvioPecWithFile01.containsKey("ImapEmailInfo")) {
			JSONObject jsonImapEmailInfo02 = (JSONObject) jsonInvioPecWithFile01.get("ImapEmailInfo");
			ImapEmailInfo imapEmailInfo02 = new ImapEmailInfo();
			invioPecWithFile01.setImapEmailInfo(imapEmailInfo02);
			if (jsonImapEmailInfo02.containsKey("FileMail")) {
				imapEmailInfo02.setFileMail((String) jsonImapEmailInfo02.get("FileMail"));
			}
			if (jsonImapEmailInfo02.containsKey("PathMail")) {
				imapEmailInfo02.setPathMail((String) jsonImapEmailInfo02.get("PathMail"));
			}
		}
		if (jsonInvioPecWithFile01.containsKey("ImapHost")) {
			invioPecWithFile01.setImapHost((String) jsonInvioPecWithFile01.get("ImapHost"));
		}
		if (jsonInvioPecWithFile01.containsKey("ImapPort")) {
			invioPecWithFile01.setImapPort(NumericManager.parseInt(((String) jsonInvioPecWithFile01.get("ImapPort"))));
		}
		if (jsonInvioPecWithFile01.containsKey("ImapPwd")) {
			invioPecWithFile01.setImapPwd((String) jsonInvioPecWithFile01.get("ImapPwd"));
		}
		if (jsonInvioPecWithFile01.containsKey("ImapSecure")) {
			invioPecWithFile01.setImapSecure("True".equals((String) jsonInvioPecWithFile01.get("ImapSecure")));
		}
		if (jsonInvioPecWithFile01.containsKey("ImapUser")) {
			invioPecWithFile01.setImapUser((String) jsonInvioPecWithFile01.get("ImapUser"));
		}
		if (jsonInvioPecWithFile01.containsKey("Mappature")) {
			JSONArray jsonMappature02 = (JSONArray) jsonInvioPecWithFile01.get("Mappature");
			for (int iMappature02 = 0; iMappature02 < jsonMappature02.size(); iMappature02++) {
				JSONObject jsonMappatura03 = (JSONObject) jsonMappature02.get(iMappature02);
				Mappatura mappatura03 = new Mappatura();
				invioPecWithFile01.getMappature().add(mappatura03);
				if (jsonMappatura03.containsKey("Anchor")) {
					mappatura03.setAnchor((String) jsonMappatura03.get("Anchor"));
				}
				if (jsonMappatura03.containsKey("Valore")) {
					mappatura03.setValore((String) jsonMappatura03.get("Valore"));
				}
			}
		}
		if (jsonInvioPecWithFile01.containsKey("PathBody")) {
			invioPecWithFile01.setPathBody((String) jsonInvioPecWithFile01.get("PathBody"));
		}
		if (jsonInvioPecWithFile01.containsKey("PathSaveMail")) {
			JSONObject jsonPathSaveMail02 = (JSONObject) jsonInvioPecWithFile01.get("PathSaveMail");
			if (jsonPathSaveMail02.containsKey("FileMailInfo")) {
				JSONObject jsonFileMailInfo03 = (JSONObject) jsonPathSaveMail02.get("FileMailInfo");
				FileMailInfo fileMailInfo03 = new FileMailInfo();
				invioPecWithFile01.setPathSaveMail(fileMailInfo03);
				if (jsonFileMailInfo03.containsKey("FileMail")) {
					fileMailInfo03.setFileMail((String) jsonFileMailInfo03.get("FileMail"));
				}
				if (jsonFileMailInfo03.containsKey("PathMail")) {
					fileMailInfo03.setPathMail((String) jsonFileMailInfo03.get("PathMail"));
				}
			}
		}
		if (jsonInvioPecWithFile01.containsKey("Port")) {
			invioPecWithFile01.setPort(NumericManager.parseInt(((String) jsonInvioPecWithFile01.get("Port"))));
		}
		if (jsonInvioPecWithFile01.containsKey("Secure")) {
			invioPecWithFile01.setSecure("True".equals((String) jsonInvioPecWithFile01.get("Secure")));
		}
		if (jsonInvioPecWithFile01.containsKey("SmtpPassword")) {
			invioPecWithFile01.setSmtpPassword((String) jsonInvioPecWithFile01.get("SmtpPassword"));
		}
		if (jsonInvioPecWithFile01.containsKey("SmtpUsername")) {
			invioPecWithFile01.setSmtpUsername((String) jsonInvioPecWithFile01.get("SmtpUsername"));
		}
		if (jsonInvioPecWithFile01.containsKey("Subject")) {
			invioPecWithFile01.setSubject((String) jsonInvioPecWithFile01.get("Subject"));
		}
		if (jsonInvioPecWithFile01.containsKey("TestoHtml")) {
			invioPecWithFile01.setTestoHtml("True".equals((String) jsonInvioPecWithFile01.get("TestoHtml")));
		}
		if (jsonInvioPecWithFile01.containsKey("Timeout")) {
			invioPecWithFile01.setTimeout(NumericManager.parseInt(((String) jsonInvioPecWithFile01.get("Timeout"))));
		}
		if (jsonInvioPecWithFile01.containsKey("Tos")) {
			JSONArray jsonTos02 = (JSONArray) jsonInvioPecWithFile01.get("Tos");
			for (int iTos02 = 0; iTos02 < jsonTos02.size(); iTos02++) {
				JSONObject jsonTos03 = (JSONObject) jsonTos02.get(iTos02);
				if (jsonTos03.containsKey("Tos")) {
					invioPecWithFile01.getTos().add((String) jsonTos03.get("Tos"));
				}
			}
		}
		return invioPecWithFile01;
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
