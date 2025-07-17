package it.dmsoft.flowmanager.agent.engine.ftp.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.net.ftp.FTPFile;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import it.dmsoft.flowmanager.agent.engine.ftp.model.FtpInfo;
import it.dmsoft.flowmanager.agent.engine.ftp.model.FtpRequest;
import it.dmsoft.flowmanager.agent.engine.ftp.model.FtpResponse;
import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.NumericManager;
import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.OcsBase64;
import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.OcsXmlConverter;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.AppOpePar;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.GenericWsManager;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.ResponseGenericClient;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.ResponseWrapper;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.KeyValueLog;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;
import it.dmsoft.flowmanager.agent.engine.utility.ftp.FtpParameters;
import it.dmsoft.flowmanager.agent.engine.utility.ftp.OcsFtpClient;
import it.dmsoft.flowmanager.agent.engine.utility.ftp.PathReader;

public class FtpSendManager {
	public static final String FORWARD_SLASH = "/";

	private static Logger logger;

	public static ResponseGenericClient runWs(JSONObject parGen, JSONObject parCli, JSONObject output) {
		logger = Logger.getLogger(FtpSendManager.class);
		logger.debug("Chiamato runWs di " + FtpSendManager.class.getSimpleName() + ".class");

		ResponseGenericClient responseWs = new ResponseGenericClient(ResponseGenericClient.RISPOSTA_KO);

		// Parametri specifici
		if (logger.isDebugEnabled()) {
			logger.debug("Parametri specifici: " + parCli.toString());
		}
		FtpRequest request = null;
		try {
			request = createRequest(parCli);
		} catch (Exception e) {
			logger.error("Errore in creazione richiesta", e);
			responseWs.setException(e);
			return responseWs;
		}

		ResponseWrapper<FtpResponse> res = new ResponseWrapper<FtpResponse>();

		try {
			res = runWsCore(request, responseWs);
			res.setResponseWs(responseWs);
		} catch (Exception e) {
			logger.error("Errore in invocazione del servizio send", e);
			responseWs.setException(e);
			return responseWs;
		}

		try {
			setResponse(res.getResponse(), parCli, output);
		} catch (Exception e) {
			logger.error("Errore in elaborazione risposta", e);
			responseWs.setException(e);
			return responseWs;
		}

		responseWs.setResponse(ResponseGenericClient.RISPOSTA_OK);
		return responseWs;
	}

