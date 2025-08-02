package it.dmsoft.flowmanager.agent.engine.core.operations;

import java.sql.Connection;
import java.sql.PreparedStatement;

import it.dmsoft.flowmanager.agent.engine.core.exception.OperationException;
import it.dmsoft.flowmanager.agent.engine.core.operations.core.DependentOperation;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.DropTmpExportTableParam;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.OperationType;
import it.dmsoft.flowmanager.agent.engine.core.utils.DatabaseUtils.DBTypeEnum;
import it.dmsoft.flowmanager.agent.engine.core.utils.FlowLogUtils;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class DropTmpExportTable extends DependentOperation<DropTmpExportTableParam>{

	private static final Logger logger = Logger.getLogger(DropTmpExportTable.class.getName());	
	
	@Override
	public void updateParameters() throws Exception {
	}
	
	@Override
	public void executeOperation() throws Exception {
		logger.info("start execution of " + DropTmpExportTable.class.getName());
		logger.info("parameters: " + parameters.toString());
		FlowLogUtils.startDetail(OperationType.DROP_TBL);
		
		try {
			//controllo se devo utilizzare il file con le colonne rielaborate in ordine e intestazione
			Connection conn = null;
			conn = DBTypeEnum.get(parameters.getDbType()).getConnection(parameters, parameters.getLibrary()); 
			
			
			String query = "DROP TABLE " + operationParams.getExportTempSchema() + Constants.DOT + operationParams.getExportTempTable();
			PreparedStatement ps = conn.prepareStatement(query);
			logger.info(query);
			
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			throw new OperationException("Error deleting temporary table ", e);
		}


		
		logger.info("table deleted : " + operationParams.getExportTempSchema() + Constants.DOT + operationParams.getExportTempTable());
		logger.info("end execution of " + DropTmpExportTable.class.getName());
		FlowLogUtils.endDetail(OperationType.DROP_TBL);	
	}

}
