package it.dmsoft.flowmanager.agent.engine.core.db;

import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;
import it.dmsoft.flowmanager.common.domain.Domains.YesNo;

public class DbConstants {
	
	private static final Logger logger = Logger.getLogger(DbConstants.class.getName());

	public static String SCHEMA_KEY = "schema";
	
	public static String GF_CURLIB = "";
	
	public static String SCHEMA = "";
	
	public static String DB_TYPE = "";
	
	public static String DB_HOST = "";
	
	public static String REMOTE_HOST = "";
	
	public static YesNo SECURE_CONNECTION = YesNo.NO;
	
	public static String USERNAME = "";
	
	public static String PASSWORD = "";
	
	public static String HIBERNATE_DIALECT = "";
	

}
