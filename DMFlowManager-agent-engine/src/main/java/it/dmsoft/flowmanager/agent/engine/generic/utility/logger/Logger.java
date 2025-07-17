package it.dmsoft.flowmanager.agent.engine.generic.utility.logger;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Level;

import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.Utility;

public class Logger {

	public static final int LEVEL_INFO = 20;
	public static final int LEVEL_DEBUG = 30;
	public static final int LEVEL_WARNING = 10;
	public static final int LEVEL_ERROR = 5;
	public static final int LEVEL_FATAL = 0;

	public static final String LEVEL_LOG_SINGOLO = "DEBUG";

	private static final String LOGMAP_DEBUG_LEVEL = "debugLevel";
	private static final String LOGMAP_DEBUG_KEY = "debugKey";
	private static final String LOGMAP_CLAZZNAME = "clazzName";

	public static final String KEY_MESSAGGIO = "messaggio";

	// Mappa di OcsLogger per classi
	private static HashMap<String, Logger> ocsLoggers = new HashMap<String, Logger>();

	private static String sizeLog;
	private static String maxBackupIndex;
	private static String path;
	private static String pathApplicationLog;
	private static String logLevel;
	private static String logKey;
	private static String jobNumberBci;
	private static String pkg;
	private static String cliente;
	private static String applicazione;
	private static boolean enableSingleLog;

	private LoggerAbstract loggerInstance = null;
	private Map<String, String> logMap = null;
	private boolean warningNoLogger = false;

	private static boolean consoleLoginActive = false;

	public int getLevel() {
		return getLevel(logMap.get(LOGMAP_DEBUG_LEVEL));
	}

	private static int getLevel(String text) {
		if (text == null) {
			return LEVEL_ERROR;
		}
		if (text.equalsIgnoreCase("INFO")) {
			return LEVEL_INFO;
		}
		if (text.equalsIgnoreCase("DEBUG")) {
			return LEVEL_DEBUG;
		}
		if (text.equalsIgnoreCase("ERROR")) {
			return LEVEL_ERROR;
		}
		if (text.equalsIgnoreCase("WARN")) {
			return LEVEL_WARNING;
		}
		if (text.equalsIgnoreCase("FATAL")) {
			return LEVEL_FATAL;
		}
		return LEVEL_ERROR;
	}

	public static Logger getLogger(Class<?> clazzManager) {
		if (clazzManager == null) {
			return getLogger((String) null);
		}
		String clazz = clazzManager.getCanonicalName();
		return getLogger(clazz);
	}

	public static Logger getLogger(String clazz) {
		return getOcsLogger(clazz);
	}

	public static Logger getLogger(String sizeLog, String maxBackupIndex, String path, String logLevel, String jobNumberBci, String pkg, String cliente, String applicazione) {
		return getLogger(sizeLog, maxBackupIndex, path, logLevel, jobNumberBci, pkg, cliente, applicazione, false);
	}

	public static Logger getLogger(String sizeLog, String maxBackupIndex, String path, String logLevel, String jobNumberBci, String pkg, String cliente, String applicazione, boolean enableSingleLog) {
		String keyLogTmp = createLogKey();
		try {
			if (pkg != null && !"".equals(pkg)) {
				pkg = Class.forName(pkg).getPackage().getName();
			}
		} catch (ClassNotFoundException e) {
		}

		enableSingleLog = defineEnablelog(applicazione, cliente, enableSingleLog);
		Logger.sizeLog = sizeLog;
		Logger.maxBackupIndex = maxBackupIndex;
		Logger.path = defineLogPath(path, jobNumberBci, enableSingleLog, keyLogTmp);
		Logger.pathApplicationLog = path;
		Logger.logLevel = logLevel;
		Logger.logKey = keyLogTmp;
		Logger.jobNumberBci = jobNumberBci;
		Logger.pkg = pkg;
		Logger.cliente = cliente;
		Logger.applicazione = applicazione;
		Logger.enableSingleLog = enableSingleLog;

		Logger loggerTmp = new Logger(true);

		ocsLoggers = new HashMap<String, Logger>();
		ocsLoggers.put(pkg, loggerTmp);

		if (enableSingleLog) {
			loggerTmp.debug(new KeyValueLog("path log singolo", Logger.path));
		}
		loggerTmp.debug(new KeyValueLog("path", Logger.pathApplicationLog), new KeyValueLog("logLevel", logLevel), new KeyValueLog("classe log", pkg), new KeyValueLog("sizeLog", sizeLog),
				new KeyValueLog("maxBackupIndex", maxBackupIndex), new KeyValueLog("enableSingleLog", Boolean.toString(enableSingleLog)), new KeyValueLog("cliente", cliente),
				new KeyValueLog("applicazione", applicazione));
		return loggerTmp;
	}

