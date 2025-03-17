package it.dmsoft.flowmanager.master.be.converter;

import it.dmsoft.flowmanager.common.domain.Domains.Direction;
import it.dmsoft.flowmanager.master.be.converter.base.BaseCodeEnumConverter;
import jakarta.persistence.Converter;

@Converter
public class DirectionConverter extends BaseCodeEnumConverter<Direction> {


}
