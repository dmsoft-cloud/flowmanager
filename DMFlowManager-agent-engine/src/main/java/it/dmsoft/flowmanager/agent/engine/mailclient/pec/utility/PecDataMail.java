package it.dmsoft.flowmanager.agent.engine.mailclient.pec.utility;

import java.io.IOException;

import it.dmsoft.flowmanager.agent.engine.mailclient.pec.inviopec.interfacce.ImapEmailInfo;
import it.dmsoft.flowmanager.agent.engine.mailclient.pec.inviopec.interfacce.InvioPecWithBody;
import it.dmsoft.flowmanager.agent.engine.mailclient.pec.inviopec.interfacce.InvioPecWithFile;
import it.dmsoft.flowmanager.agent.engine.mailclient.utility.DataMail;

public class PecDataMail extends DataMail {
	private String imapHost;
	private int imapPort;
	private boolean imapSecure;
	private String imapUser;
	private String imapPwd;
	private ImapEmailInfo imapEmailInfo;

	public PecDataMail(InvioPecWithBody request) {
		super(request);
		imapHost = request.getImapHost();
		imapPort = request.getImapPort();
		imapSecure = request.isImapSecure();
		imapUser = request.getImapUser();
		imapPwd = request.getImapPwd();
		imapEmailInfo = request.getImapEmailInfo();
	}

	public PecDataMail(InvioPecWithFile request) throws IOException {
		super(request);
		imapHost = request.getImapHost();
		imapPort = request.getImapPort();
		imapSecure = request.isImapSecure();
		imapUser = request.getImapUser();
		imapPwd = request.getImapPwd();
		imapEmailInfo = request.getImapEmailInfo();
	}

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
