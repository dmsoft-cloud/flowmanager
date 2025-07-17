package it.dmsoft.flowmanager.agent.engine.generic.authentication.santander;

import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.AuthenticationProperties.NomeApplicazione;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.output.Retriever;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.output.data.TokenRetrieverData;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.exception.AuthenticationException;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class TokenRetrieverSantander extends TokenRetrieverSantanderAbs implements Retriever<TokenRetrieverData> {

	@Override
	public TokenRetrieverData get(Logger logger) throws AuthenticationException {
		return authenticationToken(logger, NomeApplicazione.SANTANDERAUTHENTICATIONCLIENT);
	}
}
