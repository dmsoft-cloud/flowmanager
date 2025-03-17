package it.dmsoft.flowmanager.framework.converter;

import it.dmsoft.flowmanager.common.domain.Domains.Direction;
import it.dmsoft.flowmanager.framework.converter.base.BaseCodeEnumConverter;
import jakarta.persistence.Converter;

@Converter
public class DirectionConverter extends BaseCodeEnumConverter<Direction> {


}
