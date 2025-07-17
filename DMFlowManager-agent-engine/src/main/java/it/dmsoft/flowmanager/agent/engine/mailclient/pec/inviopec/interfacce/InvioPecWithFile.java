package it.dmsoft.flowmanager.agent.engine.mailclient.pec.inviopec.interfacce;

import it.dmsoft.flowmanager.agent.engine.mailclient.interfacce.RequestWithFile;

public class InvioPecWithFile extends RequestWithFile {
	private String imapHost;
	private int imapPort;
	private boolean imapSecure;
	private String imapUser;
	private String imapPwd;
	private ImapEmailInfo imapEmailInfo;

	public String getImapHost() {
		return imapHost;
	}

	public void setImapHost(String imapHost) {
		this.imapHost = imapHost;
	}

	public int getImapPort() {
		return imapPort;
	}

	public void setImapPort(int imapPort) {
		this.imapPort = imapPort;
	}

	public boolean isImapSecure() {
		return imapSecure;
	}

	public void setImapSecure(boolean imapSecure) {
		this.imapSecure = imapSecure;
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

	public ImapEmailInfo getImapEmailInfo() {
		return imapEmailInfo;
	}

	public void setImapEmailInfo(ImapEmailInfo imapEmailInfo) {
		this.imapEmailInfo = imapEmailInfo;
	}
}
