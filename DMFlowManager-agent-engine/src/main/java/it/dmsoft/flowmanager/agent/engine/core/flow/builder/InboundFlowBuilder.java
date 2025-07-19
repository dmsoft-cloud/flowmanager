package it.dmsoft.flowmanager.agent.engine.core.flow.builder;

import java.util.ArrayList;
import java.util.List;

import it.dmsoft.flowmanager.agent.engine.core.model.ExecutionFlowData;
import it.dmsoft.flowmanager.agent.engine.core.operations.CheckFileHash;
import it.dmsoft.flowmanager.agent.engine.core.operations.ChkIfsFileEmpty;
import it.dmsoft.flowmanager.agent.engine.core.operations.CopyFile;
import it.dmsoft.flowmanager.agent.engine.core.operations.CrtDbFile;
import it.dmsoft.flowmanager.agent.engine.core.operations.CrtDbFileIfNotExist;
import it.dmsoft.flowmanager.agent.engine.core.operations.core.ConstraintDependentOperation;
import it.dmsoft.flowmanager.agent.engine.core.operations.core.DependentOperation;
import it.dmsoft.flowmanager.agent.engine.core.operations.core.Operation;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.ChkIfsFileEmptyParam;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.CopyFileParam;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.CreateDbFileParam;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.HashCheckParam;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.OperationParams;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;

public class InboundFlowBuilder extends FlowBuilder{

	/*public InboundFlowBuilder db2File(ExecutionFlowData executionFlowData) {
		return this;
	}*/
	
	public FlowBuilder conversionToDestFile(ExecutionFlowData executionFlowData, OperationParams operationParams) throws Exception {
		return super.conversionToDestFile(executionFlowData, operationParams, Constants.SI.equals(executionFlowData.getFlowBypassQtemp()));
	}
	
	public FlowBuilder createDbFileQtemp(ExecutionFlowData executionFlowData, OperationParams operationParams) {
		Operation<CreateDbFileParam> crtDbFile = new CrtDbFile();
		
		CreateDbFileParam createDbFileParam = getCreateDbFileParam(executionFlowData, operationParams);
		
		createDbFileParam.setLibreria(Constants.QTEMP);
		crtDbFile.setParameters(createDbFileParam);
		flow.addOperation(crtDbFile);

		return this;
	}
	
	public FlowBuilder fromQtempToDestinationFileOperation(ExecutionFlowData executionFlowData, OperationParams operationParams) {

		Operation<CreateDbFileParam> fromQtempToDestinationFile = new CrtDbFileIfNotExist();
	
		fromQtempToDestinationFile.setParameters(getCreateDbFileParam(executionFlowData, operationParams));
		
		flow.addOperation(fromQtempToDestinationFile);

		return this;
	}
	
	public FlowBuilder cpyFile(ExecutionFlowData executionFlowData, OperationParams operationParams) {
		
		DependentOperation<CopyFileParam> copyFile = new CopyFile();
		
		CopyFileParam copyFileParam= new CopyFileParam();
		
		updateGenericAs400(executionFlowData, copyFileParam);
		copyFileParam.setFromLibrary(Constants.QTEMP);
		copyFileParam.setFromFile(executionFlowData.getFlowFile());
		copyFileParam.setToLibrary(executionFlowData.getFlowLibreria());
		copyFileParam.setToFile(executionFlowData.getFlowFile());
		copyFileParam.setMbrOpt(executionFlowData.getFlowModAcquisizione());		
		
		copyFile.setOperationParams(operationParams);
		
		copyFile.setParameters(copyFileParam);
		
		flow.addOperation(copyFile);
		
		return this;
	}
	
	public FlowBuilder checkIfsFileEmpty(ExecutionFlowData executionFlowData, OperationParams operationParams) {
		ChkIfsFileEmptyParam chkIfsFileEmptyParam = new ChkIfsFileEmptyParam();	
		
		updateGenericAs400(executionFlowData, chkIfsFileEmptyParam);
		
		List<String> checkedFiles = new ArrayList<String>();

		
		for (String fileName : operationParams.getFileNames()) {
			checkedFiles.add(executionFlowData.getFlowFolder() + Constants.PATH_DELIMITER + fileName);
		}
		
		chkIfsFileEmptyParam.setCheckedFiles(checkedFiles);
		
		
		ConstraintDependentOperation<ChkIfsFileEmptyParam, Boolean> chIfsOperation = new ChkIfsFileEmpty();
		chIfsOperation.setOperationParams(operationParams);
		chIfsOperation.setOtgffana(executionFlowData);
		chIfsOperation.setParameters(chkIfsFileEmptyParam);
		
		flow.addOperation(chIfsOperation);
		
		return this;
		
	}
	
	public FlowBuilder checkHashFile(ExecutionFlowData executionFlowData, OperationParams operationParams, boolean write) throws Exception {
        HashCheckParam hashCheckParam = new HashCheckParam();
        hashCheckParam.setFileNames(operationParams.getTrasmissionFiles());
        hashCheckParam.setWrite(write);
        
        ConstraintDependentOperation<HashCheckParam, Boolean> checkHashOperation = new CheckFileHash();
        checkHashOperation.setOperationParams(operationParams);
        checkHashOperation.setOtgffana(executionFlowData);
        checkHashOperation.setParameters(hashCheckParam);

        flow.addOperation(checkHashOperation);
        
        return this;
    }
	
	
}
