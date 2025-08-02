package it.dmsoft.flowmanager.agent.engine.core.operations;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import it.dmsoft.flowmanager.agent.engine.core.operations.core.Operation;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.ReadSpoolFilesParams;
import it.dmsoft.flowmanager.agent.engine.core.properties.PropertiesUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.OperationType;
import it.dmsoft.flowmanager.agent.engine.core.utils.DatabaseUtils.DBTypeEnum;
import it.dmsoft.flowmanager.agent.engine.core.utils.FlowLogUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.StringUtils;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;
import it.dmsoft.flowmanager.common.domain.Domains.YesNo;


public class ReadSpoolFiles extends Operation<ReadSpoolFilesParams> {

	private static final Logger logger = Logger.getLogger(ReadFileNames.class.getName());

    private static class SpoolFile {
        String path;
        String name;

        SpoolFile(String path, String name) {
            this.path = path;
            this.name = name;
        }       
        
    }
	
	@Override
	public void execute() throws Exception {
		
		logger.info("start execution of " + ReadSpoolFiles.class.getName());
		logger.info("parameters: " + parameters.toString());
		FlowLogUtils.startDetail(OperationType.READ_SPOOL);
		
		String folder = parameters.getFolder();
		List<SpoolFile> spoolFiles = new ArrayList<SpoolFile>();
		List<String> taskFileNames = new ArrayList<>();
		
		if(!YesNo.YES.equals(parameters.isIBMi())) {
			//leggi il file degli spool con il codice lavoro e popola array file
			Connection conn = null;
			String queryString = "SELECT PDF_PATH, PDF_NAME FROM "+ parameters.getSchema() + "ASMFFPDFDATA " +
								 "where PDF_JOB_NAME = ? and PDF_JOB_USER = ? and PDF_JOB_NBR = ? " ;
			logger.debug("query to be executed: " + queryString );
			
			conn = DBTypeEnum.get(parameters.getDbType()).getConnection(parameters, parameters.getSchema()); 
			
			PreparedStatement ps = conn.prepareStatement(queryString);
			ps.setString(1, parameters.getOperationParams().getExternalJob());
			ps.setString(2, parameters.getOperationParams().getExternalUser());
			
			ps.setBigDecimal(3, parameters.getOperationParams().getExternalNumber());
			ResultSet rs = ps.executeQuery();
			
			logger.debug("Executed query using the following parameter - job: "  + parameters.getOperationParams().getExternalJob() +
								" , user: " + parameters.getOperationParams().getExternalUser() + 
								" , number: " + parameters.getOperationParams().getExternalNumber());
			
			while (rs.next()) { 
				spoolFiles.add(new SpoolFile(Paths.get(rs.getString("PDF_PATH")).getFileName().toString(), rs.getString("PDF_NAME")));
				//String spoolName = Paths.get(rs.getString("PDF_PATH")).getFileName().toString();
				folder = Paths.get(rs.getString("PDF_PATH")).getParent().toString();
				
				//spoolFiles.add(spoolName);
			}
			
			//leggi per il task se ci sono specifici file da inviare altrimenti manda tutto
			if (!StringUtils.isNullOrEmpty(parameters.getOperationParams().getExternalTask())) {
				
				logger.info("Start filter using OTGFFTSKID!");
				String taskQuery = "SELECT OTGFTSK_FILE_NAME FROM " + parameters.getSchema() + "OTGFFTSKID WHERE OTGFTSK_TASK_ID = ?";
                PreparedStatement taskPs = conn.prepareStatement(taskQuery);
                taskPs.setString(1, parameters.getOperationParams().getExternalTask());
                ResultSet taskRs = taskPs.executeQuery();
                
                while (taskRs.next()) {
                    taskFileNames.add(taskRs.getString("OTGFTSK_FILE_NAME"));
                }
                
                // Se esistono file associati al task, filtriamo gli spool file
                if (!taskFileNames.isEmpty()) {
                    spoolFiles = spoolFiles.stream()
                            .filter(spool -> taskFileNames.contains(spool.name))
                            .collect(Collectors.toList());
                }
				
			}
			
			//folder = spoolFiles.isEmpty() ? folder : Paths.get(spoolFiles.get(0).path).getParent().toString();			
			parameters.setFolder(folder);
	
			parameters.getOperationParams().setFileNames(spoolFiles.stream().map(spool -> spool.path).collect(Collectors.toList()));
            parameters.getOperationParams().setTrasmissionFiles(spoolFiles.stream().map(spool -> spool.path).collect(Collectors.toList()));
		}
		
		for (SpoolFile file : spoolFiles) {
            logger.debug("File Name: " + file.name + " ,   SPOOL name: " + file.path);
        }

		logger.debug("Folder for spool file --> " + folder);
		
		logger.info("end execution of " + ReadSpoolFiles.class.getName());
		
		FlowLogUtils.endDetail(OperationType.READ_SPOOL);
	}
	
	private void addEnvVar(StringBuilder sb) throws Exception {
		List<String> envAssignments = PropertiesUtils.getEnvs();
		
		for (String envAssignment : envAssignments) {
			sb.append("export " + envAssignment + " && ");
		}
	}
	

}
