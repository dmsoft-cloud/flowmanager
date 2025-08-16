package it.dmsoft.flowmanager.master.api.log.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import it.dmsoft.flowmanager.be.entities.Email;
import it.dmsoft.flowmanager.be.entities.Flow;
import it.dmsoft.flowmanager.be.entities.FlowLog;
import it.dmsoft.flowmanager.be.entities.FlowLogDetails;
import it.dmsoft.flowmanager.common.model.EmailData;
import it.dmsoft.flowmanager.common.model.FlowData;
import it.dmsoft.flowmanager.common.model.FlowLogData;
import it.dmsoft.flowmanager.common.model.FlowLogDetailsData;
import it.dmsoft.flowmanager.framework.api.base.BaseMapper;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.WARN)
public interface FlowLogDetailsMapper extends BaseMapper<FlowLogDetails, FlowLogDetailsData>{

	@Override
    @Mapping(source = "flowLogDetailsId.logProgrLog",  target = "logProgrLog")
    @Mapping(source = "flowLogDetailsId.logProgrFase", target = "logProgrFase")
    FlowLogDetailsData convertEntity(FlowLogDetails entity);

    @Override
    @org.mapstruct.Mapping(target = "flowLogDetailsId.logProgrLog",  source = "logProgrLog")
    @org.mapstruct.Mapping(target = "flowLogDetailsId.logProgrFase", source = "logProgrFase")
    FlowLogDetails convertModel(FlowLogDetailsData data);
    
}
