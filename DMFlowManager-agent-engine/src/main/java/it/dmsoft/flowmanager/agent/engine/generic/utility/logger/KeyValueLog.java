package it.dmsoft.flowmanager.agent.engine.generic.utility.logger;

public class KeyValueLog {
	private String key = "";
	private String value = "";

	public KeyValueLog(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	public KeyValueLog(String key, Integer value) {
		super();
		this.key = key;
		if (value != null) {
			this.value = Integer.toString(value);
		} else {
			this.value = null;
		}
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}
}
