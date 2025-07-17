package it.dmsoft.flowmanager.agent.engine.generic.authentication.common.parameters;

import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.AppOpePar;

public abstract class ClientDataAbstract {
	private String url;
	private AppOpePar params;

	public ClientDataAbstract(AppOpePar params, String url) {
		super();
		this.url = url;
		this.params = params;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public AppOpePar getParams() {
		return params;
	}

	@Override
	public String toString() {
		return "ClientDataAbstract [url=" + url + ", params=" + params + "]";
	}

}
