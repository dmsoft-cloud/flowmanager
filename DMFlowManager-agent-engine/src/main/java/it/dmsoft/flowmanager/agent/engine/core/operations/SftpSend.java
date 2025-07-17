package it.dmsoft.flowmanager.agent.engine.core.operations;

import it.dmsoft.flowmanager.agent.engine.core.exception.OperationException;
import it.dmsoft.flowmanager.agent.engine.core.operations.core.Trasmission;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.OperationType;
import it.dmsoft.flowmanager.agent.engine.core.utils.ExceptionUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.LogDb;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.ResponseWrapper;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;
import it.dmsoft.flowmanager.agent.engine.sftp.manager.SendManager;
import it.dmsoft.flowmanager.agent.engine.sftp.model.SftpResponse;

public class SftpSend extends Trasmission {

	private static Logger logger = Logger.getLogger(SftpSend.class);

	@Override
	public void executeTrasmission() throws Exception {
		logger.info("start execution of " + SftpSend.class.getName());
		logger.info("parameters: " + parameters.toString());
		LogDb.start(OperationType.SFTP_SND);
		
		// System.out.println(parameters);
		// parameters.setKeyFile("ocssftp -i
		// /qsys.lib/ocsdati.lib/oxtsfidrsa.file/id_rsa.mbr");

		logger.info("loggerExt => " + logger + "\n" + " localPath => " + parameters.getLocal_Folder() + "/"
				+ parameters.getLocal_File_Name() + "\n" + " remotePath => " + parameters.getRemote_Folder() + "\n"
				+ " remoteFileName => " + parameters.getRemote_File_Name() + "\n"
				+ " removeAfter => " + false + "\n" + " hostKeyAlias  => " + parameters.getHostKeyAlias() + "" + "\n" + " identityPassword =>"
				+ "null" + "\n" + " identityPath =>" + parameters.getKeyFile() + "\n" + " knownHostsPath => "
				+ parameters.getKnown_Hosts_File() + "\n" + " sftpHost => " + parameters.getHost() + "\n"
				+ " sftpPassword => " + parameters.getPassword() + "\n" + " sftpPort => "
				+ parameters.getPort().intValue() + "\n");

		try {
			ResponseWrapper<SftpResponse> resp = SendManager.exposedRun(null, Logger.getLogger(SftpSend.class),
					parameters.getLocal_Folder() + '/' + parameters.getLocal_File_Name(), parameters.getRemote_Folder(), parameters.getRemote_File_Name(),
					false, parameters.getHostKeyAlias(), null, parameters.getKeyFile(), parameters.getKnown_Hosts_File(),
					parameters.getHost(), parameters.getPassword(), parameters.getPort().intValue());
			
			ExceptionUtils.throwExceptionIfNecessary(resp);
		} catch (OperationException e1) {
			throw e1;
		} catch (Throwable e) {
			throw new Exception("Error on sftp invocation: ", e);
		}
		
		logger.info("end execution of " + SftpSend.class.getName());
		LogDb.end(OperationType.SFTP_SND);

	}

	
}
