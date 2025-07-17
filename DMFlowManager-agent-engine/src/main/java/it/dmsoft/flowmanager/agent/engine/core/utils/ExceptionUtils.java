package it.dmsoft.flowmanager.agent.engine.core.utils;

import java.util.List;

import it.dmsoft.flowmanager.agent.engine.core.exception.OperationException;
import it.dmsoft.flowmanager.agent.engine.ftp.model.FtpInfo;
import it.dmsoft.flowmanager.agent.engine.ftp.model.FtpResponse;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.ResponseGenericClient;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.ResponseWrapper;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;
import it.dmsoft.flowmanager.agent.engine.sftp.model.SftpInfo;
import it.dmsoft.flowmanager.agent.engine.sftp.model.SftpResponse;
import it.dmsoft.flowmanager.agent.engine.zip.model.ZipResponse;

public class ExceptionUtils {
	
	private enum ResponseHandler {
		FtpResponse(FtpResponse.class) {
			@Override
			public void handle(OperationException e, Object response) {
				FtpResponse ftpResponse = (FtpResponse) response;
				
				List<FtpInfo> ftpInfos = ftpResponse.getFtpInfo();
				
				if(ftpInfos == null) {
					return;
				}
				
				for (FtpInfo ftpInfo : ftpInfos) {
					e.addKeyValueLog(ftpInfo.getCodice().value(), ftpInfo.getDescrizione());
				}
				
			}
		},
		
		SftpResponse(SftpResponse.class) {
			@Override
			public void handle(OperationException e, Object response) {
				SftpResponse sftpResponse = (SftpResponse) response;
				
				List<SftpInfo> sftpInfos = sftpResponse.getSftpInfo();
				
				if(sftpInfos == null) {
					return;
				}
				
				for (SftpInfo sftpInfo : sftpInfos) {
					e.addKeyValueLog(sftpInfo.getCodice().value(), sftpInfo.getDescrizione());
				}
				
			}
		},
		
		ZipResponse(ZipResponse.class) {
			@Override
			public void handle(OperationException e, Object response) {
				ZipResponse zipResponse = (ZipResponse) response;
				e.addKeyValueLog(Constants.OUTCOME, zipResponse.getOutcome());				
			}
		}
		
		;
		
		private Class<?> responseClazz;
		
		private ResponseHandler(Class<?> responseClazz) {
			this.responseClazz = responseClazz;
		}
		
		public abstract void handle(OperationException e, Object response);
		
		public Class<?> getResponseClazz() {
			return this.responseClazz;
		}
		
		public static ResponseHandler get(Class<?> responseClazz) {
			for(ResponseHandler ra : ResponseHandler.values()) {
				if (ra.getResponseClazz().equals(responseClazz)) {
					return ra;
				}
			}
			
			return null;
		}
	}
	
	public static void throwExceptionIfNecessary(ResponseWrapper<?> responseWrapper) throws OperationException {	
		Logger logger = Logger.getLogger(ExceptionUtils.class);
		logger.info("Esito" + responseWrapper.getResponseWs().getResponse());
		
		if (Constants.KO.equals(responseWrapper.getResponseWs().getResponse())) {			
			//TODO LANCIARE ECCEZIONE E GESTIRE I VARI TIPI POSSIBILI DI RESPONSE WRAPPATA (IO USEREI ENUM)
			ResponseGenericClient resClient = responseWrapper.getResponseWs(); 
			OperationException e = new OperationException(resClient.getMessage(), resClient.getException());
			
			Object response = responseWrapper.getResponse();
			ResponseHandler ra = ResponseHandler.get(response.getClass());
			ra.handle(e, response);
			
			throw e;
		}
	}

}
