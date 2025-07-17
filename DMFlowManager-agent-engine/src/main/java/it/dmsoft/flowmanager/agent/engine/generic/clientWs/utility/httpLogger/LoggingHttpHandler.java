package it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.httpLogger;

import java.util.HashSet;
import java.util.Set;

import org.json.simple.JSONObject;

import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class LoggingHttpHandler {
	private LoggingHttpHandler() {

	}

	// jsonParCli aggiunto per possibili sviluppi futuri
	public static void addLogger(Class<?> clazz, JSONObject parGen, JSONObject jsonParCli) {
		JSONObject comuni = (JSONObject) parGen.get("comuni");

		String endPoint = (String) comuni.get("URL_WS");
		addLogger(clazz, endPoint);
	}

	// jsonParCli aggiunto per possibili sviluppi futuri
	public static void addLogger(Logger logger, JSONObject parGen, JSONObject jsonParCli) {
		JSONObject comuni = (JSONObject) parGen.get("comuni");

		String endPoint = (String) comuni.get("URL_WS");
		logger.debug("Endpoint per logger " + endPoint);
		if (endPoint == null || !"".equals(endPoint)) {
			addLogger(logger);
		}
	}

	public static void addLogger(Class<?> clazz, String endPoint) {
		Logger.getLogger(clazz).debug("Endpoint per logger " + endPoint);
		if (endPoint == null || !"".equals(endPoint)) {
			addLogger(clazz);
		}
	}

	private static void addLogger(Class<?> clazz) {
		addLogger(Logger.getLogger(clazz));
	}

	private static void addLogger(Logger logger) {
		logger.debug("Avviato log per " + logger.getClass().getCanonicalName());
		System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
		System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
		System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dumpTreshold", "999999");
		System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dumpTreshold", "999999");
		int level = Logger.LEVEL_INFO;

		Set<String> managers = new HashSet<String>();
		managers.add("it.ocsnet.compassservice.client.CompassServiceAddDocumentManager");
		managers.add("it.ocsnet.compassservice.client.CompassServiceCheckListManager");
		managers.add("it.ocsnet.compassservice.client.CompassServiceCheckManager");
		managers.add("it.ocsnet.compassservice.client.CompassServiceGetDocumentByPraticaAndDoctypeManager");
		managers.add("it.ocsnet.compassservice.client.CompassServiceManager");
		managers.add("it.ocsnet.compassservice.client.CompassServiceUploadDocumentsManager");

		if (managers.contains(logger.getClass().getName())) {
			level = Logger.LEVEL_DEBUG;
		}
		it.dmsoft.flowmanager.agent.engine.generic.utility.logger.OutErrLogger.setOutAndErrToLog(logger, level);
	}
}
