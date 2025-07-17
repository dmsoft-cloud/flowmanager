package it.dmsoft.flowmanager.agent.engine.generic.utility.logger.implementation;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.filter.ThresholdFilter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.rolling.FixedWindowRollingPolicy;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy;
import ch.qos.logback.core.util.FileSize;
import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.Utility;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.LoggerAbstract;

public class LoggerSlf4j extends LoggerAbstract {
	private static final String SEPARATORE = "|";
	private static final String SEPARATORE_CHIAVE_VALORE = "=";

	private static final String LAYOUT_FILE_APPENDER_DEFAULT = "%d{yyyy-MM-dd HH:mm:ss.SSS}"
			+ SEPARATORE
			+ "%p"
			+ SEPARATORE
			+ "applicazione=%X{applicazione}"
			+ SEPARATORE
			+ "Key"
			+ SEPARATORE_CHIAVE_VALORE
			+ "%X{key}"
			+ SEPARATORE
			+ "%m%n";
	private static final String LAYOUT_CONSOLE_APPENDER_DEFAULT = "%d{HH:mm:ss} - %m%n";

	public LoggerSlf4j(String sizeLog, String maxBackupIndex, String path, String pathApplicationLog, String logLevel, String jobNumber, String clazz, String applicazione, String logKey,
			boolean forceOneAppender, boolean enableSingleLog) {
		super();
		// Se clazz è stata passata recupera il logger della classe altrimenti
		// il root logger
		Logger logger = (clazz == null || clazz.trim().equals("")) ? (Logger) LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME) : (Logger) LoggerFactory.getLogger(clazz);

		// Elimina la console appenders presente di default
		Iterator<Appender<ILoggingEvent>> appenders = logger.iteratorForAppenders();
		while (appenders.hasNext()) {
			Appender<ILoggingEvent> app = appenders.next();
			if (forceOneAppender) {
				logger.detachAppender(app);
			} else if (app != null && app.getName() != null && app.getName().equals("console")) {
				logger.detachAppender(app);
			}
		}

		// Se non esistono appender li crea altrimenti imposta la chiave
		if (!logger.iteratorForAppenders().hasNext()) {
			setLogger(sizeLog, maxBackupIndex, path, pathApplicationLog, logLevel, jobNumber, applicazione, clazz, logKey, enableSingleLog);
		} else {
			setKeyApplicazione(logKey, applicazione);
		}
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
	 * @param applicazione
	 *            informazioni per loggatura
	 * @param clazz
	 *            classe sulla quale impostare il logger
	 * @param logKey
	 *            TODO
	 * @param enableSingleLog
	 *            TODO
	 */
	private static void setLogger(String size, String maxBackupIndex, String path, String pathApplicationLog, String level, String jobNumber, String applicazione, String clazz, String logKey,
			boolean enableSingleLog) {
		Logger logger = null;

		// Se non impostati i parametri fondamentali il programma non crea il
		// logger
		if (size == null || size.equals("") || maxBackupIndex == null || maxBackupIndex.equals("") || path == null || path.equals("") || level == null || level.equals("")) {
			return;
		}

		// Se la classe non è stata passata recupera il rootLogger
		if (clazz == null || clazz.trim().equals("")) {
			logger = (Logger) LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
		} else {
			// Spegne il log sul rootLogger se non sono stati impostati degli
			// appenders
			Logger rootLogger = (Logger) LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);

			// Elimina la console appenders presente di default
			Iterator<Appender<ILoggingEvent>> appenders = rootLogger.iteratorForAppenders();
			while (appenders.hasNext()) {
				Appender<ILoggingEvent> app = appenders.next();
				if (app.getName().equals("console")) {
					rootLogger.detachAppender(app);
				}
			}

			if (!rootLogger.iteratorForAppenders().hasNext()) {
				rootLogger.setLevel(Level.OFF);
			}

			logger = (Logger) LoggerFactory.getLogger(clazz);
		}

		// Impostazione della chiave
		setKeyApplicazione(logKey, applicazione);

		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();

		PatternLayoutEncoder ple = new PatternLayoutEncoder();
		ple.setPattern(LAYOUT_FILE_APPENDER_DEFAULT);
		ple.setContext(lc);
		ple.start();

		if (enableSingleLog) {
			FileAppender<ILoggingEvent> fileAppender = new FileAppender<ILoggingEvent>();
			fileAppender.setFile(path);
			fileAppender.setEncoder(ple);
			fileAppender.setContext(lc);
			fileAppender.clearAllFilters();
			ThresholdFilter filter = new ThresholdFilter();
			filter.setLevel(it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger.LEVEL_LOG_SINGOLO);
			filter.start();
			fileAppender.addFilter(filter);
			fileAppender.start();
			logger.addAppender(fileAppender);
		}

