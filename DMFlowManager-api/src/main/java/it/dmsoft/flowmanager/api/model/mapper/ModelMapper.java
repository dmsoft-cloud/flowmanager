package it.dmsoft.flowmanager.api.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import it.dmsoft.flowmanager.api.base.BaseMapper;
import it.dmsoft.flowmanager.api.model.model.ModelData;
import it.dmsoft.flowmanager.be.entities.Model;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface ModelMapper extends BaseMapper<Model, ModelData>{

}
