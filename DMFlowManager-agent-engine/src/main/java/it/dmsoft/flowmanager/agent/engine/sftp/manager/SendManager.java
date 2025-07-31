package it.dmsoft.flowmanager.agent.engine.sftp.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.NumericManager;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.AppOpePar;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.GenericWsManager;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.ResponseGenericClient;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.ResponseWrapper;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.KeyValueLog;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;
import it.dmsoft.flowmanager.agent.engine.generic.utility.sftp.SftpConnection;
import it.dmsoft.flowmanager.agent.engine.generic.utility.sftp.SftpParameters;
import it.dmsoft.flowmanager.agent.engine.sftp.model.SftpInfo;
import it.dmsoft.flowmanager.agent.engine.sftp.model.SftpRequest;
import it.dmsoft.flowmanager.agent.engine.sftp.model.SftpResponse;
import it.dmsoft.flowmanager.agent.engine.sftp.utility.PathReader;
import it.dmsoft.flowmanager.common.domain.Domains.YesNo;

public class SendManager {
	private static final String FORWARD_SLASH = "/";
	private static Logger logger;

	public static ResponseGenericClient runWs(JSONObject parGen, JSONObject parCli, JSONObject output) {
		String jarPath = "";
		String endPoint = "";
		String username = "";
		String repository = "";
		logger = Logger.getLogger(SendManager.class);
		logger.debug("Chiamato runWs di " + SendManager.class.getSimpleName() + ".class");

		ResponseGenericClient responseWs = new ResponseGenericClient(ResponseGenericClient.RISPOSTA_KO);

		// Parametri generali
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Parametri generali: " + parGen.toString());
			}
			JSONObject parametriGenerali = (JSONObject) parGen.get("comuni");
			jarPath = (String) parametriGenerali.get("CLASSE");
			endPoint = (String) parametriGenerali.get("URL_WS");
			username = (String) parametriGenerali.get("REMOTO_USER");
			repository = (String) parametriGenerali.get("REMOTO_REP");
			logger.info("jarPath: " + jarPath);
			logger.info("endPoint: " + endPoint);
			logger.info("username: " + username);
			logger.info("repository: " + repository);
		} catch (Exception e) {
			logger.error("Si sono verificati problemi nel parsing dei parametri generali", e);
			responseWs.setException(e);
			return responseWs;
		}

		// Parametri specifici
		if (logger.isDebugEnabled()) {
			logger.debug("Parametri specifici: " + parCli.toString());
		}
		SftpRequest request = null;
		try {
			request = createRequest(parCli);
		} catch (Exception e) {
			logger.error("Errore in creazione richiesta", e);
			responseWs.setException(e);
			return responseWs;
		}

		logger.info("Invocazione del servizio send");
		ResponseWrapper<SftpResponse> res = new ResponseWrapper<SftpResponse>();

		try {
			res = runWsCore(request, responseWs);
			res.setResponseWs(responseWs);
		} catch (SftpException sftpException) {
			logger.error("SftpException: ", sftpException);
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

	// Metodo chiamato da exposedRun
	public static ResponseWrapper<SftpResponse> exposedRun(AppOpePar parametri, Logger loggerExt, String localPath, String remotePath, String remoteFile, boolean removeAfter, String hostKeyAlias,
			String identityPassword, String identityPath, YesNo trustHost, String knownHostsPath, String sftpHost, String sftpPassword, int sftpPort)
			throws KeyManagementException, NoSuchAlgorithmException, JSchException, IOException, SftpException {
		SftpRequest request = new SftpRequest();
		request.setLocalPath(localPath);
		request.setRemotePath(remotePath);
		request.setRemoteFile(remoteFile);
		request.setRemoveAfter(removeAfter);
		SftpParameters sftpParameters = new SftpParameters();
		sftpParameters.setHostKeyAlias(hostKeyAlias);
		sftpParameters.setIdentityPassword(identityPassword);
		sftpParameters.setIdentityPath(identityPath);
		sftpParameters.setKnownHostsPath(knownHostsPath);
		sftpParameters.setSftpHost(sftpHost);
		sftpParameters.setTrustHost(trustHost);
		sftpParameters.setSftpPassword(sftpPassword);
		sftpParameters.setSftpPort(sftpPort);
		request.setSftpParameters(sftpParameters);
		logger = loggerExt == null ? Logger.getLogger(SendManager.class) : loggerExt;

		GenericWsManager.setupGeneric(parametri, logger);
		logger.info(new KeyValueLog("Dati di input", request.toString()));
		return runWsCore(request, null);
	}

	/**
	 * @deprecated
	 * @param parametri
	 * @param loggerExt
	 * @param localPath
	 * @param remotePath
	 * @param removeAfter
	 * @param hostKeyAlias
	 * @param identityPassword
	 * @param identityPath
	 * @param knownHostsPath
	 * @param sftpHost
	 * @param sftpPassword
	 * @param sftpPort
	 * @return
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws JSchException
	 * @throws IOException
	 * @throws SftpException
	 */
	@Deprecated
	public static ResponseWrapper<SftpResponse> exposedRun(AppOpePar parametri, Logger loggerExt, String localPath, String remotePath, boolean removeAfter, String hostKeyAlias,
			String identityPassword, String identityPath, YesNo trustHost, String knownHostsPath, String sftpHost, String sftpPassword, int sftpPort)
			throws KeyManagementException, NoSuchAlgorithmException, JSchException, IOException, SftpException {
		return exposedRun(parametri, loggerExt, localPath, remotePath, null, removeAfter, hostKeyAlias, identityPassword, identityPath, trustHost, knownHostsPath, sftpHost, sftpPassword, sftpPort);
	}

	// Metodo chiamato da exposedRun
	private static ResponseWrapper<SftpResponse> runWsCore(SftpRequest sftpRequest, ResponseGenericClient responseWs) throws JSchException, IOException, SftpException {
		ResponseWrapper<SftpResponse> res = new ResponseWrapper<SftpResponse>();
		SftpResponse response = new SftpResponse();
		if (responseWs == null) {
			responseWs = new ResponseGenericClient();
		}
		res.setResponseWs(responseWs);
		res.setResponse(response);

		long start = System.currentTimeMillis();
		try {
			SftpParameters sftpParams = sftpRequest.getSftpParameters();
			if (logger.isInfoEnabled()) {
				logger.info(sftpRequest.toString());
			}
			SftpConnection sftp = new SftpConnection(sftpParams.getSftpHost(), sftpParams.getSftpPort());
			sftp.setSftpParameters(sftpParams);
			sftp.open();
			logger.debug("Connessione SFTP aperta.");

			try {
				String localPath = sftpRequest.getLocalPath();
				String remotePath = sftpRequest.getRemotePath();

				boolean isRemoteDir = sftp.isRemoteDir(remotePath);
				if (!isRemoteDir) {
					String message = "Remote path deve puntare ad una cartella.";
					logger.debug(message);
					responseWs.setResponse(ResponseGenericClient.RISPOSTA_KO);
					responseWs.setMessage(message);
					return res;
				}
				if (isRemoteDir && !remotePath.endsWith(FORWARD_SLASH)) {
					remotePath += FORWARD_SLASH;
				}

				for (String realLocalPath : PathReader.getFilePaths(localPath)) {
					File localFile = new File(realLocalPath);
					
					if (!localFile.exists()) {
						throw new FileNotFoundException(realLocalPath);
					}
					
					boolean isLocalDir = localFile.isDirectory();
					if (isLocalDir) {
						File[] files = localFile.listFiles();
						int i;
						int filesCopied = 0;
						for (i = 0; i < files.length; i++) {
							if (!files[i].isDirectory()) {
								FileInputStream fis = new FileInputStream(files[i]);
								String dest = remotePath + files[i].getName();
								sftp.put(fis, dest);
								filesCopied += 1;
								fis.close();
							}
						}
						if (logger.isDebugEnabled()) {
							logger.debug(filesCopied + " file copiat" + (filesCopied == 1 ? "o." : "i."));
						}
					} else {
						FileInputStream fis = new FileInputStream(localFile);
						String destFile = localFile.getName();
						if (sftpRequest.getRemoteFile() != null && !"".equals(sftpRequest.getRemoteFile())) {
							destFile = sftpRequest.getRemoteFile();
						}
						sftp.put(fis, (remotePath + destFile));
						fis.close();
						logger.debug("1 file copiato.");
					}
				}
			} finally {
				sftp.close();
				logger.debug("Connessione SFTP chiusa.");
			}
		} finally {
			long duration = System.currentTimeMillis() - start;
			logger.info("Durata servizio send: " + duration + " ms");
		}

		return res;
	}

	private static SftpRequest createRequest(JSONObject jsonSftpRequest01) {
		SftpRequest sftpRequest01 = new SftpRequest();
		if (jsonSftpRequest01.containsKey("LocalPath")) {
			sftpRequest01.setLocalPath((String) jsonSftpRequest01.get("LocalPath"));
		}
		if (jsonSftpRequest01.containsKey("RemotePath")) {
			sftpRequest01.setRemotePath((String) jsonSftpRequest01.get("RemotePath"));
		}
		if (jsonSftpRequest01.containsKey("RemoveAfter")) {
			sftpRequest01.setRemoveAfter("True".equals(jsonSftpRequest01.get("RemoveAfter")));
		}
		if (jsonSftpRequest01.containsKey("SftpParameters")) {
			JSONObject jsonSftpParameters02 = (JSONObject) jsonSftpRequest01.get("SftpParameters");
			SftpParameters sftpParameters02 = new SftpParameters();
			sftpRequest01.setSftpParameters(sftpParameters02);
			if (jsonSftpParameters02.containsKey("HostKeyAlias")) {
				sftpParameters02.setHostKeyAlias((String) jsonSftpParameters02.get("HostKeyAlias"));
			}
			if (jsonSftpParameters02.containsKey("IdentityPassword")) {
				sftpParameters02.setIdentityPassword((String) jsonSftpParameters02.get("IdentityPassword"));
			}
			if (jsonSftpParameters02.containsKey("IdentityPath")) {
				sftpParameters02.setIdentityPath((String) jsonSftpParameters02.get("IdentityPath"));
			}
			if (jsonSftpParameters02.containsKey("KnownHostsPath")) {
				sftpParameters02.setKnownHostsPath((String) jsonSftpParameters02.get("KnownHostsPath"));
			}
			if (jsonSftpParameters02.containsKey("SftpHost")) {
				sftpParameters02.setSftpHost((String) jsonSftpParameters02.get("SftpHost"));
			}
			if (jsonSftpParameters02.containsKey("SftpPassword")) {
				sftpParameters02.setSftpPassword((String) jsonSftpParameters02.get("SftpPassword"));
			}
			if (jsonSftpParameters02.containsKey("SftpPort")) {
				sftpParameters02.setSftpPort(NumericManager.parseInt(((String) jsonSftpParameters02.get("SftpPort"))));
			}
		}
		return sftpRequest01;
	}

	@SuppressWarnings("unchecked")
	private static void setResponse(SftpResponse sftpResponse01, JSONObject parCli, JSONObject jsonSftpResponse01) {
		if (sftpResponse01.getSftpInfo() != null) {
			JSONArray jsonSftpInfo02 = new JSONArray();
			jsonSftpResponse01.put("SftpInfo", jsonSftpInfo02);
			for (int iSftpInfo02 = 0; iSftpInfo02 < sftpResponse01.getSftpInfo().size(); iSftpInfo02++) {
				JSONObject jsonSftpInfo03 = new JSONObject();
				SftpInfo sftpInfo03 = sftpResponse01.getSftpInfo().get(iSftpInfo02);
				jsonSftpInfo02.add(jsonSftpInfo03);
				if (sftpInfo03.getCodice() != null && sftpInfo03.getCodice().value() != null) {
					jsonSftpInfo03.put("Codice", sftpInfo03.getCodice().value());
				}
				if (sftpInfo03.getDescrizione() != null) {
					jsonSftpInfo03.put("Descrizione", sftpInfo03.getDescrizione());
				}
			}
		}
	}
}
