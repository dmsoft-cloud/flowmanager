package it.dmsoft.flowmanager.agent.engine.generic.genericWsClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Authenticator;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.fasterxml.jackson.core.JsonProcessingException;

import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.AuthenticationProperties;

//com.sun.xml.internal.ws.client.BindingProviderProperties;

import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.Utility;
import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.UtilityJson;
import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.httpLogger.LoggingHttpHandler;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.properties.JvmProperties;
import it.dmsoft.flowmanager.agent.engine.generic.utility.json.LogErrIfs;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.KeyValueLog;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;
//import it.ocsnet.genericWsClient.widiba.EndPointHandler;

public class GenericWsManager {
	private static final int TEMPO_ATTESA = 100;

	private static boolean isInitCalled = false;
	private static String cliente = "";
	private static HashMap<String, URL> risorse = new HashMap<String, URL>();
	private static HashMap<String, MetodoClasse> metodi = new HashMap<String, MetodoClasse>();

	/**
	 * Abilita i log di request, respone ed error in sostituzione del XML
	 * GENERATE Cobol. Inserita variabile per abilitarlo da RPGLE solo se la
	 * nuova logica di log (ifs) è presente dal cliente
	 */
	private static boolean logReqResErr = false;

	private GenericWsManager() {

	}

	public static void enableLogReqResErr() {
		logReqResErr = true;
	}

	public static String init(String parApps) {
		try {
			isInitCalled = true;
			String result = JvmProperties.init(parApps);
			AuthenticationProperties.init(parApps);
			return result;
		} catch (Throwable e) {
			System.err.println("Errore in fase di init: " + e);
			e.printStackTrace(System.out);
		}
		return "KO";
	}

	public static String initOnlyCse(String parApps) {
		try {
			initCse(parApps);
			return "OK";
		} catch (Throwable e) {
			System.err.println("Errore in fase di init: " + e);
			e.printStackTrace(System.out);
		}
		return "KO";
	}

	/**
	 * Inizilizza le chiamate a CSEAUTORIZZATIVOCLIENT
	 * 
	 * @param parApps
	 *            json dei parametri di input
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	private static void initCse(String parApps) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> c = null;
		Method m = null;
		try {
			c = Class.forName("it.ocsnet.cse.autorizzativo.client.Servizio");
			m = c.getMethod("initAll", String.class);
			m.invoke(null, parApps);
		} catch (ClassNotFoundException e) {
			// Se non trova la classe prosegue senza fare nulla: la classe non è
			// presente nel job BCI
		}
	}

	public static void setCliente(String cliente) {
		GenericWsManager.cliente = cliente;
	}

	public static String getCliente() {
		return GenericWsManager.cliente;
	}

	/**
	 * @deprecated La nuova versione della parte iSeries usa il metodo
	 *             {@link sendRequest2}. Questo è da mantenere fin tanto che i
	 *             clienti useranno la versione vecchia del sorgente su iSeries
	 * @param parGen
	 * @param parCli
	 * @param parOut
	 * @return
	 */
	@Deprecated
	public static String sendRequest(StringBuffer parGen, StringBuffer parCli, StringBuffer parOut) {
		return (sendRequest2(parGen, parCli, parOut)).getResponse();
	}

	public static ResponseGenericClient sendRequest2(StringBuffer parGen, StringBuffer parCli, StringBuffer parOut) {
		ResponseGenericClient response = new ResponseGenericClient(ResponseGenericClient.RISPOSTA_KO);
		try {
			response = sendRequest3(parGen, parCli, parOut);
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println(e.getMessage() + ";" + e.getCause());
		}
		return response;
	}

