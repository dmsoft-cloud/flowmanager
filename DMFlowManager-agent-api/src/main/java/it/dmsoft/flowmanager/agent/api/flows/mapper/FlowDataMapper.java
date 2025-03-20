package it.dmsoft.flowmanager.agent.api.flows.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import it.dmsoft.flowmanager.agent.api.flows.model.ExecutionFlowData;
import it.dmsoft.flowmanager.common.model.EmailData;
import it.dmsoft.flowmanager.common.model.FlowData;
import it.dmsoft.flowmanager.common.model.GroupData;
import it.dmsoft.flowmanager.common.model.InterfaceData;
import it.dmsoft.flowmanager.common.model.ModelData;
import it.dmsoft.flowmanager.common.model.OriginData;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface FlowDataMapper {
    
    @Mapping(target = "description", source = "person.description")
	@Mapping(target = "flowId", source = "flow.id")
	@Mapping(target = "flowDesc", source = "flow.description")
	@Mapping(target = "flowGruppo", source = "group.id")
	@Mapping(target = "flowCodInterfaccia", source = "interface.id")
	@Mapping(target = "flowStato", source = "flow.enabled")
	@Mapping(target = "flowTipFlusso", source = "model.type")
	@Mapping(target = "flowDirezione", source = "model.direction")
	@Mapping(target = "flowLibreria", source = "model.database")
	@Mapping(target = "flowFile", source = "flow.file")
	@Mapping(target = "flowMembro", source = "")
	@Mapping(target = "flowLibSource", source = "")
	@Mapping(target = "flowFileSource", source = "")
	@Mapping(target = "flowMembroSource", source = "")
	@Mapping(target = "flowFolder", source = "flow.folder")
	@Mapping(target = "flowFileName", source = "flow.file")
	@Mapping(target = "flowFormato", source = "model.fileFormat")
	@Mapping(target = "flowDelimRecord", source = "model.recordDelimiter")
	@Mapping(target = "flowDelimStringa", source = "model.stringDelimiter")
	@Mapping(target = "flowRimozSpazi", source = "model.removingSpaces")
	@Mapping(target = "flowDelimCampo", source = "model.fieldDelimiter")
	@Mapping(target = "flowRiempCampo", source = "model.numericFilling")
	@Mapping(target = "flowCodepage", source = "model.destCharset")
	@Mapping(target = "flowModAcquisizione", source = "")
	@Mapping(target = "flowFromCcsid", source = "model.sourceCharset")
	@Mapping(target = "flowPgmControllo", source = "")
	@Mapping(target = "flowTipologiaConn", source = "interface.connectionType")
	@Mapping(target = "flowHost", source = "interface.host")
	@Mapping(target = "flowPort", source = "interface.port")
	@Mapping(target = "flowRemoteFolder", source = "flow.remoteFolder")
	@Mapping(target = "flowRemoteFileName", source = "flow.remoteFile")
	@Mapping(target = "flowUtente", source = "interface.user")
	@Mapping(target = "flowPassword", source = "interface.password")
	@Mapping(target = "flowUtenteSftp", source = "interface.sftpAlias")
	@Mapping(target = "flowIntegrityCheck", source = "model.integrityCheck")
	@Mapping(target = "flowFlNameSemaforo", source = "flow.integrityFileName")
	@Mapping(target = "flowNumTentaRicez", source = "model.retry")
	@Mapping(target = "flowIntervalloRetry", source = "model.retryInterval")
	@Mapping(target = "flowRetention", source = "model.retention")
	@Mapping(target = "flowCompression", source = "model.compression")
	@Mapping(target = "flowDeCompression", source = "model.decompression")
	@Mapping(target = "flowBackup", source = "")
	@Mapping(target = "flowInviaMail", source = "model.sendMail")
	@Mapping(target = "flowLetteraOk", source = "flow.notificationOk")
	@Mapping(target = "flowLetteraKo", source = "flow.notificationKo")
	@Mapping(target = "flowProgPerson", source = "")
	@Mapping(target = "flowKnownHtFl", source = "interface.knownHost")
	@Mapping(target = "flowKeyFl", source = "interface.keyFile")
	@Mapping(target = "flowModalitaPassiva", source = "interface.passiveMode")
	@Mapping(target = "flowJobDesc", source = "")
	@Mapping(target = "flowLibJobDesc", source = "")
	@Mapping(target = "flowCancellaFil", source = "model.deleteFile")
	@Mapping(target = "flowRisottomet", source = "")
	@Mapping(target = "flowLunghezzaFlFlat", source = "flow.lengthFixed")
	@Mapping(target = "flowTipoTrasferimento", source = "model.type")
	@Mapping(target = "flowBypassQtemp", source = "")
	@Mapping(target = "flowEsistenzaFile", source = "")
	@Mapping(target = "flowLetteraFlusso", source = "")
	@Mapping(target = "flowAggNomiCol", source = "")
	@Mapping(target = "flowCreaVuoto", source = "")
	@Mapping(target = "flowInternaz", source = "")
	@Mapping(target = "flowSostValNull", source = "")
	@Mapping(target = "flowElimNomCol", source = "")
	@Mapping(target = "flowFlagOkVuoto", source = "")
	@Mapping(target = "flowFtpSecure", source = "interface.secureFtp")
	@Mapping(target = "flowInteractiveType", source = "")
	@Mapping(target = "flowInteractiveProgram", source = "")
	@Mapping(target = "flowInteractiveResult", source = "")
	@Mapping(target = "flowInteractiveCommand", source = "")
	@Mapping(target = "flowDelaySemaforo", source = "")
	@Mapping(target = "flowHashUnico", source = "model.uniqueHash")
	@Mapping(target = "flowExportFlag", source = "")
	@Mapping(target = "flowExportCode", source = "")
	@Mapping(target = "flowFetchSize", source = "")
	@Mapping(target = "flowCharEmptySpace", source = "")
	ExecutionFlowData convert(FlowData flow, GroupData group, EmailData emailOk, EmailData emailKo,
			InterfaceData _interface, ModelData model, OriginData origin);

}
