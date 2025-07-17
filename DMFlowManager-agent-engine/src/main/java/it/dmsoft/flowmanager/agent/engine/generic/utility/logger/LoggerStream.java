package it.dmsoft.flowmanager.agent.engine.generic.utility.logger;

import java.io.IOException;
import java.io.OutputStream;

public class LoggerStream extends OutputStream {
	private Logger logger;
	private final int logLevel;

	public LoggerStream(Logger logger, int logLevel) {
		super();

		this.logger = logger;
		this.logLevel = logLevel;
	}

	public LoggerStream(org.apache.log4j.Logger logger, int logLevel) {
		this(Logger.log4j2Logger(logger), logLevel);
	}

	@Override
	public void write(byte[] b) throws IOException {
		String string = new String(b);
		if (!string.trim().isEmpty()) {
			logger.log(logLevel, string);
		}
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		String string = new String(b, off, len);
		if (!string.trim().isEmpty()) {
			logger.log(logLevel, string);
		}
	}

	@Override
	public void write(int b) throws IOException {
		String string = String.valueOf((char) b);
		if (!string.trim().isEmpty()) {
			logger.log(logLevel, string);
		}
	}
}