package it.dmsoft.flowmanager.agent.app.persistence;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;

/*
@Configuration
//@PropertySource({ "classpath:persistence-multiple-db.properties" })
@EnableJpaRepositories(basePackages = "it.dmsoft.flowmanager.be.repositories", entityManagerFactoryRef = "userEntityManager", transactionManagerRef = "userTransactionManager")
//@EntityScan("it.dmsoft.flowmanager.be.entities")*/
public class PersistenceUserConfiguration {
	/*@Autowired
	private Environment env;

	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean userEntityManager() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(userDataSource());
		em.setPackagesToScan(new String[] { "it.dmsoft.flowmanager.be.entities" });

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect"/*env.getProperty("hibernate.dialect")*//*);
		em.setJpaPropertyMap(properties);

		return em;
	}

	@Primary
	@Bean
	public DataSource userDataSource() {

		Properties props = new Properties();
        props.setProperty("jdbcUrl", "jdbc:mysql://127.0.0.1:3306/flowmanager?useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
        props.setProperty("dataSource.user", "SA");
        props.setProperty("dataSource.password", "");
        //props.setProperty("dataSource.databaseName", POSTGRESQL_CONTAINER.getDatabaseName());
        props.put("dataSource.logWriter", new PrintWriter(System.out));

        HikariConfig config = new HikariConfig(props);
        config.setInitializationFailTimeout(-1);
        return new HikariDataSource(config);
	}

	@Primary
	@Bean
	public PlatformTransactionManager userTransactionManager() {

		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(userEntityManager().getObject());
		return transactionManager;
	}*/
}