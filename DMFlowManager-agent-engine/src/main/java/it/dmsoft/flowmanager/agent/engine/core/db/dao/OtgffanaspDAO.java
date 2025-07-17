package it.dmsoft.flowmanager.agent.engine.core.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.dmsoft.flowmanager.agent.engine.core.as400.JdbcConnection;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.Otgffanasp;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.Otgffanasp.OtgffanaspCoulmn;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.DatabaseUtils.DBTypeEnum;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class OtgffanaspDAO {

	private static final Logger logger = Logger.getLogger(OtgffanaspDAO.class.getName());

	public static Otgffanasp read(String otgfanaspId) throws Exception {

		logger.debug("start to read Otgffanasp");
		logger.info("otgfanaspId: " + otgfanaspId);
		Otgffanasp otgffanasp = new Otgffanasp();

		Connection conn = null;
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT" + Constants.SPACE);
		sb.append(getCoulmns());
		sb.append("FROM " + DbConstants.SCHEMA + "OTGFFANASP  WHERE OTGFANASP_ID = ?" );
		
		
		conn = DBTypeEnum.fromString(DbConstants.DB_TYPE).getConnection();
		logger.info("QueryString: " + sb.toString());
		
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		ps.setString(1, otgfanaspId);
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			
			logger.info("Move row to Otgffanasp");
			moveToOtgffanaSp(otgffanasp, rs);
		}
		
		logger.debug("Result: " + otgffanasp);
		logger.debug("Finised to read Otgffanasp");
		return otgffanasp;

	}

	private static Otgffanasp moveToOtgffanaSp(Otgffanasp otgffanasp, ResultSet rs) throws SQLException {

		otgffanasp.setOtgfanasp_Id(rs.getString(OtgffanaspCoulmn.OTGFANASP_ID.name()).trim());
		otgffanasp.setOtgfanasp_Spmqm(rs.getString(OtgffanaspCoulmn.OTGFANASP_SPMQM.name()).trim());
		otgffanasp.setOtgfanasp_SpqName(rs.getString(OtgffanaspCoulmn.OTGFANASP_SPQNAME.name()).trim());
		otgffanasp.setOtgfanasp_Stmdb(rs.getString(OtgffanaspCoulmn.OTGFANASP_STMDB.name()).trim());
		otgffanasp.setOtgfanasp_Dbfenc(rs.getString(OtgffanaspCoulmn.OTGFANASP_DBFENC.name()).trim());
		otgffanasp.setOtgfanasp_Sender(rs.getString(OtgffanaspCoulmn.OTGFANASP_SENDER.name()).trim());
		otgffanasp.setOtgfanasp_Corrid(rs.getString(OtgffanaspCoulmn.OTGFANASP_CORRID.name()).trim());
		otgffanasp.setOtgfanasp_Spuserid(rs.getString(OtgffanaspCoulmn.OTGFANASP_SPUSERID.name()).trim());
		otgffanasp.setOtgfanasp_Sppwd(rs.getString(OtgffanaspCoulmn.OTGFANASP_SPPWD.name()).trim());
		otgffanasp.setOtgfanasp_Mode(rs.getString(OtgffanaspCoulmn.OTGFANASP_MODE.name()).trim());
		otgffanasp.setOtgfanasp_Ccsid(rs.getString(OtgffanaspCoulmn.OTGFANASP_CCSID.name()).trim());
		otgffanasp.setOtgfanasp_Stmeor(rs.getString(OtgffanaspCoulmn.OTGFANASP_STMEOR.name()).trim());
		otgffanasp.setOtgfanasp_Stmtype(rs.getString(OtgffanaspCoulmn.OTGFANASP_STMTYPE.name()).trim());
		otgffanasp.setOtgfanasp_Usrcls(rs.getString(OtgffanaspCoulmn.OTGFANASP_USRCLS.name()).trim());
		otgffanasp.setOtgfanasp_Rep_Add(rs.getString(OtgffanaspCoulmn.OTGFANASP_REP_ADD.name()).trim());
		otgffanasp.setOtgfanasp_Acq_Fl_Let(rs.getString(OtgffanaspCoulmn.OTGFANASP_ACQ_FL_LET.name()).trim());
		otgffanasp.setOtgfanasp_StmEnc(rs.getString(OtgffanaspCoulmn.OTGFANASP_STMENC.name()).trim());
		otgffanasp.setOtgfanasp_Direct_From_Db(rs.getString(OtgffanaspCoulmn.OTGFANASP_DIRECT_FROM_DB.name()).trim());
		
		return otgffanasp;

	}
	
	private static StringBuilder getCoulmns() {
		
		StringBuilder sb= new StringBuilder();
		
		sb.append(Constants.SPACE + OtgffanaspCoulmn.OTGFANASP_ID + Constants.COMMA );
		sb.append(Constants.SPACE + OtgffanaspCoulmn.OTGFANASP_SPMQM + Constants.COMMA );
		sb.append(Constants.SPACE + OtgffanaspCoulmn.OTGFANASP_SPQNAME + Constants.COMMA );
		sb.append(Constants.SPACE + OtgffanaspCoulmn.OTGFANASP_STMDB + Constants.COMMA );
		sb.append(Constants.SPACE + OtgffanaspCoulmn.OTGFANASP_DBFENC + Constants.COMMA );
		sb.append(Constants.SPACE + OtgffanaspCoulmn.OTGFANASP_SENDER + Constants.COMMA );
		sb.append(Constants.SPACE + OtgffanaspCoulmn.OTGFANASP_CORRID + Constants.COMMA );
		sb.append(Constants.SPACE + OtgffanaspCoulmn.OTGFANASP_SPUSERID + Constants.COMMA );
		sb.append(Constants.SPACE + OtgffanaspCoulmn.OTGFANASP_SPPWD + Constants.COMMA );
		sb.append(Constants.SPACE + OtgffanaspCoulmn.OTGFANASP_MODE + Constants.COMMA );
		sb.append(Constants.SPACE + OtgffanaspCoulmn.OTGFANASP_CCSID + Constants.COMMA);
		sb.append(Constants.SPACE + OtgffanaspCoulmn.OTGFANASP_STMEOR + Constants.COMMA);
		sb.append(Constants.SPACE + OtgffanaspCoulmn.OTGFANASP_STMTYPE + Constants.COMMA);
		sb.append(Constants.SPACE + OtgffanaspCoulmn.OTGFANASP_USRCLS + Constants.COMMA);
		sb.append(Constants.SPACE + OtgffanaspCoulmn.OTGFANASP_REP_ADD + Constants.COMMA);
		sb.append(Constants.SPACE + OtgffanaspCoulmn.OTGFANASP_ACQ_FL_LET + Constants.COMMA);
		sb.append(Constants.SPACE + OtgffanaspCoulmn.OTGFANASP_STMENC  + Constants.COMMA);
		sb.append(Constants.SPACE + OtgffanaspCoulmn.OTGFANASP_DIRECT_FROM_DB + Constants.SPACE);
		
		return sb;
	}

	

}