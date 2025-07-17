package it.dmsoft.flowmanager.agent.engine.core.db.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.dmsoft.flowmanager.agent.engine.core.as400.JdbcConnection;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.Otgffana;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.Otgffana.OtgffanaCoulmn;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.Otgffhash;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.Otgffhash.OtgffhashCoulmn;
import it.dmsoft.flowmanager.agent.engine.core.utils.DatabaseUtils.DBTypeEnum;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class OtgffhashDAO {

	private static final Logger logger = Logger.getLogger(OtgffhashDAO.class.getName());
	
	public static Integer existsHash(String fileHash) throws Exception {
		logger.debug("start to existsHash");
		Connection conn = null;
		
        String query = "SELECT OTGFHASH_PROGR_LOGT FROM " + DbConstants.SCHEMA + "OTGFFHASH WHERE otgfhash_Footprint = ?";
        conn = DBTypeEnum.fromString(DbConstants.DB_TYPE).getConnection();
        
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, fileHash);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("OTGFHASH_PROGR_LOGT");
        } else {
            return null;  // Oppure un valore speciale, ad esempio -1
        }
	}


	public static void saveHash(String fileHash, BigDecimal transactionId, String anaId) throws Exception {
		logger.debug("start to insert in Otgffhash");
		
		Connection conn = null;
        String query = "INSERT INTO " + DbConstants.SCHEMA + "OTGFFHASH (otgfhash_Progr_Logt, otgfhash_Id, otgfhash_Footprint) VALUES (?, ?, ?)";

        conn = JdbcConnection.get();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setBigDecimal(1, transactionId);
        stmt.setString(2, anaId);
        stmt.setString(3, fileHash);
        stmt.executeUpdate();
        stmt.close();
    }


	private static Otgffhash moveToOtgffhash(Otgffhash otgffhash, ResultSet rs) throws SQLException {

		otgffhash.setOtgfhash_Progr_Logt(new BigDecimal(rs.getString(OtgffhashCoulmn.OTGFHASH_PROGR_LOGT.name())));
		otgffhash.setOtgfhash_Id(rs.getString(OtgffhashCoulmn.OTGFHASH_ID.name()).trim());
		otgffhash.setOtgfhash_Footprint(rs.getString(OtgffhashCoulmn.OTGFHASH_FOOTPRINT.name()).trim());
		
		return otgffhash;

	}
}