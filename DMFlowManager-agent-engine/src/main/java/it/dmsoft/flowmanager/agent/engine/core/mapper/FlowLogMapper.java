package it.dmsoft.flowmanager.agent.engine.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import it.dmsoft.flowmanager.agent.engine.core.model.ExecutionFlowData;
import it.dmsoft.flowmanager.be.entities.FlowConfig;
import it.dmsoft.flowmanager.be.entities.FlowLog;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.WARN)
public interface FlowLogMapper {
	
	//DA AGGIUNGERE
	//trustHost
	//model.database
	//file dei parametri generali: path backup generale
    
	@Mapping(source = "flowConfig.transactionName", target = "logId")
	FlowLog convert(FlowConfig flowConfig);
	
    //OK
	@Mapping(source = "executionFlowData.flowId", target = "logId")
	//OK
	//@Mapping(source = "executionFlowData.flowDesc", target = "fullFlowData.flow.description")
	//OK
	//@Mapping(source = "executionFlowData.flowGruppo", target = "fullFlowData.group.id")
    //OK
	@Mapping(source = "executionFlowData.flowCodInterfaccia", target = "logCodInterfaccia")
	//OK
	@Mapping(source = "executionFlowData.flowStato", target = "logStato")
    //OK
	@Mapping(source = "executionFlowData.flowTipFlusso", target = "logTipFlusso")
	//OK
	@Mapping(source = "executionFlowData.flowDirezione", target = "logDirezione")
	//OK
	@Mapping(source = "executionFlowData.flowLibreria", target = "logLibreria")
	//AL MOMENTO NON IMPLEMENTATO
	@Mapping(source = "executionFlowData.flowFile", target = "logFile")
	//AL MOMENTO NON IMPLEMENTATO
	@Mapping(source = "executionFlowData.flowMembro", target = "logMembro")
	//AL MOMENTO NON IMPLEMENTATO
	@Mapping(source = "executionFlowData.flowLibSource", target = "logLibSource")
	//AL MOMENTO NON IMPLEMENTATO
	@Mapping(source = "executionFlowData.flowFileSource", target = "logFileSource")
	//AL MOMENTO NON IMPLEMENTATO
	@Mapping(source = "executionFlowData.flowMembroSource", target = "logMembroSource")
	//OK
	@Mapping(source = "executionFlowData.flowFolder", target = "logFolder")
	//OK
	@Mapping(source = "executionFlowData.flowFileName", target = "logFileName")
    //OK
	@Mapping(source = "executionFlowData.flowFormato", target = "logFormato")
    //OK
	@Mapping(source = "executionFlowData.flowDelimRecord", target = "logDelimRecord")
    //OK
	//@Mapping(source = "executionFlowData.flowDelimStringa", target = "fullFlowData.model.stringDelimiter")
    //OK
	//@Mapping(source = "executionFlowData.flowRimozSpazi", target = "fullFlowData.model.removingSpaces")
    //OK
	//@Mapping(source = "executionFlowData.flowDelimCampo", target = "logDelimCampo")
    //OK
	//@Mapping(source = "executionFlowData.flowRiempCampo", target = "fullFlowData.model.numericFilling")
    //OK
	@Mapping(source = "executionFlowData.flowCodepage", target = "logCodepage")
	//DA AGGIUNGERE NEL MODEL (da mettere anche a video)
	@Mapping(source = "executionFlowData.flowModAcquisizione", target = "logModAcquiszione")
    //OK
	//@Mapping(source = "executionFlowData.flowFromCcsid", target = "fullFlowData.model.sourceCharset")
	//AL MOMENTO NON IMPLEMENTATO
	@Mapping(source = "executionFlowData.flowPgmControllo", target = "logPgmControllo")
    //OK
	@Mapping(source = "executionFlowData.flowTipologiaConn", target = "logTipologiaConn")
	//OK
    @Mapping(source = "executionFlowData.flowHost", target = "logHost")
	//OK
    @Mapping(source = "executionFlowData.flowPort", target = "logPort")
	//OK
	@Mapping(source = "executionFlowData.flowRemoteFolder", target = "logRemoteFolder")
	//OK
	@Mapping(source = "executionFlowData.flowRemoteFileName", target = "logRemoteFileName")
    //OK
	@Mapping(source = "executionFlowData.flowUtente", target = "logUtente")
    //OK
	@Mapping(source = "executionFlowData.flowPassword", target = "logPassword")
	//OK
	@Mapping(source = "executionFlowData.flowUtenteSftp", target = "logUtenteSftp")
    //OK
	@Mapping(source = "executionFlowData.flowIntegrityCheck", target = "logIntegrityCheck")
	//OK
	@Mapping(source = "executionFlowData.flowFlNameSemaforo", target = "logFlNameSemaforo")
    //OK
	@Mapping(source = "executionFlowData.flowNumTentaRicez", target = "logNumTentaRicez")
    //OK
	@Mapping(source = "executionFlowData.flowIntervalloRetry", target = "logIntervalloRetry")
    //OK
	@Mapping(source = "executionFlowData.flowRetention", target = "logRetention")
    //OK
	@Mapping(source = "executionFlowData.flowCompression", target = "logCompression")
    //OK
	//@Mapping(source = "executionFlowData.flowDeCompression", target = "fullFlowData.model.decompression")
	//DA AGGIUNGERE sia nei parametri generali sia nel flow
	@Mapping(source = "executionFlowData.flowBackup", target = "logBackup")
    //OK
	@Mapping(source = "executionFlowData.flowInviaMail", target = "logInviaMail")
	//OK
	@Mapping(source = "executionFlowData.flowLetteraOk", target = "logLetteraOk")
	//OK
	@Mapping(source = "executionFlowData.flowLetteraKo", target = "logLetteraKo")
	//AL MOMENTO NON IMPLEMENTATO
	//@Mapping(source = "executionFlowData.flowProgPerson", target = "")
    //OK
	//@Mapping(source = "executionFlowData.flowKnownHtFl", target = "fullFlowData.interface.knownHost")
    //OK
	//@Mapping(source = "executionFlowData.flowKeyFl", target = "fullFlowData.interface.keyFile")
    //OK
	//@Mapping(source = "executionFlowData.flowModalitaPassiva", target = "fullFlowData.interface.passiveMode")
	//AL MOMENTO NON IMPLEMENTATO
	//@Mapping(source = "executionFlowData.flowJobDesc", target = "")
	//AL MOMENTO NON IMPLEMENTATO
	//@Mapping(source = "executionFlowData.flowLibJobDesc", target = "")
    //OK
	//@Mapping(source = "executionFlowData.flowCancellaFil", target = "fullFlowData.model.deleteFile")
	//AL MOMENTO NON IMPLEMENTATO
	//@Mapping(source = "executionFlowData.flowRisottomet", target = "")
	//OK
	//@Mapping(source = "executionFlowData.flowLunghezzaFlFlat", target = "fullFlowData.flow.lengthFixed")
	//OK
	@Mapping(source = "executionFlowData.flowTipoTrasferimento", target = "logTipoTrasferimento")
	//AL MOMENTO NON IMPLEMENTATO
	@Mapping(source = "executionFlowData.flowBypassQtemp", target = "logBypassQtemp")
	//AL MOMENTO NON IMPLEMENTATO
	@Mapping(source = "executionFlowData.flowEsistenzaFile", target = "logEsistenzaFile")
	///OK
	@Mapping(source = "executionFlowData.flowLetteraFlusso", target = "logLetteraFlusso")
    //OK
	//@Mapping(source = "executionFlowData.flowAggNomiCol", target = "")
    //OK
	//@Mapping(source = "executionFlowData.flowCreaVuoto", target = "")
    //OK
	@Mapping(source = "executionFlowData.flowInternaz", target = "logInternaz")
	//AL MOMENTO NON PARAMETRIZZATO MA IMPLEMENTATO
	@Mapping(source = "executionFlowData.flowSostValNull", target = "logSostValNull")
	//AL MOMENTO NON PARAMETRIZZATO MA IMPLEMENTATO
	@Mapping(source = "executionFlowData.flowElimNomCol", target = "logElimNomCol")
	//AL MOMENTO NON PARAMETRIZZATO MA IMPLEMENTATO
	@Mapping(source = "executionFlowData.flowFlagOkVuoto", target = "logFlagOkVuoto")
    //OK
	@Mapping(source = "executionFlowData.flowFtpSecure", target = "logFtpSecure")
	//AL MOMENTO NON IMPLEMENTATO
	@Mapping(source = "executionFlowData.flowInteractiveType", target = "logInteractiveType")
	//AL MOMENTO NON IMPLEMENTATO
	@Mapping(source = "executionFlowData.flowInteractiveProgram", target = "logInteractiveProgram")
	//AL MOMENTO NON IMPLEMENTATO
	@Mapping(source = "executionFlowData.flowInteractiveResult", target = "logInteractiveResult")
	//AL MOMENTO NON IMPLEMENTATO
	@Mapping(source = "executionFlowData.flowInteractiveCommand", target = "logInteractiveCommand")
	//AL MOMENTO NON PARAMETRIZZATO MA IMPLEMENTATO
	@Mapping(source = "executionFlowData.flowDelaySemaforo", target = "logDelaySemaforo")
    //OK
	@Mapping(source = "executionFlowData.flowHashUnico", target = "logHashUnico")
	//AL MOMENTO NON PARAMETRIZZATO MA IMPLEMENTATO (utile ordinamento colonne)
	@Mapping(source = "executionFlowData.flowExportFlag", target = "logExportFlag")
	//AL MOMENTO NON PARAMETRIZZATO MA IMPLEMENTATO
	@Mapping(source = "executionFlowData.flowExportCode", target = "logExportCode")
    //OK
	@Mapping(source = "executionFlowData.flowFetchSize", target = "logFetchSize")
	//AL MOMENTO NON PARAMETRIZZATO MA IMPLEMENTATO
	@Mapping(source = "executionFlowData.flowCharEmptySpace", target = "logCharEmptySpace")
	FlowLog convert(ExecutionFlowData executionFlowData);

}
