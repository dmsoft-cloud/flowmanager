package it.dmsoft.flowmanager.be.converter;

import it.dmsoft.flowmanager.be.converter.base.BaseCodeEnumConverter;
import it.dmsoft.flowmanager.common.domain.Domains.DbType;
import jakarta.persistence.Converter;

@Converter
public class DbTypeConverter extends BaseCodeEnumConverter<DbType> {
}
