package it.dmsoft.flowmanager.agent.engine.core.flow.builder;

import java.util.Arrays;

import org.springframework.stereotype.Service;

import it.dmsoft.flowmanager.agent.engine.core.manager.FlowManager.Replacer;
import it.dmsoft.flowmanager.agent.engine.core.model.ExecutionFlowData;
import it.dmsoft.flowmanager.agent.engine.core.operations.CheckFileHash;
import it.dmsoft.flowmanager.agent.engine.core.operations.ChkDbFileEmpty;
import it.dmsoft.flowmanager.agent.engine.core.operations.ChkObj;
import it.dmsoft.flowmanager.agent.engine.core.operations.CrtFile;
import it.dmsoft.flowmanager.agent.engine.core.operations.DropTmpExportTable;
import it.dmsoft.flowmanager.agent.engine.core.operations.SelectFileColumns;
import it.dmsoft.flowmanager.agent.engine.core.operations.core.ConstraintDependentOperation;
import it.dmsoft.flowmanager.agent.engine.core.operations.core.DependentOperation;
import it.dmsoft.flowmanager.agent.engine.core.operations.core.Operation;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.ChkDbFileEmptyParam;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.ChkObjParam;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.CreateDbFileParam;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.CreateFileParam;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.DropTmpExportTableParam;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.HashCheckParam;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.OperationParams;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.SelectFileColumnsParam;
import it.dmsoft.flowmanager.agent.engine.core.properties.PropertiesConstants;
import it.dmsoft.flowmanager.agent.engine.core.properties.PropertiesUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.StringUtils;
import it.dmsoft.flowmanager.common.domain.Domains.YesNo;

@Service("outboundFlowBuilder")
public class OutboundFlowBuilder extends FlowBuilder {

	public FlowBuilder createFile(ExecutionFlowData executionFlowData, OperationParams operationParams) {

		if (YesNo.YES.equals(executionFlowData.getFlowIntegrityCheck())
				&& !StringUtils.isNullOrEmpty(executionFlowData.getFlowFlNameSemaforo())) {
			
			Operation<CreateFileParam> crtFile = new CrtFile();
			CreateFileParam createFileParam = new CreateFileParam();

			createFileParam.setFileName(operationParams.getSempahoreFile());
			createFileParam.setFolder(executionFlowData.getFlowFolder());
			createFileParam.setDbType(executionFlowData.getOrigin().getDbType());

			updateGenericConnectionParams(executionFlowData, createFileParam);
			crtFile.setParameters(createFileParam);

			flow.addOperation(crtFile);
		}

		return this;
	}

	public FlowBuilder createIfsFile(ExecutionFlowData executionFlowData, OperationParams operationParams) throws Exception {
	
		if(operationParams.getFileNames()== null || operationParams.getFileNames().size() == 0) {
			String replaceLocal = Replacer.replace(executionFlowData.getFlowFileName(), executionFlowData, operationParams);
			Operation<CreateFileParam> crtFile = new CrtFile();
			CreateFileParam createFileParam = new CreateFileParam();
			operationParams.setFileNames(Arrays.asList(replaceLocal));
			operationParams.setTrasmissionFiles(Arrays.asList(replaceLocal));
			operationParams.setTrasmissionFolder(executionFlowData.getFlowFolder());
			createFileParam.setFileName(replaceLocal);
			createFileParam.setFolder(executionFlowData.getFlowFolder());
			updateGenericConnectionParams(executionFlowData, createFileParam);
			crtFile.setParameters(createFileParam);

	
			flow.addOperation(crtFile);
		}

		return this;
	}
	
