package it.dmsoft.flowmanager.agent.engine.core.properties;

import it.dmsoft.flowmanager.agent.engine.core.db.dto.Otgffana;
import it.dmsoft.flowmanager.agent.engine.core.operations.core.Operation;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.OperationParams;

public class PropertiesConstants {
	
	//db properties
	public static final String SCHEMA_EXPORT_CONFIG_FILE = "export_config_schema";
	
	public static final String DATABASE_TYPE = "dbType";
	
	public static final String DATABASE_URL = "dbUrl";
	
	public static final String REMOTE_HOST = "remoteHost";
	
	public static final String SECURE_CONNECTION = "secureConnection";
	
	public static final String TMP_LIBRARY = "tmpLibrary";
	
	//general properties
	public static final String DEFAULT_SHELL = "defaultShell";
	
	//properties per smtp legacy
	
	public static final String SMTP_USER = "smtp.user";
	
	public static final String SMTP_PASSWORD = "smtp.password";
	
	public static final String SMTP_PORT = "smtp.port";
	
	public static final String SMTP_HOST = "smtp.host";
	
	public static final String SMTP_SECURE = "smtp.secure";

}
