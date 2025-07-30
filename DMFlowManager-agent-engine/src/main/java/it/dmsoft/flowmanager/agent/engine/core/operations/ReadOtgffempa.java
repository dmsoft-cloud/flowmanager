package it.dmsoft.flowmanager.agent.engine.core.operations;

import java.math.BigDecimal;

import com.ibm.as400.access.AS400Text;
import com.ibm.as400.access.AS400ZonedDecimal;
import com.ibm.as400.access.ProgramParameter;

import it.dmsoft.flowmanager.agent.engine.core.as400.CallAs400;
import it.dmsoft.flowmanager.agent.engine.core.operations.core.Operation;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.SendMailParam;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.OperationType;
import it.dmsoft.flowmanager.agent.engine.core.utils.FlowLogUtils;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;
import it.dmsoft.flowmanager.common.domain.Domains.YesNo;

public class ReadOtgffempa extends Operation<SendMailParam> {

	private static Logger logger = Logger.getLogger(ReadOtgffempa.class);

	@Override
	public void execute() throws Exception {

		logger.info("start execution of " + ReadOtgffempa.class.getName());
		logger.info("parameters: " + parameters.toString());
		FlowLogUtils.startDetail(OperationType.READ_MAIL);
		
		CallAs400 callAs400 = CallAs400.get(parameters);

		ProgramParameter[] parmlist = new ProgramParameter[22];

		//AS400Bin4 bin4 = new AS400Bin4();
		AS400ZonedDecimal dec = new AS400ZonedDecimal(5, 0);

		AS400Text char256Converter = new AS400Text(256);
		AS400Text char50Converter = new AS400Text(50);
		//AS400Text char40Converter = new AS400Text(40);
		//AS400Text char20Converter = new AS400Text(20);
		//AS400Text char10Converter = new AS400Text(10);
		AS400Text char8Converter = new AS400Text(8);
		AS400Text char1Converter = new AS400Text(1);
		//AS400Text char5Converter = new AS400Text(5);

		//AS400Bin8 bin8 = new AS400Bin8();

		parmlist[0] = new ProgramParameter(char50Converter.toBytes(parameters.getFrom()));
		parmlist[1] = new ProgramParameter(8);
		parmlist[2] = new ProgramParameter(40);
		parmlist[3] = new ProgramParameter(50);
		parmlist[4] = new ProgramParameter(256);
		parmlist[5] = new ProgramParameter(5);
		parmlist[6] = new ProgramParameter(50);
		parmlist[7] = new ProgramParameter(50);
		parmlist[8] = new ProgramParameter(1);
		parmlist[9] = new ProgramParameter(10);
		parmlist[10] = new ProgramParameter(1);
		parmlist[11] = new ProgramParameter(256);
		parmlist[12] = new ProgramParameter(256);
		parmlist[13] = new ProgramParameter(256);
		parmlist[14] = new ProgramParameter(1);
		parmlist[15] = new ProgramParameter(256);
		parmlist[16] = new ProgramParameter(5);
		parmlist[17] = new ProgramParameter(50);
		parmlist[18] = new ProgramParameter(50);
		parmlist[19] = new ProgramParameter(10);
		parmlist[20] = new ProgramParameter(1);
		parmlist[21] = new ProgramParameter(256);

		callAs400.programCall("/QSYS.LIB/" + parameters.getPgmLibrary() + ".LIB/OTGFPEMPA.PGM", parmlist);

		parameters.setSmtpUsername(char50Converter.toObject(parmlist[6].getOutputData()).toString());
		parameters.setSmtpPassword(char8Converter.toObject(parmlist[7].getOutputData()).toString());
		parameters.setPort(new BigDecimal(dec.toObject(parmlist[5].getOutputData()).toString()));
		parameters.setHostName(char256Converter.toObject(parmlist[4].getOutputData()).toString().trim());
		Boolean secure = YesNo.YES.getCode().equals(char1Converter.toObject(parmlist[8].getOutputData()).toString());
		parameters.setSecure(secure);

		logger.info("end execution of " + ReadOtgffempa.class.getName());
		FlowLogUtils.endDetail(OperationType.READ_MAIL);
	}

}
