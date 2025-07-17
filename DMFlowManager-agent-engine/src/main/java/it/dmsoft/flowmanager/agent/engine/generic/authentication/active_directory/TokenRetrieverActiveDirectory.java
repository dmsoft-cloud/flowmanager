package it.dmsoft.flowmanager.agent.engine.generic.authentication.active_directory;

import java.net.URI;

//import com.nimbusds.oauth2.sdk.token.AccessToken;

import it.dmsoft.flowmanager.agent.engine.generic.authentication.active_directory.parameters.data.ClientDataActiveDirectory;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.AuthenticationProperties;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.AuthenticationProperties.NomeApplicazione;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.output.Retriever;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.output.data.TokenRetrieverData;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.exception.AuthenticationException;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.KeyValueLog;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;
//import it.ocsnet.ngp.filter.auth.OauthAccessTokenManager;

public class TokenRetrieverActiveDirectory implements Retriever<TokenRetrieverData> {

	@Override
	public TokenRetrieverData get(Logger logger) throws AuthenticationException {
		// Recupero parametri per comunicazione.
		ClientDataActiveDirectory data = AuthenticationProperties.getData(NomeApplicazione.ACTIVEDIRECTORYCLIENT,
				ClientDataActiveDirectory.class);
		if (data == null) {
			logger.warn("Impossibile recuperare dati di autenticazione");
			return new TokenRetrieverData(false, null);
		}
		logger.info(new KeyValueLog("ClientDataActiveDirectory", data.toString()));
		//AccessToken accessToken = OauthAccessTokenManager.getAccessToken(URI.create(data.getUrl()), data.getClientId(),
		//		data.getClientSecret(), data.getScope());

		return new TokenRetrieverData(true, null/*accessToken.getValue()*/);
	}
}
