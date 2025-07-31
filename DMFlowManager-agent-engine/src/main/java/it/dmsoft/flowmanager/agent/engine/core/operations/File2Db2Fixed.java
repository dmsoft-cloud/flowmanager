package it.dmsoft.flowmanager.agent.engine.core.operations;

import java.math.BigDecimal;
import java.util.List;

import com.ibm.as400.access.AS400Text;
import com.ibm.as400.access.ProgramParameter;

import it.dmsoft.flowmanager.agent.engine.core.as400.CallAs400;
import it.dmsoft.flowmanager.agent.engine.core.operations.core.DependentOperation;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.DbConversionParam;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.OperationType;
import it.dmsoft.flowmanager.agent.engine.core.utils.FlowLogUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.StringUtils;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;
import it.dmsoft.flowmanager.common.domain.Domains.Direction;

public class File2Db2Fixed extends DependentOperation<DbConversionParam>{

	private static final Logger logger = Logger.getLogger(File2Db2Fixed.class.getName());
	
	@Override
	public void updateParameters() throws Exception {
		if (Direction.OUTBOUND.equals(executionFlowData.getFlowDirezione())) {
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
			return;
		}
		logger.info("start execution of " + File2Db2Fixed.class.getName());
		logger.info("parameters: " + parameters.toString());
		FlowLogUtils.startDetail(OperationType.FILE_2_DB);
		
		CallAs400 callAs400 = CallAs400.get(parameters);
		
		List<String> files = parameters.getDownloadedFiles();
		
		for (int i = 0; i < files.size(); i++) {
			
            String currentFile = StringUtils.removePath(files.get(i));
            logger.info("Processing file: " + currentFile);
		
			String command = "cp " + parameters.getFolderIfs() + Constants.PATH_DELIMITER + currentFile + " " + parameters.getFolderIfs() + Constants.PATH_DELIMITER + currentFile + "."
					+ "tmp";
			Process process = Runtime.getRuntime().exec(command);
			
			 // Attendi che il processo termini e ottieni l'exit code
	        int exitCode = process.waitFor();
			
			
			StringBuilder sb = new StringBuilder();
			
			sb.append("CPYFRMSTMF");
			
			sb.append(Constants.SPACE + "FROMSTMF");
			sb.append(Constants.OPEN_BRACKET + Constants.SINGLE_QUOTATION_MARK);
	        
			if (exitCode == 0) {
	            logger.info("File copiato con successo a .tmp!");
	            sb.append(parameters.getFolderIfs() + Constants.PATH_DELIMITER + parameters.getFileNameIfs()+ ".tmp");
	            
	        } else {
	        	sb.append(parameters.getFolderIfs() + Constants.PATH_DELIMITER + parameters.getFileNameIfs());
	            logger.error("Errore nella copia del file a file .tmp. Codice di uscita: " + exitCode);
	        }
			
			sb.append(Constants.SINGLE_QUOTATION_MARK + Constants.CLOSE_BRACKET);
			
			sb.append(Constants.SPACE + "TOMBR");
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
			if (!StringUtils.isNullOrEmpty(parameters.getRecordDelimiter())) {
				sb.append(Constants.SPACE + "ENDLINFMT" + Constants.OPEN_BRACKET + parameters.getRecordDelimiter()
						+ Constants.CLOSE_BRACKET);
			}
	
			// modalita acquisizione
			if (!StringUtils.isNullOrEmpty(parameters.getMemberOptionAddReplace())) {
				String mbrOpt = (i == 0) ? parameters.getMemberOptionAddReplace() : "*ADD";
				sb.append(Constants.SPACE + "MBROPT" + Constants.OPEN_BRACKET + mbrOpt	+ Constants.CLOSE_BRACKET);
			}
			
			// TABEXPN
			if (!StringUtils.isNullOrEmpty(parameters.getTabexpn())) {
				sb.append(Constants.SPACE + "TABEXPN" + Constants.OPEN_BRACKET + parameters.getTabexpn()
						+ Constants.CLOSE_BRACKET);
			}
			
			logger.debug("Command File2Db :  " + sb.toString());
			
			callAs400.commandCall(sb.toString());	
			
			//eseguo la cancellazione del file .tmp solo se ero riuscito a crearlo e dopo che è terminato il cpyfrmstmf
			if (exitCode == 0) {
	            logger.info("Remove file .tmp!");
	            String rmCommand = "rm "  + parameters.getFolderIfs() + Constants.PATH_DELIMITER + currentFile + ".tmp";
	    		Process rmProcess = Runtime.getRuntime().exec(rmCommand);
	    		
	        }
		
		}
		
		logger.info("end execution of " + File2Db2Fixed.class.getName());
		FlowLogUtils.endDetail(OperationType.FILE_2_DB);
		
	}

}
