package it.dmsoft.flowmanager.agent.engine.core.operations;

import it.dmsoft.flowmanager.agent.engine.core.operations.core.ConstraintTrasmission;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.ResponseWrapper;
import it.dmsoft.flowmanager.agent.engine.sftp.model.SftpResponse;

public class DummyOperation extends ConstraintTrasmission<ResponseWrapper<SftpResponse>> {


	@Override
	public ResponseWrapper<SftpResponse> executeTrasmission() throws Exception {
		ResponseWrapper<SftpResponse> ret = new ResponseWrapper<SftpResponse>();
		
		return ret;
	}


	@Override
	public void updateOperationParams(ResponseWrapper<SftpResponse> data) throws Exception {
		System.out.println("ciao");
	}

	
}
