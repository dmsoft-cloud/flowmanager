package it.dmsoft.flowmanager.agent.engine.core.properties;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import it.dmsoft.flowmanager.agent.engine.core.Main;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class PropertiesUtils {
	
	private static final String PROP_FILENAME = "gestoreflussi.properties";
	
	private static final Logger logger = Logger.getLogger(PropertiesUtils.class.getName());
	
	private static Properties appProps;
	
	private static Properties getProperties() throws Exception {
		/*if (appProps == null) {
			//String path = new File(Main.class.getProtectionDomain().getCodeSource().getLocation()
			//	    .toURI()).getParent();
			String path = new File("").getAbsolutePath();
			logger.info("Working Directory -> " + path);
			appProps = new Properties();
			appProps.load(new FileInputStream(path + Constants.PATH_DELIMITER + PROP_FILENAME));
		}*/
		
		return new Properties();//appProps;
	}
	
	public static String get(String key) throws Exception {	
		return get(key, false);
	}
	
	public static String get(String key, boolean logValue) throws Exception {	
		if(logValue)
			logger.info("key -> " + key);
		String value = getProperties().getProperty(key);
		if(logValue)
			logger.info("value ->" + value);
		return value;
	}
	
	public static List<String> getEnvs() throws Exception {
		Properties properties = getProperties();
		List<String> ret = new ArrayList<String>();
		
		for (Object key : properties.keySet()) {
			String keyStr = (String) key;
			
			if (keyStr.startsWith(Constants.ENV_PREFIX)) {
				String envSet = keyStr.substring(Constants.ENV_PREFIX.length(), keyStr.length()) + Constants.EQUAL + properties.get(keyStr);
				ret.add(envSet);
			}
			
		}
		
		return ret;
	}

}
