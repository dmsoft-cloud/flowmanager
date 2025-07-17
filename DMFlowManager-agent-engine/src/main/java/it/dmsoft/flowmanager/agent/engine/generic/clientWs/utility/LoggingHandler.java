package it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility;

import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class LoggingHandler implements SOAPHandler<SOAPMessageContext> {

	private String loggerClass = null;
	private StackTraceElement[] stackTrace = null;

	/**
	 * @deprecated Utilizzare
	 *             {@link it.ocsnet.clientWs.utility.httpLogger.LoggingHttpHandler#addLogger(Class)}
	 * @param loggerClass
	 */
	public LoggingHandler(Class<?> loggerClass) {
		super();
		setStackTrace(Thread.currentThread().getStackTrace());
		this.loggerClass = loggerClass.getCanonicalName();
	}

	/**
	 * 
	 * @deprecated Utilizzare
	 *             {@link it.ocsnet.clientWs.utility.httpLogger.LoggingHttpHandler#addLogger(Class)}
	 * @param loggerClass
	 * @param logRequestInfo
	 * @param logResponseInfo
	 */
	public LoggingHandler(Class<?> loggerClass, boolean logRequestInfo, boolean logResponseInfo) {
		this(loggerClass);
	}

	/**
	 * @deprecated Utilizzare
	 *             {@link it.ocsnet.clientWs.utility.httpLogger.LoggingHttpHandler#addLogger(Class)}
	 *
	 */
	@Deprecated
	public LoggingHandler() {
		super();
		setStackTrace(Thread.currentThread().getStackTrace());
		int ind = 0;
		boolean trovato = false;
		for (ind = 0; ind < stackTrace.length; ind++) {
			if (trovato) {
				break;
			}
			if (stackTrace[ind].getClassName().equals(LoggingHandler.class.getCanonicalName())) {
				trovato = true;
			}
		}
		if (!trovato) {
			ind = 0;
		}
		loggerClass = Thread.currentThread().getStackTrace()[ind].getClassName();
	}

	public String getLoggerClass() {
		if (loggerClass == null) {
			loggerClass = "it.ocsnet";
		}
		return loggerClass;
	}

	public void setLoggerClass(String loggerClass) {
		this.loggerClass = loggerClass;
	}

	public void close(MessageContext arg0) {
		
	}

	/**
	 * Non più implementata a favore della classe
	 * {@link it.ocsnet.clientWs.utility.httpLogger.LoggingHttpHandler{
	 */
	public boolean handleFault(SOAPMessageContext arg0) {
		return true;
	}

	/**
	 * Non più implementata a favore della classe
	 * {@link it.ocsnet.clientWs.utility.httpLogger.LoggingHttpHandler{
	 */
	public boolean handleMessage(SOAPMessageContext arg0) {
		return true;
	}

	public Set<QName> getHeaders() {
		// TODO Auto-generated method stub
		return null;
	}

	public StackTraceElement[] getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(StackTraceElement[] stackTrace) {
		this.stackTrace = stackTrace;
	}	
}
