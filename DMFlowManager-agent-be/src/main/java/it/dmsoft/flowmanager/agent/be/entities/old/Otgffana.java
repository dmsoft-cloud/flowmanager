package it.dmsoft.flowmanager.agent.be.entities.old;

import java.math.BigDecimal;

public class Otgffana {
	public static enum OtgffanaCoulmn {

		FANA_ID, 
		FANA_DESC, 
		FANA_GRUPPO, 
		FANA_COD_INTERFACCIA, 
		FANA_STATO, 
		FANA_TIP_FLUSSO, 
		FANA_DIREZIONE,
		FANA_LIBRERIA,
		FANA_FILE, 
		FANA_MEMBRO, 
		FANA_LIB_SOURCE, 
		FANA_FILE_SOURCE,
		FANA_MEMBRO_SOURCE,
		FANA_FOLDER,
		FANA_FILE_NAME,
		FANA_FORMATO,
		FANA_DELIM_RECORD,
		FANA_DELIM_STRINGA,
		FANA_RIMOZ_SPAZI,
		FANA_DELIM_CAMPO,
		FANA_RIEMP_CAMPO,
		FANA_CODEPAGE,
		FANA_MOD_ACQUISIZIONE,
		FANA_FROM_CCSID,
		FANA_PGM_CONTROLLO, 
		FANA_TIPOLOGIA_CONN,
		FANA_HOST, 
		FANA_PORT, 
		FANA_REMOTE_FOLDER, 
		FANA_REMOTE_FILE_NAME,
		FANA_UTENTE, 
		FANA_PASSWORD,
		FANA_UTENTE_SFTP, 
		FANA_INTEGRITY_CHECK,
		FANA_FL_NAME_SEMAFORO, 
		FANA_NUM_TENTA_RICEZ,
		FANA_INTERVALLO_RETRY, 
		FANA_RETENTION, 
		FANA_COMPRESSION, 
		FANA_DE_COMPRESSION, 
		FANA_BACKUP, 
		FANA_INVIA_MAIL,
		FANA_LETTERA_OK, 
		FANA_LETTERA_KO,
		FANA_PROG_PERSON,
		FANA_KNOWN_HT_FL,
		FANA_KEY_FL,
		FANA_MODALITA_PASSIVA,
		FANA_JOB_DESC,
		FANA_LIB_JOB_DESC,
		FANA_CANCELLA_FIL,
		FANA_RISOTTOMET,
		FANA_LUNGHEZZA_FL_FLAT,
		FANA_TIPO_TRASFERIMENTO,
		FANA_BYPASS_QTEMP,
		FANA_ESISTENZA_FILE,
		FANA_LETTERA_FLUSSO,
		FANA_AGG_NOMI_COL,
		FANA_CREA_VUOTO,
		FANA_INTERNAZ,
		FANA_SOST_VAL_NULL,
		FANA_ELIM_NOM_COL,
		FANA_FLAG_OK_VUOTO,
		FANA_FTP_SECURE,
		FANA_INTERACTIVE_TYPE,
		FANA_INTERACTIVE_PROGRAM,
		FANA_INTERACTIVE_RESULT,
		FANA_INTERACTIVE_COMMAND,
		FANA_DELAY_SEMAFORO,
		FANA_HASH_UNICO,
		FANA_EXPORT_FLAG,
		FANA_EXPORT_CODE,
		FANA_FETCH_SIZE,
		FANA_CHAR_EMPTY_SPACE
	}

	
	private String fana_Id;
	private String fana_Desc;
	private String fana_Gruppo;
	private String fana_Cod_Interfaccia;
	private String fana_Stato;
	private String fana_Tip_Flusso;
	private String fana_Direzione;
	private String fana_Libreria;
	private String fana_File;
	private String fana_Membro;
	private String fana_Lib_Source;
	private String fana_File_Source;
	private String fana_Membro_Source;
	private String fana_Folder;
	private String fana_File_Name;
	private String fana_Formato;
	private String fana_Delim_Record;
	private String fana_Delim_Stringa;
	private String fana_Rimoz_Spazi;
	private String fana_Delim_Campo;
	private String fana_Riemp_Campo;
	private BigDecimal fana_Codepage;
	private String fana_Mod_Acquisizione;
	private BigDecimal fana_From_Ccsid;
	private String fana_Pgm_Controllo;
	private String fana_Tipologia_Conn;
	private String fana_Host;
	private BigDecimal fana_Port;
	private String fana_Remote_Folder;
	private String fana_Remote_File_Name;
	private String fana_Utente;
	private String fana_Password;
	private String fana_Utente_Sftp;
	private String fana_Intergiry_Check;
	private String fana_Fl_Name_Semaforo;
	private BigDecimal fana_Num_Tenta_Ricez;
	private BigDecimal fana_Intervallo_Retry;
	private BigDecimal fana_Retention;
	private String fana_Compression;
	private String fana_De_Compression;
	private String fana_Backup;
	private String fana_Invia_Mail;
	private String fana_Lettera_Ok;
	private String fana_Lettera_Ko;
	private String fana_Prog_Person;
	private String fana_Known_Ht_Fl;
	private String fana_Key_Fl;
	private String fana_Modalita_Passiva;
	private String fana_Job_Desc;
	private String fana_Lib_Job_Desc;
	private String fana_Cancella_File;
	private String fana_Risottomettibile;
	private BigDecimal fana_Lunghezza_Fl_Flat;
	private String fana_Tipo_Trasferimento;
	private String fana_Bypass_Qtemp;
	private String fana_Esistenza_File;
	private String fana_Lettera_Flusso;
	private String fana_Agg_Nomi_Col;
	private String fana_Crea_Vuoto;
	private String fana_Internaz;
	private String fana_Sost_Val_Null;
	private String fana_Elim_Nom_Col;
	private String fana_Flag_Ok_Vuoto;
	private String fana_Ftp_Secure;
	private String fana_Interactive_Type;
	private String fana_Interactive_Program;
	private String fana_Interactive_Result;
	private String fana_Interactive_Command;
	private BigDecimal fana_Delay_Semaforo;
	private String fana_Hash_Unico;
	private String fana_Export_Flag;
	private String fana_Export_Code;
	private BigDecimal fana_Fetch_Size;
	private String fana_char_empty_space;
	
	
	public String getFana_Id() {
		return fana_Id;
	}
	public void setFana_Id(String fana_Id) {
		this.fana_Id = fana_Id;
	}
	public String getFana_Desc() {
		return fana_Desc;
	}
	public void setFana_Desc(String fana_Desc) {
		this.fana_Desc = fana_Desc;
	}
	public String getFana_Gruppo() {
		return fana_Gruppo;
	}
	public void setFana_Gruppo(String fana_Gruppo) {
		this.fana_Gruppo = fana_Gruppo;
	}
	public String getFana_Cod_Interfaccia() {
		return fana_Cod_Interfaccia;
	}
	public void setFana_Cod_Interfaccia(String fana_Cod_Interfaccia) {
		this.fana_Cod_Interfaccia = fana_Cod_Interfaccia;
	}
	public String getFana_Stato() {
		return fana_Stato;
	}
	public void setFana_Stato(String fana_Stato) {
		this.fana_Stato = fana_Stato;
	}
	public String getFana_Tip_Flusso() {
		return fana_Tip_Flusso;
	}
	public void setFana_Tip_Flusso(String fana_Tip_Flusso) {
		this.fana_Tip_Flusso = fana_Tip_Flusso;
	}
	public String getFana_Direzione() {
		return fana_Direzione;
	}
	public void setFana_Direzione(String fana_Direzione) {
		this.fana_Direzione = fana_Direzione;
	}
	public String getFana_Libreria() {
		return fana_Libreria;
	}
	public void setFana_Libreria(String fana_Libreria) {
		this.fana_Libreria = fana_Libreria;
	}
	public String getFana_File() {
		return fana_File;
	}
	public void setFana_File(String fana_File) {
		this.fana_File = fana_File;
	}
	public String getFana_Membro() {
		return fana_Membro;
	}
	public void setFana_Membro(String fana_Membro) {
		this.fana_Membro = fana_Membro;
	}
	public String getFana_Lib_Source() {
		return fana_Lib_Source;
	}
	public void setFana_Lib_Source(String fana_Lib_Source) {
		this.fana_Lib_Source = fana_Lib_Source;
	}
	public String getFana_File_Source() {
		return fana_File_Source;
	}
	public void setFana_File_Source(String fana_File_Source) {
		this.fana_File_Source = fana_File_Source;
	}
	public String getFana_Membro_Source() {
		return fana_Membro_Source;
	}
	public void setFana_Membro_Source(String fana_Membro_Source) {
		this.fana_Membro_Source = fana_Membro_Source;
	}
	public String getFana_Folder() {
		return fana_Folder;
	}
	public void setFana_Folder(String fana_Folder) {
		this.fana_Folder = fana_Folder;
	}
	public String getFana_File_Name() {
		return fana_File_Name;
	}
	public void setFana_File_Name(String fana_File_Name) {
		this.fana_File_Name = fana_File_Name;
	}
	public String getFana_Formato() {
		return fana_Formato;
	}
	public void setFana_Formato(String fana_Formato) {
		this.fana_Formato = fana_Formato;
	}
	public String getFana_Delim_Record() {
		return fana_Delim_Record;
	}
	public void setFana_Delim_Record(String fana_Delim_Record) {
		this.fana_Delim_Record = fana_Delim_Record;
	}
	public String getFana_Delim_Stringa() {
		return fana_Delim_Stringa;
	}
	public void setFana_Delim_Stringa(String fana_Delim_Stringa) {
		this.fana_Delim_Stringa = fana_Delim_Stringa;
	}
	public String getFana_Rimoz_Spazi() {
		return fana_Rimoz_Spazi;
	}
	public void setFana_Rimoz_Spazi(String fana_Rimoz_Spazi) {
		this.fana_Rimoz_Spazi = fana_Rimoz_Spazi;
	}
	public String getFana_Delim_Campo() {
		return fana_Delim_Campo;
	}
	public void setFana_Delim_Campo(String fana_Delim_Campo) {
		this.fana_Delim_Campo = fana_Delim_Campo;
	}
	public String getFana_Riemp_Campo() {
		return fana_Riemp_Campo;
	}
	public void setFana_Riemp_Campo(String fana_Riemp_Campo) {
		this.fana_Riemp_Campo = fana_Riemp_Campo;
	}
	public BigDecimal getFana_Codepage() {
		return fana_Codepage;
	}
	public void setFana_Codepage(BigDecimal fana_Codepage) {
		this.fana_Codepage = fana_Codepage;
	}
	public String getFana_Mod_Acquisizione() {
		return fana_Mod_Acquisizione;
	}
	public void setFana_Mod_Acquisizione(String fana_Mod_Acquisizione) {
		this.fana_Mod_Acquisizione = fana_Mod_Acquisizione;
	}
	public BigDecimal getFana_From_Ccsid() {
		return fana_From_Ccsid;
	}
	public void setFana_From_Ccsid(BigDecimal fana_From_Ccsid) {
		this.fana_From_Ccsid = fana_From_Ccsid;
	}
	public String getFana_Pgm_Controllo() {
		return fana_Pgm_Controllo;
	}
	public void setFana_Pgm_Controllo(String fana_Pgm_Controllo) {
		this.fana_Pgm_Controllo = fana_Pgm_Controllo;
	}
	public String getFana_Tipologia_Conn() {
		return fana_Tipologia_Conn;
	}
	public void setFana_Tipologia_Conn(String fana_Tipologia_Conn) {
		this.fana_Tipologia_Conn = fana_Tipologia_Conn;
	}
	public String getFana_Host() {
		return fana_Host;
	}
	public void setFana_Host(String fana_Host) {
		this.fana_Host = fana_Host;
	}
	public BigDecimal getFana_Port() {
		return fana_Port;
	}
	public void setFana_Port(BigDecimal fana_Port) {
		this.fana_Port = fana_Port;
	}
	public String getFana_Remote_Folder() {
		return fana_Remote_Folder;
	}
	public void setFana_Remote_Folder(String fana_Remote_Folder) {
		this.fana_Remote_Folder = fana_Remote_Folder;
	}
	public String getFana_Remote_File_Name() {
		return fana_Remote_File_Name;
	}
	public void setFana_Remote_File_Name(String fana_Remote_File_Name) {
		this.fana_Remote_File_Name = fana_Remote_File_Name;
	}
	public String getFana_Utente() {
		return fana_Utente;
	}
	public void setFana_Utente(String fana_Utente) {
		this.fana_Utente = fana_Utente;
	}
	public String getFana_Password() {
		return fana_Password;
	}
	public void setFana_Password(String fana_Password) {
		this.fana_Password = fana_Password;
	}
	public String getFana_Utente_Sftp() {
		return fana_Utente_Sftp;
	}
	public void setFana_Utente_Sftp(String fana_Utente_Sftp) {
		this.fana_Utente_Sftp = fana_Utente_Sftp;
	}
	public String getFana_Intergiry_Check() {
		return fana_Intergiry_Check;
	}
	public void setFana_Intergiry_Check(String fana_Intergiry_Check) {
		this.fana_Intergiry_Check = fana_Intergiry_Check;
	}
	public String getFana_Fl_Name_Semaforo() {
		return fana_Fl_Name_Semaforo;
	}
	public void setFana_Fl_Name_Semaforo(String fana_Fl_Name_Semaforo) {
		this.fana_Fl_Name_Semaforo = fana_Fl_Name_Semaforo;
	}
	public BigDecimal getFana_Num_Tenta_Ricez() {
		return fana_Num_Tenta_Ricez;
	}
	public void setFana_Num_Tenta_Ricez(BigDecimal fana_Num_Tenta_Ricez) {
		this.fana_Num_Tenta_Ricez = fana_Num_Tenta_Ricez;
	}
	public BigDecimal getFana_Intervallo_Retry() {
		return fana_Intervallo_Retry;
	}
	public void setFana_Intervallo_Retry(BigDecimal fana_Intervallo_Retry) {
		this.fana_Intervallo_Retry = fana_Intervallo_Retry;
	}
	public BigDecimal getFana_Retention() {
		return fana_Retention;
	}
	public void setFana_Retention(BigDecimal fana_Retention) {
		this.fana_Retention = fana_Retention;
	}
	public String getFana_Compression() {
		return fana_Compression;
	}
	public void setFana_Compression(String fana_Compression) {
		this.fana_Compression = fana_Compression;
	}
	public String getFana_De_Compression() {
		return fana_De_Compression;
	}
	public void setFana_De_Compression(String fana_De_Compression) {
		this.fana_De_Compression = fana_De_Compression;
	}
	public String getFana_Backup() {
		return fana_Backup;
	}
	public void setFana_Backup(String fana_Backup) {
		this.fana_Backup = fana_Backup;
	}
	public String getFana_Invia_Mail() {
		return fana_Invia_Mail;
	}
	public void setFana_Invia_Mail(String fana_Invia_Mail) {
		this.fana_Invia_Mail = fana_Invia_Mail;
	}
	public String getFana_Lettera_Ok() {
		return fana_Lettera_Ok;
	}
	public void setFana_Lettera_Ok(String fana_Lettera_Ok) {
		this.fana_Lettera_Ok = fana_Lettera_Ok;
	}
	public String getFana_Lettera_Ko() {
		return fana_Lettera_Ko;
	}
	public void setFana_Lettera_Ko(String fana_Lettera_Ko) {
		this.fana_Lettera_Ko = fana_Lettera_Ko;
	}
	public String getFana_Prog_Person() {
		return fana_Prog_Person;
	}
	public void setFana_Prog_Person(String fana_Prog_Person) {
		this.fana_Prog_Person = fana_Prog_Person;
	}
	public String getFana_Known_Ht_Fl() {
		return fana_Known_Ht_Fl;
	}
	public void setFana_Known_Ht_Fl(String fana_Known_Ht_Fl) {
		this.fana_Known_Ht_Fl = fana_Known_Ht_Fl;
	}
	public String getFana_Key_Fl() {
		return fana_Key_Fl;
	}
	public void setFana_Key_Fl(String fana_Key_Fl) {
		this.fana_Key_Fl = fana_Key_Fl;
	}
	public String getFana_Modalita_Passiva() {
		return fana_Modalita_Passiva;
	}
	public void setFana_Modalita_Passiva(String fana_Modalita_Passiva) {
		this.fana_Modalita_Passiva = fana_Modalita_Passiva;
	}
	public String getFana_Job_Desc() {
		return fana_Job_Desc;
	}
	public void setFana_Job_Desc(String fana_Job_Desc) {
		this.fana_Job_Desc = fana_Job_Desc;
	}
	public String getFana_Lib_Job_Desc() {
		return fana_Lib_Job_Desc;
	}
	public void setFana_Lib_Job_Desc(String fana_Lib_Job_Desc) {
		this.fana_Lib_Job_Desc = fana_Lib_Job_Desc;
	}
	public String getFana_Cancella_File() {
		return fana_Cancella_File;
	}
	public void setFana_Cancella_File(String fana_Cancella_File) {
		this.fana_Cancella_File = fana_Cancella_File;
	}
	public String getFana_Risottomettibile() {
		return fana_Risottomettibile;
	}
	public void setFana_Risottomettibile(String fana_Risottomettibile) {
		this.fana_Risottomettibile = fana_Risottomettibile;
	}
	public BigDecimal getFana_Lunghezza_Fl_Flat() {
		return fana_Lunghezza_Fl_Flat;
	}
	public void setFana_Lunghezza_Fl_Flat(BigDecimal fana_Lunghezza_Fl_Flat) {
		this.fana_Lunghezza_Fl_Flat = fana_Lunghezza_Fl_Flat;
	}
	public String getFana_Tipo_Trasferimento() {
		return fana_Tipo_Trasferimento;
	}
	public void setFana_Tipo_Trasferimento(String fana_tipo_trasferimento) {
		this.fana_Tipo_Trasferimento = fana_tipo_trasferimento;
	}
	public String getFana_Bypass_Qtemp() {
		return fana_Bypass_Qtemp;
	}
	public void setFana_Bypass_Qtemp(String fana_Bypass_Qtemp) {
		this.fana_Bypass_Qtemp = fana_Bypass_Qtemp;
	}
	public String getFana_Esistenza_File() {
		return fana_Esistenza_File;
	}
	public void setFana_Esistenza_File(String fana_Esistenza_File) {
		this.fana_Esistenza_File = fana_Esistenza_File;
	}
	public String getFana_Lettera_Flusso() {
		return fana_Lettera_Flusso;
	}
	public void setFana_Lettera_Flusso(String fana_lettera_flusso) {
		this.fana_Lettera_Flusso = fana_lettera_flusso;
	}
	public String getFana_Agg_Nomi_Col() {
		return fana_Agg_Nomi_Col;
	}
	public void setFana_Agg_Nomi_Col(String fana_Agg_Nomi_Col) {
		this.fana_Agg_Nomi_Col = fana_Agg_Nomi_Col;
	}
	public String getFana_Crea_Vuoto() {
		return fana_Crea_Vuoto;
	}
	public void setFana_Crea_Vuoto(String fana_Crea_Vuoto) {
		this.fana_Crea_Vuoto = fana_Crea_Vuoto;
	}
	public String getFana_Internaz() {
		return fana_Internaz;
	}
	public void setFana_Internaz(String fana_Internaz) {
		this.fana_Internaz = fana_Internaz;
	}
	
