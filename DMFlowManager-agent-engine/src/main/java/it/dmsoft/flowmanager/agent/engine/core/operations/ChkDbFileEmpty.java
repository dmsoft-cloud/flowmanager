package it.dmsoft.flowmanager.agent.engine.core.operations;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import it.dmsoft.flowmanager.agent.engine.core.exception.OperationException;
import it.dmsoft.flowmanager.agent.engine.core.operations.core.Operation;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.ChkDbFileEmptyParam;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.OperationType;
import it.dmsoft.flowmanager.agent.engine.core.utils.DatabaseUtils.DBTypeEnum;
import it.dmsoft.flowmanager.agent.engine.core.utils.FlowLogUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.StringUtils;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class ChkDbFileEmpty extends Operation<ChkDbFileEmptyParam>{

	private static final Logger logger = Logger.getLogger(ChkDbFileEmpty.class.getName());	
	
	@Override
	public void execute() throws Exception {
		logger.info("start execution of " + ChkDbFileEmpty.class.getName());
		logger.info("parameters: " + parameters.toString());
		FlowLogUtils.startDetail(OperationType.CHK_EMPTY);
		
		String library = StringUtils.isNullOrEmpty(parameters.getLibreria()) ? "" : parameters.getLibreria() + Constants.DOT;
		String query = "SELECT count(*) FROM " 
							+ library
							+ parameters.getFile();
		
		
		logger.debug("Count Query :  " + query);
		
		Connection conn = DBTypeEnum.get(parameters.getDbType()).getConnection(parameters, parameters.getLibreria()); 

		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet rs = ps.executeQuery();

		if (!rs.next() || rs.getBigDecimal(1) == null || BigDecimal.ZERO.compareTo(rs.getBigDecimal(1)) == 0) {
			throw new OperationException("File " + library + parameters.getFile() + " is empty");
		}
		
		
		logger.info("end execution of " + ChkDbFileEmpty.class.getName());
		FlowLogUtils.endDetail(OperationType.CHK_EMPTY);	
	}

}