		if (it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger.isConsoleLoginActive()) {
			PatternLayoutEncoder pleCons = new PatternLayoutEncoder();
			pleCons.setPattern(LAYOUT_CONSOLE_APPENDER_DEFAULT);
			pleCons.setContext(lc);
			pleCons.start();

			ConsoleAppender<ILoggingEvent> consoleAppender = new ConsoleAppender<ILoggingEvent>();
			consoleAppender.setContext(lc);
			consoleAppender.setName("ConsoleAppender");
			consoleAppender.setEncoder(pleCons);
			consoleAppender.start();

			ThresholdFilter filter = new ThresholdFilter();
			filter.setLevel(Level.DEBUG.toString());
			filter.start();
			consoleAppender.addFilter(filter);
			consoleAppender.start();
			logger.addAppender(consoleAppender);
		}

		RollingFileAppender<ILoggingEvent> fileAppenderRolling = new RollingFileAppender<ILoggingEvent>();
		fileAppenderRolling.setContext(lc);
		fileAppenderRolling.setFile(pathApplicationLog);

		FixedWindowRollingPolicy rollingPolicy = new FixedWindowRollingPolicy();
		rollingPolicy.setContext(lc);
		rollingPolicy.setParent(fileAppenderRolling);
		rollingPolicy.setFileNamePattern(pathApplicationLog + ".%i");
		rollingPolicy.setMinIndex(1);
		rollingPolicy.setMaxIndex(Integer.valueOf(maxBackupIndex));
		rollingPolicy.start();

		SizeBasedTriggeringPolicy<ILoggingEvent> triggeringPolicy = new SizeBasedTriggeringPolicy<ILoggingEvent>();
		triggeringPolicy.setMaxFileSize(FileSize.valueOf(size + "MB"));
		triggeringPolicy.start();

		fileAppenderRolling.setEncoder(ple);
		fileAppenderRolling.setRollingPolicy(rollingPolicy);
		fileAppenderRolling.setTriggeringPolicy(triggeringPolicy);
		fileAppenderRolling.clearAllFilters();
		ThresholdFilter filter = new ThresholdFilter();
		filter.setLevel(level);
		filter.start();
		fileAppenderRolling.addFilter(filter);
		fileAppenderRolling.start();
		logger.addAppender(fileAppenderRolling);

		logger.setAdditive(false); /* set to true if root should log too */

		// Imposta il livello più basso al logger
		Level loggerLevel = Level.toLevel(level);
		if (enableSingleLog) {
			Level logSingoloLevel = (Level.toLevel(it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger.LEVEL_LOG_SINGOLO));
			if (loggerLevel.isGreaterOrEqual(logSingoloLevel)) {
				loggerLevel = logSingoloLevel;
			}
		}
		logger.setLevel(loggerLevel);

	}

	/**
	 * Imposta la chiave del logger
	 * 
	 * @param key
	 * @param applicazione
	 */
	private static void setKeyApplicazione(String key, String applicazione) {
		// Impostazione della chiave
		String keyLocal = (key != null) ? key : "";
		MDC.clear();
		MDC.put("key", keyLocal);
		MDC.put("applicazione", applicazione);
	}

	@Override
	public void log(int level, String clazz, Map<String, String> messages, Throwable e, int levelHeader) {

		StringBuilder message = new StringBuilder();

		// Rimuove il messaggio per aggiungerlo in coda alle eventuali coppie
		// chiave valore
		String messaggio = (messages.get(it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger.KEY_MESSAGGIO));
		messages.remove(it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger.KEY_MESSAGGIO);

		for (Entry<String, String> entry : messages.entrySet()) {
			if (message.length() != 0) {
				message.append(SEPARATORE);
			}
			message.append(entry.getKey()).append(SEPARATORE_CHIAVE_VALORE).append(entry.getValue());
		}
		if (message.length() != 0) {
			message.append(SEPARATORE);
		}
		if (messaggio != null) {
			message.append(it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger.KEY_MESSAGGIO).append(SEPARATORE_CHIAVE_VALORE).append(messaggio);
		}

		Logger logger = (Logger) LoggerFactory.getLogger(clazz);
		String messageString = "";
		if (message.length() > 0) {
			messageString = Utility.maskMessage(message, clazz);
		}
		switch (level) {
		case it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger.LEVEL_DEBUG:
			logger.debug(messageString, e);
			break;
		case it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger.LEVEL_INFO:
			logger.info(messageString, e);
			break;
		case it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger.LEVEL_WARNING:
			logger.warn(messageString, e);
			break;
		case it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger.LEVEL_ERROR:
		case it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger.LEVEL_FATAL:
			logger.error(messageString, e);
			break;
		default:
			break;
		}
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
