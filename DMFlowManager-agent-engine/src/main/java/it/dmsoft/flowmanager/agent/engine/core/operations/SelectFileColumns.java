package it.dmsoft.flowmanager.agent.engine.core.operations;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import it.dmsoft.flowmanager.agent.engine.core.exception.OperationException;
import it.dmsoft.flowmanager.agent.engine.core.operations.core.ConstraintDependentOperation;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.SelectFileColumnsParam;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.OperationType;
import it.dmsoft.flowmanager.agent.engine.core.utils.DatabaseUtils.DBTypeEnum;
import it.dmsoft.flowmanager.agent.engine.core.utils.ExportUtils.ExportType;
import it.dmsoft.flowmanager.agent.engine.core.utils.FlowLogUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.StringUtils;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;
import it.dmsoft.flowmanager.be.entities.FileColumnsStruct;
import it.dmsoft.flowmanager.be.entities.FileMetadata;
import it.dmsoft.flowmanager.common.domain.Domains.DbType;

public class SelectFileColumns extends ConstraintDependentOperation<SelectFileColumnsParam, Boolean> {
	
	private static final Logger logger = Logger.getLogger(SelectFileColumns.class.getName());
	StringBuilder qs ;

    @Override
    public void updateParameters() throws Exception {
    
    }

    @Override
    public Boolean executeOperation() throws Exception {
    	
    	logger.info("start execution of " + SelectFileColumns.class.getName());
		logger.info("parameters: " + parameters.toString());
		FlowLogUtils.startDetail(OperationType.SELECT_COL);

		//serve a verificare se è modificato l'ordinamento delle colonne
		Boolean reorderColumns= false;
		//consnete di capire se è configurato l'export file
		long counter = 0;
		
		//campi per replicare il funzionamento as400
		int max_lenght = 0;
		int exp_ltest =0;
		int exp_lrec = 0;
		int wk_max_lenght=0;
		List<FileColumnsStruct> sortedItems = new ArrayList<FileColumnsStruct>();
		List<FileColumnsStruct> colList= new ArrayList<FileColumnsStruct>();

        try {

        	Connection conn = null;
    		conn = DBTypeEnum.get(parameters.getDbType()).getConnection(parameters, parameters.getSchema());
    		
        	executeQryInsertNewColumns(conn);
        	executeQryRemoveColumns(conn);
        	
        	String st = ExportType.fromString(executionFlowData.getFlowExportFlag()).getQryExtractExportData(parameters.getSchema(),
        			!StringUtils.isNullOrEmpty(parameters.getPgmLibrary()) ? parameters.getPgmLibrary() + Constants.DOT : parameters.getSchema() );
        	logger.info("check export file query:  " + st);
    		
    		PreparedStatement ps = conn.prepareStatement(st);
    		ps.setString(1, executionFlowData.getFlowExportCode());
    		ResultSet rs = ps.executeQuery();

    		while (rs.next()) { 
    			counter++;
    			FileColumnsStruct item = new FileColumnsStruct(); 
    			if (counter == 1) {
    				//era usato per creare il file guida....da farsi quando ne capiamo il funzionamento
    				wk_max_lenght =  Math.max(exp_lrec, exp_ltest);	
    			}
    			item.setDescription(rs.getString("COL_DESC"));
    			int prog = rs.getInt("PROGRESSIVO");
    			int seq = rs.getInt("SEQ");
    			item.setProg(prog);
    			item.setSeq(seq); 
    			if (!rs.wasNull()) {
    			    // Se i valori sono diversi, imposta reorderColumns a true
    			    if (prog != seq) {
    			        reorderColumns = true;
    			    }
    			}
    			colList.add(item);
    		}
            if (counter==0) throw new Exception("Export file not allowed or not configured!!!");
            
            //riodino le colonne estratte dai file di configurazione export se necessario altrimenti sono quelle originarie
            if (reorderColumns) {
            	sortedItems = colList.stream()
            		.filter(item -> item.getSeq() != 0)
            		.sorted(Comparator.comparingInt(FileColumnsStruct::getSeq))
                    .collect(Collectors.toList());
            } else sortedItems = colList;
            if (counter > 0) {
            	// Recupera i metadati della tabella originale
	            List<FileMetadata> availableFields = getTableFields(conn, executionFlowData.getFlowFile(), executionFlowData.getFlowLibreria());
	             
	            // Riordina availableFields in base all'ordine di selectedFields
	            List<FileMetadata> sortedAvailableFields = sortedItems.stream()
	                .map(item -> availableFields.stream()
	                    .filter(field -> field.getProg() == item.getProg())
	                    .findFirst()
	                    .orElseThrow(() -> new IllegalArgumentException("Campo non trovato per selezione tabella temporanea: " + item.getDescription())))
	                .collect(Collectors.toList());
	            
	            createNewTableAndTransferData(conn, parameters.getTargetTable(), parameters.getTargetSchema() , executionFlowData.getFlowFile(),executionFlowData.getFlowLibreria() , sortedItems, sortedAvailableFields, parameters.getDbType());
	            
	            
	            /*
	            logger.info("override original file using custom file!!!!! --> file: " + DbConstants.GF_CURLIB + Constants.DOT + targetTable);
	            executionFlowData.setFlowFile(targetTable);
	            executionFlowData.setFlowLibreria(DbConstants.GF_CURLIB);
	            */
	            
            }
            
            
            logger.info("end execution of " + SelectFileColumns.class.getName());
			FlowLogUtils.endDetail(OperationType.SELECT_COL);
			
            return true;
        } catch (OperationException e) {
            throw e;
        } catch (Throwable e) {
            throw new Exception("Error during select columns operation: ", e);
        }
    }

