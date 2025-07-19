package it.dmsoft.flowmanager.agent.engine.core.operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

import it.dmsoft.flowmanager.agent.engine.core.db.DbConstants;
import it.dmsoft.flowmanager.agent.engine.core.exception.OperationException;
import it.dmsoft.flowmanager.agent.engine.core.operations.core.DependentOperation;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.ChkObjParam;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.OperationType;
import it.dmsoft.flowmanager.agent.engine.core.utils.DatabaseUtils.DBTypeEnum;
import it.dmsoft.flowmanager.agent.engine.core.utils.FlowLogUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.StringUtils;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class ChkObj extends DependentOperation<ChkObjParam>{

	private static final Logger logger = Logger.getLogger(ChkObj.class.getName());	
	
	@Override
	public void updateParameters() throws Exception {
	}
	
	@Override
	public void executeOperation() throws Exception {
		logger.info("start execution of " + ChkObj.class.getName());
		logger.info("parameters: " + parameters.toString());
		FlowLogUtils.startDetail(OperationType.CHECK_OBJ);
		
		/* vecchio metodo con uso chkobj
		 * 
		 * CallAs400 callAs400 = CallAs400.get(parameters);
		
		StringBuilder sb = new StringBuilder();
		
		//CHKOBJ OBJ(OCSOAM/QPFSRC) OBJTYPE(*FILE) MBR(OMFFTP1)
		
		sb.append("CHKOBJ");

		sb.append(Constants.SPACE + "OBJ");
		sb.append(Constants.OPEN_BRACKET);
		sb.append(parameters.getLibreria() + Constants.PATH_DELIMITER + parameters.getObj());
		sb.append(Constants.CLOSE_BRACKET);
		
		
		sb.append(Constants.SPACE + "OBJTYPE");
		sb.append(Constants.OPEN_BRACKET);
		sb.append(parameters.getObjType());
		sb.append(Constants.CLOSE_BRACKET);
		
		logger.debug("Command ChkObj :  " + sb.toString());		
		
		callAs400.commandCall(sb.toString());
		*/
		//controllo se devo utilizzare il file con le colonne rielaborate in ordine e intestazione
		Connection conn = null;
		conn = DBTypeEnum.fromString(DbConstants.DB_TYPE).getConnection(); 
		int counter=0;
		String table;
		String schema;
		
		//DBTypeEnum dbTypeEnum = DBTypeEnum.fromString(DbConstants.DB_TYPE);
		String query = DBTypeEnum.fromString(DbConstants.DB_TYPE).getQueryCheckObj();
	
		PreparedStatement ps = conn.prepareStatement(query);
		logger.info(query);
		if (Optional.ofNullable(operationParams.getExportFileHeaders()).filter(Constants.SI::equals).isPresent()
				&& !StringUtils.isNullOrEmpty(operationParams.getExportTempTable())) {
			schema = operationParams.getExportTempSchema();
			table = operationParams.getExportTempTable();
		} else {
			schema = parameters.getLibreria();
			table = parameters.getObj();
		}
		
		ps.setString(1, schema);
		ps.setString(2, table);
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			counter=rs.getInt(1);
		}
		
		ps.close();
		rs.close();
		if (counter<=0) throw new OperationException("table not found: " + schema + Constants.DOT + table);
		
		logger.info("table exists: " + schema + Constants.DOT + table);
		logger.info("end execution of " + ChkObj.class.getName());
		FlowLogUtils.endDetail(OperationType.CHECK_OBJ);	
	}

}
