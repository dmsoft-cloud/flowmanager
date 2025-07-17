package it.dmsoft.flowmanager.agent.engine.core.exception;

import java.util.ArrayList;
import java.util.List;

import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.KeyValueLog;

public class OperationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7229225956128749661L;
	private List<KeyValueLog> keyValueLogs = new ArrayList<KeyValueLog>();;

	public OperationException(String message) {
		super(message);		
	}

	public OperationException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public void addKeyValueLog(String key, String value) {
		addKeyValueLog(new KeyValueLog(key, value));
	}

	public void addKeyValueLog(KeyValueLog keyValueLog) {
		keyValueLogs.add(keyValueLog);
	}

	public List<KeyValueLog> getKeyValueLogs() {
		return keyValueLogs;
	}

	public void setKeyValueLogs(List<KeyValueLog> keyValueLogs) {
		this.keyValueLogs = keyValueLogs;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof OperationException))
			return false;
		
		OperationException objOe = (OperationException) obj;
		
		if (this.getMessage() != null && this.getMessage().equals(objOe.getMessage())) {
			return true;
		}
			
		return false;
	}
}
