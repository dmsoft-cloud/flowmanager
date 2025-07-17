package it.dmsoft.flowmanager.agent.engine.mailclient.pec.verificapec.interfacce;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MovePecRequest extends PecConnection {
	private String inboxFolder;
	private List<MoveInfo> moveInfo;

	public String getInboxFolder() {
		return inboxFolder;
	}

	public void setInboxFolder(String inboxFolder) {
		this.inboxFolder = inboxFolder;
	}

	public List<MoveInfo> getMoveInfo() {
		if (moveInfo == null) {
			moveInfo = new ArrayList<>();
		}

		return moveInfo;
	}
	
	public HashMap<String, String> getMoveInfoInternal() {
		HashMap<String, String> moveInfoInternal = new HashMap<>();
		
		for (MoveInfo mi : getMoveInfo()) {
			moveInfoInternal.put(mi.getMessageId(), mi.getMoveFolder());
		}
		
		return moveInfoInternal;
	}

	public void setMoveInfo(List<MoveInfo> moveInfo) {
		this.moveInfo = moveInfo;
	}
}
