package it.dmsoft.flowmanager.agent.engine.core.operations;

import java.math.BigDecimal;
import java.util.List;

import it.dmsoft.flowmanager.agent.engine.core.as400.CallAs400;
import it.dmsoft.flowmanager.agent.engine.core.operations.core.DependentOperation;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.DbConversionParam;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.OperationType;
import it.dmsoft.flowmanager.agent.engine.core.utils.FlowLogUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.StringUtils;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;
import it.dmsoft.flowmanager.common.domain.Domains.YesNo;

public class File2Db extends DependentOperation<DbConversionParam>{

	private static final Logger logger = Logger.getLogger(File2Db.class.getName());
	
	@Override
	public void updateParameters() throws Exception {
		if (Constants.OUTBOUND.equals(executionFlowData.getFlowDirezione())) {
			return;
		}
		
		List<String> downloadedFiles = operationParams.getTrasmissionFiles();		
		parameters.setDownloadedFiles(downloadedFiles);
		//parameters.setFileNameIfs(StringUtils.removePath(downloadedFiles.get(0)));
		logger.info("changed file name ifs " + parameters.getFileNameIfs());
	}

	@Override
	public void executeOperation() throws Exception {
		if(operationParams.isBypassConversion()) {
			logger.info("skip File2Db step");
			return;
		}
		logger.info("start execution of " + File2Db.class.getName());
		logger.info("parameters: " + parameters.toString());
		FlowLogUtils.startDetail(OperationType.FILE_2_DB);
		
		CallAs400 callAs400 = CallAs400.get(parameters);
		
        List<String> files = parameters.getDownloadedFiles();
        
        for (int i = 0; i < files.size(); i++) {
            String currentFile = StringUtils.removePath(files.get(i));
            logger.info("Processing file: " + currentFile);
		
			StringBuilder sb = new StringBuilder();
			
			sb.append("CPYFRMIMPF");
			
			sb.append(Constants.SPACE + "FROMSTMF");
			sb.append(Constants.OPEN_BRACKET + Constants.SINGLE_QUOTATION_MARK);
			sb.append(parameters.getFolderIfs() + Constants.PATH_DELIMITER + parameters.getFileNameIfs());
			sb.append(Constants.SINGLE_QUOTATION_MARK + Constants.CLOSE_BRACKET);
			
			sb.append(Constants.SPACE + "TOFILE");
			sb.append(Constants.OPEN_BRACKET);
			sb.append(parameters.getLibrary() + Constants.PATH_DELIMITER + parameters.getFile());
			sb.append(Constants.CLOSE_BRACKET);
			
			//from_ccsid TODO 
			if (parameters.getFromCcsid() != null && BigDecimal.ZERO.compareTo(parameters.getFromCcsid()) != 0) {
				sb.append(Constants.SPACE + "FROMCCSID"+ Constants.OPEN_BRACKET + parameters.getFromCcsid() + Constants.CLOSE_BRACKET);
			}
			
		
			if (parameters.getCodepage() != null && BigDecimal.ZERO.compareTo(parameters.getCodepage()) != 0) {
				sb.append(Constants.SPACE + "TOCCSID"+ Constants.OPEN_BRACKET + parameters.getCodepage() + Constants.CLOSE_BRACKET);
			}
			
			// delimitatore record
			if (!StringUtils.isNullOrEmpty(parameters.getRecordDelimiter())) {
				sb.append(Constants.SPACE + "RCDDLM" + Constants.OPEN_BRACKET + parameters.getRecordDelimiter()
						+ Constants.CLOSE_BRACKET);
			}
	
			// delimitatore campo
			if (!StringUtils.isNullOrEmpty(parameters.getFieldDelimiter())) {
				sb.append(Constants.SPACE + "FLDDLM" + Constants.OPEN_BRACKET + Constants.SINGLE_QUOTATION_MARK
						+ parameters.getFieldDelimiter() + Constants.SINGLE_QUOTATION_MARK + Constants.CLOSE_BRACKET);
			}
	
			// delimitatore stringa
			// STRDLM String delimiter Character value, *DBLQUOTE, *NONE
			if (!StringUtils.isNullOrEmpty(parameters.getStringDelimiter())) {
				sb.append(Constants.SPACE + "STRDLM" + Constants.OPEN_BRACKET + parameters.getStringDelimiter()
						+ Constants.CLOSE_BRACKET);
			}
	
			// modalita acquisizione
			if (!StringUtils.isNullOrEmpty(parameters.getMemberOptionAddReplace())) {
				 String mbrOpt = (i == 0) ? parameters.getMemberOptionAddReplace() : "*ADD";
				sb.append(Constants.SPACE + "MBROPT" + Constants.OPEN_BRACKET + mbrOpt + Constants.CLOSE_BRACKET);
			}
	
			// Rimozione spazi
			if (!StringUtils.isNullOrEmpty(parameters.getRemoveBlanks())) {
				sb.append(Constants.SPACE + "RMVBLANK" + Constants.OPEN_BRACKET + parameters.getRemoveBlanks()
						+ Constants.CLOSE_BRACKET);
			}
			
			//RPLNULLVAL values *NO, *FILEDDEFAULT 
			if(!StringUtils.isNullOrEmpty(parameters.getReplaceNullVal())) {
				sb.append(Constants.SPACE + "RPLNULLVAL" + Constants.OPEN_BRACKET + parameters.getReplaceNullVal()
						  +Constants.CLOSE_BRACKET);
			}
			
			//RMVCOLNAM values *NO, *YES
			if(parameters.getRemoveColName() != null) {
				 String valueConverted;
				 if(YesNo.YES.equals(parameters.getRemoveColName()))
					 valueConverted = Constants.YES_AS400;
				 else valueConverted = Constants.NO_AS400;
				 
				 sb.append(Constants.SPACE + "RMVCOLNAM" + Constants.OPEN_BRACKET + valueConverted + Constants.CLOSE_BRACKET);
			}
			
			logger.debug("Command File2Db :  " + sb.toString());
			
			callAs400.commandCall(sb.toString());	
		
        }
			
		logger.info("end execution of " + File2Db.class.getName());
		FlowLogUtils.endDetail(OperationType.FILE_2_DB);
		
	}

}
