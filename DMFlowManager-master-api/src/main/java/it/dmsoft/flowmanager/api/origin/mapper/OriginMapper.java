package it.dmsoft.flowmanager.api.origin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import it.dmsoft.flowmanager.be.entities.Origin;
import it.dmsoft.flowmanager.common.model.OriginData;
import it.dmsoft.flowmanager.framework.api.base.BaseMapper;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface OriginMapper extends BaseMapper<Origin, OriginData>{

}