	private static Logger getOcsLogger(String clazz) {
		if (ocsLoggers.get(clazz) == null || ocsLoggers.get(clazz).loggerInstance == null) {
			Logger loggRet = new Logger(false);
			loggRet.logMap.put(LOGMAP_CLAZZNAME, clazz);
			ocsLoggers.put(clazz, loggRet);
			return loggRet;
		}
		return ocsLoggers.get(clazz);
	}

	private void duplicateField(Logger logger) {
		if (logger == null) {
			return;
		}
		this.loggerInstance = logger.loggerInstance;
		this.logMap = logger.logMap;
		this.warningNoLogger = logger.warningNoLogger;
	}

	public static int level4j2LevelOcs(Level level) {
		return getLevel(level.toString());
	}

	public static Logger log4j2Logger(org.apache.log4j.Logger logger4j) {
		if (logger4j == null) {
			return null;
		}
		getOcsLogger(logger4j.getName()).loggerInstance = LoggerFactory.getInstanceLog4j();
		return getOcsLogger(logger4j.getName());
	}

	private static String createLogKey() {
		String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lower = upper.toLowerCase(Locale.ROOT);
		String digits = "0123456789";
		String alphanum = upper + lower + digits;
		Random random = new SecureRandom();
		char[] symbols = alphanum.toCharArray();
		char[] buf = new char[10];
		for (int idx = 0; idx < buf.length; ++idx) {
			buf[idx] = symbols[random.nextInt(symbols.length)];
		}
		return new String(buf);
	}

	private static String defineLogPath(String logPath, String jobNumber, boolean enableSingleLog, String keyLogTmp) {
		if (!enableSingleLog) {
			return logPath;
		}

		return Utility.defineFilePath(logPath, jobNumber, keyLogTmp);
	}

	private Logger(boolean forceOneAppender) {
		super();
		loggerInstance = LoggerFactory.getInstance(sizeLog, maxBackupIndex, path, pathApplicationLog, logLevel, jobNumberBci, pkg, cliente, applicazione, logKey, forceOneAppender, enableSingleLog);

		logMap = new HashMap<String, String>();

		if (loggerInstance != null) {
			if (logLevel != null) {
				logMap.put(LOGMAP_DEBUG_LEVEL, logLevel);
			}
			if (logKey != null) {
				logMap.put(LOGMAP_DEBUG_KEY, logKey);
			}
			if (pkg != null) {
				logMap.put(LOGMAP_CLAZZNAME, pkg);
			}
		}

	}

	public void debug(KeyValueLog... keyValue) {
		List<KeyValueLog> lista = new ArrayList<KeyValueLog>();
		for (KeyValueLog keyValueLog : keyValue) {
			if (keyValueLog != null) {
				lista.add(keyValueLog);
			}
		}
		log(LEVEL_DEBUG, logMap.get(LOGMAP_CLAZZNAME), lista, null);
	}

	public void debug(String message) {
		log(LEVEL_DEBUG, logMap.get(LOGMAP_CLAZZNAME), message);
	}

	public void error(KeyValueLog... keyValue) {
		List<KeyValueLog> lista = new ArrayList<KeyValueLog>();
		for (KeyValueLog keyValueLog : keyValue) {
			if (keyValueLog != null) {
				lista.add(keyValueLog);
			}
		}
		log(LEVEL_ERROR, logMap.get(LOGMAP_CLAZZNAME), lista, null);
	}

	public void error(List<KeyValueLog> keyValue, Throwable e) {
		log(LEVEL_ERROR, logMap.get(LOGMAP_CLAZZNAME), keyValue, e);
	}

	public void error(String message) {
		log(LEVEL_ERROR, logMap.get(LOGMAP_CLAZZNAME), message);
	}

	public void error(String message, Throwable e) {
		log(LEVEL_ERROR, logMap.get(LOGMAP_CLAZZNAME), message, e);
	}

	public String getName() {
		return logMap.get(LOGMAP_CLAZZNAME);
	}

	public void info(KeyValueLog... keyValue) {
		List<KeyValueLog> lista = new ArrayList<KeyValueLog>();
		for (KeyValueLog keyValueLog : keyValue) {
			if (keyValueLog != null) {
				lista.add(keyValueLog);
			}
		}
		log(LEVEL_INFO, logMap.get(LOGMAP_CLAZZNAME), lista, null);
	}

	public void info(String message) {
		log(LEVEL_INFO, logMap.get(LOGMAP_CLAZZNAME), message);
	}

