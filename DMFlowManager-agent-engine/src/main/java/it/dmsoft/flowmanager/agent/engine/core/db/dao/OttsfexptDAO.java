package it.dmsoft.flowmanager.agent.engine.core.db.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.dmsoft.flowmanager.agent.engine.core.as400.JdbcConnection;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.Otgffana;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.Ottsfexpt;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.Otgffana.OtgffanaCoulmn;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.Ottsfexpt.OttsfexptColumn;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.DatabaseUtils.DBTypeEnum;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class OttsfexptDAO {

	private static final Logger logger = Logger.getLogger(OttsfexptDAO.class.getName());

	public static Ottsfexpt read(String id) throws Exception {

		logger.debug("start to read Ottsfexpt");
		Ottsfexpt ottsfexpt = new Ottsfexpt();

		Connection conn = null;
		String queryString = "SELECT * FROM "+ DbConstants.SCHEMA + "OTTSFEXPT where EXPT_IDE = ?";
		conn = DBTypeEnum.fromString(DbConstants.DB_TYPE).getConnection(); 

		PreparedStatement ps = conn.prepareStatement(queryString);
		ps.setString(1, id);
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			
			logger.info("Move row to Ottsfexpt");
			moveToOttsexpt(ottsfexpt, rs);
		}

		return ottsfexpt;

	}

	private static Ottsfexpt moveToOttsexpt(Ottsfexpt ottsfexpt, ResultSet rs) throws SQLException {

		ottsfexpt.setExpt_Ide(rs.getString(OttsfexptColumn.EXPT_IDE.name()).trim());
		ottsfexpt.setExpt_File_Type(rs.getString(OttsfexptColumn.EXPT_FILE_TYPE.name()).trim());
		ottsfexpt.setExpt_Dest_Type(rs.getString(OttsfexptColumn.EXPT_DEST_TYPE.name()).trim());
		ottsfexpt.setExpt_Dest(rs.getString(OttsfexptColumn.EXPT_DEST.name()).trim());
		
		ottsfexpt.setExpt_File_Name(rs.getString(OttsfexptColumn.EXPT_FILE_NAME.name()).trim());
		ottsfexpt.setExpt_File_Des(rs.getString(OttsfexptColumn.EXPT_FILE_DES.name()).trim());
		ottsfexpt.setExpt_Testata(rs.getString(OttsfexptColumn.EXPT_TESTATA.name()).trim());
		ottsfexpt.setExpt_Dlmt(rs.getString(OttsfexptColumn.EXPT_DLMT.name()).trim());
		ottsfexpt.setExpt_Rmvblk(rs.getString(OttsfexptColumn.EXPT_RMVBLK.name()).trim());
		ottsfexpt.setExpt_Titolo(rs.getString(OttsfexptColumn.EXPT_TITOLO.name()).trim());
		ottsfexpt.setExpt_Errato(rs.getString(OttsfexptColumn.EXPT_ERRATO.name()).trim());
		ottsfexpt.setExpt_Rim_Spazi_Alfan_Vuoti(rs.getString(OttsfexptColumn.EXPT_RIM_SPAZI_ALFAN_VUOTI.name()).trim());
		ottsfexpt.setExpt_Fine_Riga(rs.getString(OttsfexptColumn.EXPT_FINE_RIGA.name()).trim());
		ottsfexpt.setExpt_Decpnt(rs.getString(OttsfexptColumn.EXPT_DECPNT.name()).trim());	
		
		return ottsfexpt;

	}
	
	public static StringBuilder getCoulmns() {
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.SPACE + OttsfexptColumn.EXPT_IDE.name() + Constants.SPACE);
		sb.append(Constants.COMMA + OttsfexptColumn.EXPT_FILE_TYPE.name() + Constants.SPACE);		
		sb.append(Constants.COMMA + OttsfexptColumn.EXPT_DEST_TYPE.name() + Constants.SPACE);		
		sb.append(Constants.COMMA + OttsfexptColumn.EXPT_DEST.name() + Constants.SPACE);		
		sb.append(Constants.COMMA + OttsfexptColumn.EXPT_FILE_NAME.name() + Constants.SPACE);		
		sb.append(Constants.COMMA + OttsfexptColumn.EXPT_FILE_DES.name() + Constants.SPACE);		
		sb.append(Constants.COMMA + OttsfexptColumn.EXPT_TESTATA.name() + Constants.SPACE);		
		sb.append(Constants.COMMA + OttsfexptColumn.EXPT_DLMT.name() + Constants.SPACE);		
		sb.append(Constants.COMMA + OttsfexptColumn.EXPT_RMVBLK.name() + Constants.SPACE);		
		sb.append(Constants.COMMA + OttsfexptColumn.EXPT_TITOLO.name() + Constants.SPACE);		
		sb.append(Constants.COMMA + OttsfexptColumn.EXPT_ERRATO.name() + Constants.SPACE);		
		sb.append(Constants.COMMA + OttsfexptColumn.EXPT_RIM_SPAZI_ALFAN_VUOTI.name() + Constants.SPACE);		
		sb.append(Constants.COMMA + OttsfexptColumn.EXPT_FINE_RIGA.name() + Constants.SPACE);		
		sb.append(Constants.COMMA + OttsfexptColumn.EXPT_DECPNT.name() + Constants.SPACE);		
		
		return sb;
	}

}