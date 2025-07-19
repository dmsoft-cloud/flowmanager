package it.dmsoft.flowmanager.agent.engine.core.operations;

import java.util.List;

import it.dmsoft.flowmanager.agent.engine.core.as400.CallAs400;
import it.dmsoft.flowmanager.agent.engine.core.operations.core.DependentOperation;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.CopyFileParam;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.FlowLogUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.StringUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.OperationType;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

//CPYF FROMFILE(B2BDATI/CCCOMUNI) TOFILE(OCSOAM/PROVA1) MBROPT(*ADD) 

public class CopyFile extends DependentOperation<CopyFileParam>{

	private static final Logger logger = Logger.getLogger(CopyFile.class.getName());
	
	@Override
	public void updateParameters() throws Exception {
		
			logger.info("Parametri COPYFILE operation -->" + getOperationParams().toString());
		
	}
	
	@Override
	public void executeOperation() throws Exception {

		logger.info("start execution of " + CopyFile.class.getName());
		logger.info("parameters: " + parameters.toString());
		
		if(operationParams.isBypassConversion()) {
			logger.info("skip CopyFile step - BYPASS CONVERTION ACTIVE AND FILE EMPTY");
			return;
		}
		
		FlowLogUtils.startDetail(OperationType.COPY_FILE);
		
		CallAs400 callAs400 = CallAs400.get(parameters);
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("CPYF");

		sb.append(Constants.SPACE + "FROMFILE");
		sb.append(Constants.OPEN_BRACKET);
		sb.append(parameters.getFromLibrary() + Constants.PATH_DELIMITER + parameters.getFromFile());
		sb.append(Constants.CLOSE_BRACKET);
		
		
		sb.append(Constants.SPACE + "TOFILE");
		sb.append(Constants.OPEN_BRACKET);
		sb.append(parameters.getToLibrary() + Constants.PATH_DELIMITER + parameters.getToFile());
		sb.append(Constants.CLOSE_BRACKET);
		
		sb.append(Constants.SPACE + "MBROPT");
		sb.append(Constants.OPEN_BRACKET);
		sb.append(parameters.getMbrOpt());
		sb.append(Constants.CLOSE_BRACKET);
		
		if (!StringUtils.isNullOrEmpty(parameters.getFormatOption())) {
			sb.append(Constants.SPACE + "FMTOPT");
			sb.append(Constants.OPEN_BRACKET);
			sb.append(parameters.getFormatOption());
			sb.append(Constants.CLOSE_BRACKET);
		}
		
		logger.debug("Command CopyFile :  " + sb.toString());
		
		
		callAs400.commandCall(sb.toString());
		
		logger.info("end execution of " + CopyFile.class.getName());
		FlowLogUtils.endDetail(OperationType.COPY_FILE);
		
	}
}
