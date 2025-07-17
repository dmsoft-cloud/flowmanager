package it.dmsoft.flowmanager.agent.engine.core.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.dmsoft.flowmanager.agent.engine.core.as400.JdbcConnection;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.Otgffmail;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.Otgffmail.OtgffmailCoulmn;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.DatabaseUtils.DBTypeEnum;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class OtgffmailDAO {

	private static final Logger logger = Logger.getLogger(OtgffmailDAO.class.getName());

	public static Otgffmail read(String codiceMail) throws Exception {

		logger.debug("start to read Otgffmail");
		logger.info("codiceMail:" + codiceMail);
		Otgffmail otgffmail = null;

		Connection conn = null;
		StringBuilder queryString = new StringBuilder();

		queryString.append("SELECT");
		queryString.append(getCoulmns());
		queryString.append("FROM " + DbConstants.SCHEMA + "OTGFFMAIL where MAIL_CODICE = ?");
		
		logger.info("Query String:" + queryString.toString());

		logger.info("get Connection");
		conn = DBTypeEnum.fromString(DbConstants.DB_TYPE).getConnection();
		

		PreparedStatement ps = conn.prepareStatement(queryString.toString());
		ps.setString(1, codiceMail);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			otgffmail = new Otgffmail();
			logger.info("Move row to Otgffmail");
			moveToOtgffmail(otgffmail, rs);
		}

		return otgffmail;

	}

	private static void moveToOtgffmail(Otgffmail otgffmail, ResultSet rs) throws SQLException {

		otgffmail.setMail_Codice(rs.getString(OtgffmailCoulmn.MAIL_CODICE.name()).trim());
		otgffmail.setMail_Testo(rs.getString(OtgffmailCoulmn.MAIL_TESTO.name()).trim());
		otgffmail.setMail_Oggetto(rs.getString(OtgffmailCoulmn.MAIL_OGGETTO.name()).trim());

	}

	public static StringBuilder getCoulmns() {
		StringBuilder sb = new StringBuilder();

		sb.append(Constants.SPACE + OtgffmailCoulmn.MAIL_CODICE.toString() + Constants.SPACE);
		sb.append(Constants.COMMA + OtgffmailCoulmn.MAIL_TESTO.toString() + Constants.SPACE);
		sb.append(Constants.COMMA + OtgffmailCoulmn.MAIL_OGGETTO.toString() + Constants.SPACE);
		return sb;
	}

}