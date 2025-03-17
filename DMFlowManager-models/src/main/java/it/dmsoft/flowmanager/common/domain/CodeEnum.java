package it.dmsoft.flowmanager.common.domain;

import java.util.Arrays;

public interface CodeEnum {
	
	public String getCode();
	
	/*public static <T extends CodeEnum> T getCodeEnum(Class<T> enumClass, String code) {
		return Arrays.asList(enumClass.getEnumConstants()).stream().filter(x -> x.getCode().equals(code)).findFirst().get();
	}*/

    static <T extends CodeEnum> T getCodeEnum(Class<T> enumClass, String code) {
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(e -> e.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown code: '" + code + "' for enum " + enumClass.getSimpleName()));
    }
	
}
