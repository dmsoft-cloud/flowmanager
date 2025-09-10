package it.dmsoft.flowmanager.agent.app.persistence.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;

import com.mysql.cj.jdbc.MysqlDataSource;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.spi.PersistenceUnitInfo;

public class JpaEntityManagerFactory {
	private String DB_URL = "jdbc:mysql://databaseurl";
	private String DB_USER_NAME = "username";
	private String DB_PASSWORD = "password";
	private Class[] entityClasses;

	public JpaEntityManagerFactory(Class[] entityClasses) {
		this.entityClasses = entityClasses;
	}

	public EntityManager getEntityManager() {
		return getEntityManagerFactory().createEntityManager();
	}

	protected EntityManagerFactory getEntityManagerFactory() {
		PersistenceUnitInfo persistenceUnitInfo = getPersistenceUnitInfo(getClass().getSimpleName());
		Map<String, Object> configuration = new HashMap<>();
		return new EntityManagerFactoryBuilderImpl(new PersistenceUnitInfoDescriptor(persistenceUnitInfo),
				configuration).build();
	}

	protected HibernatePersistenceUnitInfo getPersistenceUnitInfo(String name) {
		return new HibernatePersistenceUnitInfo(name, getEntityClassNames(), getProperties());
	}

	protected List<String> getEntityClassNames() {
		return Arrays.asList(getEntities()).stream().map(Class::getName).collect(Collectors.toList());
	}

	protected Properties getProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		properties.put("hibernate.id.new_generator_mappings", false);
		properties.put("hibernate.connection.datasource", getMysqlDataSource());
		return properties;
	}

	protected Class[] getEntities() {
		return entityClasses;
	}

	protected DataSource getMysqlDataSource() {
		MysqlDataSource mysqlDataSource = new MysqlDataSource();
		mysqlDataSource.setURL(DB_URL);
		mysqlDataSource.setUser(DB_USER_NAME);
		mysqlDataSource.setPassword(DB_PASSWORD);
		return mysqlDataSource;
	}
}
