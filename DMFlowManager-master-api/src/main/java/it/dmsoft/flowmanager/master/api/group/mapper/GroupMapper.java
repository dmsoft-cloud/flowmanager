package it.dmsoft.flowmanager.master.api.group.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import it.dmsoft.flowmanager.common.model.GroupData;
import it.dmsoft.flowmanager.framework.api.base.BaseMapper;
import it.dmsoft.flowmanager.master.be.entities.ConfigurationGroup;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface GroupMapper  extends BaseMapper<ConfigurationGroup, GroupData>{
}