	public boolean isDebugEnabled() {
		return isEnableLog(LEVEL_DEBUG);
	}

	public boolean isEnableLog(int level) {
		if (enableSingleLog && level <= getLevel(LEVEL_LOG_SINGOLO)) {
			return true;
		}

		int levelHeader = getLevel(logMap.get(LOGMAP_DEBUG_LEVEL));
		return (level <= levelHeader);
	}

	public boolean isErrorEnabled() {
		return isEnableLog(LEVEL_ERROR);
	}

	public boolean isInfoEnabled() {
		return isEnableLog(LEVEL_INFO);
	}

	public boolean isWarningEnabled() {
		return isEnableLog(LEVEL_WARNING);
	}

	private void log(int level, String clazz, String message, Throwable e) {
		ArrayList<KeyValueLog> lista = new ArrayList<KeyValueLog>();
		if (message != null) {
			lista.add(new KeyValueLog(KEY_MESSAGGIO, message));
		}
		log(level, clazz, lista, e);
	}

	public void log(int level, String message) {
		log(level, logMap.get(LOGMAP_CLAZZNAME), message);
	}

	private void log(int level, String clazz, List<KeyValueLog> keyValue, Throwable e) {

		// Verifica che il logger sia stato inizializzato
		if (loggerInstance == null) {
			Logger loggRet = new Logger(false);
			loggRet.logMap.put(LOGMAP_CLAZZNAME, clazz);
			ocsLoggers.put(clazz, loggRet);
			duplicateField(loggRet);
			if (loggerInstance == null) {
				if (!warningNoLogger) {
					StringBuilder messaggio = new StringBuilder();
					messaggio.append("No logger defined (").append(logMap.get(LOGMAP_CLAZZNAME)).append(")");
					System.err.println(messaggio.toString());
					System.err.println(
							"Please initialize Logger system property use: getLogger(String sizeLog, String maxBackupIndex, String path, String logLevel, String jobNumber, String pkg, String cliente, String applicazione)");
					warningNoLogger = true;
				}
				return;
			}
		}

		if (!isEnableLog(level)) {
			return;
		}
		HashMap<String, String> map = new HashMap<String, String>();
		for (KeyValueLog keyValueLog : keyValue) {
			if (keyValueLog != null) {
				map.put(keyValueLog.getKey(), keyValueLog.getValue());
			}
		}

		if (e != null) {
			int levelHeader = Logger.getLevel(logMap.get(LOGMAP_DEBUG_LEVEL));
			getOcsLogger(clazz).loggerInstance.log(level, clazz, map, e, levelHeader);
		} else {
			getOcsLogger(clazz).loggerInstance.log(level, clazz, map);
		}
	}

	private void log(int level, String clazz, String message) {
		List<KeyValueLog> lista = new ArrayList<KeyValueLog>();
		if (message != null) {
			lista.add(new KeyValueLog(KEY_MESSAGGIO, message));
		}
		log(level, clazz, lista, null);
	}

	public void warn(KeyValueLog... keyValue) {
		List<KeyValueLog> lista = new ArrayList<KeyValueLog>();
		for (KeyValueLog keyValueLog : keyValue) {
			if (keyValueLog != null) {
				lista.add(keyValueLog);
			}
		}
		log(LEVEL_WARNING, logMap.get(LOGMAP_CLAZZNAME), lista, null);
	}

	public void warn(List<KeyValueLog> keyValue, Throwable e) {
		log(LEVEL_WARNING, logMap.get(LOGMAP_CLAZZNAME), keyValue, e);
	}

	public void warn(String message) {
		log(LEVEL_WARNING, logMap.get(LOGMAP_CLAZZNAME), message);
	}

	public void warn(String message, Throwable e) {
		log(LEVEL_WARNING, logMap.get(LOGMAP_CLAZZNAME), message, e);
	}

	public void fatal(String message) {
		log(LEVEL_FATAL, logMap.get(LOGMAP_CLAZZNAME), message);
	}

	public String getLogKey() {
		return Logger.logKey;
	}

	public String getLogPath() {
		return Logger.path;
	}

	public boolean isEnableSingleLog() {
		return enableSingleLog;
	}

	private static boolean defineEnablelog(String applicazione, String cliente, boolean enableSingleLog) {
		if (Constants.GESTOREFLUSSI.equals(applicazione)) {
			enableSingleLog = false;
		}

		return enableSingleLog;
	}

	public static boolean isConsoleLoginActive() {
		return consoleLoginActive;
	}

	public static void setConsoleLoginActive(boolean consoleLoginActive) {
		Logger.consoleLoginActive = consoleLoginActive;
	}
}
