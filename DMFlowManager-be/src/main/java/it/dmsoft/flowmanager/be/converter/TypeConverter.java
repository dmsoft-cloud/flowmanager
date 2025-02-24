package it.dmsoft.flowmanager.be.converter;

import it.dmsoft.flowmanager.be.common.BaseEntity.Type;
import it.dmsoft.flowmanager.be.converter.base.BaseCodeEnumConverter;
import jakarta.persistence.Converter;

@Converter
public class TypeConverter extends BaseCodeEnumConverter<Type> {
}
