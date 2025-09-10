package it.dmsoft.flowmanager.master.app;

import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RestController;

import it.dmsoft.flowmanager.common.domain.Domains.ConnectionType;
import it.dmsoft.flowmanager.common.domain.Domains.DbType;
import it.dmsoft.flowmanager.common.domain.Domains.YesNo;
import it.dmsoft.flowmanager.common.model.EmailData;
import it.dmsoft.flowmanager.common.model.FlowData;
import it.dmsoft.flowmanager.common.model.FlowExecutionOutcome;
import it.dmsoft.flowmanager.common.model.FlowLogData;
import it.dmsoft.flowmanager.common.model.FlowLogDetailsData;
import it.dmsoft.flowmanager.common.model.FullFlowData;
import it.dmsoft.flowmanager.common.model.GroupData;
import it.dmsoft.flowmanager.common.model.InterfaceData;
import it.dmsoft.flowmanager.common.model.ModelData;
import it.dmsoft.flowmanager.common.model.OriginData;
import it.dmsoft.flowmanager.common.model.email.RecipientData;
import it.dmsoft.flowmanager.common.websocket.FlowExecutionRequest;

@ComponentScan(basePackages = {"it.dmsoft.flowmanager.master.app.*", "it.dmsoft.flowmanager.master.api.*", "it.dmsoft.flowmanager.be.*", "it.dmsoft.flowmanager.framework.*"})
@EnableJpaRepositories("it.dmsoft.flowmanager.master.repositories")
@EntityScan("it.dmsoft.flowmanager.be.entities")
@RegisterReflectionForBinding({FlowExecutionOutcome.class, FlowLogData.class, FlowLogDetailsData.class, 
	FlowExecutionRequest.class, FullFlowData.class, EmailData.class, RecipientData.class, FlowData.class, 
	GroupData.class, InterfaceData.class, ConnectionType.class, ModelData.class, OriginData.class, DbType.class,  YesNo.class})
@SpringBootApplication
//@SpringBootApplication(scanBasePackages = "it.dmsoft.flowmanager")
@RestController
public class FlowManagerMasterApplication {
	
	//private static final Logger log = LoggerFactory.getLogger(FlowManagerMasterApplication.class);

	public static void main(String[] args) {
		if (args != null) {
			for(String arg : args) {
				System.out.println(arg);
			}
		}
		
		SpringApplication.run(FlowManagerMasterApplication.class, args);
	}
}
