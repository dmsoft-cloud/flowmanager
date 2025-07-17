package it.dmsoft.flowmanager.agent.engine.mailclient.pec.inviopec.interfacce;

import it.dmsoft.flowmanager.agent.engine.mailclient.interfacce.FileMailInfo;

public class InvioPecResponse {
	private String msgId;
	private FileMailInfo pathSavedMail;

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public FileMailInfo getPathSavedMail() {
		return pathSavedMail;
	}

	public void setPathSavedMail(FileMailInfo pathSavedMail) {
		this.pathSavedMail = pathSavedMail;
	}

}
