package it.dmsoft.flowmanager.framework.json;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UtilityJson {
	
	private static ObjectMapper mapperGenerico = new ObjectMapper();
	private static ObjectMapper mapper = new ObjectMapper();
	private static ObjectMapper mapperIgnoreUnkonwn = new ObjectMapper();
	private static ObjectMapper prettyMapper = new ObjectMapper();

	private static boolean init = false;
	
	private UtilityJson() {

	}

	static{
		mapperIgnoreUnkonwn.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	public static ObjectMapper getMapper() {
		initJson();
		return mapper;
	}
	
	public static String writeValueAsString(Object obj) throws JsonProcessingException {
		initJson();
		SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
		mapper.setDateFormat(d);
		return mapper.writeValueAsString(obj);
	}

	/**
	 * Traduzione dell'oggetto in stringa tramite mapper senza specificare un DateFormat
	 * @param obj
	 * @return
	 * @throws JsonProcessingException
	 */
	public static String writeValueAsStringRaw(Object obj) throws JsonProcessingException {
		initJson();
		return mapperGenerico.writeValueAsString(obj);
	}
	
	public static String prettyWriteValueAsString(Object obj) throws JsonProcessingException {
		initJson();
		return prettyMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
	}
	
	public static <T> T readValue(String content, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
		initJson();
		return mapper.readValue(content, valueType);
	}
	
	public static <T> T readValueIgnoreUnknown(String content, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
		initJson();
		return mapperIgnoreUnkonwn.readValue(content, valueType);
	}
	
	public static <T> T readValue(File content, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
		initJson();
		return mapper.readValue(content, valueType);
	}

	private static void initJson() {
		if(!init) {
			mapper.findAndRegisterModules();
			mapperGenerico.findAndRegisterModules();
			mapperIgnoreUnkonwn.findAndRegisterModules();
			prettyMapper.findAndRegisterModules();
		}
		
		init = true;
	}
}


