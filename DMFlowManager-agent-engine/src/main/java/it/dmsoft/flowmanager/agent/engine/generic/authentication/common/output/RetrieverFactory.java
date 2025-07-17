package it.dmsoft.flowmanager.agent.engine.generic.authentication.common.output;

public class RetrieverFactory {

	public static <T extends Retriever<?>> T create(Class<T> clazz) throws RetrieverFactoryException {
		try {
			return clazz.newInstance();
		} catch (InstantiationException e) {
			throw new RetrieverFactoryException("Impossibile creare istanza");
		} catch (IllegalAccessException e) {
			throw new RetrieverFactoryException("Costruttore non disponibile");
		}
	}

	public static class RetrieverFactoryException extends Exception {
		private static final long serialVersionUID = 1L;

		public RetrieverFactoryException(String message) {
			super(message);
		}

	}
}
