package it.dmsoft.flowmanager.agent.engine.core.operations;

import java.math.BigDecimal;

import org.apache.tools.ant.taskdefs.XSLTProcess.Param;

import it.dmsoft.flowmanager.agent.engine.core.as400.CallAs400;
import it.dmsoft.flowmanager.agent.engine.core.operations.core.Operation;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.ChkObjParam;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.DelayIntegrityCheckParams;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.OperationType;
import it.dmsoft.flowmanager.agent.engine.core.utils.FlowLogUtils;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class DelayIntegrityCheck extends Operation<DelayIntegrityCheckParams>{

	private static final Logger logger = Logger.getLogger(DelayIntegrityCheck.class.getName());	
	
	@Override
	public void execute() throws Exception {
		logger.info("start execution of " + DelayIntegrityCheck.class.getName());
		logger.info("parameters: " + parameters.toString());
		FlowLogUtils.startDetail(OperationType.DLY_CHK);
		
		long delayInMillis = parameters.getDelaySecond().multiply(new BigDecimal(1000)).longValue();
		logger.info("sleep " + delayInMillis + " milliseconds");
		Thread.sleep(delayInMillis);
		
		logger.info("end execution of " + DelayIntegrityCheck.class.getName());
		FlowLogUtils.endDetail(OperationType.DLY_CHK);	
	}

}
