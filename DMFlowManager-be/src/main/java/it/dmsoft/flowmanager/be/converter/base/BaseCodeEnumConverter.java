package it.dmsoft.flowmanager.be.converter.base;



import java.lang.reflect.ParameterizedType;

import it.dmsoft.flowmanager.be.common.CodeEnum;
import jakarta.persistence.AttributeConverter;

public abstract class BaseCodeEnumConverter<T extends CodeEnum> implements AttributeConverter<T, String>{

	protected Class<? extends CodeEnum> codeEnumClass;
	
    @SuppressWarnings("unchecked")
    public BaseCodeEnumConverter() {
        this.codeEnumClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
	
	public String convertToDatabaseColumn(T attribute) {
		return attribute != null ? attribute.getCode() : null;
	}

	@SuppressWarnings("unchecked")
	public T convertToEntityAttribute(String dbData) {
		return (dbData != null) ? (T) CodeEnum.getCodeEnum(codeEnumClass, dbData) : null;
	}
}
