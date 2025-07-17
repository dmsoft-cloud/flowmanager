package it.dmsoft.flowmanager.agent.engine.generic.utility.json;

import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.web.util.UriComponentsBuilder;

public class ManageURL {
	private HashMap<String, String> queryParams = null;
	private URI uri = null;
	private UriComponentsBuilder builder = null;

	public ManageURL(String endPoint) {
		this.builder = UriComponentsBuilder.fromHttpUrl(endPoint);
		this.queryParams = new HashMap<String, String>();
		this.uri = null;
	}

	public void addQueryParam(String key, String value) {
		if (queryParams == null) {
			queryParams = new HashMap<String, String>();
		}
		queryParams.put(key, value);
	}

	public URI getUri() {
		if (uri == null) {
			if (queryParams != null) {
				Iterator<Entry<String, String>> iQueryParams = queryParams.entrySet().iterator();
				while (iQueryParams.hasNext()) {
					Map.Entry<java.lang.String, java.lang.String> entry = (Map.Entry<java.lang.String, java.lang.String>) iQueryParams.next();
					if (entry.getValue() != null && !"".equals(entry.getValue().trim())) {
						builder.queryParam(entry.getKey(), entry.getValue());
					}
				}
			}
			uri = builder.build().encode().toUri();
		}
		return uri;
	}

}
