package it.dmsoft.flowmanager.agent.engine.core.operations;

import it.dmsoft.flowmanager.agent.engine.core.operations.core.Operation;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.SendMailParam;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.OperationType;
import it.dmsoft.flowmanager.agent.engine.core.utils.LogDb;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;
import it.dmsoft.flowmanager.agent.engine.mailclient.SendMailManager;

public class SendMail extends Operation<SendMailParam> {

	private static Logger logger = Logger.getLogger(SendMail.class);

	@Override
	public void execute() throws Exception {

		logger.info("start execution of " + SendMail.class.getName());
		logger.info("parameters: " + parameters);
		LogDb.start(OperationType.SND_MAIL);

		SendMailManager.exposedRun(null, Logger.getLogger(SendMail.class), parameters.getHostName(),
				parameters.getPort().intValue(), parameters.isSecure(), parameters.getSmtpUsername(),
				parameters.getSmtpPassword(), parameters.getFrom(), parameters.getTos(), parameters.getCcs(),
				parameters.getBccs(), parameters.getSubject(), parameters.getAllegati(),
				parameters.getTimeout().intValue(), parameters.getBody(), parameters.isTestoHtml());

		logger.info("end execution of " + SendMail.class.getName());
		LogDb.end(OperationType.SND_MAIL);
	}

}
