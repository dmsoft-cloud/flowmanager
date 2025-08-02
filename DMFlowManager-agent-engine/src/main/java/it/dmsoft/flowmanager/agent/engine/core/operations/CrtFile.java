package it.dmsoft.flowmanager.agent.engine.core.operations;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

import it.dmsoft.flowmanager.agent.engine.core.as400.CallAs400;
import it.dmsoft.flowmanager.agent.engine.core.operations.core.Operation;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.CreateFileParam;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.OperationType;
import it.dmsoft.flowmanager.agent.engine.core.utils.FlowLogUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.StringUtils;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;
import it.dmsoft.flowmanager.common.domain.Domains.YesNo;

//QSH CMD('touch <fana_folder>/<fana_filenamesemaforo>')

public class CrtFile extends Operation<CreateFileParam>{
	
	private static final Logger logger = Logger.getLogger(CrtFile.class.getName());
	
	@Override
	public void execute() throws Exception {
		
		logger.info("start execution of " + CrtFile.class.getName());
		logger.info("parameters: " + parameters.toString());
		FlowLogUtils.startDetail(OperationType.CREATE_FL);
		
		//verifico valore db2
		//DBTypeEnum dbTypeEnum = DBTypeEnum.get(parameters.getDbType()); 
		
		//per ora mantengo la creazione con il qsh solo nel caso di esecuzione locale su as400
		if (YesNo.YES.equals(parameters.isIBMi()) && StringUtils.isNullOrEmpty(parameters.getHost())) {
			CallAs400 callAs400 = CallAs400.get(parameters);
			
			StringBuilder sb = new StringBuilder();
			
			sb.append("QSH");
			sb.append(Constants.SPACE + "CMD");
			sb.append(Constants.OPEN_BRACKET);
			sb.append(Constants.SINGLE_QUOTATION_MARK);
			sb.append("touch");
			sb.append(Constants.SPACE + parameters.getFolder().trim() + Constants.PATH_DELIMITER + parameters.getFileName().trim());
			sb.append(Constants.SINGLE_QUOTATION_MARK);
			sb.append(Constants.CLOSE_BRACKET);
			
			logger.debug("Command Create File :  " + sb.toString());
		
			callAs400.commandCall(sb.toString());
		} else {
			Path filePath = Paths.get(parameters.getFolder().trim() + Constants.PATH_DELIMITER + parameters.getFileName().trim());
			 if (!Files.exists(filePath)) {
	                Files.createFile(filePath);
	                logger.debug("Command Create File :  " +  parameters.getFolder().trim() + Constants.PATH_DELIMITER + parameters.getFileName().trim() );
	         } else {
	            	logger.debug("the file already exist.");
	         }
			 try {
				 Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rwxrwxrwx");
				 Files.setPosixFilePermissions(filePath, perms);
			 } catch(Exception e) {
				 logger.debug("posix grant not supported");
			 }
		}
		
		
		logger.info("end execution of " + CrtFile.class.getName());
		FlowLogUtils.endDetail(OperationType.CREATE_FL);
	}

}
