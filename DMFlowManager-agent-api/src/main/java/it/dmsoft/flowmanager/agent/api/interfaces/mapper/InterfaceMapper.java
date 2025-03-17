package it.dmsoft.flowmanager.agent.api.interfaces.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import it.dmsoft.flowmanager.agent.be.entities.Interface;
import it.dmsoft.flowmanager.common.model.InterfaceData;
import it.dmsoft.flowmanager.framework.api.base.BaseMapper;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface InterfaceMapper extends BaseMapper<Interface, InterfaceData>{

}
