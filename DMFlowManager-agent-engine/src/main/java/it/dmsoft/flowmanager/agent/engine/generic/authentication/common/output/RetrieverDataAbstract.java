package it.dmsoft.flowmanager.agent.engine.generic.authentication.common.output;

public abstract class RetrieverDataAbstract {
	private boolean attivo;

	protected RetrieverDataAbstract(boolean attivo) {
		super();
		this.attivo = attivo;
	}

	public boolean isAttivo() {
		return attivo;
	}

	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
	}

}
