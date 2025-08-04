package it.dmsoft.flowmanager.agent.engine.core.utils;

import java.math.BigDecimal;

public class Constants {
	
	public static final String PATH_DELIMITER = "/";
	
	public static final String OPEN_BRACKET = "(";
	
	public static final String CLOSE_BRACKET = ")";
	
	public static final String SINGLE_QUOTATION_MARK = "'";
	
	public static final String DOUBLE_QUOTE = "\"";
	
	public static final String EQUAL = "=";
	
	public static final String DOT = ".";
	
	public static final String STAR = "*";
	
	public static final String COMMA = ",";
	
	public static final String SEMICOLON = ";";
	
	public static final String QUESTION_MARK = "?";
	
	public static final String CURLIB = "*CURLIB";
	
	public static final String LIBL = "*LIBL";

	public static final String GESTOREFLUSSI = "GESTOREFLUSSI";
	
	public static final String SPACE = " ";
	
	public static final String LIB = "LIB";
	
	public static final String FILE = "FILE";
	
	public static final String MBR = "MBR";
	
	public static final String QSYS = "QSYS";
	
	public static final String SI = "S";
	
	public static final String NO = "N";
	
	public static final String EXF = "*EXF";
	
	public static final String SPEDIZIONE = "I";
	
	public static final String FORMAT_TIMESTAMP = "yyyy-MM-dd-hh.mm.ss.SSSSSS";

	public static final String KO = "KO";

	public static final String EMPTY_TIMESTAMP = "0000-00-00-00.00.00.000000";
	
	public static final String OK = "OK";
	
	public static final String OUTCOME = "OUTCOME";
	
	public static final String ZIP_EXTENSION = ".zip";
	
	public static final String QGPL = "QGPL";

	public static final String ERROR = "ERROR";
	
	public static final String QTEMP = "QTEMP";
	
	public static final BigDecimal MAIL_TIMEOUT = new BigDecimal(180000);
	
	public static final String IFS = "I";
	
	public static final String SPOOL = "S";
	
	public static final String THEMA_SPAZIO = "S";
	
	public static final String IFS_FORMAT = "*IFS";
	
	public static final String AS400_FORMAT = "*AS400";
	
	//public static final String DB2 = "D";

	//public static final String INBOUND = "I";
	
	//public static final String OUTBOUND = "O";
	
	public static final String ROWID = "ROWID";

	public static final String MAIL_SUBJECT = "Gestore Flussi";

	public static final String KO_DESCR = "Error on execution: ";

	public static final String END_PHASE_DESCR = "End of phase";
	
	public static final String START_PHASE_DESCR = "Start of phase";

	public static final String HOME = "/home";

	public static final String KNOWN_HOSTS_FILE = ".ssh/known_hosts";
	
	public static final String KEY_FILE = ".ssh/id_rsa";

	public static final String AT = "@";
	
	public static final String CHANGE_DIRECTORY = "cd";
	
	public static final String AND_OPERATOR = "&&";
	
	public static final String IBM280= "IBM280";
	
	public static final String $DT = "$DT";
	
	public static final String $DS = "$DS";
	
	public static final String $TS = "$TS";

	public static final String $DH = "$DH";
	
	public static final String $FMP = "$FMP";
	
	public static final String $IMP = "$IMP";
	
	public static final String $FMS = "$FMS";
	
	public static final String $IMS = "$IMS";
	
	public static final String $FMC = "$FMC";
	
	public static final String $IMC = "$IMC";
	
	//public static final String $PR = "$PR";
	
	public static final String CARET = "^";
	
	public static final String DOLLAR = "$";

	public static final String NOCHECK = "*NOCHK";
	
	public static final String LAST = "*LAST";
	
	public static final String SELECT = "*SELECT";
	
	public static final String UNREAD = "*UNREAD";

	public static final String UNDERSCORE = "_";

	public static final String $D6 = "$D6";

	public static final String $H4 = "$H4";
	
	public static final String $H2 = "$H2";

	public static final String $H6 = "$H6";

	public static final String $PR__REGEXP = "\\$([0-9])PR";
	
	public static final String REGEXP_WILDCARD = ".*";

	public static final String[] REGEXP_NOT_SUPPORTED = new String[] {"<", "(", "[", "{", "\\", "^", "-", "=", "$", "!", "|", "]", "}", ")", /*"?", "*",*/ "+", ".", ">"};

	public static final String ENV_PREFIX = "env.";

	public static final String FTP_FILE_NOT_FOUND = "Impossibile recuperare il file";

	public static final String SFTP_FILE_NOT_FOUND = "No such file";

	public static final String PGM_LIBRARY_KEY = "pgmlibrary";
		
	public static final String $DS__REGEXP = "\\$([0-9])([+-])([A-Z]+)DS";
	
	public static final String MINUS = "-";
	
	public static final String FIXED = "*FIXED";

	public static final String FIRST = "*FIRST";
	
	public static final String YES_AS400 = "*YES";
	
	public static final String NO_AS400 = "*NO";
	
	public static final String CSV_FORMAT = "csvFormat";
	
