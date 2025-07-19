package it.dmsoft.flowmanager.agent.engine.core.model;

import java.math.BigDecimal;

public class ExecutionFlowData {
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
	
	
	public String getFlowId() {
		return fana_Id;
	}
	public void setFlowId(String fana_Id) {
		this.fana_Id = fana_Id;
	}
	public String getFlowDesc() {
		return fana_Desc;
	}
	public void setFlowDesc(String fana_Desc) {
		this.fana_Desc = fana_Desc;
	}
	public String getFlowGruppo() {
		return fana_Gruppo;
	}
	public void setFlowGruppo(String fana_Gruppo) {
		this.fana_Gruppo = fana_Gruppo;
	}
	public String getFlowCodInterfaccia() {
		return fana_Cod_Interfaccia;
	}
	public void setFlowCodInterfaccia(String fana_Cod_Interfaccia) {
		this.fana_Cod_Interfaccia = fana_Cod_Interfaccia;
	}
	public String getFlowStato() {
		return fana_Stato;
	}
	public void setFlowStato(String fana_Stato) {
		this.fana_Stato = fana_Stato;
	}
	public String getFlowTipFlusso() {
		return fana_Tip_Flusso;
	}
	public void setFlowTipFlusso(String fana_Tip_Flusso) {
		this.fana_Tip_Flusso = fana_Tip_Flusso;
	}
	public String getFlowDirezione() {
		return fana_Direzione;
	}
	public void setFlowDirezione(String fana_Direzione) {
		this.fana_Direzione = fana_Direzione;
	}
	public String getFlowLibreria() {
		return fana_Libreria;
	}
	public void setFlowLibreria(String fana_Libreria) {
		this.fana_Libreria = fana_Libreria;
	}
	public String getFlowFile() {
		return fana_File;
	}
	public void setFlowFile(String fana_File) {
		this.fana_File = fana_File;
	}
	public String getFlowMembro() {
		return fana_Membro;
	}
	public void setFlowMembro(String fana_Membro) {
		this.fana_Membro = fana_Membro;
	}
	public String getFlowLibSource() {
		return fana_Lib_Source;
	}
	public void setFlowLibSource(String fana_Lib_Source) {
		this.fana_Lib_Source = fana_Lib_Source;
	}
	public String getFlowFileSource() {
		return fana_File_Source;
	}
	public void setFlowFileSource(String fana_File_Source) {
		this.fana_File_Source = fana_File_Source;
	}
	public String getFlowMembroSource() {
		return fana_Membro_Source;
	}
	public void setFlowMembroSource(String fana_Membro_Source) {
		this.fana_Membro_Source = fana_Membro_Source;
	}
	public String getFlowFolder() {
		return fana_Folder;
	}
	public void setFlowFolder(String fana_Folder) {
		this.fana_Folder = fana_Folder;
	}
	public String getFlowFileName() {
		return fana_File_Name;
	}
	public void setFlowFileName(String fana_File_Name) {
		this.fana_File_Name = fana_File_Name;
	}
	public String getFlowFormato() {
		return fana_Formato;
	}
	public void setFlowFormato(String fana_Formato) {
		this.fana_Formato = fana_Formato;
	}
	public String getFlowDelimRecord() {
		return fana_Delim_Record;
	}
	public void setFlowDelimRecord(String fana_Delim_Record) {
		this.fana_Delim_Record = fana_Delim_Record;
	}
	public String getFlowDelimStringa() {
		return fana_Delim_Stringa;
	}
	public void setFlowDelimStringa(String fana_Delim_Stringa) {
		this.fana_Delim_Stringa = fana_Delim_Stringa;
	}
	public String getFlowRimozSpazi() {
		return fana_Rimoz_Spazi;
	}
	public void setFlowRimozSpazi(String fana_Rimoz_Spazi) {
		this.fana_Rimoz_Spazi = fana_Rimoz_Spazi;
	}
	public String getFlowDelimCampo() {
		return fana_Delim_Campo;
	}
	public void setFlowDelimCampo(String fana_Delim_Campo) {
		this.fana_Delim_Campo = fana_Delim_Campo;
	}
	public String getFlowRiempCampo() {
		return fana_Riemp_Campo;
	}
	public void setFlowRiempCampo(String fana_Riemp_Campo) {
		this.fana_Riemp_Campo = fana_Riemp_Campo;
	}
	public BigDecimal getFlowCodepage() {
		return fana_Codepage;
	}
	public void setFlowCodepage(BigDecimal fana_Codepage) {
		this.fana_Codepage = fana_Codepage;
	}
	public String getFlowModAcquisizione() {
		return fana_Mod_Acquisizione;
	}
	public void setFlowModAcquisizione(String fana_Mod_Acquisizione) {
		this.fana_Mod_Acquisizione = fana_Mod_Acquisizione;
	}
	public BigDecimal getFlowFromCcsid() {
		return fana_From_Ccsid;
	}
	public void setFlowFromCcsid(BigDecimal fana_From_Ccsid) {
		this.fana_From_Ccsid = fana_From_Ccsid;
	}
	public String getFlowPgmControllo() {
		return fana_Pgm_Controllo;
	}
	public void setFlowPgmControllo(String fana_Pgm_Controllo) {
		this.fana_Pgm_Controllo = fana_Pgm_Controllo;
	}
	public String getFlowTipologiaConn() {
		return fana_Tipologia_Conn;
	}
	public void setFlowTipologiaConn(String fana_Tipologia_Conn) {
		this.fana_Tipologia_Conn = fana_Tipologia_Conn;
	}
	public String getFlowHost() {
		return fana_Host;
	}
	public void setFlowHost(String fana_Host) {
		this.fana_Host = fana_Host;
	}
	public BigDecimal getFlowPort() {
		return fana_Port;
	}
	public void setFlowPort(BigDecimal fana_Port) {
		this.fana_Port = fana_Port;
	}
	public String getFlowRemoteFolder() {
		return fana_Remote_Folder;
	}
	public void setFlowRemoteFolder(String fana_Remote_Folder) {
		this.fana_Remote_Folder = fana_Remote_Folder;
	}
	public String getFlowRemoteFileName() {
		return fana_Remote_File_Name;
	}
	public void setFlowRemoteFileName(String fana_Remote_File_Name) {
		this.fana_Remote_File_Name = fana_Remote_File_Name;
	}
	public String getFlowUtente() {
		return fana_Utente;
	}
	public void setFlowUtente(String fana_Utente) {
		this.fana_Utente = fana_Utente;
	}
	public String getFlowPassword() {
		return fana_Password;
	}
	public void setFlowPassword(String fana_Password) {
		this.fana_Password = fana_Password;
	}
	public String getFlowUtenteSftp() {
		return fana_Utente_Sftp;
	}
	public void setFlowUtenteSftp(String fana_Utente_Sftp) {
		this.fana_Utente_Sftp = fana_Utente_Sftp;
	}
	public String getFlowIntergiryCheck() {
		return fana_Intergiry_Check;
	}
	public void setFlowIntergiryCheck(String fana_Intergiry_Check) {
		this.fana_Intergiry_Check = fana_Intergiry_Check;
	}
	public String getFlowFlNameSemaforo() {
		return fana_Fl_Name_Semaforo;
	}
	public void setFlowFlNameSemaforo(String fana_Fl_Name_Semaforo) {
		this.fana_Fl_Name_Semaforo = fana_Fl_Name_Semaforo;
	}
	public BigDecimal getFlowNumTentaRicez() {
		return fana_Num_Tenta_Ricez;
	}
	public void setFlowNumTentaRicez(BigDecimal fana_Num_Tenta_Ricez) {
		this.fana_Num_Tenta_Ricez = fana_Num_Tenta_Ricez;
	}
	public BigDecimal getFlowIntervalloRetry() {
		return fana_Intervallo_Retry;
	}
	public void setFlowIntervalloRetry(BigDecimal fana_Intervallo_Retry) {
		this.fana_Intervallo_Retry = fana_Intervallo_Retry;
	}
	public BigDecimal getFlowRetention() {
		return fana_Retention;
	}
	public void setFlowRetention(BigDecimal fana_Retention) {
		this.fana_Retention = fana_Retention;
	}
	public String getFlowCompression() {
		return fana_Compression;
	}
	public void setFlowCompression(String fana_Compression) {
		this.fana_Compression = fana_Compression;
	}
	public String getFlowDeCompression() {
		return fana_De_Compression;
	}
	public void setFlowDeCompression(String fana_De_Compression) {
		this.fana_De_Compression = fana_De_Compression;
	}
	public String getFlowBackup() {
		return fana_Backup;
	}
	public void setFlowBackup(String fana_Backup) {
		this.fana_Backup = fana_Backup;
	}
	public String getFlowInviaMail() {
		return fana_Invia_Mail;
	}
	public void setFlowInviaMail(String fana_Invia_Mail) {
		this.fana_Invia_Mail = fana_Invia_Mail;
	}
	public String getFlowLetteraOk() {
		return fana_Lettera_Ok;
	}
	public void setFlowLetteraOk(String fana_Lettera_Ok) {
		this.fana_Lettera_Ok = fana_Lettera_Ok;
	}
	public String getFlowLetteraKo() {
		return fana_Lettera_Ko;
	}
	public void setFlowLetteraKo(String fana_Lettera_Ko) {
		this.fana_Lettera_Ko = fana_Lettera_Ko;
	}
	public String getFlowProgPerson() {
		return fana_Prog_Person;
	}
	public void setFlowProgPerson(String fana_Prog_Person) {
		this.fana_Prog_Person = fana_Prog_Person;
	}
	public String getFlowKnownHtFl() {
		return fana_Known_Ht_Fl;
	}
	public void setFlowKnownHtFl(String fana_Known_Ht_Fl) {
		this.fana_Known_Ht_Fl = fana_Known_Ht_Fl;
	}
	public String getFlowKeyFl() {
		return fana_Key_Fl;
	}
	public void setFlowKeyFl(String fana_Key_Fl) {
		this.fana_Key_Fl = fana_Key_Fl;
	}
	public String getFlowModalitaPassiva() {
		return fana_Modalita_Passiva;
	}
	public void setFlowModalitaPassiva(String fana_Modalita_Passiva) {
		this.fana_Modalita_Passiva = fana_Modalita_Passiva;
	}
	public String getFlowJobDesc() {
		return fana_Job_Desc;
	}
	public void setFlowJobDesc(String fana_Job_Desc) {
		this.fana_Job_Desc = fana_Job_Desc;
	}
	public String getFlowLibJobDesc() {
		return fana_Lib_Job_Desc;
	}
	public void setFlowLibJobDesc(String fana_Lib_Job_Desc) {
		this.fana_Lib_Job_Desc = fana_Lib_Job_Desc;
	}
	public String getFlowCancellaFile() {
		return fana_Cancella_File;
	}
	public void setFlowCancellaFile(String fana_Cancella_File) {
		this.fana_Cancella_File = fana_Cancella_File;
	}
	public String getFlowRisottomettibile() {
		return fana_Risottomettibile;
	}
	public void setFlowRisottomettibile(String fana_Risottomettibile) {
		this.fana_Risottomettibile = fana_Risottomettibile;
	}
	public BigDecimal getFlowLunghezzaFlFlat() {
		return fana_Lunghezza_Fl_Flat;
	}
	public void setFlowLunghezzaFlFlat(BigDecimal fana_Lunghezza_Fl_Flat) {
		this.fana_Lunghezza_Fl_Flat = fana_Lunghezza_Fl_Flat;
	}
	public String getFlowTipoTrasferimento() {
		return fana_Tipo_Trasferimento;
	}
	public void setFlowTipoTrasferimento(String fana_tipo_trasferimento) {
		this.fana_Tipo_Trasferimento = fana_tipo_trasferimento;
	}
	public String getFlowBypassQtemp() {
		return fana_Bypass_Qtemp;
	}
	public void setFlowBypassQtemp(String fana_Bypass_Qtemp) {
		this.fana_Bypass_Qtemp = fana_Bypass_Qtemp;
	}
	public String getFlowEsistenzaFile() {
		return fana_Esistenza_File;
	}
	public void setFlowEsistenzaFile(String fana_Esistenza_File) {
		this.fana_Esistenza_File = fana_Esistenza_File;
	}
	public String getFlowLetteraFlusso() {
		return fana_Lettera_Flusso;
	}
	public void setFlowLetteraFlusso(String fana_lettera_flusso) {
		this.fana_Lettera_Flusso = fana_lettera_flusso;
	}
	public String getFlowAggNomiCol() {
		return fana_Agg_Nomi_Col;
	}
	public void setFlowAggNomiCol(String fana_Agg_Nomi_Col) {
		this.fana_Agg_Nomi_Col = fana_Agg_Nomi_Col;
	}
	public String getFlowCreaVuoto() {
		return fana_Crea_Vuoto;
	}
	public void setFlowCreaVuoto(String fana_Crea_Vuoto) {
		this.fana_Crea_Vuoto = fana_Crea_Vuoto;
	}
	public String getFlowInternaz() {
		return fana_Internaz;
	}
	public void setFlowInternaz(String fana_Internaz) {
		this.fana_Internaz = fana_Internaz;
	}
	
