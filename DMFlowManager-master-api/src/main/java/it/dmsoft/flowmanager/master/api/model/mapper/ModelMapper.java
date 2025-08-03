package it.dmsoft.flowmanager.master.api.model.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import it.dmsoft.flowmanager.be.entities.Model;
import it.dmsoft.flowmanager.common.model.ModelData;
import it.dmsoft.flowmanager.framework.api.base.BaseMapper;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.WARN, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
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
