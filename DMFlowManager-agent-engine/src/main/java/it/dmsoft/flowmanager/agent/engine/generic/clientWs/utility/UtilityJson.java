package it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UtilityJson {
	static ObjectMapper mapperGenerico = new ObjectMapper();
	static ObjectMapper mapper = new ObjectMapper();
	static ObjectMapper mapperIgnoreUnkonwn = new ObjectMapper();
	static ObjectMapper prettyMapper = new ObjectMapper();

	private UtilityJson() {

	}

	static{
		mapperIgnoreUnkonwn.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	public static String writeValueAsString(Object obj) throws JsonProcessingException {
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
		return mapperGenerico.writeValueAsString(obj);
	}
	
	public static String prettyWriteValueAsString(Object obj) throws JsonProcessingException {
		return prettyMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
	}
	
	public static <T> T readValue(String content, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
		return mapper.readValue(content, valueType);
	}
	
	public static <T> T readValueIgnoreUnknown(String content, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
		return mapperIgnoreUnkonwn.readValue(content, valueType);
	}
	
	public static <T> T readValue(File content, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
		return mapper.readValue(content, valueType);
	}

}
