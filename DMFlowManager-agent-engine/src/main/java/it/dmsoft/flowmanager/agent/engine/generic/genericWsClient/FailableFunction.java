package it.dmsoft.flowmanager.agent.engine.generic.genericWsClient;

import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public interface FailableFunction<T, R> {

	R apply(T t, Logger logger) throws Exception;

}
