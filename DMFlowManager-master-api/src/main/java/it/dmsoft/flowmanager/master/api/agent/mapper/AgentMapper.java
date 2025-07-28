package it.dmsoft.flowmanager.master.api.agent.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import it.dmsoft.flowmanager.be.entities.Agent;
import it.dmsoft.flowmanager.common.model.AgentData;
import it.dmsoft.flowmanager.framework.api.base.BaseMapper;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.WARN)
public interface AgentMapper extends BaseMapper<Agent, AgentData>{

}
