package it.dmsoft.flowmanager.agent.engine.generic.utility.logger.implementation;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.Priority;
import org.apache.log4j.RollingFileAppender;

import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.Utility;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.LoggerAbstract;

/**
 * Logger personalizzato OCS basato su log4j
 * 
 * @author Torri Luca
 * @version 1.0
 * 
 */
public class Logger4j extends LoggerAbstract {
	private static final String SEPARATORE = "; ";
	private static final String SEPARATORE_CHIAVE_VALORE = ": ";

	private static final String LAYOUT_FILE_APPENDER_DEFAULT = "%d{dd MMM yyyy HH:mm:ss,SSS} - %-5p - [Key: %x] %m%n";
	private static final String LAYOUT_CONSOLE_APPENDER_DEFAULT = "%d{HH:mm:ss} - [Key: %x] %m (%C:%L)%n";

	public Logger4j(String sizeLog, String maxBackupIndex, String path, String pathApplicationLog, String logLevel, String jobNumber, String clazz, String logKey, boolean forceOneAppender,
			boolean enableSingleLog) {
		super();
		// Se clazz è stata passata recupera il logger della classe altrimenti
		// il root logger
		org.apache.log4j.Logger logger = (clazz == null || clazz.trim().equals("")) ? org.apache.log4j.Logger.getRootLogger() : org.apache.log4j.Logger.getLogger(clazz);

		if (forceOneAppender) {
			logger.removeAllAppenders();
		}

		// Se non esistono appender li crea altrimenti imposta la chiave
		if (!logger.getAllAppenders().hasMoreElements()) {
			setLogger(sizeLog, maxBackupIndex, path, pathApplicationLog, logLevel, jobNumber, clazz, false, logKey, enableSingleLog);
		} else {
			setKey(logKey);
		}
	}

	public Logger4j() {

	}

	/**
	 * Imposta la chiave del logger
	 * 
	 * @param key
	 */
	private static void setKey(String key) {
		// Impostazione della chiave
		String keyLocal = (key != null) ? key : "";

		org.apache.log4j.NDC.clear();
		org.apache.log4j.NDC.push(keyLocal);
	}

