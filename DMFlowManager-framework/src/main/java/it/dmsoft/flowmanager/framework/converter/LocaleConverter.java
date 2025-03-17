package it.dmsoft.flowmanager.framework.converter;

import it.dmsoft.flowmanager.common.domain.Domains.Locale;
import it.dmsoft.flowmanager.framework.converter.base.BaseCodeEnumConverter;
import jakarta.persistence.Converter;

@Converter
public class LocaleConverter extends BaseCodeEnumConverter<Locale> {
}
