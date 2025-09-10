package it.dmsoft.flowmanager.master.api.log.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import it.dmsoft.flowmanager.be.entities.FlowLogDetails;
import it.dmsoft.flowmanager.common.model.FlowLogDetailsData;
import it.dmsoft.flowmanager.framework.api.base.BaseMapper;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.WARN)
public interface FlowLogDetailsMapper extends BaseMapper<FlowLogDetails, FlowLogDetailsData>{
	
	@Mapping(source = "flowLogDetailsId.logProgrLog", target = "logProgrLog")
	@Mapping(source = "flowLogDetailsId.logProgrFase", target = "logProgrFase")
	@Override
	FlowLogDetailsData convertEntity(FlowLogDetails entity);
	
	@Mapping(source = "logProgrLog", target = "flowLogDetailsId.logProgrLog")
	@Mapping(source = "logProgrFase", target = "flowLogDetailsId.logProgrFase")
	@Override
	FlowLogDetails convertModel(FlowLogDetailsData model);

}