	/**
	 * 
	 * @param parGen
	 * @param parCli
	 * @param parOut
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static ResponseGenericClient sendRequest3(StringBuffer parGen, StringBuffer parCli, StringBuffer parOut) {
		ResponseGenericClient response = new ResponseGenericClient();

		if (parGen == null || parCli == null || parOut == null) {
			System.out.println("Parametri mancanti: parGen-" + parGen + "; parCli-" + parCli + "; parOut-" + parOut);
			response.setResponse(ResponseGenericClient.RISPOSTA_KO);
			response.setMessage("Parametri mancanti: parGen-" + parGen + "; parCli-" + parCli + "; parOut-" + parOut);
			System.out.println("Risposta GenericWsManager: " + response);
			return response;
		}
		String jobNumber = "";
		String jobNumberBci = "";
		String sizeLog = "";
		String maxBackupIndex = "";
		String path = "";
		String logLevel = "";
		String jarPath = "";
		String proxyIP = "";
		String proxyPort = "0";
		String proxyUser = "";
		String proxyPass = "";
		String endPoint = "";
		String clazz = "";
		String method = "";
		String pkg = "";
		String truststorePath = "";
		String truststorePwd = "";
		String timeout = "";
		String timeoutOver = "";
		String keyStoreFileName = "";
		String keyStorePassword = "";
		String protocol = "";
		String applicativoOpen = "";
		boolean enableSingleLog = false;

		Logger logger = null;
		JSONObject jsonParGen = null;
		JSONObject jsonParCli = null;
		JSONObject jsonOutput = new JSONObject();
		JSONObject comuni = null;

		// Reperimento parametri generali
		try {
			jsonParGen = (JSONObject) JSONValue.parse(parGen.toString());
			jobNumber = (String) jsonParGen.get("NUMERO_JOB");
			jobNumberBci = (String) jsonParGen.get("NUMERO_JOB_BCI");
			clazz = (String) jsonParGen.get("CLASS");
			method = (String) jsonParGen.get("METHOD");
			pkg = (String) jsonParGen.get("PACKAGE");
			comuni = (JSONObject) jsonParGen.get("comuni");
			applicativoOpen = (String) jsonParGen.get("APP_OPEN");

			enableSingleLog = "S".equals(comuni.get("ABILITA_LOG_SINGOLO"));
			sizeLog = (String) comuni.get("LOG_DIM");
			maxBackupIndex = (String) comuni.get("LOG_NUM_DOC");
			path = (String) comuni.get("LOG_DOCUM");
			logLevel = (String) comuni.get("LOG_LEVEL");

			jarPath = (String) comuni.get("CLASSE");

			keyStoreFileName = (String) comuni.get("KEYSTORE_SERVER_PATH");
			keyStorePassword = (String) comuni.get("KEYSTORE_SERVER_PWD");
			protocol = (String) comuni.get("PROTOCOLLO");

			proxyIP = (String) comuni.get("PROXY_IP");
			proxyPort = (String) comuni.get("PROXY_PORTA");
			proxyUser = (String) comuni.get("PROXY_USER");
			proxyPass = (String) comuni.get("PROXY_PWD");
			truststorePath = (String) comuni.get("TRUSTSTORE_PATH");
			truststorePwd = (String) comuni.get("TRUSTSTORE_PWD");

			endPoint = (String) comuni.get("URL_WS");
			response.setMethod(method);
			response.setClasseManager(clazz);

		} catch (Throwable e) {
			System.out.println("Si sono verificati problemi nel parsing dei parametri generali");
			e.printStackTrace();
			response.setResponse(ResponseGenericClient.RISPOSTA_KO);
			response.setException(e);
			response.setMessage(e.toString());
			System.out.println("Risposta GenericWsManager: " + response);
			return response;
		}

		// Creazione logger
		try {
			logger = Logger.getLogger(sizeLog, maxBackupIndex, path, logLevel, jobNumberBci, pkg, cliente, applicativoOpen, enableSingleLog);
			logger.info(new KeyValueLog("jobNumber", jobNumber), new KeyValueLog("jobNumberBci", jobNumberBci), new KeyValueLog("class", clazz), new KeyValueLog("method", method),
					new KeyValueLog("truststorePath", truststorePath), new KeyValueLog("jarPath", jarPath), new KeyValueLog("endPoint", endPoint));

		} catch (Throwable e) {
			System.out.println("Si sono verificati problemi con la configurazione del log");
			e.printStackTrace();
			response.setResponse(ResponseGenericClient.RISPOSTA_KO);
			response.setException(e);
			response.setMessage(e.toString());
			System.out.println("Risposta GenericWsManager: " + response);
			return response;
		}

		// Gestione timeout
		// Se passato il timeout di override (ad esempio per l'esame di
		// prescoring di DEBK) viene impostato al client al posto del
		// timeout.
		// Se presenti sia timeout che timeout di override viene impostato
		// il
		// minore
		// Se il timeout di override è negativo il client termina con errore
		timeoutOver = (String) comuni.get("TIMEOUT_OVER");
		try {
			if (timeoutOver != null && !"".equals(timeoutOver)) {
				Integer timeoutOverInt = Integer.parseInt(timeoutOver.trim());
				if (timeoutOverInt >= 0) {
					timeout = (String) comuni.get("TIMEOUT");
					logger.debug(new KeyValueLog("timeoutOver", timeoutOver));
					logger.debug(new KeyValueLog("timeout", timeout));
					Integer timeoutInt;
					if (timeout == null || "".equals(timeout)) {
						timeoutInt = 0;
					} else {
						timeoutInt = Integer.parseInt(timeout.trim());
					}
					if (timeoutInt == 0 || timeoutOverInt < timeoutInt) {
						comuni.put("TIMEOUT", timeoutOver);
						logger.info("Eseguito override timeout: da " + timeout + " a " + (String) comuni.get("TIMEOUT"));
					}
				} else {
					logger.error("Timeout ricevuto negativo : " + timeoutOverInt);
					response.setResponse(ResponseGenericClient.RISPOSTA_KO);
					logger.error("Risposta GenericWsManager: " + response);
					return response;
				}
			}
		} catch (NumberFormatException e) {
			logger.warn("Timeout non numerico", e);
		} catch (Throwable e) {
			logger.warn("Errore in fase di gestione del timeout", e);
		}

		try {
			AppOpePar appOpePar = new AppOpePar(truststorePath, protocol, keyStoreFileName, truststorePwd, keyStorePassword, proxyIP, proxyPort, proxyUser, proxyPass);

			// Aggiunta loggatura centralizzata per comunicazione Http
			LoggingHttpHandler.addLogger(logger, jsonParGen, jsonParCli);
			setupGeneric(appOpePar, logger);
		} catch (Throwable e) {
			logger.error("Errore generico set properties", e);
			response.setResponse(ResponseGenericClient.RISPOSTA_KO);
			response.setException(e);
			response.setMessage(e.toString());
			logger.error("Risposta GenericWsManager: " + response);
			return response;
		}

		puliziaParametri(comuni, clazz);

		// Parsing dei parametri specifici per client
		try {
			jsonParCli = (JSONObject) JSONValue.parse(parCli.toString().replace("\\u20ac", "\u20ac"));
		} catch (Throwable e) {
			logger.error("Errore in parsing dei parametri specifici del client", e);
			response.setResponse(ResponseGenericClient.RISPOSTA_KO);
			response.setException(e);
			response.setMessage(e.toString());
			logger.error("Risposta GenericWsManager: " + response);
			return response;
		}

		if (jsonParCli == null) {
			logger.error("Errore parsing dei parametri specifici del client: " + parCli.toString());
		}

		// Log dei parametri: sotto verifica "if info" perchè nella maggior
		// parte dei client specifici vengono già loggati a livello info e
		// debug
		if (logger.isInfoEnabled() && !logger.isDebugEnabled()) {
			logger.info(new KeyValueLog("Parametri generali", parGen.toString()));
			logger.info(new KeyValueLog("Parametri specifici", parCli.toString()));
		}

		String logReqPath = "";
		try {
			logReqPath = Utility.defineFilePath(path, jobNumberBci, logger.getLogKey(), "REQ");
			logOnFile(parCli.toString(), logReqPath, applicativoOpen);
			response.setLogReqPath(logReqPath);
		} catch (IOException e) {
			logger.error("Errore loggatura request");
		}

		// Modifica l'endpoit
		//EndPointHandler.manageRac(logger, cliente, jsonParGen, applicativoOpen);

		// Chiamata client specifico
		MetodoClasse metodoClasse = metodi.get(clazz);
		Class<?> c = null;
		Method m = null;
		try {
			if (metodoClasse == null) {
				c = Class.forName(clazz);
				m = c.getMethod(method, JSONObject.class, JSONObject.class, JSONObject.class);
				metodi.put(clazz, new MetodoClasse(m, c));
			} else {
				c = metodoClasse.getC();
				m = metodoClasse.getM();
			}
		} catch (Throwable e) {
			logger.error("Errore in invocazione dinamica della classe del client specifico", e);
			response.setResponse(ResponseGenericClient.RISPOSTA_KO);
			response.setException(e);
			response.setMessage(e.toString());
			logger.error(new KeyValueLog("Risposta GenericWsManager: ", response.toString()));
			return response;
		}

		try {
			Object responseObj = null;

			long start = System.currentTimeMillis();
			responseObj = m.invoke(null, jsonParGen, jsonParCli, jsonOutput);
			long duration = System.currentTimeMillis() - start;
			if (responseObj instanceof String) {
				response.setResponse((String) responseObj);
			} else if (responseObj instanceof ResponseGenericClient) {
				response = (ResponseGenericClient) responseObj;
				response.setLogReqPath(logReqPath);
				response.setMethod(method);
				response.setClasseManager(clazz);
			}
			response.setMethodDuration(String.valueOf(duration));
			response.setLogKey(logger.getLogKey());
			logger.info(new KeyValueLog("elapsed", response.getMethodDuration()));
			if (logger.isEnableSingleLog()) {
				response.setLogPath(logger.getLogPath());
			}
		} catch (Throwable e) {
			e.printStackTrace();
			logger.error("Errore in esecuzione del metodo " + method + " del client specifico", e);
			response.setResponse(ResponseGenericClient.RISPOSTA_KO);
			response.setException(e);
			response.setMessage(e.toString());
			logger.error(new KeyValueLog("Risposta GenericWsManager: ", response.toString()));
			return response;
		}

		// Loggatura risposta su file
		String responseClient2 = jsonOutput.toString();

		try {
			String logResPath = Utility.defineFilePath(path, jobNumberBci, logger.getLogKey(), "RES");
			logOnFile(responseClient2, logResPath, applicativoOpen);
			response.setLogResPath(logResPath);
		} catch (IOException e) {
			logger.error("Errore loggatura response");
		}

		try {
			String logErrPath = Utility.defineFilePath(path, jobNumberBci, logger.getLogKey(), "ERR");
			String errJson = errJson(response);
			if (!"".equals(errJson)) {
				logOnFile(errJson, logErrPath, applicativoOpen);
				response.setLogErrPath(logErrPath);
			}
		} catch (IOException e) {
			logger.error("Errore in loggatura error");
		}

		// Analisi della risposta
		if ("KO".equals(response.getResponse())) {
			logger.error("Errore in classe del client specifico");
			response.setResponse(ResponseGenericClient.RISPOSTA_KO);
			logger.error(new KeyValueLog("Risposta GenericWsManager: ", response.toString()));
			return response;
		}

		// Restituzione della risposta
		if (logger.isInfoEnabled()) {
			logger.info(new KeyValueLog("Risposta client2", responseClient2));
			logger.info(new KeyValueLog("Risposta GenericWsManager", response.toString()));
		}

		parOut.append(responseClient2);
		response.setResponse(ResponseGenericClient.RISPOSTA_OK);
		return response;
	}

	private static void logOnFile(String content, String logPath, String applicativoOpen) throws IOException {
		if (!logReqResErr) {
			return;
		}
		Object pj = UtilityJson.readValue(content, Object.class);
		String prettyjson = UtilityJson.prettyWriteValueAsString(pj);
		Utility.string2File(Utility.maskMessage(prettyjson, applicativoOpen), logPath);
	}

	private static String errJson(ResponseGenericClient response) throws JsonProcessingException {
		LogErrIfs error = new LogErrIfs(response);

		if ("".equals(error.getMessage())) {
			return "";
		}

		return UtilityJson.writeValueAsString(error);
	}

	public static <T, R> R applyFunctionWithParameters(FailableFunction<T, R> function, T input, AppOpePar alternativeAppOperPar, Logger logger) throws Exception {
		AppOpePar currentAppOpePar = AppOpeParClient;
		setupGeneric(alternativeAppOperPar, logger);
		R result = function.apply(input, logger);
		setupGeneric(currentAppOpePar, logger);
		return result;
	}

	private static AppOpePar AppOpeParClient = null;

	public static void setupGeneric(AppOpePar appOpePar, Logger logger) throws KeyManagementException, NoSuchAlgorithmException {
		AppOpeParClient = appOpePar;

		System.setProperty("file.encoding", "UTF-8");
		System.setProperty("sun.net.client.defaultConnectTimeout", "120000");
		System.setProperty("sun.net.client.defaultReadTimeout", "120000");
		if (appOpePar == null || logger == null) {
			return;
		}

		String protocol = appOpePar.getProtocol();
		String keyStoreFileName = appOpePar.getKeyStoreFileName();
		String keyStorePassword = appOpePar.getKeyStorePassword();
		String truststorePath = appOpePar.getTruststorePath();
		String truststorePwd = appOpePar.getTruststorePwd();
		String proxyIP = appOpePar.getProxyIP();
		String proxyPort = appOpePar.getProxyPort();
		String proxyUser = appOpePar.getProxyUser();
		String proxyPass = appOpePar.getProxyPass();

		if (isInitCalled && !JvmProperties.isInitDisabled(logger)) {
			logger.debug("Uso nuovo init");
			if (protocol != null && !"".equals(protocol)) {
				protocol = "TLSv" + protocol;
			} else {
				protocol = "TLS";
			}
			if ((keyStoreFileName != null && !"".equals(keyStoreFileName)) || (truststorePath != null && !"".equals(truststorePath))) {
				logger.debug(String.format("Set KeyStore con protcollo %s", protocol));
				JvmProperties.setKeyStore(protocol, logger);
			} else {
				logger.debug(String.format("Reset KeyStore con protcollo %s", protocol));
				JvmProperties.resetKeyStore(protocol);
			}
		} else {
			logger.info(new KeyValueLog(Logger.KEY_MESSAGGIO, "SetupGeneric invocato"), new KeyValueLog("keyStoreFileName", keyStoreFileName), new KeyValueLog("keyStorePassword", keyStorePassword),
					new KeyValueLog("truststorePath", truststorePath), new KeyValueLog("truststorePwd", truststorePwd));
			if (protocol != null && !"".equals(protocol)) {
				protocol = "TLSv" + protocol;
				System.clearProperty("https.protocols");
				System.setProperty("https.protocols", protocol);
			}

			System.clearProperty("javax.net.ssl.trustStore");
			System.clearProperty("javax.net.ssl.trustStorePassword");
			System.clearProperty("javax.net.ssl.keyStore");
			System.clearProperty("javax.net.ssl.keyStoreType");
			System.clearProperty("javax.net.ssl.keyStorePassword");

			// Impostazione truststore
			if (truststorePath != null && !truststorePath.trim().equals("")) {
				System.setProperty("javax.net.ssl.trustStore", truststorePath);
			}

			if (truststorePwd != null && !truststorePwd.trim().equals("")) {
				System.setProperty("javax.net.ssl.trustStorePassword", truststorePwd);
			}
			// Impostazione keystore
			if (keyStoreFileName != null && !"".equals(keyStoreFileName)) {
				System.setProperty("javax.net.ssl.keyStore", keyStoreFileName);
				System.setProperty("javax.net.ssl.keyStoreType", "JKS");
			}
			if (keyStorePassword != null && !"".equals(keyStorePassword)) {
				System.setProperty("javax.net.ssl.keyStorePassword", keyStorePassword);
			}
		}

		// Creazione proxy
		setProxy(proxyIP, proxyPort, proxyUser, proxyPass, logger);

	}

	public static void setProxy(String proxyIP, String proxyPort, String proxyUser, String proxyPass, String clazzManager) {
		setProxy(proxyIP, proxyPort, proxyUser, proxyPass, Logger.getLogger(clazzManager));
	}

	private static void setProxy(String proxyIP, String proxyPort, String proxyUser, String proxyPass, Logger logger) {
		logger.info(new KeyValueLog(Logger.KEY_MESSAGGIO, "SetProxy invocato"), new KeyValueLog("proxyIP", proxyIP), new KeyValueLog("proxyPort", proxyPort), new KeyValueLog("proxyUser", proxyUser),
				new KeyValueLog("proxyPass", proxyPass));

		if (proxyIP == null || proxyIP.trim().equals("")) {
			clearProxy(logger);
			return;
		}
		int proxyPortNum = Integer.parseInt(proxyPort);

		if (proxyUser != null && !proxyUser.equals("")) {
			logger.debug("Impostazione autenticazione proxy");
			Authenticator.setDefault(new CustomAuthenticator(proxyUser, proxyPass));
		}
		logger.debug(new KeyValueLog("Proxy IP", proxyIP));
		logger.debug(new KeyValueLog("Proxy Port", Integer.toString(proxyPortNum)));
		logger.debug(new KeyValueLog("Proxy User", proxyUser));
		if (proxyPass == null || proxyPass.equals("")) {
			logger.debug(new KeyValueLog("Proxy Psw", "non impostata"));
		} else {
			logger.debug(new KeyValueLog("Proxy Psw", "impostata"));
		}
		logger.debug("Impostazione proxy");
		System.setProperty("http.proxyHost", proxyIP);
		System.setProperty("http.proxyPort", Integer.toString(proxyPortNum));
		System.setProperty("https.proxyHost", proxyIP);
		System.setProperty("https.proxyPort", Integer.toString(proxyPortNum));
	}

	private static void clearProxy(Logger logger) {
		logger.info("Proxy non specificato. Rimuovo le properties");
		System.clearProperty("http.proxyHost");
		System.clearProperty("http.proxyPort");
		System.clearProperty("https.proxyHost");
		System.clearProperty("https.proxyPort");
		Authenticator.setDefault(null);
	}

	private static void debug(Logger logger, String msg) {
		GenericWsManager.debug(logger, msg, null);
	}

	private static void debug(Logger logger, String msg, Throwable e) {
		if (logger == null) {
			return;
		}
		if (e == null) {
			logger.debug("checkwsdl: " + msg);
		} else {
			logger.error("checkwsdl: " + msg, e);
		}
	}

	public static URL checkWsdl(String jarPath, String wsdlDirectoryPath, String wsdlName, String lockName, String projectName) throws Exception {
		return checkWsdl(jarPath, wsdlDirectoryPath, wsdlName, lockName, projectName, null);
	}

	public static URL checkWsdl(String jarPath, String wsdlDirectoryPath, String wsdlName, String lockName, String projectName, String pkg) throws Exception {
		String key = (new StringBuilder()).append(wsdlDirectoryPath).append("_").append(wsdlName).append("_").append(projectName).toString();
		Logger logger = null;

		if (pkg != null) {
			logger = Logger.getLogger(pkg);
			logger.debug("Chiamata checkWsdl");
		}

		if (risorse.get(key) != null) {
			return risorse.get(key);
		}

		Enumeration<URL> listaEnum = ClassLoader.getSystemClassLoader().getResources(wsdlDirectoryPath + "/" + wsdlName);
		List<URL> list = Collections.list(listaEnum);
		URL url = null;

		// Se trova un solo elemento nella lista vuol dire che devo recuperarlo
		// da li
		if (list != null && list.size() == 1 && list.get(0) != null) {
			url = list.get(0);
		}

		// Se sono presenti più elementi dal classloader cerco quello presente
		// nel file jar
		if (url == null && list != null && list.size() > 1) {
			for (URL urltmp : list) {
				if (urltmp.toString().startsWith("jar:file")) {
					url = urltmp;
					break;
				}
			}
		}

		if (logger != null) {
			logger.info(new KeyValueLog("Path risorsa wsdl richiesta", wsdlDirectoryPath + File.separator + wsdlName));
			logger.info(new KeyValueLog("Risorsa da checkWsdl", url == null ? null : url.toString()));
		}

		risorse.put(key, url);
		return url;
	}

	/**
	 * Vecchio metodo checkWsdl che recupera il WSDL dal jar.
	 * 
	 * @param jarPath
	 * @param wsdlDirectoryPath
	 * @param wsdlName
	 * @param lockName
	 * @param projectName
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public static URL checkWsdlFromJarPath(String jarPath, String wsdlDirectoryPath, String wsdlName, String lockName, String projectName) throws Exception {
		return GenericWsManager.checkWsdlFromJarPath(jarPath, wsdlDirectoryPath, wsdlName, lockName, projectName, null);
	}

	/**
	 * Vecchio metodo checkWsdl che recupera il WSDL dal jar.
	 * 
	 * @param jarPath
	 * @param wsdlDirectoryPath
	 * @param wsdlName
	 * @param lockName
	 * @param projectName
	 * @param pkg
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public static URL checkWsdlFromJarPath(String jarPath, String wsdlDirectoryPath, String wsdlName, String lockName, String projectName, String pkg) throws Exception {
		Logger logger = null;
		if (pkg != null) {
			logger = Logger.getLogger(pkg);
		}

		File jarFile = new File(jarPath);
		GenericWsManager.debug(logger, "jarFile->" + jarFile);
		File directory = jarFile.getParentFile();
		GenericWsManager.debug(logger, "directory->" + directory);
		File wsdlDirectoryFile = new File(directory, wsdlDirectoryPath);
		GenericWsManager.debug(logger, "wsdlDirectoryFile->" + wsdlDirectoryFile);
		File projectDirectoryFile = new File(directory, wsdlDirectoryPath + File.separator + projectName);
		GenericWsManager.debug(logger, "projectDirectoryFile->" + projectDirectoryFile);
		File lockFile = new File(directory, lockName);
		GenericWsManager.debug(logger, "lockFile->" + lockFile);
		File wsdlFile = new File(projectDirectoryFile, wsdlName);
		GenericWsManager.debug(logger, "wsdlFile->" + wsdlFile);
		if (!wsdlDirectoryFile.exists() || !projectDirectoryFile.exists()) {
			GenericWsManager.debug(logger, "createWsdlDirectory senza delete");
			createWsdlDirectory(jarFile, projectDirectoryFile, wsdlDirectoryPath, lockFile, false, logger);
		} else {
			if (!wsdlFile.exists() || wsdlFile.lastModified() < jarFile.lastModified()) {
				GenericWsManager.debug(logger, "createWsdlDirectory con delete");
				createWsdlDirectory(jarFile, projectDirectoryFile, wsdlDirectoryPath, lockFile, true, logger);
			}
		}
		GenericWsManager.debug(logger, "termine operazioni");
		return wsdlFile.toURI().toURL();
	}

	private static boolean setLock(File lockFile, Logger logger) throws Exception {
		if (checkLock(lockFile, logger)) {
			GenericWsManager.debug(logger, "Lock trovato");
			return false;
		}
		GenericWsManager.debug(logger, "Creazione file");
		if (!lockFile.createNewFile()) {
			GenericWsManager.debug(logger, "Creazione file non riuscita");
			checkLock(lockFile, logger);
			return false;
		}
		return true;
	}

	private static boolean checkLock(File lockFile, Logger logger) throws Exception {
		if (lockFile.exists()) {
			for (int i = 0; i < 25; i++) {
				if (lockFile.exists()) {
					try {
						Thread.sleep(TEMPO_ATTESA);
					} catch (InterruptedException e) {
					}
				} else {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	private static void createWsdlDirectory(File file, File directoryFile, String wsdlDirectoryPath, File lockFile, boolean deleteDirectoryFile, Logger logger) throws Exception {
		if (setLock(lockFile, logger)) {
			GenericWsManager.debug(logger, "Creazione wsdl");
			try {
				if (deleteDirectoryFile) {
					GenericWsManager.debug(logger, "Cancellazione directory: " + directoryFile);
					FileUtils.deleteDirectory(directoryFile);
				}
				GenericWsManager.debug(logger, "Creazione directory: " + directoryFile);
				directoryFile.mkdirs();
				JarFile jarFile = new JarFile(file, false, JarFile.OPEN_READ);
				Enumeration<JarEntry> e = jarFile.entries();
				while (e.hasMoreElements()) {
					JarEntry je = e.nextElement();
					String entryName = je.getName();
					if (entryName.startsWith(wsdlDirectoryPath)) {
						if (!je.isDirectory()) {
							String fileName = entryName.substring(wsdlDirectoryPath.length());
							GenericWsManager.debug(logger, "Scrittura file: " + directoryFile + " - " + fileName);
							IOUtils.copyLarge(jarFile.getInputStream(je), new FileOutputStream(new File(directoryFile, fileName)));
						} else {
							String dirName = entryName.substring(wsdlDirectoryPath.length() + 1);
							new File(directoryFile.getPath() + File.separator + dirName).mkdirs();
						}
					}
				}
				GenericWsManager.debug(logger, "Creazione file wsdl riuscita");
				jarFile.close();
			} catch (Exception e) {
				GenericWsManager.debug(logger, "checkWsdl: errore creazione wsdl", e);
				throw e;
			} finally {
				try {
					lockFile.delete();
				} catch (Throwable e) {
					GenericWsManager.debug(logger, "checkWsdl: errore cancellazione file lock: " + lockFile, e);
				}
			}
		}
	}

	public static void setTimeout(Map<String, Object> rc, String timeout, String clazzManager) throws NumberFormatException {
		Logger logger = null;
		if (clazzManager != null) {
			logger = Logger.getLogger(clazzManager);
		}
		int timeoutInt = 0;
		if (!"".equals(timeout)) {
			timeoutInt = Integer.parseInt(timeout.trim());
			if (timeoutInt > 0) {
				if (logger != null) {
					logger.debug("Inserite proprieta di timeout: " + timeoutInt);
				}
				//rc.put(BindingProviderProperties.REQUEST_TIMEOUT, timeoutInt);
				//rc.put(BindingProviderProperties.CONNECT_TIMEOUT, timeoutInt);
				rc.put("com.sun.xml.ws.request.timeout", timeoutInt);
				rc.put("com.sun.xml.ws.connect.timeout", timeoutInt);
			}
		}
	}

	/**
	 * @deprecated
	 * @param rc
	 * @param timeout
	 * @param logger
	 * @throws NumberFormatException
	 */
	/*
	@Deprecated
	public static void setTimeout(Map<String, Object> rc, String timeout, org.apache.log4j.Logger logger) throws NumberFormatException {
		int timeoutInt = 0;
		if (!"".equals(timeout)) {
			timeoutInt = Integer.parseInt(timeout.trim());
			if (timeoutInt > 0) {
				Logger.log4j2Logger(logger).debug("Inserite proprieta di timeout: " + timeoutInt);
				//rc.put(BindingProviderProperties.REQUEST_TIMEOUT, timeoutInt);
				//rc.put(BindingProviderProperties.CONNECT_TIMEOUT, timeoutInt);
			}
		}

	}
	*/

