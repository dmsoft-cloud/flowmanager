package it.dmsoft.flowmanager.be.converter;

import it.dmsoft.flowmanager.be.common.BaseEntity.Locale;
import it.dmsoft.flowmanager.be.converter.base.BaseCodeEnumConverter;
import jakarta.persistence.Converter;

@Converter
public class LocaleConverter extends BaseCodeEnumConverter<Locale> {
}
