package it.dmsoft.flowmanager.common.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import it.dmsoft.flowmanager.common.domain.Domains.ConnectionType;
import it.dmsoft.flowmanager.common.domain.Domains.Direction;
import it.dmsoft.flowmanager.common.domain.Domains.FileFormat;
import it.dmsoft.flowmanager.common.domain.Domains.Status;
import it.dmsoft.flowmanager.common.domain.Domains.Type;
import it.dmsoft.flowmanager.common.domain.Domains.YesNo;

public class FlowLogData {

	private BigDecimal logProgrLog;
	
	private String logId;
	private Timestamp logTsInizio;
	private Timestamp logTsFine;
	
	private Status logEsito;
	
	private String logUtente;
	
	private String logPassword;
	
	private String logCodInterfaccia;
	
	private YesNo logStato;
	
	private Type logTipFlusso;
	
	private Direction logDirezione;
	
	private String logLibreria;
	
	private String logFile;
	
	private String logMembro;
	
	private String logLibSource;
	
	private String logFileSource;
	
	private String logMembroSource;
	
	private String logFolder;
	
	private String logFileName;
	
	private FileFormat logFormato;
	
	private String logDelimRecord;
	
	private String logDelimCampo;
	
	private BigDecimal logCodepage;
	
	private String logModAcquiszione;
	
	private String logPgmControllo;
	
	private ConnectionType logTipologiaConn;
	
	private String logHost;
	
	private BigDecimal logPort;
	
	private String logRemoteFolder;
	
	private String logRemoteFileName;
	
	private String logUtenteSftp;
	
	private YesNo logIntegrityCheck;
	
	private YesNo logFlNameSemaforo;
	
	private BigDecimal logNumTentaRicez;
	
	private BigDecimal logIntervalloRetry;
	
	private BigDecimal logRetention;
	
	private YesNo logCompression;
	
	private String logBackup;
	
	private YesNo logInviaMail;
	
	private String logLetteraOk;
	
	private String logLetteraKo;
	
	private String logLogFile;
	
	private String logErroreDesc;
	
	private String logJobAs;
	
	private String logUserAs;
	
	private String logJobNbrAs;
	
	private ConnectionType logTipoTrasferimento;
	
	private YesNo logBypassQtemp;
	
	private String logJavaHome;
	
	//aggiunta campi mancanti
	
	private YesNo logEsistenzaFile;
	
	private String logLetteraFlusso;
	
	private YesNo logCreaVuoto;
	
	private String logInternaz;
	
	private String logSostValNull;
	
	private YesNo logElimNomCol;
	
	private YesNo logFlagOkVuoto;
	
	private YesNo logFtpSecure;
	
	private String logInteractiveType;
	
	private String logInteractiveProgram;
	
	private String logInteractiveResult;
	
	private String logInteractiveCommand;
	
	private BigDecimal logDelaySemaforo;	
	
	private YesNo logHashUnico;
	
	private YesNo logExportFlag;
	
	private String logExportCode;
	
	private BigDecimal logFetchSize;
	
	private YesNo logCharEmptySpace;

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

	public Status getLogEsito() {
		return logEsito;
	}

	public void setLogEsito(Status logEsito) {
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

	public YesNo getLogStato() {
		return logStato;
	}

	public void setLogStato(YesNo logStato) {
		this.logStato = logStato;
	}

	public Type getLogTipFlusso() {
		return logTipFlusso;
	}

	public void setLogTipFlusso(Type logTipFlusso) {
		this.logTipFlusso = logTipFlusso;
	}

	public Direction getLogDirezione() {
		return logDirezione;
	}

	public void setLogDirezione(Direction logDirezione) {
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

	public String getLogBackup() {
		return logBackup;
	}

	public void setLogBackup(String logBackup) {
		this.logBackup = logBackup;
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

	public String getLogJavaHome() {
		return logJavaHome;
	}

	public void setLogJavaHome(String logJavaHome) {
		this.logJavaHome = logJavaHome;
	}

	public String getLogLetteraFlusso() {
		return logLetteraFlusso;
	}

	public void setLogLetteraFlusso(String logLetteraFlusso) {
		this.logLetteraFlusso = logLetteraFlusso;
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

	public FileFormat getLogFormato() {
		return logFormato;
	}

	public void setLogFormato(FileFormat logFormato) {
		this.logFormato = logFormato;
	}

	public ConnectionType getLogTipologiaConn() {
		return logTipologiaConn;
	}

	public void setLogTipologiaConn(ConnectionType logTipologiaConn) {
		this.logTipologiaConn = logTipologiaConn;
	}

	public YesNo getLogIntegrityCheck() {
		return logIntegrityCheck;
	}

	public void setLogIntegrityCheck(YesNo logIntegrityCheck) {
		this.logIntegrityCheck = logIntegrityCheck;
	}

	public YesNo getLogFlNameSemaforo() {
		return logFlNameSemaforo;
	}

	public void setLogFlNameSemaforo(YesNo logFlNameSemaforo) {
		this.logFlNameSemaforo = logFlNameSemaforo;
	}

	public YesNo getLogCompression() {
		return logCompression;
	}

	public void setLogCompression(YesNo logCompression) {
		this.logCompression = logCompression;
	}

	public YesNo getLogInviaMail() {
		return logInviaMail;
	}

	public void setLogInviaMail(YesNo logInviaMail) {
		this.logInviaMail = logInviaMail;
	}

	public ConnectionType getLogTipoTrasferimento() {
		return logTipoTrasferimento;
	}

	public void setLogTipoTrasferimento(ConnectionType logTipoTrasferimento) {
		this.logTipoTrasferimento = logTipoTrasferimento;
	}

	public YesNo getLogBypassQtemp() {
		return logBypassQtemp;
	}

	public void setLogBypassQtemp(YesNo logBypassQtemp) {
		this.logBypassQtemp = logBypassQtemp;
	}

	public YesNo getLogEsistenzaFile() {
		return logEsistenzaFile;
	}

	public void setLogEsistenzaFile(YesNo logEsistenzaFile) {
		this.logEsistenzaFile = logEsistenzaFile;
	}

	public YesNo getLogCreaVuoto() {
		return logCreaVuoto;
	}

	public void setLogCreaVuoto(YesNo logCreaVuoto) {
		this.logCreaVuoto = logCreaVuoto;
	}

	public YesNo getLogElimNomCol() {
		return logElimNomCol;
	}

	public void setLogElimNomCol(YesNo logElimNomCol) {
		this.logElimNomCol = logElimNomCol;
	}

	public YesNo getLogFlagOkVuoto() {
		return logFlagOkVuoto;
	}

	public void setLogFlagOkVuoto(YesNo logFlagOkVuoto) {
		this.logFlagOkVuoto = logFlagOkVuoto;
	}

	public YesNo getLogFtpSecure() {
		return logFtpSecure;
	}

	public void setLogFtpSecure(YesNo logFtpSecure) {
		this.logFtpSecure = logFtpSecure;
	}

	public YesNo getLogHashUnico() {
		return logHashUnico;
	}

	public void setLogHashUnico(YesNo logHashUnico) {
		this.logHashUnico = logHashUnico;
	}

	public YesNo getLogExportFlag() {
		return logExportFlag;
	}

	public void setLogExportFlag(YesNo logExportFlag) {
		this.logExportFlag = logExportFlag;
	}

	public YesNo getLogCharEmptySpace() {
		return logCharEmptySpace;
	}

	public void setLogCharEmptySpace(YesNo logCharEmptySpace) {
		this.logCharEmptySpace = logCharEmptySpace;
	}

	
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