	/**
	 * Rimuove dal json i parametri che vengono impostati nel generic e non più
	 * da impostare nel singolo applicativo open
	 * 
	 * @param comuni
	 */
	@SuppressWarnings("unchecked")
	private static void puliziaParametri(JSONObject comuni, String clazz) {

		if (clazz.startsWith("it.ocsnet.cse.backoffice") || clazz.startsWith("it.ocsnet.printservice") || clazz.startsWith("it.ocsnet.ws.mef")) {
			return;
		}

		// Pulizia variabili non più utili ai singoli applicativi open
		// perchè impostati da setupGeneric
		comuni.put("KEYSTORE_SERVER_PATH", "");
		comuni.put("KEYSTORE_SERVER_PWD", "");
		comuni.put("PROXY_IP", "");
		comuni.put("PROXY_PORTA", "");
		comuni.put("PROXY_USER", "");
		comuni.put("PROXY_PWD", "");
		comuni.put("TRUSTSTORE_PATH", "");
		comuni.put("TRUSTSTORE_PWD", "");
	}

	public static void main(String[] args) {
		String parApps = "{\"LOGGER\": {\"SIZE\": \"0005\", \"MAX_BCK_INDEX\": \"01\", \"PATH\": \"\\/ocscod\\/logs\\/GenericWsClient.log\", \"LOG_LEVEL\": \"DEBUG\", \"JOB_NUMBER\": \"180458\", \"PATH_JAR\": \"\\/ocscod\\/libs\\/GenericWsClient.jar\", \"CLIENTE\": \"WIDI\"}, \"APPS\": [{\"APP_NAME\": \"OCSFTPMANAGER\", \"URL_WS\": \"\", \"PROXY_IP\": \"\", \"PROXY_PORT\": \"00000\", \"PROXY_USER\": \"\", \"PROXY_PWD\": \"\", \"TRUSTSTORE_PATH\": \"\", \"TRUSTSTORE_PWD\": \"\", \"KEYSTORE_SERVER_PATH\": \"\", \"KEYSTORE_SERVER_PWD\": \"\"}, {\"APP_NAME\": \"OCSSFTPMANAGER\", \"URL_WS\": \"\", \"PROXY_IP\": \"\", \"PROXY_PORT\": \"00000\", \"PROXY_USER\": \"\", \"PROXY_PWD\": \"\", \"TRUSTSTORE_PATH\": \"\", \"TRUSTSTORE_PWD\": \"\", \"KEYSTORE_SERVER_PATH\": \"\", \"KEYSTORE_SERVER_PWD\": \"\"}, {\"APP_NAME\": \"OCSZIPPER\", \"URL_WS\": \"\", \"PROXY_IP\": \"\", \"PROXY_PORT\": \"00000\", \"PROXY_USER\": \"\", \"PROXY_PWD\": \"\", \"TRUSTSTORE_PATH\": \"\", \"TRUSTSTORE_PWD\": \"\", \"KEYSTORE_SERVER_PATH\": \"\", \"KEYSTORE_SERVER_PWD\": \"\"}, {\"APP_NAME\": \"WIDIBAAUTHENTICATIONCLIENT\", \"URL_WS\": \"http:\\/\\/localhost:8080\", \"PROXY_IP\": \"\", \"PROXY_PORT\": \"00000\", \"PROXY_USER\": \"\", \"PROXY_PWD\": \"\", \"TRUSTSTORE_PATH\": \"\\/ocscod\\/cert\\/copergmps-truststore.jks\", \"TRUSTSTORE_PWD\": \"123456\", \"KEYSTORE_SERVER_PATH\": \"\", \"KEYSTORE_SERVER_PWD\": \"\"}], \"APPS_AUTH\": [{\"APPLICAZIONE\": \"WIDIBAAUTHENTICATIONCLIENT\", \"URL\": \"http:\\/\\/localhost:8080\", \"URL_TOKEN\": \"http:\\/\\/localhost:8080\", \"USERNAME\": \"APPW7CON\", \"PASSWORD\": \"1_s6KYIsz61La9e6YpDA6ehQ==\", \"CLIENT_ID\": \"c7consumerfinance\", \"CLIENT_SECRET\": \"12uOrYFRvfQRzXgD4IH2hQhvSKsAHflkDvREbYZn6k\", \"AZIENDA\": \"1030\", \"N_TENTATIVI\": \"10\", \"RAC\": \"\"}]}";
		init(parApps);
	}
}
