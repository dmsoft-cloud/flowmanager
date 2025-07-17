package it.dmsoft.flowmanager.agent.engine.core.db.dao;

import java.util.Optional;

import it.dmsoft.flowmanager.agent.engine.core.exception.InvalidDBTypeException;
import it.dmsoft.flowmanager.agent.engine.core.properties.PropertiesConstants;
import it.dmsoft.flowmanager.agent.engine.core.properties.PropertiesUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.StringUtils;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class DbConstants {
	
	private static final Logger logger = Logger.getLogger(DbConstants.class.getName());

	public static String SCHEMA_KEY = "schema";
	
	public static String GF_CURLIB = "";
	
	public static String SCHEMA = "";
	
	public static String DB_TYPE = "";
	
	public static String DB_HOST = "";
	
	public static String REMOTE_HOST = "";
	
	public static String SECURE_CONNECTION = "";
	
	public static String USERNAME = "";
	
	public static String PASSWORD = "";
	
	public static String HIBERNATE_DIALECT = "";
	

}
