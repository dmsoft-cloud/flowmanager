package it.dmsoft.flowmanager.be.converter;

import it.dmsoft.flowmanager.be.common.BaseEntity.StringDelimiterType;
import it.dmsoft.flowmanager.be.converter.base.BaseCodeEnumConverter;
import jakarta.persistence.Converter;

@Converter
public class StringDelimiterTypeConverter extends BaseCodeEnumConverter<StringDelimiterType> {
}
