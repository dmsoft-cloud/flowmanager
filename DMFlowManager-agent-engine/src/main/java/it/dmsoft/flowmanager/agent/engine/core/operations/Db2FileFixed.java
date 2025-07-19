package it.dmsoft.flowmanager.agent.engine.core.operations;

import java.math.BigDecimal;

import it.dmsoft.flowmanager.agent.engine.core.as400.CallAs400;
import it.dmsoft.flowmanager.agent.engine.core.operations.core.ConstraintDependentOperation;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.DbConversionParam;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.OperationType;
import it.dmsoft.flowmanager.agent.engine.core.utils.FlowLogUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.StringUtils;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class Db2FileFixed extends ConstraintDependentOperation<DbConversionParam, Boolean>{

	private static final Logger logger = Logger.getLogger(Db2FileFixed.class.getName());
	
	@Override
	public Boolean executeOperation() throws Exception {
		logger.info("start execution of " + Db2FileFixed.class.getName());
		logger.info("parameters: " + parameters.toString());
		FlowLogUtils.startDetail(OperationType.DB_2_FIXED);
		
		CallAs400 callAs400 = CallAs400.get(parameters);
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("CPYTOSTMF");
		
		sb.append(Constants.SPACE + "TOSTMF");
		sb.append(Constants.OPEN_BRACKET + Constants.SINGLE_QUOTATION_MARK);
		sb.append(parameters.getFolderIfs() + Constants.PATH_DELIMITER + parameters.getFileNameIfs());
		sb.append(Constants.SINGLE_QUOTATION_MARK + Constants.CLOSE_BRACKET);
		
		sb.append(Constants.SPACE + "FROMMBR");
		sb.append(Constants.OPEN_BRACKET + Constants.SINGLE_QUOTATION_MARK);
		sb.append(Constants.PATH_DELIMITER + Constants.QSYS + Constants.DOT + Constants.LIB 
				+ Constants.PATH_DELIMITER + parameters.getLibrary() + Constants.DOT + Constants.LIB
				+ Constants.PATH_DELIMITER + parameters.getFile() + Constants.DOT + Constants.FILE
				+ Constants.PATH_DELIMITER + 
				(StringUtils.isNullOrEmpty(parameters.getMember()) ? parameters.getFile() : parameters.getMember())
				+ Constants.DOT + Constants.MBR);
		sb.append(Constants.SINGLE_QUOTATION_MARK + Constants.CLOSE_BRACKET);
		 
		//from_ccsid TODO 
		if (parameters.getFromCcsid() != null && BigDecimal.ZERO.compareTo(parameters.getFromCcsid()) != 0) {
			sb.append(Constants.SPACE + "STMFCCSID"
					+ ""+ Constants.OPEN_BRACKET + parameters.getFromCcsid() + Constants.CLOSE_BRACKET);
		}
	
		if (parameters.getCodepage() != null && BigDecimal.ZERO.compareTo(parameters.getCodepage()) != 0) {
			sb.append(Constants.SPACE + "DBFCCSID"+ Constants.OPEN_BRACKET + parameters.getCodepage() + Constants.CLOSE_BRACKET);
		}		
		
		// delimitatore record
		/*
		if (!StringUtils.isNullOrEmpty(parameters.getRecordDelimiter())) {
			sb.append(Constants.SPACE + "ENDLINFMT" + Constants.OPEN_BRACKET + parameters.getRecordDelimiter()
					+ Constants.CLOSE_BRACKET);
		}*/
		
		sb.append(Constants.SPACE + "ENDLINFMT" + Constants.OPEN_BRACKET + Constants.FIXED
		+ Constants.CLOSE_BRACKET);

		// modalita acquisizione
		if (!StringUtils.isNullOrEmpty(parameters.getMemberOptionAddReplace())) {
			sb.append(Constants.SPACE + "STMFOPT" + Constants.OPEN_BRACKET + parameters.getMemberOptionAddReplace()
					+ Constants.CLOSE_BRACKET);
		}

		sb.append(Constants.SPACE + "AUT" + Constants.OPEN_BRACKET + Constants.AUT_INDIR + Constants.CLOSE_BRACKET);
		
		logger.debug("Command Db2FileFixed :  " + sb.toString());
		
		callAs400.commandCall(sb.toString());	
		
		logger.info("end execution of " + Db2FileFixed.class.getName());
		FlowLogUtils.endDetail(OperationType.DB_2_FIXED);
		return true;
		
	}

	@Override
    public void updateParameters() throws Exception {
    	
    }
    
    @Override
    public void updateOperationParams(Boolean result) throws Exception {
        // Aggiorna i parametri dell'operazione se necessario
    }
}
