package it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.properties;

import java.io.File;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.GenericWsManager;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.ResponseGenericClient;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.properties.keystore.KeyStoreMerger;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.properties.keystore.KeyStorePropHandler;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.properties.keystore.TrustStoreMerger;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.KeyValueLog;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

/**
 * Classe statica principale di accesso per l'impostazione delle properties
 * della jvm. Il metodo di init prende in input un json contente le
 * configurazioni dei vari applicativi e provvede a costruire in memoria gli
 * oggetti necessari alla corretta parametrizzazione della jvm: - mappa dei
 * proxy per endpoint - cacerts formato dalla fusione dei singoli cacerts -
 * keystore formato dalla fusione dei singoli keystore
 */
public class JvmProperties {

	private static final String DISABLE_INIT_FILENAME = "disableInit";

	private static Logger logger;
	private static String jarParentFolderPath = "";

	/**
	 * Metodo principale raccolta configurazioni e inizializzazione
	 * 
	 * @param parApps
	 *            StringBuilder contenente il json con i parametri degli
	 *            applicativi
	 */
	public static String init(String parApps) {
		if (parApps == null) {
			System.out.println("Parametri mancanti: parApps = " + parApps);
			return ResponseGenericClient.RISPOSTA_KO;
		}
		try {
			JSONObject jsonParAppObj = (JSONObject) JSONValue.parse(parApps);
			JSONObject jsonParLogger = (JSONObject) jsonParAppObj.get("LOGGER");
			configureLogger(jsonParLogger);
			if (JvmProperties.isInitDisabled(logger)) {
				logger.warn("Init è disabilitato");
				return ResponseGenericClient.RISPOSTA_OK;
			}
			logger.info("Init con parApps " + parApps);
			String pathJar = (String) jsonParLogger.get("PATH_JAR");
			setJarParentFolder(pathJar);
			JSONArray jsonParAppsArray = (JSONArray) jsonParAppObj.get("APPS");
			Set<String> appNames = configureAllAppsParameter(jsonParAppsArray);
			KeyStorePropHandler.init(KeyStoreMerger.getKeyStoreInUse(), KeyStoreMerger.getKsInUsePassword(), TrustStoreMerger.getKeyStoreInUse(), appNames);
		} catch (Exception e) {
			String message = "Errore: " + e.getMessage();
			if (logger != null) {
				logger.error(message, e);
			} else {
				e.printStackTrace();
				System.err.println(e.getStackTrace());
			}
			return ResponseGenericClient.RISPOSTA_KO;
		}
		return ResponseGenericClient.RISPOSTA_OK;
	}

