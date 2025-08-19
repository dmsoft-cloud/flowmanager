package it.dmsoft.flowmanager.agent.engine.core.persistence.example;

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

import it.dmsoft.flowmanager.agent.engine.core.utils.HibernateUtils;
import it.dmsoft.flowmanager.be.entities.BaseEntity;
import it.dmsoft.flowmanager.be.entities.Email;
import it.dmsoft.flowmanager.be.entities.Recipient;
import it.dmsoft.flowmanager.be.entities.ScheduleDate;
import it.dmsoft.flowmanager.common.engine.FlowConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.spi.PersistenceUnitInfo;

public class JpaEntityManagerFactory {
	private FlowConfig flowConfig;
	private Class<? extends BaseEntity>[] entityClasses;
	
	private static JpaEntityManagerFactory instance;
	
	public static JpaEntityManagerFactory get(FlowConfig flowConfig) {
		if (instance == null) {
			instance = new JpaEntityManagerFactory(
			      new Class[]{Email.class, Recipient.class, ScheduleDate.class}, flowConfig);
		}
		
		return instance;
	}
	
	public JpaEntityManagerFactory(Class<? extends BaseEntity>[] entityClasses, FlowConfig flowConfig) {
		this.flowConfig = flowConfig;
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
		properties.put("hibernate.connection.datasource", getDataSource(null));
		return properties;
	}

	protected Class<? extends BaseEntity>[] getEntities() {
		return entityClasses;
	}

	//TODO to BE IMPLEMENTED
	protected DataSource getDataSource(String jdbcDataSourceClass) {
		//HibernateUtils.getHibernateUtils(jdbcDataSourceClass);
		
		
		MysqlDataSource mysqlDataSource = new MysqlDataSource();
		mysqlDataSource.setURL(flowConfig.getDatasourceUrl());
		mysqlDataSource.setUser(flowConfig.getDatasourceUsername());
		mysqlDataSource.setPassword(flowConfig.getDatasourcePassword());
		return mysqlDataSource;
	}
}
