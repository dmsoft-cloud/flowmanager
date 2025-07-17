package it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.restful;

import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.exceptions.OCSAuthenticationException;
import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.exceptions.OCSURLOAuth2NotFound;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class Authentication {

	public static HttpHeaders getBasicHeaders(MediaType mediaType, String username, String password, Logger logger) {
		String auth = username + ":" + password;
		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
		String authHeader = "Basic " + new String(encodedAuth);
		logger.debug("Imposto Basic authentication: " + authHeader);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", authHeader);
		headers.setContentType(mediaType); // application/json
		return headers;
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public static HttpHeaders getBasicHeaders(MediaType mediaType, String username, String password, org.apache.log4j.Logger logger4j) {
		return getBasicHeaders(mediaType, username, password, Logger.log4j2Logger(logger4j));
	}

	public static HttpHeaders getOAuth2Headers(MediaType mediaType, String urlToken, String userbasic, String pwdbasic, Logger logger) throws OCSAuthenticationException, OCSURLOAuth2NotFound {

		if (urlToken == null || "".equals(urlToken.trim())) {
			throw new OCSURLOAuth2NotFound();
		}

		ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
		resourceDetails.setAccessTokenUri(urlToken);
		resourceDetails.setClientId(userbasic);
		resourceDetails.setClientSecret(pwdbasic);

		OAuth2RestTemplate oAuthRestTemplate = new OAuth2RestTemplate(resourceDetails);
		OAuth2AccessToken token = null;
		long start = System.currentTimeMillis();

		boolean isTokenRetrieveFailed = false;

		try {
			logger.info("Recupero del token dall'indirizzo: " + urlToken);
			token = oAuthRestTemplate.getAccessToken();
			logger.debug("Token staccato: " + token);
		} catch (Exception e) {
			logger.error("Errore in recupero del token di validazione", e);
			isTokenRetrieveFailed = true;
		} finally {
			long duration = System.currentTimeMillis() - start;
			logger.info("Durata recupero token: " + duration + " ms");
		}

		if (isTokenRetrieveFailed) {
			throw new OCSAuthenticationException("Errore in recupero del token di validazione");
		}

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);
		headers.setContentType(mediaType); // application/json
		return headers;
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public static HttpHeaders getOAuth2Headers(MediaType mediaType, String urlToken, String userbasic, String pwdbasic, org.apache.log4j.Logger logger4j)
			throws OCSAuthenticationException, OCSURLOAuth2NotFound {
		return getOAuth2Headers(mediaType, urlToken, userbasic, pwdbasic, Logger.log4j2Logger(logger4j));
	}
}