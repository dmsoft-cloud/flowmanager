package it.dmsoft.flowmanager.agent.engine.generic.utility.json;

import java.util.HashMap;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;

public class OcsPropertyNamingStrategy extends PropertyNamingStrategy {
	private HashMap<String, String> listKey = new HashMap<String, String>();

	private static final long serialVersionUID = -1018442581283355437L;

	public void setListKey(HashMap<String, String> listKey) {
		this.listKey = listKey;
	}

	@Override
	public String nameForField(MapperConfig<?> config, AnnotatedField field, String defaultName) {
		return convert(defaultName);

	}

	@Override
	public String nameForGetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
		return convert(defaultName);
	}

	@Override
	public String nameForSetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
		String a = convert(defaultName);
		return a;
	}

	public String convert(String defaultName) {

		if (listKey.containsKey(defaultName)) {
			return listKey.get(defaultName);
		}

		return defaultName;
	}

}
