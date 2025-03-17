package it.dmsoft.flowmanager.framework.converter;

import it.dmsoft.flowmanager.common.domain.Domains.ConnectionType;
import it.dmsoft.flowmanager.framework.converter.base.BaseCodeEnumConverter;
import jakarta.persistence.Converter;

@Converter
public class ConnectionTypeConverter extends BaseCodeEnumConverter<ConnectionType> {


}
