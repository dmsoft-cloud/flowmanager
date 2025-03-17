package it.dmsoft.flowmanager.be.converter;

import it.dmsoft.flowmanager.be.converter.base.BaseCodeEnumConverter;
import it.dmsoft.flowmanager.common.domain.Domains.ConnectionType;
import jakarta.persistence.Converter;

@Converter
public class ConnectionTypeConverter extends BaseCodeEnumConverter<ConnectionType> {


}
