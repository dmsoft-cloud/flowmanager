package it.dmsoft.flowmanager.framework.converter;


import it.dmsoft.flowmanager.common.domain.Domains.StringDelimiterType;
import it.dmsoft.flowmanager.framework.converter.base.BaseCodeEnumConverter;
import jakarta.persistence.Converter;

@Converter
public class StringDelimiterTypeConverter extends BaseCodeEnumConverter<StringDelimiterType> {
}
