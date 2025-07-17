package it.dmsoft.flowmanager.agent.engine.mailclient.pec.verificapec.interfacce;

public class PecConnection {
	private String hostname;
	private int port;
	private boolean secure;
	private String imapUser;
	private String imapPwd;

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isSecure() {
		return secure;
	}

	public void setSecure(boolean secure) {
		this.secure = secure;
	}

	public String getImapUser() {
		return imapUser;
	}

	public void setImapUser(String imapUser) {
		this.imapUser = imapUser;
	}

	public String getImapPwd() {
		return imapPwd;
	}

	public void setImapPwd(String imapPwd) {
		this.imapPwd = imapPwd;
	}
}
