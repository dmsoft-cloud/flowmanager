package it.dmsoft.flowmanager.agent.engine.core.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.dmsoft.flowmanager.agent.engine.core.as400.JdbcConnection;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.Otgffanacf;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.Otgffanacf.OtgffanacfCoulmn;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.Otgffanasp;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.Otgffanasp.OtgffanaspCoulmn;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class OtgffanacfDAO {

	private static final Logger logger = Logger.getLogger(OtgffanacfDAO.class.getName());

	public static Otgffanacf read(String otgfanacfId) throws Exception {

		logger.debug("start to read Otgffanacf");
		logger.info("otgfanaspId: " + otgfanacfId);
		Otgffanacf otgffanacf = new Otgffanacf();

		Connection conn = null;
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT" + Constants.SPACE);
		sb.append(getCoulmns());
		sb.append("FROM " + DbConstants.SCHEMA + "Otgffanacf  WHERE OTGFANACF_ID = ?" );
		
		
		conn = JdbcConnection.get();
		logger.info("QueryString: " + sb.toString());
		
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		ps.setString(1, otgfanacfId);
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			
			logger.info("Move row to Otgffanacf");
			moveToOtgffanaCf(otgffanacf, rs);
		}
		
		logger.debug("Result: " + otgffanacf);
		logger.debug("Finised to read Otgffanasp");
		return otgffanacf;

	}

	private static Otgffanacf moveToOtgffanaCf(Otgffanacf otgffanacf, ResultSet rs) throws SQLException {

		otgffanacf.setOtgfanacf_Id(rs.getString(OtgffanacfCoulmn.OTGFANACF_ID.name()).trim());
		otgffanacf.setOtgfanacf_Part(rs.getString(OtgffanacfCoulmn.OTGFANACF_PART.name()).trim());
		otgffanacf.setOtgfanacf_Idf(rs.getString(OtgffanacfCoulmn.OTGFANACF_IDF.name()).trim());
		otgffanacf.setOtgfanacf_Fname(rs.getString(OtgffanacfCoulmn.OTGFANACF_FNAME.name()).trim());
		otgffanacf.setOtgfanacf_Parm(rs.getString(OtgffanacfCoulmn.OTGFANACF_PARM.name()).trim());
		otgffanacf.setOtgfanacf_Exec(rs.getString(OtgffanacfCoulmn.OTGFANACF_EXEC.name()).trim());
		otgffanacf.setOtgfanacf_Exece(rs.getString(OtgffanacfCoulmn.OTGFANACF_EXECE.name()).trim());
		otgffanacf.setOtgfanacf_Nfname(rs.getString(OtgffanacfCoulmn.OTGFANACF_NFNAME.name()).trim());
		otgffanacf.setOtgfanacf_Archifname(rs.getString(OtgffanacfCoulmn.OTGFANACF_ARCHIFNAME.name()).trim());
		
		return otgffanacf;

	}
	
	private static StringBuilder getCoulmns() {
		
		StringBuilder sb= new StringBuilder();
		
		sb.append(Constants.SPACE + OtgffanacfCoulmn.OTGFANACF_ID + Constants.COMMA );
		sb.append(Constants.SPACE + OtgffanacfCoulmn.OTGFANACF_PART + Constants.COMMA );
		sb.append(Constants.SPACE + OtgffanacfCoulmn.OTGFANACF_IDF + Constants.COMMA );
		sb.append(Constants.SPACE + OtgffanacfCoulmn.OTGFANACF_FNAME + Constants.COMMA );
		sb.append(Constants.SPACE + OtgffanacfCoulmn.OTGFANACF_PARM + Constants.COMMA );
		sb.append(Constants.SPACE + OtgffanacfCoulmn.OTGFANACF_EXEC + Constants.COMMA );
		sb.append(Constants.SPACE + OtgffanacfCoulmn.OTGFANACF_EXECE + Constants.COMMA );
		sb.append(Constants.SPACE + OtgffanacfCoulmn.OTGFANACF_NFNAME + Constants.COMMA );
		sb.append(Constants.SPACE + OtgffanacfCoulmn.OTGFANACF_ARCHIFNAME + Constants.SPACE );
		
		return sb;
	}

	

}