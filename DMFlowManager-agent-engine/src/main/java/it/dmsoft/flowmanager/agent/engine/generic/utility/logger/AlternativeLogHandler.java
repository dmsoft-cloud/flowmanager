package it.dmsoft.flowmanager.agent.engine.generic.utility.logger;

import java.util.SortedMap;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;

public class AlternativeLogHandler {
	private static final String LAYOUT_FILE_APPENDER_DEFAULT = "%m%n";
	private static final String DATE_PATTERN_APPENDER_DEFAULT = "%d{yyyy-MM-dd}";
	private static Logger logger = null;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void initLogger(String path, String level, String clazz) {
		if (logger == null) {
			logger = (Logger) LoggerFactory.getLogger(AlternativeLogHandler.class);

			LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

			RollingFileAppender appender = new RollingFileAppender();
			appender.setContext(loggerContext);
			appender.setPrudent(true);

			TimeBasedRollingPolicy rolling = new TimeBasedRollingPolicy();
			rolling.setContext(loggerContext);
			rolling.setParent(appender);
			rolling.setFileNamePattern(path + "." + DATE_PATTERN_APPENDER_DEFAULT);
			rolling.start();
			appender.setRollingPolicy(rolling);

			PatternLayoutEncoder layout = new PatternLayoutEncoder();
			layout.setContext(loggerContext);
			layout.setPattern(LAYOUT_FILE_APPENDER_DEFAULT);
			layout.start();
			appender.setEncoder(layout);
			appender.start();

			logger.addAppender(appender);
			logger.setLevel(Level.toLevel(level));
		}

	}

	public static void write(SortedMap<String, String> coppieChiaveValore) {
		StringBuilder record = new StringBuilder();
		for (String chiave : coppieChiaveValore.keySet()) {
			String key = chiave.toString();
			String value = coppieChiaveValore.get(chiave).toString();
			if (record.length() == 0) {
				record.append(key + "=\"" + value + "\"");
			} else {
				record.append(" " + key + "=\"" + value + "\"");
			}
		}
		logger.info(record.toString());
	}
}
