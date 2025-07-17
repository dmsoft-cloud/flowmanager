package it.dmsoft.flowmanager.agent.engine.core.db.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.dmsoft.flowmanager.agent.engine.core.db.dto.Ccschdt;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.Ccschdt.CcschdtColumn;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.DatabaseUtils.DBTypeEnum;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class CcschdtDAO {

	private static final Logger logger = Logger.getLogger(CcschdtDAO.class.getName());

	public static BigDecimal getSchedDate() throws Exception {
		logger.debug("start to get next progr from Ccschdt");
		
		Connection conn = DBTypeEnum.fromString(DbConstants.DB_TYPE).getConnection();
		
		String queryStr = "SELECT " + getCoulmns() 
				+ " FROM " + DbConstants.SCHEMA + "CCSCHDT";

		logger.info("Query String:" + queryStr);
		

		PreparedStatement ps = conn.prepareStatement(queryStr);
		ResultSet rs = ps.executeQuery();

		logger.info("Move rows from Resultset  to Ccschdt");
		Ccschdt ccschdt = null;
		if(rs.next()) {			
			ccschdt = new Ccschdt();
			moveToCcschdt(ccschdt, rs);
		}

		return ccschdt.getCcscch_Data();
		
	}
	
	private static void moveToCcschdt(Ccschdt ccschdt, ResultSet rs) throws SQLException {
		ccschdt.setCcscch_Data(rs.getBigDecimal(CcschdtColumn.CCSCH_DATA.name()));
		ccschdt.setCcscch_Data_Da(rs.getBigDecimal(CcschdtColumn.CCSCH_DATA_DA.name()));
	}

	public static StringBuilder getCoulmns() {
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.SPACE + CcschdtColumn.CCSCH_DATA_DA.name() + Constants.SPACE);
		sb.append(Constants.COMMA + CcschdtColumn.CCSCH_DATA.name() + Constants.SPACE);		
		return sb;
	}

}