	public String getFana_Sost_Val_Null() {
		return fana_Sost_Val_Null;
	}
	public void setFana_Sost_Val_Null(String fana_Sost_Val_Null) {
		this.fana_Sost_Val_Null = fana_Sost_Val_Null;
	}
	
	public String getFana_Elim_Nom_Col() {
		return fana_Elim_Nom_Col;
	}
	public void setFana_Elim_Nom_Col(String fana_Elim_Nom_Col) {
		this.fana_Elim_Nom_Col = fana_Elim_Nom_Col;
	}
	
	public String getFana_Flag_Ok_Vuoto() {
		return fana_Flag_Ok_Vuoto;
	}
	public void setFana_Flag_Ok_Vuoto(String fana_Flag_Ok_Vuoto) {
		this.fana_Flag_Ok_Vuoto = fana_Flag_Ok_Vuoto;
	}
	public String getFana_Ftp_Secure() {
		return fana_Ftp_Secure;
	}
	public void setFana_Ftp_Secure(String fana_Ftp_Secure) {
		this.fana_Ftp_Secure = fana_Ftp_Secure;
	}
	
	public String getFana_Interactive_Type() {
		return fana_Interactive_Type;
	}
	public void setFana_Interactive_Type(String fana_Interactive_Type) {
		this.fana_Interactive_Type = fana_Interactive_Type;
	}
	public String getFana_Interactive_Program() {
		return fana_Interactive_Program;
	}
	public void setFana_Interactive_Program(String fana_Interactive_Program) {
		this.fana_Interactive_Program = fana_Interactive_Program;
	}
	public String getFana_Interactive_Result() {
		return fana_Interactive_Result;
	}
	public void setFana_Interactive_Result(String fana_Interactive_Result) {
		this.fana_Interactive_Result = fana_Interactive_Result;
	}
	public String getFana_Interactive_Command() {
		return fana_Interactive_Command;
	}
	public void setFana_Interactive_Command(String fana_Interactive_Command) {
		this.fana_Interactive_Command = fana_Interactive_Command;
	}
	public BigDecimal getFana_Delay_Semaforo() {
		return fana_Delay_Semaforo;
	}
	public void setFana_Delay_Semaforo(BigDecimal fana_Delay_Semaforo) {
		this.fana_Delay_Semaforo = fana_Delay_Semaforo;
	}
	public String getFana_Hash_Unico() {
		return fana_Hash_Unico;
	}
	public void setFana_Hash_Unico(String fana_Hash_Unico) {
		this.fana_Hash_Unico = fana_Hash_Unico;
	}
	public String getFana_Export_Flag() {
		return fana_Export_Flag;
	}
	public void setFana_Export_Flag(String fana_Export_Flag) {
		this.fana_Export_Flag = fana_Export_Flag;
	}
	public String getFana_Export_Code() {
		return fana_Export_Code;
	}
	public void setFana_Export_Code(String fana_Export_Code) {
		this.fana_Export_Code = fana_Export_Code;
	}
	

