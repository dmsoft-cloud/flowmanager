package it.dmsoft.flowmanager.agent.api.flows.model;

public class ExecutionFlowData {

	private static String[] fields = new String[] { "FANA_ID", "FANA_DESC", "FANA_GRUPPO", "FANA_COD_INTERFACCIA",
			"FANA_STATO", "FANA_TIP_FLUSSO", "FANA_DIREZIONE", "FANA_LIBRERIA", "FANA_FILE", "FANA_MEMBRO",
			"FANA_LIB_SOURCE", "FANA_FILE_SOURCE", "FANA_MEMBRO_SOURCE", "FANA_FOLDER", "FANA_FILE_NAME",
			"FANA_FORMATO", "FANA_DELIM_RECORD", "FANA_DELIM_STRINGA", "FANA_RIMOZ_SPAZI", "FANA_DELIM_CAMPO",
			"FANA_RIEMP_CAMPO", "FANA_CODEPAGE", "FANA_MOD_ACQUISIZIONE", "FANA_FROM_CCSID", "FANA_PGM_CONTROLLO",
			"FANA_TIPOLOGIA_CONN", "FANA_HOST", "FANA_PORT", "FANA_REMOTE_FOLDER", "FANA_REMOTE_FILE_NAME",
			"FANA_UTENTE", "FANA_PASSWORD", "FANA_UTENTE_SFTP", "FANA_INTEGRITY_CHECK", "FANA_FL_NAME_SEMAFORO",
			"FANA_NUM_TENTA_RICEZ", "FANA_INTERVALLO_RETRY", "FANA_RETENTION", "FANA_COMPRESSION",
			"FANA_DE_COMPRESSION", "FANA_BACKUP", "FANA_INVIA_MAIL", "FANA_LETTERA_OK", "FANA_LETTERA_KO",
			"FANA_PROG_PERSON", "FANA_KNOWN_HT_FL", "FANA_KEY_FL", "FANA_MODALITA_PASSIVA", "FANA_JOB_DESC",
			"FANA_LIB_JOB_DESC", "FANA_CANCELLA_FIL", "FANA_RISOTTOMET", "FANA_LUNGHEZZA_FL_FLAT",
			"FANA_TIPO_TRASFERIMENTO", "FANA_BYPASS_QTEMP", "FANA_ESISTENZA_FILE", "FANA_LETTERA_FLUSSO",
			"FANA_AGG_NOMI_COL", "FANA_CREA_VUOTO", "FANA_INTERNAZ", "FANA_SOST_VAL_NULL", "FANA_ELIM_NOM_COL",
			"FANA_FLAG_OK_VUOTO", "FANA_FTP_SECURE", "FANA_INTERACTIVE_TYPE", "FANA_INTERACTIVE_PROGRAM",
			"FANA_INTERACTIVE_RESULT", "FANA_INTERACTIVE_COMMAND", "FANA_DELAY_SEMAFORO", "FANA_HASH_UNICO",
			"FANA_EXPORT_FLAG", "FANA_EXPORT_CODE", "FANA_FETCH_SIZE", "FANA_CHAR_EMPTY_SPACE" };

