package it.dmsoft.flowmanager.agent.engine.generic.utility.logger.implementation;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.appender.rolling.DefaultRolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.SizeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.config.AbstractConfiguration;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.filter.ThresholdFilter;
import org.apache.logging.log4j.core.layout.PatternLayout;

import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.Utility;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.LoggerAbstract;

public class Logger4j2 extends LoggerAbstract {
	private static final String SEPARATORE = "; ";
	private static final String SEPARATORE_CHIAVE_VALORE = ": ";

	private static final String LAYOUT_FILE_APPENDER_DEFAULT = "%d{dd MMM yyyy HH:mm:ss,SSS} - %-5p - [Key: %X{key}] %m%n";
	private static final String LAYOUT_CONSOLE_APPENDER_DEFAULT = "%d{HH:mm:ss} - [Key: %X{key}] %m (%C:%L)%n";

	private static final Layout<? extends Serializable> LAYOUT_FILE = PatternLayout.newBuilder().withPattern(LAYOUT_FILE_APPENDER_DEFAULT).withCharset(StandardCharsets.UTF_8).build();
	private static final Layout<? extends Serializable> LAYOUT_CONSOLE = PatternLayout.newBuilder().withPattern(LAYOUT_CONSOLE_APPENDER_DEFAULT).build();

	private static Hashtable<String, FileAppender> fileAppenders = new Hashtable<String, FileAppender>();
	private static Hashtable<String, RollingFileAppender> rollingfileAppenders = new Hashtable<String, RollingFileAppender>();
	private static Hashtable<String, ConsoleAppender> consoleAppenders = new Hashtable<String, ConsoleAppender>();

	public Logger4j2(String sizeLog, String maxBackupIndex, String path, String pathApplicationLog, String logLevel, String jobNumber, String clazz, String logKey, boolean forceOneAppender,
			boolean enableSingleLog) {
		super();

		// Se clazz è stata passata recupera il logger della classe altrimenti
		// il root logger
		org.apache.logging.log4j.core.Logger logger = (org.apache.logging.log4j.core.Logger) ((clazz == null || clazz.trim().equals("")) ? org.apache.logging.log4j.LogManager.getRootLogger()
				: org.apache.logging.log4j.LogManager.getLogger(clazz));

		LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		Configuration config = ctx.getConfiguration();
		AbstractConfiguration absConfig = (AbstractConfiguration) ctx.getConfiguration();
		Collection<Appender> configAppenders = config.getAppenders().values();

		// Elimina la console appenders presente di default
		if (forceOneAppender) {
			for (Appender app : configAppenders) {
				// if (app.getName().toLowerCase().contains("default")) {
				config.getRootLogger().removeAppender(app.getName());
				absConfig.removeAppender(app.getName());
				logger.removeAppender(app);
				app.stop();
				// }
			}
		}
		ctx.updateLoggers();

		// Se non esistono appender li crea altrimenti imposta la chiave
		setLogger(sizeLog, maxBackupIndex, path, pathApplicationLog, logLevel, jobNumber, clazz, false, logKey, enableSingleLog);
		// if (configAppenders.isEmpty()) {
		// setLogger(sizeLog, maxBackupIndex, path, pathApplicationLog,
		// logLevel, jobNumber, clazz, false, logKey, enableSingleLog);
		// } else {
		// setKey(logKey, jobNumber);
		// }
	}

	public Logger4j2() {

	}

