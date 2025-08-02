package it.dmsoft.flowmanager.agent.engine.core.as400;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400JDBCConnection;
import com.ibm.as400.access.AS400JDBCDriver;
import com.ibm.as400.access.AS400SecurityException;
import com.ibm.as400.access.ErrorCompletingRequestException;
import com.ibm.as400.access.Job;
import com.ibm.as400.access.ObjectDoesNotExistException;
import com.ibm.as400.access.SecureAS400;

import it.dmsoft.flowmanager.agent.engine.core.operations.params.GenericConnectionParams;
import it.dmsoft.flowmanager.agent.engine.core.properties.PropertiesUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

@Deprecated
public class JdbcConnection {
	
	private static final Logger logger = Logger.getLogger(JdbcConnection.class.getName());
	
	private static Connection conn = null;
	
	private static AS400 as400;
	
	private static String currentLibrary;
	
	public static Connection get(GenericConnectionParams genericConnectionParams) throws Exception {
		if (conn == null) {
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
		
		if (as400 != null) {
			as400.disconnectAllServices();
		}
	}
	
	public static String getCurrentLibrary(GenericConnectionParams genericConnectionParams) throws Exception {
		if (conn == null) {
			initialize(genericConnectionParams);
		}
		
		return currentLibrary;
	}
	
	private static void initialize(GenericConnectionParams genericConnectionParams) throws Exception {
		AS400JDBCDriver a = new AS400JDBCDriver();
		logger.info("driver -> " + a);
		//AS400 as400 = new AS400();
		
		as400 = CallAs400.get(genericConnectionParams).getAs400();
		logger.info("as -> " + as400);			
		try {
			AS400JDBCConnection conn = (AS400JDBCConnection) a.connect(as400);		
			Job job = conn.getSystem().getJobs(AS400.DATABASE)[0];
			//TODO
			
			logger.info("user -> " + job.getUser());
			logger.info("job number -> " + job.getNumber());
			logger.info("currentLibraryExistence -> " + job.getCurrentLibraryExistence());
			
			if (job.getCurrentLibraryExistence()) {
				currentLibrary = job.getCurrentLibrary();
				conn.setSchema(currentLibrary);
				logger.info("currentLibrary -> " +  currentLibrary);
			}
						
			//Thread.sleep(1000000);
			JdbcConnection.conn = conn;
		} catch (AS400SecurityException | ErrorCompletingRequestException | InterruptedException | IOException
				| ObjectDoesNotExistException | SQLException e) {
			logger.error("Error on getting JDBC connection: ", e);
		}
	}
	
}
