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
import java.util.ArrayList;
import java.util.List;

import it.dmsoft.flowmanager.be.entities.ColumnMetadata;
import it.dmsoft.flowmanager.common.domain.Domains.Direction;
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

public class File2TableFixed extends DependentOperation<DbConversionParam>{

	private static final Logger logger = Logger.getLogger(File2TableFixed.class.getName());
	
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
		logger.info("start execution of " + File2TableFixed.class.getName());
		logger.info("parameters: " + parameters.toString());
		FlowLogUtils.startDetail(OperationType.FILE_2_DB);
		
		
		//utilizzato per gestire il file in modalità *add/*replace
		boolean appendMode = parameters.getMemberOptionAddReplace().equals("*ADD") ? true : false;

		Charset cs = StringUtils.getCharset(parameters.getCodepage().intValue());
		String fieldDelimiter = parameters.getFieldDelimiter();
		
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
			
			// Calcola le lunghezze cumulative delle colonne per separare i campi
	        List<Integer> fieldLengths = getFieldLengths(tableColumns);
			
			// Costruisci la query di insert
			String insertSQL = DatabaseUtils.buildInsertSQL(tableColumns, parameters.getLibrary(), parameters.getFile());
			
			//se sono in modalità con terminatore fixed spacchetto la stringa in base alla lunghezza
			if (parameters.getRecordDelimiter().equalsIgnoreCase(Constants.FIXED)) {
				if(executionFlowData.getFlowLunghezzaFlFlat().intValue()> 0) {
					processFileByFixedLength(conn, insertSQL, file ,  executionFlowData.getFlowLunghezzaFlFlat().intValue(),fieldLengths, cs, tableColumns );
				} else throw new OperationException("file flat = 0!!!!");
			} else processFileByEndDelimiter(conn, insertSQL, file ,  executionFlowData.getFlowLunghezzaFlFlat().intValue(),fieldLengths, cs, tableColumns );
		
        }
		
		logger.info("end execution of " + File2TableFixed.class.getName());
		FlowLogUtils.endDetail(OperationType.FILE_2_DB);
		
	}
	
    /**
     * Calcola la lunghezza cumulativa dei campi usando le precisioni delle colonne.
     */
    private static List<Integer> getFieldLengths(List<ColumnMetadata> columns) {
        List<Integer> lengths = new ArrayList<>();
        for (ColumnMetadata column : columns) {
            lengths.add(column.getPrecision()); // Usa la precisione come lunghezza del campo
        }
        return lengths;
    }

    /**
     * Divide una riga in base alle lunghezze dei campi.
     */
    private static List<String> parseLine(String line, List<Integer> fieldLengths) {
        List<String> fields = new ArrayList<>();
        int position = 0;

        for (int length : fieldLengths) {
            if (position + length <= line.length()) {
                fields.add(line.substring(position, position + length)); // Estrai il campo
            } else {
                fields.add(line.substring(position)); // Estrai il resto se la linea è più corta
                break;
            }
            position += length;
        }

        return fields;
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
	 
	 
	 private static List<String> splitFileByFixedLength(BufferedReader reader, int fixedLength) throws Exception {
		    List<String> lines = new ArrayList<>();    

		    char[] buffer = new char[fixedLength];
		        int charsRead;
		        
		        while ((charsRead = reader.read(buffer)) != -1) {
		            lines.add(new String(buffer, 0, charsRead)); // Aggiungi il blocco letto come riga
		        }
		    
		    
		    return lines;
	 }
	 
	 
	 private static void processFileByFixedLength(Connection conn, String insertSQL, File file, int fixedLength, List<Integer> fieldLengths, Charset cs, List<ColumnMetadata> tableColumns) throws Exception {
		 try (PreparedStatement pstmt = conn.prepareStatement(insertSQL);
	        		BufferedReader brData = new BufferedReader(new InputStreamReader(new FileInputStream(file), cs))) {
	       
		 
		        char[] buffer = new char[fixedLength];
		        int charsRead;

		        while ((charsRead = brData.read(buffer)) != -1) {
		            String line = new String(buffer, 0, charsRead);
		            
		            // Dividi la riga in campi
		            List<String> fields = parseLine(line, fieldLengths);

		            // Associa i valori al PreparedStatement
		            for (int i = 0; i < fields.size(); i++) {
		            	ColumnMetadata column = tableColumns.get(i);
	        			setPreparedStatementValue(pstmt, i + 1, column, fields.get(i).trim());
		            }

		            // Esegui l'inserimento
		            pstmt.executeUpdate();
		        }
		    }
		}
	 
	 private static void processFileByEndDelimiter(Connection conn, String insertSQL, File file, int fixedLength, List<Integer> fieldLengths, Charset cs, List<ColumnMetadata> tableColumns) throws Exception {
		 try (PreparedStatement pstmt = conn.prepareStatement(insertSQL);
	        		BufferedReader brData = new BufferedReader(new InputStreamReader(new FileInputStream(file), cs))) {
	        	

		        	String line;
		        	int rowCount = 0;
		        	while ((line = brData.readLine()) != null) {
		        		List<String> fields = parseLine(line, fieldLengths);

		        		// Setta solo i valori necessari per le colonne della tabella
		        		for (int i = 0; i < fields.size(); i++) {
		        			ColumnMetadata column = tableColumns.get(i);
		        			setPreparedStatementValue(pstmt, i + 1, column, fields.get(i).trim());
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

}
