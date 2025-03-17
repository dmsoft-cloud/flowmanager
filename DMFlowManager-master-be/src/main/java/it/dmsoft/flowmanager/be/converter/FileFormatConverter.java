package it.dmsoft.flowmanager.be.converter;

import it.dmsoft.flowmanager.be.converter.base.BaseCodeEnumConverter;
import it.dmsoft.flowmanager.common.domain.Domains.FileFormat;
import jakarta.persistence.Converter;

@Converter
public class FileFormatConverter extends BaseCodeEnumConverter<FileFormat> {
}
