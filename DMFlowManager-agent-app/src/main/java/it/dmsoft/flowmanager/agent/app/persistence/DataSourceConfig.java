package it.dmsoft.flowmanager.agent.app.persistence;

import java.io.PrintWriter;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
/*
@Configuration*/
public class DataSourceConfig {
    /*
    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceBuilder.url("jdbc:mysql://127.0.0.1:3306/flowmanager?useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC ");
        dataSourceBuilder.username("SA");
        dataSourceBuilder.password("");
        return dataSourceBuilder.build();
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
    */
}