    @Override
    public void updateOperationParams(Boolean result) throws Exception {
        operationParams.setExportTempTable(parameters.getTargetTable());
        operationParams.setExportTempSchema(parameters.getTargetSchema());
        
    }
    
    private void executeQryInsertNewColumns(Connection conn) throws SQLException {
    	String st = ExportType.fromString(executionFlowData.getFlowExportFlag()).getQryInsertNewColumns(parameters.getSchema(),
    			!StringUtils.isNullOrEmpty(parameters.getPgmLibrary()) ? parameters.getPgmLibrary() + Constants.DOT : parameters.getSchema() );
    	logger.info("check order columns query:  " + st);
    	PreparedStatement ps1 = conn.prepareStatement(st);
    	
		ps1.setString(1, executionFlowData.getFlowExportCode());
		ps1.executeUpdate();
		
		ps1.close();
    }
    
    private void executeQryRemoveColumns(Connection conn) throws SQLException {
    	String st = ExportType.fromString(executionFlowData.getFlowExportFlag()).getQryRemoveColumns(parameters.getSchema(),
    			!StringUtils.isNullOrEmpty(parameters.getPgmLibrary()) ? parameters.getPgmLibrary() + Constants.DOT : parameters.getSchema() );
    	
    	logger.info("delete columns query:  " + st); 
    	PreparedStatement ps2 = conn.prepareStatement(st);
		ps2.setString(1, executionFlowData.getFlowExportCode());
		
		ps2.executeUpdate();
		
		ps2.close();
    }
    
