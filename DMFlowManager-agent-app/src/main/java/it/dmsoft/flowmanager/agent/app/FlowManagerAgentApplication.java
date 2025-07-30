package it.dmsoft.flowmanager.agent.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RestController;

@ComponentScan({"it.dmsoft.flowmanager.agent.app.*", "it.dmsoft.flowmanager.agent.api.*", "it.dmsoft.flowmanager.agent.engine.*", "it.dmsoft.flowmanager.be.*", "it.dmsoft.flowmanager.framework.*"})
@EnableJpaRepositories("it.dmsoft.flowmanager.be.repositories")
@EntityScan("it.dmsoft.flowmanager.be.entities")
@SpringBootApplication
//@SpringBootApplication(scanBasePackages = "it.dmsoft.flowmanager")
@RestController
public class FlowManagerAgentApplication {
	
	private static final Logger logger = LogManager.getLogger(FlowManagerAgentApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(FlowManagerAgentApplication.class, args);
	}
}
