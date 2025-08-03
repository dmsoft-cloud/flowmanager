package it.dmsoft.flowmanager.framework.converter;



import it.dmsoft.flowmanager.common.domain.Domains.RecordDelimiterType;
import it.dmsoft.flowmanager.framework.converter.base.BaseCodeEnumConverter;
import jakarta.persistence.Converter;

@Converter
public class RecordDelimiterTypeConverter extends BaseCodeEnumConverter<RecordDelimiterType> {
}
