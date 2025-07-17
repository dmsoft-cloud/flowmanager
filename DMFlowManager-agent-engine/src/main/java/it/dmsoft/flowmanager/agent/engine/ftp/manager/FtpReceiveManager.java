package it.dmsoft.flowmanager.agent.engine.ftp.manager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.net.ftp.FTPFile;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import it.dmsoft.flowmanager.agent.engine.ftp.model.FtpFileDescription;
import it.dmsoft.flowmanager.agent.engine.ftp.model.FtpInfo;
import it.dmsoft.flowmanager.agent.engine.ftp.model.FtpInfoCodice;
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

public class FtpReceiveManager {
	private static Logger logger;

	private FtpReceiveManager() {

	}

	public static ResponseGenericClient runWs(JSONObject parGen, JSONObject parCli, JSONObject output) {
		logger = Logger.getLogger(FtpReceiveManager.class);
		logger.debug("Chiamato runWs di " + FtpReceiveManager.class.getSimpleName() + ".class");

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
			logger.error("Errore in invocazione del servizio Receive", e);
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
		logger.info("Invocazione del servizio receive");
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
			logger.info(ftpParams.toString());

			OcsFtpClient ftp = new OcsFtpClient(ftpParams, logger);
			ftp.open();
			logger.debug("Connessione FTP aperta.");
			try {
				String localPath = request.getLocalPath();
				String remotePath = request.getRemotePath();
				if (request.getRemoteFile() != null && !"".equals(request.getRemoteFile())) {
					remotePath = Paths.get(remotePath, request.getRemoteFile()).toString();
				}
				if (logger.isDebugEnabled()) {
					logger.debug(new KeyValueLog("localPath", localPath), new KeyValueLog("remotePath", remotePath));
				}

				File localFile = new File(localPath);
				File remoteFile = new File(remotePath);
				boolean isRemoteDir = ftp.isRemoteDir(remotePath);

				int filesCopied = 0;
				FTPFile[] files = ftp.ls(remotePath);

				if (files.length == 0) {
					String parent = remoteFile.getParent();
					String remoteFileName = remoteFile.getName();
					if (!"".equals(remoteFileName)) {
						if (remoteFileName.contains("?") || remoteFileName.contains("*")) {
							FTPFile[] tmpFiles = ftp.ls(parent);
							List<FTPFile> filteredFiles = new ArrayList<FTPFile>();
							String pattern = getFileNamePattern(remoteFileName);
							for (FTPFile tmpFile : tmpFiles) {
								String tmpFileName = tmpFile.getName();
								if (tmpFileName.matches(pattern)) {
									filteredFiles.add(tmpFile);
								}
							}
							files = new FTPFile[filteredFiles.size()];
							files = filteredFiles.toArray(files);
						} else {
							FTPFile file = new FTPFile();
							file.setName(remoteFileName);
							file.setType(FTPFile.FILE_TYPE);
							files = new FTPFile[] { file };
						}
					}
				}

				StringBuffer sb = new StringBuffer("");
				for (FTPFile ftpFile : files) {
					logger.debug(ftpFile.getName());
					if (!ftpFile.isDirectory()) {
						String root = isRemoteDir ? remotePath : remoteFile.getParent();
						String remoteSrc = root + File.separator + ftpFile.getName();
						String dest = "";
						if (localFile.isDirectory()) {
							dest = localPath + File.separator + ftpFile.getName();
						} else {
							dest = localPath;
						}
						ftp.get(remoteSrc, dest);
						response.getDowloadedFiles().add(new FtpFileDescription(dest));
						filesCopied++;
						if (request.isRemoveAfter()) {
							try {
								ftp.rm(remoteSrc);
							} catch (IOException e) {
								logger.error(new KeyValueLog("Eliminazione fallita", remotePath), new KeyValueLog("Messaggio", e.getMessage()));
								sb.append(String.format("Eliminazione Fallita '%s' [%s]%n", remotePath, e.getMessage()));
								responseWs.setResponse(ResponseGenericClient.RISPOSTA_KO);
								response.getFtpInfo().add(new FtpInfo(FtpInfoCodice.NON_ELIMINATO, remotePath));
							}
						}
					}
				}
				String exceptionMessage = sb.toString();
				if (!"".equals(exceptionMessage)) {
					responseWs.setException(new Exception(exceptionMessage));
				}
				if (logger.isDebugEnabled()) {
					logger.debug(filesCopied + " file copiat " + (filesCopied == 1 ? "o." : "i."));
				}
			} finally {
				ftp.close();
				logger.debug("Connessione FTP chiusa.");
			}
		} finally {
			long duration = System.currentTimeMillis() - start;
			logger.info("Durata servizio receive: " + duration + " ms");
			res.getResponseWs().setServiceInvocationDuration(duration);
		}

		return res;
	}

	private static String getFileNamePattern(String remoteFileName) {
		int x = 0;
		List<String> patternParts = new ArrayList<String>();
		for (int i = 0; i < remoteFileName.length(); i++) {
			char charI = remoteFileName.charAt(i);
			if (charI == '?' || charI == '*') {
				patternParts.add(Pattern.quote(remoteFileName.substring(x, i)));
				patternParts.add(charI == '?' ? "." : ".*");
				x = i + 1;
			}
		}
		if (x < remoteFileName.length()) {
			patternParts.add(Pattern.quote(remoteFileName.substring(x, remoteFileName.length())));
		}
		StringBuilder sb = new StringBuilder();
		for (String patternPart : patternParts) {
			sb.append(patternPart);
		}
		return sb.toString();
	}

	public static ResponseWrapper<FtpResponse> exposedRun(AppOpePar parametri, Logger loggerExt, String localPath, String remotePath, String remoteFile, boolean removeAfter, String ftpHost,
			String ftpUser, String ftpPassword, int ftpPort, boolean passiveMode, boolean secureConnection) throws KeyManagementException, NoSuchAlgorithmException, IOException {
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

		logger = loggerExt == null ? Logger.getLogger(FtpReceiveManager.class) : loggerExt;
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
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	@Deprecated
	public static ResponseWrapper<FtpResponse> exposedRun(AppOpePar parametri, Logger loggerExt, String localPath, String remotePath, boolean removeAfter, String ftpHost, String ftpUser,
			String ftpPassword, int ftpPort, boolean passiveMode) throws KeyManagementException, NoSuchAlgorithmException, IOException {
		return exposedRun(parametri, loggerExt, localPath, remotePath, null, removeAfter, ftpHost, ftpUser, ftpPassword, ftpPort, passiveMode, false);
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
