package it.dmsoft.flowmanager.agent.engine.generic.authentication.debk;

import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class TokenRetrieverDeutscheBank {

	@SuppressWarnings("deprecation")
	public static HttpHeaders OAuth2RestTokenRetriever(String userbasic, String pwdbasic, String urlToken, Logger logger) {
		HttpHeaders headers = new HttpHeaders();
		if ((userbasic == null || userbasic.equals("")) || (pwdbasic == null || pwdbasic.equals("")) || (urlToken == null || urlToken.equals(""))) {
			logger.warn("Dati per recupero token non validi");
			return headers;
		}
		ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
		resourceDetails.setAccessTokenUri(urlToken);
		resourceDetails.setClientId(userbasic);
		resourceDetails.setClientSecret(pwdbasic);

		OAuth2RestTemplate oAuthRestTemplate = new OAuth2RestTemplate(resourceDetails);
		OAuth2AccessToken token = null;
		long start = System.currentTimeMillis();
		logger.info("Recupero del token dall'indirizzo: " + urlToken);
		token = oAuthRestTemplate.getAccessToken();
		logger.debug("Token staccato: " + token);

		long duration = System.currentTimeMillis() - start;
		logger.info("Durata recupero token: " + duration + " ms");

		headers.set("Authorization", "Bearer " + token);

		return headers;
	}
}