	private String flowId;
	private String flowDesc;
	private String flowGruppo;
	private String flowCodInterfaccia;
	private String flowStato;
	private String flowTipFlusso;
	private String flowDirezione;
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
	private String flowCodepage;
	private String flowModAcquisizione;
	private String flowFromCcsid;
	private String flowPgmControllo;
	private String flowTipologiaConn;
	private String flowHost;
	private String flowPort;
	private String flowRemoteFolder;
	private String flowRemoteFileName;
	private String flowUtente;
	private String flowPassword;
	private String flowUtenteSftp;
	private String flowIntegrityCheck;
	private String flowFlNameSemaforo;
	private String flowNumTentaRicez;
	private String flowIntervalloRetry;
	private String flowRetention;
	private String flowCompression;
	private String flowDeCompression;
	private String flowBackup;
	private String flowInviaMail;
	private String flowLetteraOk;
	private String flowLetteraKo;
	private String flowProgPerson;
	private String flowKnownHtFl;
	private String flowKeyFl;
	private String flowModalitaPassiva;
	private String flowJobDesc;
	private String flowLibJobDesc;
	private String flowCancellaFil;
	private String flowRisottomet;
	private String flowLunghezzaFlFlat;
	private String flowTipoTrasferimento;
	private String flowBypassQtemp;
	private String flowEsistenzaFile;
	private String flowLetteraFlusso;
	private String flowAggNomiCol;
	private String flowCreaVuoto;
	private String flowInternaz;
	private String flowSostValNull;
	private String flowElimNomCol;
	private String flowFlagOkVuoto;
	private String flowFtpSecure;
	private String flowInteractiveType;
	private String flowInteractiveProgram;
	private String flowInteractiveResult;
	private String flowInteractiveCommand;
	private String flowDelaySemaforo;
	private String flowHashUnico;
	private String flowExportFlag;
	private String flowExportCode;
	private String flowFetchSize;
	private String flowCharEmptySpace;
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
	public String getFlowStato() {
		return flowStato;
	}
	public void setFlowStato(String flowStato) {
		this.flowStato = flowStato;
	}
	public String getFlowTipFlusso() {
		return flowTipFlusso;
	}
	public void setFlowTipFlusso(String flowTipFlusso) {
		this.flowTipFlusso = flowTipFlusso;
	}
	public String getFlowDirezione() {
		return flowDirezione;
	}
	public void setFlowDirezione(String flowDirezione) {
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
	public String getFlowCodepage() {
		return flowCodepage;
	}
	public void setFlowCodepage(String flowCodepage) {
		this.flowCodepage = flowCodepage;
	}
	public String getFlowModAcquisizione() {
		return flowModAcquisizione;
	}
	public void setFlowModAcquisizione(String flowModAcquisizione) {
		this.flowModAcquisizione = flowModAcquisizione;
	}
	public String getFlowFromCcsid() {
		return flowFromCcsid;
	}
	public void setFlowFromCcsid(String flowFromCcsid) {
		this.flowFromCcsid = flowFromCcsid;
	}
	public String getFlowPgmControllo() {
		return flowPgmControllo;
	}
	public void setFlowPgmControllo(String flowPgmControllo) {
		this.flowPgmControllo = flowPgmControllo;
	}
	public String getFlowTipologiaConn() {
		return flowTipologiaConn;
	}
	public void setFlowTipologiaConn(String flowTipologiaConn) {
		this.flowTipologiaConn = flowTipologiaConn;
	}
	public String getFlowHost() {
		return flowHost;
	}
	public void setFlowHost(String flowHost) {
		this.flowHost = flowHost;
	}
	public String getFlowPort() {
		return flowPort;
	}
	public void setFlowPort(String flowPort) {
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
	public String getFlowIntegrityCheck() {
		return flowIntegrityCheck;
	}
	public void setFlowIntegrityCheck(String flowIntegrityCheck) {
		this.flowIntegrityCheck = flowIntegrityCheck;
	}
	public String getFlowFlNameSemaforo() {
		return flowFlNameSemaforo;
	}
	public void setFlowFlNameSemaforo(String flowFlNameSemaforo) {
		this.flowFlNameSemaforo = flowFlNameSemaforo;
	}
	public String getFlowNumTentaRicez() {
		return flowNumTentaRicez;
	}
	public void setFlowNumTentaRicez(String flowNumTentaRicez) {
		this.flowNumTentaRicez = flowNumTentaRicez;
	}
	public String getFlowIntervalloRetry() {
		return flowIntervalloRetry;
	}
	public void setFlowIntervalloRetry(String flowIntervalloRetry) {
		this.flowIntervalloRetry = flowIntervalloRetry;
	}
	public String getFlowRetention() {
		return flowRetention;
	}
	public void setFlowRetention(String flowRetention) {
		this.flowRetention = flowRetention;
	}
	public String getFlowCompression() {
		return flowCompression;
	}
	public void setFlowCompression(String flowCompression) {
		this.flowCompression = flowCompression;
	}
	public String getFlowDeCompression() {
		return flowDeCompression;
	}
	public void setFlowDeCompression(String flowDeCompression) {
		this.flowDeCompression = flowDeCompression;
	}
	public String getFlowBackup() {
		return flowBackup;
	}
	public void setFlowBackup(String flowBackup) {
		this.flowBackup = flowBackup;
	}
	public String getFlowInviaMail() {
		return flowInviaMail;
	}
	public void setFlowInviaMail(String flowInviaMail) {
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
	public String getFlowModalitaPassiva() {
		return flowModalitaPassiva;
	}
	public void setFlowModalitaPassiva(String flowModalitaPassiva) {
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
	public String getFlowCancellaFil() {
		return flowCancellaFil;
	}
	public void setFlowCancellaFil(String flowCancellaFil) {
		this.flowCancellaFil = flowCancellaFil;
	}
	public String getFlowRisottomet() {
		return flowRisottomet;
	}
	public void setFlowRisottomet(String flowRisottomet) {
		this.flowRisottomet = flowRisottomet;
	}
	public String getFlowLunghezzaFlFlat() {
		return flowLunghezzaFlFlat;
	}
	public void setFlowLunghezzaFlFlat(String flowLunghezzaFlFlat) {
		this.flowLunghezzaFlFlat = flowLunghezzaFlFlat;
	}
	public String getFlowTipoTrasferimento() {
		return flowTipoTrasferimento;
	}
	public void setFlowTipoTrasferimento(String flowTipoTrasferimento) {
		this.flowTipoTrasferimento = flowTipoTrasferimento;
	}
	public String getFlowBypassQtemp() {
		return flowBypassQtemp;
	}
	public void setFlowBypassQtemp(String flowBypassQtemp) {
		this.flowBypassQtemp = flowBypassQtemp;
	}
	public String getFlowEsistenzaFile() {
		return flowEsistenzaFile;
	}
	public void setFlowEsistenzaFile(String flowEsistenzaFile) {
		this.flowEsistenzaFile = flowEsistenzaFile;
	}
	public String getFlowLetteraFlusso() {
		return flowLetteraFlusso;
	}
	public void setFlowLetteraFlusso(String flowLetteraFlusso) {
		this.flowLetteraFlusso = flowLetteraFlusso;
	}
	public String getFlowAggNomiCol() {
		return flowAggNomiCol;
	}
	public void setFlowAggNomiCol(String flowAggNomiCol) {
		this.flowAggNomiCol = flowAggNomiCol;
	}
	public String getFlowCreaVuoto() {
		return flowCreaVuoto;
	}
	public void setFlowCreaVuoto(String flowCreaVuoto) {
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
	public String getFlowElimNomCol() {
		return flowElimNomCol;
	}
	public void setFlowElimNomCol(String flowElimNomCol) {
		this.flowElimNomCol = flowElimNomCol;
	}
	public String getFlowFlagOkVuoto() {
		return flowFlagOkVuoto;
	}
	public void setFlowFlagOkVuoto(String flowFlagOkVuoto) {
		this.flowFlagOkVuoto = flowFlagOkVuoto;
	}
	public String getFlowFtpSecure() {
		return flowFtpSecure;
	}
	public void setFlowFtpSecure(String flowFtpSecure) {
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
	public String getFlowInteractiveResult() {
		return flowInteractiveResult;
	}
	public void setFlowInteractiveResult(String flowInteractiveResult) {
		this.flowInteractiveResult = flowInteractiveResult;
	}
	public String getFlowInteractiveCommand() {
		return flowInteractiveCommand;
	}
	public void setFlowInteractiveCommand(String flowInteractiveCommand) {
		this.flowInteractiveCommand = flowInteractiveCommand;
	}
	public String getFlowDelaySemaforo() {
		return flowDelaySemaforo;
	}
	public void setFlowDelaySemaforo(String flowDelaySemaforo) {
		this.flowDelaySemaforo = flowDelaySemaforo;
	}
	public String getFlowHashUnico() {
		return flowHashUnico;
	}
	public void setFlowHashUnico(String flowHashUnico) {
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
	public String getFlowFetchSize() {
		return flowFetchSize;
	}
	public void setFlowFetchSize(String flowFetchSize) {
		this.flowFetchSize = flowFetchSize;
	}
	public String getFlowCharEmptySpace() {
		return flowCharEmptySpace;
	}
	public void setFlowCharEmptySpace(String flowCharEmptySpace) {
		this.flowCharEmptySpace = flowCharEmptySpace;
	}

}
