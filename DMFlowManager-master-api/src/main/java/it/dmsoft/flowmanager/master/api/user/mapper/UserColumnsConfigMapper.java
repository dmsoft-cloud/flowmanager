package it.dmsoft.flowmanager.master.api.user.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import it.dmsoft.flowmanager.be.entities.User;
import it.dmsoft.flowmanager.be.entities.UserColumnsConfig;
import it.dmsoft.flowmanager.common.model.UserColumnsConfigData;
import it.dmsoft.flowmanager.common.model.UserData;
import it.dmsoft.flowmanager.framework.api.base.BaseMapper;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.WARN, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserColumnsConfigMapper extends BaseMapper<UserColumnsConfig, UserColumnsConfigData>{
	
	@Override
	@Mapping(target = "user", ignore = true)
    UserColumnsConfig convertModel(UserColumnsConfigData model);

	@Override
	UserColumnsConfigData convertEntity(UserColumnsConfig entity);

}
