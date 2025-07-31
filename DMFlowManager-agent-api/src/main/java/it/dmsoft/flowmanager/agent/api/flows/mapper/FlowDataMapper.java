package it.dmsoft.flowmanager.agent.api.flows.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import it.dmsoft.flowmanager.agent.engine.core.model.ExecutionFlowData;
import it.dmsoft.flowmanager.common.model.FullFlowData;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.WARN)
public interface FlowDataMapper {
	
	//DA AGGIUNGERE
	//trustHost
	//model.database
	//file dei parametri generali: path backup generale
    

    //OK
	@Mapping(target = "flowId", source = "fullFlowData.flow.id")
	//OK
	@Mapping(target = "flowDesc", source = "fullFlowData.flow.description")
	//OK
	@Mapping(target = "flowGruppo", source = "fullFlowData.group.id")
    //OK
	@Mapping(target = "flowCodInterfaccia", source = "fullFlowData._interface.id")
	//OK
	@Mapping(target = "flowStato", source = "fullFlowData.flow.enabled")
    //OK
	@Mapping(target = "flowTipFlusso", source = "fullFlowData.model.type")
	//OK
	@Mapping(target = "flowDirezione", source = "fullFlowData.model.direction")
	//OK
	@Mapping(target = "flowLibreria", source = "fullFlowData.model.schema")
	//OK
	@Mapping(target = "flowFile", source = "fullFlowData.flow.dbTable")
	//AL MOMENTO NON IMPLEMENTATO
	//@Mapping(target = "flowMembro", source = "")
	//AL MOMENTO NON IMPLEMENTATO
	//@Mapping(target = "flowLibSource", source = "")
	//AL MOMENTO NON IMPLEMENTATO
	//@Mapping(target = "flowFileSource", source = "")
	//AL MOMENTO NON IMPLEMENTATO
	//@Mapping(target = "flowMembroSource", source = "")
	//OK
	@Mapping(target = "flowFolder", source = "fullFlowData.flow.folder")
	//OK
	@Mapping(target = "flowFileName", source = "fullFlowData.flow.file")
    //OK
	@Mapping(target = "flowFormato", source = "fullFlowData.model.fileFormat")
    //OK
	@Mapping(target = "flowDelimRecord", source = "fullFlowData.model.recordDelimiter")
    //OK
	@Mapping(target = "flowDelimStringa", source = "fullFlowData.model.stringDelimiter")
    //OK
	@Mapping(target = "flowRimozSpazi", source = "fullFlowData.model.removingSpaces")
    //OK
	@Mapping(target = "flowDelimCampo", source = "fullFlowData.model.fieldDelimiter")
    //OK
	@Mapping(target = "flowRiempCampo", source = "fullFlowData.model.numericFilling")
    //OK
	@Mapping(target = "flowCodepage", source = "fullFlowData.model.destCharset")
	//DA AGGIUNGERE NEL MODEL (da mettere anche a video)
	//@Mapping(target = "flowModAcquisizione", source = "")
    //OK
	@Mapping(target = "flowFromCcsid", source = "fullFlowData.model.sourceCharset")
	//AL MOMENTO NON IMPLEMENTATO
	//@Mapping(target = "flowPgmControllo", source = "")
    //OK
	//@Mapping(target = "flowTipologiaConn", source = "fullFlowData.model.type")
	//OK
    @Mapping(target = "flowHost", source = "fullFlowData._interface.host")
	//OK
    @Mapping(target = "flowPort", source = "fullFlowData._interface.port")
	//OK
	@Mapping(target = "flowRemoteFolder", source = "fullFlowData.flow.remoteFolder")
	//OK
	@Mapping(target = "flowRemoteFileName", source = "fullFlowData.flow.remoteFile")
    //OK
	@Mapping(target = "flowUtente", source = "fullFlowData._interface.user")
    //OK
	@Mapping(target = "flowPassword", source = "fullFlowData._interface.password")
	//OK
	@Mapping(target = "flowUtenteSftp", source = "fullFlowData._interface.sftpAlias")
    //OK
	@Mapping(target = "flowIntegrityCheck", source = "fullFlowData.model.integrityCheck")
	//OK
	@Mapping(target = "flowFlNameSemaforo", source = "fullFlowData.flow.integrityFileName")
    //OK
	@Mapping(target = "flowNumTentaRicez", source = "fullFlowData.model.retry")
    //OK
	@Mapping(target = "flowIntervalloRetry", source = "fullFlowData.model.retryInterval")
    //OK
	@Mapping(target = "flowRetention", source = "fullFlowData.model.retention")
    //OK
	@Mapping(target = "flowCompression", source = "fullFlowData.model.compression")
    //OK
	@Mapping(target = "flowDeCompression", source = "fullFlowData.model.decompression")
	//DA AGGIUNGERE sia nei parametri generali sia nel flow
	//@Mapping(target = "flowBackup", source = "")
    //OK
	@Mapping(target = "flowInviaMail", source = "fullFlowData.model.sendMail")
	//OK
	@Mapping(target = "flowLetteraOk", source = "fullFlowData.flow.notificationOk")
	//OK
	@Mapping(target = "flowLetteraKo", source = "fullFlowData.flow.notificationKo")
	//AL MOMENTO NON IMPLEMENTATO
	//@Mapping(target = "flowProgPerson", source = "")
    //OK
	@Mapping(target = "flowKnownHtFl", source = "fullFlowData._interface.knownHost")
    //OK
	@Mapping(target = "flowKeyFl", source = "fullFlowData._interface.keyFile")
    //OK
	@Mapping(target = "flowModalitaPassiva", source = "fullFlowData._interface.passiveMode")
	//AL MOMENTO NON IMPLEMENTATO
	//@Mapping(target = "flowJobDesc", source = "")
	//AL MOMENTO NON IMPLEMENTATO
	//@Mapping(target = "flowLibJobDesc", source = "")
    //OK
	@Mapping(target = "flowCancellaFile", source = "fullFlowData.model.deleteFile")
	//AL MOMENTO NON IMPLEMENTATO
	//@Mapping(target = "flowRisottomet", source = "")
	//OK
	@Mapping(target = "flowLunghezzaFlFlat", source = "fullFlowData.flow.lengthFixed")
	//OK
	@Mapping(target = "flowTipoTrasferimento", source = "fullFlowData._interface.connectionType")
	//AL MOMENTO NON IMPLEMENTATO
	//@Mapping(target = "flowBypassQtemp", source = "")
	//AL MOMENTO NON IMPLEMENTATO
	//@Mapping(target = "flowEsistenzaFile", source = "")
	///OK
	@Mapping(target = "flowLetteraFlusso", source = "fullFlowData.flow.notificationFlow")
    //OK
	@Mapping(target = "flowAggNomiCol", source = "fullFlowData.model.header")
    //OK
	@Mapping(target = "flowCreaVuoto", source = "fullFlowData.model.createFile")
    //OK
	@Mapping(target = "flowInternaz", source = "fullFlowData.model.internationalization")
	//AL MOMENTO NON PARAMETRIZZATO MA IMPLEMENTATO
	//@Mapping(target = "flowSostValNull", source = "")
	//AL MOMENTO NON PARAMETRIZZATO MA IMPLEMENTATO
	//@Mapping(target = "flowElimNomCol", source = "")
	//AL MOMENTO NON PARAMETRIZZATO MA IMPLEMENTATO
	//@Mapping(target = "flowFlagOkVuoto", source = "")
    //OK
	@Mapping(target = "flowFtpSecure", source = "fullFlowData._interface.secureFtp")
	//AL MOMENTO NON IMPLEMENTATO
	//@Mapping(target = "flowInteractiveType", source = "")
	//AL MOMENTO NON IMPLEMENTATO
	//@Mapping(target = "flowInteractiveProgram", source = "")
	//AL MOMENTO NON IMPLEMENTATO
	//@Mapping(target = "flowInteractiveResult", source = "")
	//AL MOMENTO NON IMPLEMENTATO
	//@Mapping(target = "flowInteractiveCommand", source = "")
	//AL MOMENTO NON PARAMETRIZZATO MA IMPLEMENTATO
	//@Mapping(target = "flowDelaySemaforo", source = "")
    //OK
	@Mapping(target = "flowHashUnico", source = "fullFlowData.model.uniqueHash")
	//AL MOMENTO NON PARAMETRIZZATO MA IMPLEMENTATO (utile ordinamento colonne)
	//@Mapping(target = "flowExportFlag", source = "")
	//AL MOMENTO NON PARAMETRIZZATO MA IMPLEMENTATO
	//@Mapping(target = "flowExportCode", source = "")
    //OK
	@Mapping(target = "flowFetchSize", source = "fullFlowData.model.fetchSize")
	//AL MOMENTO NON PARAMETRIZZATO MA IMPLEMENTATO
	//@Mapping(target = "flowCharEmptySpace", source = "")
	ExecutionFlowData convert(FullFlowData fullFlowData);

}
