package it.dmsoft.flowmanager.agent.engine.core.db.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.dmsoft.flowmanager.agent.engine.core.as400.JdbcConnection;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.Otgffana;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.Otgffana.OtgffanaCoulmn;
import it.dmsoft.flowmanager.agent.engine.core.utils.DatabaseUtils.DBTypeEnum;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class OtgffanaDAO {

	private static final Logger logger = Logger.getLogger(OtgffanaDAO.class.getName());

	public static Otgffana read(String id) throws Exception {

		logger.debug("start to read Otgffana");
		logger.info("id:" + id);
		Otgffana otgffana = new Otgffana();

		Connection conn = null;
		String queryString = "SELECT * FROM "+ DbConstants.SCHEMA + "OTGFFANA where FANA_ID = ?";
		conn = DBTypeEnum.fromString(DbConstants.DB_TYPE).getConnection(); 

		PreparedStatement ps = conn.prepareStatement(queryString);
		ps.setString(1, id);
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			
			logger.info("Move row to Otgffana");
			moveToOtgffana(otgffana, rs);
		}

		return otgffana;

	}

	public static void update(Otgffana otgffana) throws Exception {

		logger.debug("start to update Otgffana");
		logger.info("Values otgffana :" + otgffana);

		if (otgffana != null) {
			Connection conn = null;
			logger.info("Prepare query ");
			String queryString = "UPDATE " + DbConstants.SCHEMA + "otgffana " + "where FANA_ID = '"
					+ otgffana.getFana_Id() + "'";
			logger.info("get connection");
			conn = JdbcConnection.get();
			PreparedStatement ps = conn.prepareStatement(queryString);

			// ps.setString(parameterIndex, x);

			ps.executeUpdate();

		}

	}

	public static void delete(String id) throws Exception {

		if (id != null && !id.isEmpty()) {
			Connection conn = null;
			String queryString = "DELETE FROM " + DbConstants.SCHEMA + "otgffana where FANA_ID = ?";
			logger.info("get connection");
			conn = JdbcConnection.get();
			PreparedStatement ps = conn.prepareStatement(queryString);
			ps.setString(1, id);
			ps.executeUpdate();

		}

	}

	private static Otgffana moveToOtgffana(Otgffana otgffana, ResultSet rs) throws SQLException {

		otgffana.setFana_Id(rs.getString(OtgffanaCoulmn.FANA_ID.name()).trim());
		otgffana.setFana_Desc(rs.getString(OtgffanaCoulmn.FANA_DESC.name()).trim());
		otgffana.setFana_Gruppo(rs.getString(OtgffanaCoulmn.FANA_GRUPPO.name()).trim());
		otgffana.setFana_Cod_Interfaccia(rs.getString(OtgffanaCoulmn.FANA_COD_INTERFACCIA.name()).trim());
		otgffana.setFana_Stato(rs.getString(OtgffanaCoulmn.FANA_STATO.name()).trim());
		otgffana.setFana_Tip_Flusso(rs.getString(OtgffanaCoulmn.FANA_TIP_FLUSSO.name()).trim());
		otgffana.setFana_Direzione(rs.getString(OtgffanaCoulmn.FANA_DIREZIONE.name()).trim());
		otgffana.setFana_Libreria(rs.getString(OtgffanaCoulmn.FANA_LIBRERIA.name()).trim());
		otgffana.setFana_File(rs.getString(OtgffanaCoulmn.FANA_FILE.name()).trim());
		otgffana.setFana_Membro(rs.getString(OtgffanaCoulmn.FANA_MEMBRO.name()).trim());
		otgffana.setFana_Lib_Source(rs.getString(OtgffanaCoulmn.FANA_LIB_SOURCE.name()).trim());
		otgffana.setFana_File_Source(rs.getString(OtgffanaCoulmn.FANA_FILE_SOURCE.name()).trim());
		otgffana.setFana_Membro_Source(rs.getString(OtgffanaCoulmn.FANA_MEMBRO_SOURCE.name()).trim());
		otgffana.setFana_Folder(rs.getString(OtgffanaCoulmn.FANA_FOLDER.name()).trim());
		otgffana.setFana_File_Name(rs.getString(OtgffanaCoulmn.FANA_FILE_NAME.name()).trim());
		otgffana.setFana_Formato(rs.getString(OtgffanaCoulmn.FANA_FORMATO.name()).trim());
		otgffana.setFana_Delim_Record(rs.getString(OtgffanaCoulmn.FANA_DELIM_RECORD.name()).trim());
		otgffana.setFana_Delim_Stringa(rs.getString(OtgffanaCoulmn.FANA_DELIM_STRINGA.name()).trim());
		otgffana.setFana_Rimoz_Spazi(rs.getString(OtgffanaCoulmn.FANA_RIMOZ_SPAZI.name()).trim());
		otgffana.setFana_Delim_Campo(rs.getString(OtgffanaCoulmn.FANA_DELIM_CAMPO.name()).trim());
		otgffana.setFana_Riemp_Campo(rs.getString(OtgffanaCoulmn.FANA_RIEMP_CAMPO.name()).trim());
		otgffana.setFana_Codepage(new BigDecimal(rs.getString(OtgffanaCoulmn.FANA_CODEPAGE.name())));
		otgffana.setFana_Mod_Acquisizione(rs.getString(OtgffanaCoulmn.FANA_MOD_ACQUISIZIONE.name()).trim());
		otgffana.setFana_From_Ccsid(new BigDecimal(rs.getString(OtgffanaCoulmn.FANA_FROM_CCSID.name())));
		otgffana.setFana_Pgm_Controllo(rs.getString(OtgffanaCoulmn.FANA_PGM_CONTROLLO.name()).trim());
		otgffana.setFana_Tipologia_Conn(rs.getString(OtgffanaCoulmn.FANA_TIPOLOGIA_CONN.name()).trim());
		otgffana.setFana_Host(rs.getString(OtgffanaCoulmn.FANA_HOST.name()).trim());
		otgffana.setFana_Port(new BigDecimal(rs.getString(OtgffanaCoulmn.FANA_PORT.name())));
		otgffana.setFana_Remote_Folder(rs.getString(OtgffanaCoulmn.FANA_REMOTE_FOLDER.name()).trim());
		otgffana.setFana_Remote_File_Name(rs.getString(OtgffanaCoulmn.FANA_REMOTE_FILE_NAME.name()).trim());
		otgffana.setFana_Utente(rs.getString(OtgffanaCoulmn.FANA_UTENTE.name()).trim());
		otgffana.setFana_Password(rs.getString(OtgffanaCoulmn.FANA_PASSWORD.name()).trim());
		otgffana.setFana_Utente_Sftp(rs.getString(OtgffanaCoulmn.FANA_UTENTE_SFTP.name()).trim());
		otgffana.setFana_Intergiry_Check(rs.getString(OtgffanaCoulmn.FANA_INTEGRITY_CHECK.name()).trim());
		otgffana.setFana_Fl_Name_Semaforo(rs.getString(OtgffanaCoulmn.FANA_FL_NAME_SEMAFORO.name()).trim());
		otgffana.setFana_Num_Tenta_Ricez(new BigDecimal(rs.getString(OtgffanaCoulmn.FANA_NUM_TENTA_RICEZ.name())));
		otgffana.setFana_Intervallo_Retry(new BigDecimal(rs.getString(OtgffanaCoulmn.FANA_INTERVALLO_RETRY.name())));
		otgffana.setFana_Retention(new BigDecimal(rs.getString(OtgffanaCoulmn.FANA_RETENTION.name()).trim()));
		otgffana.setFana_Compression(rs.getString(OtgffanaCoulmn.FANA_COMPRESSION.name()).trim());
		otgffana.setFana_De_Compression(rs.getString(OtgffanaCoulmn.FANA_DE_COMPRESSION.name()).trim());
		otgffana.setFana_Backup(rs.getString(OtgffanaCoulmn.FANA_BACKUP.name()).trim());
		otgffana.setFana_Invia_Mail(rs.getString(OtgffanaCoulmn.FANA_INVIA_MAIL.name()).trim());
		otgffana.setFana_Lettera_Ok(rs.getString(OtgffanaCoulmn.FANA_LETTERA_OK.name()).trim());
		otgffana.setFana_Lettera_Ko(rs.getString(OtgffanaCoulmn.FANA_LETTERA_KO.name()).trim());
		otgffana.setFana_Prog_Person(rs.getString(OtgffanaCoulmn.FANA_PROG_PERSON.name()).trim());
		otgffana.setFana_Known_Ht_Fl(rs.getString(OtgffanaCoulmn.FANA_KNOWN_HT_FL.name()).trim());
		otgffana.setFana_Key_Fl(rs.getString(OtgffanaCoulmn.FANA_KEY_FL.name()).trim());
		otgffana.setFana_Modalita_Passiva(rs.getString(OtgffanaCoulmn.FANA_MODALITA_PASSIVA.name()).trim());
		otgffana.setFana_Job_Desc(rs.getString(OtgffanaCoulmn.FANA_JOB_DESC.name()).trim());
		otgffana.setFana_Lib_Job_Desc(rs.getString(Otgffana.OtgffanaCoulmn.FANA_LIB_JOB_DESC.name()).trim());
		otgffana.setFana_Cancella_File(rs.getString(Otgffana.OtgffanaCoulmn.FANA_CANCELLA_FIL.name()).trim());
		otgffana.setFana_Risottomettibile(rs.getString(Otgffana.OtgffanaCoulmn.FANA_RISOTTOMET.name()).trim());
		otgffana.setFana_Lunghezza_Fl_Flat(rs.getBigDecimal(Otgffana.OtgffanaCoulmn.FANA_LUNGHEZZA_FL_FLAT.name()));
		otgffana.setFana_Tipo_Trasferimento(rs.getString(Otgffana.OtgffanaCoulmn.FANA_TIPO_TRASFERIMENTO.name()).trim());
		otgffana.setFana_Bypass_Qtemp(rs.getString(OtgffanaCoulmn.FANA_BYPASS_QTEMP.name()).trim());
		otgffana.setFana_Esistenza_File(rs.getString(OtgffanaCoulmn.FANA_ESISTENZA_FILE.name()).trim());
		otgffana.setFana_Lettera_Flusso(rs.getString(OtgffanaCoulmn.FANA_LETTERA_FLUSSO.name()).trim());
		otgffana.setFana_Agg_Nomi_Col(rs.getString(Otgffana.OtgffanaCoulmn.FANA_AGG_NOMI_COL.name()).trim());
		otgffana.setFana_Crea_Vuoto(rs.getString(OtgffanaCoulmn.FANA_CREA_VUOTO.name().trim()));
		otgffana.setFana_Internaz(rs.getString(OtgffanaCoulmn.FANA_INTERNAZ.name().trim()));
		otgffana.setFana_Sost_Val_Null(rs.getString(Otgffana.OtgffanaCoulmn.FANA_SOST_VAL_NULL.name().trim()));
		otgffana.setFana_Elim_Nom_Col(rs.getString(OtgffanaCoulmn.FANA_ELIM_NOM_COL.name().trim()));
		otgffana.setFana_Flag_Ok_Vuoto(rs.getString(OtgffanaCoulmn.FANA_FLAG_OK_VUOTO.name().trim()));
		otgffana.setFana_Ftp_Secure(rs.getString(OtgffanaCoulmn.FANA_FTP_SECURE.name().trim()));
		otgffana.setFana_Interactive_Type(rs.getString(OtgffanaCoulmn.FANA_INTERACTIVE_TYPE.name().trim()));
		otgffana.setFana_Interactive_Program(rs.getString(OtgffanaCoulmn.FANA_INTERACTIVE_PROGRAM.name().trim()));
		otgffana.setFana_Interactive_Result(rs.getString(OtgffanaCoulmn.FANA_INTERACTIVE_RESULT.name().trim()));
		otgffana.setFana_Interactive_Command(rs.getString(OtgffanaCoulmn.FANA_INTERACTIVE_COMMAND.name().trim()));
		otgffana.setFana_Delay_Semaforo(rs.getBigDecimal(Otgffana.OtgffanaCoulmn.FANA_DELAY_SEMAFORO.name()));
		otgffana.setFana_Hash_Unico(rs.getString(OtgffanaCoulmn.FANA_HASH_UNICO.name().trim()));
		otgffana.setFana_Export_Flag(rs.getString(OtgffanaCoulmn.FANA_EXPORT_FLAG.name().trim()));
		otgffana.setFana_Export_Code(rs.getString(OtgffanaCoulmn.FANA_EXPORT_CODE.name().trim()));
		otgffana.setFana_Fetch_Size(rs.getBigDecimal(OtgffanaCoulmn.FANA_FETCH_SIZE.name()));
		otgffana.setFana_char_empty_space(rs.getString(OtgffanaCoulmn.FANA_CHAR_EMPTY_SPACE.name()));
		
		
		return otgffana;

	}



}