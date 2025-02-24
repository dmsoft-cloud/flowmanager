package it.dmsoft.flowmanager.api.interfaces.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import it.dmsoft.flowmanager.api.base.BaseMapper;
import it.dmsoft.flowmanager.api.interfaces.model.InterfaceData;
import it.dmsoft.flowmanager.be.entities.Interface;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface InterfaceMapper extends BaseMapper<Interface, InterfaceData>{

}
