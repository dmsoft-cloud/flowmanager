package it.dmsoft.flowmanager.agent.engine.core.operations;

import com.ibm.as400.access.AS400Text;
import com.ibm.as400.access.ProgramParameter;

import it.dmsoft.flowmanager.agent.engine.core.as400.CallAs400;
import it.dmsoft.flowmanager.agent.engine.core.operations.core.Operation;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.CftFileParam;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.SpFileParam;
import it.dmsoft.flowmanager.agent.engine.core.properties.PropertiesUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.OperationType;
import it.dmsoft.flowmanager.agent.engine.core.utils.FlowLogUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.StringUtils;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class CftFileSnd extends Operation<CftFileParam> {

	private static final Logger logger = Logger.getLogger(CftFileSnd.class.getName());

	@Override
	public void execute() throws Exception {

		logger.info("start execution of " + CftFileSnd.class.getName());
		logger.info("parameters: " + parameters.toString());
		FlowLogUtils.startDetail(OperationType.CFT_FL_SND);

		CallAs400 callAs400 = CallAs400.get(parameters);
		
		ProgramParameter[] parmlist = new ProgramParameter[6];
		AS400Text char256Converter = new AS400Text(256);
		AS400Text char50Converter = new AS400Text(50);
		AS400Text char32Converter = new AS400Text(32);
		AS400Text char10Converter = new AS400Text(10);
		AS400Text char2Converter = new AS400Text(2);
		
        // Converti il BigDecimal in stringa e rimuovi eventuali punti decimali e segni
        String tid = parameters.getTransactionId().stripTrailingZeros().toPlainString();
        tid = tid.replace("-", "").replace(".", "");
        
        // Se la stringa è più lunga di 7 caratteri, prendi solo gli ultimi 7
        if (tid.length() > 7) {
        	tid = tid.substring(tid.length() - 7);
        }
		
		String msgq = "CFT" + tid ;
		logger.info("nome msgq:  " + msgq);
		
		//String parm = parameters.getParm().equals("*NONE") ? msgq : msgq + ";" + parameters.getParm();
		String parm = StringUtils.isNullOrEmpty(parameters.getParm()) ? "*NONE" : parameters.getParm();
		
		
		parmlist[0] = new ProgramParameter(char32Converter.toBytes(parameters.getPartner()));
		parmlist[1] = new ProgramParameter(char32Converter.toBytes(parameters.getIdf()));
		parmlist[2] = new ProgramParameter(char50Converter.toBytes(parameters.getfName()));
		parmlist[3] = new ProgramParameter(char256Converter.toBytes(parm));
		parmlist[4] = new ProgramParameter(char10Converter.toBytes(msgq));
		parmlist[5] = new ProgramParameter(2);
		logger.info("Parametri call programma " + parmlist[0] + " " + parmlist[1] + " " + parmlist[2] + " " + parmlist[3] + " " + parmlist[4]);
		if (!StringUtils.isNullOrEmpty(PropertiesUtils.get(Constants.CFT_CL_PGM))) {
			//parametro 
			logger.info("chiamata programma interattivo CFT: " + PropertiesUtils.get(Constants.CFT_CL_PGM) ) ;
			callAs400.programCall(PropertiesUtils.get(Constants.CFT_CL_PGM), parmlist);
		} else callAs400.programCall("/QSYS.LIB/" + PropertiesUtils.get(Constants.PGM_LIBRARY_KEY) + ".LIB/" + "OMCCFTSND.PGM", parmlist);

		if (!char2Converter.toObject(parmlist[parmlist.length-1].getOutputData()).toString().equals(Constants.OK)) { 
			logger.info("esito programma interattivo: " +  char2Converter.toObject(parmlist[parmlist.length-1].getOutputData()).toString()) ;
			throw new Exception("Program call throw an error");
		}
		
		
		/*StringBuilder sb = new StringBuilder();

		sb.append("EXCFTSEND");

		if (!StringUtils.isNullOrEmpty(parameters.getPartner())) {
			sb.append(Constants.SPACE + "PART");
			sb.append(Constants.OPEN_BRACKET);
			sb.append(parameters.getPartner());
			sb.append(Constants.CLOSE_BRACKET);
		}

		if (!StringUtils.isNullOrEmpty(parameters.getIdf())) {
			sb.append(Constants.SPACE + "IDF");
			sb.append(Constants.OPEN_BRACKET);
			sb.append(parameters.getIdf());
			sb.append(Constants.CLOSE_BRACKET);
		}

		if (!StringUtils.isNullOrEmpty(parameters.getfName())) {
			sb.append(Constants.SPACE + "FNAME");
			sb.append(Constants.OPEN_BRACKET);
			sb.append(parameters.getfName());
			sb.append(Constants.CLOSE_BRACKET);
		}
		
		if (!StringUtils.isNullOrEmpty(parameters.getParm())) {
			sb.append(Constants.SPACE + "PARM");
			sb.append(Constants.OPEN_BRACKET);
			sb.append(parameters.getParm());
			sb.append(Constants.CLOSE_BRACKET);
		}
		
		if (!StringUtils.isNullOrEmpty(parameters.getArchiFname())) {
			sb.append(Constants.SPACE + "ARCHIFNAME");
			sb.append(Constants.OPEN_BRACKET);
			sb.append(parameters.getArchiFname());
			sb.append(Constants.CLOSE_BRACKET);
		}
		
		logger.debug("Command CftFileSnd :  " + sb.toString());

		callAs400.commandCall(sb.toString());*/

		logger.info("end execution of " + CftFileSnd.class.getName());
		FlowLogUtils.endDetail(OperationType.CFT_FL_SND);
	}

}
