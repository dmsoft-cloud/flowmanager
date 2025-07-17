package it.dmsoft.flowmanager.agent.engine.zip;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.naming.OperationNotSupportedException;

import org.json.simple.JSONObject;

import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.OcsBase64;
import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.OcsXmlConverter;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.AppOpePar;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.GenericWsManager;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.ResponseGenericClient;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.ResponseWrapper;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.KeyValueLog;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;
import it.dmsoft.flowmanager.agent.engine.utility.OCSZipper;
import it.dmsoft.flowmanager.agent.engine.zip.model.ZipRequest;
import it.dmsoft.flowmanager.agent.engine.zip.model.ZipRequest.Operation;
import it.dmsoft.flowmanager.agent.engine.zip.model.ZipResponse;

public class ZipManager {
	private static Logger logger;

	public static ResponseGenericClient runWs(JSONObject parGen, JSONObject parCli, JSONObject output) {
		logger = Logger.getLogger(ZipManager.class);
		logger.debug("Chiamato runWs di " + ZipManager.class.getSimpleName() + ".class");

		ResponseGenericClient responseWs = new ResponseGenericClient(ResponseGenericClient.RISPOSTA_KO);

		// Parametri specifici
		if (logger.isDebugEnabled()) {			
			logger.debug("Parametri specifici: " + parCli.toString());
		}
		ZipRequest request = null;
		try {
			request = createRequest(parCli);
		} catch (Exception e) {
			logger.error("Errore in creazione richiesta", e);
			responseWs.setException(e);
			return responseWs;
		}

		ResponseWrapper<ZipResponse> res = new ResponseWrapper<ZipResponse>();

		try {
			res = runWsCore(request, responseWs);
			res.setResponseWs(responseWs);
		} catch (Exception e) {
			logger.error("Errore in invocazione del servizio zip/unzip", e);
			responseWs.setException(e);
			return responseWs;
		}

		try {
			setResponse(res.getResponse(), parCli, output);
		} catch (Exception e) {
			logger.error("Errore in elaborazione risposta", e);
			responseWs.setException(e);
			return responseWs;
		}

		responseWs.setResponse(ResponseGenericClient.RISPOSTA_OK);
		return responseWs;
	}

	private static ResponseWrapper<ZipResponse> runWsCore(ZipRequest request, ResponseGenericClient responseWs) throws IOException, OperationNotSupportedException {
		ResponseWrapper<ZipResponse> res = new ResponseWrapper<ZipResponse>();
		logger.info(new KeyValueLog("Dati di input", request.toString()));
		
		ZipResponse response = new ZipResponse();
		if (responseWs == null) {
			responseWs = new ResponseGenericClient();
		}
		res.setResponseWs(responseWs);
		res.setResponse(response);
		long start = System.currentTimeMillis();
		try {
			Operation operation = request.getOperation();
			File sourceFile = new File(request.getSource());
			String destination = request.getDestination();
			if (Operation.ZIP.equals(operation)) {
				logger.info("Invocazione del servizio zip");
				OCSZipper.zip(logger, sourceFile, destination);
			} else if (Operation.UNZIP.equals(operation)) {
				if (!OCSZipper.isArchive(sourceFile)) {
					responseWs.setResponse(ResponseGenericClient.RISPOSTA_KO);
					String message = "Non un file zip!";
					logger.error(message);
					throw new IOException(message);
				}
				logger.info("Invocazione del servizio unzip");
				OCSZipper.unzip(logger, sourceFile, destination);
			} else {
				String message = operation.toString() + " non supportata!";
				logger.error(message);
				throw new OperationNotSupportedException(message);
			}
			response.setOutcome(ZipResponse.Outcome.OK.getStringValue());
			responseWs.setResponse(ResponseGenericClient.RISPOSTA_OK);
		} finally {
			long duration = System.currentTimeMillis() - start;
			logger.info("Durata servizio receive: " + duration + " ms");
			res.getResponseWs().setServiceInvocationDuration(duration);
		}

		return res;
	}

	public static ResponseWrapper<ZipResponse> exposedRun(AppOpePar parametri, Logger loggerExt, String source, String destination, String operation) throws KeyManagementException, NoSuchAlgorithmException, OperationNotSupportedException, IOException  {
		ZipRequest request = new ZipRequest(source, destination, operation);

		logger = loggerExt == null ? Logger.getLogger(ZipManager.class) : loggerExt;
		GenericWsManager.setupGeneric(parametri, logger);

		return runWsCore(request, null);
	}

	private static ZipRequest createRequest(JSONObject jsonZipRequest01) {
		ZipRequest zipRequest01 = new ZipRequest();
		if (jsonZipRequest01.containsKey("Source")) {
			zipRequest01.setSource((String) jsonZipRequest01.get("Source"));
		}
		if (jsonZipRequest01.containsKey("Destination")) {
			zipRequest01.setDestination((String) jsonZipRequest01.get("Destination"));
		}
		if (jsonZipRequest01.containsKey("Operation")) {
			zipRequest01.setOperation((String) jsonZipRequest01.get("Operation"));
		}
		return zipRequest01;
	}

	@SuppressWarnings("unchecked")
	private static void setResponse(ZipResponse zipResponse01, JSONObject parCli, JSONObject jsonZipResponse01) {
		OcsBase64.setFileNames(parCli);
		OcsXmlConverter.setNameSpaces(parCli);
		if (zipResponse01.getOutcome() != null) {
			jsonZipResponse01.put("Outcome", zipResponse01.getOutcome());
		}
	}

}
