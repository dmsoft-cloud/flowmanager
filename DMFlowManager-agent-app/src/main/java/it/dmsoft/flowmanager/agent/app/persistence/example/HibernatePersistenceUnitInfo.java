package it.dmsoft.flowmanager.agent.app.persistence.example;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import jakarta.persistence.SharedCacheMode;
import jakarta.persistence.ValidationMode;
import jakarta.persistence.spi.ClassTransformer;
import jakarta.persistence.spi.PersistenceUnitInfo;
import jakarta.persistence.spi.PersistenceUnitTransactionType;

public class HibernatePersistenceUnitInfo implements PersistenceUnitInfo {
    
    public static String JPA_VERSION = "2.1";
    private String persistenceUnitName;
    private PersistenceUnitTransactionType transactionType
      = PersistenceUnitTransactionType.RESOURCE_LOCAL;
    private List<String> managedClassNames;
    private List<String> mappingFileNames = new ArrayList<>();
    private Properties properties;
    private DataSource jtaDataSource;
    private DataSource nonjtaDataSource;
    private List<ClassTransformer> transformers = new ArrayList<>();
    
    public HibernatePersistenceUnitInfo(
      String persistenceUnitName, List<String> managedClassNames, Properties properties) {
        this.persistenceUnitName = persistenceUnitName;
        this.managedClassNames = managedClassNames;
        this.properties = properties;
    }

	@Override
	public String getPersistenceUnitName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPersistenceProviderClassName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PersistenceUnitTransactionType getTransactionType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataSource getJtaDataSource() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataSource getNonJtaDataSource() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getMappingFileNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<URL> getJarFileUrls() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URL getPersistenceUnitRootUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getManagedClassNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean excludeUnlistedClasses() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SharedCacheMode getSharedCacheMode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ValidationMode getValidationMode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Properties getProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPersistenceXMLSchemaVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClassLoader getClassLoader() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addTransformer(ClassTransformer transformer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ClassLoader getNewTempClassLoader() {
		// TODO Auto-generated method stub
		return null;
	}

    // standard setters / getters   
}
