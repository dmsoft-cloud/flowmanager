package it.dmsoft.flowmanager.agent.engine.core.utils;

import it.dmsoft.flowmanager.agent.engine.core.db.dao.DbConstants;


public class ExportUtils {
	
	public enum ExportType {
		STANDARD("S") {

			@Override
			public String getQryInsertNewColumns(String confLib) {
				StringBuilder qs1= new StringBuilder();		
				qs1.append("INSERT INTO " + DbConstants.SCHEMA + "OTTSFEXPTD ");
	    		qs1.append("(EXPTD_IDE, EXPTD_PRG, EXPTD_SEQ) ");
	    		qs1.append(" (SELECT A.EXPD_IDE , A.EXPD_PRG, A.EXPD_PRG FROM ");
	    		qs1.append( confLib + "OTTSFEXPD A ");
	    		qs1.append("WHERE A.EXPD_IDE = ? AND NOT EXISTS ");
	    		qs1.append(" (SELECT 0 FROM " + DbConstants.SCHEMA + "OTTSFEXPTD B ");
	    		qs1.append("WHERE B.EXPTD_IDE = A.EXPD_IDE AND B.EXPTD_PRG = A.EXPD_PRG)) ");	    	
				return qs1.toString();
			}

			@Override
			public String getQryRemoveColumns(String confLib) {
				StringBuilder qs2= new StringBuilder();		
				//qs2.append("SELECT * FROM " + DbConstants.SCHEMA + "OTTSFEXPTD  ");
				qs2.append("DELETE FROM " + DbConstants.SCHEMA + "OTTSFEXPTD  ");

				qs2.append(" WHERE  EXPTD_IDE = ? ");
	    		
	    		qs2.append(" AND NOT EXISTS ");
	    		qs2.append(" (SELECT 0 FROM ");
	    		qs2.append( confLib + "OTTSFEXPD ");
	    		qs2.append(" WHERE EXPTD_IDE = EXPD_IDE AND ");
	    		qs2.append(" EXPTD_PRG = EXPD_PRG) ");
	    		return qs2.toString();
			}

			@Override
			public String getQryExtractExportData(String confLib) {
				StringBuilder qs= new StringBuilder();
				qs.append("SELECT EXP_IDE as EXPORT, EXPD_PRG as PROGRESSIVO, EXPD_TESTATA as COL_DESC, "
	    				+ " EXPTD_SEQ as SEQ FROM ");
				qs.append(confLib + "OTTSFEXP " +
	    				" join "  + confLib + "OTTSFEXPD ");
	    		qs.append(" on EXP_IDE = EXPD_IDE  ");
	    		qs.append(" left join "  + DbConstants.SCHEMA + "OTTSFEXPT ");
	    		qs.append(" on EXP_IDE = EXPT_IDE ");
	            qs.append(" left join " + DbConstants.SCHEMA + "OTTSFEXPTD ");
	            qs.append(" on EXP_IDE = EXPTD_IDE ");
	            qs.append( " and EXPD_PRG = EXPTD_PRG ");
	            qs.append( " where EXP_IDE = ? ");
	    		
				return qs.toString();
			}
			
			
			
			
		},
		OLD("O"){

			@Override
			public String getQryInsertNewColumns(String confLib) {
				StringBuilder qs1= new StringBuilder();		
				qs1.append("INSERT INTO " + DbConstants.SCHEMA + "SYEXPTFCPD ");
	    		qs1.append("(EXPTFCPD_FILE, EXPTFCPD_PROGRESSIVO, EXPTFCPD_ALLINEAMENTO, EXPTFCPD_SEQUENZA) ");
	    		qs1.append(" (SELECT A.EXPTFD_FILE , ' ' ,A.EXPTFD_PROGRESSIVO, A.EXPTFD_PROGRESSIVO FROM ");
	    		qs1.append( confLib + "SYEXPTFD A ");
	    		qs1.append("WHERE A.EXPTFD_FILE = ? AND NOT EXISTS ");
	    		qs1.append(" (SELECT 0 FROM " + DbConstants.SCHEMA + "SYEXPTFCPD B ");
	    		qs1.append("WHERE B.EXPTFCPD_FILE = A.EXPTFD_FILE AND B.EXPTFCPD_PROGRESSIVO = A.EXPTFD_PROGRESSIVO)) ");	    	
				return qs1.toString();
			}

			@Override
			public String getQryRemoveColumns(String confLib) {
				StringBuilder qs2= new StringBuilder();		
				qs2.append("DELETE FROM " + DbConstants.SCHEMA + "SYEXPTFCPD  ");
	    		qs2.append(" WHERE  SYEXPTFCPD.EXPTFCPD_FILE = ? AND NOT EXISTS ");
	    		qs2.append(" (SELECT 0 FROM ");
	    		qs2.append( confLib + "SYEXPTFD  ");
	    		qs2.append(" WHERE SYEXPTFCPD.EXPTFCPD_FILE = SYEXPTFD.EXPTFD_FILE AND ");
	    		qs2.append(" SYEXPTFCPD.EXPTFCPD_PROGRESSIVO = SYEXPTFD.EXPTFD_PROGRESSIVO) ");
	    		return qs2.toString();
			}

			@Override
			public String getQryExtractExportData(String confLib) {
				StringBuilder qs= new StringBuilder();
				qs.append("SELECT EXPTF_FILE EXPORT, EXPTFD_PROGRESSIVO as PROGRESSIVO,"
	    				+ " EXPTFD_DESCRIZIONE as COL_DESC, EXPTFCPD_SEQUENZA as SEQ FROM ");
				qs.append(confLib + "SYEXPTF " +
	    				" join "  + confLib +  "SYEXPTFD " );
				qs.append(" on EXPTF_FILE = EXPTFD_FILE  ");
	    		qs.append(" left join "  + DbConstants.SCHEMA + "SYEXPTFCPY ");
	    		qs.append(" on EXPTF_FILE = EXPTFCPY_FILE ");
	            qs.append(" left join " + DbConstants.SCHEMA + "SYEXPTFCPD ");
	            qs.append(" on EXPTF_FILE = EXPTFCPD_FILE ");
	            qs.append( " and EXPTFD_PROGRESSIVO = EXPTFCPD_PROGRESSIVO ");
	            qs.append( " where EXPTF_FILE = ? ");
				return qs.toString();
			}
			
			
			
		};
		
		private String code;

		public String getCode() {
			return code;
		}

		private ExportType(String code) {
			this.code = code;
		}
		
		 // Metodo per cercare un enum a partire dal valore stringa
	    public static ExportType fromString(String exportType) {
	        for (ExportType exType : ExportType.values()) {
	            if (exType.code.equalsIgnoreCase(exportType)) {
	                return exType;
	            }
	        }
	        // Se non viene trovato alcun valore corrispondente, restituisce null
	        return null;
	    }


		public abstract String getQryInsertNewColumns(String confLib);

		public abstract String getQryRemoveColumns(String confLib);
		
		public abstract String getQryExtractExportData(String confLib);
	}
	

}
