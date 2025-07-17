package it.dmsoft.flowmanager.agent.engine.core.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.dmsoft.flowmanager.agent.engine.core.as400.JdbcConnection;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.Otgffmdest;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.Otgffmdest.OtgffmdestCoulmn;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.DatabaseUtils.DBTypeEnum;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class OtgffmdestDAO {

	private static final Logger logger = Logger.getLogger(OtgffmdestDAO.class.getName());

	public static List<Otgffmdest> read(String codiceMail) throws Exception {

		logger.debug("start to read Otgffmdest");
		logger.info("parameters:[" + "codiceMail: " + codiceMail + " ]");
			
		

		Connection conn = null;
		StringBuilder queryString = new StringBuilder();

		queryString.append("SELECT");
		queryString.append(getCoulmns());
		queryString.append("FROM " + DbConstants.SCHEMA + "OTGFFMDEST where MDEST_CODICE_MAIL = ? ");
		logger.info("Query String:" + queryString.toString());
		
		logger.info("get Connection");
		conn = DBTypeEnum.fromString(DbConstants.DB_TYPE).getConnection();

		
		
		PreparedStatement ps = conn.prepareStatement(queryString.toString());
		ps.setString(1, codiceMail);
		
		ResultSet rs = ps.executeQuery();
		List<Otgffmdest> otgffmdestList= new ArrayList<Otgffmdest>();

		logger.info("Move rows from Resultset  to Otgffmdestlist");
		while(rs.next()) {
			
			Otgffmdest otgffmdest = new Otgffmdest();
			moveToOtgffmdest(otgffmdest, rs);
			otgffmdestList.add(otgffmdest);
			
		}

		return otgffmdestList;

	}

	private static void moveToOtgffmdest(Otgffmdest otgffmdest, ResultSet rs) throws SQLException {

		otgffmdest.setMdest_Codice_Mail(rs.getString(OtgffmdestCoulmn.MDEST_CODICE_MAIL.name()).trim());
		otgffmdest.setMdest_Destinatario(rs.getString(OtgffmdestCoulmn.MDEST_DESTINATARIO.name()).trim());
		otgffmdest.setMdest_Tipo_Dest(rs.getString(OtgffmdestCoulmn.MDEST_TIPO_DEST.name()).trim());

	}

	public static StringBuilder getCoulmns() {
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.SPACE + OtgffmdestCoulmn.MDEST_CODICE_MAIL.name() + Constants.SPACE);
		sb.append(Constants.COMMA + OtgffmdestCoulmn.MDEST_DESTINATARIO.name() + Constants.SPACE);
		sb.append(Constants.COMMA + OtgffmdestCoulmn.MDEST_TIPO_DEST.name() + Constants.SPACE);
		return sb;
	}

}