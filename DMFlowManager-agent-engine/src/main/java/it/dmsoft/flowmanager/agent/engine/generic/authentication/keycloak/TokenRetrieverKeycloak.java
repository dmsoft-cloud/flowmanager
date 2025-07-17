package it.dmsoft.flowmanager.agent.engine.generic.authentication.keycloak;

import java.net.URI;

//import com.nimbusds.oauth2.sdk.token.AccessToken;

import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.AuthenticationProperties;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.AuthenticationProperties.NomeApplicazione;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.output.Retriever;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.output.data.TokenRetrieverData;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.parameters.data.ClientDataOauth;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.exception.AuthenticationException;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.FailableFunction;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.GenericWsManager;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.KeyValueLog;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;
//import it.ocsnet.ngp.filter.auth.OauthAccessTokenManager;

public class TokenRetrieverKeycloak implements Retriever<TokenRetrieverData> {
	@Override
	public TokenRetrieverData get(Logger logger) throws AuthenticationException {

		FailableFunction<ClientDataOauth, TokenRetrieverData> ff = new FailableFunction<ClientDataOauth, TokenRetrieverData>() {
			@Override
			public TokenRetrieverData apply(ClientDataOauth data, Logger logger) throws Exception {
				//AccessToken accessToken = OauthAccessTokenManager.getAccessToken(URI.create(data.getUrl()),
				//		data.getClientId(), data.getClientSecret());

				return new TokenRetrieverData(true, null/*accessToken.getValue()*/);
			}
		};

		// Recupero parametri per comunicazione.
		ClientDataOauth data = AuthenticationProperties.getData(NomeApplicazione.KEYCLOAKAUTHORIZATIONCLIENT,
				ClientDataOauth.class);

		if (data == null) {
			logger.warn("Impossibile recuperare dati di autenticazione");
			return new TokenRetrieverData(false, null);
		}

		logger.info(new KeyValueLog("ClientDataOauth", data.toString()));

		try {
			return GenericWsManager.applyFunctionWithParameters(ff, data, data.getParams(), logger);
		} catch (Exception e) {
			throw new AuthenticationException(e.getMessage(), e);
		}
	}

}
