package it.dmsoft.flowmanager.agent.engine.core.db.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;

import it.dmsoft.flowmanager.agent.engine.core.as400.JdbcConnection;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.Otgffana;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.Otgfflogt;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.Otgfflogt.OtgfflogtCoulmn;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.DatabaseUtils.DBTypeEnum;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class OtgfflogtDAO {

	private static final Logger logger = Logger.getLogger(OtgfflogtDAO.class.getName());

	// private static AS400JDBCConnectionPoolDataSource
	// as400jdbcConnectionPoolDataSource = new AS400JDBCConnectionPoolDataSource();

	// return number of row affected by update
	
	public static Otgfflogt insert(Otgffana otgffana) throws Exception {
		return insert(moveToOtgfflogt(otgffana));
	}
	
	public static Otgfflogt insert(Otgfflogt otgfflogt) throws Exception {
		logger.debug("start to get next progr from Otgfflogt");
		logger.debug("Values Otgfflogt: " + otgfflogt.toString());
		
		Connection conn = DBTypeEnum.fromString(DbConstants.DB_TYPE).getConnection();
		
    	otgfflogt.setOtgflogt_Progr_Log(OtgfflognDAO.getNextProgressive());
	    		
	    StringBuilder queryString = new StringBuilder();

		queryString.append("INSERT INTO " + DbConstants.SCHEMA + "OTGFFLOGT " + Constants.OPEN_BRACKET);
		
		boolean first = true;
		for (OtgfflogtCoulmn col : OtgfflogtCoulmn.values()) {
			if (!first)
				queryString.append(Constants.COMMA);
			
			queryString.append(Constants.SPACE + col.toString());
			
			first = false;
		}
		
		queryString.append(Constants.CLOSE_BRACKET + " VALUES " + Constants.OPEN_BRACKET);
		
		first = true;
		for (int i = 0; i < OtgfflogtCoulmn.values().length; i++) {
			if (!first) {
				queryString.append(Constants.COMMA);
			}
			
			queryString.append(Constants.SPACE + Constants.QUESTION_MARK);			
			first = false;
		}
		
		queryString.append(Constants.CLOSE_BRACKET);
		
		logger.debug("query: " + queryString.toString());		
		PreparedStatement psInsert = conn.prepareStatement(queryString.toString());

		moveFromOtgfflogt(otgfflogt, psInsert, true);

		int row = psInsert.executeUpdate();
		logger.info("Row affected by insert: " + row);	
		psInsert.close();
		
		return otgfflogt;
	}

	public static int update(Otgfflogt otgfflogt) throws Exception {

		logger.debug("start updating Otgfflogt");

		logger.debug("Values Otgfflogt: " + otgfflogt.toString());

		int row = 0;
		Connection conn = JdbcConnection.get();
		StringBuilder queryString = new StringBuilder();

		queryString.append("UPDATE " + DbConstants.SCHEMA + "OTGFFLOGT SET");

		queryString.append(Constants.SPACE + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_ID.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_TS_INIZIO.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_TS_FINE.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_ESITO.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_UTENTE.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_PASSWORD.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_COD_INTERFACCIA.toString()
				+ Constants.EQUAL + Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_STATO.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_TIP_FLUSSO.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_DIREZIONE.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_LIBRERIA.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_FILE.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_MEMBRO.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_LIB_SOURCE.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_FILE_SOURCE.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_MEMBRO_SOURCE.toString()
				+ Constants.EQUAL + Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_FOLDER.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_FILE_NAME.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_FORMATO.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_DELIM_RECORD.toString()
				+ Constants.EQUAL + Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_DELIM_CAMPO.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_CODEPAGE.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_MOD_ACQUISIZIONE.toString()
				+ Constants.EQUAL + Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_PGM_CONTROLLO.toString()
				+ Constants.EQUAL + Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_TIPOLOGIA_CONN.toString()
				+ Constants.EQUAL + Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_HOST.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_PORT.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_REMOTE_FOLDER.toString()
				+ Constants.EQUAL + Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_REMOTE_FILE_NAME.toString()
				+ Constants.EQUAL + Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_UTENTE_SFTP.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_INTEGRITY_CHECK.toString()
				+ Constants.EQUAL + Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_FL_NAME_SEMAFORO.toString()
				+ Constants.EQUAL + Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_NUM_TENTA_RICEZ.toString()
				+ Constants.EQUAL + Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_INTERVALLO_RETRY.toString()
				+ Constants.EQUAL + Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_RETENTION.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_COMPRESSION.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_BACKUP.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_INVIA_MAIL.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_LETTERA_OK.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_LETTERA_KO.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_LOG_FILE.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_ERRORE_DESC.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_JOB_AS.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_USER_AS.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_JOB_NBR_AS.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_TIPO_TRASFERIMENTO.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_BYPASS_QTEMP.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_JAVA_HOME.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		
		//nuove colonne
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_ESISTENZA_FILE.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_LETTERA_FLUSSO.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_CREA_VUOTO.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_INTERNAZ.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_SOST_VAL_NULL.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_ELIM_NOM_COL.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_FLAG_OK_VUOTO.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_FTP_SECURE.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		
		
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_INTERACTIVE_TYPE.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_INTERACTIVE_PROGRAM.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_INTERACTIVE_RESULT.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_INTERACTIVE_COMMAND.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_DELAY_SEMAFORO.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		
		
		
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_HASH_UNICO.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_EXPORT_FLAG.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_EXPORT_CODE.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_FETCH_SIZE.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		queryString.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_CHAR_EMPTY_SPACE.toString() + Constants.EQUAL
				+ Constants.QUESTION_MARK + Constants.SPACE);
		
		

		queryString.append("Where " + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_PROGR_LOG.toString() + Constants.SPACE
				+ Constants.EQUAL + Constants.SPACE + Constants.QUESTION_MARK);
		
		

		PreparedStatement ps = conn.prepareStatement(queryString.toString());

		moveFromOtgfflogt(otgfflogt, ps, false);

		logger.debug("query: " + queryString.toString());
		

		row = ps.executeUpdate();

		logger.info("Row affected by update: " + row);

		return row;

	}
	
	public static int updateLogPath(BigDecimal otgflogt_Progr_Log, String path) throws Exception{
		
		logger.debug("Otgfftlogt = start updating logPath of transactionId: " + otgflogt_Progr_Log.toString());

		logger.debug("Value logPath: " + path);

		int row = 0;
		
		Connection conn = DBTypeEnum.fromString(DbConstants.DB_TYPE).getConnection();
		StringBuilder queryString = new StringBuilder();
		
		
		queryString.append("UPDATE " + DbConstants.SCHEMA + "OTGFFLOGT SET");
		
		queryString.append(Constants.SPACE + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_LOG_FILE.toString() + Constants.SPACE + Constants.EQUAL
				+ Constants.SPACE + Constants.QUESTION_MARK + Constants.SPACE);
		
		queryString.append("WHERE " + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_PROGR_LOG.toString() + Constants.SPACE
				+ Constants.EQUAL + Constants.SPACE + Constants.QUESTION_MARK);
		
		logger.debug("query: " + queryString.toString());
		
		PreparedStatement ps = conn.prepareStatement(queryString.toString());
		
		ps.setString(1, path);
		ps.setBigDecimal(2, otgflogt_Progr_Log);
		
		row = ps.executeUpdate();
		
		logger.info("Row affected by update: " + row);
		
		
		return row;
		
	}
	
	public static int updateBackupPath(BigDecimal otgflogt_Progr_Log, String backupPath) throws Exception {
		
		logger.debug("Otgfftlogt = start updating backup path of transactionId: " + otgflogt_Progr_Log.toString());

		logger.debug("Value logPath: " + backupPath);

		int row = 0;
		
		Connection conn = DBTypeEnum.fromString(DbConstants.DB_TYPE).getConnection();
		StringBuilder queryString = new StringBuilder();
		
		
		queryString.append("UPDATE "+ DbConstants.SCHEMA + "OTGFFLOGT SET");
		
		queryString.append(Constants.SPACE + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_BACKUP.toString() + Constants.SPACE + Constants.EQUAL
				+ Constants.SPACE + Constants.QUESTION_MARK + Constants.SPACE);
		
		queryString.append("WHERE " + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_PROGR_LOG.toString() + Constants.SPACE
				+ Constants.EQUAL + Constants.SPACE + Constants.QUESTION_MARK);
		
		logger.debug("query: " + queryString.toString());
		
		PreparedStatement ps = conn.prepareStatement(queryString.toString());
		
		ps.setString(1, backupPath);
		ps.setBigDecimal(2, otgflogt_Progr_Log);
		
		row = ps.executeUpdate();
		
		logger.info("Row affected by update: " + row);
		
		
		return row;
		
	}
	
	
	public static Otgffana readForResubmit(BigDecimal otgflogt_Progr_Log) throws Exception {

		logger.debug("start to read Otgfflogt");
		logger.info("id:" + otgflogt_Progr_Log);
		Otgffana otgffana = new Otgffana();

		Connection conn = null;

		StringBuilder queryString = new StringBuilder();

		queryString.append("Select");

		queryString.append(getCoulmns());

		queryString.append(Constants.SPACE + " FROM " + DbConstants.SCHEMA + "OTGFFLOGT Where ");
		queryString.append(Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_PROGR_LOG.toString() + " = ?");

		conn = DBTypeEnum.fromString(DbConstants.DB_TYPE).getConnection();

		PreparedStatement ps = conn.prepareStatement(queryString.toString());

		ps.setBigDecimal(1, otgflogt_Progr_Log);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {

			logger.info("Move row to Otgfflogt");
			moveToOtgffana(rs, otgffana);
		}

		return otgffana;

	}

	public static Otgfflogt read(BigDecimal otgflogt_Progr_Log) throws Exception {

		logger.debug("start to read Otgfflogt");
		logger.info("id:" + otgflogt_Progr_Log);
		Otgfflogt otgfflogt = new Otgfflogt();

		Connection conn = null;

		StringBuilder queryString = new StringBuilder();

		queryString.append("Select");

		queryString.append(getCoulmns());

		queryString.append(Constants.SPACE + " FROM " + DbConstants.SCHEMA + "OTGFFLOGT Where ");
		queryString.append(Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_PROGR_LOG.toString() + " = ?");

		conn = DBTypeEnum.fromString(DbConstants.DB_TYPE).getConnection();

		PreparedStatement ps = conn.prepareStatement(queryString.toString());

		ps.setBigDecimal(1, otgflogt_Progr_Log);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {

			logger.info("Move row to Otgfflogt");
			moveToOtgfflogt(rs, otgfflogt);
		}

		return otgfflogt;

	}
	
	public static void moveFromOtgfflogt(Otgfflogt otgfflogt, PreparedStatement ps, boolean insert) throws SQLException {

		int paramIndex = 1;
		if (insert) {
			ps.setBigDecimal(paramIndex++, otgfflogt.getOtgflogt_Progr_Log());
		}
		
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Id());
		ps.setTimestamp(paramIndex++, otgfflogt.getOtgflogt_Ts_Inizio());
		ps.setTimestamp(paramIndex++, otgfflogt.getOtgflogt_Ts_Fine());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Esito());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Utente());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Password());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Cod_Interfaccia());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Stato());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Tip_Flusso());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Direzione());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Libreria());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_File());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Membro());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Lib_Source());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_File_Source());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Membro_Source());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Folder());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_File_Name());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Formato());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Delim_Record());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Delim_Campo());
		ps.setBigDecimal(paramIndex++, otgfflogt.getOtgflogt_Codepage());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Mod_Acquiszione());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Controllo());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Tipologia_Conn());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Host());
		ps.setBigDecimal(paramIndex++, otgfflogt.getOtgflogt_Port());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Remote_Folder());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Remote_File_Name());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Utente_Sftp());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Integrity_Check());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Fl_Name_Semaforo());
		ps.setBigDecimal(paramIndex++, otgfflogt.getOtgflogt_Num_Tenta_Ricez());
		ps.setBigDecimal(paramIndex++, otgfflogt.getOtgflogt_Intervallo_Retry());
		ps.setBigDecimal(paramIndex++, otgfflogt.getOtgflogt_Retention());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Compression());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Backup());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Invia_Mail());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Lettera_Ok());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Lettera_Ko());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Log_File());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Errore_Desc());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Job_As());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_User_As());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Job_Nbr_As());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Tipo_Trasferimento());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Bypass_Qtemp());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Java_Home());
		
		//campi aggiuntivi
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Esistenza_File());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Lettera_Flusso());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Crea_Vuoto());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Internaz());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Sost_Val_Null());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Elim_Nom_Col());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Flag_Ok_Vuoto());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Ftp_Secure());
		
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Interactive_Type());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Interactive_Program());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Interactive_Result());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Interactive_Command());
		ps.setBigDecimal(paramIndex++, otgfflogt.getOtgflogt_Delay_Semaforo());
		
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Hash_Unico());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Export_Flag());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Export_Code());
		ps.setBigDecimal(paramIndex++, otgfflogt.getOtgflogt_Fetch_Size());
		ps.setString(paramIndex++, otgfflogt.getOtgflogt_Char_Empty_Space());
		
		

		if (!insert) {
			ps.setBigDecimal(paramIndex++, otgfflogt.getOtgflogt_Progr_Log());
		}

		//ps.setBigDecimal(49, otgfflogt.getOtgflogt_Progr_Log());

	}
	
	public static Otgfflogt moveToOtgfflogt(Otgffana otgffana) {

		Otgfflogt otgfflogt = new Otgfflogt();
		
		otgfflogt.setOtgflogt_Progr_Log(null);
		otgfflogt.setOtgflogt_Id(otgffana.getFana_Id());
		otgfflogt.setOtgflogt_Ts_Inizio(new Timestamp(System.currentTimeMillis()));
		otgfflogt.setOtgflogt_Ts_Fine(new Timestamp(System.currentTimeMillis()));
		otgfflogt.setOtgflogt_Esito("");
		otgfflogt.setOtgflogt_Utente("TEST");
		otgfflogt.setOtgflogt_Password(otgffana.getFana_Password());
		otgfflogt.setOtgflogt_Cod_Interfaccia(otgffana.getFana_Cod_Interfaccia());
		otgfflogt.setOtgflogt_Stato(otgffana.getFana_Stato());
		otgfflogt.setOtgflogt_Tip_Flusso(otgffana.getFana_Tip_Flusso());
		otgfflogt.setOtgflogt_Direzione(otgffana.getFana_Direzione());
		otgfflogt.setOtgflogt_Libreria(otgffana.getFana_Libreria());
		otgfflogt.setOtgflogt_File(otgffana.getFana_File());
		otgfflogt.setOtgflogt_Membro(otgffana.getFana_Membro());
		otgfflogt.setOtgflogt_Lib_Source(otgffana.getFana_Lib_Source());
		otgfflogt.setOtgflogt_File_Source(otgffana.getFana_File_Source());
		otgfflogt.setOtgflogt_Membro_Source(otgffana.getFana_Membro_Source());
		otgfflogt.setOtgflogt_Folder(otgffana.getFana_Folder());
		otgfflogt.setOtgflogt_File_Name(otgffana.getFana_File_Name());
		otgfflogt.setOtgflogt_Formato(otgffana.getFana_Formato());
		otgfflogt.setOtgflogt_Delim_Record(otgffana.getFana_Delim_Record());
		otgfflogt.setOtgflogt_Delim_Campo(otgffana.getFana_Delim_Campo());
		otgfflogt.setOtgflogt_Codepage(otgffana.getFana_Codepage());
		otgfflogt.setOtgflogt_Mod_Acquiszione(otgffana.getFana_Mod_Acquisizione());
		otgfflogt.setOtgflogt_Controllo(otgffana.getFana_Pgm_Controllo());
		otgfflogt.setOtgflogt_Tipologia_Conn(otgffana.getFana_Tipologia_Conn());
		otgfflogt.setOtgflogt_Host(otgffana.getFana_Host());
		otgfflogt.setOtgflogt_Port(otgffana.getFana_Port());
		otgfflogt.setOtgflogt_Remote_Folder(otgffana.getFana_Folder());
		otgfflogt.setOtgflogt_Remote_File_Name(otgffana.getFana_Remote_File_Name());
		otgfflogt.setOtgflogt_Utente_Sftp(otgffana.getFana_Utente_Sftp());
		otgfflogt.setOtgflogt_Integrity_Check(otgffana.getFana_Intergiry_Check());
		otgfflogt.setOtgflogt_Fl_Name_Semaforo(otgffana.getFana_Fl_Name_Semaforo());
		otgfflogt.setOtgflogt_Num_Tenta_Ricez(otgffana.getFana_Num_Tenta_Ricez());
		otgfflogt.setOtgflogt_Intervallo_Retry(otgffana.getFana_Intervallo_Retry());
		otgfflogt.setOtgflogt_Retention(otgffana.getFana_Retention());
		otgfflogt.setOtgflogt_Compression(otgffana.getFana_Compression());
		otgfflogt.setOtgflogt_Backup(otgffana.getFana_Backup());
		otgfflogt.setOtgflogt_Invia_Mail(otgffana.getFana_Invia_Mail());
		otgfflogt.setOtgflogt_Lettera_Ok(otgffana.getFana_Lettera_Ok());
		otgfflogt.setOtgflogt_Lettera_Ko(otgffana.getFana_Lettera_Ko());
		otgfflogt.setOtgflogt_Log_File("");
		otgfflogt.setOtgflogt_Errore_Desc("");
		otgfflogt.setOtgflogt_Job_As("TEST");
		otgfflogt.setOtgflogt_User_As("TEST");
		otgfflogt.setOtgflogt_Job_Nbr_As("0");
		otgfflogt.setOtgflogt_Tipo_Trasferimento(otgffana.getFana_Tipo_Trasferimento());
		otgfflogt.setOtgflogt_Bypass_Qtemp(otgffana.getFana_Bypass_Qtemp());
		otgfflogt.setOtgflogt_Java_Home("");
		
		return otgfflogt;
	}

	public static void moveToOtgfflogt(ResultSet rs, Otgfflogt otgfflogt) throws SQLException, ParseException {

		otgfflogt.setOtgflogt_Progr_Log(rs.getBigDecimal(OtgfflogtCoulmn.OTGFLOGT_PROGR_LOG.name()));
		otgfflogt.setOtgflogt_Id(rs.getString(OtgfflogtCoulmn.OTGFLOGT_ID.name()).trim());
		otgfflogt.setOtgflogt_Ts_Inizio(rs.getTimestamp(OtgfflogtCoulmn.OTGFLOGT_TS_INIZIO.name()));
		otgfflogt.setOtgflogt_Ts_Fine(rs.getTimestamp(OtgfflogtCoulmn.OTGFLOGT_TS_FINE.name()));
		otgfflogt.setOtgflogt_Esito(rs.getString(OtgfflogtCoulmn.OTGFLOGT_ESITO.name()).trim());
		otgfflogt.setOtgflogt_Utente(rs.getString(OtgfflogtCoulmn.OTGFLOGT_UTENTE.name()).trim());
		otgfflogt.setOtgflogt_Password(rs.getString(OtgfflogtCoulmn.OTGFLOGT_PASSWORD.name()).trim());
		otgfflogt.setOtgflogt_Cod_Interfaccia(rs.getString(OtgfflogtCoulmn.OTGFLOGT_COD_INTERFACCIA.name()).trim());
		otgfflogt.setOtgflogt_Stato(rs.getString(OtgfflogtCoulmn.OTGFLOGT_STATO.name()).trim());
		otgfflogt.setOtgflogt_Tip_Flusso(rs.getString(OtgfflogtCoulmn.OTGFLOGT_TIP_FLUSSO.name()).trim());
		otgfflogt.setOtgflogt_Direzione(rs.getString(OtgfflogtCoulmn.OTGFLOGT_DIREZIONE.name()).trim());
		otgfflogt.setOtgflogt_Libreria(rs.getString(OtgfflogtCoulmn.OTGFLOGT_LIBRERIA.name()).trim());
		otgfflogt.setOtgflogt_File(rs.getString(OtgfflogtCoulmn.OTGFLOGT_FILE.name()).trim());
		otgfflogt.setOtgflogt_Membro(rs.getString(OtgfflogtCoulmn.OTGFLOGT_MEMBRO.name()).trim());
		otgfflogt.setOtgflogt_Lib_Source(rs.getString(OtgfflogtCoulmn.OTGFLOGT_LIB_SOURCE.name()).trim());
		otgfflogt.setOtgflogt_File_Source(rs.getString(OtgfflogtCoulmn.OTGFLOGT_FILE_SOURCE.name()).trim());
		otgfflogt.setOtgflogt_Membro_Source(rs.getString(OtgfflogtCoulmn.OTGFLOGT_MEMBRO_SOURCE.name()).trim());
		otgfflogt.setOtgflogt_Folder(rs.getString(OtgfflogtCoulmn.OTGFLOGT_FOLDER.name()));
		otgfflogt.setOtgflogt_File_Name(rs.getString(OtgfflogtCoulmn.OTGFLOGT_FILE_NAME.name()).trim());
		otgfflogt.setOtgflogt_Formato(rs.getString(OtgfflogtCoulmn.OTGFLOGT_FORMATO.name()).trim());
		otgfflogt.setOtgflogt_Delim_Record(rs.getString(OtgfflogtCoulmn.OTGFLOGT_DELIM_RECORD.name()).trim());
		otgfflogt.setOtgflogt_Delim_Campo(rs.getString(OtgfflogtCoulmn.OTGFLOGT_DELIM_CAMPO.name()).trim());
		otgfflogt.setOtgflogt_Codepage(rs.getBigDecimal(OtgfflogtCoulmn.OTGFLOGT_CODEPAGE.name()));
		otgfflogt.setOtgflogt_Mod_Acquiszione(rs.getString(OtgfflogtCoulmn.OTGFLOGT_MOD_ACQUISIZIONE.name()).trim());
		otgfflogt.setOtgflogt_Controllo(rs.getString(OtgfflogtCoulmn.OTGFLOGT_PGM_CONTROLLO.name()).trim());
		otgfflogt.setOtgflogt_Tipologia_Conn(rs.getString(OtgfflogtCoulmn.OTGFLOGT_TIPOLOGIA_CONN.name()).trim());
		otgfflogt.setOtgflogt_Host(rs.getString(OtgfflogtCoulmn.OTGFLOGT_HOST.name()).trim());
		otgfflogt.setOtgflogt_Port(rs.getBigDecimal(OtgfflogtCoulmn.OTGFLOGT_PORT.name()));
		otgfflogt.setOtgflogt_Remote_Folder(rs.getString(OtgfflogtCoulmn.OTGFLOGT_REMOTE_FOLDER.name()).trim());
		otgfflogt.setOtgflogt_Remote_File_Name(rs.getString(OtgfflogtCoulmn.OTGFLOGT_REMOTE_FILE_NAME.name()).trim());
		otgfflogt.setOtgflogt_Utente_Sftp(rs.getString(OtgfflogtCoulmn.OTGFLOGT_UTENTE_SFTP.name()).trim());
		otgfflogt.setOtgflogt_Integrity_Check(rs.getString(OtgfflogtCoulmn.OTGFLOGT_INTEGRITY_CHECK.name()).trim());
		otgfflogt.setOtgflogt_Fl_Name_Semaforo(rs.getString(OtgfflogtCoulmn.OTGFLOGT_FL_NAME_SEMAFORO.name()).trim());
		otgfflogt.setOtgflogt_Num_Tenta_Ricez(rs.getBigDecimal(OtgfflogtCoulmn.OTGFLOGT_NUM_TENTA_RICEZ.name()));
		otgfflogt.setOtgflogt_Intervallo_Retry(rs.getBigDecimal(OtgfflogtCoulmn.OTGFLOGT_INTERVALLO_RETRY.name()));
		otgfflogt.setOtgflogt_Retention(rs.getBigDecimal(OtgfflogtCoulmn.OTGFLOGT_RETENTION.name()));
		otgfflogt.setOtgflogt_Compression(rs.getString(OtgfflogtCoulmn.OTGFLOGT_COMPRESSION.name()).trim());
		otgfflogt.setOtgflogt_Backup(rs.getString(OtgfflogtCoulmn.OTGFLOGT_BACKUP.name()).trim());
		otgfflogt.setOtgflogt_Invia_Mail(rs.getString(OtgfflogtCoulmn.OTGFLOGT_INVIA_MAIL.name()).trim());
		otgfflogt.setOtgflogt_Lettera_Ok(rs.getString(OtgfflogtCoulmn.OTGFLOGT_LETTERA_OK.name()).trim());
		otgfflogt.setOtgflogt_Lettera_Ko(rs.getString(OtgfflogtCoulmn.OTGFLOGT_LETTERA_KO.name()).trim());
		otgfflogt.setOtgflogt_Log_File(rs.getString(OtgfflogtCoulmn.OTGFLOGT_LOG_FILE.name()).trim());
		otgfflogt.setOtgflogt_Errore_Desc(rs.getString(OtgfflogtCoulmn.OTGFLOGT_ERRORE_DESC.name()).trim());
		otgfflogt.setOtgflogt_Job_As(rs.getString(OtgfflogtCoulmn.OTGFLOGT_JOB_AS.name()).trim());
		otgfflogt.setOtgflogt_User_As(rs.getString(OtgfflogtCoulmn.OTGFLOGT_USER_AS.name()).trim());
		otgfflogt.setOtgflogt_Job_Nbr_As(rs.getString(OtgfflogtCoulmn.OTGFLOGT_JOB_NBR_AS.name()).trim());
		otgfflogt.setOtgflogt_Tipo_Trasferimento(rs.getString(OtgfflogtCoulmn.OTGFLOGT_TIPO_TRASFERIMENTO.name()).trim());
		otgfflogt.setOtgflogt_Bypass_Qtemp(rs.getString(OtgfflogtCoulmn.OTGFLOGT_BYPASS_QTEMP.name()).trim());
		otgfflogt.setOtgflogt_Java_Home(rs.getString(OtgfflogtCoulmn.OTGFLOGT_JAVA_HOME.name()).trim());

		otgfflogt.setOtgflogt_Esistenza_File(rs.getString(OtgfflogtCoulmn.OTGFLOGT_ESISTENZA_FILE.name()).trim());
		otgfflogt.setOtgflogt_Lettera_Flusso(rs.getString(OtgfflogtCoulmn.OTGFLOGT_LETTERA_FLUSSO.name()).trim());
		otgfflogt.setOtgflogt_Crea_Vuoto(rs.getString(OtgfflogtCoulmn.OTGFLOGT_CREA_VUOTO.name()).trim());
		otgfflogt.setOtgflogt_Internaz(rs.getString(OtgfflogtCoulmn.OTGFLOGT_INTERNAZ.name()).trim());
		otgfflogt.setOtgflogt_Sost_Val_Null(rs.getString(OtgfflogtCoulmn.OTGFLOGT_SOST_VAL_NULL.name()).trim());
		otgfflogt.setOtgflogt_Elim_Nom_Col(rs.getString(OtgfflogtCoulmn.OTGFLOGT_ELIM_NOM_COL.name()).trim());
		otgfflogt.setOtgflogt_Flag_Ok_Vuoto(rs.getString(OtgfflogtCoulmn.OTGFLOGT_FLAG_OK_VUOTO.name()).trim());
		otgfflogt.setOtgflogt_Ftp_Secure(rs.getString(OtgfflogtCoulmn.OTGFLOGT_FTP_SECURE.name()).trim());
		
		otgfflogt.setOtgflogt_Interactive_Type(rs.getString(OtgfflogtCoulmn.OTGFLOGT_INTERACTIVE_TYPE.name()).trim());
		otgfflogt.setOtgflogt_Interactive_Program(rs.getString(OtgfflogtCoulmn.OTGFLOGT_INTERACTIVE_PROGRAM.name()).trim());
		otgfflogt.setOtgflogt_Interactive_Result(rs.getString(OtgfflogtCoulmn.OTGFLOGT_INTERACTIVE_RESULT.name()).trim());
		otgfflogt.setOtgflogt_Interactive_Command(rs.getString(OtgfflogtCoulmn.OTGFLOGT_INTERACTIVE_COMMAND.name()).trim());
		otgfflogt.setOtgflogt_Delay_Semaforo(rs.getBigDecimal(OtgfflogtCoulmn.OTGFLOGT_DELAY_SEMAFORO.name()));
		
		otgfflogt.setOtgflogt_Hash_Unico(rs.getString(OtgfflogtCoulmn.OTGFLOGT_HASH_UNICO.name()).trim());
		otgfflogt.setOtgflogt_Export_Flag(rs.getString(OtgfflogtCoulmn.OTGFLOGT_EXPORT_FLAG.name()).trim());
		otgfflogt.setOtgflogt_Export_Code(rs.getString(OtgfflogtCoulmn.OTGFLOGT_EXPORT_CODE.name()).trim());
		otgfflogt.setOtgflogt_Fetch_Size(rs.getBigDecimal(OtgfflogtCoulmn.OTGFLOGT_FETCH_SIZE.name()));
		otgfflogt.setOtgflogt_Char_Empty_Space(rs.getString(OtgfflogtCoulmn.OTGFLOGT_CHAR_EMPTY_SPACE.name()).trim());

		
		
		
	}
	
	public static void moveToOtgffana(ResultSet rs, Otgffana otgffana) throws SQLException, ParseException {

		otgffana.setFana_Id(rs.getString(OtgfflogtCoulmn.OTGFLOGT_ID.name()).trim());
		otgffana.setFana_Utente(rs.getString(OtgfflogtCoulmn.OTGFLOGT_UTENTE.name()).trim());
		otgffana.setFana_Password(rs.getString(OtgfflogtCoulmn.OTGFLOGT_PASSWORD.name()).trim());
		otgffana.setFana_Cod_Interfaccia(rs.getString(OtgfflogtCoulmn.OTGFLOGT_COD_INTERFACCIA.name()).trim());
		otgffana.setFana_Stato(rs.getString(OtgfflogtCoulmn.OTGFLOGT_STATO.name()).trim());
		otgffana.setFana_Tip_Flusso(rs.getString(OtgfflogtCoulmn.OTGFLOGT_TIP_FLUSSO.name()).trim());
		otgffana.setFana_Direzione(rs.getString(OtgfflogtCoulmn.OTGFLOGT_DIREZIONE.name()).trim());
		otgffana.setFana_Libreria(rs.getString(OtgfflogtCoulmn.OTGFLOGT_LIBRERIA.name()).trim());
		otgffana.setFana_File(rs.getString(OtgfflogtCoulmn.OTGFLOGT_FILE.name()).trim());
		otgffana.setFana_Membro(rs.getString(OtgfflogtCoulmn.OTGFLOGT_MEMBRO.name()).trim());
		otgffana.setFana_Lib_Source(rs.getString(OtgfflogtCoulmn.OTGFLOGT_LIB_SOURCE.name()).trim());
		otgffana.setFana_File_Source(rs.getString(OtgfflogtCoulmn.OTGFLOGT_FILE_SOURCE.name()).trim());
		otgffana.setFana_Membro_Source(rs.getString(OtgfflogtCoulmn.OTGFLOGT_MEMBRO_SOURCE.name()).trim());
		otgffana.setFana_Folder(rs.getString(OtgfflogtCoulmn.OTGFLOGT_FOLDER.name()));
		otgffana.setFana_File_Name(rs.getString(OtgfflogtCoulmn.OTGFLOGT_FILE_NAME.name()).trim());
		otgffana.setFana_Formato(rs.getString(OtgfflogtCoulmn.OTGFLOGT_FORMATO.name()).trim());
		otgffana.setFana_Delim_Record(rs.getString(OtgfflogtCoulmn.OTGFLOGT_DELIM_RECORD.name()).trim());
		otgffana.setFana_Delim_Campo(rs.getString(OtgfflogtCoulmn.OTGFLOGT_DELIM_CAMPO.name()).trim());
		otgffana.setFana_Codepage(rs.getBigDecimal(OtgfflogtCoulmn.OTGFLOGT_CODEPAGE.name()));
		otgffana.setFana_Mod_Acquisizione(rs.getString(OtgfflogtCoulmn.OTGFLOGT_MOD_ACQUISIZIONE.name()).trim());
		otgffana.setFana_Pgm_Controllo(rs.getString(OtgfflogtCoulmn.OTGFLOGT_PGM_CONTROLLO.name()).trim());
		otgffana.setFana_Tipologia_Conn(rs.getString(OtgfflogtCoulmn.OTGFLOGT_TIPOLOGIA_CONN.name()).trim());
		otgffana.setFana_Host(rs.getString(OtgfflogtCoulmn.OTGFLOGT_HOST.name()).trim());
		otgffana.setFana_Port(rs.getBigDecimal(OtgfflogtCoulmn.OTGFLOGT_PORT.name()));
		otgffana.setFana_Remote_Folder(rs.getString(OtgfflogtCoulmn.OTGFLOGT_REMOTE_FOLDER.name()).trim());
		otgffana.setFana_Remote_File_Name(rs.getString(OtgfflogtCoulmn.OTGFLOGT_REMOTE_FILE_NAME.name()).trim());
		otgffana.setFana_Utente_Sftp(rs.getString(OtgfflogtCoulmn.OTGFLOGT_UTENTE_SFTP.name()).trim());
		otgffana.setFana_Intergiry_Check(rs.getString(OtgfflogtCoulmn.OTGFLOGT_INTEGRITY_CHECK.name()).trim());
		otgffana.setFana_Fl_Name_Semaforo(rs.getString(OtgfflogtCoulmn.OTGFLOGT_FL_NAME_SEMAFORO.name()).trim());
		otgffana.setFana_Num_Tenta_Ricez(rs.getBigDecimal(OtgfflogtCoulmn.OTGFLOGT_NUM_TENTA_RICEZ.name()));
		otgffana.setFana_Intervallo_Retry(rs.getBigDecimal(OtgfflogtCoulmn.OTGFLOGT_INTERVALLO_RETRY.name()));
		otgffana.setFana_Retention(rs.getBigDecimal(OtgfflogtCoulmn.OTGFLOGT_RETENTION.name()));
		otgffana.setFana_Compression(rs.getString(OtgfflogtCoulmn.OTGFLOGT_COMPRESSION.name()).trim());
		otgffana.setFana_Backup(rs.getString(OtgfflogtCoulmn.OTGFLOGT_BACKUP.name()).trim());
		otgffana.setFana_Invia_Mail(rs.getString(OtgfflogtCoulmn.OTGFLOGT_INVIA_MAIL.name()).trim());
		otgffana.setFana_Lettera_Ok(rs.getString(OtgfflogtCoulmn.OTGFLOGT_LETTERA_OK.name()).trim());
		otgffana.setFana_Lettera_Ko(rs.getString(OtgfflogtCoulmn.OTGFLOGT_LETTERA_KO.name()).trim());
		otgffana.setFana_Tipo_Trasferimento(rs.getString(OtgfflogtCoulmn.OTGFLOGT_TIPO_TRASFERIMENTO.name()).trim());
		otgffana.setFana_Bypass_Qtemp(rs.getString(OtgfflogtCoulmn.OTGFLOGT_BYPASS_QTEMP.name()).trim());
		
		
		/*
		otgffana.setFana_Job_As(rs.getString(OtgfflogtCoulmn.OTGFLOGT_JOB_AS.name()).trim());
		otgffana.setFana_User_As(rs.getString(OtgfflogtCoulmn.OTGFLOGT_USER_AS.name()).trim());
		otgffana.setFana_Job_Nbr_As(rs.getString(OtgfflogtCoulmn.OTGFLOGT_JOB_NBR_AS.name()).trim());
		*/
	}
	
	public static StringBuilder getCoulmns() {

		StringBuilder coulmns = new StringBuilder();

		coulmns.append(Constants.SPACE + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_PROGR_LOG.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_ID.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_TS_INIZIO.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_TS_FINE.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_ESITO.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_UTENTE.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_PASSWORD.toString() + Constants.SPACE);
		coulmns.append(
				Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_COD_INTERFACCIA.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_STATO.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_TIP_FLUSSO.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_DIREZIONE.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_LIBRERIA.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_FILE.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_MEMBRO.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_LIB_SOURCE.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_FILE_SOURCE.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_MEMBRO_SOURCE.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_FOLDER.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_FILE_NAME.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_FORMATO.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_DELIM_RECORD.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_DELIM_CAMPO.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_CODEPAGE.toString() + Constants.SPACE);
		coulmns.append(
				Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_MOD_ACQUISIZIONE.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_PGM_CONTROLLO.toString() + Constants.SPACE);
		coulmns.append(
				Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_TIPOLOGIA_CONN.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_HOST.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_PORT.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_REMOTE_FOLDER.toString() + Constants.SPACE);
		coulmns.append(
				Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_REMOTE_FILE_NAME.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_UTENTE_SFTP.toString() + Constants.SPACE);
		coulmns.append(
				Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_INTEGRITY_CHECK.toString() + Constants.SPACE);
		coulmns.append(
				Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_FL_NAME_SEMAFORO.toString() + Constants.SPACE);
		coulmns.append(
				Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_NUM_TENTA_RICEZ.toString() + Constants.SPACE);
		coulmns.append(
				Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_INTERVALLO_RETRY.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_RETENTION.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_COMPRESSION.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_BACKUP.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_INVIA_MAIL.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_LETTERA_OK.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_LETTERA_KO.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_LOG_FILE.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_ERRORE_DESC.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_JOB_AS.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_USER_AS.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_JOB_NBR_AS.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_TIPO_TRASFERIMENTO.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_BYPASS_QTEMP.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_JAVA_HOME.toString() + Constants.SPACE);

		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_ESISTENZA_FILE.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_LETTERA_FLUSSO.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_CREA_VUOTO.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_INTERNAZ.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_SOST_VAL_NULL.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_ELIM_NOM_COL.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_FLAG_OK_VUOTO.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_FTP_SECURE.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_INTERACTIVE_TYPE.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_INTERACTIVE_PROGRAM.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_INTERACTIVE_RESULT.toString() + Constants.SPACE);

		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_INTERACTIVE_COMMAND.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_DELAY_SEMAFORO.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_HASH_UNICO.toString() + Constants.SPACE);

		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_EXPORT_FLAG.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_EXPORT_CODE.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_FETCH_SIZE.toString() + Constants.SPACE);
		coulmns.append(Constants.COMMA + Otgfflogt.OtgfflogtCoulmn.OTGFLOGT_CHAR_EMPTY_SPACE.toString() + Constants.SPACE);

		return coulmns;

	}

}