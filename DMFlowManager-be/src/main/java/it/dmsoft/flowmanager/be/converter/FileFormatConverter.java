package it.dmsoft.flowmanager.be.converter;

import it.dmsoft.flowmanager.be.common.BaseEntity.FileFormat;
import it.dmsoft.flowmanager.be.converter.base.BaseCodeEnumConverter;
import jakarta.persistence.Converter;

@Converter
public class FileFormatConverter extends BaseCodeEnumConverter<FileFormat> {
}
