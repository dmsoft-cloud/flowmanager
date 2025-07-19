package it.dmsoft.flowmanager.agent.be.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class FlowLog {
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

	//Auto increment
	private BigDecimal logProgrLog;

	private String logId;

	private Timestamp logTsInizio;

	private Timestamp logTsFine;

	private String logEsito;

	private String logUtente;

	private String logPassword;

	private String logCodInterfaccia;

	private String logStato;

	private String logTipFlusso;

	private String logDirezione;

	private String logLibreria;

	private String logFile;

	private String logMembro;

	private String logLibSource;

	private String logFileSource;

	private String logMembroSource;

	private String logFolder;

	private String logFileName;

	private String logFormato;

	private String logDelimRecord;

	private String logDelimCampo;

	private BigDecimal logCodepage;

	private String logModAcquiszione;

	private String logPgmControllo;

	private String logTipologiaConn;

	private String logHost;

	private BigDecimal logPort;

	private String logRemoteFolder;

	private String logRemoteFileName;

	private String logUtenteSftp;

	private String logIntegrityCheck;

	private String logFlNameSemaforo;

	private BigDecimal logNumTentaRicez;

	private BigDecimal logIntervalloRetry;

	private BigDecimal logRetention;

	private String logCompression;

	private String logBackup;

	private String logInviaMail;

	private String logLetteraOk;

	private String logLetteraKo;

	private String logLogFile;

	private String logErroreDesc;

	private String logJobAs;

	private String logUserAs;

	private String logJobNbrAs;

	private String logTipoTrasferimento;

	private String logBypassQtemp;

	private String logJavaHome;
	
	//aggiunta campi mancanti
	
	private String logEsistenzaFile;
	
	private String logLetteraFlusso;
	
	private String logCreaVuoto;
	
	private String logInternaz;
	
	private String logSostValNull;
	
	private String logElimNomCol;
	
	private String logFlagOkVuoto;
	
	private String logFtpSecure;
	
	private String logInteractiveType;
	
	private String logInteractiveProgram;
	
	private String logInteractiveResult;
	
	private String logInteractiveCommand;
	
	private BigDecimal logDelaySemaforo;	
	
	
	private String logHashUnico;
	
	private String logExportFlag;
	
	private String logExportCode;
	
	private BigDecimal logFetchSize;
	
	private String logCharEmptySpace;

	public BigDecimal getLogProgrLog() {
		return logProgrLog;
	}

	public void setLogProgrLog(BigDecimal logProgrLog) {
		this.logProgrLog = logProgrLog;
	}

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public Timestamp getLogTsInizio() {
		return logTsInizio;
	}

	public void setLogTsInizio(Timestamp logTsInizio) {
		this.logTsInizio = logTsInizio;
	}

	public Timestamp getLogTsFine() {
		return logTsFine;
	}

	public void setLogTsFine(Timestamp logTsFine) {
		this.logTsFine = logTsFine;
	}

	public String getLogEsito() {
		return logEsito;
	}

	public void setLogEsito(String logEsito) {
		this.logEsito = logEsito;
	}

	public String getLogUtente() {
		return logUtente;
	}

	public void setLogUtente(String logUtente) {
		this.logUtente = logUtente;
	}

	public String getLogPassword() {
		return logPassword;
	}

	public void setLogPassword(String logPassword) {
		this.logPassword = logPassword;
	}

	public String getLogCodInterfaccia() {
		return logCodInterfaccia;
	}

	public void setLogCodInterfaccia(String logCodInterfaccia) {
		this.logCodInterfaccia = logCodInterfaccia;
	}

	public String getLogStato() {
		return logStato;
	}

	public void setLogStato(String logStato) {
		this.logStato = logStato;
	}

	public String getLogTipFlusso() {
		return logTipFlusso;
	}

	public void setLogTipFlusso(String logTipFlusso) {
		this.logTipFlusso = logTipFlusso;
	}

	public String getLogDirezione() {
		return logDirezione;
	}

	public void setLogDirezione(String logDirezione) {
		this.logDirezione = logDirezione;
	}

	public String getLogLibreria() {
		return logLibreria;
	}

	public void setLogLibreria(String logLibreria) {
		this.logLibreria = logLibreria;
	}

	public String getLogFile() {
		return logFile;
	}

	public void setLogFile(String logFile) {
		this.logFile = logFile;
	}

	public String getLogMembro() {
		return logMembro;
	}

	public void setLogMembro(String logMembro) {
		this.logMembro = logMembro;
	}

	public String getLogLibSource() {
		return logLibSource;
	}

	public void setLogLibSource(String logLibSource) {
		this.logLibSource = logLibSource;
	}

	public String getLogFileSource() {
		return logFileSource;
	}

	public void setLogFileSource(String logFileSource) {
		this.logFileSource = logFileSource;
	}

	public String getLogMembroSource() {
		return logMembroSource;
	}

	public void setLogMembroSource(String logMembroSource) {
		this.logMembroSource = logMembroSource;
	}

	public String getLogFolder() {
		return logFolder;
	}

	public void setLogFolder(String logFolder) {
		this.logFolder = logFolder;
	}

	public String getLogFileName() {
		return logFileName;
	}

	public void setLogFileName(String logFileName) {
		this.logFileName = logFileName;
	}

	public String getLogFormato() {
		return logFormato;
	}

	public void setLogFormato(String logFormato) {
		this.logFormato = logFormato;
	}

	public String getLogDelimRecord() {
		return logDelimRecord;
	}

	public void setLogDelimRecord(String logDelimRecord) {
		this.logDelimRecord = logDelimRecord;
	}

	public String getLogDelimCampo() {
		return logDelimCampo;
	}

	public void setLogDelimCampo(String logDelimCampo) {
		this.logDelimCampo = logDelimCampo;
	}

	public BigDecimal getLogCodepage() {
		return logCodepage;
	}

	public void setLogCodepage(BigDecimal logCodepage) {
		this.logCodepage = logCodepage;
	}

	public String getLogModAcquiszione() {
		return logModAcquiszione;
	}

	public void setLogModAcquiszione(String logModAcquiszione) {
		this.logModAcquiszione = logModAcquiszione;
	}

	public String getLogPgmControllo() {
		return logPgmControllo;
	}

	public void setLogPgmControllo(String logPgmControllo) {
		this.logPgmControllo = logPgmControllo;
	}

	public String getLogTipologiaConn() {
		return logTipologiaConn;
	}

	public void setLogTipologiaConn(String logTipologiaConn) {
		this.logTipologiaConn = logTipologiaConn;
	}

	public String getLogHost() {
		return logHost;
	}

	public void setLogHost(String logHost) {
		this.logHost = logHost;
	}

	public BigDecimal getLogPort() {
		return logPort;
	}

	public void setLogPort(BigDecimal logPort) {
		this.logPort = logPort;
	}

	public String getLogRemoteFolder() {
		return logRemoteFolder;
	}

	public void setLogRemoteFolder(String logRemoteFolder) {
		this.logRemoteFolder = logRemoteFolder;
	}

	public String getLogRemoteFileName() {
		return logRemoteFileName;
	}

	public void setLogRemoteFileName(String logRemoteFileName) {
		this.logRemoteFileName = logRemoteFileName;
	}

	public String getLogUtenteSftp() {
		return logUtenteSftp;
	}

	public void setLogUtenteSftp(String logUtenteSftp) {
		this.logUtenteSftp = logUtenteSftp;
	}

	public String getLogIntegrityCheck() {
		return logIntegrityCheck;
	}

	public void setLogIntegrityCheck(String logIntegrityCheck) {
		this.logIntegrityCheck = logIntegrityCheck;
	}

	public String getLogFlNameSemaforo() {
		return logFlNameSemaforo;
	}

	public void setLogFlNameSemaforo(String logFlNameSemaforo) {
		this.logFlNameSemaforo = logFlNameSemaforo;
	}

	public BigDecimal getLogNumTentaRicez() {
		return logNumTentaRicez;
	}

	public void setLogNumTentaRicez(BigDecimal logNumTentaRicez) {
		this.logNumTentaRicez = logNumTentaRicez;
	}

	public BigDecimal getLogIntervalloRetry() {
		return logIntervalloRetry;
	}

	public void setLogIntervalloRetry(BigDecimal logIntervalloRetry) {
		this.logIntervalloRetry = logIntervalloRetry;
	}

	public BigDecimal getLogRetention() {
		return logRetention;
	}

	public void setLogRetention(BigDecimal logRetention) {
		this.logRetention = logRetention;
	}

	public String getLogCompression() {
		return logCompression;
	}

	public void setLogCompression(String logCompression) {
		this.logCompression = logCompression;
	}

	public String getLogBackup() {
		return logBackup;
	}

	public void setLogBackup(String logBackup) {
		this.logBackup = logBackup;
	}

	public String getLogInviaMail() {
		return logInviaMail;
	}

	public void setLogInviaMail(String logInviaMail) {
		this.logInviaMail = logInviaMail;
	}

	public String getLogLetteraOk() {
		return logLetteraOk;
	}

	public void setLogLetteraOk(String logLetteraOk) {
		this.logLetteraOk = logLetteraOk;
	}

	public String getLogLetteraKo() {
		return logLetteraKo;
	}

	public void setLogLetteraKo(String logLetteraKo) {
		this.logLetteraKo = logLetteraKo;
	}

	public String getLogLogFile() {
		return logLogFile;
	}

	public void setLogLogFile(String logLogFile) {
		this.logLogFile = logLogFile;
	}

	public String getLogErroreDesc() {
		return logErroreDesc;
	}

	public void setLogErroreDesc(String logErroreDesc) {
		this.logErroreDesc = logErroreDesc;
	}

	public String getLogJobAs() {
		return logJobAs;
	}

	public void setLogJobAs(String logJobAs) {
		this.logJobAs = logJobAs;
	}

	public String getLogUserAs() {
		return logUserAs;
	}

	public void setLogUserAs(String logUserAs) {
		this.logUserAs = logUserAs;
	}

	public String getLogJobNbrAs() {
		return logJobNbrAs;
	}

	public void setLogJobNbrAs(String logJobNbrAs) {
		this.logJobNbrAs = logJobNbrAs;
	}

	public String getLogTipoTrasferimento() {
		return logTipoTrasferimento;
	}

	public void setLogTipoTrasferimento(String logTipoTrasferimento) {
		this.logTipoTrasferimento = logTipoTrasferimento;
	}

	public String getLogBypassQtemp() {
		return logBypassQtemp;
	}

	public void setLogBypassQtemp(String logBypassQtemp) {
		this.logBypassQtemp = logBypassQtemp;
	}

	public String getLogJavaHome() {
		return logJavaHome;
	}

	public void setLogJavaHome(String logJavaHome) {
		this.logJavaHome = logJavaHome;
	}

	
	
	public String getLogEsistenzaFile() {
		return logEsistenzaFile;
	}

	public void setLogEsistenzaFile(String logEsistenzaFile) {
		this.logEsistenzaFile = logEsistenzaFile;
	}

	public String getLogLetteraFlusso() {
		return logLetteraFlusso;
	}

	public void setLogLetteraFlusso(String logLetteraFlusso) {
		this.logLetteraFlusso = logLetteraFlusso;
	}

	public String getLogCreaVuoto() {
		return logCreaVuoto;
	}

	public void setLogCreaVuoto(String logCreaVuoto) {
		this.logCreaVuoto = logCreaVuoto;
	}

	public String getLogInternaz() {
		return logInternaz;
	}

	public void setLogInternaz(String logInternaz) {
		this.logInternaz = logInternaz;
	}

	public String getLogSostValNull() {
		return logSostValNull;
	}

	public void setLogSostValNull(String logSostValNull) {
		this.logSostValNull = logSostValNull;
	}

	public String getLogElimNomCol() {
		return logElimNomCol;
	}

	public void setLogElimNomCol(String logElimNomCol) {
		this.logElimNomCol = logElimNomCol;
	}

	public String getLogFlagOkVuoto() {
		return logFlagOkVuoto;
	}

	public void setLogFlagOkVuoto(String logFlagOkVuoto) {
		this.logFlagOkVuoto = logFlagOkVuoto;
	}

	public String getLogFtpSecure() {
		return logFtpSecure;
	}

	public void setLogFtpSecure(String logFtpSecure) {
		this.logFtpSecure = logFtpSecure;
	}

	public String getLogInteractiveType() {
		return logInteractiveType;
	}

	public void setLogInteractiveType(String logInteractiveType) {
		this.logInteractiveType = logInteractiveType;
	}

	public String getLogInteractiveProgram() {
		return logInteractiveProgram;
	}

	public void setLogInteractiveProgram(String logInteractiveProgram) {
		this.logInteractiveProgram = logInteractiveProgram;
	}

	public String getLogInteractiveResult() {
		return logInteractiveResult;
	}

	public void setLogInteractiveResult(String logInteractiveResult) {
		this.logInteractiveResult = logInteractiveResult;
	}

	public String getLogInteractiveCommand() {
		return logInteractiveCommand;
	}

	public void setLogInteractiveCommand(String logInteractiveCommand) {
		this.logInteractiveCommand = logInteractiveCommand;
	}

	public BigDecimal getLogDelaySemaforo() {
		return logDelaySemaforo;
	}

	public void setLogDelaySemaforo(BigDecimal logDelaySemaforo) {
		this.logDelaySemaforo = logDelaySemaforo;
	}

	public String getLogHashUnico() {
		return logHashUnico;
	}

	public void setLogHashUnico(String logHashUnico) {
		this.logHashUnico = logHashUnico;
	}	

	public String getLogExportFlag() {
		return logExportFlag;
	}

	public void setLogExportFlag(String logExportFlag) {
		this.logExportFlag = logExportFlag;
	}

	public String getLogExportCode() {
		return logExportCode;
	}

	public void setLogExportCode(String logExportCode) {
		this.logExportCode = logExportCode;
	}

	public BigDecimal getLogFetchSize() {
		return logFetchSize;
	}

	public void setLogFetchSize(BigDecimal logFetchSize) {
		this.logFetchSize = logFetchSize;
	}



	public String getLogCharEmptySpace() {
		return logCharEmptySpace;
	}

	public void setLogCharEmptySpace(String logCharEmptySpace) {
		this.logCharEmptySpace = logCharEmptySpace;
	}

	@Override
	public String toString() {
		return "FlowLog [logProgrLog=" + logProgrLog + ", logId=" + logId + ", logTsInizio=" + logTsInizio
				+ ", logTsFine=" + logTsFine + ", logEsito=" + logEsito + ", logUtente=" + logUtente + ", logPassword="
				+ logPassword + ", logCodInterfaccia=" + logCodInterfaccia + ", logStato=" + logStato
				+ ", logTipFlusso=" + logTipFlusso + ", logDirezione=" + logDirezione + ", logLibreria=" + logLibreria
				+ ", logFile=" + logFile + ", logMembro=" + logMembro + ", logLibSource=" + logLibSource
				+ ", logFileSource=" + logFileSource + ", logMembroSource=" + logMembroSource + ", logFolder="
				+ logFolder + ", logFileName=" + logFileName + ", logFormato=" + logFormato + ", logDelimRecord="
				+ logDelimRecord + ", logDelimCampo=" + logDelimCampo + ", logCodepage=" + logCodepage
				+ ", logModAcquiszione=" + logModAcquiszione + ", logPgmControllo=" + logPgmControllo + ", logTipologiaConn="
				+ logTipologiaConn + ", logHost=" + logHost + ", logPort=" + logPort + ", logRemoteFolder="
				+ logRemoteFolder + ", logRemoteFileName=" + logRemoteFileName + ", logUtenteSftp=" + logUtenteSftp
				+ ", logIntegrityCheck=" + logIntegrityCheck + ", logFlNameSemaforo=" + logFlNameSemaforo
				+ ", logNumTentaRicez=" + logNumTentaRicez + ", logIntervalloRetry=" + logIntervalloRetry
				+ ", logRetention=" + logRetention + ", logCompression=" + logCompression + ", logBackup=" + logBackup
				+ ", logInviaMail=" + logInviaMail + ", logLetteraOk=" + logLetteraOk + ", logLetteraKo=" + logLetteraKo
				+ ", logLogFile=" + logLogFile + ", logErroreDesc=" + logErroreDesc + ", logJobAs=" + logJobAs
				+ ", logUserAs=" + logUserAs + ", logJobNbrAs=" + logJobNbrAs + ", logTipoTrasferimento="
				+ logTipoTrasferimento + ", logBypassQtemp=" + logBypassQtemp + ", logJavaHome=" + logJavaHome
				+ ", logEsistenzaFile=" + logEsistenzaFile + ", logLetteraFlusso=" + logLetteraFlusso
				+ ", logCreaVuoto=" + logCreaVuoto + ", logInternaz=" + logInternaz + ", logSostValNull="
				+ logSostValNull + ", logElimNomCol=" + logElimNomCol + ", logFlagOkVuoto=" + logFlagOkVuoto
				+ ", logFtpSecure=" + logFtpSecure + ", logInteractiveType=" + logInteractiveType
				+ ", logInteractiveProgram=" + logInteractiveProgram + ", logInteractiveResult=" + logInteractiveResult
				+ ", logInteractiveCommand=" + logInteractiveCommand + ", logDelaySemaforo=" + logDelaySemaforo
				+ ", logHashUnico=" + logHashUnico + ", logExportFlag=" + logExportFlag + ", logExportCode="
				+ logExportCode + ", logFetchSize=" + logFetchSize + ", logCharEmptySpace=" + logCharEmptySpace + "]";
	}
	
}
