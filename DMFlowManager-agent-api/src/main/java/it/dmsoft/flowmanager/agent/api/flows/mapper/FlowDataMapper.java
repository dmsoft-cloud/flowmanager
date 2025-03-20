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
	@Mapping(target = "flowTipologiaConn", source = "")
	@Mapping(target = "flowHost", source = "")
	@Mapping(target = "flowPort", source = "")
	@Mapping(target = "flowRemoteFolder", source = "")
	@Mapping(target = "flowRemoteFileName", source = "")
	@Mapping(target = "flowUtente", source = "")
	@Mapping(target = "flowPassword", source = "")
	@Mapping(target = "flowUtenteSftp", source = "")
	@Mapping(target = "flowIntegrityCheck", source = "")
	@Mapping(target = "flowFlNameSemaforo", source = "")
	@Mapping(target = "flowNumTentaRicez", source = "")
	@Mapping(target = "flowIntervalloRetry", source = "")
	@Mapping(target = "flowRetention", source = "")
	@Mapping(target = "flowCompression", source = "")
	@Mapping(target = "flowDeCompression", source = "")
	@Mapping(target = "flowBackup", source = "")
	@Mapping(target = "flowInviaMail", source = "")
	@Mapping(target = "flowLetteraOk", source = "")
	@Mapping(target = "flowLetteraKo", source = "")
	@Mapping(target = "flowProgPerson", source = "")
	@Mapping(target = "flowKnownHtFl", source = "")
	@Mapping(target = "flowKeyFl", source = "")
	@Mapping(target = "flowModalitaPassiva", source = "")
	@Mapping(target = "flowJobDesc", source = "")
	@Mapping(target = "flowLibJobDesc", source = "")
	@Mapping(target = "flowCancellaFil", source = "")
	@Mapping(target = "flowRisottomet", source = "")
	@Mapping(target = "flowLunghezzaFlFlat", source = "")
	@Mapping(target = "flowTipoTrasferimento", source = "")
	@Mapping(target = "flowBypassQtemp", source = "")
	@Mapping(target = "flowEsistenzaFile", source = "")
	@Mapping(target = "flowLetteraFlusso", source = "")
	@Mapping(target = "flowAggNomiCol", source = "")
	@Mapping(target = "flowCreaVuoto", source = "")
	@Mapping(target = "flowInternaz", source = "")
	@Mapping(target = "flowSostValNull", source = "")
	@Mapping(target = "flowElimNomCol", source = "")
	@Mapping(target = "flowFlagOkVuoto", source = "")
	@Mapping(target = "flowFtpSecure", source = "")
	@Mapping(target = "flowInteractiveType", source = "")
	@Mapping(target = "flowInteractiveProgram", source = "")
	@Mapping(target = "flowInteractiveResult", source = "")
	@Mapping(target = "flowInteractiveCommand", source = "")
	@Mapping(target = "flowDelaySemaforo", source = "")
	@Mapping(target = "flowHashUnico", source = "")
	@Mapping(target = "flowExportFlag", source = "")
	@Mapping(target = "flowExportCode", source = "")
	@Mapping(target = "flowFetchSize", source = "")
	@Mapping(target = "flowCharEmptySpace", source = "")
	ExecutionFlowData convert(FlowData flow, GroupData group, EmailData emailOk, EmailData emailKo,
			InterfaceData _interface, ModelData model, OriginData origin);

}
