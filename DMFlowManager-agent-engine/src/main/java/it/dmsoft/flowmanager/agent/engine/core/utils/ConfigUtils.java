package it.dmsoft.flowmanager.agent.engine.core.utils;

import java.io.File;
import java.io.FileReader;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import it.dmsoft.flowmanager.agent.engine.core.db.dao.DbConstants;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.ConfigDto;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.MailParms;
import it.dmsoft.flowmanager.agent.engine.core.exception.InvalidDBTypeException;
import it.dmsoft.flowmanager.agent.engine.core.properties.PropertiesConstants;
import it.dmsoft.flowmanager.agent.engine.core.properties.PropertiesUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.DatabaseUtils.DBTypeEnum;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class ConfigUtils {	
	
	private static ConfigDto config;
	Logger logger = null;
	
	public static ConfigDto setConfig(String param) throws Exception {

			JSONParser parser = new JSONParser();
	        JSONObject data = (JSONObject) parser.parse(param);


	        config = new ConfigDto();
	        config.setTransactionName((String)data.get(Constants.CONF_TRANSACTION_NAME));
	        config.setTransactionId((String)data.get(Constants.CONF_TRANSACTION_ID));
	        config.setLogPath((String)data.get(Constants.CONF_LOG_PATH));
	        config.setLogLevel((String)data.get(Constants.CONF_LOG_LEVEL));
	        config.setLogSizeMB((String)data.get(Constants.CONF_LOG_SIZE));
	        config.setLogRotation((String)data.get(Constants.CONF_LOG_ROTATION));
	        config.setBackupPath((String)data.get(Constants.CONF_BACKUP_PATH));
	        config.setMailFrom((String)data.get(Constants.CONF_MAIL_FROM));
	        config.setCliente((String)data.get(Constants.CONF_CUSTOMER));
	        
	        config.setExecutionDateStr((String)data.get(Constants.CONF_EXECUTION_DATE));
	        config.setLegacyModernization((String)data.get(Constants.CONF_LEGACY_MODERNIZATION));
	        config.setMasterdataOverrides(data.get(Constants.CONF_MASTERDATA_OVERRIDES).toString());
	        
	        config.setExtJob((String)data.getOrDefault(Constants.CONF_EXT_JOB, ""));
	        config.setExtUser((String)data.getOrDefault(Constants.CONF_EXT_USER, ""));
	        config.setExtNumber((String)data.getOrDefault(Constants.CONF_EXT_NUMBER, "0" ));
	        config.setExtTask((String)data.getOrDefault(Constants.CONF_EXT_TASK,"" ));
        	
	        //System.out.println(config.toString());
		return config;
	}
	
	public static MailParms getMailConfig() throws Exception {
		
		MailParms mp = new MailParms();
		mp.setSmtp_host(PropertiesUtils.get(PropertiesConstants.SMTP_HOST));
		mp.setSmtp_port(PropertiesUtils.get(PropertiesConstants.SMTP_PORT));
		mp.setSmtp_user(PropertiesUtils.get(PropertiesConstants.SMTP_USER));
		mp.setSmtp_password(PropertiesUtils.get(PropertiesConstants.SMTP_PASSWORD));
		mp.setSmtp_secure(PropertiesUtils.get(PropertiesConstants.SMTP_SECURE));
		return mp ;
	}
	

}


