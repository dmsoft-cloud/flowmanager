package it.dmsoft.flowmanager.agent.engine.core.operations;

import java.math.BigDecimal;

import it.dmsoft.flowmanager.agent.engine.core.as400.CallAs400;
import it.dmsoft.flowmanager.agent.engine.core.operations.core.ConstraintDependentOperation;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.DbConversionParam;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.OperationType;
import it.dmsoft.flowmanager.agent.engine.core.utils.LogDb;
import it.dmsoft.flowmanager.agent.engine.core.utils.StringUtils;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class Db2File extends ConstraintDependentOperation<DbConversionParam, Boolean>{
	
	private static final Logger logger = Logger.getLogger(Db2File.class.getName());
	
	@Override
	public Boolean executeOperation() throws Exception {
		
		logger.info("start execution of " + Db2File.class.getName());
		logger.info("parameters: " + parameters.toString());
		LogDb.start(OperationType.DB_2_FILE);
		
		CallAs400 callAs400 = CallAs400.get(parameters);
		StringBuilder sb = new StringBuilder();
		
		//System.out.println(parameters);
		
		sb.append("CPYTOIMPF FROMFILE(");
		
		if (!StringUtils.isNullOrEmpty(parameters.getLibrary())) {
			sb.append(parameters.getLibrary() + Constants.PATH_DELIMITER);
		}
		
		sb.append(parameters.getFile());

		if (!StringUtils.isNullOrEmpty(parameters.getMember())) {
			sb.append(Constants.SPACE + parameters.getMember());
		}
		
		sb.append(")"); 
		sb.append(" TOSTMF('"+ Constants.PATH_DELIMITER + parameters.getFolderIfs() + Constants.PATH_DELIMITER + parameters.getFileNameIfs() + "')");
		
		//codepage
		if (parameters.getCodepage() != null && BigDecimal.ZERO.compareTo(parameters.getCodepage()) != 0) {
			sb.append(Constants.SPACE + "STMFCCSID" + Constants.OPEN_BRACKET + parameters.getCodepage() + Constants.CLOSE_BRACKET); 
		}
		
		//codepage
		if (parameters.getFromCcsid() != null && BigDecimal.ZERO.compareTo(parameters.getFromCcsid()) != 0) {
			sb.append(Constants.SPACE + "FROMCCSID" + Constants.OPEN_BRACKET + parameters.getFromCcsid() + Constants.CLOSE_BRACKET); 
		}
		
		//delimitatore record
		if (!StringUtils.isNullOrEmpty(parameters.getRecordDelimiter())) {
			sb.append(Constants.SPACE + "RCDDLM" + Constants.OPEN_BRACKET  + parameters.getRecordDelimiter() + Constants.CLOSE_BRACKET);
		}
		
		//delimitatore campo
		if (!StringUtils.isNullOrEmpty(parameters.getFieldDelimiter())) {
			sb.append(Constants.SPACE + "FLDDLM"+ Constants.OPEN_BRACKET + Constants.SINGLE_QUOTATION_MARK  + parameters.getFieldDelimiter() + Constants.SINGLE_QUOTATION_MARK + Constants.CLOSE_BRACKET);
		}
		
		//delimitatore stringa
		//STRDLM	String delimiter	Character value, *DBLQUOTE, *NONE
		if (!StringUtils.isNullOrEmpty(parameters.getStringDelimiter())) {
			sb.append(Constants.SPACE + "STRDLM"+ Constants.OPEN_BRACKET + parameters.getStringDelimiter() + Constants.CLOSE_BRACKET);
		}
		
		//modalità acquisizione
		if(!StringUtils.isNullOrEmpty(parameters.getMemberOptionAddReplace())) {
			sb.append(Constants.SPACE + "MBROPT" + Constants.OPEN_BRACKET+ parameters.getMemberOptionAddReplace() + Constants.CLOSE_BRACKET);
		}
		
		//Rimozione spazi
		if(!StringUtils.isNullOrEmpty(parameters.getRemoveBlanks())) {
			sb.append(Constants.SPACE + "RMVBLANK"+ Constants.OPEN_BRACKET + parameters.getRemoveBlanks() + Constants.CLOSE_BRACKET);
		}
		
		//Nomi colonne
		if(!StringUtils.isNullOrEmpty(parameters.getColumnName())) {
			if(parameters.getColumnName().equals(Constants.EXF)) sb.append(Constants.SPACE + "ADDCOLNAM" + Constants.OPEN_BRACKET + "*SQL" + Constants.CLOSE_BRACKET);
			else sb.append(Constants.SPACE + "ADDCOLNAM" + Constants.OPEN_BRACKET + parameters.getColumnName() + Constants.CLOSE_BRACKET);
		}
		
		//Controllo che sia CSV per mettere DECPNT
		if(!StringUtils.isNullOrEmpty(parameters.getDecimalPointer())) {
			sb.append(Constants.SPACE + "DECPNT" + Constants.OPEN_BRACKET + parameters.getDecimalPointer() + Constants.CLOSE_BRACKET);
		}
		
		//NUMFLDPAD Values *NONE, *BLANK, *ZERO
		if(!StringUtils.isNullOrEmpty(parameters.getFieldFilling())) {
			sb.append(Constants.SPACE + "NUMFLDPAD" + Constants.OPEN_BRACKET + parameters.getFieldFilling() + Constants.CLOSE_BRACKET);
		}
		
		
		//autorizzazione public
		sb.append(Constants.SPACE + "STMFAUT(*INDIR)");
		
		logger.debug("Command Db2File :  " + sb.toString());
	
		callAs400.commandCall(sb.toString());		
		
		logger.info("end execution of " + Db2File.class.getName());
		LogDb.end(OperationType.DB_2_FILE);
		
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