	/**
	 * Imposta la chiave del logger
	 * 
	 * @param key
	 */
	private static void setKey(String key) {
		// Impostazione della chiave
		String keyLocal = (key != null) ? key : "";

		ThreadContext.clearAll();
		ThreadContext.put("key", keyLocal);
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

		org.apache.logging.log4j.core.Logger logger = null;
		LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		Configuration config = ctx.getConfiguration();
		AbstractConfiguration absConfig = (AbstractConfiguration) ctx.getConfiguration();

		// Se non impostati i parametri fondamentali il programma non crea il
		// logger
		if (size == null || size.equals("") || maxBackupIndex == null || maxBackupIndex.equals("") || path == null || path.equals("") || level == null || level.equals("")) {
			return;
		}

		// Se la classe non è stata passata recupera il rootLogger
		if (clazz == null || clazz.trim().equals("")) {
			logger = (org.apache.logging.log4j.core.Logger) org.apache.logging.log4j.LogManager.getRootLogger();
		} else {
			// Spegne il log sul rootLogger se non sono stati impostati degli
			// appenders
			logger = (org.apache.logging.log4j.core.Logger) org.apache.logging.log4j.LogManager.getRootLogger();

			if (logger.getAppenders().size() <= 0) {
				logger.setLevel(Level.OFF);
			}

			logger = (org.apache.logging.log4j.core.Logger) org.apache.logging.log4j.LogManager.getLogger(clazz);

		}

		// Impostazione della chiave
		setKey(logKey);

		// Se la gestione per un unico log è disabilitata appende solo il file
		// appender

		if (enableSingleLog) {
			FileAppender appenderFile = fileAppenders.get(clazz);

			if (appenderFile != null) {
				appenderFile.stop();
				config.getRootLogger().removeAppender(appenderFile.getName());
				absConfig.removeAppender(appenderFile.getName());
				logger.removeAppender(appenderFile);
				ctx.updateLoggers();
			}

			appenderFile = FileAppender.newBuilder().withFileName(path).setName("File-" + clazz).setLayout(LAYOUT_FILE)
					.setFilter(ThresholdFilter.createFilter(Level.toLevel(Logger.LEVEL_LOG_SINGOLO), Filter.Result.ACCEPT, Filter.Result.NEUTRAL)).build();

			appenderFile.start();
			logger.addAppender(appenderFile);
			absConfig.addAppender(appenderFile);
			fileAppenders.put(clazz, appenderFile);
			ctx.updateLoggers();
		}

		if (Logger.isConsoleLoginActive()) {
			ConsoleAppender console = null;
			if (consoleAppenders.get(clazz) != null) {
				console = consoleAppenders.get(clazz);
				console.start();
			}
			console = ConsoleAppender.newBuilder().setName("OcsConsoleLogger-" + clazz).setLayout(LAYOUT_CONSOLE)
					.setFilter(ThresholdFilter.createFilter(Level.DEBUG, Filter.Result.ACCEPT, Filter.Result.NEUTRAL)).build();
			console.start();
			logger.addAppender(console);
			consoleAppenders.put(clazz, console);
		}

		RollingFileAppender appenderRolling = rollingfileAppenders.get(clazz);
		if (appenderRolling != null) {
			appenderRolling.stop();
			config.getRootLogger().removeAppender(appenderRolling.getName());
			absConfig.removeAppender(appenderRolling.getName());
			logger.removeAppender(appenderRolling);
			ctx.updateLoggers();
		}
		appenderRolling = RollingFileAppender.newBuilder().withFileName(pathApplicationLog).withFilePattern(pathApplicationLog + ".%i").setName("RollingAppender-" + clazz).setLayout(LAYOUT_FILE)
				.withPolicy(SizeBasedTriggeringPolicy.createPolicy(Integer.parseInt(size) + "MB")).withStrategy(DefaultRolloverStrategy.newBuilder().withMax(maxBackupIndex).build())
				.setFilter(ThresholdFilter.createFilter(Level.toLevel(level), Filter.Result.ACCEPT, Filter.Result.DENY)).build();
		rollingfileAppenders.put(clazz, appenderRolling);
		appenderRolling.start();
		logger.addAppender(appenderRolling);
		absConfig.addAppender(appenderRolling);
		ctx.updateLoggers();

		// Imposta il livello più basso al logger
		Level loggerLevel = Level.toLevel(level);
		if (enableSingleLog) {
			Level logSingoloLevel = (Level.toLevel(Logger.LEVEL_LOG_SINGOLO));
			if (loggerLevel.isMoreSpecificThan(logSingoloLevel)) {
				loggerLevel = logSingoloLevel;
			}
		}
		Configurator.setLevel(logger.getName(), loggerLevel);
		ctx.updateLoggers();
	}

	@Override
	public void log(int level, String clazz, Map<String, String> messages, Throwable e, int levelHeader) {

		Level priority = null;
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
		org.apache.logging.log4j.core.Logger logtmp = (org.apache.logging.log4j.core.Logger) org.apache.logging.log4j.LogManager.getLogger(clazz);

		if (message.length() > 0) {
			logtmp.log(priority, Utility.maskMessage(message, clazz), e);
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
