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

import it.dmsoft.flowmanager.agent.engine.core.db.DbConstants;
import it.dmsoft.flowmanager.agent.engine.core.operations.core.ConstraintDependentOperation;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.DbConversionParam;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.OperationType;
import it.dmsoft.flowmanager.agent.engine.core.utils.ConvertionUtils.EndOfLine;
import it.dmsoft.flowmanager.agent.engine.core.utils.ConvertionUtils.FieldFillType;
import it.dmsoft.flowmanager.agent.engine.core.utils.DatabaseUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.DatabaseUtils.DBTypeEnum;
import it.dmsoft.flowmanager.agent.engine.core.utils.FlowLogUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.StringUtils;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class Table2FileFixed extends ConstraintDependentOperation<DbConversionParam, Boolean>{

	private static final Logger logger = Logger.getLogger(Table2FileFixed.class.getName());
	
	@Override
	public Boolean executeOperation() throws Exception {
		logger.info("start execution of " + Table2FileFixed.class.getName());
		logger.info("parameters: " + parameters.toString());
		FlowLogUtils.startDetail(OperationType.TB_2_FIXED);
		
		//utilizzato per gestire il file in modalità *add/*replace
		boolean appendMode = parameters.getMemberOptionAddReplace().equals("*ADD") ? true : false;
		File file = new File(Constants.PATH_DELIMITER + parameters.getFolderIfs() + Constants.PATH_DELIMITER + parameters.getFileNameIfs());
		if (!file.exists()) {
		    appendMode = false;
		}
		
		//qualora sia stato creato il file temporaneo di export lo devo cancellare
		
		Connection conn = DBTypeEnum.fromString(DbConstants.DB_TYPE).getConnection();
		StringBuilder sb = new StringBuilder();
		
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
		
		//se l'ultima colonna del file si chiama ROWID non la considero tra le colonne da esportare in quanto colonna tecnica LM.
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = DatabaseUtils.getRealColumnsCount(rsmd);
		
		//imposto il charset da utilizzare per convertire il flusso
		Charset cs = StringUtils.getCharset(parameters.getCodepage().intValue());
		
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(Constants.PATH_DELIMITER + parameters.getFolderIfs() + Constants.PATH_DELIMITER + parameters.getFileNameIfs(), appendMode));
		
		String endLine = "";
		byte[] endLineByteEbcdic = null;
		byte[] endLineByte;
		if (!parameters.getRecordDelimiter().equalsIgnoreCase(Constants.FIXED)) {
			endLine = EndOfLine.fromString(parameters.getRecordDelimiter()).getEndLine();
			endLineByteEbcdic = EndOfLine.fromString(parameters.getRecordDelimiter()).getEndLineEbcdic();
		}
		
		//valuto se tenere il fine riga base o quello ebcdic
		if (StringUtils.isCharsetSupported(parameters.getCodepage().intValue())) {
			endLineByte = endLineByteEbcdic;
		} else endLineByte = endLine.getBytes(cs);
		
		//attualmente commento l'esportazione
		/*
		if ((parameters.getColumnName().equals("*SQL") || parameters.getColumnName().equals("*SYS")) && !appendMode ) {
	        for (int i = 1; i <= columnCount; i++) {	        	
	        	bos.write(rsmd.getColumnName(i).getBytes(cs));
	        }
	        bos.write(endLineByte);
	        bos.flush();
		}
		*/
		
		
        // Scrivi i dati, riga per riga
        while (rs.next()) {
            for (int i = 1; i <= columnCount; i++) {
            	//String value = rs.getString(i) != null ? rs.getString(i).trim() : "";
            	String value ;
            	int columnType = rsmd.getColumnType(i);
            	if (columnType == Types.NUMERIC || columnType == Types.DECIMAL) {
            		
            		BigDecimal rawValue = rs.getBigDecimal(i);
            	    boolean isNegative = rawValue != null && rawValue.signum() < 0; // Controlla se è negativo
            	    value = (rawValue != null) ? rawValue.abs().toPlainString() : ""; // Usa il valore assoluto
                    if (Optional.ofNullable(parameters.getDecimalPointer()).filter("*COMMA"::equals).isPresent() ) {
                        value = value.replace('.', ',');
                    }
                    if (Optional.ofNullable(parameters.getDecimalPointer()).filter("*NONE"::equals).isPresent() ) {
                        value = value.replace(".", "");
                    }
                    
                    // Se è negativo, lo riempiamo con uno zero in meno rispetto ai positivi
                    int fieldSize = DatabaseUtils.getCorrectColumnSize(rsmd, i);
                    value = FieldFillType.fromString("*ZERO").executeFieldFill(value, isNegative ? fieldSize - 1 : fieldSize);
                    // Se negativo, aggiungiamo il segno "-" come primo carattere
                    if (isNegative) {
                        value = "-" + value;
                    }
                    
                } else {
                    // Per altri tipi (es. VARCHAR), ottieni la stringa
                    value = rs.getString(i) != null ? rs.getString(i) : "";
                }
            	
            	if ((columnType == Types.CHAR || columnType == Types.VARCHAR) && Optional.ofNullable(parameters.getStringDelimiter()).filter("*DBLQUOTE"::equals).isPresent()) {
            		value = "\"" + value + "\"";
            	}
            	/*
            	value = StringUtils.convertCharset(parameters.getCodepage().intValue(), value);
            	
                writer.write(value); // gestisci null se necessario
                */
            	bos.write(value.getBytes(cs));
            }
            //writer.write(endLine); // Nuova riga dopo ogni record
            if (!parameters.getRecordDelimiter().equalsIgnoreCase(Constants.FIXED)) bos.write(endLineByte);
	        bos.flush();
        }
        
        if (rs != null) rs.close();
        if (stmt != null) stmt.close();
        //if (writer != null) writer.close();
        if (bos != null) bos.close();
	
		
        /*  to do
		sb.append(Constants.SPACE + "ENDLINFMT" + Constants.OPEN_BRACKET + Constants.FIXED
		+ Constants.CLOSE_BRACKET);
		*/
        
        
        /*crea con rwx 
         * 
         */

	
		
		logger.info("end execution of " + Table2FileFixed.class.getName());
		FlowLogUtils.endDetail(OperationType.TB_2_FIXED);
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
