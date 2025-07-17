package it.dmsoft.flowmanager.agent.engine.generic.utility.logger;

import java.util.Map;

public abstract class LoggerAbstract {

	public abstract void log(int level, Class<?> clazz, Map<String, String> messages, Throwable e, int levelHeader);

	public abstract void log(int level, Class<?> clazz, Map<String, String> messages);

	public abstract void log(int level, String clazz, Map<String, String> messages, Throwable e, int levelHeader);

	public abstract void log(int level, String clazz, Map<String, String> messages);
}