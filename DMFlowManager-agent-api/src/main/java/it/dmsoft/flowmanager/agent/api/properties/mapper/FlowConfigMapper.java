package it.dmsoft.flowmanager.agent.api.properties.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import it.dmsoft.flowmanager.agent.api.properties.AgentPropertiesService;
import it.dmsoft.flowmanager.be.entities.FlowConfig;
import it.dmsoft.flowmanager.common.model.FullFlowData;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.WARN)
public interface FlowConfigMapper {
	
	@Mapping(target = "transactionName", source = "fullFlowData.flow.id")
	@Mapping(target = "transactionId", source = "fullFlowData.flow.description")
	@Mapping(target = "cliente", source = "agentPropertiesService.customer")
	@Mapping(target = "IBMi", source = "agentPropertiesService.IBMi")
	FlowConfig convert(AgentPropertiesService agentPropertiesService, FullFlowData fullFlowData);

}
