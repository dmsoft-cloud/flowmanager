package it.dmsoft.flowmanager.agent.engine.core.model;

import java.math.BigDecimal;

import it.dmsoft.flowmanager.common.domain.Domains.ConnectionType;
import it.dmsoft.flowmanager.common.domain.Domains.Direction;
import it.dmsoft.flowmanager.common.domain.Domains.YesNo;

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

	
	private String flowId;
	private String flowDesc;
	private String flowGruppo;
	private String flowCodInterfaccia;
	private YesNo flowStato;
	private String flowTipFlusso;
	private Direction flowDirezione;
	private String flowLibreria;
	private String flowFile;
	private String flowMembro;
	private String flowLibSource;
	private String flowFileSource;
	private String flowMembroSource;
	private String flowFolder;
	private String flowFileName;
	private String flowFormato;
	private String flowDelimRecord;
	private String flowDelimStringa;
	private String flowRimozSpazi;
	private String flowDelimCampo;
	private String flowRiempCampo;
	private BigDecimal flowCodepage;
	private String flowModAcquisizione;
	private BigDecimal flowFromCcsid;
	private String flowPgmControllo;
	//private Type flowTipologiaConn;
	private String flowHost;
	private BigDecimal flowPort;
	private String flowRemoteFolder;
	private String flowRemoteFileName;
	private String flowUtente;
	private String flowPassword;
	private String flowUtenteSftp;
	private YesNo flowIntegrityCheck;
	private String flowFlNameSemaforo;
	private BigDecimal flowNumTentaRicez;
	private BigDecimal flowIntervalloRetry;
	private BigDecimal flowRetention;
	private YesNo flowCompression;
	private YesNo flowDeCompression;
	private String flowBackup;
	private YesNo flowInviaMail;
	private String flowLetteraOk;
	private String flowLetteraKo;
	private String flowProgPerson;
	private String flowKnownHtFl;
	private String flowKeyFl;
	private YesNo flowModalitaPassiva;
	private String flowJobDesc;
	private String flowLibJobDesc;
	private YesNo flowCancellaFile;
	private YesNo flowRisottomettibile;
	private BigDecimal flowLunghezzaFlFlat;
	private ConnectionType flowTipoTrasferimento;
	private YesNo flowBypassQtemp;
	private YesNo flowEsistenzaFile;
	private String flowLetteraFlusso;
	private String flowAggNomiCol;
	private YesNo flowCreaVuoto;
	private String flowInternaz;
	private String flowSostValNull;
	private YesNo flowElimNomCol;
	private YesNo flowFlagOkVuoto;
	private YesNo flowFtpSecure;
	private String flowInteractiveType;
	private String flowInteractiveProgram;
	private YesNo flowInteractiveResult;
	private String flowInteractiveCommand;
	private BigDecimal flowDelaySemaforo;
	private YesNo flowHashUnico;
	private String flowExportFlag;
	private String flowExportCode;
	private BigDecimal flowFetchSize;
	private String flowcharemptyspace;
	
	
	public String getFlowId() {
		return flowId;
	}
	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
	public String getFlowDesc() {
		return flowDesc;
	}
	public void setFlowDesc(String flowDesc) {
		this.flowDesc = flowDesc;
	}
	public String getFlowGruppo() {
		return flowGruppo;
	}
	public void setFlowGruppo(String flowGruppo) {
		this.flowGruppo = flowGruppo;
	}
	public String getFlowCodInterfaccia() {
		return flowCodInterfaccia;
	}
	public void setFlowCodInterfaccia(String flowCodInterfaccia) {
		this.flowCodInterfaccia = flowCodInterfaccia;
	}
	public YesNo getFlowStato() {
		return flowStato;
	}
	public void setFlowStato(YesNo flowStato) {
		this.flowStato = flowStato;
	}
	public String getFlowTipFlusso() {
		return flowTipFlusso;
	}
	public void setFlowTipFlusso(String flowTipFlusso) {
		this.flowTipFlusso = flowTipFlusso;
	}
	public Direction getFlowDirezione() {
		return flowDirezione;
	}
	public void setFlowDirezione(Direction flowDirezione) {
		this.flowDirezione = flowDirezione;
	}
	public String getFlowLibreria() {
		return flowLibreria;
	}
	public void setFlowLibreria(String flowLibreria) {
		this.flowLibreria = flowLibreria;
	}
	public String getFlowFile() {
		return flowFile;
	}
	public void setFlowFile(String flowFile) {
		this.flowFile = flowFile;
	}
	public String getFlowMembro() {
		return flowMembro;
	}
	public void setFlowMembro(String flowMembro) {
		this.flowMembro = flowMembro;
	}
	public String getFlowLibSource() {
		return flowLibSource;
	}
	public void setFlowLibSource(String flowLibSource) {
		this.flowLibSource = flowLibSource;
	}
	public String getFlowFileSource() {
		return flowFileSource;
	}
	public void setFlowFileSource(String flowFileSource) {
		this.flowFileSource = flowFileSource;
	}
	public String getFlowMembroSource() {
		return flowMembroSource;
	}
	public void setFlowMembroSource(String flowMembroSource) {
		this.flowMembroSource = flowMembroSource;
	}
	public String getFlowFolder() {
		return flowFolder;
	}
	public void setFlowFolder(String flowFolder) {
		this.flowFolder = flowFolder;
	}
	public String getFlowFileName() {
		return flowFileName;
	}
	public void setFlowFileName(String flowFileName) {
		this.flowFileName = flowFileName;
	}
	public String getFlowFormato() {
		return flowFormato;
	}
	public void setFlowFormato(String flowFormato) {
		this.flowFormato = flowFormato;
	}
	public String getFlowDelimRecord() {
		return flowDelimRecord;
	}
	public void setFlowDelimRecord(String flowDelimRecord) {
		this.flowDelimRecord = flowDelimRecord;
	}
	public String getFlowDelimStringa() {
		return flowDelimStringa;
	}
	public void setFlowDelimStringa(String flowDelimStringa) {
		this.flowDelimStringa = flowDelimStringa;
	}
	public String getFlowRimozSpazi() {
		return flowRimozSpazi;
	}
	public void setFlowRimozSpazi(String flowRimozSpazi) {
		this.flowRimozSpazi = flowRimozSpazi;
	}
	public String getFlowDelimCampo() {
		return flowDelimCampo;
	}
	public void setFlowDelimCampo(String flowDelimCampo) {
		this.flowDelimCampo = flowDelimCampo;
	}
	public String getFlowRiempCampo() {
		return flowRiempCampo;
	}
	public void setFlowRiempCampo(String flowRiempCampo) {
		this.flowRiempCampo = flowRiempCampo;
	}
	public BigDecimal getFlowCodepage() {
		return flowCodepage;
	}
	public void setFlowCodepage(BigDecimal flowCodepage) {
		this.flowCodepage = flowCodepage;
	}
	public String getFlowModAcquisizione() {
		return flowModAcquisizione;
	}
	public void setFlowModAcquisizione(String flowModAcquisizione) {
		this.flowModAcquisizione = flowModAcquisizione;
	}
	public BigDecimal getFlowFromCcsid() {
		return flowFromCcsid;
	}
	public void setFlowFromCcsid(BigDecimal flowFromCcsid) {
		this.flowFromCcsid = flowFromCcsid;
	}
	public String getFlowPgmControllo() {
		return flowPgmControllo;
	}
	public void setFlowPgmControllo(String flowPgmControllo) {
		this.flowPgmControllo = flowPgmControllo;
	}
	//public Type getFlowTipologiaConn() {
	//	return flowTipologiaConn;
	//}
	
	//public void setFlowTipologiaConn(Type flowTipologiaConn) {
	//	this.flowTipologiaConn = flowTipologiaConn;
	//}
	
	public String getFlowHost() {
		return flowHost;
	}
	public void setFlowHost(String flowHost) {
		this.flowHost = flowHost;
	}
	public BigDecimal getFlowPort() {
		return flowPort;
	}
	public void setFlowPort(BigDecimal flowPort) {
		this.flowPort = flowPort;
	}
	public String getFlowRemoteFolder() {
		return flowRemoteFolder;
	}
	public void setFlowRemoteFolder(String flowRemoteFolder) {
		this.flowRemoteFolder = flowRemoteFolder;
	}
	public String getFlowRemoteFileName() {
		return flowRemoteFileName;
	}
	public void setFlowRemoteFileName(String flowRemoteFileName) {
		this.flowRemoteFileName = flowRemoteFileName;
	}
	public String getFlowUtente() {
		return flowUtente;
	}
	public void setFlowUtente(String flowUtente) {
		this.flowUtente = flowUtente;
	}
	public String getFlowPassword() {
		return flowPassword;
	}
	public void setFlowPassword(String flowPassword) {
		this.flowPassword = flowPassword;
	}
	public String getFlowUtenteSftp() {
		return flowUtenteSftp;
	}
	public void setFlowUtenteSftp(String flowUtenteSftp) {
		this.flowUtenteSftp = flowUtenteSftp;
	}
	public YesNo getFlowIntegrityCheck() {
		return flowIntegrityCheck;
	}
	public void setFlowIntegrityCheck(YesNo flowIntergiryCheck) {
		this.flowIntegrityCheck = flowIntergiryCheck;
	}
	public String getFlowFlNameSemaforo() {
		return flowFlNameSemaforo;
	}
	public void setFlowFlNameSemaforo(String flowFlNameSemaforo) {
		this.flowFlNameSemaforo = flowFlNameSemaforo;
	}
	public BigDecimal getFlowNumTentaRicez() {
		return flowNumTentaRicez;
	}
	public void setFlowNumTentaRicez(BigDecimal flowNumTentaRicez) {
		this.flowNumTentaRicez = flowNumTentaRicez;
	}
	public BigDecimal getFlowIntervalloRetry() {
		return flowIntervalloRetry;
	}
	public void setFlowIntervalloRetry(BigDecimal flowIntervalloRetry) {
		this.flowIntervalloRetry = flowIntervalloRetry;
	}
	public BigDecimal getFlowRetention() {
		return flowRetention;
	}
	public void setFlowRetention(BigDecimal flowRetention) {
		this.flowRetention = flowRetention;
	}
	public YesNo getFlowCompression() {
		return flowCompression;
	}
	public void setFlowCompression(YesNo flowCompression) {
		this.flowCompression = flowCompression;
	}
	public YesNo getFlowDeCompression() {
		return flowDeCompression;
	}
	public void setFlowDeCompression(YesNo flowDeCompression) {
		this.flowDeCompression = flowDeCompression;
	}
	public String getFlowBackup() {
		return flowBackup;
	}
	public void setFlowBackup(String flowBackup) {
		this.flowBackup = flowBackup;
	}
	public YesNo getFlowInviaMail() {
		return flowInviaMail;
	}
	public void setFlowInviaMail(YesNo flowInviaMail) {
		this.flowInviaMail = flowInviaMail;
	}
	public String getFlowLetteraOk() {
		return flowLetteraOk;
	}
	public void setFlowLetteraOk(String flowLetteraOk) {
		this.flowLetteraOk = flowLetteraOk;
	}
	public String getFlowLetteraKo() {
		return flowLetteraKo;
	}
	public void setFlowLetteraKo(String flowLetteraKo) {
		this.flowLetteraKo = flowLetteraKo;
	}
	public String getFlowProgPerson() {
		return flowProgPerson;
	}
	public void setFlowProgPerson(String flowProgPerson) {
		this.flowProgPerson = flowProgPerson;
	}
	public String getFlowKnownHtFl() {
		return flowKnownHtFl;
	}
	public void setFlowKnownHtFl(String flowKnownHtFl) {
		this.flowKnownHtFl = flowKnownHtFl;
	}
	public String getFlowKeyFl() {
		return flowKeyFl;
	}
	public void setFlowKeyFl(String flowKeyFl) {
		this.flowKeyFl = flowKeyFl;
	}
	public YesNo getFlowModalitaPassiva() {
		return flowModalitaPassiva;
	}
	public void setFlowModalitaPassiva(YesNo flowModalitaPassiva) {
		this.flowModalitaPassiva = flowModalitaPassiva;
	}
	public String getFlowJobDesc() {
		return flowJobDesc;
	}
	public void setFlowJobDesc(String flowJobDesc) {
		this.flowJobDesc = flowJobDesc;
	}
	public String getFlowLibJobDesc() {
		return flowLibJobDesc;
	}
	public void setFlowLibJobDesc(String flowLibJobDesc) {
		this.flowLibJobDesc = flowLibJobDesc;
	}
	public YesNo getFlowCancellaFile() {
		return flowCancellaFile;
	}
	public void setFlowCancellaFile(YesNo flowCancellaFile) {
		this.flowCancellaFile = flowCancellaFile;
	}
	public YesNo getFlowRisottomettibile() {
		return flowRisottomettibile;
	}
	public void setFlowRisottomettibile(YesNo flowRisottomettibile) {
		this.flowRisottomettibile = flowRisottomettibile;
	}
	public BigDecimal getFlowLunghezzaFlFlat() {
		return flowLunghezzaFlFlat;
	}
	public void setFlowLunghezzaFlFlat(BigDecimal flowLunghezzaFlFlat) {
		this.flowLunghezzaFlFlat = flowLunghezzaFlFlat;
	}
	public ConnectionType getFlowTipoTrasferimento() {
		return flowTipoTrasferimento;
	}
	public void setFlowTipoTrasferimento(ConnectionType flowtipotrasferimento) {
		this.flowTipoTrasferimento = flowtipotrasferimento;
	}
	public YesNo getFlowBypassQtemp() {
		return flowBypassQtemp;
	}
	public void setFlowBypassQtemp(YesNo flowBypassQtemp) {
		this.flowBypassQtemp = flowBypassQtemp;
	}
	public YesNo getFlowEsistenzaFile() {
		return flowEsistenzaFile;
	}
	public void setFlowEsistenzaFile(YesNo flowEsistenzaFile) {
		this.flowEsistenzaFile = flowEsistenzaFile;
	}
	public String getFlowLetteraFlusso() {
		return flowLetteraFlusso;
	}
	public void setFlowLetteraFlusso(String flowletteraflusso) {
		this.flowLetteraFlusso = flowletteraflusso;
	}
	public String getFlowAggNomiCol() {
		return flowAggNomiCol;
	}
	public void setFlowAggNomiCol(String flowAggNomiCol) {
		this.flowAggNomiCol = flowAggNomiCol;
	}
	public YesNo getFlowCreaVuoto() {
		return flowCreaVuoto;
	}
	public void setFlowCreaVuoto(YesNo flowCreaVuoto) {
		this.flowCreaVuoto = flowCreaVuoto;
	}
	public String getFlowInternaz() {
		return flowInternaz;
	}
	public void setFlowInternaz(String flowInternaz) {
		this.flowInternaz = flowInternaz;
	}
	
	public String getFlowSostValNull() {
		return flowSostValNull;
	}
	public void setFlowSostValNull(String flowSostValNull) {
		this.flowSostValNull = flowSostValNull;
	}
	
	public YesNo getFlowElimNomCol() {
		return flowElimNomCol;
	}
	public void setFlowElimNomCol(YesNo flowElimNomCol) {
		this.flowElimNomCol = flowElimNomCol;
	}
	
	public YesNo getFlowFlagOkVuoto() {
		return flowFlagOkVuoto;
	}
	public void setFlowFlagOkVuoto(YesNo flowFlagOkVuoto) {
		this.flowFlagOkVuoto = flowFlagOkVuoto;
	}
	public YesNo getFlowFtpSecure() {
		return flowFtpSecure;
	}
	public void setFlowFtpSecure(YesNo flowFtpSecure) {
		this.flowFtpSecure = flowFtpSecure;
	}
	
	public String getFlowInteractiveType() {
		return flowInteractiveType;
	}
	public void setFlowInteractiveType(String flowInteractiveType) {
		this.flowInteractiveType = flowInteractiveType;
	}
	public String getFlowInteractiveProgram() {
		return flowInteractiveProgram;
	}
	public void setFlowInteractiveProgram(String flowInteractiveProgram) {
		this.flowInteractiveProgram = flowInteractiveProgram;
	}
	public YesNo getFlowInteractiveResult() {
		return flowInteractiveResult;
	}
	public void setFlowInteractiveResult(YesNo flowInteractiveResult) {
		this.flowInteractiveResult = flowInteractiveResult;
	}
	public String getFlowInteractiveCommand() {
		return flowInteractiveCommand;
	}
	public void setFlowInteractiveCommand(String flowInteractiveCommand) {
		this.flowInteractiveCommand = flowInteractiveCommand;
	}
	public BigDecimal getFlowDelaySemaforo() {
		return flowDelaySemaforo;
	}
	public void setFlowDelaySemaforo(BigDecimal flowDelaySemaforo) {
		this.flowDelaySemaforo = flowDelaySemaforo;
	}
	public YesNo getFlowHashUnico() {
		return flowHashUnico;
	}
	public void setFlowHashUnico(YesNo flowHashUnico) {
		this.flowHashUnico = flowHashUnico;
	}
	public String getFlowExportFlag() {
		return flowExportFlag;
	}
	public void setFlowExportFlag(String flowExportFlag) {
		this.flowExportFlag = flowExportFlag;
	}
	public String getFlowExportCode() {
		return flowExportCode;
	}
	public void setFlowExportCode(String flowExportCode) {
		this.flowExportCode = flowExportCode;
	}
	

	public BigDecimal getFlowFetchSize() {
		return flowFetchSize;
	}
	public void setFlowFetchSize(BigDecimal flowFetchSize) {
		this.flowFetchSize = flowFetchSize;
	}
	
	public String getFlowCharEmptySpace() {
		return flowcharemptyspace;
	}
	public void setFlowCharEmptySpace(String flowcharemptyspace) {
		this.flowcharemptyspace = flowcharemptyspace;
	}
	
	@Override
	public String toString() {
		return "Otgffana [flowId=" + flowId + ", flowDesc=" + flowDesc + ", flowGruppo=" + flowGruppo
				+ ", flowCodInterfaccia=" + flowCodInterfaccia + ", flowStato=" + flowStato + ", flowTipFlusso="
				+ flowTipFlusso + ", flowDirezione=" + flowDirezione + ", flowLibreria=" + flowLibreria
				+ ", flowFile=" + flowFile + ", flowMembro=" + flowMembro + ", flowLibSource=" + flowLibSource
				+ ", flowFileSource=" + flowFileSource + ", flowMembroSource=" + flowMembroSource
				+ ", flowFolder=" + flowFolder + ", flowFileName=" + flowFileName + ", flowFormato="
				+ flowFormato + ", flowDelimRecord=" + flowDelimRecord + ", flowDelimStringa="
				+ flowDelimStringa + ", flowRimozSpazi=" + flowRimozSpazi + ", flowDelimCampo="
				+ flowDelimCampo + ", flowRiempCampo=" + flowRiempCampo + ", flowCodepage=" + flowCodepage
				+ ", flowModAcquisizione=" + flowModAcquisizione + ", flowFromCcsid=" + flowFromCcsid
				+ ", flowPgmControllo=" + flowPgmControllo + ", flowHost=" + flowHost + ", flowPort=" + flowPort + ", flowRemoteFolder=" + flowRemoteFolder
				+ ", flowRemoteFileName=" + flowRemoteFileName + ", flowUtente=" + flowUtente
				+ ", flowPassword=" + flowPassword + ", flowUtenteSftp=" + flowUtenteSftp
				+ ", flowIntergiryCheck=" + flowIntegrityCheck + ", flowFlNameSemaforo=" + flowFlNameSemaforo
				+ ", flowNumTentaRicez=" + flowNumTentaRicez + ", flowIntervalloRetry=" + flowIntervalloRetry
				+ ", flowRetention=" + flowRetention + ", flowCompression=" + flowCompression
				+ ", flowDeCompression=" + flowDeCompression + ", flowBackup=" + flowBackup + ", flowInviaMail="
				+ flowInviaMail + ", flowLetteraOk=" + flowLetteraOk + ", flowLetteraKo=" + flowLetteraKo
				+ ", flowProgPerson=" + flowProgPerson + ", flowKnownHtFl=" + flowKnownHtFl + ", flowKeyFl="
				+ flowKeyFl + ", flowModalitaPassiva=" + flowModalitaPassiva + ", flowJobDesc=" + flowJobDesc
				+ ", flowLibJobDesc=" + flowLibJobDesc + ", flowCancellaFile=" + flowCancellaFile
				+ ", flowRisottomettibile=" + flowRisottomettibile + ", flowLunghezzaFlFlat="
				+ flowLunghezzaFlFlat + ", flowTipoTrasferimento=" + flowTipoTrasferimento
				+ ", flowBypassQtemp=" + flowBypassQtemp + ", flowEsistenzaFile=" + flowEsistenzaFile
				+ ", flowLetteraFlusso=" + flowLetteraFlusso + ", flowAggNomiCol=" + flowAggNomiCol
				+ ", flowCreaVuoto=" + flowCreaVuoto + ", flowInternaz=" + flowInternaz + ", flowSostValNull="
				+ flowSostValNull + ", flowElimNomCol=" + flowElimNomCol + ", flowFlagOkVuoto="
				+ flowFlagOkVuoto + ", flowFtpSecure=" + flowFtpSecure + ", flowInteractiveType="
				+ flowInteractiveType + ", flowInteractiveProgram=" + flowInteractiveProgram
				+ ", flowInteractiveResult=" + flowInteractiveResult + ", flowInteractiveCommand="
				+ flowInteractiveCommand + ", flowDelaySemaforo=" + flowDelaySemaforo + ", flowHashUnico="
				+ flowHashUnico + ", flowExportFlag=" + flowExportFlag + ", flowExportCode=" + flowExportCode
				+ ", flowFetchSize=" + flowFetchSize + ", flowcharemptyspace=" + flowcharemptyspace + "]";
	}
	

	
	
}
