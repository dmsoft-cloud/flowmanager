package it.dmsoft.flowmanager.api.model.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import it.dmsoft.flowmanager.api.base.BaseMapper;
import it.dmsoft.flowmanager.api.model.model.ModelData;
import it.dmsoft.flowmanager.be.entities.Model;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface ModelMapper extends BaseMapper<Model, ModelData>{
	
    @Override
    @Mapping(source = "db",       target = "database")
    @Mapping(source = "dbSchema", target = "schema")
    ModelData convertEntity(Model entity);
    
    
    // mapping inverso da DTO a Entity; erediti le stesse mappature
    @Override
    @InheritInverseConfiguration
    Model convertModel(ModelData modelData);

}
