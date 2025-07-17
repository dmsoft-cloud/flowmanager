package it.dmsoft.flowmanager.agent.engine.core.sqlServer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import it.dmsoft.flowmanager.agent.engine.core.db.dao.DbConstants;
import it.dmsoft.flowmanager.agent.engine.core.properties.PropertiesUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.StringUtils;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class JdbcConnection {
	
	private static final Logger logger = Logger.getLogger(JdbcConnection.class.getName());
	
	private static Connection conn = null;
	
	private static String currentLibrary;
	
	public static Connection get() throws Exception {
		if (conn == null || conn.isClosed()) {
			initialize();
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
	
	public static String getCurrentLibrary() throws Exception {
		if (conn == null) {
			initialize();
		}
		
		return currentLibrary;
	}
	
	
	private static void initialize() throws Exception {
		
		try {
			// Carica le proprietà per la connessione dal file di configurazione
            String dbUrl = DbConstants.DB_HOST;
            String dbUser = DbConstants.USERNAME;
            String dbPassword = DbConstants.PASSWORD;
            if (!StringUtils.isNullOrEmpty(dbUser) && !StringUtils.isNullOrEmpty(dbPassword) && Optional.ofNullable(DbConstants.SECURE_CONNECTION).filter(Constants.SI::equals).isPresent()) {
            // Effettua la connessione al database SQL Server
            dbUrl = dbUrl + Constants.SEMICOLON + "encrypt=true;trustServerCertificate=true";
            } else {
            	dbUrl = dbUrl + Constants.SEMICOLON + "encrypt=false;trustServerCertificate=true";             
            }
            
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
