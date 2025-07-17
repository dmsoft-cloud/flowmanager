package it.dmsoft.flowmanager.agent.engine.core.flow.builder;

import java.util.Arrays;
import java.util.Optional;

import it.dmsoft.flowmanager.agent.engine.core.db.dao.DbConstants;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.Otgffana;
import it.dmsoft.flowmanager.agent.engine.core.manager.FlowManager.Replacer;
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

public class OutboundFlowBuilder extends FlowBuilder {

	public FlowBuilder createFile(Otgffana otgffana, OperationParams operationParams) {

		if (Constants.SI.equals(otgffana.getFana_Intergiry_Check())
				&& !StringUtils.isNullOrEmpty(otgffana.getFana_Fl_Name_Semaforo())) {
			
			Operation<CreateFileParam> crtFile = new CrtFile();
			CreateFileParam createFileParam = new CreateFileParam();

			createFileParam.setFileName(operationParams.getSempahoreFile());
			createFileParam.setFolder(otgffana.getFana_Folder());

			updateGenericAs400(otgffana, createFileParam);
			crtFile.setParameters(createFileParam);

			flow.addOperation(crtFile);
		}

		return this;
	}

	public FlowBuilder createIfsFile(Otgffana otgffana, OperationParams operationParams) throws Exception {
	
		if(operationParams.getFileNames()== null || operationParams.getFileNames().size() == 0) {
			String replaceLocal = Replacer.replace(otgffana.getFana_File_Name(), otgffana, operationParams);
			Operation<CreateFileParam> crtFile = new CrtFile();
			CreateFileParam createFileParam = new CreateFileParam();
			operationParams.setFileNames(Arrays.asList(replaceLocal));
			operationParams.setTrasmissionFiles(Arrays.asList(replaceLocal));
			operationParams.setTrasmissionFolder(otgffana.getFana_Folder());
			createFileParam.setFileName(replaceLocal);
			createFileParam.setFolder(otgffana.getFana_Folder());
			updateGenericAs400(otgffana, createFileParam);
			crtFile.setParameters(createFileParam);
	
			flow.addOperation(crtFile);
		}

		return this;
	}
	
	public FlowBuilder crtDb2FileIfNotExist(Otgffana otgffana, OperationParams operationParams) {
		
		CreateDbFileParam createDbFileParam = new CreateDbFileParam();
		updateGenericAs400(otgffana, createDbFileParam);
		createDbFileParam.setFile(otgffana.getFana_File());
		createDbFileParam.setLibreria(StringUtils.setDefault(operationParams.getLibrary(), otgffana.getFana_Libreria()));
		createDbFileParam.setSrcFile(otgffana.getFana_File_Source());
		createDbFileParam.setSrcLibreria(otgffana.getFana_Lib_Source());
		createDbFileParam.setSrcMembro(otgffana.getFana_Membro_Source());
		createDbFileParam.setCreaSeNonEsiste(Constants.SI.equals(otgffana.getFana_Crea_Vuoto()));
		createDbFileParam.setRecordLength(otgffana.getFana_Lunghezza_Fl_Flat());
		
		DependentOperation<ChkObjParam> chkObj = new ChkObj();
		chkObj.setParameters(getChkObjParam(createDbFileParam));
		chkObj.setOperationParams(operationParams);
		
		try {
		chkObj.execute();
		}
		catch(Exception e) {
			operationParams.setSkipCpyFrmFile(true);
		}
		
		if(operationParams.getSkipCpyFrmFile() == false) {
			Operation<ChkDbFileEmptyParam> chOperation = new ChkDbFileEmpty();
			chOperation.setParameters(getCheckDbFileEmptyParam(otgffana, operationParams));
			try {
				chOperation.execute();
			}
			catch(Exception e) {
				operationParams.setSkipCpyFrmFile(true);
			}
		}
		

		return this;
	}
	
	private ChkObjParam getChkObjParam(CreateDbFileParam createDbFileParam) {
		ChkObjParam chkObjParam = new ChkObjParam();
		chkObjParam.setObj(createDbFileParam.getFile());
		chkObjParam.setLibreria(createDbFileParam.getLibreria());
		chkObjParam.setMbr(createDbFileParam.getMembro());
		return chkObjParam; 
	}
	
	private ChkDbFileEmptyParam getCheckDbFileEmptyParam(Otgffana otgffana, OperationParams operationParams) {
		ChkDbFileEmptyParam chkDbFileEmptyParam = new ChkDbFileEmptyParam();
		
		updateGenericAs400(otgffana, chkDbFileEmptyParam);
		chkDbFileEmptyParam.setFile(otgffana.getFana_File());
		chkDbFileEmptyParam.setLibreria(otgffana.getFana_Libreria());
		return chkDbFileEmptyParam;
		
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
	
	public FlowBuilder selectFileColumns(Otgffana otgffana, OperationParams operationParams) throws Exception {
        SelectFileColumnsParam sfcParam = new SelectFileColumnsParam();
        sfcParam.setPgmLibrary(!StringUtils.isNullOrEmpty(PropertiesUtils.get(PropertiesConstants.SCHEMA_EXPORT_CONFIG_FILE)) ?
        				PropertiesUtils.get(PropertiesConstants.SCHEMA_EXPORT_CONFIG_FILE) : PropertiesUtils.get(Constants.PGM_LIBRARY_KEY) );
        sfcParam.setTargetSchema(StringUtils.isNullOrEmpty(operationParams.getTmpLibrary()) ? DbConstants.GF_CURLIB: operationParams.getTmpLibrary());
        sfcParam.setTargetTable("GF" + operationParams.getTransactionId());
        
        ConstraintDependentOperation<SelectFileColumnsParam, Boolean> sfc = new SelectFileColumns();
        sfc.setOperationParams(operationParams);
        sfc.setOtgffana(otgffana);
        sfc.setParameters(sfcParam);

        flow.addOperation(sfc);
        
        return this;
    }
	
	public FlowBuilder dropTmpExportTable(Otgffana otgffana, OperationParams operationParams) throws Exception {
        DropTmpExportTableParam dteParam = new DropTmpExportTableParam();
        dteParam.setLibrary(StringUtils.isNullOrEmpty(operationParams.getTmpLibrary()) ? DbConstants.GF_CURLIB: operationParams.getTmpLibrary());
        dteParam.setTable("GF" + operationParams.getTransactionId());
        
        DependentOperation<DropTmpExportTableParam> dtet = new DropTmpExportTable();
        dtet.setOperationParams(operationParams);
        dtet.setOtgffana(otgffana);
        dtet.setParameters(dteParam);

        flow.addOperation(dtet);
        
        return this;
    }

}