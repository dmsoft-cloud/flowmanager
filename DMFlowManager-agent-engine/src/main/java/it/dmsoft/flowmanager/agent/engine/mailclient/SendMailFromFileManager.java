package it.dmsoft.flowmanager.agent.engine.mailclient;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.mail.MessagingException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.NumericManager;
import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.OcsBase64;
import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.OcsXmlConverter;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.AppOpePar;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.GenericWsManager;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.ResponseGenericClient;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.ResponseWrapper;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.KeyValueLog;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;
import it.dmsoft.flowmanager.agent.engine.mailclient.interfacce.FileMailInfo;
import it.dmsoft.flowmanager.agent.engine.mailclient.interfacce.RequestWithFile;
import it.dmsoft.flowmanager.agent.engine.mailclient.interfacce.ResponseMail;
import it.dmsoft.flowmanager.agent.engine.mailclient.utility.Allegato;
import it.dmsoft.flowmanager.agent.engine.mailclient.utility.DataMail;
import it.dmsoft.flowmanager.agent.engine.mailclient.utility.Mappatura;
import it.dmsoft.flowmanager.agent.engine.mailclient.utility.SendEmail;

public class SendMailFromFileManager {
	static Logger logger;

	private SendMailFromFileManager() {

	}

	// Metodo chiamato da BCI
	public static ResponseGenericClient runWs(JSONObject parGen, JSONObject parCli, JSONObject output) {

		String jarPath = "";
		String keyStoreFileName = "";
		String keyStorePassword = "";
		String tls = "";
		logger = Logger.getLogger(SendMailFromFileManager.class);
		logger.debug("Chiamato runWs di " + SendMailFromFileManager.class.getSimpleName() + ".class");

		ResponseGenericClient responseWs = new ResponseGenericClient(ResponseGenericClient.RISPOSTA_KO);
		// Parametri client
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Parametri client: " + parCli.toString());
			}
		} catch (Exception e) {
			logger.error("Si sono verificati problemi nel parsing dei parametri client", e);
			responseWs.setException(e);
			return responseWs;
		}

		// Parametri generali
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Parametri generali: " + parGen.toString());
			}
			JSONObject comuni = (JSONObject) parGen.get("comuni");
			jarPath = (String) comuni.get("CLASSE");
			keyStoreFileName = (String) comuni.get("KEYSTORE_SERVER_PATH");
			keyStorePassword = (String) comuni.get("KEYSTORE_SERVER_PWD");
			tls = (String) comuni.get("PROTOCOLLO");
			logger.debug("jarPath: " + jarPath);
			logger.debug("keyStoreFileName: " + keyStoreFileName);
		} catch (Exception e) {
			logger.error("Si sono verificati problemi nel parsing dei parametri generali", e);
			responseWs.setException(e);
			return responseWs;
		}

		if (!"".equals(keyStoreFileName) && !"".equals(keyStorePassword)) {
			System.setProperty("javax.net.ssl.keyStore", keyStoreFileName);
			System.setProperty("javax.net.ssl.keyStorePassword", keyStorePassword);
			System.setProperty("javax.net.ssl.keyStoreType", "JKS");
		}

		DataMail dataMail = null;
		try {
			dataMail = new DataMail(createRequest(parCli));
		} catch (IOException e) {
			logger.error("Si sono verificati problemi nel parsing dei parametri specifici", e);
			responseWs.setException(e);
			return responseWs;
		}

		ResponseWrapper<ResponseMail> res = new ResponseWrapper<ResponseMail>();
		try {
			res = runWsCore(dataMail, responseWs, tls);
			responseWs = res.getResponseWs();
		} catch (MessagingException e) {
			logger.error("Si sono verificati problemi nell'invio della mail", e);
			responseWs.setException(e);
			return responseWs;
		} catch (UnsupportedEncodingException e) {
			logger.error("Si sono verificati problemi nell'invio della mail", e);
			responseWs.setException(e);
			return responseWs;
		} catch (FileNotFoundException e) {
			logger.error("Errore salvataggio file", e);
			responseWs.setException(e);
			return responseWs;
		} catch (IOException e) {
			logger.error("Errore salvataggio file", e);
			responseWs.setException(e);
			return responseWs;
		}
		setResponse(res.getResponse(), parCli, output);
		responseWs.setResponse(ResponseGenericClient.RISPOSTA_OK);

		return responseWs;
	}

	// Chiamato dal manager flussi
	public static ResponseWrapper<ResponseMail> exposedRun(AppOpePar parametri, Logger loggerExt, String hostname, int port, boolean secure, String smtpUsername, String smtpPassword, String from,
			ArrayList<String> tos, ArrayList<String> ccs, ArrayList<String> bccs, String subject, ArrayList<Allegato> allegati, int timeout, String body, boolean testoHtml)
			throws KeyManagementException, NoSuchAlgorithmException, MessagingException, FileNotFoundException, IOException {

		DataMail dataMailTmp = new DataMail();
		dataMailTmp.setHostname(hostname);
		dataMailTmp.setPort(port);
		dataMailTmp.setSecure(secure);
		dataMailTmp.setSmtpUsername(smtpUsername);
		dataMailTmp.setSmtpPassword(smtpPassword);
		dataMailTmp.setFrom(from);
		dataMailTmp.setTos(tos);
		dataMailTmp.setCcs(ccs);
		dataMailTmp.setBccs(bccs);
		dataMailTmp.setSubject(subject);
		dataMailTmp.setAllegati(allegati);
		dataMailTmp.setTimeout(timeout);
		dataMailTmp.setBody(body);
		dataMailTmp.setTestoHtml(testoHtml);

		logger = loggerExt == null ? Logger.getLogger(SendMailFromFileManager.class) : loggerExt;
		GenericWsManager.setupGeneric(parametri, logger);

		return runWsCore(dataMailTmp, null, parametri.getProtocol());
	}

	// Metodo chiamato da exposedRun
	private static ResponseWrapper<ResponseMail> runWsCore(DataMail dataMail, ResponseGenericClient responseWs, String tls) throws MessagingException, IOException {
		logger.info(new KeyValueLog("Dati di input", dataMail.toString()));

		ResponseWrapper<ResponseMail> res = new ResponseWrapper<ResponseMail>();
		if (responseWs == null) {
			responseWs = new ResponseGenericClient();
		}
		res.setResponseWs(responseWs);

		Long start = System.currentTimeMillis();
		try {
			res.setResponse(SendEmail.sendMail(dataMail, tls));
		} finally {
			res.getResponseWs().setServiceInvocationDuration((System.currentTimeMillis() - start) + " ms");
		}
		return res;
	}

	private static RequestWithFile createRequest(JSONObject jsonRequestWithFile01) {
		RequestWithFile requestWithFile01 = new RequestWithFile();
		if (jsonRequestWithFile01.containsKey("Allegati")) {
			JSONArray jsonAllegati02 = (JSONArray) jsonRequestWithFile01.get("Allegati");
			for (int iAllegati02 = 0; iAllegati02 < jsonAllegati02.size(); iAllegati02++) {
				JSONObject jsonAllegato03 = (JSONObject) jsonAllegati02.get(iAllegati02);
				Allegato allegato03 = new Allegato();
				requestWithFile01.getAllegati().add(allegato03);
				if (jsonAllegato03.containsKey("ContentId")) {
					allegato03.setContentId((String) jsonAllegato03.get("ContentId"));
				}
				if (jsonAllegato03.containsKey("Path")) {
					allegato03.setPath((String) jsonAllegato03.get("Path"));
				}
			}
		}
		if (jsonRequestWithFile01.containsKey("Bccs")) {
			JSONArray jsonBccs02 = (JSONArray) jsonRequestWithFile01.get("Bccs");
			for (int iBccs02 = 0; iBccs02 < jsonBccs02.size(); iBccs02++) {
				JSONObject jsonBccs03 = (JSONObject) jsonBccs02.get(iBccs02);
				if (jsonBccs03.containsKey("Bccs")) {
					requestWithFile01.getBccs().add((String) jsonBccs03.get("Bccs"));
				}
			}
		}
		if (jsonRequestWithFile01.containsKey("Ccs")) {
			JSONArray jsonCcs02 = (JSONArray) jsonRequestWithFile01.get("Ccs");
			for (int iCcs02 = 0; iCcs02 < jsonCcs02.size(); iCcs02++) {
				JSONObject jsonCcs03 = (JSONObject) jsonCcs02.get(iCcs02);
				if (jsonCcs03.containsKey("Ccs")) {
					requestWithFile01.getCcs().add((String) jsonCcs03.get("Ccs"));
				}
			}
		}
		if (jsonRequestWithFile01.containsKey("From")) {
			requestWithFile01.setFrom((String) jsonRequestWithFile01.get("From"));
		}
		if (jsonRequestWithFile01.containsKey("Hostname")) {
			requestWithFile01.setHostname((String) jsonRequestWithFile01.get("Hostname"));
		}
		if (jsonRequestWithFile01.containsKey("Mappature")) {
			JSONArray jsonMappature02 = (JSONArray) jsonRequestWithFile01.get("Mappature");
			for (int iMappature02 = 0; iMappature02 < jsonMappature02.size(); iMappature02++) {
				JSONObject jsonMappatura03 = (JSONObject) jsonMappature02.get(iMappature02);
				Mappatura mappatura03 = new Mappatura();
				requestWithFile01.getMappature().add(mappatura03);
				if (jsonMappatura03.containsKey("Anchor")) {
					mappatura03.setAnchor((String) jsonMappatura03.get("Anchor"));
				}
				if (jsonMappatura03.containsKey("Valore")) {
					mappatura03.setValore((String) jsonMappatura03.get("Valore"));
				}
			}
		}
		if (jsonRequestWithFile01.containsKey("PathBody")) {
			requestWithFile01.setPathBody((String) jsonRequestWithFile01.get("PathBody"));
		}
		if (jsonRequestWithFile01.containsKey("PathSaveMail")) {
			JSONObject jsonPathSaveMail02 = (JSONObject) jsonRequestWithFile01.get("PathSaveMail");
			if (jsonPathSaveMail02.containsKey("FileMailInfo")) {
				JSONObject jsonFileMailInfo03 = (JSONObject) jsonPathSaveMail02.get("FileMailInfo");
				FileMailInfo fileMailInfo03 = new FileMailInfo();
				requestWithFile01.setPathSaveMail(fileMailInfo03);
				if (jsonFileMailInfo03.containsKey("FileMail")) {
					fileMailInfo03.setFileMail((String) jsonFileMailInfo03.get("FileMail"));
				}
				if (jsonFileMailInfo03.containsKey("PathMail")) {
					fileMailInfo03.setPathMail((String) jsonFileMailInfo03.get("PathMail"));
				}
			}
		}
		if (jsonRequestWithFile01.containsKey("Port")) {
			requestWithFile01.setPort(NumericManager.parseInt(((String) jsonRequestWithFile01.get("Port"))));
		}
		if (jsonRequestWithFile01.containsKey("Secure")) {
			requestWithFile01.setSecure("True".equals(jsonRequestWithFile01.get("Secure")));
		}
		if (jsonRequestWithFile01.containsKey("SmtpPassword")) {
			requestWithFile01.setSmtpPassword((String) jsonRequestWithFile01.get("SmtpPassword"));
		}
		if (jsonRequestWithFile01.containsKey("SmtpUsername")) {
			requestWithFile01.setSmtpUsername((String) jsonRequestWithFile01.get("SmtpUsername"));
		}
		if (jsonRequestWithFile01.containsKey("Subject")) {
			requestWithFile01.setSubject((String) jsonRequestWithFile01.get("Subject"));
		}
		if (jsonRequestWithFile01.containsKey("TestoHtml")) {
			requestWithFile01.setTestoHtml("True".equals(jsonRequestWithFile01.get("TestoHtml")));
		}
		if (jsonRequestWithFile01.containsKey("Timeout")) {
			requestWithFile01.setTimeout(NumericManager.parseInt(((String) jsonRequestWithFile01.get("Timeout"))));
		}
		if (jsonRequestWithFile01.containsKey("Tos")) {
			JSONArray jsonTos02 = (JSONArray) jsonRequestWithFile01.get("Tos");
			for (int iTos02 = 0; iTos02 < jsonTos02.size(); iTos02++) {
				JSONObject jsonTos03 = (JSONObject) jsonTos02.get(iTos02);
				if (jsonTos03.containsKey("Tos")) {
					requestWithFile01.getTos().add((String) jsonTos03.get("Tos"));
				}
			}
		}
		return requestWithFile01;
	}

	@SuppressWarnings("unchecked")
	private static void setResponse(ResponseMail responseMail01, JSONObject parCli, JSONObject jsonResponseMail01) {
		OcsBase64.setFileNames(parCli);
		OcsXmlConverter.setNameSpaces(parCli);
		if (responseMail01.getMessageId() != null) {
			jsonResponseMail01.put("MessageId", responseMail01.getMessageId());
		}
		if (responseMail01.getPathSavedMail() != null) {
			JSONObject jsonPathSavedMail02 = new JSONObject();
			jsonResponseMail01.put("PathSavedMail", jsonPathSavedMail02);
			JSONObject jsonFileMailInfo03 = new JSONObject();
			FileMailInfo fileMailInfo03 = responseMail01.getPathSavedMail();
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
