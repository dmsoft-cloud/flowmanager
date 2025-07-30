package it.dmsoft.flowmanager.agent.engine.generic.utility.logger;

import java.io.PrintStream;
import java.util.HashMap;

//import org.apache.log4j.Level;

public class OutErrLogger {
	private static HashMap<String, PrintStream> streamsInfo = new HashMap<String, PrintStream>();
	private static HashMap<String, PrintStream> streamsError = new HashMap<String, PrintStream>();

	protected OutErrLogger() {

	}

	public static void setOutAndErrToLog(Logger logger) {
		setOutAndErrToLog(logger, Logger.LEVEL_INFO);
	}

	public static void setOutAndErrToLog(Logger logger, int level) {
		setOutToLog(logger, level);
		setErrToLog(logger);
	}

	/**
	 * @deprecated
	 */
	/*
	@Deprecated
	public static void setOutAndErrToLog(org.apache.log4j.Logger logger) {
		setOutAndErrToLog(Logger.log4j2Logger(logger), Logger.LEVEL_INFO);
	}
	*/

	/**
	 * @deprecated
	 */
	/*
	@Deprecated
	public static void setOutAndErrToLog(org.apache.log4j.Logger logger, Level level) {
		setOutAndErrToLog(Logger.log4j2Logger(logger), Logger.level4j2LevelOcs(level));
	}
	*/
	
	private static void setOutToLog(Logger logger, int level) {
		if (!streamsInfo.containsKey(logger.getName())) {
			streamsInfo.put(logger.getName(), new PrintStream(new LoggerStream(logger, level)));
		}
		System.setOut(streamsInfo.get(logger.getName()));
	}

	private static void setErrToLog(Logger logger) {
		if (!streamsError.containsKey(logger.getName())) {
			streamsError.put(logger.getName(), new PrintStream(new LoggerStream(logger, Logger.LEVEL_ERROR)));
		}
		System.setErr(streamsError.get(logger.getName()));
	}

}