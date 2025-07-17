package it.dmsoft.flowmanager.agent.engine.core.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import it.dmsoft.flowmanager.agent.engine.core.as400.JdbcConnection;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.Otgfflogd;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.DatabaseUtils.DBTypeEnum;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class OtgfflogdDAO {

	private static final Logger logger = Logger.getLogger(OtgfflogdDAO.class.getName());

	//private static AS400JDBCConnectionPoolDataSource as400jdbcConnectionPoolDataSource = new AS400JDBCConnectionPoolDataSource();

	public static Boolean insert(Otgfflogd otgfflogd) throws Exception {

		logger.info("start to INSERT otgfflogd");
		logger.debug("Values otgfflogd :" + otgfflogd.toString());

		logger.info("get connection:");
		Connection conn =  DBTypeEnum.fromString(DbConstants.DB_TYPE).getConnection();

		StringBuilder queryString = new StringBuilder();
		queryString.append("INSERT INTO " + DbConstants.SCHEMA + "OTGFFLOGD ");

		// TODO campi da aggiornare
		queryString.append(Constants.OPEN_BRACKET);
		queryString.append(Otgfflogd.OtgfflogdCoulmn.OTGFLOGD_PROGR_LOG.toString() + Constants.COMMA);
		queryString.append(Constants.SPACE + Otgfflogd.OtgfflogdCoulmn.OTGFLOGD_PROGR_FASE.toString() + Constants.COMMA);
		queryString.append(Constants.SPACE + Otgfflogd.OtgfflogdCoulmn.OTGFLOGD_FASE.toString() + Constants.COMMA);
		queryString.append(Constants.SPACE + Otgfflogd.OtgfflogdCoulmn.OTGFLOGD_TS.toString() + Constants.COMMA);
		queryString.append(Constants.SPACE + Otgfflogd.OtgfflogdCoulmn.OTGFLOGD_ESITO.toString() + Constants.COMMA);
		queryString.append(Constants.SPACE + Otgfflogd.OtgfflogdCoulmn.OTGFLOGD_NOTE.toString());
		queryString.append(Constants.CLOSE_BRACKET);

		queryString.append(Constants.SPACE + "VALUES" + Constants.SPACE);
		queryString.append(Constants.OPEN_BRACKET);
		queryString.append(Constants.QUESTION_MARK + Constants.COMMA);
		queryString.append(Constants.QUESTION_MARK + Constants.COMMA);
		queryString.append(Constants.QUESTION_MARK + Constants.COMMA);
		queryString.append(Constants.QUESTION_MARK + Constants.COMMA);
		queryString.append(Constants.QUESTION_MARK + Constants.COMMA);
		queryString.append(Constants.QUESTION_MARK);
		queryString.append(Constants.CLOSE_BRACKET);

		logger.debug("Query:" + queryString.toString());

		PreparedStatement ps = conn.prepareStatement(queryString.toString());
		
		logger.info("Move values to PrepareStatements");
		moveFromOtgfflogd(otgfflogd, ps);

		ps.execute();

		logger.info("record inserted successfully");

		return true;

	}

	/*
	 * public static Boolean update(Otgfflogd otgfflogd) {
	 * 
	 * 
	 * logger.info("start to update in  OCSDB401.otgfflogd"); logger.debug("Values:"
	 * + otgfflogd.toString());
	 * 
	 * Boolean result= false; Connection conn = null; try { StringBuilder
	 * queryString = new StringBuilder(); queryString.append("update otgfflogd");
	 * 
	 * //TODO campi da inserire queryString.append(Constants.OPEN_BRACKET);
	 * queryString.append(Otgfflogd.OtgfflogdCoulmn.OTGFLOGD_PROGR_LOG.toString() +
	 * Constants.COMMA); queryString.append(Constants.SPACE +
	 * Otgfflogd.OtgfflogdCoulmn.OTGFLOGD_PROGR_FASE.toString() + Constants.COMMA);
	 * queryString.append(Constants.SPACE +
	 * Otgfflogd.OtgfflogdCoulmn.OTGFLOGD_FASE.toString() + Constants.COMMA);
	 * queryString.append(Constants.SPACE +
	 * Otgfflogd.OtgfflogdCoulmn.OTGFLOGD_TS.toString() + Constants.COMMA);
	 * queryString.append(Constants.SPACE +
	 * Otgfflogd.OtgfflogdCoulmn.OTGFLOGD_ESITO.toString() + Constants.COMMA);
	 * queryString.append(Constants.SPACE +
	 * Otgfflogd.OtgfflogdCoulmn.OTGFLOGD_NOTE.toString());
	 * queryString.append(Constants.CLOSE_BRACKET);
	 * 
	 * 
	 * //TODO campi da preparare
	 * 
	 * queryString.append(Constants.SPACE + "VALUES" + Constants.SPACE);
	 * queryString.append(Constants.OPEN_BRACKET);
	 * queryString.append(Constants.QUESTION_MARK + Constants.COMMA);
	 * queryString.append(Constants.QUESTION_MARK + Constants.COMMA);
	 * queryString.append(Constants.QUESTION_MARK + Constants.COMMA);
	 * queryString.append(Constants.QUESTION_MARK + Constants.COMMA);
	 * queryString.append(Constants.QUESTION_MARK + Constants.COMMA);
	 * queryString.append(Constants.QUESTION_MARK);
	 * queryString.append(Constants.CLOSE_BRACKET);
	 * 
	 * 
	 * conn = as400jdbcConnectionPoolDataSource.getConnection();
	 * logger.debug("Query:" + queryString.toString()); PreparedStatement ps =
	 * conn.prepareStatement(queryString.toString());
	 * 
	 * moveFromOtgfflogd(otgfflogd, ps);
	 * 
	 * result = ps.execute();
	 * 
	 * 
	 * } catch (SQLException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } finally { try { conn.close(); } catch (SQLException e)
	 * { // TODO Auto-generated catch block e.printStackTrace(); } }
	 * 
	 * return result;
	 * 
	 * }
	 */

	private static void moveFromOtgfflogd(Otgfflogd otgfflogd, PreparedStatement ps) throws SQLException {

		ps.setBigDecimal(1, otgfflogd.getOtgflogd_Progr_Log());
		ps.setBigDecimal(2, otgfflogd.getOtgflogd_Progr_Fase());
		ps.setString(3, otgfflogd.getOtgflogd_Fase());
		ps.setTimestamp(4, otgfflogd.getOtgflogd_Ts());
		ps.setString(5, otgfflogd.getOtgflogd_Esito());
		ps.setString(6, otgfflogd.getOtgflogd_Note());

	}

}