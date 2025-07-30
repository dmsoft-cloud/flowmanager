package it.dmsoft.flowmanager.agent.engine.generic.utility.logger;

import java.util.HashMap;

import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.implementation.Logger4j;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.implementation.Logger4j2;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.implementation.LoggerSlf4j;

public class LoggerFactory {
	private static Boolean log4jAvailable = null;
	private static Boolean log4j2Available = null;

	private LoggerFactory() {

	}

	public static LoggerAbstract getInstance(String sizeLog, String maxBackupIndex, String path, String pathApplicationLog, String logLevel, String jobNumber, String pkg, String cliente,
			String applicazione, String logKey, boolean forceOneAppender, boolean enableSingleLog) {
		LoggerAbstract loggerReturn;

		// Se non impostati i parametri fondamentali il programma non crea il
		// logger
		if (sizeLog == null || sizeLog.equals("") || maxBackupIndex == null || maxBackupIndex.equals("") || path == null || path.equals("") || logLevel == null || logLevel.equals("")) {
			return null;
		}

		LogType logType = defLogType(cliente);
		switch (logType) {
		case BSAS:
			loggerReturn = new LoggerSlf4j(sizeLog, maxBackupIndex, path, pathApplicationLog, logLevel, jobNumber, pkg, applicazione, logKey, forceOneAppender, enableSingleLog);
			break;

		case LOG4J2:
			// Aggiunta necessaria per quelle situazioni in cui nel classpath
			// esistono applicativi che usano log4j internamente. In questo modo
			// vengono aggiunti gli appender corretti al logger. Il check su
			// log4j è necessario per poter gestire quelel situazioni (es. DEBK)
			// per cui la classe viene rimossa in fase di build del Generic
			//if (checkLog4jAvailable()) {
			//	new Logger4j(sizeLog, maxBackupIndex, path, pathApplicationLog, logLevel, jobNumber, pkg, logKey, forceOneAppender, enableSingleLog);
			//}
			loggerReturn = new Logger4j2(sizeLog, maxBackupIndex, path, pathApplicationLog, logLevel, jobNumber, pkg, logKey, forceOneAppender, enableSingleLog);
			break;

		case LOG4J1:
		default:
			loggerReturn = new Logger4j2(sizeLog, maxBackupIndex, path, pathApplicationLog, logLevel, jobNumber, pkg, logKey, forceOneAppender, enableSingleLog);
			break;
		}

		HashMap<String, String> messaggio = new HashMap<String, String>(1);
		messaggio.put(Logger.KEY_MESSAGGIO, (new StringBuilder("Utilizzo classe logger: ")).append(logType.name()).toString());
		loggerReturn.log(Logger.LEVEL_INFO, pkg, messaggio);
		return loggerReturn;
	}

	/**
	 * Calcola il tipo di log da usare. Se il logType non viene passato (nel
	 * caso in cui la parte cobol non sia stata ancora adeguata) usa il cliente.
	 * 
	 * @param cliente
	 *            Cliente su cui viene esegui
	 * 
	 * @return
	 */
	private static LogType defLogType(String cliente) {

		if (Constants.DEBK.equals(cliente)) {
			return LogType.LOG4J2;
		} else if (Constants.BSAS.equals(cliente)) {
			return LogType.BSAS;
		}

		if (checkLog4j2Available()) {
			return LogType.LOG4J2;
		}

		return LogType.LOG4J1;

	}

	private static boolean checkClassAvailable(String clazz) {
		try {
			Class.forName(clazz, false, LoggerFactory.class.getClassLoader());
		} catch (LinkageError e) {
			return false;
		} catch (ClassNotFoundException e) {
			return false;
		}
		return true;
	}

	/**
	 * Verifica che nel classpath sia presente log4j2
	 * 
	 * @return
	 */
	private static boolean checkLog4j2Available() {
		if (log4j2Available == null) {
			log4j2Available = checkClassAvailable("org.apache.logging.log4j.core.Logger");
		}
		return log4j2Available;
	}

	/**
	 * Verifica che nel classpath sia presente log4j
	 * 
	 * @return
	 */
	private static boolean checkLog4jAvailable() {
		if (log4jAvailable == null) {
			log4jAvailable = checkClassAvailable("org.apache.log4j.Logger");
		}
		return log4jAvailable;
	}

	/**
	 * Costruttutore di default.
	 * 
	 * @return
	 */
	public static LoggerAbstract getInstanceLog4j() {
		//if (checkLog4j2Available()) {
		return new Logger4j2();
		//} else {
		//	return new Logger4j();
		//}
	}

}
