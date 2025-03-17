package it.dmsoft.flowmanager.framework.converter;

import it.dmsoft.flowmanager.common.domain.Domains.Type;
import it.dmsoft.flowmanager.framework.converter.base.BaseCodeEnumConverter;
import jakarta.persistence.Converter;

@Converter
public class TypeConverter extends BaseCodeEnumConverter<Type> {
}
