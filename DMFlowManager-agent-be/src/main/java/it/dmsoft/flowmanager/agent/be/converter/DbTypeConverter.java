package it.dmsoft.flowmanager.agent.be.converter;

import it.dmsoft.flowmanager.agent.be.converter.base.BaseCodeEnumConverter;
import it.dmsoft.flowmanager.common.domain.Domains.DbType;
import jakarta.persistence.Converter;

@Converter
public class DbTypeConverter extends BaseCodeEnumConverter<DbType> {
}
