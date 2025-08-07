package it.dmsoft.flowmanager.master.api.log.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import it.dmsoft.flowmanager.be.entities.Flow;
import it.dmsoft.flowmanager.be.entities.FlowLog;
import it.dmsoft.flowmanager.common.model.FlowData;
import it.dmsoft.flowmanager.common.model.FlowLogData;
import it.dmsoft.flowmanager.framework.api.base.BaseMapper;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.WARN)
public interface FlowLogMapper extends BaseMapper<FlowLog, FlowLogData>{

}
