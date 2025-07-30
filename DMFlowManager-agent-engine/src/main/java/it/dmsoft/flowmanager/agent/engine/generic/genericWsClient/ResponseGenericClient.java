package it.dmsoft.flowmanager.agent.engine.generic.genericWsClient;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;

public class ResponseGenericClient {
	// Dimensione massima definita dalla funzione RPGLE JNIString2Alfa
	public static final String RISPOSTA_OK = "OK";
	public static final String RISPOSTA_KO = "KO";

	private static final int MAX_LEN_MESSAGE = 3200;
	private static final String TAG_MESSAGE = "Message";
	private static final String TAG_MESSAGE_OPEN = "<" + TAG_MESSAGE + ">";
	private static final String TAG_MESSAGE_CLOSE = "</" + TAG_MESSAGE + ">";
	private static final String TAG_CLASS = "Class";
	private static final String TAG_CLASS_OPEN = "<" + TAG_CLASS + ">";
	private static final String TAG_CLASS_CLOSE = "</" + TAG_CLASS + ">";
	private static final String TAG_METHOD = "Method";
	private static final String TAG_METHOD_OPEN = "<" + TAG_METHOD + ">";
	private static final String TAG_METHOD_CLOSE = "</" + TAG_METHOD + ">";

	private String response = null;
	private String message = null;
	private String classeManager = null;
	private String method = null;
	private Throwable exception = null;
	private String methodDuration = null;
	private String serviceInvocationDuration = null;
	private List<String> messagges = null;
	private String logKey;
	private String logPath;
	private String logReqPath;
	private String logResPath;
	private String logErrPath;

	public ResponseGenericClient(String response, String message, Exception exception) {
		super();
		setResponse(response);
		setMessage(message);
		setException(exception);
	}

	public ResponseGenericClient(String response, String message) {
		this(response, message, null);
	}

	public ResponseGenericClient(String response) {
		this(response, null, null);
	}

	public ResponseGenericClient() {
		this(null, null, null);
	}

	public String getResponse() {
		if (this.response == null || "".equals(this.response)) {
			setResponse(ResponseGenericClient.RISPOSTA_OK);
		}
		return response.trim();
	}

	public void setResponse(String response) {
		this.messagges = null;
		this.response = response;
	}

	public String getMessageWithTag() {
		return TAG_MESSAGE_OPEN + getMessage() + TAG_MESSAGE_CLOSE;

	}

	public String getMessage() {
		if (this.message == null) {
			this.message = (this.getException() != null ? this.getException().toString() : "");
		}
		message = message.trim();

		try {
			byte[] messageBytes = message.getBytes();
			message = new String(messageBytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			LogManager.getLogger(getClass()).warn("Impossibile codificare in UTF-8: " + message);
		}

		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Throwable getException() {
		return exception;
	}

	public void setException(Throwable exception) {
		this.exception = exception;
	}

	private List<String> getMessagges() {
		if (messagges == null) {
			messagges = Arrays.asList(getMessageWithTag().split("(?<=\\G.{" + MAX_LEN_MESSAGE + "})"));
		}
		return messagges;
	}

	public String getMessaggesItem(int ind) {
		if (ind < 0 || (ind + 1 > getMessagges().size())) {
			return "";
		}
		return getMessagges().get(ind);
	}

	public boolean messageExceeds() {
		return (getMessage().length() > MAX_LEN_MESSAGE);
	}

	public int messaggesLen() {
		if ("".equals(getMessage())) {
			return 0;
		}
		return getMessagges().size();
	}

	public String getClasseManagerWithTag() {
		return TAG_CLASS_OPEN + getClasseManager() + TAG_CLASS_CLOSE;
	}

	public String getClasseManager() {
		if (classeManager == null) {
			classeManager = "";
		}
		return classeManager;
	}

	public void setClasseManager(String classeManager) {
		this.classeManager = classeManager;
	}

	public String getMethodWithTag() {
		return TAG_METHOD_OPEN + getMethod() + TAG_METHOD_CLOSE;

	}

	public String getMethod() {
		if (method == null) {
			method = "";
		}
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setMethodDuration(String methodDuration) {
		this.methodDuration = methodDuration;
	}

	public void setServiceInvocationDuration(String serviceInvocationDuration) {
		this.serviceInvocationDuration = serviceInvocationDuration;
	}

	public void setServiceInvocationDuration(long serviceInvocationDuration) {
		this.serviceInvocationDuration = String.valueOf(serviceInvocationDuration);
	}

	public String getServiceInvocationDuration() {
		if (serviceInvocationDuration == null) {
			serviceInvocationDuration = "";
		}
		if (serviceInvocationDuration.contains("ms")) {
			serviceInvocationDuration = serviceInvocationDuration.replaceAll("ms", "");
		}
		return serviceInvocationDuration;
	}

	public String getMethodDuration() {
		if (methodDuration == null) {
			methodDuration = "";
		}
		return methodDuration;
	}

	public String getLogKey() {
		return logKey;
	}

	public void setLogKey(String logKey) {
		this.logKey = logKey;
	}

	public String getLogPath() {
		return logPath;
	}

	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}
	
	public String getLogReqPath() {
		return logReqPath;
	}

	public void setLogReqPath(String logReqPath) {
		this.logReqPath = logReqPath;
	}
	
	public String getLogResPath() {
		return logResPath;
	}

	public void setLogResPath(String logResPath) {
		this.logResPath = logResPath;
	}
	
	public String getLogErrPath() {
		return logErrPath;
	}

	public void setLogErrPath(String logErrPath) {
		this.logErrPath = logErrPath;
	}

	@Override
	public String toString() {
		return "ResponseGenericClient [response=" + response + ", message=" + message + ", classeManager=" + classeManager + ", method=" + method + ", exception=" + exception + ", methodDuration="
				+ methodDuration + ", serviceInvocationDuration=" + serviceInvocationDuration + "]";
	}
}