	public FlowBuilder crtDb2FileIfNotExist(ExecutionFlowData executionFlowData, OperationParams operationParams) {
		
		CreateDbFileParam createDbFileParam = new CreateDbFileParam();
		updateGenericConnectionParams(executionFlowData, createDbFileParam);
		createDbFileParam.setFile(executionFlowData.getFlowFile());
		createDbFileParam.setLibreria(StringUtils.setDefault(operationParams.getLibrary(), executionFlowData.getFlowLibreria()));
		createDbFileParam.setSrcFile(executionFlowData.getFlowFileSource());
		createDbFileParam.setSrcLibreria(executionFlowData.getFlowLibSource());
		createDbFileParam.setSrcMembro(executionFlowData.getFlowMembroSource());
		createDbFileParam.setCreaSeNonEsiste(YesNo.YES.equals(executionFlowData.getFlowCreaVuoto()));
		createDbFileParam.setRecordLength(executionFlowData.getFlowLunghezzaFlFlat());
		
		DependentOperation<ChkObjParam> chkObj = new ChkObj();
		chkObj.setParameters(getChkObjParam(executionFlowData, createDbFileParam));
		chkObj.setOperationParams(operationParams);
		
		try {
		chkObj.execute();
		}
		catch(Exception e) {
			operationParams.setSkipCpyFrmFile(true);
		}
		
		if(operationParams.getSkipCpyFrmFile() == false) {
			Operation<ChkDbFileEmptyParam> chOperation = new ChkDbFileEmpty();
			chOperation.setParameters(getCheckDbFileEmptyParam(executionFlowData, operationParams));
			try {
				chOperation.execute();
			}
			catch(Exception e) {
				operationParams.setSkipCpyFrmFile(true);
			}
		}
		

		return this;
	}
	
	private ChkObjParam getChkObjParam(ExecutionFlowData executionFlowData, CreateDbFileParam createDbFileParam) {
		ChkObjParam chkObjParam = new ChkObjParam();
		chkObjParam.setObj(createDbFileParam.getFile());
		chkObjParam.setLibreria(createDbFileParam.getLibreria());
		chkObjParam.setMbr(createDbFileParam.getMembro());
		chkObjParam.setDbType(executionFlowData.getOrigin().getDbType());
		return chkObjParam; 
	}
	
	private ChkDbFileEmptyParam getCheckDbFileEmptyParam(ExecutionFlowData executionFlowData, OperationParams operationParams) {
		ChkDbFileEmptyParam chkDbFileEmptyParam = new ChkDbFileEmptyParam();
		
		updateGenericConnectionParams(executionFlowData, chkDbFileEmptyParam);
		chkDbFileEmptyParam.setFile(executionFlowData.getFlowFile());
		chkDbFileEmptyParam.setLibreria(executionFlowData.getFlowLibreria());
		chkDbFileEmptyParam.setDbType(executionFlowData.getOrigin().getDbType());
		
		return chkDbFileEmptyParam;
		
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
	
	public FlowBuilder selectFileColumns(ExecutionFlowData executionFlowData, OperationParams operationParams) throws Exception {
        SelectFileColumnsParam sfcParam = new SelectFileColumnsParam();
        sfcParam.setPgmLibrary(!StringUtils.isNullOrEmpty(PropertiesUtils.get(PropertiesConstants.SCHEMA_EXPORT_CONFIG_FILE)) ?
        				PropertiesUtils.get(PropertiesConstants.SCHEMA_EXPORT_CONFIG_FILE) : PropertiesUtils.get(Constants.PGM_LIBRARY_KEY) );
        sfcParam.setTargetSchema(StringUtils.isNullOrEmpty(operationParams.getTmpLibrary()) ? executionFlowData.getFlowLibreria() : operationParams.getTmpLibrary());
        sfcParam.setTargetTable("GF" + operationParams.getTransactionId());
        sfcParam.setDbType(executionFlowData.getOrigin().getDbType());
        sfcParam.setSchema(executionFlowData.getFlowLibreria());
        
        ConstraintDependentOperation<SelectFileColumnsParam, Boolean> sfc = new SelectFileColumns();
        sfc.setOperationParams(operationParams);
        sfc.setOtgffana(executionFlowData);
        sfc.setParameters(sfcParam);

        flow.addOperation(sfc);
        
        return this;
    }
	
	public FlowBuilder dropTmpExportTable(ExecutionFlowData executionFlowData, OperationParams operationParams) throws Exception {
        DropTmpExportTableParam dteParam = new DropTmpExportTableParam();
        dteParam.setLibrary(StringUtils.isNullOrEmpty(operationParams.getTmpLibrary()) ? executionFlowData.getFlowLibreria() : operationParams.getTmpLibrary());
        dteParam.setTable("GF" + operationParams.getTransactionId());
        dteParam.setDbType(executionFlowData.getOrigin().getDbType());
        
        DependentOperation<DropTmpExportTableParam> dtet = new DropTmpExportTable();
        dtet.setOperationParams(operationParams);
        dtet.setOtgffana(executionFlowData);
        dtet.setParameters(dteParam);

        flow.addOperation(dtet);
        
        return this;
    }

}