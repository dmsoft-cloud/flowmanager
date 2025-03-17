package it.dmsoft.flowmanager.agent.api.email.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import it.dmsoft.flowmanager.agent.be.entities.Email;
import it.dmsoft.flowmanager.common.model.EmailData;
import it.dmsoft.flowmanager.framework.api.base.BaseMapper;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface EmailMapper  extends BaseMapper<Email, EmailData>{
	
	@Override
    @Mapping(target = "id", source = "id") // Assicura il mapping dell'ID
    Email convertModel(EmailData model);

	@Override
    EmailData convertEntity(Email entity);
	
}