    // Recupera i campi di una tabella
    private static List<FileMetadata> getTableFields(Connection conn, String tableName, String tableSchema ) throws SQLException {
        List<FileMetadata> fields = new ArrayList<>();
        String query = "SELECT * FROM " + tableSchema + Constants.DOT + tableName + " WHERE 1 = 0 ";  // Evita di caricare troppi dati
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            ResultSetMetaData rsMetaData = rs.getMetaData();
            int columnCount = rsMetaData.getColumnCount();
    		if (columnCount > 0 && "ROWID".equalsIgnoreCase(rsMetaData.getColumnName(columnCount))) {
    		    columnCount--;  
    		}
            for (int i = 1; i <= columnCount; i++) {
                String fieldName = rsMetaData.getColumnName(i);  // Recupera il nome del campo
                FileMetadata item = new FileMetadata();
                item.setDescription(fieldName);
                item.setProg(i);
                fields.add(item);  // Crea oggetto Field con nome e progressivo
            }
        }
        return fields;
    }
    
    
    // Crea una nuova tabella con i campi rinominati e riordinati e li inserisce nella tabella target
    private static void createNewTableAndTransferData(Connection conn, String newTableName, String newTableSchema , String oldTableName,String oldTableSchema , List<FileColumnsStruct> fields, List<FileMetadata> sortedAvailableFields, DbType dbType) throws SQLException {
    	// Crea la query per creare la nuova tabella con i campi riordinati e rinominati
        //String createQuery = "CREATE TABLE " + newTableSchema + Constants.DOT + newTableName + " AS ( SELECT ";

        String strFiled = fields.stream().map(fileColumnsStruct -> {
        			// Cerca il nome del campo originale da 'sortedAvailableFields'
                     FileMetadata field = sortedAvailableFields.stream()
                         .filter(f -> f.getProg() == fileColumnsStruct.getProg())
                         .findFirst()
                         .orElseThrow(() -> new IllegalArgumentException("Campo non trovato: " + fileColumnsStruct.getDescription()));
                     
                     //Controllo se mettere escape nel nome colonna
                     String newField = needsEscape(fileColumnsStruct.getDescription()) ?  DBTypeEnum.get(dbType).escapeColumnName(fileColumnsStruct.getDescription()) : fileColumnsStruct.getDescription();
                     return field.getDescription() + " AS " + newField;  // Restituisci l'associazione campo originale -> nome nuovo
                     
        		 })
        		.collect(Collectors.joining(", ")) ; // Unisce tutti i campi con una virgola
        	

        	
        	//String strFrom =	" FROM " + oldTableSchema + Constants.DOT + oldTableName + " WHERE 1=0 ) " + " WITH NO DATA ";
        	//String finalQry = createQuery + strFiled + strFrom;
        	String finalQry = DBTypeEnum.get(dbType).getQueryCreateEmptyTable(strFiled, newTableSchema, newTableName, oldTableSchema, oldTableName );
        	
        	logger.info("query to crate new table: " + finalQry);
        // Esegui la query per creare la nuova tabella (solo struttura)
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(finalQry);
            stmt.close();
        }

        // Crea la query per travasare i dati dalla tabella sorgente alla tabella riordinata
         
        String insertQuery = "INSERT INTO " + newTableSchema + Constants.DOT + newTableName + " (" +
            fields.stream()
                .map(fileColumnsStruct -> needsEscape(fileColumnsStruct.getDescription()) ? 
                		DBTypeEnum.get(dbType).escapeColumnName(fileColumnsStruct.getDescription()) : fileColumnsStruct.getDescription())
                .collect(Collectors.joining(", ")) +  // Unisce i nomi dei nuovi campi con una virgola
            ") SELECT " +
            fields.stream()
                .map(fileColumnsStruct -> sortedAvailableFields.stream()
                    .filter(field -> field.getProg() == fileColumnsStruct.getProg())
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Campo non trovato")))
                    .map(field -> needsEscape(field.getDescription()) ? 
                    		DBTypeEnum.get(dbType).escapeColumnName(field.getDescription()) : field.getDescription())
                .collect(Collectors.joining(", ")) +  // Unisce i nomi originali dei campi con una virgola
            " FROM " + oldTableSchema + Constants.DOT + oldTableName;

        // Esegui la query per travasare i dati
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(insertQuery);
            stmt.close();
        }
        
        
        
    }
    
    private static boolean needsEscape(String columnName) {
        return columnName.contains("'"); // Controlla se il nome della colonna contiene un apice
    }
    
}