	/**
	 * Metodo per settare il keystore/truststore dal client
	 * 
	 * @param protocol
	 *            versione TLS
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	public static void setKeyStore(String protocol, Logger logger) throws KeyManagementException, NoSuchAlgorithmException {
		KeyStorePropHandler.setKeyStore(protocol, logger);
	}

	public static void resetKeyStore(String protocol) throws KeyManagementException, NoSuchAlgorithmException {
		KeyStorePropHandler.resetKeyStore(protocol);
	}

	private static void configureLogger(JSONObject jsonParLogger) {
		String sizeLog = (String) jsonParLogger.get("SIZE");
		String maxBackupIndex = (String) jsonParLogger.get("MAX_BCK_INDEX");
		String path = (String) jsonParLogger.get("PATH");
		String logLevel = (String) jsonParLogger.get("LOG_LEVEL");
		String jobNumber = (String) jsonParLogger.get("JOB_NUMBER");
		String cliente = (String) jsonParLogger.get("CLIENTE");
		GenericWsManager.setCliente(cliente);
		String clazz = JvmProperties.class.getCanonicalName();
		logger = Logger.getLogger(sizeLog, maxBackupIndex, path, logLevel, jobNumber, clazz, cliente, "GenericWsClient");
		logger.debug(new KeyValueLog("jobNumberBci", jobNumber));

	}

	private static void setJarParentFolder(String jarPath) {
		File parentFolderFile = new File(jarPath).getParentFile();
		if (parentFolderFile != null && parentFolderFile.exists()) {
			jarParentFolderPath = parentFolderFile.getAbsolutePath();
			return;
		}
		logger.warn("Impossibile ricavare cartella del jar");
	}

	public static boolean isInitDisabled(Logger loggerExt) {
		String disableInitFilePath = jarParentFolderPath + File.separator + DISABLE_INIT_FILENAME;
		loggerExt.debug(String.format("Cerco file %s", disableInitFilePath));
		File disableInitFile = new File(disableInitFilePath);
		return disableInitFile.exists();
	}

	private static Set<String> configureAllAppsParameter(JSONArray jsonParAppsArray) {
		Set<String> appNames = new HashSet<String>();
		for (int i = 0; i < jsonParAppsArray.size(); i++) {
			JSONObject jsonParApp = (JSONObject) jsonParAppsArray.get(i);
			String appName = (String) jsonParApp.get("APP_NAME");
			try {
				configureAppParameter(appName, jsonParApp);
				appNames.add(appName);
			} catch (UnrecoverableKeyException e) {
				if (logger != null) {
					logger.error("Errore in configurazione applicativo " + appName, e);
				} else {
					e.printStackTrace();
					System.err.println(e.getStackTrace());
				}
			} catch (KeyStoreException e) {
				if (logger != null) {
					logger.error("Errore in configurazione applicativo " + appName, e);
				} else {
					e.printStackTrace();
					System.err.println(e.getStackTrace());
				}
			} catch (NoSuchAlgorithmException e) {
				if (logger != null) {
					logger.error("Errore in configurazione applicativo " + appName, e);
				} else {
					e.printStackTrace();
					System.err.println(e.getStackTrace());
				}
			} catch (CertificateException e) {
				if (logger != null) {
					logger.error("Errore in configurazione applicativo " + appName, e);
				} else {
					e.printStackTrace();
					System.err.println(e.getStackTrace());
				}
			} catch (Exception e) {
				if (logger != null) {
					logger.error("Errore in configurazione applicativo " + appName, e);
				} else {
					e.printStackTrace();
					System.err.println(e.getStackTrace());
				}
			}
		}
		return appNames;
	}

	private static void configureAppParameter(String appName, JSONObject jsonParApp) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException {
		String truststorePath = (String) jsonParApp.get("TRUSTSTORE_PATH");
		String truststorePwd = (String) jsonParApp.get("TRUSTSTORE_PWD");
		String keyStoreFileName = (String) jsonParApp.get("KEYSTORE_SERVER_PATH");
		String keyStorePassword = (String) jsonParApp.get("KEYSTORE_SERVER_PWD");
		addTrustStore(truststorePath, truststorePwd, appName);
		addKeyStore(keyStoreFileName, keyStorePassword, appName);
	}

	private static void addTrustStore(String truststorePath, String truststorePwd, String appName) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException {
		if (truststorePath != null && !truststorePath.equals("")) {
			truststorePwd = truststorePwd != null ? truststorePwd : "";
			appName = appName != null ? appName : "";
			logger.debug(String.format("Aggiungo trustStore '%s' per client '%s'", truststorePath, appName));
			TrustStoreMerger.addTrustStore(truststorePath, truststorePwd, appName);
		}
	}

	private static void addKeyStore(String keyStoreFileName, String keyStorePassword, String appName)
			throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException {
		if (keyStoreFileName != null && !keyStoreFileName.equals("")) {
			keyStorePassword = keyStorePassword != null ? keyStorePassword : "";
			appName = appName != null ? appName : "";
			logger.debug(String.format("Aggiungo keyStore '%s' per client '%s'", keyStoreFileName, appName));
			KeyStoreMerger.addKeyStore(keyStoreFileName, keyStorePassword, appName);
		}
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void printSystemProps(Logger loggerExt, String filter) {
		if (loggerExt == null) {
			loggerExt = logger;
		}
		loggerExt.info("Stampo system properties");
		for (Entry<Object, Object> entry : System.getProperties().entrySet()) {
			String key = (String) entry.getKey();
			if (key.contains(filter)) {
				loggerExt.info(String.format("%s = %s", key, entry.getValue()));
			}
		}
	}
}
