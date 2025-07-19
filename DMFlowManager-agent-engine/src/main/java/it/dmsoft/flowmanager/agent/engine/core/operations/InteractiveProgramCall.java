package it.dmsoft.flowmanager.agent.engine.core.operations;

import com.ibm.as400.access.AS400Text;
import com.ibm.as400.access.ProgramCall;
import com.ibm.as400.access.ProgramParameter;

import it.dmsoft.flowmanager.agent.engine.core.as400.CallAs400;
import it.dmsoft.flowmanager.agent.engine.core.operations.core.Operation;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.InteractiveProgramCallParam;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.OperationType;
import it.dmsoft.flowmanager.agent.engine.core.utils.FlowLogUtils;

import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

 

public class InteractiveProgramCall extends Operation<InteractiveProgramCallParam>{

	private static final Logger logger = Logger.getLogger(InteractiveProgramCall.class.getName());
	
	@Override
	public void execute() throws Exception {
		logger.info("start execution of " + InteractiveProgramCall.class.getName());
		logger.info("parameters: " + parameters.toString());
		FlowLogUtils.startDetail(OperationType.INT_PGM);
		
		CallAs400 callAs400 = CallAs400.get(parameters);
		//istanzio la programcall
		//ProgramCall spgm = new ProgramCall(callAs400.getAs400());
		
		//dichiaro i formattatori
		AS400Text char2Converter = new AS400Text(2);
		ProgramParameter[] params;
						
		//istanzio la programcall
		if (parameters.getResult().equals(Constants.SI)) {
			params=new ProgramParameter[1];
			params[0]  = new ProgramParameter(2);
		} else {
			params=new ProgramParameter[0];
		}
		logger.debug("Program interactive:  " + parameters.getPgm());
		//spgm.setProgram(parameters.getPgm().trim(), params);
	    //boolean esito = spgm.run();

		
		callAs400.programCall(parameters.getPgm().trim(), params);
		if (parameters.getResult().equals(Constants.SI) && !char2Converter.toObject(params[params.length-1].getOutputData()).toString().equals(Constants.OK)) { 
			logger.info("esito programma interattivo: " +  char2Converter.toObject(params[params.length-1].getOutputData()).toString()) ;
			throw new Exception("Program call throw an error");
		}
		logger.info("end execution of " + InteractiveProgramCall.class.getName());
		FlowLogUtils.endDetail(OperationType.INT_PGM);
		
	}
}
