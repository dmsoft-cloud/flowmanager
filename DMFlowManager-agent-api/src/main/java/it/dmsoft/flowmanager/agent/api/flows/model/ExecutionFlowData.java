package it.dmsoft.flowmanager.agent.api.flows.model;

public class ExecutionFlowData {

	private static String[] pippo = new String[] { "FANA_ID", "FANA_DESC", "FANA_GRUPPO", "FANA_COD_INTERFACCIA",
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

	private String fanaId;
	private String fanaDesc;
	private String fanaGruppo;
	private String fanaCodInterfaccia;
	private String fanaStato;
	private String fanaTipFlusso;
	private String fanaDirezione;
	private String fanaLibreria;
	private String fanaFile;
	private String fanaMembro;
	private String fanaLibSource;
	private String fanaFileSource;
	private String fanaMembroSource;
	private String fanaFolder;
	private String fanaFileName;
	private String fanaFormato;
	private String fanaDelimRecord;
	private String fanaDelimStringa;
	private String fanaRimozSpazi;
	private String fanaDelimCampo;
	private String fanaRiempCampo;
	private String fanaCodepage;
	private String fanaModAcquisizione;
	private String fanaFromCcsid;
	private String fanaPgmControllo;
	private String fanaTipologiaConn;
	private String fanaHost;
	private String fanaPort;
	private String fanaRemoteFolder;
	private String fanaRemoteFileName;
	private String fanaUtente;
	private String fanaPassword;
	private String fanaUtenteSftp;
	private String fanaIntegrityCheck;
	private String fanaFlNameSemaforo;
	private String fanaNumTentaRicez;
	private String fanaIntervalloRetry;
	private String fanaRetention;
	private String fanaCompression;
	private String fanaDeCompression;
	private String fanaBackup;
	private String fanaInviaMail;
	private String fanaLetteraOk;
	private String fanaLetteraKo;
	private String fanaProgPerson;
	private String fanaKnownHtFl;
	private String fanaKeyFl;
	private String fanaModalitaPassiva;
	private String fanaJobDesc;
	private String fanaLibJobDesc;
	private String fanaCancellaFil;
	private String fanaRisottomet;
	private String fanaLunghezzaFlFlat;
	private String fanaTipoTrasferimento;
	private String fanaBypassQtemp;
	private String fanaEsistenzaFile;
	private String fanaLetteraFlusso;
	private String fanaAggNomiCol;
	private String fanaCreaVuoto;
	private String fanaInternaz;
	private String fanaSostValNull;
	private String fanaElimNomCol;
	private String fanaFlagOkVuoto;
	private String fanaFtpSecure;
	private String fanaInteractiveType;
	private String fanaInteractiveProgram;
	private String fanaInteractiveResult;
	private String fanaInteractiveCommand;
	private String fanaDelaySemaforo;
	private String fanaHashUnico;
	private String fanaExportFlag;
	private String fanaExportCode;
	private String fanaFetchSize;
	private String fanaCharEmptySpace;

	public static String[] getPippo() {
		return pippo;
	}

	public static void setPippo(String[] pippo) {
		ExecutionFlowData.pippo = pippo;
	}

	public String getFanaId() {
		return fanaId;
	}

	public void setFanaId(String fanaId) {
		this.fanaId = fanaId;
	}

	public String getFanaDesc() {
		return fanaDesc;
	}

	public void setFanaDesc(String fanaDesc) {
		this.fanaDesc = fanaDesc;
	}

	public String getFanaGruppo() {
		return fanaGruppo;
	}

	public void setFanaGruppo(String fanaGruppo) {
		this.fanaGruppo = fanaGruppo;
	}

	public String getFanaCodInterfaccia() {
		return fanaCodInterfaccia;
	}

	public void setFanaCodInterfaccia(String fanaCodInterfaccia) {
		this.fanaCodInterfaccia = fanaCodInterfaccia;
	}

	public String getFanaStato() {
		return fanaStato;
	}

	public void setFanaStato(String fanaStato) {
		this.fanaStato = fanaStato;
	}

	public String getFanaTipFlusso() {
		return fanaTipFlusso;
	}

	public void setFanaTipFlusso(String fanaTipFlusso) {
		this.fanaTipFlusso = fanaTipFlusso;
	}

	public String getFanaDirezione() {
		return fanaDirezione;
	}

	public void setFanaDirezione(String fanaDirezione) {
		this.fanaDirezione = fanaDirezione;
	}

	public String getFanaLibreria() {
		return fanaLibreria;
	}

	public void setFanaLibreria(String fanaLibreria) {
		this.fanaLibreria = fanaLibreria;
	}

	public String getFanaFile() {
		return fanaFile;
	}

	public void setFanaFile(String fanaFile) {
		this.fanaFile = fanaFile;
	}

	public String getFanaMembro() {
		return fanaMembro;
	}

	public void setFanaMembro(String fanaMembro) {
		this.fanaMembro = fanaMembro;
	}

	public String getFanaLibSource() {
		return fanaLibSource;
	}

	public void setFanaLibSource(String fanaLibSource) {
		this.fanaLibSource = fanaLibSource;
	}

	public String getFanaFileSource() {
		return fanaFileSource;
	}

	public void setFanaFileSource(String fanaFileSource) {
		this.fanaFileSource = fanaFileSource;
	}

	public String getFanaMembroSource() {
		return fanaMembroSource;
	}

	public void setFanaMembroSource(String fanaMembroSource) {
		this.fanaMembroSource = fanaMembroSource;
	}

	public String getFanaFolder() {
		return fanaFolder;
	}

	public void setFanaFolder(String fanaFolder) {
		this.fanaFolder = fanaFolder;
	}

	public String getFanaFileName() {
		return fanaFileName;
	}

	public void setFanaFileName(String fanaFileName) {
		this.fanaFileName = fanaFileName;
	}

	public String getFanaFormato() {
		return fanaFormato;
	}

	public void setFanaFormato(String fanaFormato) {
		this.fanaFormato = fanaFormato;
	}

	public String getFanaDelimRecord() {
		return fanaDelimRecord;
	}

	public void setFanaDelimRecord(String fanaDelimRecord) {
		this.fanaDelimRecord = fanaDelimRecord;
	}

	public String getFanaDelimStringa() {
		return fanaDelimStringa;
	}

	public void setFanaDelimStringa(String fanaDelimStringa) {
		this.fanaDelimStringa = fanaDelimStringa;
	}

	public String getFanaRimozSpazi() {
		return fanaRimozSpazi;
	}

	public void setFanaRimozSpazi(String fanaRimozSpazi) {
		this.fanaRimozSpazi = fanaRimozSpazi;
	}

	public String getFanaDelimCampo() {
		return fanaDelimCampo;
	}

	public void setFanaDelimCampo(String fanaDelimCampo) {
		this.fanaDelimCampo = fanaDelimCampo;
	}

	public String getFanaRiempCampo() {
		return fanaRiempCampo;
	}

	public void setFanaRiempCampo(String fanaRiempCampo) {
		this.fanaRiempCampo = fanaRiempCampo;
	}

	public String getFanaCodepage() {
		return fanaCodepage;
	}

	public void setFanaCodepage(String fanaCodepage) {
		this.fanaCodepage = fanaCodepage;
	}

	public String getFanaModAcquisizione() {
		return fanaModAcquisizione;
	}

	public void setFanaModAcquisizione(String fanaModAcquisizione) {
		this.fanaModAcquisizione = fanaModAcquisizione;
	}

	public String getFanaFromCcsid() {
		return fanaFromCcsid;
	}

	public void setFanaFromCcsid(String fanaFromCcsid) {
		this.fanaFromCcsid = fanaFromCcsid;
	}

	public String getFanaPgmControllo() {
		return fanaPgmControllo;
	}

	public void setFanaPgmControllo(String fanaPgmControllo) {
		this.fanaPgmControllo = fanaPgmControllo;
	}

	public String getFanaTipologiaConn() {
		return fanaTipologiaConn;
	}

	public void setFanaTipologiaConn(String fanaTipologiaConn) {
		this.fanaTipologiaConn = fanaTipologiaConn;
	}

	public String getFanaHost() {
		return fanaHost;
	}

	public void setFanaHost(String fanaHost) {
		this.fanaHost = fanaHost;
	}

	public String getFanaPort() {
		return fanaPort;
	}

	public void setFanaPort(String fanaPort) {
		this.fanaPort = fanaPort;
	}

	public String getFanaRemoteFolder() {
		return fanaRemoteFolder;
	}

	public void setFanaRemoteFolder(String fanaRemoteFolder) {
		this.fanaRemoteFolder = fanaRemoteFolder;
	}

	public String getFanaRemoteFileName() {
		return fanaRemoteFileName;
	}

	public void setFanaRemoteFileName(String fanaRemoteFileName) {
		this.fanaRemoteFileName = fanaRemoteFileName;
	}

	public String getFanaUtente() {
		return fanaUtente;
	}

	public void setFanaUtente(String fanaUtente) {
		this.fanaUtente = fanaUtente;
	}

	public String getFanaPassword() {
		return fanaPassword;
	}

	public void setFanaPassword(String fanaPassword) {
		this.fanaPassword = fanaPassword;
	}

	public String getFanaUtenteSftp() {
		return fanaUtenteSftp;
	}

	public void setFanaUtenteSftp(String fanaUtenteSftp) {
		this.fanaUtenteSftp = fanaUtenteSftp;
	}

	public String getFanaIntegrityCheck() {
		return fanaIntegrityCheck;
	}

	public void setFanaIntegrityCheck(String fanaIntegrityCheck) {
		this.fanaIntegrityCheck = fanaIntegrityCheck;
	}

	public String getFanaFlNameSemaforo() {
		return fanaFlNameSemaforo;
	}

	public void setFanaFlNameSemaforo(String fanaFlNameSemaforo) {
		this.fanaFlNameSemaforo = fanaFlNameSemaforo;
	}

	public String getFanaNumTentaRicez() {
		return fanaNumTentaRicez;
	}

	public void setFanaNumTentaRicez(String fanaNumTentaRicez) {
		this.fanaNumTentaRicez = fanaNumTentaRicez;
	}

	public String getFanaIntervalloRetry() {
		return fanaIntervalloRetry;
	}

	public void setFanaIntervalloRetry(String fanaIntervalloRetry) {
		this.fanaIntervalloRetry = fanaIntervalloRetry;
	}

	public String getFanaRetention() {
		return fanaRetention;
	}

	public void setFanaRetention(String fanaRetention) {
		this.fanaRetention = fanaRetention;
	}

	public String getFanaCompression() {
		return fanaCompression;
	}

	public void setFanaCompression(String fanaCompression) {
		this.fanaCompression = fanaCompression;
	}

	public String getFanaDeCompression() {
		return fanaDeCompression;
	}

	public void setFanaDeCompression(String fanaDeCompression) {
		this.fanaDeCompression = fanaDeCompression;
	}

	public String getFanaBackup() {
		return fanaBackup;
	}

	public void setFanaBackup(String fanaBackup) {
		this.fanaBackup = fanaBackup;
	}

	public String getFanaInviaMail() {
		return fanaInviaMail;
	}

	public void setFanaInviaMail(String fanaInviaMail) {
		this.fanaInviaMail = fanaInviaMail;
	}

	public String getFanaLetteraOk() {
		return fanaLetteraOk;
	}

	public void setFanaLetteraOk(String fanaLetteraOk) {
		this.fanaLetteraOk = fanaLetteraOk;
	}

	public String getFanaLetteraKo() {
		return fanaLetteraKo;
	}

	public void setFanaLetteraKo(String fanaLetteraKo) {
		this.fanaLetteraKo = fanaLetteraKo;
	}

	public String getFanaProgPerson() {
		return fanaProgPerson;
	}

	public void setFanaProgPerson(String fanaProgPerson) {
		this.fanaProgPerson = fanaProgPerson;
	}

	public String getFanaKnownHtFl() {
		return fanaKnownHtFl;
	}

	public void setFanaKnownHtFl(String fanaKnownHtFl) {
		this.fanaKnownHtFl = fanaKnownHtFl;
	}

	public String getFanaKeyFl() {
		return fanaKeyFl;
	}

	public void setFanaKeyFl(String fanaKeyFl) {
		this.fanaKeyFl = fanaKeyFl;
	}

	public String getFanaModalitaPassiva() {
		return fanaModalitaPassiva;
	}

	public void setFanaModalitaPassiva(String fanaModalitaPassiva) {
		this.fanaModalitaPassiva = fanaModalitaPassiva;
	}

	public String getFanaJobDesc() {
		return fanaJobDesc;
	}

	public void setFanaJobDesc(String fanaJobDesc) {
		this.fanaJobDesc = fanaJobDesc;
	}

	public String getFanaLibJobDesc() {
		return fanaLibJobDesc;
	}

	public void setFanaLibJobDesc(String fanaLibJobDesc) {
		this.fanaLibJobDesc = fanaLibJobDesc;
	}

	public String getFanaCancellaFil() {
		return fanaCancellaFil;
	}

	public void setFanaCancellaFil(String fanaCancellaFil) {
		this.fanaCancellaFil = fanaCancellaFil;
	}

	public String getFanaRisottomet() {
		return fanaRisottomet;
	}

	public void setFanaRisottomet(String fanaRisottomet) {
		this.fanaRisottomet = fanaRisottomet;
	}

	public String getFanaLunghezzaFlFlat() {
		return fanaLunghezzaFlFlat;
	}

	public void setFanaLunghezzaFlFlat(String fanaLunghezzaFlFlat) {
		this.fanaLunghezzaFlFlat = fanaLunghezzaFlFlat;
	}

	public String getFanaTipoTrasferimento() {
		return fanaTipoTrasferimento;
	}

	public void setFanaTipoTrasferimento(String fanaTipoTrasferimento) {
		this.fanaTipoTrasferimento = fanaTipoTrasferimento;
	}

	public String getFanaBypassQtemp() {
		return fanaBypassQtemp;
	}

	public void setFanaBypassQtemp(String fanaBypassQtemp) {
		this.fanaBypassQtemp = fanaBypassQtemp;
	}

	public String getFanaEsistenzaFile() {
		return fanaEsistenzaFile;
	}

	public void setFanaEsistenzaFile(String fanaEsistenzaFile) {
		this.fanaEsistenzaFile = fanaEsistenzaFile;
	}

	public String getFanaLetteraFlusso() {
		return fanaLetteraFlusso;
	}

	public void setFanaLetteraFlusso(String fanaLetteraFlusso) {
		this.fanaLetteraFlusso = fanaLetteraFlusso;
	}

	public String getFanaAggNomiCol() {
		return fanaAggNomiCol;
	}

	public void setFanaAggNomiCol(String fanaAggNomiCol) {
		this.fanaAggNomiCol = fanaAggNomiCol;
	}

	public String getFanaCreaVuoto() {
		return fanaCreaVuoto;
	}

	public void setFanaCreaVuoto(String fanaCreaVuoto) {
		this.fanaCreaVuoto = fanaCreaVuoto;
	}

	public String getFanaInternaz() {
		return fanaInternaz;
	}

	public void setFanaInternaz(String fanaInternaz) {
		this.fanaInternaz = fanaInternaz;
	}

	public String getFanaSostValNull() {
		return fanaSostValNull;
	}

	public void setFanaSostValNull(String fanaSostValNull) {
		this.fanaSostValNull = fanaSostValNull;
	}

	public String getFanaElimNomCol() {
		return fanaElimNomCol;
	}

	public void setFanaElimNomCol(String fanaElimNomCol) {
		this.fanaElimNomCol = fanaElimNomCol;
	}

	public String getFanaFlagOkVuoto() {
		return fanaFlagOkVuoto;
	}

	public void setFanaFlagOkVuoto(String fanaFlagOkVuoto) {
		this.fanaFlagOkVuoto = fanaFlagOkVuoto;
	}

	public String getFanaFtpSecure() {
		return fanaFtpSecure;
	}

	public void setFanaFtpSecure(String fanaFtpSecure) {
		this.fanaFtpSecure = fanaFtpSecure;
	}

	public String getFanaInteractiveType() {
		return fanaInteractiveType;
	}

	public void setFanaInteractiveType(String fanaInteractiveType) {
		this.fanaInteractiveType = fanaInteractiveType;
	}

	public String getFanaInteractiveProgram() {
		return fanaInteractiveProgram;
	}

	public void setFanaInteractiveProgram(String fanaInteractiveProgram) {
		this.fanaInteractiveProgram = fanaInteractiveProgram;
	}

	public String getFanaInteractiveResult() {
		return fanaInteractiveResult;
	}

	public void setFanaInteractiveResult(String fanaInteractiveResult) {
		this.fanaInteractiveResult = fanaInteractiveResult;
	}

	public String getFanaInteractiveCommand() {
		return fanaInteractiveCommand;
	}

	public void setFanaInteractiveCommand(String fanaInteractiveCommand) {
		this.fanaInteractiveCommand = fanaInteractiveCommand;
	}

	public String getFanaDelaySemaforo() {
		return fanaDelaySemaforo;
	}

	public void setFanaDelaySemaforo(String fanaDelaySemaforo) {
		this.fanaDelaySemaforo = fanaDelaySemaforo;
	}

	public String getFanaHashUnico() {
		return fanaHashUnico;
	}

	public void setFanaHashUnico(String fanaHashUnico) {
		this.fanaHashUnico = fanaHashUnico;
	}

	public String getFanaExportFlag() {
		return fanaExportFlag;
	}

	public void setFanaExportFlag(String fanaExportFlag) {
		this.fanaExportFlag = fanaExportFlag;
	}

	public String getFanaExportCode() {
		return fanaExportCode;
	}

	public void setFanaExportCode(String fanaExportCode) {
		this.fanaExportCode = fanaExportCode;
	}

	public String getFanaFetchSize() {
		return fanaFetchSize;
	}

	public void setFanaFetchSize(String fanaFetchSize) {
		this.fanaFetchSize = fanaFetchSize;
	}

	public String getFanaCharEmptySpace() {
		return fanaCharEmptySpace;
	}

	public void setFanaCharEmptySpace(String fanaCharEmptySpace) {
		this.fanaCharEmptySpace = fanaCharEmptySpace;
	}

}
