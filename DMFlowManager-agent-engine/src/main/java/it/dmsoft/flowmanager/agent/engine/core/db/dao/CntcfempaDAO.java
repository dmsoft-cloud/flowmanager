package it.dmsoft.flowmanager.agent.engine.core.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.dmsoft.flowmanager.agent.engine.core.db.dto.Cntcfempa;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.Cntcfempa.CntcfempaCoulmn;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.DatabaseUtils.DBTypeEnum;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class CntcfempaDAO {

	private static final Logger logger = Logger.getLogger(CntcfempaDAO.class.getName());

	public static Cntcfempa read(String cntcempaAccount) throws Exception {

		logger.debug("start to read Cntcfempa");
		logger.info("cntcempaAccount: " + cntcempaAccount);
		Cntcfempa cntcfempa = new Cntcfempa();

		Connection conn = null;
		StringBuilder queryString= new StringBuilder();
		
		queryString.append("SELECT");
		queryString.append(getCoulmns().toString());
		queryString.append("FROM " + DbConstants.SCHEMA + "CNTCFEMPA where CNTCEMPA_ACCOUNT = ?");
		
		logger.info("Query: " + queryString.toString());
		conn = DBTypeEnum.fromString(DbConstants.DB_TYPE).getConnection();

		PreparedStatement ps = conn.prepareStatement(queryString.toString());
		ps.setString(1, cntcempaAccount);
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			
			logger.info("Move row to Cntcfempa");
			moveToCntcfempa(cntcfempa, rs);
		}

		return cntcfempa;

	}



	

	private static Cntcfempa moveToCntcfempa(Cntcfempa cntcfempa, ResultSet rs) throws SQLException {
		
		
		
		cntcfempa.setCntcempa_Account(rs.getString(CntcfempaCoulmn.CNTCEMPA_ACCOUNT.name()).trim());
		cntcfempa.setCntcempa_Server_U(rs.getString(CntcfempaCoulmn.CNTCEMPA_SERVER_U.name()).trim());
		cntcfempa.setCntcempa_Porta_U(rs.getBigDecimal(CntcfempaCoulmn.CNTCEMPA_PORTA_U.name()));
		cntcfempa.setCntcempa_Utente_U(rs.getString(CntcfempaCoulmn.CNTCEMPA_UTENTE_U.name()).trim());
		cntcfempa.setCntcempa_Password_U(rs.getBytes(CntcfempaCoulmn.CNTCEMPA_PASSWORD_U.name()));
		cntcfempa.setCntcempa_Autenticaz(rs.getString(CntcfempaCoulmn.CNTCEMPA_AUTENTICAZ.name()).trim());
		cntcfempa.setCntcempa_Protocollo_U(rs.getString(CntcfempaCoulmn.CNTCEMPA_PROTOCOLLO_U.name()).trim());
		cntcfempa.setCntcempa_Flag_Ssl_U(rs.getString(CntcfempaCoulmn.CNTCEMPA_FLAG_SSL_U.name()).trim());
		cntcfempa.setCntcempa_Path_Al_As(rs.getString(CntcfempaCoulmn.CNTCEMPA_PATH_AL_AS.name()).trim());
		cntcfempa.setCntcempa_Path_Al_Pc(rs.getString(CntcfempaCoulmn.CNTCEMPA_PATH_AL_PC.name()).trim());
		cntcfempa.setCntcempa_Path_Email(rs.getString(CntcfempaCoulmn.CNTCEMPA_PATH_EMAIL.name()).trim());
		cntcfempa.setCntcempa_Tipo_Email(rs.getString(CntcfempaCoulmn.CNTCEMPA_TIPO_EMAIL.name()).trim());
		cntcfempa.setCntcempa_Server_I(rs.getString(CntcfempaCoulmn.CNTCEMPA_SERVER_I.name()).trim());
		cntcfempa.setCntcempa_Porta_I(rs.getBigDecimal(CntcfempaCoulmn.CNTCEMPA_PORTA_I.name()));
		cntcfempa.setCntcempa_Utente_I(rs.getString(CntcfempaCoulmn.CNTCEMPA_UTENTE_I.name()).trim());
		cntcfempa.setCntcempa_Password_I(rs.getBytes(CntcfempaCoulmn.CNTCEMPA_PASSWORD_I.name()));
		cntcfempa.setCntcempa_Protocollo_I(rs.getString(CntcfempaCoulmn.CNTCEMPA_PROTOCOLLO_I.name()).trim());
		cntcfempa.setCntcempa_Flag_Ssl_I(rs.getString(CntcfempaCoulmn.CNTCEMPA_FLAG_SSL_I.name()).trim());
		cntcfempa.setCntcempa_Path_Save(rs.getString(CntcfempaCoulmn.CNTCEMPA_PATH_SAVE.name()).trim());
		
		
		return cntcfempa;

	}
	
	public static StringBuilder getCoulmns() {
		StringBuilder sb= new StringBuilder();
		
		
		sb.append(Constants.SPACE + CntcfempaCoulmn.CNTCEMPA_ACCOUNT.toString() + Constants.SPACE);
		sb.append(Constants.COMMA + CntcfempaCoulmn.CNTCEMPA_SERVER_U.toString() + Constants.SPACE);
		sb.append(Constants.COMMA + CntcfempaCoulmn.CNTCEMPA_PORTA_U.toString() + Constants.SPACE);
		sb.append(Constants.COMMA + CntcfempaCoulmn.CNTCEMPA_UTENTE_U.toString() + Constants.SPACE);
		sb.append(Constants.COMMA + CntcfempaCoulmn.CNTCEMPA_PASSWORD_U.toString() + Constants.SPACE);
		sb.append(Constants.COMMA + CntcfempaCoulmn.CNTCEMPA_AUTENTICAZ.toString() + Constants.SPACE);
		sb.append(Constants.COMMA + CntcfempaCoulmn.CNTCEMPA_PROTOCOLLO_U.toString() + Constants.SPACE);
		sb.append(Constants.COMMA + CntcfempaCoulmn.CNTCEMPA_FLAG_SSL_U.toString() + Constants.SPACE);
		sb.append(Constants.COMMA + CntcfempaCoulmn.CNTCEMPA_PATH_AL_AS.toString() + Constants.SPACE);
		sb.append(Constants.COMMA + CntcfempaCoulmn.CNTCEMPA_PATH_AL_PC.toString() + Constants.SPACE);
		sb.append(Constants.COMMA + CntcfempaCoulmn.CNTCEMPA_PATH_EMAIL.toString() + Constants.SPACE);
		sb.append(Constants.COMMA + CntcfempaCoulmn.CNTCEMPA_TIPO_EMAIL.toString() + Constants.SPACE);
		sb.append(Constants.COMMA + CntcfempaCoulmn.CNTCEMPA_SERVER_I.toString() + Constants.SPACE);
		sb.append(Constants.COMMA + CntcfempaCoulmn.CNTCEMPA_PORTA_I.toString() + Constants.SPACE);
		sb.append(Constants.COMMA + CntcfempaCoulmn.CNTCEMPA_UTENTE_I.toString() + Constants.SPACE);
		sb.append(Constants.COMMA + CntcfempaCoulmn.CNTCEMPA_PASSWORD_I.toString() + Constants.SPACE);
		sb.append(Constants.COMMA + CntcfempaCoulmn.CNTCEMPA_PROTOCOLLO_I.toString() + Constants.SPACE);
		sb.append(Constants.COMMA + CntcfempaCoulmn.CNTCEMPA_FLAG_SSL_I.toString() + Constants.SPACE);
		sb.append(Constants.COMMA + CntcfempaCoulmn.CNTCEMPA_PATH_SAVE.toString() + Constants.SPACE);
		
		
		return sb;
	}

	

}