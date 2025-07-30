package it.dmsoft.flowmanager.agent.engine.core.operations;

import it.dmsoft.flowmanager.agent.engine.core.exception.OperationException;
import it.dmsoft.flowmanager.agent.engine.core.operations.core.Trasmission;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.OperationType;
import it.dmsoft.flowmanager.agent.engine.core.utils.ExceptionUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.FlowLogUtils;
import it.dmsoft.flowmanager.agent.engine.ftp.manager.FtpSendManager;
import it.dmsoft.flowmanager.agent.engine.ftp.model.FtpResponse;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.ResponseWrapper;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;
import it.dmsoft.flowmanager.common.domain.Domains.YesNo;

public class FtpSend extends Trasmission {

	private static Logger logger = Logger.getLogger(FtpSend.class);
	
	@Override
	public void executeTrasmission() throws Exception {
		logger.info("start execution of " + FtpSend.class.getName());
		logger.info("parameters: " + parameters.toString());
		FlowLogUtils.startDetail(OperationType.FTP_SND);
		
		boolean passiveMode;
		boolean ftpSecure;
		if(YesNo.YES.equals(parameters.getPassive_mode())) {
			passiveMode = true;
		}else {
			passiveMode = false;
		}
		
		if(YesNo.YES.equals(parameters.getFtp_secure())) {
			ftpSecure = true;
		}else {
			ftpSecure = false;
		}
		
		logger.info("loggerExt => " + logger + " localPath => " + parameters.getLocal_Folder() + "/"
				+ parameters.getLocal_File_Name() + "\n" + " remotePath => " + parameters.getRemote_Folder() 
				+ " remoteFile => " +  parameters.getRemote_File_Name()
				+ "\n" + " removeAfter => " + false
				+ " ftpHost => " + parameters.getHost() + " ftpUser => " + parameters.getUser() + " ftpPassword => "
				+ parameters.getPassword() + " ftpPort => " + parameters.getPort().intValue());
		
		try {
			ResponseWrapper<FtpResponse> resp = FtpSendManager.exposedRun(null, Logger.getLogger(FtpSend.class),
					// null,
					parameters.getLocal_Folder() + Constants.PATH_DELIMITER + parameters.getLocal_File_Name(),
					parameters.getRemote_Folder() , parameters.getRemote_File_Name(), false,
					// parameters.getUser() + "@" + parameters.getHost(),
					parameters.getHost(), parameters.getUser(), parameters.getPassword(),
					parameters.getPort().intValue(), passiveMode, ftpSecure);
			
			ExceptionUtils.throwExceptionIfNecessary(resp);
		} catch (OperationException e1) { 
			throw e1;
		} catch (Throwable e) {		
			throw new Exception("Error on ftp invocation: ", e);
		}

		logger.info("end execution of " + FtpSend.class.getName());
		FlowLogUtils.endDetail(OperationType.FTP_SND);
		
	}

	

}
