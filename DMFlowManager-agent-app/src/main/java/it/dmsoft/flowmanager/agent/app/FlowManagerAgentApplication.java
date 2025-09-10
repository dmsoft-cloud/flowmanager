package it.dmsoft.flowmanager.agent.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RestController;

//import it.dmsoft.flowmanager.agent.app.FlowManagerAgentApplication.ApplicationHints;
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

@ComponentScan({"it.dmsoft.flowmanager.agent.app.*", "it.dmsoft.flowmanager.agent.api.*", "it.dmsoft.flowmanager.agent.engine.*", "it.dmsoft.flowmanager.be.*", "it.dmsoft.flowmanager.framework.*"})
//@EnableJpaRepositories("it.dmsoft.flowmanager.be.repositories")
//@EntityScan("it.dmsoft.flowmanager.be.entities")
@RegisterReflectionForBinding({FlowExecutionOutcome.class, FlowLogData.class, FlowLogDetailsData.class, 
	FlowExecutionRequest.class, FullFlowData.class, EmailData.class, RecipientData.class, FlowData.class, 
	GroupData.class, InterfaceData.class, ConnectionType.class, ModelData.class, OriginData.class, DbType.class,  YesNo.class})
//@ImportRuntimeHints(ApplicationHints.class)
@SpringBootApplication
//@SpringBootApplication(scanBasePackages = "it.dmsoft.flowmanager")
@RestController
public class FlowManagerAgentApplication {
	
	private static final Logger logger = LogManager.getLogger(FlowManagerAgentApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(FlowManagerAgentApplication.class, args);
	}
/*	
	static class ApplicationHints implements RuntimeHintsRegistrar {
        public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
            hints.reflection().registerType(AbstractPersistable.class, MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS);
        }
    }
*/
}
