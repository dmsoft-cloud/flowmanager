package it.dmsoft.flowmanager.agent.engine.core.sqlServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import it.dmsoft.flowmanager.agent.engine.core.operations.params.GenericConnectionParams;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.StringUtils;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;
import it.dmsoft.flowmanager.common.domain.Domains.YesNo;

@Deprecated
public class JdbcConnection {
	
	private static final Logger logger = Logger.getLogger(JdbcConnection.class.getName());
	
	private static Connection conn = null;
	
	//private static String currentLibrary;
	
	public static Connection get(GenericConnectionParams genericConnectionParams) throws Exception {
		if (conn == null || conn.isClosed()) {
			initialize(genericConnectionParams);
		}
		
		return conn;
	}
	
	public static void close() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.error("Error on closing connection: ", e);
			}
		}
		
	}
	
	/*
	public static String getCurrentLibrary() throws Exception {
		if (conn == null) {
			initialize();
		}
		
		return currentLibrary;
	}
	*/
	
	
	private static void initialize(GenericConnectionParams genericConnectionParams) throws Exception {
		
		try {
			// Carica le proprietà per la connessione dal file di configurazione
			
			
            String dbUrl = genericConnectionParams.getHost();
            String dbUser = genericConnectionParams.getUser();
            String dbPassword = genericConnectionParams.getPassword();
            
            if (!StringUtils.isNullOrEmpty(dbUser) && !StringUtils.isNullOrEmpty(dbPassword) && YesNo.YES.equals(genericConnectionParams.isSecure())) {
            	// Effettua la connessione al database SQL Server
            	dbUrl = dbUrl + Constants.SEMICOLON + "encrypt=true;trustServerCertificate=true";
            } else {
            	dbUrl = dbUrl + Constants.SEMICOLON + "encrypt=false;trustServerCertificate=true";             
            }
            
            //OVERRIDE JDBC CONNECTION STRING
            if (StringUtils.isNullOrEmpty(genericConnectionParams.getJdbcCustomString()))
            	dbUrl = genericConnectionParams.getJdbcCustomString();
            
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

            logger.info("Successfully connected to the database.");
            
            //controllo db di connessione
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DB_NAME() AS CurrentDatabase");
            /*
            if (rs.next()) {
                String databaseName = rs.getString("CurrentDatabase");
                logger.info("Connected to database: " + databaseName);
            }
            
            rs = stmt.executeQuery("SELECT SCHEMA_NAME()");
            if (rs.next()) {
                String schemaName = rs.getString(1);
                logger.info("Default schema: " + schemaName);
            }
			*/
            
		} catch (SQLException e) {
			logger.error("Error on getting JDBC connection: ", e);
		}
	}
	
}
