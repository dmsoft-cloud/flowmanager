package it.dmsoft.flowmanager.agent.engine.mailclient.pec.verificapec.interfacce;

public class MoveInfo {
	private String messageId;
	private String moveFolder;

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getMoveFolder() {
		return moveFolder;
	}

	public void setMoveFolder(String moveFolder) {
		this.moveFolder = moveFolder;
	}
}
