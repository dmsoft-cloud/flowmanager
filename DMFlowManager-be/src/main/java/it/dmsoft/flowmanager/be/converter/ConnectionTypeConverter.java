package it.dmsoft.flowmanager.be.converter;

import it.dmsoft.flowmanager.be.common.BaseEntity.ConnectionType;
import it.dmsoft.flowmanager.be.converter.base.BaseCodeEnumConverter;
import jakarta.persistence.Converter;

@Converter
public class ConnectionTypeConverter extends BaseCodeEnumConverter<ConnectionType> {


}
