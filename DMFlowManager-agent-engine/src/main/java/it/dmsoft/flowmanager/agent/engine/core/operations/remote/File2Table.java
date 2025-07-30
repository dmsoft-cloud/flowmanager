package it.dmsoft.flowmanager.agent.engine.core.operations.remote;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import it.dmsoft.flowmanager.be.entities.ColumnMetadata;
import it.dmsoft.flowmanager.common.domain.Domains.YesNo;
import it.dmsoft.flowmanager.agent.engine.core.db.DbConstants;
import it.dmsoft.flowmanager.agent.engine.core.exception.OperationException;
import it.dmsoft.flowmanager.agent.engine.core.operations.core.DependentOperation;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.DbConversionParam;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.OperationType;
import it.dmsoft.flowmanager.agent.engine.core.utils.DatabaseUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.DatabaseUtils.DBTypeEnum;
import it.dmsoft.flowmanager.agent.engine.core.utils.FlowLogUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.StringUtils;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class File2Table extends DependentOperation<DbConversionParam>{

	private static final Logger logger = Logger.getLogger(File2Table.class.getName());
	
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
		logger.info("start execution of " + File2Table.class.getName());
		logger.info("parameters: " + parameters.toString());
		FlowLogUtils.startDetail(OperationType.FILE_2_TB);
		
		/*
		
		
		// delimitatore record --> non lo testo in quanto la lettura con buffer reader gia li tratta
		
		
		// delimitatore stringa
		// STRDLM String delimiter Character value, *DBLQUOTE, *NONE
		if (!StringUtils.isNullOrEmpty(parameters.getStringDelimiter())) {
			sb.append(Constants.SPACE + "STRDLM" + Constants.OPEN_BRACKET + parameters.getStringDelimiter()
					+ Constants.CLOSE_BRACKET);
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
		

		*/
				
		Charset cs = StringUtils.getCharset(parameters.getCodepage().intValue());
		String fieldDelimiter = parameters.getFieldDelimiter();
		
		//utilizzato per gestire il file in modalità *add/*replace
		boolean appendMode = parameters.getMemberOptionAddReplace().equals("*ADD") ? true : false;
		
		Connection conn = DBTypeEnum.fromString(DbConstants.DB_TYPE).getConnection();

        List<String> files = parameters.getDownloadedFiles();
        
        for (int j = 0; j < files.size(); j++) {
        	
            String currentFile = StringUtils.removePath(files.get(j));
            logger.info("Processing file: " + currentFile);
		
	        File file = new File(Constants.PATH_DELIMITER + parameters.getFolderIfs() + Constants.PATH_DELIMITER + currentFile);	
			
			
			if (!DatabaseUtils.checkTableExists(conn,parameters.getFile(), parameters.getLibrary())) {
				throw new OperationException("Table not found: " + parameters.getLibrary() + Constants.DOT + parameters.getFile());
	        }
			List<ColumnMetadata> tableColumns = DatabaseUtils.getTableColumns(conn, parameters.getLibrary(), parameters.getFile());
			
			//pulisco il file se siamo in modalità di replace
			if(!appendMode && j == 0) DatabaseUtils.clearFile(conn, parameters.getLibrary(), parameters.getFile());
			
			//verifico la presenza di rowid tra le colonne e la elimino per LM
			tableColumns.removeIf(column -> column.getName().equalsIgnoreCase(Constants.ROWID));
	
	        // Costruisci la query di insert
	        String insertSQL = DatabaseUtils.buildInsertSQL(tableColumns, parameters.getLibrary(), parameters.getFile());
	
	        // Apre il lettore per rileggere i dati e ignora l'header se necessario
	        try (PreparedStatement pstmt = conn.prepareStatement(insertSQL);
	        		BufferedReader brData = new BufferedReader(new InputStreamReader(new FileInputStream(file), cs))) {
	        	
	        	//BufferedReader brData = new BufferedReader(new FileReader(file))   
	        	
	        	// Salta la prima riga se specificato dal parametro
	        	 if(YesNo.YES.equals(parameters.getRemoveColName())) 
	        		 	brData.readLine(); 
	
	             String line;
	             int rowCount = 0;
	             while ((line = brData.readLine()) != null) {
	               String[] values = line.split(fieldDelimiter);
	
	               // Setta solo i valori necessari per le colonne della tabella
	               for (int i = 0; i < tableColumns.size(); i++) {
	            	   ColumnMetadata column = tableColumns.get(i);
	            	   String value = i < values.length ? values[i].trim() : null;
	            	   setPreparedStatementValue(pstmt, i + 1, column, value);
	               }
	
	                    pstmt.addBatch();  // Aggiunge alla batch
	                    rowCount++;
	                    if (rowCount % 1000 == 0) {
	                        executeBatchWithErrorHandling(pstmt, rowCount);
	                    }
	                }
	             // Esegue l'ultimo batch rimanente
	             if (rowCount % 1000 != 0) {
	                 executeBatchWithErrorHandling(pstmt, rowCount);
	             }
	             
	            }
			
        }
        
		logger.info("end execution of " + File2Table.class.getName());
		FlowLogUtils.endDetail(OperationType.FILE_2_TB);
		
	}
	

	 private static void executeBatchWithErrorHandling(PreparedStatement pstmt, int rowCount) throws SQLException {
		    try {
		        pstmt.executeBatch(); // Esegue il batch
		    } catch (BatchUpdateException e) {
		        // Analizza i dettagli dell'errore
		        int[] updateCounts = e.getUpdateCounts();
		        for (int i = 0; i < updateCounts.length; i++) {
		            if (updateCounts[i] == Statement.EXECUTE_FAILED) {
		                System.err.println("Error importing file at line: " + (rowCount - updateCounts.length + i + 1));
		            }
		        }
		        throw e; // Rilancia l'eccezione per gestirla nel chiamante
		    }
	 }
	 
	 private static void setPreparedStatementValue(PreparedStatement pstmt, int parameterIndex, ColumnMetadata column, String value) throws SQLException {
		    String typeName = column.getTypeName();
		    try {
		        if (value == null) {
		            pstmt.setNull(parameterIndex, java.sql.Types.NULL);
		            return;
		        }

		        switch (typeName.toUpperCase()) {
		            case "VARCHAR":
		            case "CHAR":
		            case "TEXT":
		                pstmt.setString(parameterIndex, value.trim());
		                break;

		            case "INTEGER":
		            case "INT":
		                pstmt.setInt(parameterIndex, Integer.parseInt(value.trim()));
		                break;

		            case "BIGINT":
		                pstmt.setLong(parameterIndex, Long.parseLong(value.trim()));
		                break;

		            case "DECIMAL":
		            case "NUMERIC":
		            case "FLOAT":
		            case "DOUBLE":
		                pstmt.setBigDecimal(parameterIndex, new BigDecimal(value.trim()));
		                break;

		            case "DATE":
		                pstmt.setDate(parameterIndex, java.sql.Date.valueOf(value.trim()));
		                break;

		            case "TIMESTAMP":
		                pstmt.setTimestamp(parameterIndex, java.sql.Timestamp.valueOf(value.trim()));
		                break;

		            case "BOOLEAN":
		                pstmt.setBoolean(parameterIndex, Boolean.parseBoolean(value.trim()));
		                break;

		            default:
		                // Tipo non gestito esplicitamente
		                pstmt.setObject(parameterIndex, value.trim());
		        }
		    } catch (Exception e) {
		        logger.error("Error converting value '" + value + "' to type " + typeName + " for column " + column.getName(), e);
		        pstmt.setNull(parameterIndex, java.sql.Types.NULL); // Imposta NULL se la conversione fallisce
		    }
		}

	
}