	public BigDecimal getFana_Fetch_Size() {
		return fana_Fetch_Size;
	}
	public void setFana_Fetch_Size(BigDecimal fana_Fetch_Size) {
		this.fana_Fetch_Size = fana_Fetch_Size;
	}
	
	public String getFana_char_empty_space() {
		return fana_char_empty_space;
	}
	public void setFana_char_empty_space(String fana_char_empty_space) {
		this.fana_char_empty_space = fana_char_empty_space;
	}
	
	@Override
	public String toString() {
		return "Otgffana [fana_Id=" + fana_Id + ", fana_Desc=" + fana_Desc + ", fana_Gruppo=" + fana_Gruppo
				+ ", fana_Cod_Interfaccia=" + fana_Cod_Interfaccia + ", fana_Stato=" + fana_Stato + ", fana_Tip_Flusso="
				+ fana_Tip_Flusso + ", fana_Direzione=" + fana_Direzione + ", fana_Libreria=" + fana_Libreria
				+ ", fana_File=" + fana_File + ", fana_Membro=" + fana_Membro + ", fana_Lib_Source=" + fana_Lib_Source
				+ ", fana_File_Source=" + fana_File_Source + ", fana_Membro_Source=" + fana_Membro_Source
				+ ", fana_Folder=" + fana_Folder + ", fana_File_Name=" + fana_File_Name + ", fana_Formato="
				+ fana_Formato + ", fana_Delim_Record=" + fana_Delim_Record + ", fana_Delim_Stringa="
				+ fana_Delim_Stringa + ", fana_Rimoz_Spazi=" + fana_Rimoz_Spazi + ", fana_Delim_Campo="
				+ fana_Delim_Campo + ", fana_Riemp_Campo=" + fana_Riemp_Campo + ", fana_Codepage=" + fana_Codepage
				+ ", fana_Mod_Acquisizione=" + fana_Mod_Acquisizione + ", fana_From_Ccsid=" + fana_From_Ccsid
				+ ", fana_Pgm_Controllo=" + fana_Pgm_Controllo + ", fana_Tipologia_Conn=" + fana_Tipologia_Conn
				+ ", fana_Host=" + fana_Host + ", fana_Port=" + fana_Port + ", fana_Remote_Folder=" + fana_Remote_Folder
				+ ", fana_Remote_File_Name=" + fana_Remote_File_Name + ", fana_Utente=" + fana_Utente
				+ ", fana_Password=" + fana_Password + ", fana_Utente_Sftp=" + fana_Utente_Sftp
				+ ", fana_Intergiry_Check=" + fana_Intergiry_Check + ", fana_Fl_Name_Semaforo=" + fana_Fl_Name_Semaforo
				+ ", fana_Num_Tenta_Ricez=" + fana_Num_Tenta_Ricez + ", fana_Intervallo_Retry=" + fana_Intervallo_Retry
				+ ", fana_Retention=" + fana_Retention + ", fana_Compression=" + fana_Compression
				+ ", fana_De_Compression=" + fana_De_Compression + ", fana_Backup=" + fana_Backup + ", fana_Invia_Mail="
				+ fana_Invia_Mail + ", fana_Lettera_Ok=" + fana_Lettera_Ok + ", fana_Lettera_Ko=" + fana_Lettera_Ko
				+ ", fana_Prog_Person=" + fana_Prog_Person + ", fana_Known_Ht_Fl=" + fana_Known_Ht_Fl + ", fana_Key_Fl="
				+ fana_Key_Fl + ", fana_Modalita_Passiva=" + fana_Modalita_Passiva + ", fana_Job_Desc=" + fana_Job_Desc
				+ ", fana_Lib_Job_Desc=" + fana_Lib_Job_Desc + ", fana_Cancella_File=" + fana_Cancella_File
				+ ", fana_Risottomettibile=" + fana_Risottomettibile + ", fana_Lunghezza_Fl_Flat="
				+ fana_Lunghezza_Fl_Flat + ", fana_Tipo_Trasferimento=" + fana_Tipo_Trasferimento
				+ ", fana_Bypass_Qtemp=" + fana_Bypass_Qtemp + ", fana_Esistenza_File=" + fana_Esistenza_File
				+ ", fana_Lettera_Flusso=" + fana_Lettera_Flusso + ", fana_Agg_Nomi_Col=" + fana_Agg_Nomi_Col
				+ ", fana_Crea_Vuoto=" + fana_Crea_Vuoto + ", fana_Internaz=" + fana_Internaz + ", fana_Sost_Val_Null="
				+ fana_Sost_Val_Null + ", fana_Elim_Nom_Col=" + fana_Elim_Nom_Col + ", fana_Flag_Ok_Vuoto="
				+ fana_Flag_Ok_Vuoto + ", fana_Ftp_Secure=" + fana_Ftp_Secure + ", fana_Interactive_Type="
				+ fana_Interactive_Type + ", fana_Interactive_Program=" + fana_Interactive_Program
				+ ", fana_Interactive_Result=" + fana_Interactive_Result + ", fana_Interactive_Command="
				+ fana_Interactive_Command + ", fana_Delay_Semaforo=" + fana_Delay_Semaforo + ", fana_Hash_Unico="
				+ fana_Hash_Unico + ", fana_Export_Flag=" + fana_Export_Flag + ", fana_Export_Code=" + fana_Export_Code
				+ ", fana_Fetch_Size=" + fana_Fetch_Size + ", fana_char_empty_space=" + fana_char_empty_space + "]";
	}
	

	
	
}
