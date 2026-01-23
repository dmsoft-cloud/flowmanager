package it.dmsoft.flowmanager.master.api.user.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import it.dmsoft.flowmanager.be.entities.User;
import it.dmsoft.flowmanager.common.model.UserData;
import it.dmsoft.flowmanager.framework.api.base.BaseMapper;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.WARN, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper extends BaseMapper<User, UserData>{

	@Override
    @Mapping(target = "username", source = "username") // Assicura il mapping dell'ID
    User convertModel(UserData model);

	@Override
	UserData convertEntity(User entity);

}
