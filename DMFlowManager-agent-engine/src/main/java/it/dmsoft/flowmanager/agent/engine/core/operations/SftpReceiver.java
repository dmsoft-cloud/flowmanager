package it.dmsoft.flowmanager.agent.engine.core.operations;

import java.util.ArrayList;
import java.util.List;

import it.dmsoft.flowmanager.agent.engine.core.exception.OperationException;
import it.dmsoft.flowmanager.agent.engine.core.operations.core.ConstraintTrasmission;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.OperationType;
import it.dmsoft.flowmanager.agent.engine.core.utils.ExceptionUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.LogDb;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.ResponseWrapper;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;
import it.dmsoft.flowmanager.agent.engine.sftp.manager.ReceiveManager;
import it.dmsoft.flowmanager.agent.engine.sftp.model.SftpFileDescription;
import it.dmsoft.flowmanager.agent.engine.sftp.model.SftpResponse;

public class SftpReceiver extends ConstraintTrasmission<ResponseWrapper<SftpResponse>> {

	private static Logger logger = Logger.getLogger(SftpReceiver.class);
	

	@Override
	public ResponseWrapper<SftpResponse> executeTrasmission() throws Exception {
		logger.info("start execution of " + SftpReceiver.class.getName());
		logger.info("parameters: " + parameters.toString());
		LogDb.start(OperationType.SFTP_RCV);
		
		logger.info("loggerExt => " + logger + "\n" + " localPath => " + parameters.getLocal_Folder() + "/"
				+ parameters.getLocal_File_Name() + "\n" + " remotePath => " + parameters.getRemote_Folder() + "\n"
				+ " remoteFileName => " + parameters.getRemote_File_Name() + "\n"
				+ " removeAfter => " + parameters.isRemoveRemoteFile() + "\n" + " hostKeyAlias  => " + parameters.getHostKeyAlias() +  "" + "\n" + " identityPassword =>"
				+ "null" + "\n" + " identityPath =>" + parameters.getKeyFile() + "\n" + " knownHostsPath => "
				+ parameters.getKnown_Hosts_File() + "\n" + " sftpHost => " + parameters.getHost() + "\n"
				+ " sftpPassword => " + parameters.getPassword() + "\n" + " sftpPort => "
				+ parameters.getPort().intValue() + "\n");

		ResponseWrapper<SftpResponse> resp = null;
		
		try {
			resp = ReceiveManager.exposedRun(null, Logger.getLogger(SftpReceiver.class),
					parameters.getLocal_Folder() + Constants.PATH_DELIMITER + parameters.getLocal_File_Name(), parameters.getRemote_Folder(), parameters.getRemote_File_Name(),
					parameters.isRemoveRemoteFile(), parameters.getHostKeyAlias(), null, parameters.getKeyFile(), parameters.getKnown_Hosts_File(),
					parameters.getHost(), parameters.getPassword(), parameters.getPort().intValue());
			
			logger.info("response SftpReciever: " + resp.toString());
			
			ExceptionUtils.throwExceptionIfNecessary(resp);
			
			launchExceptionIfNoFileFound(resp.getResponse().getDowloadedFiles());
		
		} catch (OperationException e1) {
			throw e1;
		} catch (Exception e) {
			//CHECK IF ERROR IS DUE TO FILKE MISSING ON SERVER
			logger.info("Exception on SftpManager execution: " + e.getMessage());
			logger.error("Exception error on SftpManager execution: ", e);
			
			if (Constants.SFTP_FILE_NOT_FOUND.equals(e.getMessage())) {
				if (parameters.isLaunchErrorIfNoFileFound()) {				
					launchNoSuchFileException();
				} else {
					logger.info("Exception suppressed due to file not found on server bypass");					
				}
			} else {
				throw e;
			}			
		
		} catch (Throwable e2) {
			throw new Exception("Error on sftp receiver invocation: ", e2);
		}

		logger.info("end execution of " + SftpReceiver.class.getName());
		LogDb.end(OperationType.SFTP_RCV);
		
		return resp;
	}


	@Override
	public void updateOperationParams(ResponseWrapper<SftpResponse> data) throws Exception {
		logger.info("Retrieving downloaded file list");
		
		if (data == null) {
			logger.info("Data is null");
			operationParams.setTrasmissionFiles(new ArrayList<String>());
			return;
		}
		
		List<String> trasmissionFiles = new ArrayList<String>(data.getResponse().getDowloadedFiles().size());
		
		for (SftpFileDescription sftipFileDesc : data.getResponse().getDowloadedFiles()) {
			String fileName = sftipFileDesc.getFilePath() + Constants.PATH_DELIMITER + sftipFileDesc.getFileName();
			logger.info("Downloaded ->" + fileName);
			trasmissionFiles.add(fileName);
		}
		
		operationParams.setTrasmissionFiles(trasmissionFiles);
		
	}

	
}
