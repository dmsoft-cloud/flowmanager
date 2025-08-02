package it.dmsoft.flowmanager.agent.engine.core.operations.remote;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.util.Optional;

import it.dmsoft.flowmanager.agent.engine.core.operations.core.ConstraintDependentOperation;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.DbConversionParam;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.OperationType;
import it.dmsoft.flowmanager.agent.engine.core.utils.ConvertionUtils.EndOfLine;
import it.dmsoft.flowmanager.agent.engine.core.utils.ConvertionUtils.FieldFillType;
import it.dmsoft.flowmanager.agent.engine.core.utils.ConvertionUtils.RmvBlank;
import it.dmsoft.flowmanager.agent.engine.core.utils.DatabaseUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.DatabaseUtils.DBTypeEnum;
import it.dmsoft.flowmanager.agent.engine.core.utils.FlowLogUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.StringUtils;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class Table2File extends ConstraintDependentOperation<DbConversionParam, Boolean>{
	
	private static final Logger logger = Logger.getLogger(Table2File.class.getName());
	
	@Override
	public Boolean executeOperation() throws Exception {
		
		logger.info("start execution of " + Table2File.class.getName());
		logger.info("parameters: " + parameters.toString());
		FlowLogUtils.startDetail(OperationType.TB_2_FILE);

		
		//utilizzato per gestire il file in modalità *add/*replace
		boolean appendMode = parameters.getMemberOptionAddReplace().equals("*ADD") ? true : false;
        File file = new File(Constants.PATH_DELIMITER + parameters.getFolderIfs() + Constants.PATH_DELIMITER + parameters.getFileNameIfs());
        if (!file.exists()) {
            appendMode = false;
        }
		
		
		Connection conn = DBTypeEnum.get(parameters.getDbType()).getConnection(parameters);
		StringBuilder sb = new StringBuilder();
		
		//imposto l'eventuale verifica sul file di export sul file temporaneo se previsto
		if(!StringUtils.isNullOrEmpty(operationParams.getExportTempTable())) {
			sb.append("SELECT * FROM " + operationParams.getExportTempSchema() + Constants.DOT + operationParams.getExportTempTable() );
		} else {
			sb.append("SELECT * FROM " + parameters.getLibrary() + Constants.DOT + parameters.getFile() );
		}
		PreparedStatement  stmt = conn.prepareStatement(sb.toString());
		
		//imposto la dimensione del fecth per il buffer di lettura		
		BigDecimal fetchSize = Optional.ofNullable(executionFlowData.getFlowFetchSize())
                .orElse(BigDecimal.valueOf(1000));
		stmt.setFetchSize(fetchSize.intValue());
		
		ResultSet rs = stmt.executeQuery();
		
		ResultSetMetaData rsmd = rs.getMetaData();
		//int columnCount = rsmd.getColumnCount();
		int columnCount = DatabaseUtils.getRealColumnsCount(rsmd);
		
		//controllo se l'ultima colonna è ROWID per LM e in tale caso diminuisco il contatore
		/*if (columnCount > 0 && "ROWID".equalsIgnoreCase(rsmd.getColumnName(columnCount))) {
		    columnCount--;  
		}*/
		Charset cs = StringUtils.getCharset(parameters.getCodepage().intValue());
		
		// Scrittura su file con buffering
		/*
		BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.PATH_DELIMITER + parameters.getFolderIfs() + Constants.PATH_DELIMITER + parameters.getFileNameIfs(), cs , appendMode));
		*/
		//uso un buffer di byte
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(Constants.PATH_DELIMITER + parameters.getFolderIfs() + Constants.PATH_DELIMITER + parameters.getFileNameIfs(), appendMode));

		
		
		//dichiaro il fine riga da usare e il separatore
		/*String endLine = StringUtils.convertCharset(parameters.getCodepage().intValue(), EndOfLine.fromString(parameters.getRecordDelimiter()).getEndLine());
		String fieldDelimiter = StringUtils.convertCharset(parameters.getCodepage().intValue(), parameters.getFieldDelimiter());
		*/
		String endLine = EndOfLine.fromString(parameters.getRecordDelimiter()).getEndLine();
		byte[] endLineByteEbcdic = EndOfLine.fromString(parameters.getRecordDelimiter()).getEndLineEbcdic();
		byte[] endLineByte;
		
		//valuto se tenere il fine riga base o quello ebcdic
		if (StringUtils.isCharsetSupported(parameters.getCodepage().intValue())) {
			endLineByte = endLineByteEbcdic;
		} else endLineByte = endLine.getBytes(cs);
		
		String fieldDelimiter = parameters.getFieldDelimiter();
		
		// Scrivi l'intestazione (nomi delle colonne)
		if ((parameters.getColumnName().equals("*SQL") || parameters.getColumnName().equals("*SYS") 
					|| parameters.getColumnName().equals(Constants.EXF) ) && !appendMode ) {
	        for (int i = 1; i <= columnCount; i++) {
	        	/*String rst = StringUtils.convertCharset(parameters.getCodepage().intValue(), rsmd.getColumnName(i));
	        	System.out.println("Stringa pre write" + rst);
	        	rst.chars()  // Ottieni lo stream di interi (valori UTF-16 dei caratteri)
	            .forEach(c -> System.out.printf("%02X ", c));
	            System.out.println("\r\n");
	            
	            writer.write(rsmd.getColumnName(i));
	            */
	        	bos.write(rsmd.getColumnName(i).getBytes(cs));
	            if (i < columnCount) {
	                //writer.write(fieldDelimiter);
	            	bos.write(fieldDelimiter.getBytes(cs));
	            }
	        }
	        bos.write(endLineByte);
	        bos.flush();
		}

        
        // Scrivi i dati, riga per riga
        while (rs.next()) {
            for (int i = 1; i <= columnCount; i++) {
            	//String value = rs.getString(i) != null ? rs.getString(i).trim() : "";
            	String value ;
            	int columnType = rsmd.getColumnType(i);
         
            	if (columnType == Types.NUMERIC || columnType == Types.DECIMAL) {
                    // Ottieni il valore numerico come stringa
                    //value = rs.getBigDecimal(i) != null ? rs.getBigDecimal(i).toPlainString() : "";
            		BigDecimal rawValue = rs.getBigDecimal(i);
            	    boolean isNegative = rawValue != null && rawValue.signum() < 0; // Controlla se è negativo
            	    value = (rawValue != null) ? rawValue.abs().toPlainString() : ""; // Usa il valore assoluto                    
            	    
                    if (Optional.ofNullable(parameters.getDecimalPointer()).filter("*COMMA"::equals).isPresent() ) {
                        value = value.replace('.', ',');
                    }
                    if (Optional.ofNullable(parameters.getDecimalPointer()).filter("*NONE"::equals).isPresent() ) {
                        value = value.replace(".", "");
                    }
                    if(!StringUtils.isNullOrEmpty(parameters.getFieldFilling())) {
                    	int fieldSize = DatabaseUtils.getCorrectColumnSize(rsmd, i);
                    	value = FieldFillType.fromString(parameters.getFieldFilling()).executeFieldFill(value, isNegative ? fieldSize - 1 : fieldSize);
                    	//old version senza gestione segno
                    	//value = FieldFillType.fromString(parameters.getFieldFilling()).executeFieldFill(value, rsmd.getColumnDisplaySize(i));
                    }
                    if (isNegative) {
                        value = "-" + value;
                    }
                } else {
                    value = rs.getString(i) != null ? rs.getString(i) : "";
                }
            	int columnSize = rsmd.getColumnDisplaySize(i);
            	//int columnSize = DatabaseUtils.getCorrectColumnSize(rsmd, i);
            	if (!StringUtils.isNullOrEmpty(parameters.getRemoveBlanks()) && 
            			!Optional.ofNullable(parameters.getRemoveBlanks()).filter("*EOR"::equals).isPresent()) {
            	    value = RmvBlank.fromString(parameters.getRemoveBlanks()).executeRmvBlank(value, columnSize);
            	}
            	if ((columnType == Types.CHAR || columnType == Types.VARCHAR) && Optional.ofNullable(parameters.getStringDelimiter()).filter("*DBLQUOTE"::equals).isPresent()) {
            		value = "\"" + value + "\"";
            	}
            	/*
            	value = StringUtils.convertCharset(parameters.getCodepage().intValue(), value);
            	
                writer.write(value); // gestisci null se necessario
                */
            	
            	//non scrivo il campo se a stringa è vuota ed è impostato a S il campo FANA_CHAR_EMPTY_SPACE
            	if (!((columnType == Types.CHAR || columnType == Types.VARCHAR)
            			&& Optional.ofNullable(executionFlowData.getFlowCharEmptySpace()).filter("S"::equals).isPresent()
            			&& "".equals(value.trim())
            			)) {
            		bos.write(value.getBytes(cs));            		
            	}
                if (i < columnCount) {
                    //writer.write(fieldDelimiter);
                	bos.write(fieldDelimiter.getBytes(cs));
                }
            }
            //writer.write(endLine); // Nuova riga dopo ogni record
	        bos.write(endLineByte);
	        bos.flush();
        }
        
        if (rs != null) rs.close();
        if (stmt != null) stmt.close();
        //if (writer != null) writer.close();
        if (bos != null) bos.close();
		
			
		
		logger.info("end execution of " + Table2File.class.getName());
		FlowLogUtils.endDetail(OperationType.TB_2_FILE);
		
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
