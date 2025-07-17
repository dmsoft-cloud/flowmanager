package it.dmsoft.flowmanager.agent.engine.mailclient.pec.inviopec.interfacce;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

public class OCSMimeMessage extends MimeMessage {
	private String from;

	public OCSMimeMessage(Session session, String from) {
		super(session);
		this.from = from;
	}

	@Override
	protected void updateMessageID() throws MessagingException {
		super.updateMessageID();
		String messageId = this.getMessageID();
		int index = -1;
		for (int i = 0; i < 3; i++) {
			index = messageId.indexOf('.', index + 1);
		}
		String newMessageId = messageId.subSequence(0, index + 1) + "OcsMailClient." + from + ">";
		setHeader("Message-ID", newMessageId);
	}

}
