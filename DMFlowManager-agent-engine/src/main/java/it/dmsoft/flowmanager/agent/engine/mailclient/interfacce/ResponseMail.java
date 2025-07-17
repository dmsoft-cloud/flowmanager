package it.dmsoft.flowmanager.agent.engine.mailclient.interfacce;

public class ResponseMail {
	private String messageId;
	private FileMailInfo pathSavedMail;

	public ResponseMail(String messageId, FileMailInfo fileMailInfo) {
		super();
		this.messageId = messageId;
		this.pathSavedMail = fileMailInfo;
	}

	public String getMessageId() {
		if (messageId == null) {
			messageId = new String();
		}
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	@Override
	public String toString() {
		return "ResponseMail [messageId=" + messageId + "]";
	}

	public FileMailInfo getPathSavedMail() {
		return pathSavedMail;
	}

	public void setPathSavedMail(FileMailInfo pathSavedMail) {
		this.pathSavedMail = pathSavedMail;
	}
}