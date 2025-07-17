package it.dmsoft.flowmanager.agent.engine.generic.utility.json;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomUtilityJson {
	static ObjectMapper mapper = null;
	static ObjectMapper mapperTime = null;
	private static HashMap<String, String> listKey = new HashMap<String, String>();
	private static HashMap<String, String> listKeyTime = new HashMap<String, String>();
	static OcsPropertyNamingStrategy ocsPropertyNamingStrategy = new OcsPropertyNamingStrategy();
	static OcsPropertyNamingStrategy ocsPropertyNamingStrategyTime = new OcsPropertyNamingStrategy();

	public static String writeValueAsString(Object obj) throws JsonProcessingException {
		setMapper();
		return mapper.writeValueAsString(obj);
	}

	public static JsonNode writeValueAsJson(Object obj) throws JsonProcessingException {
		setMapper();
		return mapper.valueToTree(obj);
	}

	public static JsonNode writeValueAsJsonWithTime(Object obj) throws JsonProcessingException {
		setMapperWithTime();
		return mapperTime.valueToTree(obj);
	}

	public static <T> T readValue(String content, Class<T> valueType)
			throws IOException, JsonParseException, JsonMappingException {
		return mapper.readValue(content, valueType);
	}

	public static <T> T readValueWithTime(String content, Class<T> valueType)
			throws IOException, JsonParseException, JsonMappingException {
		return mapperTime.readValue(content, valueType);
	}

	private static void setMapper() {
		if (mapper == null) {
			mapper = new ObjectMapper();
			SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
			mapper.setDateFormat(d);
			mapper.setPropertyNamingStrategy(ocsPropertyNamingStrategy);
		}
		ocsPropertyNamingStrategy.setListKey(getListKey());
	}

	private static void setMapperWithTime() {
		if (mapperTime == null) {
			mapperTime = new ObjectMapper();
			SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
			mapperTime.setDateFormat(d);
			mapperTime.setPropertyNamingStrategy(ocsPropertyNamingStrategyTime);
		}
		ocsPropertyNamingStrategyTime.setListKey(getListKeyTime());
	}

	public static void addListKeyTime(String key, String value) {
		getListKeyTime().put(key, value);
	}

	private static HashMap<String, String> getListKeyTime() {
		if (listKeyTime == null) {
			listKeyTime = new HashMap<String, String>();
		}
		return listKeyTime;
	}

	public static void addListKey(String key, String value) {
		getListKey().put(key, value);
	}

	private static HashMap<String, String> getListKey() {
		if (listKey == null) {
			listKey = new HashMap<String, String>();
		}
		return listKey;
	}

}