	/**
	 * Imposta il logger per la classe passata. Se la classe non viene passata,
	 * o è <i>null</i>, viene impostato il rootLogger. Se uno dei valori tra
	 * {@code size}, {@code maxBackupIndex}, {@code path}, {@code level} non è
	 * impostata il metodo non setta il logger.
	 * 
	 * @param size
	 *            dimensione massima del logger in MB
	 * @param maxBackupIndex
	 *            indice massimo per i logger
	 * @param path
	 *            path del file di log
	 * @param pathApplicationLog
	 *            TODO
	 * @param level
	 *            livello di log
	 * @param jobNumber
	 *            chiave indicativa del log
	 * @param clazz
	 *            classe sulla quale impostare il logger
	 * @param sysout
	 *            se <code>true</code> aggiunge un appender per lo standard
	 *            output.
	 * @param logKey
	 *            TODO
	 * @param enableSingleLog
	 *            TODO
	 * @throws Exception
	 *             In caso di errore in fase di generazione dell'appender
	 */
	private static void setLogger(String size, String maxBackupIndex, String path, String pathApplicationLog, String level, String jobNumber, String clazz, boolean sysout, String logKey,
			boolean enableSingleLog) {
		org.apache.log4j.Logger logger = null;

		// Se non impostati i parametri fondamentali il programma non crea il
		// logger
		if (size == null || size.equals("") || maxBackupIndex == null || maxBackupIndex.equals("") || path == null || path.equals("") || level == null || level.equals("")) {
			return;
		}

		// Se la classe non è stata passata recupera il rootLogger
		if (clazz == null || clazz.trim().equals("")) {
			logger = org.apache.log4j.Logger.getRootLogger();
		} else {
			// Spegne il log sul rootLogger se non sono stati impostati degli
			// appenders
			if (!org.apache.log4j.Logger.getRootLogger().getAllAppenders().hasMoreElements()) {
				org.apache.log4j.Logger.getRootLogger().setLevel(Level.OFF);
			}
			logger = org.apache.log4j.Logger.getLogger(clazz);
		}

		// Impostazione della chiave
		setKey(logKey);

		// Se la gestione per un unico log è disabilitata appende solo il file
		// appender
		if (enableSingleLog) {
			FileAppender appenderFile = new FileAppender();
			appenderFile.setEncoding("UTF-8");
			appenderFile.setFile(path);
			appenderFile.setLayout(new PatternLayout(LAYOUT_FILE_APPENDER_DEFAULT));
			appenderFile.activateOptions();
			appenderFile.setThreshold(Level.toLevel(Logger.LEVEL_LOG_SINGOLO));
			logger.addAppender(appenderFile);
		}

		if (Logger.isConsoleLoginActive()) {
			ConsoleAppender console = new ConsoleAppender();
			console.setName("OcsConsoleLogger");
			console.setLayout(new PatternLayout("%d{HH:mm:ss,SSS}  %m%n"));
			console.setThreshold(Level.DEBUG);
			console.activateOptions();
			logger.addAppender(console);
		}

		RollingFileAppender appenderRolling = new RollingFileAppender();
		appenderRolling.setMaxFileSize(Integer.parseInt(size) + "MB");
		appenderRolling.setMaxBackupIndex(Integer.parseInt(maxBackupIndex));
		appenderRolling.setEncoding("UTF-8");
		appenderRolling.setFile(pathApplicationLog);
		appenderRolling.setLayout(new PatternLayout(LAYOUT_FILE_APPENDER_DEFAULT));
		appenderRolling.activateOptions();
		appenderRolling.setThreshold(Level.toLevel(level));
		logger.addAppender(appenderRolling);

		// Aggiunta appenders per standard output
		if (sysout) {
			ConsoleAppender consoleAppender = new ConsoleAppender(new PatternLayout(LAYOUT_CONSOLE_APPENDER_DEFAULT));
			logger.addAppender(consoleAppender);
		}

		// Imposta il livello più basso al logger
		Level loggerLevel = Level.toLevel(level);
		if (enableSingleLog) {
			Level logSingoloLevel = (Level.toLevel(Logger.LEVEL_LOG_SINGOLO));
			if (loggerLevel.isGreaterOrEqual(logSingoloLevel)) {
				loggerLevel = logSingoloLevel;
			}
		}
		logger.setLevel(loggerLevel);

	}

	@Override
	public void log(int level, String clazz, Map<String, String> messages, Throwable e, int levelHeader) {

		Priority priority = null;
		switch (level) {
		case Logger.LEVEL_DEBUG:
			priority = Level.DEBUG;
			break;
		case Logger.LEVEL_INFO:
			priority = Level.INFO;
			break;
		case Logger.LEVEL_WARNING:
			priority = Level.WARN;
			break;
		case Logger.LEVEL_ERROR:
			priority = Level.ERROR;
			break;
		case Logger.LEVEL_FATAL:
			priority = Level.FATAL;
			break;
		default:
			priority = Level.OFF;
			break;
		}

		StringBuilder message = new StringBuilder();

		// Per prima cosa inserisce il messaggio e poi le eventuali coppie
		// chiave valore
		if (messages.get(Logger.KEY_MESSAGGIO) != null) {
			message.append(messages.get(Logger.KEY_MESSAGGIO));
			messages.remove(Logger.KEY_MESSAGGIO);
		}
		for (Entry<String, String> entry : messages.entrySet()) {
			if (message.length() != 0) {
				message.append(SEPARATORE);
			}
			message.append(entry.getKey()).append(SEPARATORE_CHIAVE_VALORE).append(entry.getValue());
		}
		org.apache.log4j.Logger.getLogger(clazz).log(priority, Utility.maskMessage(message, clazz), e);
	}

	@Override
	public void log(int level, Class<?> clazz, Map<String, String> messages, Throwable e, int levelHeader) {
		log(level, clazz.getName(), messages, e, levelHeader);
	}

	@Override
	public void log(int level, String clazz, Map<String, String> messages) {
		log(level, clazz, messages, (Throwable) null, 0);
	}

	@Override
	public void log(int level, Class<?> clazz, Map<String, String> messages) {
		log(level, clazz.getName(), messages);
	}

}