	private static ResponseWrapper<FtpResponse> runWsCore(FtpRequest request, ResponseGenericClient responseWs) throws IOException {
		logger.info("Invocazione del servizio send");
		logger.info(new KeyValueLog("Dati di input", request.toString()));
		ResponseWrapper<FtpResponse> res = new ResponseWrapper<FtpResponse>();
		FtpResponse response = new FtpResponse();
		if (responseWs == null) {
			responseWs = new ResponseGenericClient();
		}
		res.setResponseWs(responseWs);
		res.setResponse(response);
		long start = System.currentTimeMillis();
		try {
			FtpParameters ftpParams = request.getFtpParameters();

			OcsFtpClient ftp = new OcsFtpClient(ftpParams, logger);
			ftp.open();
			logger.debug("Connessione FTP aperta.");
			try {
				String localPath = request.getLocalPath();
				String remotePath = request.getRemotePath();
				boolean isRemoteDir = ftp.isRemoteDir(remotePath);
				if (!isRemoteDir) {
					String message = "Remote path deve puntare ad una cartella.";
					logger.error(message);
					responseWs.setResponse(ResponseGenericClient.RISPOSTA_KO);
					responseWs.setMessage(message);
					return res;
				}

				if (isRemoteDir && !remotePath.endsWith(FORWARD_SLASH) && remotePath != null && !"".equals(remotePath)) {
					remotePath += FORWARD_SLASH;
				}

				for (String realLocalPath : PathReader.getFilePaths(localPath)) {
					File localFile = new File(realLocalPath);

					boolean isLocalDir = localFile.isDirectory();

					if (isLocalDir) {
						File[] files = localFile.listFiles();

						int filesCopied = 0;
						for (File file : files) {
							if (!file.isDirectory()) {
								String dest = remotePath + file.getName();
								ftp.put(file, dest);
								FTPFile ftpFile = ftp.ls(dest)[0];
								if (!ftpFile.getName().equals(file.getName()) || ftpFile.getSize() != file.length()) {
									String message = file.getName() + ": Nome file o dimensione non coerente";
									logger.debug(message);
									responseWs.setResponse(ResponseGenericClient.RISPOSTA_KO);
									responseWs.setMessage(message);
									return res;
								}
								filesCopied += 1;
							}
						}
						if (logger.isDebugEnabled()) {
							logger.debug(filesCopied + " file copiat" + (filesCopied == 1 ? "o." : "i."));
						}
					} else {
						String destFile = localFile.getName();
						if (request.getRemoteFile() != null && !"".equals(request.getRemoteFile())) {
							destFile = request.getRemoteFile();
						}
						ftp.put(localFile, remotePath, destFile);
						String remoteFilePath = remotePath+destFile;
						FTPFile ftpFile = ftp.ls(remoteFilePath)[0];
						// Per i file singoli è possibile rinominare il file di destinazione,
						// quindi non effettuo il controllo di coerenza sul nome.
						if (ftpFile.getSize() != localFile.length()) {
							String message = "Dimensione non coerente";
							logger.debug(message);
							logger.debug("Dimensione file locale: " + localFile.length());
							logger.debug("Dimensione file remoto: " + ftpFile.getSize());
							//responseWs.setResponse(ResponseGenericClient.RISPOSTA_KO);
							responseWs.setMessage(message);
							return res;
						}
						logger.debug("1 file copiato.");
					}
				}
			} catch (FileNotFoundException e) {
				responseWs.setResponse(ResponseGenericClient.RISPOSTA_KO);
				responseWs.setMessage(e.getMessage());
				return res;
			} finally {
				ftp.close();
				logger.debug("Connessione FTP chiusa.");
			}
		} finally {
			long duration = System.currentTimeMillis() - start;
			logger.info("Durata servizio send: " + duration + " ms");
			res.getResponseWs().setServiceInvocationDuration(duration);
		}
		return res;
	}

	public static ResponseWrapper<FtpResponse> exposedRun(AppOpePar parametri, Logger loggerExt, String localPath, String remotePath, String remoteFile, boolean removeAfter, String ftpHost,
			String ftpUser, String ftpPassword, int ftpPort, boolean passiveMode, boolean secureConnection) throws IOException, KeyManagementException, NoSuchAlgorithmException {
		FtpRequest request = new FtpRequest();
		request.setLocalPath(localPath);
		request.setRemotePath(remotePath);
		request.setRemoveAfter(removeAfter);
		request.setRemoteFile(remoteFile);
		FtpParameters ftpParameters = new FtpParameters();
		ftpParameters.setFtpHost(ftpHost);
		ftpParameters.setFtpUser(ftpUser);
		ftpParameters.setFtpPassword(ftpPassword);
		ftpParameters.setFtpPort(ftpPort);
		ftpParameters.setPassiveMode(passiveMode);
		ftpParameters.setSecureConnection(secureConnection);
		request.setFtpParameters(ftpParameters);

		logger = loggerExt == null ? Logger.getLogger(FtpSendManager.class) : loggerExt;

		GenericWsManager.setupGeneric(parametri, logger);
		return runWsCore(request, null);
	}

	/**
	 * @deprecated
	 * @param parametri
	 * @param loggerExt
	 * @param localPath
	 * @param remotePath
	 * @param removeAfter
	 * @param ftpHost
	 * @param ftpUser
	 * @param ftpPassword
	 * @param ftpPort
	 * @param passiveMode
	 * @return
	 * @throws IOException
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 */
	@Deprecated
	public static ResponseWrapper<FtpResponse> exposedRun(AppOpePar parametri, Logger loggerExt, String localPath, String remotePath, boolean removeAfter, String ftpHost, String ftpUser,
			String ftpPassword, int ftpPort, boolean passiveMode) throws IOException, KeyManagementException, NoSuchAlgorithmException {
		return exposedRun(parametri, loggerExt, localPath, remotePath, null, removeAfter, ftpHost, ftpUser, ftpPassword, ftpPort, passiveMode, false);
	}

