package it.dmsoft.flowmanager.agent.engine.core.operations;

import java.math.BigDecimal;

import it.dmsoft.flowmanager.agent.engine.core.as400.CallAs400;
import it.dmsoft.flowmanager.agent.engine.core.operations.core.Operation;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.CreateDbFileParam;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.LogDb;
import it.dmsoft.flowmanager.agent.engine.core.utils.StringUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.OperationType;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

//CRTPF FILE(OCSOAM/TEST2) SRCFILE(OCSSY401/QPFSRC) SRCMBR(CCCOMUNI)

public class CrtDbFile extends Operation<CreateDbFileParam>{
	
	private static final Logger logger = Logger.getLogger(CrtDbFile.class.getName());
	
	@Override
	public void execute() throws Exception {
		
		logger.info("start execution of " + CrtDbFile.class.getName());
		logger.info("parameters: " + parameters.toString());
		LogDb.start(OperationType.CREATE_DB);
				
		CallAs400 callAs400 = CallAs400.get(parameters);
		
		StringBuilder sb = new StringBuilder();
		
		
		
		sb.append("CRTPF");
		sb.append(Constants.SPACE + "FILE");
		sb.append(Constants.OPEN_BRACKET);
		sb.append(parameters.getLibreria() + Constants.PATH_DELIMITER + parameters.getFile());
		sb.append(Constants.CLOSE_BRACKET);
		
		if (!StringUtils.isNullOrEmpty(parameters.getSrcFile())) {
			sb.append(Constants.SPACE + "SRCFILE");
			sb.append(Constants.OPEN_BRACKET);
			sb.append(parameters.getSrcLibreria() + Constants.PATH_DELIMITER + parameters.getSrcFile());
			sb.append(Constants.CLOSE_BRACKET);
		}

		if (!StringUtils.isNullOrEmpty(parameters.getSrcMembro())) {
			sb.append(Constants.SPACE + "SRCMBR");
			sb.append(Constants.OPEN_BRACKET);
			sb.append( parameters.getSrcMembro());
			sb.append(Constants.CLOSE_BRACKET);
		}
		
		if (parameters.getRecordLength() != null && BigDecimal.ZERO.compareTo(parameters.getRecordLength()) != 0) {
			sb.append(Constants.SPACE + "RCDLEN");
			sb.append(Constants.OPEN_BRACKET);
			sb.append( parameters.getRecordLength().toString());
			sb.append(Constants.CLOSE_BRACKET);
		}
		
		//size *noMax
		sb.append(Constants.SPACE + "SIZE");
		sb.append(Constants.OPEN_BRACKET);
		sb.append("*NOMAX");
		sb.append(Constants.CLOSE_BRACKET);
		
		logger.debug("Command CrtDbFile :  " + sb.toString());
	
		callAs400.commandCall(sb.toString());
		
		logger.info("end execution of " + CrtDbFile.class.getName());
		LogDb.end(OperationType.CREATE_DB);
	}

}
