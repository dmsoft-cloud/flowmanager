package it.dmsoft.flowmanager.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RestController;

@ComponentScan({"it.dmsoft.flowmanager.app.*", "it.dmsoft.flowmanager.api.*", "it.dmsoft.flowmanager.be.*", "it.dmsoft.flowmanager.framework.*"})
@EnableJpaRepositories("it.dmsoft.flowmanager.be.repositories")
@EntityScan("it.dmsoft.flowmanager.be.entities")
@SpringBootApplication
//@SpringBootApplication(scanBasePackages = "it.dmsoft.flowmanager")
@RestController
public class FlowManagerMasterApplication {
	
	private static final Logger log = LoggerFactory.getLogger(FlowManagerMasterApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(FlowManagerMasterApplication.class, args);
	}
}
