package it.dmsoft.flowmanager.api.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import it.dmsoft.flowmanager.be.entities.Model;
import it.dmsoft.flowmanager.common.model.ModelData;
import it.dmsoft.flowmanager.framework.api.base.BaseMapper;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface ModelMapper extends BaseMapper<Model, ModelData>{

}
