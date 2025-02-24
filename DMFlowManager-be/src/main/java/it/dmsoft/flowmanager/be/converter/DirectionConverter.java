package it.dmsoft.flowmanager.be.converter;

import it.dmsoft.flowmanager.be.common.BaseEntity.Direction;
import it.dmsoft.flowmanager.be.converter.base.BaseCodeEnumConverter;
import jakarta.persistence.Converter;

@Converter
public class DirectionConverter extends BaseCodeEnumConverter<Direction> {


}
