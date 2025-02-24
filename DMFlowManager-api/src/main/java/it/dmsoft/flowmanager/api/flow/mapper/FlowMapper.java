package it.dmsoft.flowmanager.api.flow.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import it.dmsoft.flowmanager.api.base.BaseMapper;
import it.dmsoft.flowmanager.api.flow.model.FlowData;
import it.dmsoft.flowmanager.be.entities.Flow;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface FlowMapper extends BaseMapper<Flow, FlowData>{

}
