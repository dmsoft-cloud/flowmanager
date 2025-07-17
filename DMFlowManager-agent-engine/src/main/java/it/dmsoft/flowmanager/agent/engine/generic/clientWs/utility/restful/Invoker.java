package it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.restful;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.UtilityJson;
import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.exceptions.OCSAccessDeniedException;
import it.dmsoft.flowmanager.agent.engine.generic.utility.json.LoggingRequestInterceptor;
import it.dmsoft.flowmanager.agent.engine.generic.utility.json.OcsResponseErrorHandler;
import it.dmsoft.flowmanager.agent.engine.generic.utility.json.exceptions.HttpException;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class Invoker {

	public static final String NOT_ENCODE_QUERYPARAM = "NOT_ENCODE_QUERYPARAM";

	private Invoker() {

	}

	public static String getParamsString(Map<String, String> params, Logger logger) throws UnsupportedEncodingException {
		return getParamsString(params, null, logger);
	}

	public static String getParamsString(Map<String, String> params, Integer lunghezzaLimite, Logger logger) throws UnsupportedEncodingException {
		StringBuilder result = new StringBuilder();
		boolean encode = true;

		if (params == null) {
			return "";
		}

		if (params.containsKey(NOT_ENCODE_QUERYPARAM)) {
			encode = false;
		}

		for (Map.Entry<String, String> entry : params.entrySet()) {
			if ((entry.getKey() != null && !entry.getKey().equals("") && !entry.getKey().equals(NOT_ENCODE_QUERYPARAM)) && (entry.getValue() != null) && !entry.getValue().equals("")) {
				if (encode) {
					result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
				} else {
					result.append(entry.getKey());
				}

				result.append("=");
				String tmp;
				if (encode) {
					tmp = URLEncoder.encode(entry.getValue(), "UTF-8");
				} else {
					tmp = entry.getValue();
				}

				// Modifica introdotta per CREC lunghezza campo non oltre 255
				// LeadClient 31/03/2021
				if (lunghezzaLimite != null && tmp.length() > lunghezzaLimite) {
					result.append(tmp.substring(0, lunghezzaLimite));
				} else {
					result.append(tmp);
				}
				result.append("&");
			}
		}
		String resultString = result.toString();
		resultString = resultString.length() > 0 ? resultString.substring(0, resultString.length() - 1) : resultString;
		logger.debug("Query parameters: " + resultString);
		return resultString;
	}

	public static <T> OcsGenericRestResponse<T> callEndpoint(String endPoint, Map<String, String> params, HttpMethod method, HttpHeaders headers, String request, int timeout, Class<T> clazz,
			Logger logger) throws ParseException, IOException {
		return callEndpoint(endPoint, params, method, headers, request, timeout, true, clazz, logger);
	}

	public static <T> OcsGenericRestResponse<T> callEndpoint(String endPoint, Map<String, String> params, HttpMethod method, HttpHeaders headers, String request, int timeout, boolean outputStreaming,
			Class<T> clazz, Logger logger) throws ParseException, IOException {
		return callEndpoint(endPoint, params, method, headers, request, null, timeout, outputStreaming, clazz, logger);
	}

	public static <T> OcsGenericRestResponse<T> callEndpoint(String endPoint, Map<String, String> params, HttpMethod method, HttpHeaders headers, Object request, Map<String, File> files, int timeout,
			boolean outputStreaming, Class<T> clazz, Logger logger) throws ParseException, IOException {
		return callEndpoint(endPoint, params, method, headers, request, files, timeout, outputStreaming, clazz, true, logger);
	}

	public static <T> OcsGenericRestResponse<T> callEndpoint(String endPoint, HttpMethod method, HttpHeaders headers, String request, int timeout, Class<T> clazz, Logger logger)
			throws ParseException, IOException {
		return callEndpoint(endPoint, null, method, headers, request, timeout, true, clazz, logger);

	}

	@SuppressWarnings("unchecked")
	public static <T> OcsGenericRestResponse<T> callEndpoint(String endPoint, Map<String, String> params, HttpMethod method, HttpHeaders headers, Object request, Map<String, File> files, int timeout,
			boolean outputStreaming, Class<T> clazz, boolean setAcceptCharset, Logger logger) throws ParseException, IOException {

		if (params != null) {
			String queryParam = getParamsString(params, logger);
			if (queryParam != null && queryParam.length() > 0) {
				endPoint += "?" + queryParam;
			}
		}

		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setOutputStreaming(outputStreaming);
		if (timeout != 0) {
			factory.setConnectTimeout(timeout);
			factory.setReadTimeout(timeout);
		}

		RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(factory));

		// TODO Rimuovere da qui
		// MappingJackson2HttpMessageConverter converter = new
		// MappingJackson2HttpMessageConverter();
		// ObjectMapper objectMapper = new ObjectMapper();
		// objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS,
		// true);
		// converter.setObjectMapper(objectMapper);
		// restTemplate.getMessageConverters().add(0, converter);
		// TODO Rimuovere a qui

		StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
		stringHttpMessageConverter.setWriteAcceptCharset(setAcceptCharset);
		restTemplate.getMessageConverters().add(0, stringHttpMessageConverter);

		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
		interceptors.add(new LoggingRequestInterceptor(logger));
		restTemplate.setInterceptors(interceptors);

		OcsResponseErrorHandler responseHandler = new OcsResponseErrorHandler();
		restTemplate.setErrorHandler(responseHandler);

		HttpEntity<?> entity = null;

		if (request instanceof String) {
			entity = new HttpEntity<String>((String) request, headers);
		} else {
			LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			if (files != null && files.entrySet() != null) {
				for (Entry<String, File> entry : files.entrySet()) {
					if (entry != null && entry.getValue() != null && entry.getKey() != null) {
						map.add(entry.getKey(), new FileSystemResource(entry.getValue()));
					}
				}
			}
			if (request instanceof HashMap<?, ?>) {
				HashMap<Object, Object> reqMap = ((HashMap<Object, Object>) request);
				for (Entry<Object, Object> entry : reqMap.entrySet()) {
					if (entry != null && entry.getValue() != null && entry.getKey() instanceof String) {
						map.add((String) entry.getKey(), entry.getValue());
					}
				}
			}
			if (!map.isEmpty()) {
				entity = new HttpEntity<LinkedMultiValueMap<String, Object>>(map, headers);
			}
		}

		if (entity == null) {
			entity = new HttpEntity<String>(headers);
		}

		return readResponse(method, entity, restTemplate, endPoint, clazz, logger);

	}

	@SuppressWarnings("unchecked")
	private static <T> OcsGenericRestResponse<T> readResponse(HttpMethod method, HttpEntity<?> entity, RestTemplate restTemplate, String endPoint, Class<T> returnClazz, Logger logger)
			throws ParseException, JsonParseException, JsonMappingException, IOException {

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(endPoint);
		logger.debug("Recupero risorsa su URL: " + builder.build().encode().toUri());

		OcsGenericRestResponse<T> ocsResponse = new OcsGenericRestResponse<T>();

		ResponseEntity<?> response = null;
		long start = System.currentTimeMillis();

		try {
			// Esecuzione chiamata WS
			if ((byte[].class).equals(returnClazz)) {
				response = restTemplate.exchange(builder.build().encode().toUri(), method, entity, byte[].class);
				ocsResponse.setResponse((T) response.getBody());
			} else {
				response = restTemplate.exchange(builder.build().encode().toUri(), method, entity, String.class);
			}

			if ((String.class).equals(returnClazz)) {
				ocsResponse.setResponse((T) response.getBody());
			} else if (returnClazz != null && !returnClazz.equals(byte[].class)) {
				JSONParser parser = new JSONParser();
				if (response.getBody() != null && !response.getBody().equals("")) {
					Object json = parser.parse((String) response.getBody());
					if (json instanceof JSONObject) {
						ocsResponse.setResponse(UtilityJson.readValue(((JSONObject) json).toString(), returnClazz));
					}
					if (json instanceof JSONArray) {
						ocsResponse.setResponse(UtilityJson.readValue(((JSONArray) json).toString(), returnClazz));
					}
				} else {
					ocsResponse.setResponseError((ResponseEntity<String>) response);
				}
			}
			ocsResponse.setHeaders(response.getHeaders());
			ocsResponse.setTimeout(false);
		} catch (ParseException e) {
			logger.error("Struttura di errore inattesa: " + response.getBody());
			ocsResponse.setResponseError((ResponseEntity<String>) response);
			throw e;
		} catch (JsonParseException e) {
			logger.error("Struttura di errore inattesa: " + response.getBody());
			ocsResponse.setResponseError((ResponseEntity<String>) response);
			throw e;
		} catch (JsonMappingException e) {
			logger.error("Struttura di errore inattesa: " + response.getBody());
			ocsResponse.setResponseError((ResponseEntity<String>) response);
			throw e;
		} catch (IOException e) {
			logger.error("Struttura di errore inattesa: " + response.getBody());
			ocsResponse.setResponseError((ResponseEntity<String>) response);
			throw e;
		} catch (IllegalArgumentException e) {
			if ("No matching constant for [550]".equals(e.getMessage())) {
				logger.error("Status code: 550");
				ocsResponse.setError(new OCSAccessDeniedException());
				ocsResponse.setErrori(true);
			} else {
				throw e;
			}
		} catch (Exception e) {
			if (e.getCause() instanceof HttpException) {
				String body = ((HttpException) e.getCause()).getBody();
				HttpStatusCode status = ((HttpException) e.getCause()).getStatusCode();
				logger.error("Status code: " + status);
				logger.error("Status text: " + ((HttpException) e.getCause()).getStatusText());
				logger.error("Body       : " + body);
				ocsResponse.setResponseError(new ResponseEntity<String>(body, status));
			} else if (e.getCause() instanceof SocketTimeoutException) {
				logger.error(((SocketTimeoutException) e.getCause()).getMessage(), e);
				ocsResponse.setTimeout(true);
				throw ((SocketTimeoutException) e.getCause());
			} else if (e.getCause() instanceof ConnectException) {
				logger.error("Connect Exception", e);
				throw ((ConnectException) e.getCause());
			} else {
				logger.error("Errore di invocazione", e);
			}
			ocsResponse.setError(e.getCause());
		} finally {
			long duration = System.currentTimeMillis() - start;
			logger.info("Durata chiamata a '" + endPoint + "': " + duration + " ms");
		}

		return ocsResponse;
	}

	public static OcsGenericRestResponse<String> callEndpoint(String endPoint, HttpMethod method, HttpHeaders headers, String request, int timeout, Logger logger) throws ParseException, IOException {

		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		if (timeout != 0) {
			factory.setConnectTimeout(timeout);
			factory.setReadTimeout(timeout);
		}

		RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(factory));
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
		interceptors.add(new LoggingRequestInterceptor(logger));
		restTemplate.setInterceptors(interceptors);

		OcsResponseErrorHandler responseHandler = new OcsResponseErrorHandler();
		restTemplate.setErrorHandler(responseHandler);

		HttpEntity<String> entity = new HttpEntity<String>(request, headers);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(endPoint);
		URI finalUri = builder.build().encode().toUri();
		logger.debug("Recupero risorsa su URL: " + finalUri);

		OcsGenericRestResponse<String> ocsResponse = new OcsGenericRestResponse<String>();
		ResponseEntity<String> response = null;
		long start = System.currentTimeMillis();

		try {
			response = restTemplate.exchange(finalUri, method, entity, String.class);
			ocsResponse.setResponse(response.getBody());
			ocsResponse.setTimeout(false);
		} catch (Exception e) {
			if (e.getCause() instanceof HttpException) {
				String body = ((HttpException) e.getCause()).getBody();
				HttpStatusCode status = ((HttpException) e.getCause()).getStatusCode();
				logger.error("Status code: " + status);
				logger.error("Status text: " + ((HttpException) e.getCause()).getStatusText());
				logger.error("Body       : " + body);
				ocsResponse.setResponseError(new ResponseEntity<String>(body, status));
			} else if (e.getCause() instanceof SocketTimeoutException) {
				logger.error(((SocketTimeoutException) e.getCause()).getMessage(), e);
				ocsResponse.setTimeout(true);
				throw ((SocketTimeoutException) e.getCause());
			} else if (e.getCause() instanceof ConnectException) {
				logger.error("Connect Exception", e);
				throw ((ConnectException) e.getCause());
			} else {
				logger.error("Errore di invocazione", e);
			}
			ocsResponse.setError(e.getCause());
		} finally {
			long duration = System.currentTimeMillis() - start;
			logger.info("Durata chiamata a '" + endPoint + "': " + duration + " ms");
		}
		return ocsResponse;
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public static <T> OcsGenericRestResponse<T> callEndpoint(String endPoint, Map<String, String> params, HttpMethod method, HttpHeaders headers, String request, int timeout, Class<T> clazz,
			org.apache.log4j.Logger logger) throws ParseException, IOException {
		return callEndpoint(endPoint, method, headers, request, timeout, clazz, Logger.log4j2Logger(logger));
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public static <T> OcsGenericRestResponse<T> callEndpoint(String endPoint, HttpMethod method, HttpHeaders headers, String request, int timeout, Class<T> clazz, org.apache.log4j.Logger logger)
			throws ParseException, IOException {

		return callEndpoint(endPoint, method, headers, request, timeout, clazz, Logger.log4j2Logger(logger));
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public static OcsGenericRestResponse<String> callEndpoint(String endPoint, HttpMethod method, HttpHeaders headers, String request, int timeout, org.apache.log4j.Logger logger)
			throws ParseException, IOException {

		return callEndpoint(endPoint, method, headers, request, timeout, Logger.log4j2Logger(logger));
	}

}
