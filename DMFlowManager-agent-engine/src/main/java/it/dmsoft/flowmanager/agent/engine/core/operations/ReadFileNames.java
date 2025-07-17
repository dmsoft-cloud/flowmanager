package it.dmsoft.flowmanager.agent.engine.core.operations;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import it.dmsoft.flowmanager.agent.engine.core.as400.CallAs400;
import it.dmsoft.flowmanager.agent.engine.core.db.dao.DbConstants;
import it.dmsoft.flowmanager.agent.engine.core.exception.OperationException;
import it.dmsoft.flowmanager.agent.engine.core.operations.core.Operation;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.ReadFileNamesParams;
import it.dmsoft.flowmanager.agent.engine.core.properties.PropertiesUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.OperationType;
import it.dmsoft.flowmanager.agent.engine.core.utils.LogDb;
import it.dmsoft.flowmanager.agent.engine.core.utils.StringUtils;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;


/*QSH CMD('ls -l /HOME/DIEGOA/GESTOREFLUSSI | grep ''^[-l]''  | awk ''{print $9}''
| grep ''^TEST'' > /HOME/DIEGOA/GESTOREFLUSSI/PIPPO') */

public class ReadFileNames extends Operation<ReadFileNamesParams> {

	private static final Logger logger = Logger.getLogger(ReadFileNames.class.getName());

	@Override
	public void execute() throws Exception {
		
		logger.info("start execution of " + ReadFileNames.class.getName());
		logger.info("parameters: " + parameters.toString());
		LogDb.start(OperationType.READ_NM_FL);
		
		String fileName = parameters.getListFile();
		String folder = parameters.getFolder();
		String fileListFolder = parameters.getListFileFolder();
		
		String searchString = StringUtils.escapeOtherRegexpChars(parameters.getFileName());
		
		searchString = Constants.CARET + searchString.replace(Constants.QUESTION_MARK, Constants.DOT)
		.replace(Constants.STAR, Constants.DOT + Constants.STAR) + Constants.DOLLAR;
		
		
		if(!Optional.ofNullable(DbConstants.REMOTE_HOST).filter(Constants.SI::equals).isPresent()){

			CallAs400 callAs400 = CallAs400.get(parameters);
			StringBuilder sb = new StringBuilder();
			sb.append("QSH");
			sb.append(Constants.SPACE + "CMD");
			sb.append(Constants.OPEN_BRACKET);
			sb.append(Constants.SINGLE_QUOTATION_MARK);
	
			addEnvVar(sb);
			
			sb.append("ls -l" + Constants.SPACE + folder + " | grep ''^[-l]''  | awk ''{print $9}'' | grep ''"
					+ searchString + "'' > " + fileListFolder + Constants.PATH_DELIMITER + fileName);
			sb.append(Constants.SINGLE_QUOTATION_MARK);
			sb.append(Constants.CLOSE_BRACKET);
	
			logger.debug("Command Read File Names  : " + sb.toString());
	
			callAs400.commandCall(sb.toString());		
	
			StringBuilder sb2 = new StringBuilder();
			sb2.append("CHGAUT");
			sb2.append(Constants.SPACE + "OBJ");
			sb2.append(Constants.OPEN_BRACKET);
			sb2.append(Constants.SINGLE_QUOTATION_MARK);
			sb2.append(fileListFolder + Constants.PATH_DELIMITER + fileName);
			sb2.append(Constants.SINGLE_QUOTATION_MARK);
			sb2.append(Constants.CLOSE_BRACKET);
			sb2.append(" USER(*PUBLIC) DTAAUT(*RWX) OBJAUT(*ALL) ");
	
			logger.debug("Command chaut for *public  : " + sb2.toString());
	
			callAs400.commandCall(sb2.toString());
		} else {
			String command = "ls -l" + Constants.SPACE + folder + " | grep '^[-l]'  | awk '{print $9}' | grep '"
					+ searchString + "' > " + fileListFolder + Constants.PATH_DELIMITER + fileName;
			logger.debug("Command Read File Names  : " + command);
			
			String shell = (StringUtils.isNullOrEmpty(parameters.getDefaultShell()) ? "sh" : parameters.getDefaultShell());
			Process process = Runtime.getRuntime().exec(new String[]{shell, "-c", command});
			// Leggi l'output standard
		    BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
		    // Leggi l'output di errore
		    BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
		    
		    // Stampa l'output standard
		    String s;
		    while ((s = stdInput.readLine()) != null) {
		        logger.info("Standard Output: " + s);
		    }

		    // Stampa l'output di errore
		    while ((s = stdError.readLine()) != null) {
		        logger.error("Error Output: " + s);
		    }
		    
			
			int exitCode = process.waitFor();
			if (exitCode == 0) {
	            logger.info("Command read files name: " + command);
	        } else {
	        	logger.error("error on read file name! -  " + command);
	        }
			
		}
		
		
		String command = "chmod 777 " + fileListFolder + Constants.PATH_DELIMITER + fileName;
		Process process = Runtime.getRuntime().exec(command);
		int exitCode = process.waitFor();
		if (exitCode == 0) {
            logger.info("Set grant on file: " + command);
        } else {
        	logger.error("Erro adding grant: " + command);
        }
		
	
		// TODO Vedere se e necessario mettere una codifica
		List<String> lines = Collections.emptyList();
		lines = (!Optional.ofNullable(DbConstants.REMOTE_HOST).filter(Constants.SI::equals).isPresent()) ?
			Files.readAllLines(Paths.get(fileListFolder + Constants.PATH_DELIMITER + fileName),
				Charset.forName(Constants.IBM280)) :
					Files.readAllLines(Paths.get(fileListFolder + Constants.PATH_DELIMITER + fileName));		
					
		parameters.getOperationParams().setFileNames(lines);
		parameters.getOperationParams().setTrasmissionFiles(lines);

		for (String line : lines) {
			logger.debug("File Name: " + line);
		}

		launchExceptionIfNoFileFound(lines);
		
		logger.info("end execution of " + ReadFileNames.class.getName());
		
		LogDb.end(OperationType.READ_NM_FL);
	}
	
	private void addEnvVar(StringBuilder sb) throws Exception {
		List<String> envAssignments = PropertiesUtils.getEnvs();
		
		for (String envAssignment : envAssignments) {
			sb.append("export " + envAssignment + " && ");
		}
	}
	
	public void launchExceptionIfNoFileFound(List<?> files) throws OperationException {
		if (parameters.isLaunchErrorIfNoFileFound() && (files == null || files.size() == 0)) {
			throw new OperationException("No such file");
		}
	}
	


}
