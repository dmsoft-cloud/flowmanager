package it.dmsoft.flowmanager.agent.engine.core.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.SessionFactoryImpl;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400JDBCConnection;
import com.ibm.as400.access.AS400SecurityException;
import com.ibm.as400.access.ErrorCompletingRequestException;
import com.ibm.as400.access.Job;
import com.ibm.as400.access.ObjectDoesNotExistException;

import it.dmsoft.flowmanager.agent.engine.core.operations.params.GenericConnectionParams;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;
import it.dmsoft.flowmanager.common.domain.Domains.DbType;
import it.dmsoft.flowmanager.common.domain.Domains.YesNo;

public enum HibernateUtils {
	
	DB2_ISERIES(DbType.DB2_ISERIES) {

		@Override
		public SessionFactory getSessionFactory(GenericConnectionParams gcp, String schema) {
			if (db2iseriesSessionFactory == null) {
	        	//Integer port = gcp.getPort() == null ? 3306 : gcp.getPort();
	        	String ssl = (YesNo.YES.equals(gcp.isSecure()) ? Boolean.TRUE : Boolean.FALSE).toString();
	        	String db = StringUtils.isNullOrEmpty(schema) ? "" : "/" + schema;
	        	
	        	String jdbcConnectionString = StringUtils.isNullOrEmpty(gcp.getJdbcCustomString()) 
	        			? "jdbc:as400://" + gcp.getHost() + "/" +  db + ";naming=sql;secure=" + ssl : gcp.getJdbcCustomString();
	        	String jdbcDriver = "com.mysql.cj.jdbc.Driver";
	        	String hibernateDialect = "org.hibernate.dialect.MySQLDialect";
	        	
	        	db2iseriesSessionFactory = createSessionFactory(gcp, jdbcConnectionString, jdbcDriver, hibernateDialect);
	        }
	        return db2iseriesSessionFactory;	
				
		}
		
		@Override
		public Connection getConnection(GenericConnectionParams gcp, String schema) {
			Connection connection = super.getConnection(gcp, schema);
			initialize(gcp, connection);
			return connection;
		}
		
		private void initialize(GenericConnectionParams gcp, Connection connection) {
			try {
				AS400JDBCConnection conn = (AS400JDBCConnection) connection;		
				String currentLibrary = gcp.getCurlib();
				Job job = conn.getSystem().getJobs(AS400.DATABASE)[0];
				//TODO
				
				logger.info("user -> " + job.getUser());
				logger.info("job number -> " + job.getNumber());
				logger.info("currentLibraryExistence -> " + job.getCurrentLibraryExistence());
				
				if (job.getCurrentLibraryExistence() && StringUtils.isNullOrEmpty(currentLibrary)) {
					currentLibrary = job.getCurrentLibrary();
					conn.setSchema(currentLibrary);
					logger.info("currentLibrary -> " +  currentLibrary);
					gcp.setCurlib(currentLibrary);
				}
							
			} catch (AS400SecurityException | ErrorCompletingRequestException | InterruptedException | IOException
					| ObjectDoesNotExistException | SQLException e) {
				logger.error("Error on getting JDBC connection: ", e);
				throw new RuntimeException(e);
			}
		}
		
	},
	
	MYSQL(DbType.MYSQL) {
		
	    public SessionFactory getSessionFactory(GenericConnectionParams gcp, String schema) {
	        if (mysqlSessionFactory == null) {
	        	Integer port = gcp.getPort() == null ? 3306 : gcp.getPort();
	        	String ssl = (YesNo.YES.equals(gcp.isSecure()) ? Boolean.TRUE : Boolean.FALSE).toString();
	        	String db = StringUtils.isNullOrEmpty(schema) ? "" : "/" + schema;
	        	
	        	String jdbcConnectionString = StringUtils.isNullOrEmpty(gcp.getJdbcCustomString()) 
	        			? "jdbc:mysql://" + gcp.getHost() + ":" + port + db + "?useSSL=" + ssl : gcp.getJdbcCustomString();
	        	String jdbcDriver = "com.mysql.cj.jdbc.Driver";
	        	String hibernateDialect = "org.hibernate.dialect.MySQLDialect";
	        	
	        	mysqlSessionFactory = createSessionFactory(gcp, jdbcConnectionString, jdbcDriver, hibernateDialect);
	        }
	        return mysqlSessionFactory;
	    }
		
	},
	
	MSSQLSERVER(DbType.MSSQLSERVER) {
		
	    public SessionFactory getSessionFactory(GenericConnectionParams gcp, String schema) {
	        if (mssqlserverSessionFactory == null) {
	        	Integer port = gcp.getPort() == null ? 1433 : gcp.getPort();
	        	String ssl = (YesNo.YES.equals(gcp.isSecure()) ? Boolean.TRUE : Boolean.FALSE).toString();
	        	String db = StringUtils.isNullOrEmpty(schema) ? "" : ";DatabaseName=" + schema;
	        	String jdbcConnectionString = StringUtils.isNullOrEmpty(gcp.getJdbcCustomString()) 
	        			? "jdbc:sqlserver://" + gcp.getHost() + ":" + port + db + "?encrypt=" + ssl + ";trustServerCertificate=true" : gcp.getJdbcCustomString();
	        	String jdbcDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	        	String hibernateDialect = "org.hibernate.dialect.SQLServerDialect";
	        	
	        	mssqlserverSessionFactory = createSessionFactory(gcp, jdbcConnectionString, jdbcDriver, hibernateDialect);
	        }
	        return mssqlserverSessionFactory;
	    }
		
	};
	
	private static final Logger logger = Logger.getLogger(HibernateUtils.class.getName());
	
	private static SessionFactory mysqlSessionFactory;
	private static SessionFactory mssqlserverSessionFactory;
	private static SessionFactory db2iseriesSessionFactory;
	
	private DbType dbType;
	
	private HibernateUtils(DbType dbType) {
		this.dbType = dbType;
	}
	
	public DbType getDbType() {
		return this.dbType;
	}
	
	private static SessionFactory createSessionFactory(GenericConnectionParams gcp, String jdbcConnectionString, String jdbcDriver, String hibernateDialect) {
		try {
            Configuration configuration = new Configuration();

            // Hibernate settings
            Properties settings = new Properties();
            settings.put("hibernate.connection.driver_class", jdbcDriver);
            settings.put("hibernate.connection.url", jdbcConnectionString);
            settings.put("hibernate.connection.username", gcp.getUser());
            settings.put("hibernate.connection.password", gcp.getPassword());
            settings.put("hibernate.dialect", hibernateDialect);
            settings.put("hibernate.show_sql", "true");
            settings.put("hibernate.hbm2ddl.auto", "update");

            configuration.setProperties(settings);

            StandardServiceRegistryBuilder serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties());

            return configuration.buildSessionFactory(serviceRegistry.build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
		
	}
	
	public abstract SessionFactory getSessionFactory(GenericConnectionParams gcp, String schema);	
	
	public Connection getConnection(GenericConnectionParams gcp, String schema) {
		SessionFactoryImpl sessionFactoryImplementation = (SessionFactoryImpl) getSessionFactory(gcp, schema);
		try {
			return sessionFactoryImplementation.getJdbcServices().getBootstrapJdbcConnectionAccess().obtainConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

}