	public String getFlowSostValNull() {
		return fana_Sost_Val_Null;
	}
	public void setFlowSostValNull(String fana_Sost_Val_Null) {
		this.fana_Sost_Val_Null = fana_Sost_Val_Null;
	}
	
	public String getFlowElimNomCol() {
		return fana_Elim_Nom_Col;
	}
	public void setFlowElimNomCol(String fana_Elim_Nom_Col) {
		this.fana_Elim_Nom_Col = fana_Elim_Nom_Col;
	}
	
	public String getFlowFlagOkVuoto() {
		return fana_Flag_Ok_Vuoto;
	}
	public void setFlowFlagOkVuoto(String fana_Flag_Ok_Vuoto) {
		this.fana_Flag_Ok_Vuoto = fana_Flag_Ok_Vuoto;
	}
	public String getFlowFtpSecure() {
		return fana_Ftp_Secure;
	}
	public void setFlowFtpSecure(String fana_Ftp_Secure) {
		this.fana_Ftp_Secure = fana_Ftp_Secure;
	}
	
	public String getFlowInteractiveType() {
		return fana_Interactive_Type;
	}
	public void setFlowInteractiveType(String fana_Interactive_Type) {
		this.fana_Interactive_Type = fana_Interactive_Type;
	}
	public String getFlowInteractiveProgram() {
		return fana_Interactive_Program;
	}
	public void setFlowInteractiveProgram(String fana_Interactive_Program) {
		this.fana_Interactive_Program = fana_Interactive_Program;
	}
	public String getFlowInteractiveResult() {
		return fana_Interactive_Result;
	}
	public void setFlowInteractiveResult(String fana_Interactive_Result) {
		this.fana_Interactive_Result = fana_Interactive_Result;
	}
	public String getFlowInteractiveCommand() {
		return fana_Interactive_Command;
	}
	public void setFlowInteractiveCommand(String fana_Interactive_Command) {
		this.fana_Interactive_Command = fana_Interactive_Command;
	}
	public BigDecimal getFlowDelaySemaforo() {
		return fana_Delay_Semaforo;
	}
	public void setFlowDelaySemaforo(BigDecimal fana_Delay_Semaforo) {
		this.fana_Delay_Semaforo = fana_Delay_Semaforo;
	}
	public String getFlowHashUnico() {
		return fana_Hash_Unico;
	}
	public void setFlowHashUnico(String fana_Hash_Unico) {
		this.fana_Hash_Unico = fana_Hash_Unico;
	}
	public String getFlowExportFlag() {
		return fana_Export_Flag;
	}
	public void setFlowExportFlag(String fana_Export_Flag) {
		this.fana_Export_Flag = fana_Export_Flag;
	}
	public String getFlowExportCode() {
		return fana_Export_Code;
	}
	public void setFlowExportCode(String fana_Export_Code) {
		this.fana_Export_Code = fana_Export_Code;
	}
	

	public BigDecimal getFlowFetchSize() {
		return fana_Fetch_Size;
	}
	public void setFlowFetchSize(BigDecimal fana_Fetch_Size) {
		this.fana_Fetch_Size = fana_Fetch_Size;
	}
	
	public String getFlowcharemptyspace() {
		return fana_char_empty_space;
	}
	public void setFlowcharemptyspace(String fana_char_empty_space) {
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
