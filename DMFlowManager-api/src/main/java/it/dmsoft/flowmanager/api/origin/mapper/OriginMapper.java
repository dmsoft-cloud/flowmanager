package it.dmsoft.flowmanager.api.origin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import it.dmsoft.flowmanager.api.base.BaseMapper;
import it.dmsoft.flowmanager.api.origin.model.OriginData;
import it.dmsoft.flowmanager.be.entities.Origin;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface OriginMapper extends BaseMapper<Origin, OriginData>{

}
