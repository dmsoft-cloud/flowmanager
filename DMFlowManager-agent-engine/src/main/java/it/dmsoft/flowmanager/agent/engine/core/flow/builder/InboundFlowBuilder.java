package it.dmsoft.flowmanager.agent.engine.core.flow.builder;

import java.util.ArrayList;
import java.util.List;

import it.dmsoft.flowmanager.agent.engine.core.db.dao.OtgffhashDAO;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.Otgffana;
import it.dmsoft.flowmanager.agent.engine.core.exception.OperationException;
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
import it.dmsoft.flowmanager.agent.engine.core.utils.HashUtils;

public class InboundFlowBuilder extends FlowBuilder{

	/*public InboundFlowBuilder db2File(Otgffana otgffana) {
		return this;
	}*/
	
	public FlowBuilder conversionToDestFile(Otgffana otgffana, OperationParams operationParams) throws Exception {
		return super.conversionToDestFile(otgffana, operationParams, Constants.SI.equals(otgffana.getFana_Bypass_Qtemp()));
	}
	
	public FlowBuilder createDbFileQtemp(Otgffana otgffana, OperationParams operationParams) {
		Operation<CreateDbFileParam> crtDbFile = new CrtDbFile();
		
		CreateDbFileParam createDbFileParam = getCreateDbFileParam(otgffana, operationParams);
		
		createDbFileParam.setLibreria(Constants.QTEMP);
		crtDbFile.setParameters(createDbFileParam);
		flow.addOperation(crtDbFile);

		return this;
	}
	
	public FlowBuilder fromQtempToDestinationFileOperation(Otgffana otgffana, OperationParams operationParams) {

		Operation<CreateDbFileParam> fromQtempToDestinationFile = new CrtDbFileIfNotExist();
	
		fromQtempToDestinationFile.setParameters(getCreateDbFileParam(otgffana, operationParams));
		
		flow.addOperation(fromQtempToDestinationFile);

		return this;
	}
	
	public FlowBuilder cpyFile(Otgffana otgffana, OperationParams operationParams) {
		
		DependentOperation<CopyFileParam> copyFile = new CopyFile();
		
		CopyFileParam copyFileParam= new CopyFileParam();
		
		updateGenericAs400(otgffana, copyFileParam);
		copyFileParam.setFromLibrary(Constants.QTEMP);
		copyFileParam.setFromFile(otgffana.getFana_File());
		copyFileParam.setToLibrary(otgffana.getFana_Libreria());
		copyFileParam.setToFile(otgffana.getFana_File());
		copyFileParam.setMbrOpt(otgffana.getFana_Mod_Acquisizione());		
		
		copyFile.setOperationParams(operationParams);
		
		copyFile.setParameters(copyFileParam);
		
		flow.addOperation(copyFile);
		
		return this;
	}
	
	public FlowBuilder checkIfsFileEmpty(Otgffana otgffana, OperationParams operationParams) {
		ChkIfsFileEmptyParam chkIfsFileEmptyParam = new ChkIfsFileEmptyParam();	
		
		updateGenericAs400(otgffana, chkIfsFileEmptyParam);
		
		List<String> checkedFiles = new ArrayList<String>();

		
		for (String fileName : operationParams.getFileNames()) {
			checkedFiles.add(otgffana.getFana_Folder() + Constants.PATH_DELIMITER + fileName);
		}
		
		chkIfsFileEmptyParam.setCheckedFiles(checkedFiles);
		
		
		ConstraintDependentOperation<ChkIfsFileEmptyParam, Boolean> chIfsOperation = new ChkIfsFileEmpty();
		chIfsOperation.setOperationParams(operationParams);
		chIfsOperation.setOtgffana(otgffana);
		chIfsOperation.setParameters(chkIfsFileEmptyParam);
		
		flow.addOperation(chIfsOperation);
		
		return this;
		
	}
	
	public FlowBuilder checkHashFile(Otgffana otgffana, OperationParams operationParams, boolean write) throws Exception {
        HashCheckParam hashCheckParam = new HashCheckParam();
        hashCheckParam.setFileNames(operationParams.getTrasmissionFiles());
        hashCheckParam.setWrite(write);
        
        ConstraintDependentOperation<HashCheckParam, Boolean> checkHashOperation = new CheckFileHash();
        checkHashOperation.setOperationParams(operationParams);
        checkHashOperation.setOtgffana(otgffana);
        checkHashOperation.setParameters(hashCheckParam);

        flow.addOperation(checkHashOperation);
        
        return this;
    }
	
	
}
