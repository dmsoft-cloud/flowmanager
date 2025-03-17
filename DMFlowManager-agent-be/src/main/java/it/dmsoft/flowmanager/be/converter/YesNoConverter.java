package it.dmsoft.flowmanager.be.converter;

import it.dmsoft.flowmanager.be.converter.base.BaseCodeEnumConverter;
import it.dmsoft.flowmanager.common.domain.Domains.YesNo;
import jakarta.persistence.Converter;

@Converter
public class YesNoConverter extends BaseCodeEnumConverter<YesNo> {
}