	public static final String CSV_FORMAT_IT = "IT";
	
	public static final String CSV_FORMAT_NO = "NO";
	
	public static final String CSV_FORMAT_COMMA = "*COMMA";
	
	public static final String CSV_FORMAT_NONE = "*NONE";
	
	public static final String USER = "user";
	
	public static final String PASSWORD = "password";
	
	public static final String LOCALHOST = "localhost";

	public static final String FILE_LIST_FOLDER = "fileListFolder";

	public static final String FILENAME_SET = "[0-9a-zA-Z_-.]";
	
	public static final String $DT_SEPARATOR_REGEXP = "\\$([A-Z]+)DT";
	
	public static final String $D6_SEPARATOR_REGEXP = "\\$([A-Z]+)D6";
	
	public static final String $DS_SEPARATOR_REGEXP = "\\$([A-Z]+)DS|\\$(([0-9])([+-])([A-Z]+))DS";
	
	public static final String AUT_INDIR = "*INDIR";
	
	//variabili per parser json di input
	public static final String CONF_TRANSACTION_NAME = "COD_FLUSSO";
	
	public static final String CONF_TRANSACTION_ID = "ID_LOG";
	
	public static final String CONF_LOG_PATH = "LOG_PATH";
	
	public static final String CONF_LOG_LEVEL = "LOG_LEVEL";
	
	public static final String CONF_LOG_SIZE = "LOG_SIZE";
	
	public static final String CONF_LOG_ROTATION = "LOG_ROTATION";
	
	public static final String CONF_BACKUP_PATH = "PATH_BACKUP";
	
	public static final String CONF_MAIL_FROM = "MAIL_FROM";
	
	public static final String CONF_CUSTOMER = "CLIENTE";	
	
	public static final String CONF_EXECUTION_DATE = "EXECUTION_DATE";
	
	public static final String CONF_LEGACY_MODERNIZATION = "LEGACY_MOD";
	
	public static final String CONF_EXT_JOB = "EXT_JOB";
	
	public static final String CONF_EXT_USER = "EXT_USER";
	
	public static final String CONF_EXT_NUMBER = "EXT_NUMBER";
	
	public static final String CONF_EXT_TASK = "EXT_TASK";
	
	public static final String CONF_MASTERDATA_OVERRIDES = "OVERRIDES";
	
	public static final String CFT_CL_PGM = "cftPgm";

	public static final Integer DEFAULT_FTP_PORT = 21;

	public static final Integer DEFAULT_SFTP_PORT = 22;
	
	
	//
	public enum OperationType {
		CHECK_OBJ("Check Obj"),
		COPY_FILE("Copy File"),
		BACKUP("Backup"),
		ZIP("Zip"),
		CREATE_DB("Create Database CRTPF"),
		CREATE_FL("Create FILE QSH"),
		DB_2_FILE("Conversion Db to file CPYTOIMPF"),
		TB_2_FILE("Conversion Db table to streamfile "),
		FILE_2_DB("Conversion file to Db CPYFROMIMPF"),
		FILE_2_TB("Conversion streamfile to table"),
		DLT_FILE("Delete file DLTF"),
		CPY_2_DST("Copy to destination from QTEMP, CPYF"),
		FTP_RCV("Ftp Reveive"),
		FTP_SND("Ftp Send"),
		READ_MAIL("Read mail parameters"),
		SND_MAIL("Send mail"),
		SFTP_SND("Sftp Send"),
		SFTP_RCV("Sftp Reveive"),
		SP_FL_DSP("Spazio File DSP"),
		SP_FL_ACQ("Spazio File ACQ"),
		TRASM_KO("Error on Trasmission execution"),
		OPE_KO("Error on operation execution"),
		READ_NM_FL("Read name files"), 
		SBM_JOB("Submit Job"), 
		CHK_EMPTY("Check empty file"), 
		CK_IFS_EMP("Check empty IFS file"),
		DB_2_FIXED("Conversion Db to file CPYTOSTMF"),
		TB_2_FIXED("Conversion table to fixed streamfile"),
		INT_PGM("Interactive Program Call"),
		INT_CMD("Interactive Command Call"),
		DLY_CHK("Delay Integrity Check"),
		CHK_HASH("Check hash file"),
		SELECT_COL("Select custom columns order"),
		DROP_TBL("Drop temporary export table"),
		READ_SPOOL("Read Spool file for specific job"),
		CFT_FL_SND("EXCFTSEND");
		
		
		private String description;
		
		private OperationType(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
		
	}
	
	
	public enum MailReceiverType {
		RECEIVER("TO"),
		CARBON_COPY("CC"),
		BLIND_CARBON_COPY("BCC");
		
		private String code;
		
		private MailReceiverType(String code) {
			this.code = code;
		}

		public String getCode() {
			return code;
		}
	}
	
	public enum InteractiveType {
		PROGRAM("P"),
		COMMAND("C");
		
		private String code;
		
		private InteractiveType(String code) {
			this.code = code;
		}

		public String getCode() {
			return code;
		}
	}



}