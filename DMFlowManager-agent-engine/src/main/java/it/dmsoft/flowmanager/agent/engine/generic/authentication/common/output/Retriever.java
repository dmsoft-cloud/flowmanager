package it.dmsoft.flowmanager.agent.engine.generic.authentication.common.output;

import it.dmsoft.flowmanager.agent.engine.generic.authentication.exception.AuthenticationException;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public interface Retriever<T> {
	
	public abstract T get(Logger logger) throws AuthenticationException;
}
