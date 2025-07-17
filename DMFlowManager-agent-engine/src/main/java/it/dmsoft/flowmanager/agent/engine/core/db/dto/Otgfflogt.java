package it.dmsoft.flowmanager.agent.engine.core.db.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Otgfflogt {
	public static enum OtgfflogtCoulmn {

		OTGFLOGT_PROGR_LOG, OTGFLOGT_ID, OTGFLOGT_TS_INIZIO, OTGFLOGT_TS_FINE, OTGFLOGT_ESITO, OTGFLOGT_UTENTE,
		OTGFLOGT_PASSWORD, OTGFLOGT_COD_INTERFACCIA, OTGFLOGT_STATO, OTGFLOGT_TIP_FLUSSO, OTGFLOGT_DIREZIONE,
		OTGFLOGT_LIBRERIA, OTGFLOGT_FILE, OTGFLOGT_MEMBRO, OTGFLOGT_LIB_SOURCE, OTGFLOGT_FILE_SOURCE,
		OTGFLOGT_MEMBRO_SOURCE, OTGFLOGT_FOLDER, OTGFLOGT_FILE_NAME, OTGFLOGT_FORMATO, OTGFLOGT_DELIM_RECORD,
		OTGFLOGT_DELIM_CAMPO, OTGFLOGT_CODEPAGE, OTGFLOGT_MOD_ACQUISIZIONE, OTGFLOGT_PGM_CONTROLLO,
		OTGFLOGT_TIPOLOGIA_CONN, OTGFLOGT_HOST, OTGFLOGT_PORT, OTGFLOGT_REMOTE_FOLDER, OTGFLOGT_REMOTE_FILE_NAME,
		OTGFLOGT_UTENTE_SFTP, OTGFLOGT_INTEGRITY_CHECK, OTGFLOGT_FL_NAME_SEMAFORO, OTGFLOGT_NUM_TENTA_RICEZ,
		OTGFLOGT_INTERVALLO_RETRY, OTGFLOGT_RETENTION, OTGFLOGT_COMPRESSION, OTGFLOGT_BACKUP, OTGFLOGT_INVIA_MAIL,
		OTGFLOGT_LETTERA_OK, OTGFLOGT_LETTERA_KO, OTGFLOGT_LOG_FILE, OTGFLOGT_ERRORE_DESC, OTGFLOGT_JOB_AS,
		OTGFLOGT_USER_AS, OTGFLOGT_JOB_NBR_AS, OTGFLOGT_TIPO_TRASFERIMENTO, OTGFLOGT_BYPASS_QTEMP , OTGFLOGT_JAVA_HOME,
		OTGFLOGT_ESISTENZA_FILE, OTGFLOGT_LETTERA_FLUSSO, OTGFLOGT_CREA_VUOTO, OTGFLOGT_INTERNAZ, OTGFLOGT_SOST_VAL_NULL,
		OTGFLOGT_ELIM_NOM_COL, OTGFLOGT_FLAG_OK_VUOTO, OTGFLOGT_FTP_SECURE, OTGFLOGT_INTERACTIVE_TYPE, OTGFLOGT_INTERACTIVE_PROGRAM,
		OTGFLOGT_INTERACTIVE_RESULT, OTGFLOGT_INTERACTIVE_COMMAND, OTGFLOGT_DELAY_SEMAFORO, 
		OTGFLOGT_HASH_UNICO, OTGFLOGT_EXPORT_FLAG, OTGFLOGT_EXPORT_CODE, OTGFLOGT_FETCH_SIZE, OTGFLOGT_CHAR_EMPTY_SPACE

	}

	private BigDecimal otgflogt_Progr_Log;

	private String otgflogt_Id;

	private Timestamp otgflogt_Ts_Inizio;

	private Timestamp otgflogt_Ts_Fine;

	private String otgflogt_Esito;

	private String otgflogt_Utente;

	private String otgflogt_Password;

	private String otgflogt_Cod_Interfaccia;

	private String otgflogt_Stato;

	private String otgflogt_Tip_Flusso;

	private String otgflogt_Direzione;

	private String otgflogt_Libreria;

	private String otgflogt_File;

	private String otgflogt_Membro;

	private String otgflogt_Lib_Source;

	private String otgflogt_File_Source;

	private String otgflogt_Membro_Source;

	private String otgflogt_Folder;

	private String otgflogt_File_Name;

	private String otgflogt_Formato;

	private String otgflogt_Delim_Record;

	private String otgflogt_Delim_Campo;

	private BigDecimal otgflogt_Codepage;

	private String otgflogt_Mod_Acquiszione;

	private String otgflogt_Controllo;

	private String otgflogt_Tipologia_Conn;

	private String otgflogt_Host;

	private BigDecimal otgflogt_Port;

	private String otgflogt_Remote_Folder;

	private String otgflogt_Remote_File_Name;

	private String otgflogt_Utente_Sftp;

	private String otgflogt_Integrity_Check;

	private String otgflogt_Fl_Name_Semaforo;

	private BigDecimal otgflogt_Num_Tenta_Ricez;

	private BigDecimal otgflogt_Intervallo_Retry;

	private BigDecimal otgflogt_Retention;

	private String otgflogt_Compression;

	private String otgflogt_Backup;

	private String otgflogt_Invia_Mail;

	private String otgflogt_Lettera_Ok;

	private String otgflogt_Lettera_Ko;

	private String otgflogt_Log_File;

	private String otgflogt_Errore_Desc;

	private String otgflogt_Job_As;

	private String otgflogt_User_As;

	private String otgflogt_Job_Nbr_As;

	private String otgflogt_Tipo_Trasferimento;

	private String otgflogt_Bypass_Qtemp;

	private String otgflogt_Java_Home;
	
	//aggiunta campi mancanti
	
	private String otgflogt_Esistenza_File;
	
	private String otgflogt_Lettera_Flusso;
	
	private String otgflogt_Crea_Vuoto;
	
	private String otgflogt_Internaz;
	
	private String otgflogt_Sost_Val_Null;
	
	private String otgflogt_Elim_Nom_Col;
	
	private String otgflogt_Flag_Ok_Vuoto;
	
	private String otgflogt_Ftp_Secure;
	
	private String otgflogt_Interactive_Type;
	
	private String otgflogt_Interactive_Program;
	
	private String otgflogt_Interactive_Result;
	
	private String otgflogt_Interactive_Command;
	
	private BigDecimal otgflogt_Delay_Semaforo;	
	
	
	private String otgflogt_Hash_Unico;
	
	private String otgflogt_Export_Flag;
	
	private String otgflogt_Export_Code;
	
	private BigDecimal otgflogt_Fetch_Size;
	
	private String otgflogt_Char_Empty_Space;

	public BigDecimal getOtgflogt_Progr_Log() {
		return otgflogt_Progr_Log;
	}

	public void setOtgflogt_Progr_Log(BigDecimal otgflogt_Progr_Log) {
		this.otgflogt_Progr_Log = otgflogt_Progr_Log;
	}

	public String getOtgflogt_Id() {
		return otgflogt_Id;
	}

	public void setOtgflogt_Id(String otgflogt_Id) {
		this.otgflogt_Id = otgflogt_Id;
	}

	public Timestamp getOtgflogt_Ts_Inizio() {
		return otgflogt_Ts_Inizio;
	}

	public void setOtgflogt_Ts_Inizio(Timestamp otgflogt_Ts_Inizio) {
		this.otgflogt_Ts_Inizio = otgflogt_Ts_Inizio;
	}

	public Timestamp getOtgflogt_Ts_Fine() {
		return otgflogt_Ts_Fine;
	}

	public void setOtgflogt_Ts_Fine(Timestamp otgflogt_Ts_Fine) {
		this.otgflogt_Ts_Fine = otgflogt_Ts_Fine;
	}

	public String getOtgflogt_Esito() {
		return otgflogt_Esito;
	}

	public void setOtgflogt_Esito(String otgflogt_Esito) {
		this.otgflogt_Esito = otgflogt_Esito;
	}

	public String getOtgflogt_Utente() {
		return otgflogt_Utente;
	}

	public void setOtgflogt_Utente(String otgflogt_Utente) {
		this.otgflogt_Utente = otgflogt_Utente;
	}

	public String getOtgflogt_Password() {
		return otgflogt_Password;
	}

	public void setOtgflogt_Password(String otgflogt_Password) {
		this.otgflogt_Password = otgflogt_Password;
	}

	public String getOtgflogt_Cod_Interfaccia() {
		return otgflogt_Cod_Interfaccia;
	}

	public void setOtgflogt_Cod_Interfaccia(String otgflogt_Cod_Interfaccia) {
		this.otgflogt_Cod_Interfaccia = otgflogt_Cod_Interfaccia;
	}

	public String getOtgflogt_Stato() {
		return otgflogt_Stato;
	}

	public void setOtgflogt_Stato(String otgflogt_Stato) {
		this.otgflogt_Stato = otgflogt_Stato;
	}

	public String getOtgflogt_Tip_Flusso() {
		return otgflogt_Tip_Flusso;
	}

	public void setOtgflogt_Tip_Flusso(String otgflogt_Tip_Flusso) {
		this.otgflogt_Tip_Flusso = otgflogt_Tip_Flusso;
	}

	public String getOtgflogt_Direzione() {
		return otgflogt_Direzione;
	}

	public void setOtgflogt_Direzione(String otgflogt_Direzione) {
		this.otgflogt_Direzione = otgflogt_Direzione;
	}

	public String getOtgflogt_Libreria() {
		return otgflogt_Libreria;
	}

	public void setOtgflogt_Libreria(String otgflogt_Libreria) {
		this.otgflogt_Libreria = otgflogt_Libreria;
	}

	public String getOtgflogt_File() {
		return otgflogt_File;
	}

	public void setOtgflogt_File(String otgflogt_File) {
		this.otgflogt_File = otgflogt_File;
	}

	public String getOtgflogt_Membro() {
		return otgflogt_Membro;
	}

	public void setOtgflogt_Membro(String otgflogt_Membro) {
		this.otgflogt_Membro = otgflogt_Membro;
	}

	public String getOtgflogt_Lib_Source() {
		return otgflogt_Lib_Source;
	}

	public void setOtgflogt_Lib_Source(String otgflogt_Lib_Source) {
		this.otgflogt_Lib_Source = otgflogt_Lib_Source;
	}

	public String getOtgflogt_File_Source() {
		return otgflogt_File_Source;
	}

	public void setOtgflogt_File_Source(String otgflogt_File_Source) {
		this.otgflogt_File_Source = otgflogt_File_Source;
	}

	public String getOtgflogt_Membro_Source() {
		return otgflogt_Membro_Source;
	}

	public void setOtgflogt_Membro_Source(String otgflogt_Membro_Source) {
		this.otgflogt_Membro_Source = otgflogt_Membro_Source;
	}

	public String getOtgflogt_Folder() {
		return otgflogt_Folder;
	}

	public void setOtgflogt_Folder(String otgflogt_Folder) {
		this.otgflogt_Folder = otgflogt_Folder;
	}

	public String getOtgflogt_File_Name() {
		return otgflogt_File_Name;
	}

	public void setOtgflogt_File_Name(String otgflogt_File_Name) {
		this.otgflogt_File_Name = otgflogt_File_Name;
	}

	public String getOtgflogt_Formato() {
		return otgflogt_Formato;
	}

	public void setOtgflogt_Formato(String otgflogt_Formato) {
		this.otgflogt_Formato = otgflogt_Formato;
	}

	public String getOtgflogt_Delim_Record() {
		return otgflogt_Delim_Record;
	}

	public void setOtgflogt_Delim_Record(String otgflogt_Delim_Record) {
		this.otgflogt_Delim_Record = otgflogt_Delim_Record;
	}

	public String getOtgflogt_Delim_Campo() {
		return otgflogt_Delim_Campo;
	}

	public void setOtgflogt_Delim_Campo(String otgflogt_Delim_Campo) {
		this.otgflogt_Delim_Campo = otgflogt_Delim_Campo;
	}

	public BigDecimal getOtgflogt_Codepage() {
		return otgflogt_Codepage;
	}

	public void setOtgflogt_Codepage(BigDecimal otgflogt_Codepage) {
		this.otgflogt_Codepage = otgflogt_Codepage;
	}

	public String getOtgflogt_Mod_Acquiszione() {
		return otgflogt_Mod_Acquiszione;
	}

	public void setOtgflogt_Mod_Acquiszione(String otgflogt_Mod_Acquiszione) {
		this.otgflogt_Mod_Acquiszione = otgflogt_Mod_Acquiszione;
	}

	public String getOtgflogt_Controllo() {
		return otgflogt_Controllo;
	}

	public void setOtgflogt_Controllo(String otgflogt_Controllo) {
		this.otgflogt_Controllo = otgflogt_Controllo;
	}

	public String getOtgflogt_Tipologia_Conn() {
		return otgflogt_Tipologia_Conn;
	}

	public void setOtgflogt_Tipologia_Conn(String otgflogt_Tipologia_Conn) {
		this.otgflogt_Tipologia_Conn = otgflogt_Tipologia_Conn;
	}

	public String getOtgflogt_Host() {
		return otgflogt_Host;
	}

	public void setOtgflogt_Host(String otgflogt_Host) {
		this.otgflogt_Host = otgflogt_Host;
	}

	public BigDecimal getOtgflogt_Port() {
		return otgflogt_Port;
	}

	public void setOtgflogt_Port(BigDecimal otgflogt_Port) {
		this.otgflogt_Port = otgflogt_Port;
	}

	public String getOtgflogt_Remote_Folder() {
		return otgflogt_Remote_Folder;
	}

	public void setOtgflogt_Remote_Folder(String otgflogt_Remote_Folder) {
		this.otgflogt_Remote_Folder = otgflogt_Remote_Folder;
	}

	public String getOtgflogt_Remote_File_Name() {
		return otgflogt_Remote_File_Name;
	}

	public void setOtgflogt_Remote_File_Name(String otgflogt_Remote_File_Name) {
		this.otgflogt_Remote_File_Name = otgflogt_Remote_File_Name;
	}

	public String getOtgflogt_Utente_Sftp() {
		return otgflogt_Utente_Sftp;
	}

	public void setOtgflogt_Utente_Sftp(String otgflogt_Utente_Sftp) {
		this.otgflogt_Utente_Sftp = otgflogt_Utente_Sftp;
	}

	public String getOtgflogt_Integrity_Check() {
		return otgflogt_Integrity_Check;
	}

	public void setOtgflogt_Integrity_Check(String otgflogt_Integrity_Check) {
		this.otgflogt_Integrity_Check = otgflogt_Integrity_Check;
	}

	public String getOtgflogt_Fl_Name_Semaforo() {
		return otgflogt_Fl_Name_Semaforo;
	}

	public void setOtgflogt_Fl_Name_Semaforo(String otgflogt_Fl_Name_Semaforo) {
		this.otgflogt_Fl_Name_Semaforo = otgflogt_Fl_Name_Semaforo;
	}

	public BigDecimal getOtgflogt_Num_Tenta_Ricez() {
		return otgflogt_Num_Tenta_Ricez;
	}

	public void setOtgflogt_Num_Tenta_Ricez(BigDecimal otgflogt_Num_Tenta_Ricez) {
		this.otgflogt_Num_Tenta_Ricez = otgflogt_Num_Tenta_Ricez;
	}

	public BigDecimal getOtgflogt_Intervallo_Retry() {
		return otgflogt_Intervallo_Retry;
	}

	public void setOtgflogt_Intervallo_Retry(BigDecimal otgflogt_Intervallo_Retry) {
		this.otgflogt_Intervallo_Retry = otgflogt_Intervallo_Retry;
	}

	public BigDecimal getOtgflogt_Retention() {
		return otgflogt_Retention;
	}

	public void setOtgflogt_Retention(BigDecimal otgflogt_Retention) {
		this.otgflogt_Retention = otgflogt_Retention;
	}

	public String getOtgflogt_Compression() {
		return otgflogt_Compression;
	}

	public void setOtgflogt_Compression(String otgflogt_Compression) {
		this.otgflogt_Compression = otgflogt_Compression;
	}

	public String getOtgflogt_Backup() {
		return otgflogt_Backup;
	}

	public void setOtgflogt_Backup(String otgflogt_Backup) {
		this.otgflogt_Backup = otgflogt_Backup;
	}

	public String getOtgflogt_Invia_Mail() {
		return otgflogt_Invia_Mail;
	}

	public void setOtgflogt_Invia_Mail(String otgflogt_Invia_Mail) {
		this.otgflogt_Invia_Mail = otgflogt_Invia_Mail;
	}

	public String getOtgflogt_Lettera_Ok() {
		return otgflogt_Lettera_Ok;
	}

	public void setOtgflogt_Lettera_Ok(String otgflogt_Lettera_Ok) {
		this.otgflogt_Lettera_Ok = otgflogt_Lettera_Ok;
	}

	public String getOtgflogt_Lettera_Ko() {
		return otgflogt_Lettera_Ko;
	}

	public void setOtgflogt_Lettera_Ko(String otgflogt_Lettera_Ko) {
		this.otgflogt_Lettera_Ko = otgflogt_Lettera_Ko;
	}

	public String getOtgflogt_Log_File() {
		return otgflogt_Log_File;
	}

	public void setOtgflogt_Log_File(String otgflogt_Log_File) {
		this.otgflogt_Log_File = otgflogt_Log_File;
	}

	public String getOtgflogt_Errore_Desc() {
		return otgflogt_Errore_Desc;
	}

	public void setOtgflogt_Errore_Desc(String otgflogt_Errore_Desc) {
		this.otgflogt_Errore_Desc = otgflogt_Errore_Desc;
	}

	public String getOtgflogt_Job_As() {
		return otgflogt_Job_As;
	}

	public void setOtgflogt_Job_As(String otgflogt_Job_As) {
		this.otgflogt_Job_As = otgflogt_Job_As;
	}

	public String getOtgflogt_User_As() {
		return otgflogt_User_As;
	}

	public void setOtgflogt_User_As(String otgflogt_User_As) {
		this.otgflogt_User_As = otgflogt_User_As;
	}

	public String getOtgflogt_Job_Nbr_As() {
		return otgflogt_Job_Nbr_As;
	}

	public void setOtgflogt_Job_Nbr_As(String otgflogt_Job_Nbr_As) {
		this.otgflogt_Job_Nbr_As = otgflogt_Job_Nbr_As;
	}

	public String getOtgflogt_Tipo_Trasferimento() {
		return otgflogt_Tipo_Trasferimento;
	}

	public void setOtgflogt_Tipo_Trasferimento(String otgflogt_Tipo_Trasferimento) {
		this.otgflogt_Tipo_Trasferimento = otgflogt_Tipo_Trasferimento;
	}

	public String getOtgflogt_Bypass_Qtemp() {
		return otgflogt_Bypass_Qtemp;
	}

	public void setOtgflogt_Bypass_Qtemp(String otgflogt_Bypass_Qtemp) {
		this.otgflogt_Bypass_Qtemp = otgflogt_Bypass_Qtemp;
	}

	public String getOtgflogt_Java_Home() {
		return otgflogt_Java_Home;
	}

	public void setOtgflogt_Java_Home(String otgflogt_Java_Home) {
		this.otgflogt_Java_Home = otgflogt_Java_Home;
	}

	
	
	public String getOtgflogt_Esistenza_File() {
		return otgflogt_Esistenza_File;
	}

	public void setOtgflogt_Esistenza_File(String otgflogt_Esistenza_File) {
		this.otgflogt_Esistenza_File = otgflogt_Esistenza_File;
	}

	public String getOtgflogt_Lettera_Flusso() {
		return otgflogt_Lettera_Flusso;
	}

	public void setOtgflogt_Lettera_Flusso(String otgflogt_Lettera_Flusso) {
		this.otgflogt_Lettera_Flusso = otgflogt_Lettera_Flusso;
	}

	public String getOtgflogt_Crea_Vuoto() {
		return otgflogt_Crea_Vuoto;
	}

	public void setOtgflogt_Crea_Vuoto(String otgflogt_Crea_Vuoto) {
		this.otgflogt_Crea_Vuoto = otgflogt_Crea_Vuoto;
	}

	public String getOtgflogt_Internaz() {
		return otgflogt_Internaz;
	}

	public void setOtgflogt_Internaz(String otgflogt_Internaz) {
		this.otgflogt_Internaz = otgflogt_Internaz;
	}

	public String getOtgflogt_Sost_Val_Null() {
		return otgflogt_Sost_Val_Null;
	}

	public void setOtgflogt_Sost_Val_Null(String otgflogt_Sost_Val_Null) {
		this.otgflogt_Sost_Val_Null = otgflogt_Sost_Val_Null;
	}

	public String getOtgflogt_Elim_Nom_Col() {
		return otgflogt_Elim_Nom_Col;
	}

	public void setOtgflogt_Elim_Nom_Col(String otgflogt_Elim_Nom_Col) {
		this.otgflogt_Elim_Nom_Col = otgflogt_Elim_Nom_Col;
	}

	public String getOtgflogt_Flag_Ok_Vuoto() {
		return otgflogt_Flag_Ok_Vuoto;
	}

	public void setOtgflogt_Flag_Ok_Vuoto(String otgflogt_Flag_Ok_Vuoto) {
		this.otgflogt_Flag_Ok_Vuoto = otgflogt_Flag_Ok_Vuoto;
	}

	public String getOtgflogt_Ftp_Secure() {
		return otgflogt_Ftp_Secure;
	}

	public void setOtgflogt_Ftp_Secure(String otgflogt_Ftp_Secure) {
		this.otgflogt_Ftp_Secure = otgflogt_Ftp_Secure;
	}

	public String getOtgflogt_Interactive_Type() {
		return otgflogt_Interactive_Type;
	}

	public void setOtgflogt_Interactive_Type(String otgflogt_Interactive_Type) {
		this.otgflogt_Interactive_Type = otgflogt_Interactive_Type;
	}

	public String getOtgflogt_Interactive_Program() {
		return otgflogt_Interactive_Program;
	}

	public void setOtgflogt_Interactive_Program(String otgflogt_Interactive_Program) {
		this.otgflogt_Interactive_Program = otgflogt_Interactive_Program;
	}

	public String getOtgflogt_Interactive_Result() {
		return otgflogt_Interactive_Result;
	}

	public void setOtgflogt_Interactive_Result(String otgflogt_Interactive_Result) {
		this.otgflogt_Interactive_Result = otgflogt_Interactive_Result;
	}

	public String getOtgflogt_Interactive_Command() {
		return otgflogt_Interactive_Command;
	}

	public void setOtgflogt_Interactive_Command(String otgflogt_Interactive_Command) {
		this.otgflogt_Interactive_Command = otgflogt_Interactive_Command;
	}

	public BigDecimal getOtgflogt_Delay_Semaforo() {
		return otgflogt_Delay_Semaforo;
	}

	public void setOtgflogt_Delay_Semaforo(BigDecimal otgflogt_Delay_Semaforo) {
		this.otgflogt_Delay_Semaforo = otgflogt_Delay_Semaforo;
	}

	public String getOtgflogt_Hash_Unico() {
		return otgflogt_Hash_Unico;
	}

	public void setOtgflogt_Hash_Unico(String otgflogt_Hash_Unico) {
		this.otgflogt_Hash_Unico = otgflogt_Hash_Unico;
	}	

	public String getOtgflogt_Export_Flag() {
		return otgflogt_Export_Flag;
	}

	public void setOtgflogt_Export_Flag(String otgflogt_Export_Flag) {
		this.otgflogt_Export_Flag = otgflogt_Export_Flag;
	}

	public String getOtgflogt_Export_Code() {
		return otgflogt_Export_Code;
	}

	public void setOtgflogt_Export_Code(String otgflogt_Export_Code) {
		this.otgflogt_Export_Code = otgflogt_Export_Code;
	}

	public BigDecimal getOtgflogt_Fetch_Size() {
		return otgflogt_Fetch_Size;
	}

	public void setOtgflogt_Fetch_Size(BigDecimal otgflogt_Fetch_Size) {
		this.otgflogt_Fetch_Size = otgflogt_Fetch_Size;
	}



	public String getOtgflogt_Char_Empty_Space() {
		return otgflogt_Char_Empty_Space;
	}

	public void setOtgflogt_Char_Empty_Space(String otgflogt_Char_Empty_Space) {
		this.otgflogt_Char_Empty_Space = otgflogt_Char_Empty_Space;
	}

	@Override
	public String toString() {
		return "Otgfflogt [otgflogt_Progr_Log=" + otgflogt_Progr_Log + ", otgflogt_Id=" + otgflogt_Id
				+ ", otgflogt_Ts_Inizio=" + otgflogt_Ts_Inizio + ", otgflogt_Ts_Fine=" + otgflogt_Ts_Fine
				+ ", otgflogt_Esito=" + otgflogt_Esito + ", otgflogt_Utente=" + otgflogt_Utente + ", otgflogt_Password="
				+ otgflogt_Password + ", otgflogt_Cod_Interfaccia=" + otgflogt_Cod_Interfaccia + ", otgflogt_Stato="
				+ otgflogt_Stato + ", otgflogt_Tip_Flusso=" + otgflogt_Tip_Flusso + ", otgflogt_Direzione="
				+ otgflogt_Direzione + ", otgflogt_Libreria=" + otgflogt_Libreria + ", otgflogt_File=" + otgflogt_File
				+ ", otgflogt_Membro=" + otgflogt_Membro + ", otgflogt_Lib_Source=" + otgflogt_Lib_Source
				+ ", otgflogt_File_Source=" + otgflogt_File_Source + ", otgflogt_Membro_Source="
				+ otgflogt_Membro_Source + ", otgflogt_Folder=" + otgflogt_Folder + ", otgflogt_File_Name="
				+ otgflogt_File_Name + ", otgflogt_Formato=" + otgflogt_Formato + ", otgflogt_Delim_Record="
				+ otgflogt_Delim_Record + ", otgflogt_Delim_Campo=" + otgflogt_Delim_Campo + ", otgflogt_Codepage="
				+ otgflogt_Codepage + ", otgflogt_Mod_Acquiszione=" + otgflogt_Mod_Acquiszione + ", otgflogt_Controllo="
				+ otgflogt_Controllo + ", otgflogt_Tipologia_Conn=" + otgflogt_Tipologia_Conn + ", otgflogt_Host="
				+ otgflogt_Host + ", otgflogt_Port=" + otgflogt_Port + ", otgflogt_Remote_Folder="
				+ otgflogt_Remote_Folder + ", otgflogt_Remote_File_Name=" + otgflogt_Remote_File_Name
				+ ", otgflogt_Utente_Sftp=" + otgflogt_Utente_Sftp + ", otgflogt_Integrity_Check="
				+ otgflogt_Integrity_Check + ", otgflogt_Fl_Name_Semaforo=" + otgflogt_Fl_Name_Semaforo
				+ ", otgflogt_Num_Tenta_Ricez=" + otgflogt_Num_Tenta_Ricez + ", otgflogt_Intervallo_Retry="
				+ otgflogt_Intervallo_Retry + ", otgflogt_Retention=" + otgflogt_Retention + ", otgflogt_Compression="
				+ otgflogt_Compression + ", otgflogt_Backup=" + otgflogt_Backup + ", otgflogt_Invia_Mail="
				+ otgflogt_Invia_Mail + ", otgflogt_Lettera_Ok=" + otgflogt_Lettera_Ok + ", otgflogt_Lettera_Ko="
				+ otgflogt_Lettera_Ko + ", otgflogt_Log_File=" + otgflogt_Log_File + ", otgflogt_Errore_Desc="
				+ otgflogt_Errore_Desc + ", otgflogt_Job_As=" + otgflogt_Job_As + ", otgflogt_User_As="
				+ otgflogt_User_As + ", otgflogt_Job_Nbr_As=" + otgflogt_Job_Nbr_As + ", otgflogt_Tipo_Trasferimento="
				+ otgflogt_Tipo_Trasferimento + ", otgflogt_Bypass_Qtemp=" + otgflogt_Bypass_Qtemp
				+ ", otgflogt_Java_Home=" + otgflogt_Java_Home + ", otgflogt_Esistenza_File=" + otgflogt_Esistenza_File
				+ ", otgflogt_Lettera_Flusso=" + otgflogt_Lettera_Flusso + ", otgflogt_Crea_Vuoto="
				+ otgflogt_Crea_Vuoto + ", otgflogt_Internaz=" + otgflogt_Internaz + ", otgflogt_Sost_Val_Null="
				+ otgflogt_Sost_Val_Null + ", otgflogt_Elim_Nom_Col=" + otgflogt_Elim_Nom_Col
				+ ", otgflogt_Flag_Ok_Vuoto=" + otgflogt_Flag_Ok_Vuoto + ", otgflogt_Ftp_Secure=" + otgflogt_Ftp_Secure
				+ ", otgflogt_Interactive_Type=" + otgflogt_Interactive_Type + ", otgflogt_Interactive_Program="
				+ otgflogt_Interactive_Program + ", otgflogt_Interactive_Result=" + otgflogt_Interactive_Result
				+ ", otgflogt_Interactive_Command=" + otgflogt_Interactive_Command + ", otgflogt_Delay_Semaforo="
				+ otgflogt_Delay_Semaforo + ", otgflogt_Hash_Unico=" + otgflogt_Hash_Unico + ", otgflogt_Export_Flag="
				+ otgflogt_Export_Flag + ", otgflogt_Export_Code=" + otgflogt_Export_Code + ", otgflogt_Fetch_Size="
				+ otgflogt_Fetch_Size + ", otgflogt_Char_Empty_Space=" + otgflogt_Char_Empty_Space + "]";
	}


	

}
