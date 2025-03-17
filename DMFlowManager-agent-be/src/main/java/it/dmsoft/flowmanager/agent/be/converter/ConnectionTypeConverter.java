package it.dmsoft.flowmanager.agent.be.converter;

import it.dmsoft.flowmanager.agent.be.converter.base.BaseCodeEnumConverter;
import it.dmsoft.flowmanager.common.domain.Domains.ConnectionType;
import jakarta.persistence.Converter;

@Converter
public class ConnectionTypeConverter extends BaseCodeEnumConverter<ConnectionType> {


}
