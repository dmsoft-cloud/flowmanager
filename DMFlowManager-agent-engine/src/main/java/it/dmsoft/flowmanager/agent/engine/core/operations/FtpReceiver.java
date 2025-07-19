package it.dmsoft.flowmanager.agent.engine.core.operations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import it.dmsoft.flowmanager.agent.engine.core.exception.OperationException;
import it.dmsoft.flowmanager.agent.engine.core.operations.core.ConstraintTrasmission;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.OperationType;
import it.dmsoft.flowmanager.agent.engine.core.utils.ExceptionUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.FlowLogUtils;
import it.dmsoft.flowmanager.agent.engine.ftp.manager.FtpReceiveManager;
import it.dmsoft.flowmanager.agent.engine.ftp.model.FtpFileDescription;
import it.dmsoft.flowmanager.agent.engine.ftp.model.FtpResponse;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.ResponseWrapper;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class FtpReceiver extends ConstraintTrasmission<ResponseWrapper<FtpResponse>> {

	private static Logger logger = Logger.getLogger(FtpReceiver.class);
	
	@Override
	public ResponseWrapper<FtpResponse> executeTrasmission() throws Exception {
		logger.info("start execution of " + FtpReceiver.class.getName());
		logger.info("parameters: " + parameters.toString());
		FlowLogUtils.startDetail(OperationType.FTP_RCV);
		
		boolean passiveMode;
		boolean ftpSecure;
		if(Constants.SI.equals(parameters.getPassive_mode())) {
			passiveMode = true;
		}else {
			passiveMode = false;
		}
		
		if(Constants.SI.equals(parameters.getFtp_secure())) {
			ftpSecure = true;
		}else {
			ftpSecure = false;
		}
		
		logger.info("loggerExt => " + logger + " localPath => " + parameters.getLocal_Folder() + "\n"
				+ " remotePath => " + parameters.getRemote_Folder() + "remoteFile =>" + parameters.getRemote_File_Name()
				+ " removeAfter => " + parameters.isRemoveRemoteFile()
				+ " ftpHost => " + parameters.getHost() + " ftpUser => " + parameters.getUser() + " ftpPassword => "
				+ parameters.getPassword() + " ftpPort => " + parameters.getPort().intValue());
		
		ResponseWrapper<FtpResponse> resp = null;
		
		try {
			resp = FtpReceiveManager.exposedRun(null, Logger.getLogger(FtpReceiver.class),
					parameters.getLocal_Folder() + Constants.PATH_DELIMITER + parameters.getLocal_File_Name(),
					parameters.getRemote_Folder() , parameters.getRemote_File_Name(), parameters.isRemoveRemoteFile(),
					parameters.getHost(), parameters.getUser(), parameters.getPassword(),
					parameters.getPort().intValue(), passiveMode, ftpSecure);
			
			List<FtpFileDescription> files = resp.getResponse().getDowloadedFiles();
			
			ExceptionUtils.throwExceptionIfNecessary(resp);
			launchExceptionIfNoFileFound(files);
			
		} catch (IOException ioe) {
			//CHECK IF ERROR IS DUE TO FILKE MISSING ON SERVER
			logger.info("Exception on FftpManager execution: " + ioe.getMessage());
			logger.error("Exception error on FftpManager execution: ", ioe);
			
			if (Constants.FTP_FILE_NOT_FOUND.equals(ioe.getMessage())) {
				if (parameters.isLaunchErrorIfNoFileFound()) {				
					launchNoSuchFileException();
				} else {
					logger.info("Exception suppressed due to file not found on server bypass");					
				}
			} else {
				throw ioe;
			}
		} catch (OperationException e1) { 
			throw e1;
		} catch (Throwable e) {		
			throw new Exception("Error on ftp receiver invocation: ", e);
		}
		
		FlowLogUtils.endDetail(OperationType.FTP_RCV);
		logger.info("end execution of " + FtpReceiver.class.getName());

		return resp;
	}

	@Override
	public void updateOperationParams(ResponseWrapper<FtpResponse> data) throws Exception {
		logger.info("Retrieving downloaded file list");
		
		if (data == null) {
			logger.info("Data is null");
			operationParams.setTrasmissionFiles(new ArrayList<String>());
			return;
		}
		
		List<String> trasmissionFiles = new ArrayList<String>(data.getResponse().getDowloadedFiles().size());
		
		for (FtpFileDescription sftipFileDesc : data.getResponse().getDowloadedFiles()) {
			String fileName = sftipFileDesc.getFilePath() + Constants.PATH_DELIMITER + sftipFileDesc.getFileName();
			logger.info("Downloaded ->" + fileName);
			trasmissionFiles.add(fileName);
		}
		
		operationParams.setTrasmissionFiles(trasmissionFiles);
		
	}

	

}
