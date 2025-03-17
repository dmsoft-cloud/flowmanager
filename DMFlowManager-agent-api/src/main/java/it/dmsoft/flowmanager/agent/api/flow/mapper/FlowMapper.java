package it.dmsoft.flowmanager.agent.api.flow.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import it.dmsoft.flowmanager.agent.be.entities.Flow;
import it.dmsoft.flowmanager.common.model.FlowData;
import it.dmsoft.flowmanager.framework.api.base.BaseMapper;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface FlowMapper extends BaseMapper<Flow, FlowData>{

}
