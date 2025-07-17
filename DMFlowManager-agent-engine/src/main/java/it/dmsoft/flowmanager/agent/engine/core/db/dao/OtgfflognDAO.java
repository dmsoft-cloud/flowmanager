package it.dmsoft.flowmanager.agent.engine.core.db.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import it.dmsoft.flowmanager.agent.engine.core.as400.JdbcConnection;
import it.dmsoft.flowmanager.agent.engine.core.utils.DatabaseUtils.DBTypeEnum;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class OtgfflognDAO {

	private static final Logger logger = Logger.getLogger(OtgfflognDAO.class.getName());

	public static BigDecimal getNextProgressive() throws Exception {
		logger.debug("start to get next progr from Otgfflogn");
		
		Connection conn = DBTypeEnum.fromString(DbConstants.DB_TYPE).getConnection();
		
		String queryStr = "SELECT " + OtgfflognColumn.OTGFLOGN_PROGRESSIVO.name() 
				+ " FROM " + DbConstants.SCHEMA + "OTGFFLOGN FOR UPDATE";

		logger.info("Query String:" + queryStr);
		
		PreparedStatement ps = conn.prepareStatement(queryStr, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE); 
		
	    ResultSet rs = ps.executeQuery();
	    if (rs.next()) {
	    	BigDecimal actualProg = rs.getBigDecimal(1);
	    	BigDecimal newProg = actualProg.add(BigDecimal.ONE);
	        rs.updateBigDecimal(1, newProg);
	        rs.updateRow();
	        
	        rs.close();
	        ps.close();
	        return newProg;
	    }
	    
	    return null;        
	}
	
	public enum OtgfflognColumn {
		OTGFLOGN_PROGRESSIVO
	}
	
}