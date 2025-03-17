package it.dmsoft.flowmanager.master.be.converter;

import it.dmsoft.flowmanager.common.domain.Domains.DbType;
import it.dmsoft.flowmanager.master.be.converter.base.BaseCodeEnumConverter;
import jakarta.persistence.Converter;

@Converter
public class DbTypeConverter extends BaseCodeEnumConverter<DbType> {
}
