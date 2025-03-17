package it.dmsoft.flowmanager.agent.be.converter;

import it.dmsoft.flowmanager.agent.be.converter.base.BaseCodeEnumConverter;
import it.dmsoft.flowmanager.common.domain.Domains.FileFormat;
import jakarta.persistence.Converter;

@Converter
public class FileFormatConverter extends BaseCodeEnumConverter<FileFormat> {
}
