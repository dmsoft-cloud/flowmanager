package it.dmsoft.flowmanager.agent.engine.core.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
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
	
	DB2_ISERIES(DbType.DB2_ISERIES, "com.ibm.as400.access.AS400JDBCDriver") {

		@Override
		public SessionFactory getSessionFactory(List<Class<?>> entityClasses, GenericConnectionParams gcp, String schema) {
			if (db2iseriesSessionFactory == null) {
	        	//Integer port = gcp.getPort() == null ? 3306 : gcp.getPort();
	        	String ssl = (YesNo.YES.equals(gcp.isSecure()) ? Boolean.TRUE : Boolean.FALSE).toString();
	        	String db = StringUtils.isNullOrEmpty(schema) ? "" : "/" + schema;
	        	
	        	String jdbcConnectionString = StringUtils.isNullOrEmpty(gcp.getJdbcCustomString()) 
	        			? "jdbc:as400://" + gcp.getHost() + "/" +  db + ";naming=sql;secure=" + ssl : gcp.getJdbcCustomString();
	        	String hibernateDialect = "org.hibernate.dialect.DB2400Dialect";
	        	
	        	db2iseriesSessionFactory = createSessionFactory(entityClasses, gcp, jdbcConnectionString, getJdbcClassName(), hibernateDialect);
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
	
	MYSQL(DbType.MYSQL, "com.mysql.cj.jdbc.Driver") {
		
	    public SessionFactory getSessionFactory(List<Class<?>> entityClasses, GenericConnectionParams gcp, String schema) {
	        if (mysqlSessionFactory == null) {
	        	Integer port = gcp.getPort() == null ? 3306 : gcp.getPort();
	        	String ssl = (YesNo.YES.equals(gcp.isSecure()) ? Boolean.TRUE : Boolean.FALSE).toString();
	        	String db = StringUtils.isNullOrEmpty(schema) ? "" : "/" + schema;
	        	
	        	String jdbcConnectionString = StringUtils.isNullOrEmpty(gcp.getJdbcCustomString()) 
	        			? "jdbc:mysql://" + gcp.getHost() + ":" + port + db + "?useSSL=" + ssl : gcp.getJdbcCustomString();
	        	String hibernateDialect = "org.hibernate.dialect.MySQLDialect";
	        	
	        	mysqlSessionFactory = createSessionFactory(entityClasses, gcp, jdbcConnectionString, getJdbcClassName(), hibernateDialect);
	        }
	        return mysqlSessionFactory;
	    }
		
	},
	
	MSSQLSERVER(DbType.MSSQLSERVER, "com.microsoft.sqlserver.jdbc.SQLServerDriver") {
		
	    public SessionFactory getSessionFactory(List<Class<?>> entityClasses, GenericConnectionParams gcp, String schema) {
	        if (mssqlserverSessionFactory == null) {
	        	Integer port = gcp.getPort() == null ? 1433 : gcp.getPort();
	        	String ssl = (YesNo.YES.equals(gcp.isSecure()) ? Boolean.TRUE : Boolean.FALSE).toString();
	        	String db = StringUtils.isNullOrEmpty(schema) ? "" : ";DatabaseName=" + schema;
	        	String jdbcConnectionString = StringUtils.isNullOrEmpty(gcp.getJdbcCustomString()) 
	        			? "jdbc:sqlserver://" + gcp.getHost() + ":" + port + db + "?encrypt=" + ssl + ";trustServerCertificate=true" : gcp.getJdbcCustomString();
	        	String hibernateDialect = "org.hibernate.dialect.SQLServerDialect";
	        	
	        	mssqlserverSessionFactory = createSessionFactory(entityClasses, gcp, jdbcConnectionString, getJdbcClassName(), hibernateDialect);
	        }
	        return mssqlserverSessionFactory;
	    }
		
	};
	
	private static final Logger logger = Logger.getLogger(HibernateUtils.class.getName());
	
	private static SessionFactory mysqlSessionFactory;
	private static SessionFactory mssqlserverSessionFactory;
	private static SessionFactory db2iseriesSessionFactory;
	
	private DbType dbType;
	
	private String jdbcClassName;
	
	private HibernateUtils(DbType dbType, String jdbcClassName) {
		this.dbType = dbType;
		this.jdbcClassName = jdbcClassName;
	}
	
	public DbType getDbType() {
		return this.dbType;
	}
	
	public String getJdbcClassName() {
		return jdbcClassName;
	}
	
	public abstract SessionFactory getSessionFactory(List<Class<?>> entityClasses, GenericConnectionParams gcp, String schema);	
	
	public SessionFactory getSessionFactory(GenericConnectionParams gcp, String schema) {
		return getSessionFactory(null, gcp, schema);
	}
	
	
	private static SessionFactory createSessionFactory(List<Class<?>> entityClasses, GenericConnectionParams gcp, String jdbcConnectionString, String jdbcDriver, String hibernateDialect) {
		try {
            Configuration configuration = new Configuration();
            
            if (entityClasses != null) {
            	for(Class<?> entityClass : entityClasses)
            		configuration.addAnnotatedClass(entityClass);
            }
            
            // Hibernate settings
            Properties settings = new Properties();
            settings.put("hibernate.connection.driver_class", jdbcDriver);
            settings.put("hibernate.connection.url", jdbcConnectionString);
            settings.put("hibernate.connection.username", gcp.getUser());
            settings.put("hibernate.connection.password", gcp.getPassword());
            settings.put("hibernate.dialect", hibernateDialect);
            settings.put("hibernate.show_sql", "true");
            settings.put("hibernate.hbm2ddl.auto", "update");
            settings.put("hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");
            configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
            configuration.setProperties(settings);
            
            StandardServiceRegistryBuilder serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties());

            return configuration.buildSessionFactory(serviceRegistry.build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
		
	}
	
	public Connection getConnection(GenericConnectionParams gcp, String schema) {
		SessionFactoryImpl sessionFactoryImplementation = (SessionFactoryImpl) getSessionFactory(gcp, schema);
		try {
			return sessionFactoryImplementation.getJdbcServices().getBootstrapJdbcConnectionAccess().obtainConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public static HibernateUtils getHibernateUtils(String jdbcClassName) {
		for(HibernateUtils hu : HibernateUtils.values()) {
			if(hu.getJdbcClassName().equals(jdbcClassName)) {
				return hu;
			}
		}
		
		return null;
	}

}
