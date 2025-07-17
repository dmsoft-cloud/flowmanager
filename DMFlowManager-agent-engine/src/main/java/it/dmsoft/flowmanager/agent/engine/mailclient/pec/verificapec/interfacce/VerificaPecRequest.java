package it.dmsoft.flowmanager.agent.engine.mailclient.pec.verificapec.interfacce;

public class VerificaPecRequest extends PecConnection {
	private String inboxFolder;

	public String getInboxFolder() {
		return inboxFolder;
	}

	public void setInboxFolder(String inboxFolder) {
		this.inboxFolder = inboxFolder;
	}
	
}
