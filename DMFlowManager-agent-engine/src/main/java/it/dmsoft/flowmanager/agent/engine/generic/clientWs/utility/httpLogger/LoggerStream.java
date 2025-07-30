package it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.httpLogger;

import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;
//import org.apache.log4j.Level;
/**
 * La classe è stata mantenuta, anche se copia di
 * {@link it.ocsnet.utility.logger.LoggerStream} perhcè già usata in alcuni
 * applicativi open e qui è stata centralizzata
 * 
 * @author lucat
 *
 */
public class LoggerStream extends it.dmsoft.flowmanager.agent.engine.generic.utility.logger.LoggerStream {

	public LoggerStream(Logger logger, int logLevel) {
		super(logger, logLevel);
	}

	/**
	 * @deprecated
	 */
	/*@Deprecated
	public LoggerStream(org.apache.log4j.Logger logger, Level logLevel) {
		this(Logger.log4j2Logger(logger), Logger.level4j2LevelOcs(logLevel));
	}*/

}