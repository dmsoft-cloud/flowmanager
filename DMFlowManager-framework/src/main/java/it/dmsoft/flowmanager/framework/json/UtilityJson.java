package it.dmsoft.flowmanager.framework.json;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.Timestamp;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.JsonDeserializer;




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
		//sostituito con i moduli registrati sul mapper generico
		//SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
		//mapper.setDateFormat(d);
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
			mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
			mapper.findAndRegisterModules();
			mapperGenerico.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
			mapperGenerico.findAndRegisterModules();
			prettyMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
			mapperIgnoreUnkonwn.findAndRegisterModules();
			prettyMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
			prettyMapper.findAndRegisterModules();
						

            SimpleModule module = new SimpleModule();
            final SimpleDateFormat dateFmt = new SimpleDateFormat("yyyyMMdd");
            final SimpleDateFormat tsFmt   = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

            module.addSerializer(Date.class, new StdSerializer<Date>(Date.class) {
                @Override
                public void serialize(Date value, JsonGenerator gen, SerializerProvider provider) throws IOException {
                  gen.writeString(dateFmt.format(value));
                }
              });

	        module.addSerializer(Timestamp.class, new StdSerializer<Timestamp>(Timestamp.class) {
	            @Override
	            public void serialize(Timestamp value, JsonGenerator gen, SerializerProvider provider) throws IOException {
	              gen.writeString(tsFmt.format(value));
	            }
	          });
	        
	        module.addDeserializer(Timestamp.class, new JsonDeserializer<Timestamp>() {
	            @Override
	            public Timestamp deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
	              String s = p.getValueAsString();
	              if (s == null || s.isBlank()) return null;

	              // accetta sia "2026-02-16 22:36:34.161" sia "2026-02-16 22:36:34"
	              LocalDateTime ldt;
	              if (s.length() == 19) { // yyyy-MM-dd HH:mm:ss
	                ldt = LocalDateTime.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	              } else {
	                ldt = LocalDateTime.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
	              }
	              return Timestamp.valueOf(ldt);
	            }
	          });


	        mapper.registerModule(module);
	        mapperGenerico.registerModule(module);
	        prettyMapper.registerModule(module);
	        mapperIgnoreUnkonwn.registerModule(module);

		}

		init = true;
	}
}


