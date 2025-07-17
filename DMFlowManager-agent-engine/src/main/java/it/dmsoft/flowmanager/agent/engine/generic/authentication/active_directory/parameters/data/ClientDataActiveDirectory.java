package it.dmsoft.flowmanager.agent.engine.generic.authentication.active_directory.parameters.data;

import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.parameters.data.ClientDataOauth;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.AppOpePar;

public class ClientDataActiveDirectory extends ClientDataOauth {
	private String tenantId;

	public ClientDataActiveDirectory(AppOpePar params, String url, String clientId, String clientSecret, String scope, String tenantId) {
		super(params, url);
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.scope = scope;
		this.tenantId = tenantId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	@Override
	public String toString() {
		return "ClientDataActiveDirectory [clientId=" + clientId + ", clientSecret=" + clientSecret + ", scope=" + scope + ", tenantId=" + tenantId + ", toString()=" + super.toString() + "]";
	}
}