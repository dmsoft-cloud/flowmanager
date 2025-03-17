package it.dmsoft.flowmanager.framework.converter;

import it.dmsoft.flowmanager.common.domain.Domains.FileFormat;
import it.dmsoft.flowmanager.framework.converter.base.BaseCodeEnumConverter;
import jakarta.persistence.Converter;

@Converter
public class FileFormatConverter extends BaseCodeEnumConverter<FileFormat> {
}
