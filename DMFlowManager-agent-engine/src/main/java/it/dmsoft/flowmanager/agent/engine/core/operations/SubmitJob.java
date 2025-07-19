package it.dmsoft.flowmanager.agent.engine.core.operations;

import it.dmsoft.flowmanager.agent.engine.core.as400.CallAs400;
import it.dmsoft.flowmanager.agent.engine.core.operations.core.Operation;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.SubmitJobParam;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.OperationType;
import it.dmsoft.flowmanager.agent.engine.core.utils.FlowLogUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.StringUtils;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

//CPYF FROMFILE(B2BDATI/CCCOMUNI) TOFILE(OCSOAM/PROVA1) MBROPT(*ADD) 

public class SubmitJob extends Operation<SubmitJobParam>{

	private static final Logger logger = Logger.getLogger(SubmitJob.class.getName());
	
	@Override
	public void execute() throws Exception {
		logger.info("start execution of " + SubmitJob.class.getName());
		logger.info("parameters: " + parameters.toString());
		FlowLogUtils.startDetail(OperationType.SBM_JOB);
		
		CallAs400 callAs400 = CallAs400.get(parameters);
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SBMJOB");

		sb.append(Constants.SPACE + "CMD");
		sb.append(Constants.OPEN_BRACKET);
		sb.append(parameters.getCommand());
		sb.append(Constants.CLOSE_BRACKET);
		
		
		if (!StringUtils.isNullOrEmpty(parameters.getJobd())) {
			String library = StringUtils.isNullOrEmpty(parameters.getJobdLibrary()) ? "" : parameters.getJobdLibrary() + Constants.PATH_DELIMITER;
			sb.append(Constants.SPACE + "JOBD");
			sb.append(Constants.OPEN_BRACKET);
			sb.append(library + parameters.getJobd());
			sb.append(Constants.CLOSE_BRACKET);
		}
			
		logger.debug("Command SubmitJob:  " + sb.toString());		
		
		callAs400.commandCall(sb.toString());
		
		logger.info("end execution of " + SubmitJob.class.getName());
		FlowLogUtils.endDetail(OperationType.SBM_JOB);
		
	}
}
