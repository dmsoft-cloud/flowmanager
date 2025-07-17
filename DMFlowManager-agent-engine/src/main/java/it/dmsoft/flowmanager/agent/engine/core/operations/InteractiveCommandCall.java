package it.dmsoft.flowmanager.agent.engine.core.operations;

import it.dmsoft.flowmanager.agent.engine.core.as400.CallAs400;
import it.dmsoft.flowmanager.agent.engine.core.operations.core.Operation;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.InteractiveCommandCallParam;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.SubmitJobParam;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.OperationType;
import it.dmsoft.flowmanager.agent.engine.core.utils.LogDb;
import it.dmsoft.flowmanager.agent.engine.core.utils.StringUtils;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

//CPYF FROMFILE(B2BDATI/CCCOMUNI) TOFILE(OCSOAM/PROVA1) MBROPT(*ADD) 

public class InteractiveCommandCall extends Operation<InteractiveCommandCallParam>{

	private static final Logger logger = Logger.getLogger(InteractiveCommandCall.class.getName());
	
	@Override
	public void execute() throws Exception {
		logger.info("start execution of " + InteractiveCommandCall.class.getName());
		logger.info("parameters: " + parameters.toString());
		LogDb.start(OperationType.INT_CMD);
		
		CallAs400 callAs400 = CallAs400.get(parameters);
	
		logger.debug("Command interactive:  " + parameters.getCommand());		
		
		callAs400.commandCall(parameters.getCommand());
		
		logger.info("end execution of " + InteractiveCommandCall.class.getName());
		LogDb.end(OperationType.INT_CMD);
		
	}
}