	public static void main(String[] args) throws ParseException {
		JSONObject parGen = (JSONObject) new JSONParser().parse(
				"{\"NUMERO_JOB_BCI\": \"321201\", \"NUMERO_JOB\": \"319144\", \"PACKAGE\": \"it.ocsnet.ftp.manager\", \"CLASS\": \"it.ocsnet.ftp.manager.FtpSendManager\", \"APP_OPEN\": \"OCSFTPMANAGER\", \"METHOD\": \"runWs\", \"comuni\": {\"CLASSE\": \"\\/ocs\\/ocsftpmanager\\/v1.7\\/ocsftpmanager.jar\", \"URL_WS\": \"\", \"TIMEOUT\": \"00000\", \"SECRET_KEY\": \"\", \"INIT_VECTOR\": \"\", \"AMBIENTE_WS\": \"\", \"LOG_DOCUM\": \"\\/ocs\\/log\\/wrk\\/OcsFtpManager.log\", \"LOG_LEVEL\": \"INFO\", \"LOG_NUM_DOC\": \"05\", \"LOG_DIM\": \"0010\", \"PROXY_IP\": \"\", \"PROXY_PORTA\": \"00000\", \"PROXY_USER\": \"\", \"PROXY_PWD\": \"\", \"BASIC_USER_ID\": \"\", \"BASIC_PWD\": \"\", \"CERT_CLIENT_PATH\": \"\", \"CERT_CLIENT_PWD\": \"\", \"CERT_CLIENT_ALIAS\": \"\", \"KEYSTORE_SERVER_PATH\": \"\", \"KEYSTORE_SERVER_PWD\": \"\", \"KEYSTORE_SERVER_ALIAS\": \"\", \"REMOTO_USER\": \"prova\", \"REMOTO_PWD\": \"prova\", \"REMOTO_REP\": \"\", \"REMOTO_SOC\": \"\", \"FILE_PATH_OUT\": \"\", \"FILE_URL\": \"\", \"URL_WS_BATCH_GEDO\": \"\", \"VISUALIZATION\": \"\", \"VERSIONE_JAVA\": \"\", \"PROTOCOLLO\": \"\", \"TRUSTSTORE_PATH\": \"\", \"TRUSTSTORE_PWD\": \"\", \"URL_TOKEN\": \"\", \"LOG_DOCUM_ALT\": \"\", \"LOG_LEVEL_ALT\": \"\"}, \"visCamerali\": {\"CRIF_REMOTO_USER\": \"\", \"CRIF_REMOTO_PWD\": \"\"}, \"crim\": {\"SERVICE_TYPE\": \"\", \"ENV\": \"\", \"HEADER_VER\": \"\", \"LANGUAGE\": \"\", \"ARCH_USER_ID\": \"\", \"ARCH_PASSWORD\": \"\", \"APPLICAZIONE\": \"\", \"VERSION_MAJOR\": \"\", \"VERSION_MINOR\": \"\"}, \"fdi\": {\"WS_USER\": \"\", \"WS_PWD\": \"\", \"AS400_IP\": \"\"}, \"kccs\": {\"CERT_ALIAS_REQ\": \"\", \"CERT_ALIAS_RES\": \"\"}, \"assilea\": {\"NUM_MAX_TENTATIVI\": \"000\"}, \"wsHost20\": {\"CENTRO_COSTO\": \"\", \"VERSIONE\": \"\"}, \"cerved\": {\"CLASSE_INS\": \"\", \"CLASSE_VER\": \"\", \"CLASSE_ANN\": \"\", \"MACCHINA\": \"\", \"USER_CERVED\": \"\", \"PWD_CERVED\": \"\", \"INDEPENDENT_ASP\": \"\", \"CHK_CERTIFICATO\": \"\"}, \"inps\": {\"MIN_INTERVALLO\": \"00000\", \"NUM_MAX_TRASM\": \"00000\"}, \"ifcedoges\": {\"objectStore\": \"\", \"objectClass\": \"\", \"matricola\": \"\", \"ruolo\": \"\", \"filiale\": \"\", \"maxRows\": \"00000\", \"dogesPath\": \"\", \"dogesUrl\": \"\", \"dogesCifratura\": \"\"}, \"cse\": {\"ABI\": \"\", \"SERVICE_NAME\": \"\", \"SERVICE_VERS\": \"\"}}");
		JSONObject parCli = (JSONObject) new JSONParser().parse(
				"{\"RemotePath\": \"\", \"LocalPath\": \"\\/HOME\\/DIEGOA\\/GESTOREFLUSSI\\/TEST1.FTP\", \"RemoveAfter\": \"True\", \"RemoteFile\": \"TEST1.FTP\", \"FtpParameters\": {\"PassiveMode\": \"True\", \"FtpHost\": \"FINCONSUMO\", \"FtpPort\": \"21.00000\", \"FtpUser\": \"OCSFTP\", \"FtpPassword\": \"OCSFTP\"}}");
		JSONObject output = (JSONObject) new JSONParser().parse("{}");
		runWs(parGen, parCli, output);
	}

