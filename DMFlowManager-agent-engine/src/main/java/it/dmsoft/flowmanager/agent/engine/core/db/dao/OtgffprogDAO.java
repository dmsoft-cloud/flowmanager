package it.dmsoft.flowmanager.agent.engine.core.db.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.dmsoft.flowmanager.agent.engine.core.as400.JdbcConnection;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.Otgffprog;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.Otgffprog.OtgffprogColumn;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.DatabaseUtils.DBTypeEnum;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class OtgffprogDAO {

	private static final Logger logger = Logger.getLogger(OtgffprogDAO.class.getName());

	public static BigDecimal getNextProgressive(String idTransazione, BigDecimal data, BigDecimal transactionFilesCount) throws Exception {
		logger.debug("start to get next progr from Otgffprog");
		logger.info("parameters:[" + "codiceTransazione: " + idTransazione + " data: "  + data + "]");
		
		Connection conn = DBTypeEnum.fromString(DbConstants.DB_TYPE).getConnection();
		
		String queryStr = "SELECT " + OtgffprogColumn.OTGFPROG_PROGRESSIVO.name() 
				+ " FROM " + DbConstants.SCHEMA + "OTGFFPROG WHERE OTGFPROG_ID_TRANSAZIONE = ? AND OTGFPROG_DATA = ? FOR UPDATE";

		logger.info("Query String:" + queryStr);
		
		PreparedStatement ps = conn.prepareStatement(queryStr, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE); 
		ps.setString(1, idTransazione);
		ps.setBigDecimal(2, data);
		
	    ResultSet rs = ps.executeQuery();
	    if (rs.next()) {
	    	BigDecimal actualProg = rs.getBigDecimal(1);
	    	BigDecimal newProg = actualProg.add(transactionFilesCount);
	        rs.updateBigDecimal(1, newProg);
	        rs.updateRow();
	        
	        rs.close();
	        ps.close();
	        return actualProg.add(BigDecimal.ONE);
	    } else {
	    	Otgffprog otgffprog = new Otgffprog();
	    	otgffprog.setOtgfprog_Id_Transazione(idTransazione);
	    	otgffprog.setOtgfprog_Data(data);
	    	otgffprog.setOtgfprog_Progressivo(BigDecimal.ZERO);
	    	rs.close();
	    	ps.close();
	    	
	    	insert(otgffprog);	    	
	    	return getNextProgressive(idTransazione, data, transactionFilesCount);
	    }
        
	}
	
	public static void insert(Otgffprog otgffprog) throws Exception {

		logger.debug("start to update Otgffprog");
		logger.info("Values otgffana :" + otgffprog);

		if (otgffprog != null) {
			Connection conn = null;
			logger.info("Prepare query ");
			String queryStr = "INSERT INTO " + DbConstants.SCHEMA + "OTGFFPROG VALUES(?, ?, ?)"; 
			
			logger.info("get connection");
			conn = DBTypeEnum.fromString(DbConstants.DB_TYPE).getConnection();
			PreparedStatement ps = conn.prepareStatement(queryStr);

			ps.setString(1, otgffprog.getOtgfprog_Id_Transazione());
			ps.setBigDecimal(2, otgffprog.getOtgfprog_Data());
			ps.setBigDecimal(3, otgffprog.getOtgfprog_Progressivo());

			ps.executeUpdate();

		}

	}
	
	public static Otgffprog read(String idTransazione, BigDecimal data) throws Exception {

		logger.debug("start to read Otgffprog");
		logger.info("parameters:[" + "codiceTransazione: " + idTransazione + " data: "  + data + "]");
			
		Connection conn = null;
		StringBuilder queryString = new StringBuilder();

		queryString.append("SELECT");
		queryString.append(getCoulmns());
		queryString.append("FROM " + DbConstants.SCHEMA + "OTGFFPROG where OTGFPROG_ID_TRANSAZIONE = ? AND OTGFPROG_DATA = ?");
		logger.info("Query String:" + queryString.toString());
		
		logger.info("get Connection");
		conn = DBTypeEnum.fromString(DbConstants.DB_TYPE).getConnection();

		PreparedStatement ps = conn.prepareStatement(queryString.toString());
		ps.setString(1, idTransazione);
		ps.setBigDecimal(2, data);
		
		ResultSet rs = ps.executeQuery();
		List<Otgffprog> otgffprogList= new ArrayList<Otgffprog>();

		logger.info("Move rows from Resultset  to Otgffprog");
		while(rs.next()) {			
			Otgffprog otgffprog = new Otgffprog();
			moveToOtgffprog(otgffprog, rs);
			otgffprogList.add(otgffprog);
		}

		return otgffprogList.get(0);

	}

	private static void moveToOtgffprog(Otgffprog otgffprog, ResultSet rs) throws SQLException {
		otgffprog.setOtgfprog_Id_Transazione(rs.getString(OtgffprogColumn.OTGFPROG_ID_TRANSAZIONE.name()).trim());
		otgffprog.setOtgfprog_Data(rs.getBigDecimal(OtgffprogColumn.OTGFPROG_DATA.name()));
		otgffprog.setOtgfprog_Progressivo(rs.getBigDecimal(OtgffprogColumn.OTGFPROG_PROGRESSIVO.name()));
	}

	public static StringBuilder getCoulmns() {
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.SPACE + OtgffprogColumn.OTGFPROG_ID_TRANSAZIONE.name() + Constants.SPACE);
		sb.append(Constants.COMMA + OtgffprogColumn.OTGFPROG_DATA.name() + Constants.SPACE);
		sb.append(Constants.COMMA + OtgffprogColumn.OTGFPROG_PROGRESSIVO.name() + Constants.SPACE);
		return sb;
	}

}