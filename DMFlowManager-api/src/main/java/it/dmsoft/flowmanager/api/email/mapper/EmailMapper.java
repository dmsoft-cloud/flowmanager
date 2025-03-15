package it.dmsoft.flowmanager.api.email.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import it.dmsoft.flowmanager.api.base.BaseMapper;
import it.dmsoft.flowmanager.api.email.model.EmailData;
import it.dmsoft.flowmanager.api.email.model.RecipientData;
import it.dmsoft.flowmanager.be.entities.Email;
import it.dmsoft.flowmanager.be.entities.Recipient;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface EmailMapper  extends BaseMapper<Email, EmailData>{
	
	@Override
    @Mapping(target = "id", source = "id") // Assicura il mapping dell'ID
    Email convertModel(EmailData model);

	@Override
    EmailData convertEntity(Email entity);
	
}
