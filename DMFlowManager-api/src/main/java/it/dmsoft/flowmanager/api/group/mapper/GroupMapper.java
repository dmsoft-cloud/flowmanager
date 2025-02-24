package it.dmsoft.flowmanager.api.group.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import it.dmsoft.flowmanager.api.base.BaseMapper;
import it.dmsoft.flowmanager.api.group.model.GroupData;
import it.dmsoft.flowmanager.be.entities.ConfigurationGroup;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface GroupMapper  extends BaseMapper<ConfigurationGroup, GroupData>{
}
