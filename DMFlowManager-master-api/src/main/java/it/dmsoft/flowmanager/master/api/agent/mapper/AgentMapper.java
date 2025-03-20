package it.dmsoft.flowmanager.master.api.agent.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import it.dmsoft.flowmanager.common.model.AgentData;
import it.dmsoft.flowmanager.framework.api.base.BaseMapper;
import it.dmsoft.flowmanager.master.be.entities.Agent;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface AgentMapper extends BaseMapper<Agent, AgentData>{

}
