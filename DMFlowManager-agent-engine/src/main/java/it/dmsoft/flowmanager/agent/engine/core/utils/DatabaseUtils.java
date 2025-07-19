package it.dmsoft.flowmanager.agent.engine.core.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import it.dmsoft.flowmanager.agent.be.entities.ColumnMetadata;
import it.dmsoft.flowmanager.agent.engine.core.db.DbConstants;
import it.dmsoft.flowmanager.agent.engine.core.exception.InvalidDBTypeException;
import it.dmsoft.flowmanager.agent.engine.core.properties.PropertiesConstants;
import it.dmsoft.flowmanager.agent.engine.core.properties.PropertiesUtils;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class DatabaseUtils {
	private static final Logger logger = Logger.getLogger(DatabaseUtils.class.getName());

	
	public static void setDBConstants() throws Exception {
		
		String dbPassword; 
		DbConstants.PASSWORD = System.getenv("DB_PASSWORD");

        if (DbConstants.PASSWORD != null) {
            System.out.println("La variabile di ambiente DB_PASSWORD è stata impostata");
        } else {
            System.out.println("La variabile di ambiente DB_PASSWORD non è definita.");
        }
		String schema = PropertiesUtils.get(DbConstants.SCHEMA_KEY);				
		if (!StringUtils.isNullOrEmpty(schema)) {
			DbConstants.SCHEMA = schema + Constants.DOT;
			DbConstants.GF_CURLIB = schema;
		}
		
		DbConstants.DB_HOST = PropertiesUtils.get(PropertiesConstants.DATABASE_URL);
		DbConstants.REMOTE_HOST = PropertiesUtils.get(PropertiesConstants.REMOTE_HOST);
		DbConstants.DB_TYPE = PropertiesUtils.get(PropertiesConstants.DATABASE_TYPE);
		DbConstants.SECURE_CONNECTION = PropertiesUtils.get(PropertiesConstants.SECURE_CONNECTION);
		DbConstants.USERNAME= PropertiesUtils.get(Constants.USER);
		dbPassword = PropertiesUtils.get(Constants.PASSWORD);
		if(!StringUtils.isNullOrEmpty(dbPassword)) DbConstants.PASSWORD=dbPassword;
		
		//controllo valorizzazioni se non sono in esecuzione locale su as400
		if(Optional.ofNullable(DbConstants.REMOTE_HOST).filter(Constants.SI::equals).isPresent()) {		
			//verifico che sia un valore previsto altrimenti ritorno errore
			Optional.ofNullable(DBTypeEnum.fromString(DbConstants.DB_TYPE)).orElseThrow(() -> new InvalidDBTypeException("DB type not allowed!!!!!!"));
			if (StringUtils.isNullOrEmpty(DbConstants.DB_HOST)) throw new InvalidDBTypeException("host not provided!!!!!!"); 
			switch (DBTypeEnum.fromString(DbConstants.DB_TYPE)) {
				case SQLServer:
					DbConstants.HIBERNATE_DIALECT = "org.hibernate.dialect.SQLServerDialect";
					break;
				/*
				case MySql:
					DbConstants.HIBERNATE_DIALECT = "org.hibernate.dialect.MySQLDialect";
					break;
				case Postgres:
					DbConstants.HIBERNATE_DIALECT = "org.hibernate.dialect.PostgreSQLDialect";
					break;
				*/
				case DB2:
					DbConstants.HIBERNATE_DIALECT = "org.hibernate.dialect.DB2400Dialect";
	                break;
			}
		}
	}
	
	//enum per i tipi di db ammessi
	public enum DBTypeEnum {
	    DB2("db2"){
	    	@Override
			public String escapeColumnName(String columnName) {
	    		return "\"" + columnName.replace("\"", "\"\"") + "\"";
			}
	    	
	    	@Override
	    	public String getQueryCheckObj() {
	    		return "SELECT count(*) from QSYS2.SYSTABLES WHERE TABLE_SCHEMA = ? and TABLE_NAME = ? "; 
	    	}

			@Override
			public Connection getConnection() throws Exception {
				return it.dmsoft.flowmanager.agent.engine.core.as400.JdbcConnection.get();
			}

			@Override
			public String getQueryCreateEmptyTable(String fileds, String newSchema, String newTable, String oldSchema, String oldTable) {
				String createQuery = "CREATE TABLE " + newSchema + Constants.DOT + newTable + " AS ( SELECT ";
				String strFrom =	" FROM " + oldSchema + Constants.DOT + oldTable + " WHERE 1=0 ) " + " WITH NO DATA ";
				return createQuery + fileds + strFrom;
			}
	    	
			
	    	
	    }
	    ,
	    SQLServer("SqlServer"){
	    	@Override
			public String escapeColumnName(String columnName) {
				return "[" + columnName.replace("]", "]]") + "]"; 
			}

			@Override
	    	public String getQueryCheckObj() {
	    		return "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ? "; 
	    	}

			@Override
			public Connection getConnection() throws Exception {
				return it.dmsoft.flowmanager.agent.engine.core.sqlServer.JdbcConnection.get();
			}

			@Override
			public String getQueryCreateEmptyTable(String fileds, String newSchema, String newTable, String oldSchema, String oldTable) {
				String createQuery = "SELECT TOP 0  ";
				String strFrom = "INTO " + newSchema + Constants.DOT + newTable + " FROM "
									+ oldSchema + Constants.DOT + oldTable + " WHERE 1 = 0 ";
				
				return createQuery + fileds + strFrom;
			}
			
			
	    	
			
	    	
	    };
	    
	    private String dbTypeName;

	    DBTypeEnum(String dbTypeName) {
	        this.dbTypeName = dbTypeName;
	    }

	    public String getDbTypeName() {
	        return dbTypeName;
	    }

	    // Metodo per cercare un enum a partire dal valore stringa
	    public static DBTypeEnum fromString(String dbTypeName) {
	        for (DBTypeEnum dbType : DBTypeEnum.values()) {
	            if (dbType.dbTypeName.equalsIgnoreCase(dbTypeName)) {
	                return dbType;
	            }
	        }
	        // Se non viene trovato alcun valore corrispondente, restituisce null
	        return null;
	    }
	    
	    //ritorna query per la verifica dell'esistenza dell'oggetto
	    public abstract String getQueryCheckObj();
	    
	    //ritorna query per la verifica dell'esistenza dell'oggetto
	    public abstract Connection getConnection() throws Exception;
	    
	  //ritorna query per la creazione di una tabella vuota
	    public abstract String getQueryCreateEmptyTable(String fileds, String newSchema, String newTable, String oldSchema, String oldTable);	
	    
	    public abstract String escapeColumnName(String columnName);
	    
	    

	}
	
	 // Metodo per ottenere la dimensione corretta della colonna, specialmente per i numerici
    public static int getCorrectColumnSize(ResultSetMetaData rsmd, int columnIndex) throws SQLException {
        int columnType = rsmd.getColumnType(columnIndex);

        switch (columnType) {
            // Se il tipo è NUMERIC o DECIMAL, calcoliamo la lunghezza dalla precisione
            case Types.NUMERIC:
            case Types.DECIMAL:
                int precision = rsmd.getPrecision(columnIndex); // Precisione del numero
                int scale = rsmd.getScale(columnIndex);         // Scala (decimali)
                
                //return precision - scale;                       // Restituisci solo la parte intera
                return precision;
            // Per altri tipi di dati (es. VARCHAR), restituiamo la lunghezza visualizzata
            default:
                return rsmd.getColumnDisplaySize(columnIndex);
        }
    }
	
	
    
    //metodo per verificare la presenza di una tabella
	public static boolean checkTableExists(Connection conn, String tableName, String tableSchema) throws SQLException {
        String checkTableQuery = DBTypeEnum.fromString(DbConstants.DB_TYPE).getQueryCheckObj();

        
        try (PreparedStatement pstmt = conn.prepareStatement(checkTableQuery)) {
            pstmt.setString(1, tableSchema);
            pstmt.setString(2, tableName);
            try (ResultSet rs = pstmt.executeQuery()) {
            	rs.next();
                return rs.getInt(1)> 0 ? true : false;  
            }
        }
    }
	
	
	public static List<ColumnMetadata> getTableColumns(Connection conn, String tableSchema , String tableName) throws SQLException {
        List<ColumnMetadata> columns = new ArrayList<>();

        // Usa una query per ottenere i metadati della tabella
        String query = "SELECT * FROM " + tableSchema + Constants.DOT + tableName + " WHERE 1 = 0";
        
        PreparedStatement pstmt = conn.prepareStatement(query); 
        ResultSet rs = pstmt.executeQuery();
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = getRealColumnsCount(metaData);
        
        for (int i = 1; i <= columnCount; i++) {
        	ColumnMetadata cm = new ColumnMetadata();
        	cm.setName(metaData.getColumnName(i));
        	cm.setLable(metaData.getColumnLabel(i));
        	cm.setTypeName(metaData.getColumnTypeName(i));
        	cm.setPrecision(metaData.getPrecision(i));
        	cm.setScale(metaData.getScale(i));
        	
            columns.add(cm);
        }
        
        return columns;
    }
	
	public static List<String> getTableColumnsName(Connection conn, String tableSchema , String tableName) throws SQLException {
        List<String> columns = new ArrayList<>();

        DatabaseMetaData metaData = conn.getMetaData();
        try (ResultSet rs = metaData.getColumns(null, tableSchema, tableName, null)) {
            while (rs.next()) {
                columns.add(rs.getString("COLUMN_NAME"));
            }
        }
        return columns;
    }

	 public static void clearFile(Connection conn, String schema, String table ) throws SQLException {
		 String sql = "DELETE FROM " + schema + Constants.DOT +  table;
		 PreparedStatement preparedStatement = conn.prepareStatement(sql);
		 int rowsAffected = preparedStatement.executeUpdate();
		 //logger.info("Deleted  " + rowsAffected + " record!! ");
		 preparedStatement.close();
	 }
	 
	 public static String buildInsertSQL(List<ColumnMetadata> columns, String schema, String table) {
	        StringBuilder insertSQL = new StringBuilder("INSERT INTO " + schema + Constants.DOT + table + " (");
	        int realNumColumns=0;
	        for (ColumnMetadata column : columns) {
	            insertSQL.append(column.getName()).append(", ");
	            realNumColumns++;
	        }
	        insertSQL.setLength(insertSQL.length() - 2); // Rimuovi ultima virgola
	        insertSQL.append(") VALUES (");
	        for (int i = 0; i < columns.size(); i++) {
	            insertSQL.append("?, ");
	        }
	        insertSQL.setLength(insertSQL.length() - 2); // Rimuovi ultima virgola
	        insertSQL.append(")");
	        return insertSQL.toString();
	 }
	 
	 
	 public static int getRealColumnsCount(ResultSetMetaData rsmd) throws SQLException {
			int columnCount = rsmd.getColumnCount();
			
			for (int i = 1; i <= columnCount; i++) {
		        String columnName = rsmd.getColumnName(i);
		        if (Constants.ROWID.equalsIgnoreCase(columnName)) {
		            return i - 1; // Escludi ROWID e tutte quelle dopo
		        }
		    }

		    return columnCount; // Se ROWID non è presente
	}


}
