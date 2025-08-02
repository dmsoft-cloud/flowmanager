package it.dmsoft.flowmanager.be.converter;

import it.dmsoft.flowmanager.be.common.BaseEntity.RecordDelimiterType;
import it.dmsoft.flowmanager.be.converter.base.BaseCodeEnumConverter;
import jakarta.persistence.Converter;

@Converter
public class RecordDelimiterTypeConverter extends BaseCodeEnumConverter<RecordDelimiterType> {
}
