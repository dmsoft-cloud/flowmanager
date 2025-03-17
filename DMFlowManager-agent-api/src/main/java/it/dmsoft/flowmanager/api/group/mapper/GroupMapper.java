package it.dmsoft.flowmanager.api.group.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import it.dmsoft.flowmanager.be.entities.ConfigurationGroup;
import it.dmsoft.flowmanager.common.model.GroupData;
import it.dmsoft.flowmanager.framework.api.base.BaseMapper;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface GroupMapper  extends BaseMapper<ConfigurationGroup, GroupData>{
}