	private static FtpRequest createRequest(JSONObject jsonFtpRequest01) {
		FtpRequest ftpRequest01 = new FtpRequest();
		if (jsonFtpRequest01.containsKey("LocalPath")) {
			ftpRequest01.setLocalPath((String) jsonFtpRequest01.get("LocalPath"));
		}
		if (jsonFtpRequest01.containsKey("RemotePath")) {
			ftpRequest01.setRemotePath((String) jsonFtpRequest01.get("RemotePath"));
		}
		if (jsonFtpRequest01.containsKey("RemoteFile")) {
			ftpRequest01.setRemoteFile((String) jsonFtpRequest01.get("RemoteFile"));
		}
		if (jsonFtpRequest01.containsKey("RemoveAfter")) {
			ftpRequest01.setRemoveAfter("True".equals(jsonFtpRequest01.get("RemoveAfter")));
		}
		if (jsonFtpRequest01.containsKey("FtpParameters")) {
			JSONObject jsonFtpParameters02 = (JSONObject) jsonFtpRequest01.get("FtpParameters");
			FtpParameters ftpParameters02 = new FtpParameters();
			ftpRequest01.setFtpParameters(ftpParameters02);
			if (jsonFtpParameters02.containsKey("FtpHost")) {
				ftpParameters02.setFtpHost((String) jsonFtpParameters02.get("FtpHost"));
			}
			if (jsonFtpParameters02.containsKey("FtpUser")) {
				ftpParameters02.setFtpUser((String) jsonFtpParameters02.get("FtpUser"));
			}
			if (jsonFtpParameters02.containsKey("FtpPassword")) {
				ftpParameters02.setFtpPassword((String) jsonFtpParameters02.get("FtpPassword"));
			}
			if (jsonFtpParameters02.containsKey("FtpPort")) {
				ftpParameters02.setFtpPort(NumericManager.parseInt(((String) jsonFtpParameters02.get("FtpPort"))));
			}
			if (jsonFtpParameters02.containsKey("PassiveMode")) {
				ftpParameters02.setPassiveMode("True".equals(jsonFtpParameters02.get("PassiveMode")));
			}
		}
		return ftpRequest01;
	}

	@SuppressWarnings("unchecked")
	private static void setResponse(FtpResponse ftpResponse01, JSONObject parCli, JSONObject jsonFtpResponse01) {
		OcsBase64.setFileNames(parCli);
		OcsXmlConverter.setNameSpaces(parCli);
		if (ftpResponse01.getFtpInfo() != null) {
			JSONArray jsonFtpInfo02 = new JSONArray();
			jsonFtpResponse01.put("FtpInfo", jsonFtpInfo02);
			for (int iFtpInfo02 = 0; iFtpInfo02 < ftpResponse01.getFtpInfo().size(); iFtpInfo02++) {
				JSONObject jsonFtpInfo03 = new JSONObject();
				FtpInfo ftpInfo03 = ftpResponse01.getFtpInfo().get(iFtpInfo02);
				jsonFtpInfo02.add(jsonFtpInfo03);
				if (ftpInfo03.getCodice() != null && ftpInfo03.getCodice().value() != null) {
					jsonFtpInfo03.put("Codice", ftpInfo03.getCodice().value());
				}
				if (ftpInfo03.getDescrizione() != null) {
					jsonFtpInfo03.put("Descrizione", ftpInfo03.getDescrizione());
				}
			}
		}
	}

}
